package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * function token
 * @author ivan
 *
 */
public class TokenFunction extends Token {

    private String name;

    /**
     * function token constructor
     * @param name function name
     */
    public TokenFunction( String name ) {
        this.name = name;
    }

    /**
     * text representation of the token
     * @return function name
     */
    public String asText() {
        return name;
    }

    /**
     * getter for function name
     * @return function name
     */
    public String getName() {
        return name;
    }

}
