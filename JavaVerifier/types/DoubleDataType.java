package oop.ex6.types;

import oop.ex6.Token;

/**
 * A class that describes a double data type.
 */
public class DoubleDataType implements PrimitiveDataType {
	// region Constants & Shared Variables

	/**
	 * The type identifier.
	 */
	private static final String TYPE_IDENTIFIER = Token.TYPE_DOUBLE.toString();

	/**
	 * The class shared instance.
	 */
	private static DoubleDataType instance;

	/**
	 * The type default value.
	 */
	private static final String DEFAULT_VALUE = "0";

	// endregion

	// region Initialization

	/**
	 * The class constructor. This is a singleton based class.
	 */
	private DoubleDataType() { }

	/**
	 * Gets the class shared instance.
	 * @return The class shared instance.
	 */
	static DoubleDataType getInstance() {
		if (instance == null) {
			instance = new DoubleDataType();
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
		return TypeParser.isDouble(value);
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
		return other.equals(this) || other.equals(TypeRegistry.factory(Token.TYPE_INT.toString()));
	}
}
