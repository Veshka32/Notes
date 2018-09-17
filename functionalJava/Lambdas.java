package stepik.functionalJava;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.*;

import static java.lang.System.out;

public class Lambdas {
    Map<String, Integer> map = new HashMap<>();
    /**
     * Functional interface is an interface with a single abstract method.
     * Static and default methods are allowed here.
     * Any functional interface can be implemented using lambda expressions, methods references or anonymous classes.
     * Examples: Runnable, Callable<V>, Comparator<T>, ActionListener and others.
     * Lambdas can access instance vatiables, static variables, effectively final method parameters and effectively final local variables.
     * Must be generic
     * With :: you can call method or constructor with appropriate number of args in lambds
     */
    //Build-in functional interfaces:

    //Supplier gets nothing, return object
    Supplier<String> supplier = String::new;
    Supplier<Integer> supplier2 = () -> 1;
    Supplier<String> supplier1 = new Supplier<String>() {
        @Override
        public String get() {
            return null;
        }
    };

    //Consumer gets value, return nothing. One method accept()
    Consumer<List<Object>> consumer = (a) -> a.forEach(Object::toString);
    Consumer<String> consumer1 = (s) -> System.out.println(s);
    Consumer<Object> consumer2 = new Consumer<Object>() {
        @Override
        public void accept(Object o) {

        }
    };

    //Biconsumer (take 2 values, return nothing
    BiConsumer<Integer, String> biConsumer = (a, s) -> map.put(s, a);

    //Predicate takes value, return boolean. One method test().
    Predicate<String> predicate = s -> s.isEmpty();
    Predicate<String> predicate1 = String::isEmpty;

    //BiPredicate (take 2 values, return boolean)
    BiPredicate<String, Integer> biPredicate = (s, i) -> s.length() == i;
    BiPredicate<String, String> biPredicate1 = String::startsWith; //(first arg for String, second for startWith )

    //Cmombining two predicates
    Predicate<String> first = s -> s.contains("first");
    Predicate<String> second = s -> s.contains("second");
    Predicate<String> firstAndSecond = first.and(second);
    Predicate<String> firstNotSecond = first.and(second.negate());

    IntPredicate isEven = z -> z % 2 == 0;     //print only those numbers which are odd or can be divided by 3 (i.e. x % 3 == 0).
    IntPredicate dividedBy3 = z -> z % 3 == 0;
    IntPredicate pred = isEven.negate().or(dividedBy3);

    //Fuction take value, return value. One method apply()
    Function<char[], String> function = (a) -> (new String(a));
    Function<Long, Long> nextEven = (a) -> (++a % 2 == 0 ? a : ++a);
    Function<List, List> duplicate = (list) -> new ArrayList<String>(new HashSet<String>(list));
    Function<Integer, Integer> abs = Math::abs;
    Function<char[], String> generator2 = String::new;

    //BiFunction (take 2 values, return one)
    BiFunction<String, String, String> biFunction = String::concat;
    BiFunction<Integer, String, String> biFunction1 = (i, s) -> i + s;

    //UnaryOperator. As function, but both parameters must be the same type. Extends Function
    UnaryOperator<String> unaryOperator = String::toUpperCase;
    //BinaryOperator
    BinaryOperator<Integer> bi = (a, b) -> (a > b ? a : b);



    public static void main(String[] args) {

        //This technique is called closure
        int x = 10;
        Function<Integer, Integer> toString = (y) -> new Integer(x + y);
        System.out.println("test closure technique:" + toString.apply(x));

        String test = "hello";
        Function<String, StringBuilder> toBuilder = (s) -> new StringBuilder(test + s);
        System.out.println("test closure technique:" + toBuilder.apply(test));

        // Collection.foreach takes Consumer and calls it for each element in the collection.
        List<String> fruits = Arrays.asList("apple", "pear", "orange", "banana");
        fruits.forEach(out::print);
        fruits.forEach(fruit -> {
            String doubleFruit = fruit.concat(fruit).toUpperCase();
            if (doubleFruit.length() > 10) {
                out.println(doubleFruit);
            }
        });

        //compose, andThen
        Function<Integer, Integer> adder = z -> z + 10;
        Function<Integer, Integer> multiplier = z -> z * 5;
        // compose: adder(multiplier(5))
        System.out.println("result: " + adder.compose(multiplier).apply(5));
        // andThen: multiplier(adder(5))
        System.out.println("result: " + adder.andThen(multiplier).apply(5));

        IntUnaryOperator mult2 = num -> num * 2;
        IntUnaryOperator add3 = num -> num + 3;

        IntUnaryOperator combinedOperator = add3.compose(mult2.andThen(add3)).andThen(mult2);
        int result = combinedOperator.applyAsInt(5);
        out.println("Result:" + result); //(3+(5*2+3))*2
    }

        public void testSAM () {
            Fun<Double, Double> fun = (x) -> x * 2 + 1;
            out.println("test Fun functional interface:" + fun.apply(0.1));

        }

        @FunctionalInterface
        public interface Fun<T, R> {
            static void doNothingStatic() {
            }

            R apply(T t);

            default void doNothingByDefault() {
            }
        }
    }
