package oop.ex6.semantic;

import java.util.ArrayList;
import java.util.List;

/**
 * A symbol that represents a function.
 */
public class FunctionSymbol extends Symbol {
	// region iVars

	private final List<FunctionArgumentSymbol> arguments;

	// endregion

	// region Construction

	/**
	 * Initializes a new function declaration symbol.
	 * @param name The function name.
	 * @param arguments The function arguments.
	 */
	public FunctionSymbol(String name, List<FunctionArgumentSymbol> arguments) {
		super(name, Identifier.FUNCTION);
		this.arguments = arguments;
	}

	// endregion

	// region Getters

	/**
	 * Gets the function arguments.
	 * @return The list of function arguments.
	 */
	public List<FunctionArgumentSymbol> getArguments() {
		return this.arguments;
	}

	// endregion
	
	// region Required API

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Symbol clone() {
		/* Clone the arguments */
		List<FunctionArgumentSymbol> arguments = new ArrayList<>();
		for (FunctionArgumentSymbol arg : this.arguments) {
			arguments.add((FunctionArgumentSymbol)arg.clone());
		}

		/* Copy */
		return new FunctionSymbol(this.getName(), arguments);
	}

	// endregion
}
