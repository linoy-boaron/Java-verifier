package oop.ex6.ast;

import java.util.LinkedList;
import java.util.List;

/**
 * An abstract factory based class used to instantiate {@link ASTNode}s.
 */
public class ASTNodeFactory {
	// region Constants

	/**
	 * The error message that's being raised if a declaration of variables is being attempted
	 * without specifying any variable.
	 */
	private static final String DECLARATION_WITHOUT_VARS_MESSAGE = "The list of variables can not be empty.";

	// endregion

	// region Initialization

	/**
	 * The class constructor. This is a static-only class.
	 */
	private ASTNodeFactory() { }

	// endregion

	// region Public API

	/**
	 * Constructs a new program {@link ASTNode}.
	 * @return The program container node.
	 */
	public static ProgramASTNode createProgram() {
		return new ProgramASTNode();
	}

	/**
	 * Creates a new assignment {@link ASTNode}.
	 * @param variableName The variable name.
	 * @param initializationValue The variable initialization value.
	 * @return The created AST node.
	 */
	public static AssignmentNode createAssignment(String variableName, String initializationValue) {
		if (variableName == null) {
			throw new NullPointerException();
		}

		return new AssignmentNode(variableName, initializationValue);
	}

	/**
	 * Creates a new condition {@link ASTNode}.
	 * @param expression The expression to use.
	 * @return The created AST node.
	 */
	public static ConditionNode createCondition(String expression) {
		if (expression == null) {
			throw new NullPointerException();
		}

		return new ConditionNode(expression);
	}

	/**
	 * Creates a new if statement {@link ASTNode}.
	 * @param expressions The conditions.
	 * @return The created AST node.
	 */
	public static IfControlFlowNode createIfStatementFromStrings(List<String> expressions) {
		List<ConditionNode> conditions = new LinkedList<>();
		for (String expr : expressions) {
			conditions.add(createCondition(expr));
		}

		return createIfStatement(conditions);
	}

	/**
	 * Creates a new if statement {@link ASTNode}.
	 * @param conditions The conditions.
	 * @return The created AST node.
	 */
	public static IfControlFlowNode createIfStatement(List<ConditionNode> conditions) {
		if (conditions == null) {
			throw new NullPointerException();
		}

		return new IfControlFlowNode(conditions);
	}

	/**
	 * Creates a new while loop {@link ASTNode}.
	 * @param expressions The loop conditions.
	 * @return The created AST node.
	 */
	public static WhileControlFlowNode createWhileLoopFromStrings(List<String> expressions) {
		List<ConditionNode> conditions = new LinkedList<>();
		for (String expr : expressions) {
			conditions.add(createCondition(expr));
		}

		return createWhileLoop(conditions);
	}

	/**
	 * Creates a new while loop {@link ASTNode}.
	 * @param conditions The conditions.
	 * @return The created AST node.
	 */
	public static WhileControlFlowNode createWhileLoop(List<ConditionNode> conditions) {
		if (conditions == null) {
			throw new NullPointerException();
		}

		return new WhileControlFlowNode(conditions);
	}

	/**
	 * Creates a new variable (global or local) declaration {@link ASTNode}.
	 * @param variables The variables to declare.
	 * @return The created AST node.
	 * @throws RuntimeException If the variables list is empty.
	 */
	public static VariableDeclarationNode createVariableDeclaration(List<VariableNode> variables) {
		if (variables == null) {
			throw new NullPointerException();
		} else if (variables.size() < 1) {
			throw new RuntimeException(DECLARATION_WITHOUT_VARS_MESSAGE);
		}

		return new VariableDeclarationNode(variables);
	}

	/**
	 * Creates a new function callee argument {@link ASTNode}.
	 * @param type The variable type.
	 * @param name The variable name.
	 * @param isFinal True if it's a final argument, false otherwise.
	 * @return The created {@link ASTNode}.
	 */
	public static FunctionArgumentNode createFunctionArgument(String type, String name, boolean isFinal) {
		if (type == null || name == null) {
			throw new NullPointerException();
		}

		return new FunctionArgumentNode(type, name, isFinal);
	}

	/**
	 * Creates a new function declaration {@link ASTNode}.
	 * @param name The function name.
	 * @param arguments The arguments this function accepts.
	 * @return The created AST node.
	 */
	public static FunctionDeclarationNode createFunctionDeclaration(String name,
	                                                                List<FunctionArgumentNode> arguments) {
		if (name == null || arguments == null) {
			throw new NullPointerException();
		}

		return new FunctionDeclarationNode(name, arguments);
	}

	/**
	 * Creates a new variable {@link ASTNode}.
	 * @param type The variable type.
	 * @param variableName The variable name.
	 * @param initialValue The variable initial value, or null if it wasn't initialized.
	 * @param isFinal True if the variable marked as "final", false otherwise.
	 * @return The created {@link ASTNode}.
	 */
	public static VariableNode createVariable(String type, String variableName,
	                                          String initialValue, boolean isFinal) {
		if (variableName == null) {
			throw new NullPointerException();
		}

		return new VariableNode(type, variableName, initialValue, isFinal);
	}

	/**
	 * Constructs a new return statement {@link ASTNode}.
	 * @return The return statement AST node.
	 */
	public static ReturnStatementNode createReturnStatement() {
		return new ReturnStatementNode();
	}

	/**
	 * Creates a new function invocation {@link ASTNode}.
	 * @param functionName The function to trigger.
	 * @param arguments The names or values of the arguments that was supplied to the function.
	 * @return The invocation AST node.
	 */
	public static InvocationNode createInvocationStatement(String functionName, List<String> arguments) {
		if (functionName == null || arguments == null) {
			throw new NullPointerException();
		}

		return new InvocationNode(functionName, arguments);
	}

	// endregion
}
