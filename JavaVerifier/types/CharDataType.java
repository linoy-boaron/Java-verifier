package oop.ex6.types;

import oop.ex6.Token;

/**
 * A class that describes a boolean data type.
 */
public class CharDataType implements PrimitiveDataType {
	// region Constants & Shared Variables

	/**
	 * The type identifier.
	 */
	private static final String TYPE_IDENTIFIER = Token.TYPE_CHAR.toString();

	/**
	 * The type default value.
	 */
	private static final String DEFAULT_VALUE = "''";

	/**
	 * The class shared instance.
	 */
	private static CharDataType instance;

	// endregion

	// region Initialization

	/**
	 * The class constructor. This is a singleton based class.
	 */
	private CharDataType() { }

	/**
	 * Gets the class shared instance.
	 * @return The class shared instance.
	 */
	static CharDataType getInstance() {
		if (instance == null) {
			instance = new CharDataType();
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
		return TypeParser.isChar(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDefaultValue() {
		return DEFAULT_VALUE;
	}
}
