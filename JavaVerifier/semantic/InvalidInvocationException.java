package oop.ex6.semantic;

/**
 * A semantic exception hat describes an invalid invocation attempt.
 */
public class InvalidInvocationException extends SemanticErrorException {
	/**
	 * {@inheritDoc}
	 */
	public InvalidInvocationException() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	public InvalidInvocationException(String message) {
		super(message);
	}

	/**
	 * {@inheritDoc}
	 */
	public InvalidInvocationException(String message, Throwable cause) {
		super(message, cause);
	}
}
