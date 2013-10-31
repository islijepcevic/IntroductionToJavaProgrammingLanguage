package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * node representing a piece of text data
 * @author ivan
 *
 */
public class TextNode extends Node {

    private String text;

    /**
     * constructor for text node
     * @param text given text content
     */
    public TextNode( String text ) {
        this.text = text;
    }

    /**
     * getter for text
     * @return text value
     */
    public String getText() {
        return text;
    }

}
