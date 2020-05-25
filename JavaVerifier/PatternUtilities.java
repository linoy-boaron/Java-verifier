package oop.ex6;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A utilities class for Regular Expressions.
 */
public class PatternUtilities {
	// region Constants

	/**
	 * A pattern that describes a string whitespace unification.
	 */
	private static final Pattern SPACE_UNIFY_PATTERN = Pattern.compile("[\\s]+");

	/**
	 * A pattern that describes a semicolon trim.
	 */
	private static final Pattern TRIM_SEMICOLON = Pattern.compile(Token.SEMICOLON + "\\s*$");

	// endregion

	// region Initialization

	/**
	 * The class constructor. This is a static-only class.
	 */
	private PatternUtilities() {
	}

	// endregion

	// region Public API

	/**
	 * Removes multiple spaces in the string and unifies tabs, etc by turning them into spaces.
	 * @param s The string to unify.
	 * @return The unified string.
	 */
	public static String unifyStringSpaces(String s) {
		return unifyStringSpaces(s, " ");
	}

	/**
	 * Removes multiple spaces in the string and unifies tabs, etc by turning them into the given value.
	 * @param s The string to unify.
	 * @param replacement The replacement string.
	 * @return The unified string.
	 */
	public static String unifyStringSpaces(String s, String replacement) {
		return SPACE_UNIFY_PATTERN.matcher(s).replaceAll(replacement);
	}

	/**
	 * Quote the given array list using {@link Pattern#quote(String)} (with "or" separation).
	 * @param arr The array of strings.
	 * @return The quoted array.
	 */
	public static String quote(String... arr) {
		if (arr == null) {
			throw new NullPointerException();
		}

		List<String> parts = new ArrayList<>();
		for (String part : arr) {
			parts.add(Pattern.quote(part));
		}
		return String.join("|", parts);
	}

	/**
	 * Strips the statement terminator (a.k.a., semicolon) from the string.
	 * @param text The text to strip the terminator from.
	 * @return The stripped text.
	 */
	public static String trimStatementTerminator(String text) {
		return TRIM_SEMICOLON.matcher(text).replaceAll("");
	}

	// endregion
}
