package oop.ex6.parser.features;

import oop.ex6.PatternUtilities;
import oop.ex6.Token;
import oop.ex6.ast.ASTNode;
import oop.ex6.ast.ASTNodeFactory;
import oop.ex6.types.TypeParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A syntax feature implementation that adds values assignment (w/o declarations!) support
 * to the s-Java language.
 */
class AssignmentSyntaxFeature extends StatementSyntaxFeature {
	// region iVars & Shared Variables

	private static final Pattern ACCEPT_PATTERN = Pattern.compile(
			TypeParser.formatPattern("^\\s*(?:{identifier})\\s*" + Token.OP_ASSIGNMENT));

	/**
	 * The class shared instance.
	 */
	private static AssignmentSyntaxFeature instance = null;

	// endregion

	// region Initialization

	/**
	 * {@inheritDoc}
	 */
	private AssignmentSyntaxFeature() { }

	/**
	 * {@inheritDoc}
	 */
	static SyntaxFeature getInstance() {
		if (instance == null) {
			instance = new AssignmentSyntaxFeature();
		}
		return instance;
	}

	// endregion

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SyntaxFeatureIdentifier getIdentifier() {
		return SyntaxFeatureIdentifier.ASSIGNMENT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accepts(String line) {
		if (!super.accepts(line)) {
			return false;
		}

		/* Attempt to match the accept pattern */
		Matcher matcher = ACCEPT_PATTERN.matcher(line);
		if (!matcher.find()) {
			return false;
		}

		/* Make sure that's the first characters string (Though, as we used "^", it should be...) */
		return matcher.start() == 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ASTNode parse(String line) throws SyntaxFeatureParseException {
		/* Simply parse and return */
		ExpressionParser.AssignmentParseResult result = ExpressionParser.parseAssignment(line);
		return ASTNodeFactory.createAssignment(result.getName(), result.getValue());
	}
}
