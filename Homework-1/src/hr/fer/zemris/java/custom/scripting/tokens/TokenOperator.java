package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * operator token
 * @author ivan
 *
 */
public class TokenOperator extends Token {

    private String symbol;

    /**
     * operator token constructor
     * @param symbol symbol for the operator
     */
    public TokenOperator( String symbol ) {
        this.symbol = symbol;
    }

    /**
     * text representation of the token
     * @return symbol
     */
    public String asText() {
        return symbol;
    }

    /**
     * getter for symbol
     * @return symbol
     */
    public String getSymbol() {
        return symbol;
    }

}
