package oop.ex6.ast;

/**
 * This class implements an AST node that represents the entire program. Thus, this node is being
 * treated as the AST root.
 */
public class ProgramASTNode extends ScopeNode {
	// region Initializer

	/**
	 * Initializes a new empty program.
	 */
	ProgramASTNode() {
		super();
	}

	// endregion

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
