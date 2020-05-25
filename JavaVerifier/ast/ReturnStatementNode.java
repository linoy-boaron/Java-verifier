package oop.ex6.ast;

/**
 * This class implements an AST node that represents a return statement.
 */
public class ReturnStatementNode extends StatementNode {
	/**
	 * Initializes a new AST node.
	 */
	ReturnStatementNode() { super(); }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
