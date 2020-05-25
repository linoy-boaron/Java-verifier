package oop.ex6.parser;

import oop.ex6.Token;
import oop.ex6.ast.ASTNode;
import oop.ex6.ast.ASTNodeFactory;
import oop.ex6.ast.ProgramASTNode;
import oop.ex6.ast.ScopeNode;
import oop.ex6.parser.features.*;

import java.util.Stack;

/**
 * Defines a code parser, which's a unit that translates the given source code, represented as a String, into
 * an AST ({@link ASTNode}) by applying on it the registered {@link SyntaxFeature}s.
 */
public class CodeParser {
	// region Constants

	/**
	 * Defines the error that's being raised if no {@link SyntaxFeature} could parse the current source line.
	 */
	private static final String UNKNOWN_FEATURE_MESSAGE = "Unknown syntax feature detected";

	/**
	 * The exception message that will be raised if an unexpected "}" (end curly brace) has been found.
	 */
	private static final String UNEXPECTED_SCOPE_CLOSE_MESSAGE
			= "Unexpected token " + Token.RIGHT_CURLY_PREN;

	/**
	 * The exception message that's being raised if scopes hasn't been closed before the end off the program.
	 */
	private static final String MISSING_PROGRAM_END_SCOPE_MESSAGE = "Missing code scope closing";

	/**
	 * The runtime exception message that's being raised if one tries to return a {@link ScopeNode} or one of
	 * its children from the {@link SyntaxFeature#parse(String)} method, without making the parsing
	 * {@link SyntaxFeature} a child of {@link ScopeSyntaxFeature}.
	 */
	private static final String SCOPE_NODE_WITHOUT_SCOPE_FEATURE_MESSAGE = "You can not return from a " +
			"SyntaxFeature a ScopeNode (or a children of it) without inheriting ScopeSyntaxFeature.";

	/**
	 * The syntax features that're being allowed in the global scope.
	 */
	private static final SyntaxFeatureIdentifier[] GLOBAL_SCOPE_FEATURES =  new SyntaxFeatureIdentifier[] {
					SyntaxFeatureIdentifier.COMMENT,
					SyntaxFeatureIdentifier.ASSIGNMENT,
					SyntaxFeatureIdentifier.VARIABLE_DECLARATION,
					SyntaxFeatureIdentifier.FUNCTION_DECLARATION
			};

	/**
	 * The token that's being used to separate code lines.
	 */
	private static final String SOURCE_CODE_SEPARATION_TOKEN = "\n";

	// endregion

	// region Nested Types

	/**
	 * Describes a program scope entry on the program stack.
	 */
	private class ScopeStackEntry {
		// region iVars

		/**
		 * The scope {@link ASTNode}.
		 */
		private final ScopeNode scope;

		/**
		 * The syntax features available within this scope.
		 */
		private final SyntaxFeature[] syntaxFeatures;

		// endregion

		// region Initializer

		/**
		 * Creates a new scope entry in the stack.
		 * @param scope The actual scope.
		 * @param syntaxFeatures The scope syntax features list.
		 */
		ScopeStackEntry(ScopeNode scope, SyntaxFeature[] syntaxFeatures) {
			this.scope = scope;
			this.syntaxFeatures = syntaxFeatures;
		}

		// endregion
	}

	// endregion

	// region Ctor

	/**
	 * Initializes a new code parser.
	 */
	public CodeParser() {
	}

	// endregion

	// region Public API

	/**
	 * Parses the given source code into an AST.
	 * @param sourceCode The source code to parse.
	 * @return The created AST root.
	 * @throws SyntaxErrorException If a syntax error was detected during the parsing.
	 */
	public ProgramASTNode parse(String sourceCode)
		throws SyntaxErrorException {
		/* Init */
		int i = 0;
		Stack<ScopeStackEntry> scopesStack = new Stack<>();

		/* Adds the program main to the stack, as its tree root */
		scopesStack.push(new ScopeStackEntry(ASTNodeFactory.createProgram(),
				SyntaxFeatureFactory.factory((GLOBAL_SCOPE_FEATURES))));

		String line = null;
		String[] lines = sourceCode.split(SOURCE_CODE_SEPARATION_TOKEN);
		try {
			for (i = 0; i < lines.length; i++) {
				/* Parse */
				parseLine(lines[i], i + 1, scopesStack);
			}
		} catch (SyntaxFeatureParseException e) {
			throw new SyntaxErrorException(e, lines[i], i + 1);
		}

		/* Does our stack contains exactly one item - the ProgramScopeNode? if not, it means that someone
		has forgot to close enough code scopes. */
		if (scopesStack.size() != 1) {
			throw new SyntaxErrorException(MISSING_PROGRAM_END_SCOPE_MESSAGE, lines[i - 1], i - 1);
		}

		return (ProgramASTNode)(scopesStack.pop().scope);
	}

	// endregion

	// region Private API

	/**
	 * Parses the given source code line.
	 * @param line The source code line.
	 * @param lineNumber The source code line number.
	 * @param scopesStack The current scopes stack.
	 * @throws SyntaxFeatureParseException If a parse error has been raised by the {@link SyntaxFeature}
	 * this line is using to get parsed.
	 * @throws SyntaxErrorException If a syntax error was detected during the parsing.
	 */
	private void parseLine(String line, int lineNumber, Stack<ScopeStackEntry> scopesStack)
		throws SyntaxFeatureParseException, SyntaxErrorException {

		/* Setup */
		String trimmedLine = line.trim();
		if (trimmedLine.equals("")) {
			return; // We don't want to fill the AST with loads of empty line nodes...
		}

		/* Should we just finish the current block? */
		if (trimmedLine.equals(Token.RIGHT_CURLY_PREN.toString())) {
			/* Are we popping too much, a.k.a. going to pop out the main program scope? */
			if (scopesStack.size() == 1) {
				throw new SyntaxErrorException(UNEXPECTED_SCOPE_CLOSE_MESSAGE, line, lineNumber);
			}

			scopesStack.pop();
			return;
		}

		/* Parse */
		ScopeStackEntry currentScope = scopesStack.peek();
		SyntaxFeature feature = selectLineParser(line + "\n", currentScope.syntaxFeatures);
		ASTNode result = feature.parse(line);
		if (result == null) {
			return; // Nothing to do here. Can caused from anything that's "not important".
		}

		/* Do we initiate new code block? */
		if (result instanceof ScopeNode) {
			/* In this case, the SyntaxFeature MUST be a ScopeSyntaxFeature (a.k.a, single line expressions
			 * can't return ScopeNodes, as they were created by a single line, lol). */
			if (!(feature instanceof ScopeSyntaxFeature)) {
				throw new RuntimeException(SCOPE_NODE_WITHOUT_SCOPE_FEATURE_MESSAGE);
			}

			/* Create a new scope in the stack */
			SyntaxFeature[] scopeFeatures = ((ScopeSyntaxFeature)feature).getInnerFeatures();
			if (scopeFeatures == null) {
				/* If the scope features wasn't supplied, infer them from the outer scope */
				scopeFeatures = currentScope.syntaxFeatures;
			}
			scopesStack.push(new ScopeStackEntry((ScopeNode)result, scopeFeatures));
		}

		/* Add the entry to the parent scope */
		currentScope.scope.append(result);
	}

	/**
	 * Selects which {@link SyntaxFeature} should be used to parse the given source code line.
	 * @param line The source code line string.
	 * @param features An array of {@link SyntaxFeature} that're applicable for the parse operation.
	 * @return The {@link SyntaxFeature} instance that should parse the given source code line.
	 * @throws SyntaxFeatureParseException If no {@link SyntaxFeature} could handle this line.
	 */
	private SyntaxFeature selectLineParser(String line, SyntaxFeature[] features)
		throws SyntaxFeatureParseException {
		/* Attempt to find the right feature to parse this line with */
		for (SyntaxFeature feature : features) {
			if (feature.accepts(line)) {
				return feature;
			}
		}

		/* Nothing applicable here :( */
		throw new SyntaxFeatureParseException(UNKNOWN_FEATURE_MESSAGE);
	}

	// endregion
}
