package oop.ex6.semantic;

import oop.ex6.types.DataType;

/**
 * A symbol that represents a function argument.
 */
public class FunctionArgumentSymbol extends Symbol {
	// region iVars

	/**
	 * The variable data type.
	 */
	private final DataType type;

	// endregion

	// region Construction

	/**
	 * Initializes a new function argument symbol.
	 * @param name The argument name.
	 * @param type The argument data type.
	 */
	public FunctionArgumentSymbol(String name, DataType type) {
		super(name, Identifier.FUNCTION_ARGUMENT);
		this.type = type;
	}

	// endregion

	// region Getters

	/**
	 * Get the argument data type.
	 * @return The argument data type.
	 */
	public DataType getType() {
		return type;
	}

	// endregion

	// region Required API

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Symbol clone() {
		return new FunctionArgumentSymbol(this.getName(), this.type);
	}

	// endregion
}
