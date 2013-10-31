package hr.fer.zemris.java.custom.collections;
import hr.fer.zemris.java.custom.collections.ArrayBackedIndexedCollection;
import hr.fer.zemris.java.custom.collections.EmptyStackException;

/**
 * @author Ivan Slijepcevic
 * Stack implementation for Objects
 * Adaptor class for ArrayBackedIndexedCollection adaptee
 */
public class ObjectStack {

    private ArrayBackedIndexedCollection adaptee;

    /**
     * Initializes the adaptee collection
     */
    public ObjectStack() {
        adaptee = new ArrayBackedIndexedCollection();
    }

    /**
     * Checks if stack is empty
     * @return true if empty
     */
    public boolean isEmpty() {
        return adaptee.isEmpty();
    }

    /**
     * Gives the size of the stack
     * @return size
     */
    public int size() {
        return adaptee.size();
    }

    /**
     * Pushes value on stack
     * @param value element to be pushed on stack, must not be null
     */
    public void push( Object value ) {
        adaptee.add( value );
    }

    /**
     * Removes and returns the top value on stack, assuming stack is not empty
     * @return top value on stack
     */
    public Object pop() {
        Object top = peek();
        adaptee.remove( adaptee.size() - 1 );
        return top;
    }

    /**
     * Returns the top value without removing it, assuming stack is not empty
     * @return top value
     */
    public Object peek() throws EmptyStackException {
        // preconditions
        if (isEmpty()) {
            throw new EmptyStackException();
        }

        Object top = adaptee.get( adaptee.size() - 1 );
        return top;
    }

    /**
     * Removes all elements from stack
     */
    public void clear() {
        adaptee.clear();
    }
}
