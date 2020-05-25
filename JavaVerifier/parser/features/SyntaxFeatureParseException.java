package oop.ex6.parser.features;

/**
 * An exception that's being raised in case of a parse error in a {@link SyntaxFeature} based class.
 */
public class SyntaxFeatureParseException extends Exception {
    /**
     * {@inheritDoc}
     */
    public SyntaxFeatureParseException() {
    }

    /**
     * {@inheritDoc}
     */
    public SyntaxFeatureParseException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public SyntaxFeatureParseException(String message, Throwable cause) {
        super(message, cause);
    }

}
