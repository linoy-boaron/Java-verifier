package oop.ex6.parser.features;

import oop.ex6.Token;

/**
 * A syntax feature in the language that's being represented by a scope, a.k.a a whole code block.
 * That feature means features based on scope features should except a new scope of statements after the
 * feature declaration.
 * For example: if-else, while, functions etc.
 */
public abstract class ScopeSyntaxFeature implements SyntaxFeature {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accepts(String line) {
		line = line.trim();
		return line.lastIndexOf(Token.LEFT_CURLY_PREN.toString()) == line.length() - 1;
	}

	/**
	 * Gets an array of of {@link SyntaxFeature}, symbolizing the features that're available
	 * within this scope.
	 * @return An array of syntax features that's being supported in this source code. If null is supplied,
	 * we should inferred the supported features from the outer block scope.
	 */
	public abstract SyntaxFeature[] getInnerFeatures();
}
