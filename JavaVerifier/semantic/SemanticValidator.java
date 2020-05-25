package oop.ex6.semantic;

import oop.ex6.Token;
import oop.ex6.ast.*;
import oop.ex6.types.DataType;
import oop.ex6.types.TypeParser;
import oop.ex6.types.TypeRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that provides (very simple) semantic validation.
 */
public class SemanticValidator {
	// region iVars

	/**
	 * The root node of the AST.
	 */
	private final ProgramASTNode root;

	/**
	 * A table that contains the declared functions and their arguments list.
	 */
	private final SymbolsTable<FunctionSymbol> functionsSymbolTable;

	/**
	 * A symbols table that keep track of allocated variables, relatively to their scope.
	 */
	private final SymbolsTable<VariableSymbol> variablesTable;

	// endregion

	// region Nested Classes - Semantic Visitors

	/**
	 * Define the abstract, base, implementation of a semantic visitor.
	 */
	private class AbstractSemanticVisitor implements ASTVisitor {
		// region Constants

		/**
		 * The message that's being raised if someone tries to assign a value to final variable.
		 */
		private static final String ATTEMPT_ASSIGN_FINAL_MESSAGE = "The variable %s was defined as " +
				"\"final\" and therefore its value can't be changed.";

		/**
		 * The message that's being raised if someone tries to assign a value to final variable.
		 */
		private static final String ASSIGNED_VALUE_INVALID_MESSAGE = "The assigned value for" +
				" the variable \"%s\" (of type \"%s\") is invalid and therefore got rejected.";

		// endregion

		// region Public API

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visit(ProgramASTNode node) {
			for (ASTNode n : node) {
				n.accept(this);
			}
		}

		/**
		 * @{inheritDoc}
		 */
		@Override
		public void visit(VariableDeclarationNode node) {
			/* Iterate over each variable and try to declare it */
			for (VariableNode variable : node.getVariables()) {
				variable.accept(this);
			}
		}

		/**
		 * A method that's being triggered when the visitor visits an {@link AssignmentNode}.
		 * @param node The node that the visitor found while iterating over the tree.
		 * @throws IllegalStateException If an error has been occurred while processing this node.
		 * The exception will contain a "cause" {@link Throwable} instance of type
		 * {@link SemanticErrorException} describing the  actual exception cause. The exception might be of
		 * the following types:
		 * A) {@link SymbolNotFoundException} If the assigned variable (a.k.a left operator) wasn't declared.
		 * B) {@link InvalidExpressionException} If the assignment operation is invalid.
		 */
		@Override
		public void visit(AssignmentNode node) {
			/* Firstly, lets check that the specified variable exists
			in our symbols table */
			if (!variablesTable.containsKey(node.getName())) {
				throw new IllegalStateException(new SymbolNotFoundException(node.getName()));
			}

			/* Now, lets make sure it's not final */
			VariableSymbol symbol = variablesTable.get(node.getName());
			if (symbol.isFinal()) {
				throw new IllegalStateException(new InvalidExpressionException(
						String.format(ATTEMPT_ASSIGN_FINAL_MESSAGE, symbol.getName())));
			}

			/* Finally, lets make sure the value we put can be assigned to this variable */
			if (!this.canAssignValue(symbol, node.getValue())) {
				throw new IllegalStateException(new InvalidExpressionException(
						String.format(ASSIGNED_VALUE_INVALID_MESSAGE, symbol.getName(),
								symbol.getType().getIdentifier())));
			}

			/* Finally, perform the assignment */
			symbol.setValue(node.getValue());
		}

		// endregion

		// region Private API

		/**
		 * Handles the declaration process of the given variable.
		 * @param node The variable node.
		 * @throws IllegalStateException If an error has been occurred while processing this node.
		 * The exception will contain a "cause" {@link Throwable} instance of type
		 * {@link SemanticErrorException} describing the actual exception cause. The exception might be of
		 * the following types:
		 * 1) {@link TypeNotFoundException}: If the variable type is invalid.
		 * 2) {@link InvalidExpressionException}: If the assigned variable value is invalid.
		 * 3) {@link UninitializedFinalVariableException}: If the variable was declared as final but
		 * wasn't initialised with any value.
		 */
		protected void handleVariableDeclaration(VariableNode node) {
			/* Check the variable type */
			if (!TypeRegistry.isRegistered(node.getType())) {
				throw new IllegalStateException(new TypeNotFoundException(node.getType()));
			}

			/* Create the symbol */
			VariableSymbol symbol = new VariableSymbol(node.getName(), TypeRegistry.factory(node.getType()),
					null, node.isFinal(), this.isInGlobalScope());

			/* Do we have a value? */
			if (node.hasValue()) {
				/* Make sure we can assign the value to the given type */
				if (!canAssignValue(symbol, node.getValue())) {
					throw new IllegalStateException(new InvalidExpressionException(
							String.format(ASSIGNED_VALUE_INVALID_MESSAGE, symbol.getName(),
									symbol.getType().getIdentifier())));
				}

				symbol.setValue(node.getValue());
			} else {
				/* Is this a final variable? it can't be, right?! */
				if (node.isFinal()) {
					throw new IllegalStateException(new UninitializedFinalVariableException(node.getName()));
				}
			}

			/* Put it! */
			variablesTable.put(symbol);
		}

		/**
		 * Determine whether or not the given value is a valid value for the argument supplied.
		 * @param argument The function argument symbol data.
		 * @param value The value that was tried to be assigned.
		 * @return True if this is a valid value, or false otherwise.
		 * @throws IllegalStateException If an error has been occurred while processing this node.
		 * The exception will contain a "cause" {@link Throwable} instance of type
		 * {@link SemanticErrorException} describing the actual exception cause. The exception might be of
		 * the following types:
		 * 1) {@link SymbolNotFoundException}: In case the assigned value is a variable, and it couldn't be
		 *    located in the symbols table.
		 * 2) {@link AccessUninitializedVariableException}: In case the assigning value is a variable, and
		 *    it was never initialised before.
		 */
		protected boolean isValidArgumentValue(FunctionArgumentSymbol argument, String value) {
			return valueMatchesType(value, argument.getType());
		}

		/**
		 * Determine whether or not we're currently inside the global scope.
		 * @return True if we're in the global scope, false otherwise.
		 */
		protected boolean isInGlobalScope() {
			return variablesTable.getDepth() == 1;
		}

		/**
		 * Checks whether or not the given value can be assigned to the symbol.
		 * @param symbol The variable symbol to assign the value to.
		 * @param value The assigned value in string representation.
		 * @return True if the value could be assigned, false otherwise.
		 * @throws NullPointerException If the given symbol or value is/are null.
		 * @throws IllegalStateException If an error has been occurred while processing this node.
		 * The exception will contain a "cause" {@link Throwable} instance of type
		 * {@link SemanticErrorException} describing the actual exception cause. The exception might be of
		 * the following types:
		 * 1) {@link SymbolNotFoundException}: In case the assigned value is a variable, and it couldn't be
		 *    located in the symbols table.
		 * 2) {@link AccessUninitializedVariableException}: In case the assigning value is a variable, and
		 *    it was never initialised before.
		 */
		protected boolean canAssignValue(VariableSymbol symbol, String value) {
			/* If this is a variable we are assigning, we need to make sure that it got the same type
			OH, and we need to make sure it got initialized as well. */
			if (symbol == null || value == null) {
				throw new NullPointerException();
			}

			return valueMatchesType(value, symbol.getType());
		}

		/**
		 * Determine whether or not the given value matches the specified data type.
		 * @param value The value to check.
		 * @param type The data type to test against.
		 * @return True if the value matches the data type, false otherwise.
		 * @throws IllegalStateException If an error has been occurred while processing this node.
		 * The exception will contain a "cause" {@link Throwable} instance of type
		 * {@link SemanticErrorException} describing the actual exception cause. The exception might be of
		 * the following types:
		 * 1) {@link SymbolNotFoundException}: In case the assigned value is a variable, and it couldn't be
		 *    located in the symbols table.
		 * 2) {@link AccessUninitializedVariableException}: In case the assigning value is a variable, and
		 *    it was never initialised before.
		 */
		protected boolean valueMatchesType(String value, DataType type) {
			/* Firstly, we have to check if this is a constant */
			if (type.isValidValue(value)) {
				return true;
			}

			/* We have no choice, but to check for an identifier - which symbolize variables */
			if (TypeParser.isIdentifier(value)) {
				/* Attempt to get the variable */
				VariableSymbol rParam = variablesTable.get(value);
				if (rParam == null) {
					throw new IllegalStateException(new SymbolNotFoundException(value));
				}

				/* Make sure this variable was initialized */
				if (!rParam.hasValue()) {
					throw new IllegalStateException(new AccessUninitializedVariableException(value));
				}

				/* Determine the assignment result by the variable types */
				return type.canCreatedFrom(rParam.getType());
			} else {
				return false;
			}
		}

		// endregion
	}

	/**
	 * Defines a visitor design pattern class used to visit the global scope definitions
	 * and store them within the functions & variable tables.
	 */
	private class GlobalScopeVisitor extends AbstractSemanticVisitor {
		// region Public API

		/**
		 * A method that's being triggered when the visitor visits an {@link FunctionArgumentNode}.
		 * @param node The node that the visitor found while iterating over the tree.
		 * @throws IllegalStateException If an error has been occurred while composing the symbol table.
		 * The exception will contain a "cause" {@link Throwable} instance of type
		 * {@link SemanticErrorException} describing the actual exception cause. The exception might be of
		 * the following types:
		 * A) {@link SymbolAlreadyExistsException} If the function was already defined or if one of the
		 * function argument names was already been used (e.g. "void foo(int a, int a)").
		 * B) {@link TypeNotFoundException} If one of the argument types doesn't exists
		 * (e.g. "void foo(aaa argName)").
		 */
		@Override
		public void visit(FunctionDeclarationNode node) {
			/* Do we have the same symbol registered already? */
			Symbol previousSymbol = functionsSymbolTable.get(node.getName());
			if (previousSymbol != null) {
				throw new IllegalStateException(new SymbolAlreadyExistsException(previousSymbol));
			}

			/* Attempt to create the symbol */
			functionsSymbolTable.put(this.createFunctionSymbol(node));
		}

		/**
		 * A method that's being triggered when the visitor visits an {@link VariableNode}.
		 * @param node The node that the visitor found while iterating over the tree.
		 * @throws IllegalStateException If an error has been occurred while processing this node.
		 * The exception will contain a "cause" {@link Throwable} instance of type
		 * {@link SemanticErrorException} describing the actual exception cause. The exception might be of
		 * the following types:
		 * 1) {@link TypeNotFoundException}: If the variable type is invalid.
		 * 2) {@link InvalidExpressionException}: If the assigned variable value is invalid.
		 * 3) {@link UninitializedFinalVariableException}: If the variable was declared as final but
		 * wasn't initialised with any value.
		 * 4) {@link SymbolAlreadyExistsException}: If a variable with that name already exists.
		 */
		@Override
		public void visit(VariableNode node) {
			/* We shouldn't handle non-global scope variables here */
			if (!this.isInGlobalScope()) {
				return;
			}

			/* Do we have a variable with that name */
			VariableSymbol previousSymbol = variablesTable.get(node.getName());
			if (previousSymbol != null) {
				throw new IllegalStateException(new SymbolAlreadyExistsException(previousSymbol));
			}

			/* Declare this variable */
			handleVariableDeclaration(node);
		}

		// endregion

		// region Private Helpers

		/**
		 * Attempts to create a function symbol.
		 * @param node The {@link ASTNode} that define the function data.
		 * @return The created node.
		 * @throws IllegalStateException If an error has been occurred while composing the symbol table. The exception
		 * will contain a "cause" {@link Throwable} instance of type {@link SemanticErrorException} describing the
		 * actual exception cause. The exception might be of the following types:
		 * A) {@link SymbolAlreadyExistsException} If one of the function argument names was already been used.
		 * B) {@link TypeNotFoundException} If one of the argument types doesn't exists (e.g. "void foo(aaa argName)").
		 */
		private FunctionSymbol createFunctionSymbol(FunctionDeclarationNode node) {
			/* Compose the arguments list */
			List<FunctionArgumentSymbol> args = new ArrayList<>();
			for (FunctionArgumentNode argNode : node.getArguments()) {
				/* Check that the used data type is valid */
				if (!TypeRegistry.isRegistered(argNode.getType())) {
					throw new IllegalStateException(new TypeNotFoundException(argNode.getType()));
				}

				/* Have we already registered an argument with this name? */
				FunctionArgumentSymbol arg = new FunctionArgumentSymbol(
						argNode.getName(), TypeRegistry.factory(argNode.getType()));
				if (args.contains(arg)) {
					throw new IllegalStateException(new SymbolAlreadyExistsException(arg));
				}

				/* Add it */
				args.add(arg);
			}

			return new FunctionSymbol(node.getName(), args);
		}

		// endregion
	}

	/**
	 * A visitor based class that iterates over the AST and validate its semantics.
	 */
	private class GeneralSemanticVisitor extends AbstractSemanticVisitor {
		// region Constants

		/**
		 * The message that being thrown if an invocation has been attempted without
		 * the right number of arguments.
		 */
		private static final String INVOCATION_INVALID_NUM_ARGS_MESSAGE = "An invocation of the function " +
				"\"%s\" was detected with %d arguments, while it accepts %d arguments.";

		/**
		 * The message that being thrown if an invocation has been attempted with invalid argument.
		 */
		private static final String INVOCATION_INVALID_ARG_MESSAGE = "The function %s was tried to be " +
				"invoked with an invalid value, %s, as it's %d'th argument (\"%s\" of type \"%s\").";

		/**
		 * The message that being thrown if an invalid control flow condition has been detected.
		 */
		private static final String INVALID_CONDITION_MESSAGE = "The specified condition can't be " +
				"evaluated. The variable \"%s\" is of type \"%s\" and thus couldn't be casted to \"%s\".";

		// endregion

		// region Public API

		/**
		 * A method that's being triggered when the visitor visits an {@link ConditionNode}.
		 * @param node The node that the visitor found while iterating over the tree.
		 * @throws IllegalStateException If an error has been occurred while processing this node.
		 * The exception will contain a "cause" {@link Throwable} instance of type
		 * {@link SemanticErrorException} describing the actual exception cause. The exception might be of
		 * the following types:
		 * 1) {@link SymbolNotFoundException}: If the condition is consisted from an undefined variable.
		 * 2) {@link InvalidExpressionException}: If the condition expression is invalid.
		 * 3) {@link AccessUninitializedVariableException}: If the condition expression tried to access
		 * an initialised variable.
		 */
		@Override
		public void visit(ConditionNode node) {
			String expression = node.getExpression();
			DataType resolver = TypeRegistry.factory(Token.TYPE_BOOLEAN.toString());

			/* Firstly, is this a boolean constant? */
			if (resolver.isValidValue(expression)) {
				return;
			}

			/* It should be a variable, so lets check that it's an initialized boolean */
			VariableSymbol symbol = variablesTable.get(expression);
			if (symbol == null) {
				throw new IllegalStateException(new SymbolNotFoundException(expression));
			}

			/* This variable is indeed a boolean? */
			if (!resolver.canCreatedFrom(symbol.getType())) {
				throw new IllegalStateException(new InvalidExpressionException(String.format(
						INVALID_CONDITION_MESSAGE, expression, symbol.getType().getIdentifier(),
						resolver.getIdentifier())));
			}

			/* This variable was initialized? */
			if (!symbol.hasValue()) {
				throw new IllegalStateException(new AccessUninitializedVariableException(expression));
			}
		}

		/**
		 * A method that's being triggered when the visitor visits an {@link FunctionArgumentNode}.
		 * @param node The node that the visitor found while iterating over the tree.
		 * @throws IllegalStateException If an error has been occurred while processing this node.
		 * The exception will contain a "cause" {@link Throwable} instance of type
		 * {@link SemanticErrorException} describing the actual exception cause. The exception might be of
		 * the following types:
		 * 1) {@link TypeNotFoundException} If the argument type wasn't declared.
		 */
		@Override
		public void visit(FunctionArgumentNode node) {
			/* Firstly, lets make sure that this type was defined earlier */
			if (!TypeRegistry.isRegistered(node.getType())) {
				throw new IllegalStateException(new TypeNotFoundException(node.getType()));
			}

			/* Declare the variable. Note that since we don't have an actual compiler/interpreter we can't
			 * actually figure out WHICH value sent to this variable, so we just use the default values. */
			DataType type = TypeRegistry.factory(node.getType());
			VariableSymbol symbol = new VariableSymbol(
					node.getName(), type,type.getDefaultValue(), node.isFinal());

			variablesTable.put(symbol);
		}

		/**
		 * A method that's being triggered when the visitor visits an {@link FunctionDeclarationNode}.
		 * @param node The node that the visitor found while iterating over the tree.
		 * @throws IllegalStateException If an error has been occurred while processing this node.
		 * The exception will contain a "cause" {@link Throwable} instance of type
		 * {@link SemanticErrorException} describing the actual exception cause. The exception might be of
		 * the following types:
		 * 1) {@link MissingReturnStatementException} If the function doesn't have a return statement
		 * at the end of it.
		 * 2) {@link SemanticErrorException}: If one of the function statement(s) triggers an exception. See
		 * the other visit overloads for more description on the various exception cases.
		 */
		@Override
		public void visit(FunctionDeclarationNode node) {
			/* Push the new state */
			variablesTable.pushState();

			/* Declare the arguments */
			for (FunctionArgumentNode argument : node.getArguments()) {
				argument.accept(this);
			}

			/* Do we have a return statement as the last statement? */
			int numberOfStatements = node.getStatements().size();
			if (numberOfStatements == 0
					|| !(node.getStatements().get(numberOfStatements - 1) instanceof ReturnStatementNode)) {
				throw new IllegalStateException(new MissingReturnStatementException(node.getName()));
			}

			/* Perform each statement in that function  */
			for (ASTNode statement : node) {
				statement.accept(this);
			}

			/* And... pop the state we were in */
			variablesTable.popState();
		}

		/**
		 * A method that's being triggered when the visitor visits an {@link IfControlFlowNode}.
		 * @param node The node that the visitor found while iterating over the tree.
		 * @throws IllegalStateException If an error has been occurred while processing this node.
		 * The exception will contain a "cause" {@link Throwable} instance of type
		 * {@link SemanticErrorException} describing the actual exception cause. The exception might be of
		 * the following types:
		 * 1) {@link SemanticErrorException}: If one of the condition expressions or the condition truth
		 * statement(s) scope triggers an exception. See the other visit overloads for more description on
		 * the various exception cases.
		 */
		@Override
		public void visit(IfControlFlowNode node) {
			/* Check the conditions */
			for (ConditionNode condition : node.getConditions()) {
				condition.accept(this);
			}

			/* Execute the control flow in a new scope */
			variablesTable.pushState();

			for (ASTNode stmt : node) {
				stmt.accept(this);
			}

			variablesTable.popState();
		}

		/**
		 * A method that's being triggered when the visitor visits an {@link InvocationNode}.
		 * @param node The node that the visitor found while iterating over the tree.
		 * @throws IllegalStateException If an error has been occurred while processing this node.
		 * The exception will contain a "cause" {@link Throwable} instance of type
		 * {@link SemanticErrorException} describing the actual exception cause. The exception might be of
		 * the following types:
		 * 1) {@link SymbolNotFoundException}: If the called function wasn't declared.
		 * 2) {@link InvalidInvocationException}: If one or more of the invocation argument(s) is invalid.
		 */
		@Override
		public void visit(InvocationNode node) {
			/* Was this function declared before? */
			FunctionSymbol functionSymbol = functionsSymbolTable.get(node.getName());
			if (functionSymbol == null) {
				throw new IllegalStateException(new SymbolNotFoundException(node.getName()));
			}

			/* Do we have enough argument? */
			List<FunctionArgumentSymbol> symbolArguments = functionSymbol.getArguments();
			List<String> nodeArguments = node.getArguments();
			int len = symbolArguments.size();
			if (len != nodeArguments.size()) {
				throw new IllegalStateException(new InvalidInvocationException(
						String.format(INVOCATION_INVALID_NUM_ARGS_MESSAGE,
								functionSymbol.getName(), nodeArguments.size(), len)));
			}

			/* Attempt to compare each argument */
			for (int i = 0; i < len; i++) {
				if (!isValidArgumentValue(symbolArguments.get(i), nodeArguments.get(i))) {
					throw new IllegalStateException(new InvalidInvocationException(
							String.format(INVOCATION_INVALID_ARG_MESSAGE,
									functionSymbol.getName(), nodeArguments.get(i),
									i + 1, symbolArguments.get(i).getName(),
									symbolArguments.get(i).getType().getIdentifier())));
				}
			}
		}

		/**
		 * A method that's being triggered when the visitor visits an {@link VariableNode}.
		 * @param node The node that the visitor found while iterating over the tree.
		 * @throws IllegalStateException If an error has been occurred while processing this node.
		 * The exception will contain a "cause" {@link Throwable} instance of type
		 * {@link SemanticErrorException} describing the actual exception cause. The exception might be of
		 * the following types:
		 * 1) {@link TypeNotFoundException}: If the variable type is invalid.
		 * 2) {@link InvalidExpressionException}: If the assigned variable value is invalid.
		 * 3) {@link UninitializedFinalVariableException}: If the variable was declared as final but
		 * wasn't initialised with any value.
		 * 4) {@link SymbolAlreadyExistsException}: If a variable with that name already exists.
		 */
		@Override
		public void visit(VariableNode node) {
			/* We shouldn't handle global scope variables */
			if (this.isInGlobalScope()) {
				return;
			}

			/* Do we have a variable with that name */
			VariableSymbol previousSymbol = variablesTable.get(node.getName());
			if (previousSymbol != null) {
				/* We do allow to define and "override" global declarations with local declarations. */
				if (!previousSymbol.isGlobal()) {
					throw new IllegalStateException(new SymbolAlreadyExistsException(previousSymbol));
				}

				/* Remove that symbol before continuing */
				variablesTable.remove(previousSymbol.getName());
			}

			/* Declare this variable */
			handleVariableDeclaration(node);
		}

		/**
		 * A method that's being triggered when the visitor visits an {@link WhileControlFlowNode}.
		 * @param node The node that the visitor found while iterating over the tree.
		 * @throws IllegalStateException If an error has been occurred while processing this node.
		 * The exception will contain a "cause" {@link Throwable} instance of type
		 * {@link SemanticErrorException} describing the actual exception cause. The exception might be of
		 * the following types:
		 * 1) {@link SemanticErrorException}: If one of the while condition expressions or the loop
		 * statement(s) scope triggers an exception. See the other visit overloads for more description on
		 * the various exception cases.
		 */
		@Override
		public void visit(WhileControlFlowNode node) {
			/* Check the loop condition(s0 */
			for (ConditionNode condition : node.getConditions()) {
				condition.accept(this);
			}

			/* Execute the control flow in a new scope */
			variablesTable.pushState();

			for (ASTNode stmt : node) {
				stmt.accept(this);
			}

			variablesTable.popState();
		}

		// endregion
	}

	// endregion

	// region Initialization

	/**
	 * Initializes a new semantic validator.
 	 * @param root The program AST node.
	 */
	public SemanticValidator(ProgramASTNode root) {
		this.root = root;
		this.functionsSymbolTable = new SymbolsTable<>();
		this.variablesTable = new SymbolsTable<>();
	}

	// endregion

	// region Public API

	/**
	 * Asserts the validity of the given code.
	 * @throws SemanticErrorException If the given code is semantically invalid. The actual exceptioon
	 * concrete class will vary on the semantic error reason.
	 */
	public void assertValidity()
		throws SemanticErrorException {
		/* Create the functions symbol table and then parse the file.
		 	Note that since we're using checked exceptions within the
			visitor, it'd be wrong to couple SemanticErrorException etc. to the actual AST visitor.
			Thus, and as we can't use the "throws" keyword, we're using a IllegalStateException - which's a
			runtime exception, and wrapping within it the actual checked exception.
			See: https://stackoverflow.com/a/19842081. */
		try {
			/* Setup the functions symbol table */
			GlobalScopeVisitor globalScopeVisitor = new GlobalScopeVisitor();
			globalScopeVisitor.visit(this.root);

			/* Make sure that each type we use is valid. */
			GeneralSemanticVisitor visitor = new GeneralSemanticVisitor();
			visitor.visit(this.root);
		} catch (IllegalStateException e) {
			/* Release the inner exception */
			if (e.getCause() != null && e.getCause() instanceof SemanticErrorException) {
				throw (SemanticErrorException) e.getCause();
			}

			throw e; // General handling.
		}
	}

	// endregion
}
