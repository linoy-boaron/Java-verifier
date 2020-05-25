package oop.ex6.parser.features;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.HashMap;

/**
 * A Factory-based class used to initialize the various, available {@link SyntaxFeature}.
 */
public class SyntaxFeatureFactory {
	// region Shared Variables

    /**
     * The registered, available features to use.
     */
    private static HashMap<SyntaxFeatureIdentifier, SyntaxFeature> features;

	// endregion

    // region Initialization

    /**
     * Static initializer.
     */
    static {
        features = new HashMap<>();
        register(CommentSyntaxFeature.getInstance());
	    register(VariableDeclarationSyntaxFeature.getInstance());
	    register(AssignmentSyntaxFeature.getInstance());
	    register(IfControlFlowSyntaxFeature.getInstance());
	    register(WhileControlFlowSyntaxFeature.getInstance());
	    register(FunctionDeclarationSyntaxFeature.getInstance());
	    register(InvocationSyntaxFeature.getInstance());
	    register(ReturnStatementSyntaxFeature.getInstance());
    }

    /**
     * The factory constructor. As this is a factory design pattern based class,
     * the constructor here is private.
     */
    private SyntaxFeatureFactory() { }

    // endregion

    // region Public API

	/**
	 * Registers the given syntax feature.
	 * @param feature The feature instance to register.
	 * @throws KeyAlreadyExistsException If the requested feature has an identifier that was already
	 * associated to another syntax feature that was registered with the factory class.
	 */
	static void register(SyntaxFeature feature) {
		if (feature == null) {
			throw new NullPointerException();
		}

		if (isRegistered(feature.getIdentifier())) {
			throw new KeyAlreadyExistsException();
		}

		features.put(feature.getIdentifier(), feature);
	}

	/**
	 * Determine whether or not the given {@link SyntaxFeatureIdentifier} was associated and registered
	 * in the factory class.
	 * @param identifier The {@link SyntaxFeature} identifier.
	 * @return True if the identifier was associated to a feature, false otherwise.
	 */
	public static boolean isRegistered(SyntaxFeatureIdentifier identifier) {
		if (identifier == null) {
			throw new NullPointerException();
		}

		return features.containsKey(identifier);
	}

	/**
	 * Gets the {@link SyntaxFeature} that was associated with the given identifier.
	 * @param identifier The syntax feature identifier.
	 * @return The {@link SyntaxFeature} associated with this identifier.
	 * @throws IllegalArgumentException If the requested feature was not registered with this class.
	 */
    public static SyntaxFeature factory(SyntaxFeatureIdentifier identifier) {
		if (identifier == null) {
			throw new NullPointerException();
		}

		if (!isRegistered(identifier)) {
			throw new IllegalArgumentException("identifier");
		}

		return features.get(identifier);
    }

	/**
	 * Gets an array of objects consisted of one of more {@link SyntaxFeature}(s), associated with
	 * the given features identifiers list.
	 * @param identifiers The feature identifiers array.
	 * @return An array containing all of the requested {@link SyntaxFeature}s.
	 * @throws IllegalArgumentException If one of the requested features was not registered with this class.
	 */
	public static SyntaxFeature[] factory(SyntaxFeatureIdentifier[] identifiers) {
		if (identifiers == null) {
			throw new NullPointerException();
		}

		SyntaxFeature[] requestedFeatures = new SyntaxFeature[identifiers.length];
		for (int i = 0; i < identifiers.length; i++) {
			requestedFeatures[i] = factory(identifiers[i]);
		}

		return requestedFeatures;
    }

    // endregion
}
