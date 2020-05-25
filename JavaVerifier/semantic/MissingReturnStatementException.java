package oop.ex6.semantic;

/**
 * A general exception that would be thrown if a function misses a return statement at the end of it.
 */
public class MissingReturnStatementException extends SemanticErrorException {
	// region iVars & Constants

	/**
	 * The default error message.
	 */
	private static final String DEFAULT_MESSAGE = "The function \"%s\" missing a return statement " +
			"at the end of the function body.";

	/**
	 * The function name.
	 */
	private String functionName;

	// endregion

	// region Constructors

	/**
	 * Initializes a new exception that describes a missing return statement at the end of a function.
	 * @param functionName The function name.
	 */
	public MissingReturnStatementException(String functionName) {
		super(String.format(DEFAULT_MESSAGE, functionName));
		this.functionName = functionName;
	}

	// endregion

	// region iVars

	/**
	 * Gets the function name.
	 * @return The function name.
	 */
	public String getFunctionName() {
		return this.functionName;
	}

	// endregion
}
