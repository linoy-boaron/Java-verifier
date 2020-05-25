package oop.ex6.parser.features;

import oop.ex6.Token;

/**
 * A syntax feature in the language that's being represented in a single statement.
 * A statement is a single line consisted of orders to the compiler.
 * For example: variable definition, assignments, function execution.
 */
public abstract class StatementSyntaxFeature implements SyntaxFeature {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accepts(String line) {
		line = line.trim();
		return line.lastIndexOf(Token.SEMICOLON.toString()) == line.length() - 1;
	}
}
