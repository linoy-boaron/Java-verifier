package oop.ex6.ast;

import java.util.List;

/**
 * This class implements an AST node that represents a function invocation.
 */
public class InvocationNode extends StatementNode {
	/**
	 * The function name that's being called.
	 */
	private final String name;

	/**
	 * The list of arguments this function accepts.
	 */
	private final List<String> arguments;

	/**
	 * Creates a new function invocation AST node.
	 * @param name The function name.
	 * @param args The function arguments list.
	 */
	InvocationNode(String name, List<String> args) {
		super();

		this.name = name;
		this.arguments = args;
	}

	/**
	 * Gets the function name.
	 * @return The function name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the function arguments list.
	 * @return The function arguments list.
	 */
	public List<String> getArguments() {
		return this.arguments;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
