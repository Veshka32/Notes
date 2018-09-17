package stepik.functionalJava;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class Optionals {
    /**
     * One of monad implementations in Java
     *
     */

    public static Optional<Double> average(int... scores){
        if (scores.length==0) return Optional.empty();
        int sum=Arrays.stream(scores).sum();
        return Optional.of((double)sum/scores.length);
    }

    public static void readOptional(Optional optional){
        if (optional.isPresent()) System.out.println(optional.get());
        System.out.println(optional.get()); //can get NoSuchElementException
    }

    public static void createOptional(Object value) throws Throwable {
        //return value==null?Optional.empty():Optional.of(value);
        //same as
        Optional optional=Optional.ofNullable(value);
        optional.ifPresent(System.out::println); //if null,do nothing, otherwise print
        System.out.println(optional.orElse(0)); //if null, print 0
        System.out.println(optional.orElseGet(()->Math.random())); //if null, get random
        System.out.println(optional.orElseThrow(()->new IllegalStateException()));
    }


}
