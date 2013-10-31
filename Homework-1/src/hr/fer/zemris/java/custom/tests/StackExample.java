package hr.fer.zemris.java.custom.tests;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.collections.EmptyStackException;

/**
 * @author ivan
 * test for ObjectStack class
 */
public class StackExample {

    /**
     * easier printing function
     */
    public static void print( String ocuVan ) { 
        System.out.println( ocuVan );
    }    

    /**
     * easier printing function for ints
     */
    public static void printInt( int n ) {
        print( new Integer( n ).toString() );
    }
        
    /**
     * @param args ignored
     */
    public static void main(String[] args) {
        //ObjectStack stack = new ObjectStack();
    	ObjectStack stack = new ObjectStack();
        
        stack.push("gitara");
        stack.push("bubanj");

        printInt( stack.size() );   //2
        print( stack.peek().toString() );      //bubanj
        print( stack.pop().toString() );       //bubanj
        print( stack.peek().toString() );      //gitara
        System.out.println( stack.isEmpty() );  //false

        stack.clear();
        
        System.out.println( stack.isEmpty() );  //true
        
        try {
            stack.pop(); //expected exception
        } catch (EmptyStackException e) {
            print("moja iznimka se desila");
        }

    }
}
