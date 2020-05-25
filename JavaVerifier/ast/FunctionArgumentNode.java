package oop.ex6.ast;

/**
 * This class implements an AST node that represents a single function declaration (callee) argument.
 */
public class FunctionArgumentNode implements ASTNode {
	/**
	 * The argument name.
	 */
	private final String name;

	/**
	 * The argument data type.
	 */
	private final String type;

	/**
	 * The value that determine if it's a final argument or not.
	 */
	private final boolean isFinal;

	/**
	 * Creates a new function argument AST node.
	 * @param type The variable type.
	 * @param name The variable name.
	 * @param isFinal True if it's a final argument, false otherwise.
	 */
	FunctionArgumentNode(String type, String name, boolean isFinal) {
		this.name = name;
		this.type = type;
		this.isFinal = isFinal;
	}

	/**
	 * Gets the variable name.
	 *
	 * @return The variable name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the variable type.
	 *
	 * @return The variable type.
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Gets the value that determine if it's a final argument.
	 * @return True if it's a final argument, false otherwise.
	 */
	public boolean isFinal() {
		return this.isFinal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
