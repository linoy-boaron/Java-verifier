package oop.ex6.parser.features;

/**
 * Defines the available language syntax features.
 */
public enum SyntaxFeatureIdentifier {
	/**
	 * A {@link SyntaxFeature} supporting comments in the language.
	 */
	COMMENT,

	/**
	 * A {@link SyntaxFeature} supporting the if control flow in the language.
	 */
	IF_CONTROL_FLOW,

	/**
	 * A {@link SyntaxFeature} supporting the while control flow in the language.
	 */
	WHILE_CONTROL_FLOW,

	/**
	 * A {@link SyntaxFeature} supporting variable declaration in the language.
	 */
	VARIABLE_DECLARATION,

	/**
	 * A {@link SyntaxFeature} supporting function declaration in the language.
	 */
	FUNCTION_DECLARATION,

	/**
	 * A {@link SyntaxFeature} supporting the return statement in the language.
	 */
	RETURN_STATEMENT,

	/**
	 * A {@link SyntaxFeature} supporting assignments in the language.
	 */
	ASSIGNMENT,

	/**
	 * A {@link SyntaxFeature} supporting functions invocation in the language.
	 */
	FUNCTION_INVOCATION
}
