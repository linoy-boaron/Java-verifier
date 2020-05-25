package oop.ex6.types;

/**
 * A class that describes a boolean data type.
 */
public class StringDataType implements DataType {
	// region Constants & Shared Variables

	/**
	 * The type identifier.
	 */
	private static final String TYPE_IDENTIFIER = "String";

	/**
	 * The class shared instance.
	 */
	private static StringDataType instance;

	/**
	 * The type default value.
	 */
	private static final String DEFAULT_VALUE = "\"\"";

	// endregion

	// region Initialization

	/**
	 * The class constructor. This is a singleton based class.
	 */
	private StringDataType() { }

	/**
	 * Gets the class shared instance.
	 * @return The class shared instance.
	 */
	static StringDataType getInstance() {
		if (instance == null) {
			instance = new StringDataType();
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
		return TypeParser.isString(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDefaultValue() {
		return DEFAULT_VALUE;
	}
}
