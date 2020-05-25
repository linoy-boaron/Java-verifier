package oop.ex6.parser.features;

import oop.ex6.Token;
import oop.ex6.ast.ASTNode;
import oop.ex6.ast.ASTNodeFactory;
import oop.ex6.types.TypeParser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A syntax feature implementation that adds function invocation support to the s-Java language.
 */
class InvocationSyntaxFeature extends StatementSyntaxFeature {
	// region iVars & Shared Variables

	/**
	 * The test pattern.
	 */
	private static final Pattern ACCEPTS_PATTERN = Pattern.compile(TypeParser.formatPattern(
			"^\\s*{method_identifier}\\s*" + Pattern.quote(Token.LEFT_PREN.toString())));

	/**
	 * The actual parse pattern.
	 */
	private static final Pattern PARSE_PATTERN = Pattern.compile(TypeParser.formatPattern(
			"^\\s*({method_identifier})\\s*" + Pattern.quote(Token.LEFT_PREN.toString()) +
					"(.*)" + Pattern.quote(Token.RIGHT_PREN.toString()) + ";$"));

	/**
	 * The message that will be raised as an {@link SyntaxFeatureParseException} in case there's
	 * a parse error.
	 */
	private static final String PARSE_PATTERN_MISMATCH_MESSAGE = "Unexpected function call";

	/**
	 * The message that will be raised as an {@link SyntaxFeatureParseException} in case the supplied
	 * functiokn arguments list is invalid.
	 */
	private static final String INVALID_ARGS_LIST_MESSAGE = "Invalid function arguments list";

	// endregion

	// region iVars & Shared Variables

	/**
	 * The class shared instance.
	 */
	private static InvocationSyntaxFeature instance = null;

	// endregion

	// region Initialization

	/**
	 * The class constructor. This is a Singleton based class, and thus it's being marked as private.
	 */
	private InvocationSyntaxFeature() { }

	/**
	 * Get the class shared instance.
	 * @return The {@link InvocationSyntaxFeature} shared instance.
	 */
	static InvocationSyntaxFeature getInstance() {
		if (instance == null) {
			instance = new InvocationSyntaxFeature();
		}
		return instance;
	}

	// endregion

	// region Public API

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SyntaxFeatureIdentifier getIdentifier() {
		return SyntaxFeatureIdentifier.FUNCTION_INVOCATION;
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

		return m.start() == 0;
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

		/* Extract the data */
		String functionName = m.group(1);
		String argsString = m.group(3);

		List<String> args = ExpressionParser.parseCallerArguments(argsString);
		if (args == null) {
			throw new SyntaxFeatureParseException(INVALID_ARGS_LIST_MESSAGE);
		}

		/* Create the AST nodes */
		return ASTNodeFactory.createInvocationStatement(functionName, args);
	}

	// endregion
}
