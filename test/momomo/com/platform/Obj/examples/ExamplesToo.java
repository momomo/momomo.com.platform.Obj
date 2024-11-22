package momomo.com.platform.Obj.examples;

import momomo.com.Obj;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Joseph S.
 */
public class ExamplesToo {
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * @return Four in the end
     */
    public static Obj.Four<StringBuilder, String, List<String>, Boolean> example1() {
        Obj.One<String>                  a   = new Obj.One<>  ("first");
        Obj.Two<String, Integer>         b   = new Obj.Two<>  ("first", 2);
        Obj.Three<String, Integer, Long> c   = new Obj.Three<>("first", 2, 3L);
        // ... 
        
        // We can read
        String  first  = a.first;
        Integer second = b.second;
    
        // We can set 
        c.first  = "första"; // Directly
        c.second = 10;       // Directly
        c.third(11L);        // Using setter
        
        // Return four
        return new Obj.Four<>(new StringBuilder("first"), "second", new ArrayList<>(), false);
    }
    
    /**
     * Showing how to auto cast using asOne(), asTwo() ...
     */
    public static void example2() {
        Obj.Four<StringBuilder, String, List<String>, Boolean> $ = new Obj.Four<>(new StringBuilder("first"), "second", new ArrayList<>(), false);
    
        Obj.One<StringBuilder>         a = $.asOne(); // Casting from Four<> to One<>
        Obj.Two<StringBuilder, String> b = $.asTwo(); // Casting from Four<> to Two<>
    
        // Still the same instance!
        if ( $.equals(a) && $.equals(b) ) {
            System.out.println(true);
        }
    
        // All the same instance! a == b == $, just casted!
        if ( a == b && b == $ ) {
            System.out.println(true);
        }
    
        // Still the same instance!
        if ( a instanceof Obj.Four ) {
            System.out.println(true);
        }
    
        // Still the same instance!
        if ( a instanceof Obj.Three ) {
            System.out.println(true);
        }
    
        // Still the same instance!
        if ( a instanceof Obj.Two ) {
            System.out.println(true);
        }
    
        // Still the same instance!
        if ( a instanceof Obj.One ) {
            System.out.println(true);
        }
        
        $.first = new StringBuilder("Changing $.first will effectivaly change a and b as well!");
        System.out.println($.first); // All the same
        System.out.println(a.first); // All the same
        System.out.println(b.first); // All the same
    }
    
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Showing how to clone / copy to new types using toOne(), toTwo() ... 
     */
    public static void example3() {
        Obj.Four<StringBuilder, String, ArrayList<Object>, Boolean> $ = new Obj.Four<>(new StringBuilder("first"), "second", new ArrayList<>(), false);
    
        Obj.One<StringBuilder>         a = $.toOne(); // Not casting, but copying to a new instance!
        Obj.Two<StringBuilder, String> b = $.toTwo(); // Not casting, but copying to a new instance!
    
        // Not the same instance!
        if ( $.equals(a) && $.equals(b) ) {
            System.out.println(false);
        }
    
        // False!
        if ( a instanceof Obj.Four ) {
            System.out.println(false);
        }
    
        // False!
        if ( a instanceof Obj.Three ) {
            System.out.println(false);
        }
    
        // False!
        if ( a instanceof Obj.Two ) {
            System.out.println(false);
        }
    
        // True!
        if ( a instanceof Obj.One ) {
            System.out.println(true);
        }
    
        $.first = new StringBuilder("Changing $.first will NOT change a and b as well!");
        System.out.println($.first); // Changing $.first will NOT change a and b too!
        System.out.println(a.first); // "first" 
        System.out.println(b.first); // "first" 
    }
    
    /////////////////////////////////////////////////////////////////////
    // Methods returning higher order, all eventually from five() 
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Create One<> by calling higher order one two()
     */
    private static Obj.One<String> one() {
        return two();  // A two can be casted to a one
    }
    
    /**
     * Create Two<> by calling higher order one three(), four() or five()
     */
    private static Obj.Two<String, Integer> two() {
        if ( false ) return four();                 // A four can be casted to two
        if ( false ) return five();                 // A five can be casted to two
        if ( false ) new Obj.Two<>("first", 2);  // We can create it directly
        
        return three(); // A three can be casted to a two
    }
    
    /**
     * Create Three<> by calling higher order one four()
     */
    private static Obj.Three<String, Integer, Long> three() {
        return four();
    }
    
    /**
     * Create Four<> by calling higher order one five()
     */
    private static Obj.Four<String, Integer, Long, Boolean> four() {
        return five();
    }
    
    /**
     * Create Five<>
     */
    private static Obj.Five<String, Integer, Long, Boolean, LinkedHashMap<String, List<ArrayList<String>>>> five() {
        return new Obj.Five<>("first", 2, 3L, Boolean.FALSE, new LinkedHashMap<>());
    }
    
    /////////////////////////////////////////////////////////////////////
    // Using one(), two(), three(), four(), five() 
    /////////////////////////////////////////////////////////////////////
    
    /**
     * Just showing all generic types will still resolve. 
     * 
     * We return one casted to Two<> from a Five<>
     */
    public static Obj.Two<String, Integer> example5() {
        Obj.Five<String, Integer, Long, Boolean, LinkedHashMap<String, List<ArrayList<String>>>> $ = five();
        
        String                                         first  = $.first;
        Integer                                        second = $.second;
        Long                                           third  = $.third;
        Boolean                                        fourth = $.fourth;
        LinkedHashMap<String, List<ArrayList<String>>> fifth  = $.fifth;
        
        return $.asTwo();
    }
    
    
    /**
     * Casting again
     */
    public static void example6() {
        Obj.Five<String, Integer, Long, Boolean, LinkedHashMap<String, List<ArrayList<String>>>> $ = five();
        
        // Auto casting. The only major benefit of this one is that you won't have to type all of the generic signatures. 
        // If you have $ here, and you would like to express it as a Four, typing those might a be a hassle, at least when typing examples. 
        // So we provide convienience methods to do that. Intellij / Eclipse will generate the field. 
        
        Obj.One<String>                                                                          one   = $.asOne();
        Obj.Two<String, Integer>                                                                 two   = $.asTwo();
        Obj.Three<String, Integer, Long>                                                         three = $.asThree();
        Obj.Four<String, Integer, Long, Boolean>                                                 four  = $.asFour();
        Obj.Five<String, Integer, Long, Boolean, LinkedHashMap<String, List<ArrayList<String>>>> five  = $.asFive();
        
        // Yes, the last one is not required, but it looks so much more consistent for this example so we added it. 
        // It just returns this and is cheap. 
    }
    
    /**
     * 'Cloning' again
     */
    public static void example7() {
        Obj.Five<String, Integer, Long, Boolean, LinkedHashMap<String, List<ArrayList<String>>>> $ = five();
        
        // Clones. 
        Obj.One<String>                                                                          one   = $.toOne();
        Obj.Two<String, Integer>                                                                 two   = $.toTwo();
        Obj.Three<String, Integer, Long>                                                         three = $.toThree();
        Obj.Four<String, Integer, Long, Boolean>                                                 four  = $.toFour();
        Obj.Five<String, Integer, Long, Boolean, LinkedHashMap<String, List<ArrayList<String>>>> five  = $.toFive();
        
        // Note that these are all clones. Sure, we could have done:  
        Obj.Four<String, Integer, Long, Boolean> last = new Obj.Four<>($.first, $.second, $.third, $.fourth);
        // But typing that manually is a bit too much. You won't get much help from editor typing that either!  
    }
    
}
