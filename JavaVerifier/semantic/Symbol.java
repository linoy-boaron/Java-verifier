package oop.ex6.semantic;

/**
 * A class that represents an (abstract) symbol in the symbol table.
 */
public abstract class Symbol implements Cloneable {
	// region Nested Types

	/**
	 * An enum structure that describes the available symbol types.
	 */
	public enum Identifier {
		/**
		 * A symbol identifier that describes a variable.
		 */
		VARIABLE,

		/**
		 * A symbol identifier that describes a function.
		 */
		FUNCTION,

		/**
		 * A symbol identifier that describes a function argument.
		 */
		FUNCTION_ARGUMENT
	}

	// endregion

	// region iVars

	/**
	 * The symbol name.
	 */
	private final String name;

	/**
	 * The symbol identifier.
	 */
	private final Identifier identifier;

	// endregion

	// region Initialization

	/**
	 * Initializes a new symbol.
	 * @param name The symbol name.
	 * @param identifier The symbol identifier.
	 */
	public Symbol(String name, Identifier identifier) {
		this.name = name;
		this.identifier = identifier;
	}

	// endregion

	// region Getters

	/**
	 * Gets the symbol name.
	 * @return The symbol name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the symbol identifier.
	 * @return The symbol identifier.
	 */
	public Identifier getIdentifier() {
		return this.identifier;
	}

	@Override
	public boolean equals(Object other) {
		/* Note that as this is a symbol, we're only interested in its name and identifier
		 * (so we can compare apples to apples, and pears to pears lol). */
		if (!(other instanceof Symbol)) {
			return false;
		}

		Symbol s = (Symbol) other;
		return s.getIdentifier() == this.identifier && this.name.equals(s.getName());
	}

	// endregion

	// region Abstract API

	/**
	 * Clones the symbol.
	 * @return The cloned symbol.
	 */
	public abstract Symbol clone();

	// endregion
}
