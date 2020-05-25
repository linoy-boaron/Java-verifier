package oop.ex6.ast;

/**
 * A Visitor based design pattern interface used to scan an AST tree.
 */
public interface ASTVisitor {
	/**
	 * A method that's being triggered when the visitor visits an {@link AssignmentNode}.
	 * @param node The node that the visitor found while iterating over the tree.
	 */
	default void visit(AssignmentNode node) { }

	/**
	 * A method that's being triggered when the visitor visits an {@link ConditionNode}.
	 * @param node The node that the visitor found while iterating over the tree.
	 */
	default void visit(ConditionNode node) { }

	/**
	 * A method that's being triggered when the visitor visits an {@link FunctionArgumentNode}.
	 * @param node The node that the visitor found while iterating over the tree.
	 */
	default void visit(FunctionArgumentNode node) { }

	/**
	 * A method that's being triggered when the visitor visits an {@link FunctionDeclarationNode}.
	 * @param node The node that the visitor found while iterating over the tree.
	 */
	default void visit(FunctionDeclarationNode node) { }

	/**
	 * A method that's being triggered when the visitor visits an {@link IfControlFlowNode}.
	 * @param node The node that the visitor found while iterating over the tree.
	 */
	default void visit(IfControlFlowNode node) { }

	/**
	 * A method that's being triggered when the visitor visits an {@link InvocationNode}.
	 * @param node The node that the visitor found while iterating over the tree.
	 */
	default void visit(InvocationNode node) { }

	/**
	 * A method that's being triggered when the visitor visits an {@link ProgramASTNode}.
	 * @param node The node that the visitor found while iterating over the tree.
	 */
	default void visit(ProgramASTNode node) { }

	/**
	 * A method that's being triggered when the visitor visits an {@link ReturnStatementNode}.
	 * @param node The node that the visitor found while iterating over the tree.
	 */
	default void visit(ReturnStatementNode node) { }

	/**
	 * A method that's being triggered when the visitor visits an {@link VariableDeclarationNode}.
	 * @param node The node that the visitor found while iterating over the tree.
	 */
	default void visit(VariableDeclarationNode node) { }

	/**
	 * A method that's being triggered when the visitor visits an {@link VariableNode}.
	 * @param node The node that the visitor found while iterating over the tree.
	 */
	default void visit(VariableNode node) { }

	/**
	 * A method that's being triggered when the visitor visits an {@link WhileControlFlowNode}.
	 * @param node The node that the visitor found while iterating over the tree.
	 */
	default void visit(WhileControlFlowNode node) { }
}
