package hr.fer.zemris.java.custom.scripting.parser;

/**
 * exception for smart script parser
 * @author ivan
 *
 */
public class SmartScriptParserException extends RuntimeException {

    /** I do not know what is this, but compiler was throwing warnings so it
     * ended here automatically generated 
     */
    private static final long serialVersionUID = 1L;

    /**
     * default constructor
     */
    public SmartScriptParserException() {
    }

    /**
     * constructor with message
     * @param message error message
     */
    public SmartScriptParserException( String message ) {
        super(message);
    }

}
