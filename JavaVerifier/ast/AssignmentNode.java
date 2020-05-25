package oop.ex6.ast;

/**
 * This class implements an AST node that represents an assignment operation.
 */
public class AssignmentNode extends StatementNode {
	/**
	 * The variable name that's being assigned.
	 */
	private final String name;

	/**
	 * The variable value that's being assigned.
	 */
	private final String value;

	/**
	 * Creates a new assignment AST node.
	 * @param name The variable name.
	 * @param value The variable value.
	 */
	AssignmentNode(String name, String value) {
		super();

		this.name = name;
		this.value = value;
	}

	/**
	 * Gets the variable name.
	 * @return The variable name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the variable value.
	 * @return The variable value.
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
