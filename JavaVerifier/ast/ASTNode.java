package oop.ex6.ast;

/**
 * This interface represents a general node in an Abstract Syntax Tree (hereinafter: "AST").
 * An AST is a tree representation of the abstract syntactic structure
 * of a source code written in a programming language.
 * Each node of the tree denotes a construct occurring in the source code.
 * The syntax is "abstract" in not representing every detail appearing in the real syntax.
 */
public interface ASTNode {
	/**
	 * Accepts a {@link ASTVisitor} and invoke on it each node this {@link ASTNode} responsible on.
	 * @param visitor The visitor.
	 */
	void accept(ASTVisitor visitor);
}
