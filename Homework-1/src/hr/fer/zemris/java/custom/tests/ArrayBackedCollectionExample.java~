package hr.fer.zemris.java.custom.tests; import
hr.fer.zemris.java.custom.collections.ArrayBackedIndexedCollection;
/** 
 * testing class for ArrayBackedIndexedColledtion
 * @author Ivan Slijepcevic 
 */
public class ArrayBackedCollectionExample {

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
     * @param args ignores args
     */
    public static void main(String[] args) {
        ArrayBackedIndexedCollection col = new 
                                        ArrayBackedIndexedCollection(2);
        col.add(new Integer(20));
        
        col.add("New York");

        col.add("San Francisco"); // here the internal array is reallocated
        
        System.out.println(col.contains("New York")); // writes: true

        col.remove(1); // removes "New York"; shifts "San Francisco" to position 1
        System.out.println(col.get(1)); // writes: "San Francisco"
        System.out.println(col.size()); // writes: 2

        col.insert( "Zagreb", 1 );
        printInt( col.indexOf( "San Francisco" ) );
        for ( int i = 1; i < col.size(); i++ ) {
            String s = col.get(i).toString();
            print( s );
        }

        col.clear();
        printInt( col.size() );

        /*
        ArrayBackedIndexedCollection c2;
        c2.get(0);
        */
    }

}
