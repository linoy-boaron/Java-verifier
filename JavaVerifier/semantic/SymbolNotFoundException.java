package oop.ex6.semantic;

/**
 * An exception that's being raised if a given symbol was not found found in a symbols table.
 */
public class SymbolNotFoundException extends SemanticErrorException {
	// region iVars & Constants

	/**
	 * The default error message.
	 */
	private static final String DEFAULT_MESSAGE = "The symbol \"%s\" couldn't be found.";

	/**
	 * The symbol name.
	 */
	private final String symbol;

	// endregion

	// region Constructors

	/**
	 * Initialize a new exception instance with the given symbol.
	 * @param symbol The symbol name.
	 */
	public SymbolNotFoundException(String symbol) {
		super(String.format(DEFAULT_MESSAGE, symbol));
		this.symbol = symbol;
	}

	/**
	 * Initialize a new exception instance with the given symbol and a description message.
	 * @param symbol The symbol name.
	 * @param message The exception details message.
	 */
	public SymbolNotFoundException(String symbol, String message) {
		super(message);
		this.symbol = symbol;
	}

	/**
	 * Initialize a new exception instance with the given symbol, a description message and inner exception.
	 * @param symbol The symbol name.
	 * @param message The exception details message.
	 * @param cause The exception that caused this exception to be thrown.
	 */
	public SymbolNotFoundException(String symbol, String message, Throwable cause) {
		super(message, cause);
		this.symbol = symbol;
	}

	// endregion

	// region Public API

	/**
	 * Gets the symbol name.
	 * @return The symbol name.
	 */
	public String getSymbol() {
		return this.symbol;
	}

	// endregion
}
