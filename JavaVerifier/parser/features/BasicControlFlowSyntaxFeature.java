package oop.ex6.parser.features;

import oop.ex6.PatternUtilities;
import oop.ex6.Token;
import oop.ex6.ast.ASTNode;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A syntax feature implementation that adds standard control flows to the s-Java language.
 */
abstract class BasicControlFlowSyntaxFeature extends ScopeSyntaxFeature {
	// region Constants

	/**
	 * Defines the format of the accepts() method string. This string is being used to validate
	 * whether or not this control flow is suitable to handle the parse request.
	 */
	private static final String ACCEPTS_STRING_FORMAT = "%s" + Token.LEFT_PREN.toString();

	/**
	 * The feature extraction pattern string.
	 */
	private static final String EXTRACTION_PATTERN_FORMAT = "^\\s*%s\\s*(\\" + Token.LEFT_PREN + "(?:\\"
			+ Token.LEFT_PREN + ".*\\" + Token.RIGHT_PREN + "|[^" + Token.LEFT_PREN + "])*\\"
			+ Token.RIGHT_PREN + ")" + "\\s*\\" + Token.LEFT_CURLY_PREN + "\\s*$";

	/**
	 * The message that's being raised when the parse process failed.
	 */
	private static final String PARSE_FAILURE_MESSAGE = "Invalid %s statement";

	/**
	 * The message that's being raised when the parse process failed.
	 */
	private static final String CONDITIONS_PARSE_FAILURE_MESSAGE = "Invalid condition expression";

	// endregion

	// region Private Vars

	/**
	 * The token that represents this control flow.
	 */
	private final Token controlFlowToken;

	/**
	 * The cached extraction pattern.
	 */
	private final Pattern cachedExtractionPattern;

	// endregion

	// region Initialization

	protected BasicControlFlowSyntaxFeature(Token token) {
		this.controlFlowToken = token;
		this.cachedExtractionPattern = Pattern.compile(String.format(
				EXTRACTION_PATTERN_FORMAT, this.controlFlowToken.toString()));
	}

	// endregion

	// region Public API

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SyntaxFeature[] getInnerFeatures() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accepts(String line) {
		/* Super */
		if (!super.accepts(line)) {
			return false;
		}

		/* Unify the string spaces and make sure it start with the control flow token, followed
		 * by a left parenthesis (e.g. "if(" or "while(")  */
		String acceptsString = String.format(ACCEPTS_STRING_FORMAT, this.controlFlowToken.toString());
		line = PatternUtilities.unifyStringSpaces(line, "");
		if (line.length() <= acceptsString.length()) {
			return false;
		}

		/* Do we start with the right token? */
		return line.substring(0, acceptsString.length()).equals(acceptsString);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ASTNode parse(String line) throws SyntaxFeatureParseException {
		/* Attempts to parse the string */
		Matcher m = this.cachedExtractionPattern.matcher(line);
		if (!m.matches()) {
			throw new SyntaxFeatureParseException(String.format(
					PARSE_FAILURE_MESSAGE, this.controlFlowToken.toString()));
		}

		/* Parse the conditions */
		String condition = m.group(1);
		List<String> expressions = ExpressionParser.parseCondition(condition);
		if (expressions == null) {
			throw new SyntaxFeatureParseException(CONDITIONS_PARSE_FAILURE_MESSAGE);
		}

		return createASTNode(expressions);
	}

	// endregion

	// region Abstract API

	/**
	 * Creates the actual {@link ASTNode} for this control flow, taking into account the given conditions.
	 * @param conditions The conditions
	 * @return The instantiated {@link ASTNode}.
	 */
	protected abstract ASTNode createASTNode(List<String> conditions);

	// endregion

}
