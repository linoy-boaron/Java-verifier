package oop.ex6.parser.features;

import oop.ex6.Token;
import oop.ex6.ast.ASTNode;
import oop.ex6.ast.ASTNodeFactory;
import oop.ex6.ast.VariableNode;
import oop.ex6.types.TypeRegistry;
import oop.ex6.types.TypeParser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A syntax feature implementation that adds variable declarations (including initializing them) support
 * to the s-Java language.
 */
class VariableDeclarationSyntaxFeature extends StatementSyntaxFeature {
	// region Constants

	/**
	 * The test pattern.
	 */
	private static final Pattern ACCEPTS_PATTERN = Pattern.compile(TypeParser.formatPattern(
			"^\\s*(" + Token.FINAL.toString() + "\\s*)?({identifier}).+;\\s*$"));

	/**
	 * The actual parse pattern.
	 */
	private static final Pattern PARSE_PATTERN = Pattern.compile(TypeParser.formatPattern(
			"^\\s*(" + Pattern.quote(Token.FINAL.toString()) + "\\s*)?({identifier})\\s*(.+);\\s*$"));

	/**
	 * The message that will be raised as an {@link SyntaxFeatureParseException} in case there's a
	 * parse error.
	 */
	private static final String PARSE_PATTERN_MISMATCH_MESSAGE = "Unexpected variable declaration";

	// endregion

	// region iVars & Shared Variables

	/**
	 * The class shared instance.
	 */
	private static VariableDeclarationSyntaxFeature instance = null;

	// endregion

	// region Initialization

	/**
	 * {@inheritDoc}
	 */
	private VariableDeclarationSyntaxFeature() { }

	/**
	 * {@inheritDoc}
	 */
	static SyntaxFeature getInstance() {
		if (instance == null) {
			instance = new VariableDeclarationSyntaxFeature();
		}
		return instance;
	}

	// endregion

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SyntaxFeatureIdentifier getIdentifier() {
		return SyntaxFeatureIdentifier.VARIABLE_DECLARATION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accepts(String line) {
		if (!super.accepts(line)) {
			return false;
		}

		/* Attempt to locate a {final}? {type} pattern */
		Matcher m = ACCEPTS_PATTERN.matcher(line);
		if (!m.find()) {
			return false;
		}

		int startIndex = m.start();
		if (startIndex != 0) {
			return false;
		}

		/* Make sure that the we got an actual data type */
		if (m.group(2) == null) {
			return false;
		}

		return TypeRegistry.isRegistered(m.group(2));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ASTNode parse(String line) throws SyntaxFeatureParseException {
		/* Parse the expression */
		Matcher m = PARSE_PATTERN.matcher(line);
		if (!m.matches()) {
			throw new SyntaxFeatureParseException(PARSE_PATTERN_MISMATCH_MESSAGE);
		}

		/* Map our matches */
		boolean isFinal = m.group(1) != null;
		String dataType = m.group(2);
		String variablesList = m.group(4);

		/* Make sure we don't have comment at the beginning or at the end of the expression.
		As we gonna use "split", we will lose this information if we won't do it now. */
		Character firstChar = variablesList.charAt(0);
		Character lastChar = variablesList.charAt(variablesList.length() - 1);

		if (firstChar.toString().equals(Token.COMMA.toString())
				|| lastChar.toString().equals(Token.COMMA.toString())) {
			throw new SyntaxFeatureParseException(PARSE_PATTERN_MISMATCH_MESSAGE);
		}

		return ASTNodeFactory.createVariableDeclaration(createVariablesList(
				dataType, variablesList, isFinal));
	}

	/**
	 * Creates a {@link VariableNode} for each of the given variables.
	 * @param dataType The variable type.
 	 * @param variablesList A string contains the variables list.
	 * @param isFinal True if the variable(s) is/are final, false otherwise.
	 * @return The list of declared variable {@link ASTNode}.
	 * @throws SyntaxFeatureParseException If there's a parse error.
	 */
	private static List<VariableNode> createVariablesList(String dataType, String variablesList,
	                                                      boolean isFinal)
		throws SyntaxFeatureParseException {
		/* Create the variables declaration AST */
		List<VariableNode> nodes = new ArrayList<>();
		for (String declaration : variablesList.split(Token.COMMA.toString())) {
			/* If this variable contains the assignment operator, parse it as an
			expression. Otherwise, create an un-initialized variable. */
			if (declaration.contains(Token.OP_ASSIGNMENT.toString())) {
				/* Attempt to parse it */
				ExpressionParser.AssignmentParseResult result
						= ExpressionParser.parseAssignment(declaration);

				nodes.add(ASTNodeFactory.createVariable(dataType, result.getName().trim(),
						result.getValue().trim(), isFinal));
			} else {
				/* Make sure that the variable name is valid */
				if (!TypeParser.isIdentifier(declaration.trim())) {
					throw new SyntaxFeatureParseException(PARSE_PATTERN_MISMATCH_MESSAGE);
				}
				nodes.add(ASTNodeFactory.createVariable(dataType, declaration.trim(),
						null, isFinal));
			}
		}

		return nodes;
	}
}
