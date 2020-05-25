package oop.ex6.types;

import oop.ex6.Token;

/**
 * A class that describes an integer data type.
 */
public class IntDataType implements PrimitiveDataType {
	// region Constants & Shared Variables

	/**
	 * The type identifier.
	 */
	private static final String TYPE_IDENTIFIER = Token.TYPE_INT.toString();

	/**
	 * The class shared instance.
	 */
	private static IntDataType instance;

	/**
	 * The type default value.
	 */
	private static final String DEFAULT_VALUE = "0";

	// endregion

	// region Initialization

	/**
	 * The class constructor. This is a singleton based class.
	 */
	private IntDataType() { }

	/**
	 * Gets the class shared instance.
	 * @return The class shared instance.
	 */
	static IntDataType getInstance() {
		if (instance == null) {
			instance = new IntDataType();
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
		return TypeParser.isInteger(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDefaultValue() {
		return DEFAULT_VALUE;
	}
}
