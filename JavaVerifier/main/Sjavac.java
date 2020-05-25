package oop.ex6.main;

import oop.ex6.ValidatorFacade;
import oop.ex6.parser.SyntaxErrorException;
import oop.ex6.semantic.SemanticErrorException;

import java.io.IOException;

/**
 * The program main entry point.
 */
public class Sjavac {
	// region Constants

	/**
	 * The argument location of the source file.
	 */
	private static final int SOURCE_FILE_ARG_LOC = 0;

	/**
	 * The return code of a success validation.
	 */
	private static final int SUCCESS_RETURN_CODE = 0;

	/**
	 * The return code of a success validation.
	 */
	private static final int FAILURE_RETURN_CODE = 1;

	/**
	 * The return code of a success validation.
	 */
	private static final int IO_EXCEPTION_RETURN_CODE = 2;

	/**
	 * The error message that describes the {@link IOException} that's being raised if
	 * the source file is missing.
	 */
	private static final String INVALID_ARGS_MESSAGE = "The number of argument supplied is invalid.";

	/**
	 * The message that's being shown if an I/O error occurred during the validation.
	 */
	private static final String IO_EXCEPTION_MESSAGE = "An I/O error occurred during the process." +
			"\nMessage: %s";

	/**
	 * The message that's being shown if an I/O error occurred during the validation.
	 */
	private static final String SYNTAX_EXCEPTION_MESSAGE = "A syntax error has been found on line: %d." +
			"\nError Reason: %s.\nLine Contents: %s";

	/**
	 * The message that's being shown if an I/O error occurred during the validation.
	 */
	private static final String SEMANTIC_EXCEPTION_MESSAGE = "A semantic error has been found while " +
			"processing this request.\nFailure Reason: %s.";
	// endregion

	/**
	 * The program main entry point.
	 * @param args The arguments sent to this program.
	 */
    public static void main(String[] args) {
    	try {
			processRequest(args);
			System.out.println(SUCCESS_RETURN_CODE);
		} catch (IOException e1) {
			System.out.println(IO_EXCEPTION_RETURN_CODE);
		    System.err.println(String.format(IO_EXCEPTION_MESSAGE, e1.toString()));
	    } catch (SyntaxErrorException e2) {
		    System.out.println(FAILURE_RETURN_CODE);
    		System.err.println(String.format(SYNTAX_EXCEPTION_MESSAGE,
				    e2.getLineNumber(), e2.getErrorReason(), e2.getLineContent()));
	    } catch (SemanticErrorException e3) {
		    System.out.println(FAILURE_RETURN_CODE);
			System.err.println(String.format(SEMANTIC_EXCEPTION_MESSAGE, e3.getMessage()));
	    }
    }

	/**
	 * Process the request.
	 * @param args The arguments given to the program.
	 * @return True if the request was successfully processed, false otherwise.
	 * @throws IOException If there was an IO related error.
	 * @throws SyntaxErrorException If a syntax error was found during the validation process.
	 * @throws SemanticErrorException If a semantic error was found during the validation process.
	 */
	private static void processRequest(String[] args)
        throws IOException, SyntaxErrorException, SemanticErrorException {
		/* Make sure we got enough args */
    	if (args.length != 1) {
    		throw new IOException(INVALID_ARGS_MESSAGE);
	    }

	    /* Use our facade to resolve this request */
		ValidatorFacade facade = new ValidatorFacade();
	    facade.validateFile(args[SOURCE_FILE_ARG_LOC]);
    }
}
