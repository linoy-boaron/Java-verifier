package oop.ex6.parser.features;

import oop.ex6.PatternUtilities;
import oop.ex6.Token;
import oop.ex6.types.TypeRegistry;
import oop.ex6.types.TypeParser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A static-only class that provides parsing utilities for expressions.
 */
class ExpressionParser {
	// region Constants

	/**
	 * The message that will be raised as an {@link SyntaxFeatureParseException} in case
	 * there's a parse error in the assignment operation.
	 */
	private static final String ASSIGNMENT_PATTERN_MISMATCH_MESSAGE = "Unexpected assignment";

	// endregion

	// region Shared Vars

	/**
	 * Defines the pattern that used to split conditions.
	 */
	private static final Pattern CONDITIONS_SPLIT_PATTERN = Pattern.compile(
			PatternUtilities.quote(Token.LOGICAL_OR.toString(), Token.LOGICAL_AND.toString()));

	/**
	 * The actual parse pattern.
	 */
	private static final Pattern ASSIGNMENT_PARSE_PATTERN = Pattern.compile(TypeParser.formatPattern(
			"^\\s*({identifier})\\s*" + Token.OP_ASSIGNMENT + "\\s*(.*)"
					+ Token.SEMICOLON.toString() + "?\\s*$"));

	/**
	 * The actual parse pattern.
	 */
	private static final Pattern FUNCTION_CALLER_ARGS_PATTERN = Pattern.compile(TypeParser.formatPattern(
			"^\\s*(" + Token.FINAL + ")?\\s*({identifier})\\s*({identifier})\\s*$"));

	// endregion

	// region Nested Identifier

	/**
	 * Describes the result of an assignment statement.
	 */
	static class AssignmentParseResult {
		// region iVars

		/**
		 * The variable name.
		 */
		private String name;

		/**
		 * The assigned value.
		 */
		private String value;

		// endregion

		// region Initialization

		/**
		 * Creates a new assignment parse result.
		 * @param name The assigned variable name.
		 * @param value The assigned value.
		 */
		private AssignmentParseResult(String name, String value) {
			this.name = name;
			this.value = value;
		}

		// endregion

		// region Getters

		/**
		 * Gets the variable name.
		 * @return The variable name.
		 */
		public String getName() {
			return name;
		}

		/**
		 * Gets the assigned value.
		 * @return The assigned value.
		 */
		public String getValue() {
			return value;
		}


		// endregion
	}

	/**
	 * Describes the result of the parsing of a function arguments.
	 */
	static class ParsedFunctionArgument  {
		// region iVars

		/**
		 * The variable name.
		 */
		private String name;

		/**
		 * The variable data type.
		 */
		private String type;

		/**
		 * Determine whether or not the function variable is final.
		 */
		private boolean isFinal;

		// endregion

		// region Initialization

		/**
		 * Initialize a new function argument with name and data type.
		 * @param name The argument name.
		 * @param type The argument data type.
		 */
		ParsedFunctionArgument(String name, String type, boolean isFinal) {
			if (name == null || type == null) {
				throw new NullPointerException();
			}

			this.name = name;
			this.type = type;
			this.isFinal = isFinal;
		}

		// endregion

		// region Getters

		/**
		 * Gets the variable name.
		 * @return The variable name.
		 */
		public String getName() {
			return this.name;
		}

		/**
		 * Gets the argument data type.
		 * @return The argument data type.
		 */
		public String getType() {
			return this.type;
		}

		/**
		 * Gets the value that determine if it's a final argument.
		 * @return True if it's a final argument, false otherwise.
		 */
		public boolean isFinal() {
			return this.isFinal;
		}

		// endregion
	}

	// endregion

	// region Initialization

	/**
	 * The class constructor. This is a static-only class.
	 */
	private ExpressionParser() { }

	// endregion

	// region Public API

	/**
	 * Gets the list of expressions supplied in the given condition statement.
	 * @param condition The condition statement (e.g. "a", "a && b", "a || b && c" etc.).
	 * @return The list of actual expressions or null if the condition statement is invalid.
	 */
	public static List<String> parseCondition(String condition) {
		/* Setup */
		if (condition == null) {
			throw new NullPointerException();
		}

		condition = condition.trim();
		if (condition.trim().equals("")) {
			return null;
		}

		/* We're not interested in the semantics so we really don't care about the order of precedence.
			That means we don't care if we'll toss the phrentesis symbols from the conditions, as they
			don't add any new information for us, and just make it harder to parse the expression.
			P.S. We don't need to worry about an expression like "if (() {" because we already
			took care of it in the code line parsing phrase. */
		condition = condition.replace(Token.LEFT_PREN.toString(), "")
				.replace(Token.RIGHT_PREN.toString(), "");

		/* Before we actually split this expression, we should make sure we didn't got the logical operators
		(we gonna use to split the expression with) as prefix or suffix. We have to do it here as we'll
		lose that information because of the split action. */
		if (condition.startsWith(Token.LOGICAL_AND.toString())
				|| condition.startsWith(Token.LOGICAL_OR.toString())
				|| condition.endsWith(Token.LOGICAL_AND.toString())
				|| condition.endsWith(Token.LOGICAL_OR.toString())) {
			return null;
		}

		/* Get the condition parts */
		String[] expressionParts = CONDITIONS_SPLIT_PATTERN.split(condition);

		/* Iterate and check each part */
		List<String> expressions = new ArrayList<>();
		for (String expr : expressionParts) {
			expr = expr.trim();
			if (expr.equals("") || !isValidConditionExpression(expr)) {
				return null;
			}

			expressions.add(expr);
		}

		return expressions;
	}

	/**
	 * Parse the given assignment expression.
	 * @param expression The assignment expression.
	 * @return A {@link AssignmentParseResult} object that contains the parse result.
	 * @throws SyntaxFeatureParseException If the expression contains invalid values.
	 */
	public static AssignmentParseResult parseAssignment(String expression)
		throws SyntaxFeatureParseException {
		/* Fetch the data */
		Matcher m = ASSIGNMENT_PARSE_PATTERN.matcher(expression);
		if (!m.matches()) {
			throw new SyntaxFeatureParseException(ASSIGNMENT_PATTERN_MISMATCH_MESSAGE);
		}

		/* Which content have we got? */
		String variableName = m.group(1);
		String value = PatternUtilities.trimStatementTerminator(m.group(3));

		return new AssignmentParseResult(variableName, value);
	}

	/**
	 * Attempts to parse the arguments list supplied by a function invocation (a.k.a, caller).
	 * @param argsList The string that contains the arguments list, separated by {@link Token#COMMA}.
	 * @return The list of arguments, or null if there is a syntax error.
	 */
	public static List<String> parseCallerArguments(String argsList) {
		/* Setup */
		argsList = argsList.trim();
		List<String> arguments = new ArrayList<>();

		/* Do we got any args? */
		if (argsList.equals("")) {
			// We CAN have function that doesn't accept any args.
			return arguments;
		}

		/* Make sure we don't have comment at the beginning or at the end of the expression.
		As we gonna use "split", we will lose this information if we won't do it now. */
		Character firstChar = argsList.charAt(0);
		Character lastChar = argsList.charAt(argsList.length() - 1);

		if (firstChar.toString().equals(Token.COMMA.toString())
				|| lastChar.toString().equals(Token.COMMA.toString())) {
			return null;
		}

		/* Construct the list by iterating over each argument and check it */
		String[] argumentParts =  argsList.split(Token.COMMA.toString());

		/* Iterate and check each part */
		for (String argumentData : argumentParts) {
			argumentData = argumentData.trim();
			if (argumentData.equals("") || !isValidExpression(argumentData)) {
				return null;
			}

			arguments.add(argumentData);
		}

		return arguments;
	}

	/**
	 * Attempts to parse the arguments list supplied by a function declaration (a.k.a, callee).
	 * @param argsList The string that contains the arguments list, separated by {@link Token#COMMA}.
	 * @return The list of arguments, represented as {@link ParsedFunctionArgument},
	 * or null if there is a syntax error.
	 */
	public static List<ParsedFunctionArgument> parseCalleeArguments(String argsList) {
		/* Setup */
		argsList = argsList.trim();
		List<ParsedFunctionArgument> arguments = new ArrayList<>();

		/* Do we got any args? */
		if (argsList.equals("")) {
			// We CAN have function that doesn't accept any args.
			return arguments;
		}

		/* Do we have any argument? */
		if (argsList.length() < 1) {
			return arguments;
		}

		/* Make sure we don't have comment at the beginning or at the end of the expression.
		As we gonna use "split", we will lose this information if we won't do it now. */
		Character firstChar = argsList.charAt(0);
		Character lastChar = argsList.charAt(argsList.length() - 1);

		if (firstChar.toString().equals(Token.COMMA.toString())
				|| lastChar.toString().equals(Token.COMMA.toString())) {
			return null;
		}

		/* Construct the list by iterating over each argument and check it */
		String[] argumentParts = argsList.split(Token.COMMA.toString());

		/* Iterate and check each part */
		for (String argumentData : argumentParts) {
			argumentData = argumentData.trim();
			if (argumentData.equals("")) {
				return null;
			}

			ParsedFunctionArgument arg = parseCalleeArgument(argumentData);
			if (arg == null) {
				return null;
			}

			arguments.add(arg);
		}

		return arguments;
	}

	// endregion

	// region Private API

	/**
	 * Determines whether or not the given value is valid condition sub-expression.
	 * @param expression The expression.
	 * @return True if it's valid expression, false otherwise.
	 */
	private static boolean isValidConditionExpression(String expression) {
		/* Conditions can have: variables or false|true|number. The latter is, in fact, the exact
		definition of a boolean value, thus we'll use that */
		return TypeParser.isIdentifier(expression) || TypeParser.isBoolean(expression);
	}

	/**
	 * Determines whether or not the given value is valid expression.
	 * @param expression The expression.
	 * @return True if it's valid expression, false otherwise.
	 */
	private static boolean isValidExpression(String expression) {
		return TypeParser.isIdentifier(expression) || TypeRegistry.resolveFromValue(expression) != null;
	}

	/**
	 * Parse the given function argument.
	 * @param argument The argument string.
	 * @return The parsed data, or null if the data couldn't be parsed.
	 */
	private static ParsedFunctionArgument parseCalleeArgument(String argument) {
		/* Match the regular expression */
		Matcher m = FUNCTION_CALLER_ARGS_PATTERN.matcher(argument);
		if (!m.matches()) {
			return null;
		}

		/* Map */
		boolean isFinal = m.group(1) != null;
		String argType = m.group(2);
		String argName = m.group(4);

		/* Return the parsed data */
		return new ParsedFunctionArgument(argName, argType, isFinal);
	}

	// endregion
}
