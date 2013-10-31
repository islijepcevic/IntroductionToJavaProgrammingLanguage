package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayBackedIndexedCollection;

/**
 * base class for all graph nodes
 * @author ivan
 *
 */
public class Node {

    private ArrayBackedIndexedCollection children;

    /**
     * default constructor for Node class
     */
    public Node() {
        children = null;
    }

    /**
     * adds given child to internally managed children nodes
     * @param child new child
     */
    public void addChildNode( Node child) {
        // check if this child is the first one
        if (children == null) {
            children = new ArrayBackedIndexedCollection();
        }

        // add a child
        children.add( child );
    }

    /**
     * returns a number of direct children
     * @return number of children
     */
    public int numberOfChildren() {
        // check if children are initialised
        if (children == null) {
            return 0;
        }
        return children.size();
    }

    /**
     * fetches selected child, or throws an exception
     * @param index index of wanted child
     * @return child
     */
    public Node getChild( int index ) {
        // checx if children are initialised
        if (children == null) {
            throw new IndexOutOfBoundsException();
        }
        return (Node) children.get( index );
    }

}
