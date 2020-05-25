package oop.ex6.parser.features;

import oop.ex6.Token;
import oop.ex6.ast.ASTNode;
import oop.ex6.ast.ASTNodeFactory;

import java.util.List;

/**
 * A syntax feature implementation that adds "if statements" support to the s-Java language.
 */
class IfControlFlowSyntaxFeature extends BasicControlFlowSyntaxFeature {
	// region iVars & Shared Variables

	/**
	 * The class shared instance.
	 */
	private static IfControlFlowSyntaxFeature instance = null;

	// endregion

	// region Initialization

	/**
	 * {@inheritDoc}
	 */
	private IfControlFlowSyntaxFeature() {
		super(Token.IF);
	}

	/**
	 * {@inheritDoc}
	 */
	static SyntaxFeature getInstance() {
		if (instance == null) {
			instance = new IfControlFlowSyntaxFeature();
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
		return SyntaxFeatureIdentifier.IF_CONTROL_FLOW;
	}

	// endregion

	// region Protected Methods

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ASTNode createASTNode(List<String> conditions) {
		return ASTNodeFactory.createIfStatementFromStrings(conditions);
	}

	// endregion
}
