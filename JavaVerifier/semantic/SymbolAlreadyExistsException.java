package oop.ex6.semantic;

/**
 * An exception that's being raised if a given symbol was already found in a symbols table.
 */
public class SymbolAlreadyExistsException extends SemanticErrorException {
	// region iVars & Constants

	/**
	 * The default error message.
	 */
	private static final String DEFAULT_MESSAGE = "The symbol \"%s\" of type %s was already " +
			"defined earlier.";

	/**
	 * The duplicate symbol.
	 */
	private final Symbol symbol;

	// endregion

	// region Constructors

	/**
	 * Initialize a new exception instance with the given symbol.
	 * @param symbol The duplicate symbol.
	 */
	public SymbolAlreadyExistsException(Symbol symbol) {
		super(String.format(DEFAULT_MESSAGE, symbol.getName(), symbol.getIdentifier()));
		this.symbol = symbol;
	}

	/**
	 * Initialize a new exception instance with the given symbol and a description message.
	 * @param symbol The duplicate symbol.
	 * @param message The exception details message.
	 */
	public SymbolAlreadyExistsException(Symbol symbol, String message) {
		super(message);
		this.symbol = symbol;
	}

	/**
	 * Initialize a new exception instance with the given symbol, a description message and inner exception.
	 * @param symbol The duplicate symbol.
	 * @param message The exception details message.
	 * @param cause The exception that caused this exception to be thrown.
	 */
	public SymbolAlreadyExistsException(Symbol symbol, String message, Throwable cause) {
		super(message, cause);
		this.symbol = symbol;
	}

	// endregion

	// region Public API

	/**
	 * Gets the duplicate symbol.
	 * @return The symbol.
	 */
	public Symbol getSymbol() {
		return this.symbol;
	}

	// endregion
}
