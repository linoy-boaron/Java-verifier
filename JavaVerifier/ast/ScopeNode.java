package oop.ex6.ast;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class implements an AST node that represents a general scope of code.
 */
public abstract class ScopeNode implements ASTNode, Iterable<ASTNode> {
	// region iVars

	/**
	 * The list of statements this block contains.
	 */
	private final List<ASTNode> statements;

	// endregion

	// region Constructors

	/**
	 * Initializes a new empty {@link ASTNode} code scope.
	 */
	ScopeNode() {
		super();
		this.statements = new LinkedList<>();
	}

	/**
	 * Initializes a {@link ASTNode} code scope from the given statements list.
	 * @param nodes The list of nodes.
	 */
	ScopeNode(List<ASTNode> nodes) {
		super();
		this.statements = nodes;
	}

	/**
	 * Initializes a {@link ASTNode} code scope from the given statements list.
	 * @param nodes The list of nodes.
	 */
	ScopeNode(ASTNode... nodes) {
		this(Arrays.asList(nodes));
	}

	// endregion

	// region Public API

	/**
	 * Appends a new {@link ASTNode} to this scope.
	 * @param node The node to append.
	 */
	public void append(ASTNode node) {
		if (node == null) {
			throw new NullPointerException();
		}

		this.statements.add(node);
	}

	/**
	 * {@inheritDoc}
	 */
	public Iterator<ASTNode> iterator() {
		return this.statements.iterator();
	}

	/**
	 * Gets the scope statements list.
	 * @return The scope statements list.
	 */
	public List<ASTNode> getStatements() {
		return this.statements;
	}

	// endregion
}
