package oop.ex6.semantic;

import java.util.*;

/**
 * A class representation of symbol table.
 * Generally speaking, a symbol table is a data structure used by a compiler or interpreter, where each
 * identifier (a.k.a. symbol) in a program's source code is associated with information relating to its
 * declaration or appearance in the source.
 */
public class SymbolsTable<T extends Symbol> extends AbstractMap<String, T> {
	// region iVars & Shared Variables

	/**
	 * The message that's being raised if one tries to pop the 1'st depth state of the table.
	 */
	private static final String INVALID_POP_STATE_MESSAGE = "The table can't pop the 1'st depth state.";

	/**
	 * The inner stack used to store the symbols.
	 */
	private final Deque<HashMap<String, T>> stack = new ArrayDeque<>();

	// endregion

	// region Initialization

	/**
	 * Initializes a new symbol table.
	 */
	public SymbolsTable() {
		this.stack.push(new HashMap<>());
	}

	// endregion

	/**
	 * Push the current table state and create a new one.
	 * This method should be used to enter into a new code scope.
	 */
	public void pushState() {
		HashMap<String, T> newTable = new HashMap<>(this.stack.peek().size());
		this.cloneAll(this.stack.peek(), newTable);
		this.stack.push(newTable);
	}

	/**
	 * Pop the current table state and gets to the previous state.
	 * This method should be used to get out of the current code scope.
	 * @throws IllegalStateException If the method is being called when there's only one symbol table.
	 */
	public void popState() {
		if (this.stack.size() == 1) {
			throw new IllegalStateException(INVALID_POP_STATE_MESSAGE);
		}

		this.stack.pop();
	}

	/**
	 * Gets the current symbols table depth.
	 * @return The depth of the symbols table.
	 */
	public int getDepth() {
		return this.stack.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Entry<String, T>> entrySet() {
		return this.stack.peek().entrySet();
	}

	/**
	 * Puts the given {@link Symbol} in the symbols table.
	 * @param value The symbol to put.
	 * @return The added symbol, or null if it already exists.
	 */
	public T put(T value) {
		return this.put(value.getName(), value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T put(String key, T value) {
		return this.stack.peek().put(key, value);
	}

	/**
	 * A hand-rolled version of {@link HashMap#putAll(Map)} which clones values instead of reference them.
	 * @param sourceTable The source table to copy from.
	 * @param destinationTable The destination table to copy into.
	 */
	@SuppressWarnings("unchecked")
	private void cloneAll(HashMap<String, T> sourceTable, HashMap<String, T> destinationTable) {
		for (Entry<String, T> entry : sourceTable.entrySet()) {
			destinationTable.put(entry.getKey(), (T)entry.getValue().clone());
		}
	}
}