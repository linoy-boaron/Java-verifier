package oop.ex6.parser.features;

import oop.ex6.ast.ASTNode;
import oop.ex6.parser.SyntaxErrorException;

/**
 * Describes a syntax feature in the s-Java language. A syntax feature is a feature in the language that can
 * be used by the programmer in her source code, and should be properly converted into
 * a matching {@link ASTNode}.
 *
 * Examples of supported syntax features: Variable declarations, comments, assignments, if/else etc.
 *
 * More examples that doesn't included in s-Java specification (but we already made our validator to support
 * them with minor modifications): Multiple data types (e.g. classes and enums), closures, do-while etc.
 */
public interface SyntaxFeature {
	/**
	 * Gets the {@link SyntaxFeature} identifier.
	 * @return The syntax feature identifier.
	 */
	SyntaxFeatureIdentifier getIdentifier();

	/**
	 * Determines whether or not this syntax feature can accept and parse the given line,
	 * in order to convert it into some kind of an {@link ASTNode}.
	 * If this method returns true, it means that this feature is responsible of parsing the given
	 * source line.
	 * @param line The source code line.
	 * @return True if this syntax feature can parse the given line, false otherwise.
	 */
	boolean accepts(String line);

	/**
	 * Parse the given source code line into an {@link ASTNode}.
	 * @param line The source code line.
	 * @return The corresponding {@link ASTNode} to this syntax feature.
	 * @throws SyntaxFeatureParseException If a syntax error was found on this line.
	 */
	ASTNode parse(String line) throws SyntaxFeatureParseException;
}
