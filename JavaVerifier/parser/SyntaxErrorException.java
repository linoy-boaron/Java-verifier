package oop.ex6.parser;

/**
 * A class that describes a general syntax error.
 */
public class SyntaxErrorException extends Exception {
	// region Constants

	/**
	 * The standard (unknown) error message format.
	 */
	private static final String STANDARD_ERROR_MESSAGE = "Syntax error on line: %d.\nLine: \"%s\".";

	/**
	 * The standard (unknown) error message format.
	 */
	private static final String DETAILED_ERROR_MESSAGE = "%s error occurred on line: %d.\nLine: \"%s\".";

	// endregion

	// region iVars

	/**
	 * The error reason.
	 */
	private String errorReason;

	/**
	 * The line that caused the error.
	 */
	private String lineContent;

	/**
	 * The line number that caused the problem.
	 */
	private int lineNumber;

	// endregion

	// region Constructors

	/**
	 * Constructs a new exception with the given line and line number.
	 *
	 * @param line       The line that caused the exception.
	 * @param lineNumber The line number that caused the exception.
	 */
	public SyntaxErrorException(String line, int lineNumber) {
		super(String.format(STANDARD_ERROR_MESSAGE, lineNumber, line));
		this.lineContent = line;
		this.lineNumber = lineNumber;
		this.errorReason = "";
	}

	/**
	 * Constructs a new exception with the given message, line and line number.
	 *
	 * @param message    The syntax detail message.
	 * @param line       The line that caused the exception.
	 * @param lineNumber The line number that caused the exception.
	 */
	public SyntaxErrorException(String message, String line, int lineNumber) {
		super(String.format(DETAILED_ERROR_MESSAGE, message, lineNumber, line));
		this.lineContent = line;
		this.lineNumber = lineNumber;
		this.errorReason = message;
	}


	/**
	 * Constructs a new exception with the given line and line number.
	 *
	 * @param cause      The inner exception that caused this exception.
	 * @param line       The line that caused the exception.
	 * @param lineNumber The line number that caused the exception.
	 */
	public SyntaxErrorException(Throwable cause, String line, int lineNumber) {
		super(String.format(DETAILED_ERROR_MESSAGE, cause.getMessage(), lineNumber, line), cause);
		this.lineContent = line;
		this.lineNumber = lineNumber;
		this.errorReason = cause.getMessage();
	}

	// endregion

	// region Getters

	/**
	 * Gets the problematic line content.
	 *
	 * @return The line content.
	 */
	public String getLineContent() {
		return this.lineContent;
	}

	/**
	 * Gets the problematic line number.
	 *
	 * @return The line number.
	 */
	public int getLineNumber() {
		return this.lineNumber;
	}

	/**
	 * Gets the error reason.
	 * @return The error reason.
	 */
	public String getErrorReason() {
		return this.errorReason;
	}

	// endregion
}
