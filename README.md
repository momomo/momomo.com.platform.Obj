<!---
-->

##### Intuitive library that makes it easier for you to return multiple, fully defined objects on the fly from any method, any time rather than being limited to the default maximum of one. 

##### Dependencies 
* None. Zero. **JDK5** compatible perhaps even. Not tested but should compile. Tested on **JDK15**.    

##### Maven dependencies available on maven central [search.maven.org](https://search.maven.org/search?q=com.momomo)
##### Dependency

```xml

<dependency>
    <groupId>com.momomo</groupId>
    <artifactId>momomo.com.platform.Obj</artifactId>
    <version>yet-to-be-released-coming-soon</version>
</dependency>                                                      
```                         
##### Repository
```xml
<repository>
    <id>maven-central</id>
    <url>http://repo1.maven.org/maven2</url>
</repository>
```                      

##### Our other, highlighted [repositories](https://github.com/momomo?tab=repositories)                          

* **[`momomo.com.platform.Core`](https://github.com/momomo/momomo.com.platform.Core)** Is essentially what makes the our the core of several of momomo.com's public releases and contains a bunch of Java utility.

* **[`momomo.com.platform.Lambda`](https://github.com/momomo/momomo.com.platform.Lambda)** Contains a bunch of `functional interfaces` similar to `Runnable`, `Supplier`, `Function`, `BiFunction`, `Consumer` `...` and so forth all packed in a easily accessed and understood intuitive pattern that are used plenty in our libraries. **`Lambda.V1E`**, **`Lambda.V2E`**, **`Lambda.R1E`**, **`Lambda.R2E`**, ...

* **[`momomo.com.platform.Nanotime`](https://github.com/momomo/momomo.com.platform.Nanotime)** Allows for nanosecond time resolution when asking for time from Java Runtime in contrast with **`System.currentTimeMillis()`**.

* **[`momomo.com.platform.DB.transactional.Hibernate`](https://github.com/momomo/momomo.com.platform.DB.transactional.Hibernate)** A library to execute database commands in transactions without  having to use annotations based on Hibernate libraries. No Spring!

* **[`momomo.com.platform.DB.transactional.Spring`](https://github.com/momomo/momomo.com.platform.DB.transactional.Spring)** A library to execute database commands in transactions without  having to use annotations based on Spring libraries.
          
### Background
 
At times, we would like the ability to return multiple values from a method and this library allows you to do so, currently **up to nine**. **`Nine`** should be plenty for you. If you need more, just declare a class for god sake!

We use this occasionally, ***but not a crazy amount in our code*** and often when we are still experimenting with signatures and what not and instead of declaring separate objects we might use this as a quick fix. 

With this class based library, you can ***for instance*** use:
 
* **`Obj.One<String>`**  
* **`Obj.Two<String, Integer, Long>`**  
* **`Obj.Three<String, Integer, Long>`** 
* **`Obj.Four<String, Integer, Long, Boolean>`**
* **`Obj.Five<String, Integer, Long, Boolean, LinkedHashMap<String, List<ArrayList<String>>>>`**
* ...
* **`Obj.Nine<...>`**

It also you to ***return subsets*** as it has a smart inheritance structure, since a **`Obj.Three<String, Integer, Long>`** is a also a **`Obj.Two<String, Integer>`** and a **`Obj.One<String>`**.   

We provide some utility methods to **`cast`** and **`clone`** from one to another, such as **`four.asTwo()`**, **`four.asThree()`** and **`four.toTwo()`** and **`four.toThree()`**, `...`.

Documentation is provided through comments within the class itself **[`Obj.java`](src/momomo/com/Obj.java)** which is self documented and we recommend you just start try to use it and you will immediately figure out its use. 

### Examples

Examples below can be found in **[`Examples.java`](test/momomo/com/platform/Obj/examples/Examples.java)** with highlights below:

#### Creating

```java
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
```

#### Casting

```java
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
```

#### Cloning

```java
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
```                                                                                

#### Composition

Creating a **`Obj.Three<...>`** from a lesser such as **`Obj.Two<...>`**. We do this using a constructor to avoid some **** calling a **`toTwo()`** inadvertedly if we had supplied that instead.       

```java
public static Obj.Three<Integer, Long, JFrame> three() {
    // Note, we create a Obj.Three from a Obj.Two meaning the last one (third) will be null and has to be set manually, otherwise it will remain null.  
    return new Obj.Three<Integer, Long, JFrame>( CREATE.two() ).third(new JFrame());
}

public static Obj.Four<Integer, Long, String, JFrame> five() {
    // Note, we create a Obj.Five from a Obj.Four meaning the last one (fourth) will be null and has to be set manually, otherwise it will remain null.  
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
```            

#### Reading

```java
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
```                           

#### Passing     

```java
public static void three(Obj.Three<Integer, Long, String> obj) {
    Integer first  = obj.first;
    Long    second = obj.second;
    String  third  = obj.third;
}

public static void four(Obj.Four<Integer, Long, String, String> obj) {
    Integer first  = obj.first;
    Long    second = obj.second;
    String  third  = obj.third;
}

public static void main() {
    three( new Obj.Three<>(1, 2L, "3") );
    three( new Obj.Four<> (1, 2L, "3", "4") );

    four( new Obj.Four<> (1, 2L, "3", "4") );
    four( new Obj.Five<> (1, 2L, "3", "4", "5") );
}
```     

### More examples

More examples can be found in **[`ExamplesToo.java`](test/momomo/com/platform/Obj/examples/ExamplesToo.java)**.  

### License
The full license can be found here [`MoL9`](https://raw.githubusercontent.com/momomo/momomo.com.yz.licenses/master/MoL9?raw=true?raw=true)

### Contribute
Send an email to `opensource{at}momomo.com` if you would like to contribute in any way, make changes or otherwise have thoughts and/or ideas on things to improve.
