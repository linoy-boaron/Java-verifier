package oop.ex6.ast;

import java.util.List;

/**
 * This class implements an AST node that represents the "if" control flow.
 */
public class IfControlFlowNode extends ScopeNode {
    // region iVars

    /**
     * The condition expressions that's being evaluated in this statement.
     * Note that as we doesn't care about semantics, we discard the logical operators associated with them.
     */
    private final List<ConditionNode> conditions;

    // endregion

    // region Initialization

    /**
     * Initializes a new if statement based AST node.
     * @param conditions The list of condition nodes.
     */
    IfControlFlowNode(List<ConditionNode> conditions) {
        super();

        this.conditions = conditions;
    }

    // endregion

    // region Public API

    /**
     * Gets the condition nodes that assembles this if statement.
     * @return The list of condition nodes.
     */
    public List<ConditionNode> getConditions() {
        return this.conditions;
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
