package oop.ex6.parser.features;

import oop.ex6.PatternUtilities;
import oop.ex6.Token;
import oop.ex6.ast.ASTNode;
import oop.ex6.ast.ASTNodeFactory;

import java.util.regex.Pattern;

/**
 * A syntax feature implementation that adds return statements support to the s-Java language.
 */
class ReturnStatementSyntaxFeature extends StatementSyntaxFeature {
	// region iVars & Shared Variables

	/**
	 * The pattern to compare the source line with to determine
	 * if this is the right parser or not.
	 */
	private static Pattern ACCEPT_PATTERN = Pattern.compile("^\\s*" + Token.RETURN + "\\s*"
			+ Token.SEMICOLON.toString() + "\\s*$");

	/**
	 * The shared instance.
	 */
	private static ReturnStatementSyntaxFeature instance = null;

	// endregion

	// region Initialization

	/**
	 * The class constructor. This is a Singleton based class, and thus it's being marked as private.
	 */
	private ReturnStatementSyntaxFeature() { }

	/**
	 * Get the class shared instance.
	 * @return The {@link ReturnStatementSyntaxFeature} shared instance.
	 */
	static ReturnStatementSyntaxFeature getInstance() {
		if (instance == null) {
			instance = new ReturnStatementSyntaxFeature();
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
		return SyntaxFeatureIdentifier.RETURN_STATEMENT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accepts(String line) {
		/* Make sure it's a valid statement */
		if (!super.accepts(line)) {
			return false;
		}

		return ACCEPT_PATTERN.matcher(line).matches();
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public ASTNode parse(String line) {
		return ASTNodeFactory.createReturnStatement();
	}

	// endregion
}
