package oop.ex6.types;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.HashMap;
import java.util.Map;

/**
 * A registry-based class which keep track of the declared types in the language, initialises them and,
 * create them (using a factory method) and resolve the type of a given literal value / variable.
 */
public class TypeRegistry {
	// region Shared Variables & Constants

	/**
	 * The message that's being raised when an un-recognized type was requested.
	 */
	private static final String TYPE_NOT_FOUND_MESSAGE = "The requested type wasn't registered.";

	/**
	 * A list of the available data types.
	 */
	private static final HashMap<String, DataType> availableTypes;

	// endregion

	// region Initialization

	/**
	 * Static initializer.
	 */
	static {
		availableTypes = new HashMap<>();
		register(IntDataType.getInstance());
		register(DoubleDataType.getInstance());
		register(BooleanDataType.getInstance());
		register(CharDataType.getInstance());
		register(StringDataType.getInstance());
	}

	// endregion

	// region Public API

	/**
	 * Register a new data type.
	 * @param type The data type.
	 */
	static void register(DataType type) {
		if (availableTypes.containsKey(type.getIdentifier())) {
			throw new KeyAlreadyExistsException();
		}

		availableTypes.put(type.getIdentifier(), type);
	}

	/**
	 * Determine whether or not the requested data type was registered.
	 * @param identifier The data type identifier.
	 * @return True if the data type was registered, false otherwise.
	 */
	public static boolean isRegistered(String identifier) {
		return availableTypes.containsKey(identifier);
	}

	/**
	 * Gets the requested data type by its identifier.
	 * @param identifier The data type identifier.
	 * @return The requested data type.
	 * @throws RuntimeException If the requested data type wasn't registered.
	 */
	public static DataType factory(String identifier) {
		/* Do we have this data type registered? */
		if (!isRegistered(identifier)) {
			throw new RuntimeException(TYPE_NOT_FOUND_MESSAGE);
		}

		return availableTypes.get(identifier);
	}

	/**
	 * Resolves the {@link DataType} of the given value.
	 * @param value The value to resolve (e.g. "Hello world", false, 1.15 etc.).
	 * @return The resolved data type, or null if none of the data types registered in the language
	 * matches this value. Note that as this is method checks for constant values (or, in the future,
	 * expressions, such as "new ClassName()", sending a variable will produce null result).
  	 */
	public static DataType resolveFromValue(String value) {
		/* Iterate over the available data types and look for the matching one */
		for (DataType t : availableTypes.values()) {
			if (t.isValidValue(value)) {
				return t;
			}
		}

		return null;
	}
}
