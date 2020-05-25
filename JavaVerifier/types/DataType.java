package oop.ex6.types;


/**
 * An interface that represents a data type in the language.
 */
public interface DataType extends Comparable<DataType> {
	/**
	 * Gets the data type identifier (a.k.a. int, boolean, etc.).
	 * @return A string that represents the data type.
	 */
	String getIdentifier();

	/**
	 * Determines whether or not the given value is valid for this data type.
	 * @param value The string that describes to check.
	 * @return True if the value is valid for this data type, or false otherwise.
	 */
	boolean isValidValue(String value);

	/**
	 * Gets a string that describes the default value for this data type.
	 * @return The default data type.
	 */
	String getDefaultValue();

	/**
	 * Determine whether or not this type can be created from the the given type.
	 * @param other The other type.
	 * @return True if the value can be created from the a value of the given type, or false otherwise.
	 */
	default boolean canCreatedFrom(DataType other) {
		return this.equals(other);
	}

	/**
	 * {@inheritDoc}
	 */
	default int compareTo(DataType other) {
		return this.getIdentifier().compareTo(other.getIdentifier());
	}
}
