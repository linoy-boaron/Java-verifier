package oop.ex6.parser.features;

import oop.ex6.Token;
import oop.ex6.ast.ASTNode;
import oop.ex6.ast.ASTNodeFactory;
import oop.ex6.ast.FunctionArgumentNode;
import oop.ex6.types.TypeParser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A syntax feature implementation that adds function declaration to the s-Java language.
 */
class FunctionDeclarationSyntaxFeature extends ScopeSyntaxFeature {
	// region Constants

	/**
	 * The test pattern.
	 */
	private static final Pattern ACCEPTS_PATTERN = Pattern.compile(TypeParser.formatPattern(
			"^\\s*" + Token.TYPE_VOID.toString() + "\\s+{method_identifier}"));

	/**
	 * The actual parse pattern.
	 */
	private static final Pattern PARSE_PATTERN = Pattern.compile(TypeParser.formatPattern(
			"^\\s*" + Token.TYPE_VOID.toString() + "\\s+({method_identifier})\\s*\\" + Token.LEFT_PREN +
					"([^\\" + Token.RIGHT_PREN + "]*)\\" + Token.RIGHT_PREN + "\\s*\\" + Token.LEFT_CURLY_PREN + "\\s*$"));

	/**
	 * The message that will be raised as an {@link SyntaxFeatureParseException} in case there's
	 * a parse error.
	 */
	private static final String PARSE_PATTERN_MISMATCH_MESSAGE = "Unexpected function declaration";

	/**
	 * The message that will be raised as an {@link SyntaxFeatureParseException} in case the supplied
	 * functiokn arguments list is invalid.
	 */
	private static final String INVALID_ARGS_LIST_MESSAGE = "Invalid function arguments list";

	/**
	 * The syntax features that're being allowed in the global scope.
	 */
	private static final SyntaxFeatureIdentifier[] FUNCTION_INNER_FEATURES =  new SyntaxFeatureIdentifier[] {
					SyntaxFeatureIdentifier.COMMENT,
					SyntaxFeatureIdentifier.ASSIGNMENT,
					SyntaxFeatureIdentifier.VARIABLE_DECLARATION,
					SyntaxFeatureIdentifier.FUNCTION_INVOCATION,
					SyntaxFeatureIdentifier.IF_CONTROL_FLOW,
					SyntaxFeatureIdentifier.WHILE_CONTROL_FLOW,
					SyntaxFeatureIdentifier.RETURN_STATEMENT
			};

	// endregion

	// region iVars & Shared Variables

	/**
	 * The class shared instance.
	 */
	private static FunctionDeclarationSyntaxFeature instance = null;

	// endregion

	// region Initialization

	/**
	 * {@inheritDoc}
	 */
	private FunctionDeclarationSyntaxFeature() {
	}

	/**
	 * {@inheritDoc}
	 */
	static SyntaxFeature getInstance() {
		if (instance == null) {
			instance = new FunctionDeclarationSyntaxFeature();
		}
		return instance;
	}

	// endregion

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SyntaxFeatureIdentifier getIdentifier() {
		return SyntaxFeatureIdentifier.FUNCTION_DECLARATION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accepts(String line) {
		if (!super.accepts(line)) {
			return false;
		}

		/* Attempt to match the accepts pattern */
		Matcher m = ACCEPTS_PATTERN.matcher(line);
		if (!m.find()) {
			return false;
		}

		return m.start() == 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ASTNode parse(String line) throws SyntaxFeatureParseException {
		/* Attempt to extract the data */
		Matcher m = PARSE_PATTERN.matcher(line);
		if (!m.matches()) {
			throw new SyntaxFeatureParseException(PARSE_PATTERN_MISMATCH_MESSAGE);
		}

		String functionName = m.group(1);
		String functionArgs = m.group(3);

		/* Parse the arguments */
		List<ExpressionParser.ParsedFunctionArgument> args
				= ExpressionParser.parseCalleeArguments(functionArgs);
		if (args == null) {
			throw new SyntaxFeatureParseException(INVALID_ARGS_LIST_MESSAGE);
		}

		/* Create the argument AST nodes */
		List<FunctionArgumentNode> arguments = new ArrayList<>();
		for (ExpressionParser.ParsedFunctionArgument arg : args) {
			arguments.add(ASTNodeFactory.createFunctionArgument(
					arg.getType(), arg.getName(), arg.isFinal()));
		}

		/* Create the AST nodes */
		return ASTNodeFactory.createFunctionDeclaration(functionName, arguments);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SyntaxFeature[] getInnerFeatures() {
		return SyntaxFeatureFactory.factory(FUNCTION_INNER_FEATURES);
	}
}