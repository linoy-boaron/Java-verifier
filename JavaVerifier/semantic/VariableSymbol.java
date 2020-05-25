package oop.ex6.semantic;

import oop.ex6.types.DataType;

/**
 * A class that represents a variable symbol.
 */
public class VariableSymbol extends Symbol {
	// region iVars

	/**
	 * The variable data type.
	 */
	private final DataType type;

	/**
	 * The string literal that define what this variable contains.
	 */
	private String value;

	/**
	 * A value that determines if this variable is final or not.
	 */
	private final boolean isFinal;

	/**
	 * A value that determines if this is a global variable or not.
	 */
	private final boolean isGlobal;

	// endregion

	// region Initialization

	/**
	 * Initializes a new variable symbol.
	 * @param name The variable name.
	 * @param type The variable type.
	 */
	public VariableSymbol(String name, DataType type) {
		this(name, type, null, false, false);
	}

	/**
	 * Initializes a new variable symbol.
	 * @param name The variable name.
	 * @param type The variable type.
	 * @param value The variable initial value.
	 */
	public VariableSymbol(String name, DataType type, String value) {
		this(name, type, value, false, false);
	}

	/**
	 * Initializes a new variable symbol.
	 * @param name The variable name.
	 * @param type The variable type.
	 * @param value The variable initial value.
	 * @param isFinal True if this is a final variable, false otherwise.
	 */
	public VariableSymbol(String name, DataType type, String value, boolean isFinal) {
		this(name, type, value, isFinal, false);
	}

	/**
	 * Initializes a new variable symbol.
	 * @param name The variable name.
	 * @param type The variable type.
	 * @param value The variable initial value.
	 * @param isFinal True if this is a final variable, false otherwise.
	 * @param isGlobal True if this is a global variable, false otherwise.
	 */
	public VariableSymbol(String name, DataType type, String value,
	                      boolean isFinal, boolean isGlobal) {
		super(name, Identifier.VARIABLE);
		this.type = type;
		this.value = value;
		this.isFinal = isFinal;
		this.isGlobal = isGlobal;
	}

	// endregion

	// region Getters

	/**
	 * Gets the variable type.
	 * @return The variable type.
	 */
	public DataType getType() {
		return this.type;
	}

	/**
	 * Gets the variable value.
	 * @return The variable value.
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * Sets the variable value.
	 * @param value The variable value.
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets the value that determine if this is a final variable.
	 * @return True if this is a final variable, false otherwise.
	 */
	public boolean isFinal() {
		return this.isFinal;
	}

	/**
	 * Gets the value that determine if this is a global variable.
	 * @return True if this is a global variable, false otherwise.
	 */
	public boolean isGlobal() {
		return this.isGlobal;
	}

	/**
	 * Gets the value that determine if this variable has a value.
	 * @return True if this variable has a value, false otherwise.
	 */
	public boolean hasValue() {
		return this.value != null;
	}

	// endregion

	// region Required API

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Symbol clone() {
		return new VariableSymbol(this.getName(), this.type, this.value, this.isFinal, this.isGlobal);
	}

	// endregion
}
