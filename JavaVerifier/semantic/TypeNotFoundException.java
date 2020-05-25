package oop.ex6.semantic;

/**
 * An exception that's being raised if a given type was not declared.
 */
public class TypeNotFoundException extends SemanticErrorException {
	// region iVars

	/**
	 * The default error message.
	 */
	private static final String DEFAULT_MESSAGE = "The type \"%s\" wasn't defined.";

	/**
	 * The type name.
	 */
	private final String type;

	// endregion

	// region Constructors

	/**
	 * Initialize a new exception instance with the given type.
	 * @param type The type name.
	 */
	public TypeNotFoundException(String type) {
		super(String.format(DEFAULT_MESSAGE, type));
		this.type = type;
	}

	/**
	 * Initialize a new exception instance with the given type and a description message.
	 * @param type The type name.
	 * @param message The exception details message.
	 */
	public TypeNotFoundException(String type, String message) {
		super(message);
		this.type = type;
	}

	/**
	 * Initialize a new exception instance with the given type, a description message and inner exception.
	 * @param type The type name.
	 * @param message The exception details message.
	 * @param cause The exception that caused this exception to be thrown.
	 */
	public TypeNotFoundException(String type, String message, Throwable cause) {
		super(message, cause);
		this.type = type;
	}

	// endregion

	// region Public API

	/**
	 * Gets the type name.
	 * @return The type name.
	 */
	public String getType() {
		return this.type;
	}

	// endregion
}
