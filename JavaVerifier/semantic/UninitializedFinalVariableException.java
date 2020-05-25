package oop.ex6.semantic;

/**
 * An exception that's being raised if a variable was declared as final but without actual value.
 */
public class UninitializedFinalVariableException extends SemanticErrorException {
	// region iVars

	/**
	 * The default error message.
	 */
	private static final String DEFAULT_MESSAGE = "The variable \"%s\" was marked as final while it didn't" +
			" got any initial value.";

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
	public UninitializedFinalVariableException(String variableName) {
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