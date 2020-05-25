package oop.ex6.semantic;

/**
 * A general exception that would be thrown if a semantic error is detected.
 */
public class SemanticErrorException extends Exception {
	/**
	 * {@inheritDoc}
	 */
	public SemanticErrorException() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	public SemanticErrorException(String message) {
		super(message);
	}

	/**
	 * {@inheritDoc}
	 */
	public SemanticErrorException(String message, Throwable cause) {
		super(message, cause);
	}
}
