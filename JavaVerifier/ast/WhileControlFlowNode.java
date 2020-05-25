package oop.ex6.ast;

import java.util.List;

/**
 * This class implements an AST node that represents the "while" control flow.
 */
public class WhileControlFlowNode extends ScopeNode {
    // region iVars

    /**
     * The expressions that's being evaluated in this loop.
     */
    private final List<ConditionNode> conditions;

    // endregion

    // region Initialization

    /**
     * Initializes a new while control flow based AST node.
     * @param conditions The list of loop condition nodes.
     */
    WhileControlFlowNode(List<ConditionNode> conditions) {
        super();
        this.conditions = conditions;
    }

    // endregion

    // region Public API

    /**
     * Gets the condition nodes that assembles this while loop.
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
