package oop.ex6.ast;

/**
 * This class implements an AST node that represents a single variable within a variable declaration.
 */
public class VariableNode implements ASTNode {
	// region iVars

	/**
	 * The data type that this value is being associated with.
	 */
	private final String type;

	/**
	 * The variable name.
	 */
	private final String name;

	/**
	 * The stored value.
	 */
	private final String value;

	/**
	 * Determines if this is a final variable.
	 */
	private final boolean isFinal;

	// endregion

	// region Constructor

	/**
	 * Initializes a new variable node.
	 * @param name The variable name.
	 * @param type The variable type.
	 * @param value The variable value.
	 */
	VariableNode(String type, String name, String value) {
		this(type, name, value, false);
	}

	/**
	 * Initializes a new variable node.
	 * @param type The variable type.
	 * @param name The variable name.
	 * @param value The variable value.
	 * @param isFinal Whether or not this variable marked as "final".
	 */
	VariableNode(String type, String name, String value, boolean isFinal) {
		this.type = type;
		this.name = name;
		this.value = value;
		this.isFinal = isFinal;
	}

	// endregion

	// region Getters

	/**
	 * Gets the variable type.
	 * @return The type of the variable.
	 */
	public String getType() {
		return this.type;
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
	 * @return The variable initial value.
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * Gets the value that determines whether or not this is a "final" variable.
	 * @return True if this is a final variable, false otherwise.
	 */
	public boolean isFinal() {
		return this.isFinal;
	}

	/**
	 * Determine if the variable has a value.
	 * @return True if the variable has a initial value, or false otherwise.
	 */
	public boolean hasValue() {
		return this.value != null;
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
