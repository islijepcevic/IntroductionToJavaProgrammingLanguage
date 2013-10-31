package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * variable token
 * @author ivan
 *
 */
public class TokenVariable extends Token {

    private String name;

    /**
     * variable token constructor
     * @param name name of the variable
     */
    public TokenVariable( String name ) {
        this.name = name;
    }

    /**
     * text representation of the variable
     * @return variable name
     */
    public String asText() {
        return name;
    }

    /**
     * getter for variable name
     * @return name
     */
    public String getName() {
        return name;
    }

}
