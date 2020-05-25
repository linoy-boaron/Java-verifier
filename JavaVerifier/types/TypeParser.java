package oop.ex6.types;

import oop.ex6.PatternUtilities;
import oop.ex6.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Provides parsing utilities for data types.
 */
public class TypeParser {
	// region Shared Variables

	/**
	 * A string that defines a a pattern that identifies identifiers (variable names, data types, etc.).
	 */
	private static final String IDENTIFIER_PATTERN_STRING
			= "([a-zA-Z]|_(?=[a-zA-Z0-9_]))[a-zA-Z0-9_]*";

	/**
	 * Defines the pattern that identifies a identifier name.
	 */
	private static final Pattern IDENTIFIER_PATTERN
			= Pattern.compile("^" + IDENTIFIER_PATTERN_STRING + "$");

	/**
	 * A string that defines a a pattern that identifies methods names.
	 */
	private static final String METHOD_IDENTIFIER_PATTERN_STRING
			= "([a-zA-Z])[a-zA-Z0-9_]*";

	/**
	 * A string that defines a a pattern that identifies single integer constant.
	 */
	private static final String INTEGER_PATTERN_STRING = "-?\\d+";

	/**
	 * Defines the pattern that identifies an integer.
	 */
	private static final Pattern INTEGER_PATTERN = Pattern.compile("^" + INTEGER_PATTERN_STRING + "$");

	/**
	 * A string that defines a a pattern that identifies single double constant.
	 */
	private static final String DOUBLE_PATTERN_STRING = "-?\\d+\\.\\d*|\\.\\d+|\\d+";

	/**
	 * Defines the pattern that identifies a double.
	 */
	private static final Pattern DOUBLE_PATTERN = Pattern.compile("^" + DOUBLE_PATTERN_STRING + "$");

	/**
	 * A string that defines a a pattern that identifies single boolean constant.
	 */
	private static final String BOOLEAN_PATTERN_STRING
			= "-?\\d+\\.\\d*|-?\\.\\d+|-?\\d+|"
			+ PatternUtilities.quote(Token.TRUE.toString(), Token.FALSE.toString());

	/**
	 * Defines the pattern that identifies a boolean.
	 */
	private static final Pattern BOOLEAN_PATTERN = Pattern.compile("^" + BOOLEAN_PATTERN_STRING + "$");

	/**
	 * A string that defines a a pattern that identifies single boolean character.
	 */
	private static final String CHAR_PATTERN_STRING
			= "'(?:\\\\.|\\\\[0-7]{1,3}|\\\\x[a-fA-F0-9]{1,2}|[^\\\\\\'\\n])'";

	/**
	 * Defines the pattern that identifies a (single) char.
	 */
	private static final Pattern CHAR_PATTERN
			= Pattern.compile("^" + CHAR_PATTERN_STRING + "$");

	/**
	 * A string that defines a a pattern that identifies single boolean character.
	 */
	private static final String STRING_PATTERN_STRING
			= "([\"])(?:(?=(\\\\?))\\2.)*?\\1";

	/**
	 * Defines the pattern that identifies a string.
	 */
	private static final Pattern STRING_PATTERN
			= Pattern.compile("^" + STRING_PATTERN_STRING);

	/**
	 * Defines the pattern of the placeholder used in the {@link TypeParser#formatPattern(String)} method.
	 */
	private static final String PLACEHOLDER_PATTERN = "{%s}";

	/**
	 * A list of available placeholders.
	 */
	private static final HashMap<String, String> placeholdersMap;

	// endregion

	// region Initialization

	/**
	 * Static initializer.
	 */
	static {
		/* Register the available placeholders */
		placeholdersMap = new HashMap<>();
		placeholdersMap.put(String.format(PLACEHOLDER_PATTERN, Token.IDENTIFIER.toString().toLowerCase()),
				IDENTIFIER_PATTERN_STRING);
		placeholdersMap.put(String.format(PLACEHOLDER_PATTERN,
				Token.METHOD_IDENTIFIER.toString().toLowerCase()), METHOD_IDENTIFIER_PATTERN_STRING);
		placeholdersMap.put(String.format(PLACEHOLDER_PATTERN, Token.TYPE_INT.toString().toLowerCase()),
				INTEGER_PATTERN_STRING);
		placeholdersMap.put(String.format(PLACEHOLDER_PATTERN, Token.TYPE_DOUBLE.toString().toLowerCase()),
				DOUBLE_PATTERN_STRING);
		placeholdersMap.put(String.format(PLACEHOLDER_PATTERN, Token.TYPE_BOOLEAN.toString().toLowerCase()),
				BOOLEAN_PATTERN_STRING);
		placeholdersMap.put(String.format(PLACEHOLDER_PATTERN, Token.TYPE_CHAR.toString().toLowerCase()),
				CHAR_PATTERN_STRING);
		placeholdersMap.put(String.format(PLACEHOLDER_PATTERN, Token.TYPE_STRING.toString().toLowerCase()),
				STRING_PATTERN_STRING);
	}

	/**
	 * Constructor. This is a static-only utilities class.
	 */
	private TypeParser() { }

	// endregion

	// region Public API

	/**
	 * Determine whether or not the given string is a valid identifier. Identifier is a token symbolizing
	 * a variable or a data type (a.k.a variables: "a", "foobar_55" etc.; types: "int", "String" etc.).
	 * @param text The text.
	 * @return True if the string is valid identifier, false otherwise.
	 */
	public static boolean isIdentifier(String text) {
		return applyPattern(text, IDENTIFIER_PATTERN);
	}

	/**
	 * Determine whether or not the given string is a valid integer.
	 * @param text The text.
	 * @return True if the given string is a valid integer, false otherwise.
	 */
	public static boolean isInteger(String text) {
		return applyPattern(text, INTEGER_PATTERN);
	}

	/**
	 * Determine whether or not the given string is a valid double.
	 * @param text The text.
	 * @return True if the string is valid double, false otherwise.
	 */
	public static boolean isDouble(String text) {
		return applyPattern(text, DOUBLE_PATTERN);
	}

	/**
	 * Determine whether or not the given string is a valid boolean.
	 * @param text The text.
	 * @return True if the string is valid boolean, false otherwise.
	 */
	public static boolean isBoolean(String text) {
		return applyPattern(text, BOOLEAN_PATTERN);
	}

	/**
	 * Determine whether or not the given string is a valid boolean.
	 * @param text The text.
	 * @return True if the string is valid boolean, false otherwise.
	 */
	public static boolean isChar(String text) {
		return applyPattern(text, CHAR_PATTERN);
	}

	/**
	 * Determine whether or not the given string is a valid boolean.
	 * @param text The text.
	 * @return True if the string is valid boolean, false otherwise.
	 */
	public static boolean isString(String text) {
		return applyPattern(text, STRING_PATTERN);
	}

	/**
	 * Assembles a pattern consisted from the given data type placeholders.
	 * @param pattern The pattern with the token placeholders.
	 * @return The assembled pattern
	 */
	public static String formatPattern(String pattern) {
		if (pattern == null) {
			throw new NullPointerException();
		}

		for (Map.Entry<String, String> entry : placeholdersMap.entrySet()) {
			pattern = pattern.replace(entry.getKey(), entry.getValue());
		}

		return pattern;
	}

	// endregion

	// region Private API

	/**
	 * Apply the given pattern on the string and check for match.
	 * @param text The text to apply the pattern on.
	 * @param pattern The pattern to apply.
	 * @return True if there's a match, false otherwise.
	 */
	private static boolean applyPattern(String text, Pattern pattern) {
		if (text == null) {
			throw new NullPointerException();
		}

		return pattern.matcher(text.trim()).matches();
	}

	// endregion
}
