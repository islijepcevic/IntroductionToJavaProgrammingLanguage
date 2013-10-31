package hr.fer.zemris.java.custom.collections;

/**
 * @author Ivan Slijepcevic
 * collection class based on indexed array
 * duplicate elements are allowed
 * null references are not allowed
 */
public class ArrayBackedIndexedCollection {

    private int size;
    private int capacity;
    private Object[] elements;

    /**
     * the default constructor
     * sets capacity to 16 elements
     */
    public ArrayBackedIndexedCollection() {
        size = 0;
        createArray( 16 );
    }

    /**
     * constructor that accepts wanted capacity
     * @param initCapacity wanted capacity - must not be less then 1
     */
    public ArrayBackedIndexedCollection( int initCapacity ) {
        size = 0;
        createArray( initCapacity );
    }


    /**
     * Creates new array and sets object's capacity
     * @param capacity capacity to allocate and set
     */
    private void createArray( int capacity ) {
        elements = allocate( capacity );
        this.capacity = capacity;
    }

    /**
     * method for resizing object's array
     * @param capacity new capacity
     */
    private void resizeArray( int capacity ) {
        elements = reallocate( elements, capacity );
        this.capacity = capacity;

        assert capacity >= size : "resize failed";
    }

    /**
     * method for allocating new object of wanted size
     * @param capacity size of the created array
     * @return reference to newly allocated array
     */
    private Object[] allocate( int capacity ) throws IllegalArgumentException {
        if (capacity < 1) {
            throw new IllegalArgumentException();
        }
        return new Object[ capacity ];
    }

    /**
     * method for reallocating the array
     * all elements up to the index capacity are saved
     * @param array array to be reallocated
     * @param capacity new capacity
     * @return reference to reallocated array
     */
    private Object[] reallocate( Object[] array, int capacity ) {
        // allocating new array
        Object[] newArray = allocate( capacity );

        // setting bound for copying
        int bound = array.length;
        if (capacity < bound) {
            bound = capacity;
        }

        // copying old array to the new array
        for (int i = 0; i < bound; i++) {
            newArray[i] = array[i];
        }

        return newArray;
    }

    /**
     * Method that indicates if the collection is logically empty
     * @return true if empty
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Method that indicates the logical size of the collection
     * @return logical size
     */
    public int size() {
        return size;
    }

    /**
     * Adds element into the first free space in the collection
     * @param value element to be added - must not be null
     */                                
    public void add( Object value ) throws IllegalArgumentException {
        // precondition
        if (value == null) {
            throw new IllegalArgumentException();
        }

        // is resize needed?
        if (size == capacity) {
            resizeArray( capacity * 2 );
        }

        assert capacity > size : "Reallocation call failed in add!";

        // add the value
        elements[size] = value;
        size++;
    }

    /**
     * Fetch the element at given position
     * @param index index of element to return, index must be between 0 and size-1
     * @return object at given index
     */
    public Object get( int index ) throws IndexOutOfBoundsException {
        // preconditions
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        // getting the object
        return elements[index];
    }

    /**
     * Removes object at given index
     * @param index needs to be between 0 and size-1
     */
    public void remove( int index ) throws IndexOutOfBoundsException {
        // preconditions
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        // removing the object
        size--;
        for (int i = index; i < size; i++) {
            elements[i] = elements[i+1];
        }
        elements[size + 1] = null;
    }

    /**
     * Inserts given value to the wanted position
     * @param value element to insert
     * @param position index to be inserted to, needs to be between 0 and size
     */
    public void insert( Object value, int position ) throws IndexOutOfBoundsException {
        // preconditions
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException();
        }

        // inserting
        Object savedObject = null;
        Object insertObject = value;
        // propagate the insertion
        for (int i = position; i < size; i++ ) {
            savedObject = elements[i];
            elements[i] = insertObject;
            insertObject = savedObject;
        }
        // call add that handles the size and reallocation if needed
        add( insertObject );
    }

    /**
     * Searches the collection and returns the index of given value based on 
     * equals method
     * @param value element searched for
     * @return index of given value, -1 if element is not found
     */
    public int indexOf( Object value ) {
        for (int i = 0; i < size; i++ ) {
            if (value.equals( elements[i] )) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Indicates if collection contains given value
     * @param value value to check
     * @return true if value is present in collection
     */
    public boolean contains( Object value ) {
        for (int i = 0; i < size; i++ ) {
            if (value.equals( elements[i] )) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes all elements from collection
     */
    public void clear() {
        for (int i = size - 1; i >= 0; i--, size--) {
            elements[i] = null;
        }
        assert size == 0 : "size is not null after clear()";
    }

}
