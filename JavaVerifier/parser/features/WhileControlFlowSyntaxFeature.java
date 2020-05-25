package oop.ex6.parser.features;

import oop.ex6.Token;
import oop.ex6.ast.ASTNode;
import oop.ex6.ast.ASTNodeFactory;

import java.util.List;

/**
 * A syntax feature implementation that adds "while control flows" support to the s-Java language.
 */
class WhileControlFlowSyntaxFeature extends BasicControlFlowSyntaxFeature {
	// region iVars & Shared Variables

	/**
	 * The class shared instance.
	 */
	private static WhileControlFlowSyntaxFeature instance = null;

	// endregion

	// region Initialization

	/**
	 * {@inheritDoc}
	 */
	private WhileControlFlowSyntaxFeature() {
		super(Token.WHILE);
	}

	/**
	 * {@inheritDoc}
	 */
	static SyntaxFeature getInstance() {
		if (instance == null) {
			instance = new WhileControlFlowSyntaxFeature();
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
		return SyntaxFeatureIdentifier.WHILE_CONTROL_FLOW;
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
