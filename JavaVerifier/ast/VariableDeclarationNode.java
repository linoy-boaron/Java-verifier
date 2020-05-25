package oop.ex6.ast;

import java.util.List;

/**
 * This class implements an AST node that represents a variable declaration.
 */
public class VariableDeclarationNode extends StatementNode {
	// region iVars

	/**
	 * The list of variables that was created.
	 */
	private final List<VariableNode> variables;

	// endregion

	// region Ctor

	/**
	 * Initialize a new declaration AST node.
	 * @param variables The list of variables to declare on.
	 */
	VariableDeclarationNode(List<VariableNode> variables) {
		super();
		this.variables = variables;
	}

	// endregion

	// region Public API

	/**
	 * Gets the list of declared variables.
	 * @return The list of declared variables.
	 */
	public List<VariableNode> getVariables() {
		return this.variables;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	// endregion
}
