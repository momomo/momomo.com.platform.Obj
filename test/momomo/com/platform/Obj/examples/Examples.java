package momomo.com.platform.Obj.examples;

import momomo.com.Obj;

import javax.swing.*;
import java.util.jar.JarInputStream;

/**
 * @author Joseph S.
 */
public class Examples {
    
    /////////////////////////////////////////////////////////////////////
    
    public static final class CREATE {
        
        public static Obj.Two<Integer, Long> two() {
            return new Obj.Two<>(1, 2L);
        }
        
        public static Obj.Three<Integer, Long, String> three() {
            return new Obj.Three<>(1, 2L, "3");
        }
    
        public static Obj.Four<Integer, Long, String, String> four() {
            return new Obj.Four<>(1, 2L, "3", "4");
        }
    
        public static void main() {
            Obj.One<String>                         one   = new Obj.One<>  ("1");
            Obj.Two<String, Integer>                two   = new Obj.Two<>  ("1", 2);
            Obj.Three<String, Integer, Long>        three = new Obj.Three<>("1", 2, 3L);
            Obj.Four<String, Integer, Long, String> four  = new Obj.Four<> ("1"); four.second(2).third(3L).fourth("4");
            
            two = new Obj.Three<>("1");
            two = new Obj.Four<> ("1");
            two = new Obj.Five<> ("1");
            two = three; 
            two = four;
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public static final class CAST {
        
        public static Obj.Two<Integer, Long> two() {
            return CREATE.four().asTwo();    // Same instance, just casted
        }
    
        public static Obj.Three<Integer, Long, String> three() {
            return CREATE.four();            // Same instance, just casted though method inference
        }
    
        public static void main() {
            Obj.Four<Integer, Long, String, String> four = CREATE.four();
        
            // All casted to lower ones
            Obj.One<Integer>                 one   = four.asOne();
            Obj.Two<Integer, Long>           two   = four.asTwo();
            Obj.Three<Integer, Long, String> three = four.asThree();
        
            if (one == two && two == three && three == four) {
                System.out.println("The universe is working!");
            }
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public static final class CLONE {
        public static Obj.Two<Integer, Long> two() {
            return CREATE.four().toTwo();    // A new instance, objects are copied over  
        }
    
        public static Obj.Three<Integer, Long, String> three() {
            return CREATE.four().toThree();  // A new instance, objects are copied over  
        }
    
        public static void main() {
            Obj.Four<Integer, Long, String, String> four = CREATE.four();
        
            // All copied / cloned to new instances
            Obj.One<Integer>                 one   = four.toOne();
            Obj.Two<Integer, Long>           two   = four.toTwo();
            Obj.Three<Integer, Long, String> three = four.toThree();
        
            if (one == two && two == three && three == four) {
                System.out.println("The universe is collapsing!");
            }
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public static final class COMPOSITION {
        public static Obj.Three<Integer, Long, JFrame> three() {
            // Note, we create a Return.Three from a Return.Two meaning the last one (third) will be null and has to be set manually, otherwise it will remain null.  
            return new Obj.Three<Integer, Long, JFrame>( CREATE.two() ).third(new JFrame());
        }
        
        public static Obj.Four<Integer, Long, String, JFrame> five() {
            // Note, we create a Return.Five from a Return.Four meaning the last one (fourth) will be null and has to be set manually, otherwise it will remain null.  
            return new Obj.Four<Integer, Long, String, JFrame>( CREATE.three() ).fourth(new JFrame());
        }
    
        public static void main() {
            Obj.Two<Integer, Long> two = CREATE.two();
    
            Obj.Three<Integer, Long, JFrame>                three = new Obj.Three<>(two);
            Obj.Four<Integer, Long, JFrame, JarInputStream> four  = new Obj.Four<>(three);
            
            if ( three.third == null && four.third == null && four.fourth == null ) {
                System.out.println("The universe is working!");
            }
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public static final class READ {
        public static void three() {
            Obj.Three<Integer, Long, String> three = CREATE.three();
        
            Integer first  = three.first;
            Long    second = three.second;
            String  third  = three.third();
        }
    
        public static Obj.Two<String, String> four() {
            Obj.Four<Integer, Long, String, String> four = CREATE.four();
    
            return new Obj.Two<>( four.first + "::" + four.second, four.third() + "::" + four.fourth() );
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    
    public static final class PASSING {
        public static void three(Obj.Three<Integer, Long, String> param) {
            Integer first  = param.first;
            Long    second = param.second;
            String  third  = param.third;
        }
    
        public static void four(Obj.Four<Integer, Long, String, String> param) {
            Integer first  = param.first;
            Long    second = param.second;
            String  third  = param.third;
        }
    
        public static void main() {
            three( new Obj.Three<>(1, 2L, "3") );
            three( new Obj.Four<> (1, 2L, "3", "4") );
    
            four( new Obj.Four<> (1, 2L, "3", "4") );
            four( new Obj.Five<> (1, 2L, "3", "4", "5") );
        }
    }
    
    /////////////////////////////////////////////////////////////////////
    
}
