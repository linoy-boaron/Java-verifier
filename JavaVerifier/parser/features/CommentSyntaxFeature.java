package oop.ex6.parser.features;

import oop.ex6.Token;
import oop.ex6.ast.ASTNode;

import java.util.regex.Pattern;

/**
 * A syntax feature implementation that adds comments support to the s-Java language.
 */
class CommentSyntaxFeature implements SyntaxFeature {
	// region iVars & Shared Variables

	/**
	 * The syntax feature describing pattern.
	 */
	private static final Pattern FEATURE_PATTERN
			= Pattern.compile("^" + Pattern.quote(Token.COMMENT.toString()) + ".*$");

	/**
	 * The shared instance.
	 */
	private static CommentSyntaxFeature instance = null;

	/**
	 * The message that will be raised as an {@link SyntaxFeatureParseException} in case
	 * there's a parse error in the comment pattern.
	 */
	private static final String PATTERN_MISMATCH_MESSAGE = "Unexpected comment found. Did you " +
			"forgot s-Java can't have any content, including spaces, before the comment?";


	// endregion

	// region Initialization

	/**
	 * The class constructor. This is a Singleton based class, and thus it's being marked as private.
	 */
	private CommentSyntaxFeature() { }

	/**
	 * Get the class shared instance.
	 * @return The {@link CommentSyntaxFeature} shared instance.
	 */
	static CommentSyntaxFeature getInstance() {
		if (instance == null) {
			instance = new CommentSyntaxFeature();
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
		return SyntaxFeatureIdentifier.COMMENT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accepts(String line) {
		return line.trim().startsWith(Token.COMMENT.toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ASTNode parse(String line)
			throws SyntaxFeatureParseException {
		/* Make sure we match the exact pattern */
		if (!FEATURE_PATTERN.matcher(line).matches()) {
			throw new SyntaxFeatureParseException(PATTERN_MISMATCH_MESSAGE);
		}

		/* Comments doesn't give us any extra processing information, so we just "toss" them. */
		return null;
	}

	// endregion
}
