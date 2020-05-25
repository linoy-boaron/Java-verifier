package oop.ex6.semantic;

/**
 * An exception that's being raised if the user tries to access an un-initialized variable.
 */
public class AccessUninitializedVariableException extends SemanticErrorException {
	// region iVars & Constants

	/**
	 * The default error message.
	 */
	private static final String DEFAULT_MESSAGE = "The variable \"%s\" has been accessed before " +
			"it got initialized.";

	/**
	 * The variable name.
	 */
	private final String variableName;

	// endregion

	// region Constructors

	/**
	 * Initialize a new exception instance with the given type.
	 *
	 * @param variableName The variable name.
	 */
	public AccessUninitializedVariableException(String variableName) {
		super(String.format(DEFAULT_MESSAGE, variableName));
		this.variableName = variableName;
	}

	// endregion

	// region Public API

	/**
	 * Gets the variable name.
	 *
	 * @return The variable name.
	 */
	public String getVariableName() {
		return this.variableName;
	}

	// endregion
}