package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * constant double token
 * @author ivan
 *
 */
public class TokenConstantDouble extends Token {

    private double value;

    /**
     * constant double token constructor
     * @param value double token value
     */
    public TokenConstantDouble( double value ) {
        this.value = value;
    } 

    /**
     * text representation of the token
     * @return value converted to String
     */
    public String asText() {
        return Double.toString( value );
    }

    /**
     * getter for value
     * @return value
     */
    public double getValue() {
        return value;
    }

}
