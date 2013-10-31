package hr.fer.zemris.java.custom.collections;

/**
 * @author Ivan Slijepcevic
 * exception that should be thrown when empty operations like pop or peek are
 * done on an empty stack
 */
public class EmptyStackException extends RuntimeException {

    /**
     * added only because compiler was yelling warnings
     * I do not understand this yet
     */
    private static final long serialVersionUID = 1L;

    /**
     * default constructor
     */
    public EmptyStackException() {
    }

    /**
     * constructor with message
     * @param message error message
     */
    public EmptyStackException( String message ) {
        super(message);
    }

}
