package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Exception to throw when end of for loop is encountered
 * @author ivan
 *
 */
public class EndLoopException extends RuntimeException {

    /**
     * I do not know what tihs is, but compiler was throwing warnings so it
     * ended here automatically generated
     */
    private static final long serialVersionUID = 1L;

	/**
     * default constructor
     */
    public EndLoopException() {
    }

    /**
     * constructor with message
     * @param message error message
     */
    public EndLoopException( String message ) {
        super(message);
    }

}
