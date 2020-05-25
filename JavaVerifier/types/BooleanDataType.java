package oop.ex6.types;

import oop.ex6.Token;

/**
 * A class that describes a boolean data type.
 */
public class BooleanDataType implements PrimitiveDataType {
	// region Constants & Shared Variables

	/**
	 * The type identifier.
	 */
	private static final String TYPE_IDENTIFIER = Token.TYPE_BOOLEAN.toString();

	/**
	 * The class shared instance.
	 */
	private static BooleanDataType instance;

	/**
	 * The type default value.
	 */
	private static final String DEFAULT_VALUE = "false";


	// endregion

	// region Initialization

	/**
	 * The class constructor. This is a singleton based class.
	 */
	private BooleanDataType() { }

	/**
	 * Gets the class shared instance.
	 * @return The class shared instance.
	 */
	static BooleanDataType getInstance() {
		if (instance == null) {
			instance = new BooleanDataType();
		}

		return instance;
	}

	// endregion

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getIdentifier() {
		return TYPE_IDENTIFIER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValidValue(String value) {
		return TypeParser.isBoolean(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDefaultValue() {
		return DEFAULT_VALUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canCreatedFrom(DataType other) {
		return other.equals(this)
				|| other.equals(TypeRegistry.factory(Token.TYPE_INT.toString()))
				|| other.equals(TypeRegistry.factory(Token.TYPE_DOUBLE.toString()));
	}
}
