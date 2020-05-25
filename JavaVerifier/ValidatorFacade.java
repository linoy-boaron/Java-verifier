package oop.ex6;

import oop.ex6.ast.ProgramASTNode;
import oop.ex6.parser.CodeParser;
import oop.ex6.parser.SyntaxErrorException;
import oop.ex6.semantic.SemanticErrorException;
import oop.ex6.semantic.SemanticValidator;

import java.io.*;

/**
 * A facade based design pattern, used to process s-Java files.
 */
public class ValidatorFacade {
	// region Initialization

	/**
	 * Initialize a new validator facade.
	 */
	public ValidatorFacade() { }

	// endregion

	// region Public API

	/**
	 * Validates the given source code file by looking for for syntax errors.
	 * @param sourceFile The source file path.
	 * @throws IOException If there was an IO related error.
	 * @throws SyntaxErrorException If a syntax error was found during the validation process.
	 * @throws SemanticErrorException If a semantic error was found during the validation process.
	 */
	public void validateFile(String sourceFile)
		throws IOException, SyntaxErrorException, SemanticErrorException {
		if (sourceFile == null) {
			throw new NullPointerException();
		}

		this.validateSourceCode(readSourceFile(sourceFile));
	}


	/**
	 * Validates the given source code by looking for for syntax errors.
	 * @param sourceCode The source code to validate.
	 * @throws SyntaxErrorException If a syntax error was found during the validation process.
	 * @throws SemanticErrorException If a semantic error was found during the validation process.
	 */
	public void validateSourceCode(String sourceCode)
		throws SyntaxErrorException, SemanticErrorException {
		if (sourceCode == null) {
			throw new NullPointerException();
		}

		/* Parse the program into an AST */
		ProgramASTNode tree = new CodeParser().parse(sourceCode);

		/* Make sure the program is valid semantic-wise */
		new SemanticValidator(tree).assertValidity();
	}

	// endregion

	// region Private API

	/**
	 * Reads the given file into a string.
	 * @param filePath The file path to read.
	 * @return The string containing the file data.
	 * @throws IOException If the file read operation failed.
	 */
	private String readSourceFile(String filePath)
		throws IOException {
		/* Does this file exists? */
		File sourceFile = new File(filePath);
		if (!sourceFile.exists()) {
			throw new FileNotFoundException();
		}

		/* Attempt to read the entire file (note we're not using try-catch
		 * as we want the exceptions, if raised, to fall back). */
		BufferedReader buffer = new BufferedReader(new FileReader(sourceFile));
		StringBuilder builder = new StringBuilder();
		String line;

		/* Read every line */
		while ((line = buffer.readLine()) != null) {
			builder.append(line).append("\n");
		}

		return builder.toString();
	}

	// endregion
}
