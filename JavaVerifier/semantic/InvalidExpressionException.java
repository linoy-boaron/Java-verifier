package oop.ex6.semantic;

/**
 * A general exception that would be thrown if an invalid expression has been attempted to get evaluated.
 */
public class InvalidExpressionException extends SemanticErrorException {
	/**
	 * {@inheritDoc}
	 */
	public InvalidExpressionException(String message) {
		super(message);
	}

	/**
	 * {@inheritDoc}
	 */
	public InvalidExpressionException(String message, Throwable cause) {
		super(message, cause);
	}
}
