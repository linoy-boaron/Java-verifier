package oop.ex6.ast;

/**
 * This class implements an AST node that represents a single condition expression (e.g. "true", "a" etc.).
 */
public class ConditionNode implements ASTNode {
    // region iVars

    /**
     * The condition expression.
     */
    private final String expression;

    // endregion

    // region Constructor

    /**
     * Initializes a new condition expression.
     * @param expression The expression string.
     */
    ConditionNode(String expression) {
        this.expression = expression;
    }

    // endregion

    // region Getters

    /**
     * Gets the condition expression contents.
     * @return The condition expression.
     */
    public String getExpression() {
        return this.expression;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    // endregion
}
