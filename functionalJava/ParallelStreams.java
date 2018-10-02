
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStreams {
    /**There are two ways exist to create parallel streams:*/

    //to call parallelStream() method of a collection instead of stream():
    List<String> languages = Arrays.asList("java", "scala", "kotlin", "clojure", "C#");
    List<String> jvmLanguages = languages.parallelStream()
            .filter(lang -> !lang.equals("C#"))
            .collect(Collectors.toList());

    //to transform an existing stream into a parallel stream using method parallel():
    long[] numbers = LongStream.rangeClosed(0, 100_000).parallel().toArray();

    public static void main(String[] args) {
        //with parallel streams we should use forEachOrdered operation that keeps the order of the elements.
        (new ArrayList<Integer>()).parallelStream().map(Function.identity())
                .forEachOrdered(n -> System.out.print(n + " "));

        boolean isParallel=(Stream.concat(Stream.of(1).parallel(),Stream.of(2))).isParallel(); //return true, because one of them is parallel;
        boolean isParallel2=(Stream.of(new ArrayList<>()).flatMap(x->x.parallelStream())).isParallel(); //return false since flatMap always return serial stream

        //save the order, but loose performance
        Stream.of(1,2,3,4,5).parallel().forEachOrdered(System.out::println);

        //improve performance
        Stream.of(1,2,3,4,5,6,7,8).unordered().parallel().skip(2); //will skip any 2, not first and second

        //use independing operations
        Arrays.asList("str","sdfg","sdfdf").parallelStream().map(x->x.toUpperCase()).forEach(System.out::println);

        //avoid stateful operaions
        List<Integer> data=new CopyOnWriteArrayList<>();
        Stream.of(1,2,3,4,5).parallel().map(x->{data.add(x);return x;}).forEach(System.out::print); //different output



    }



}

/**There are several factors to evaluate performance of a parallel stream.
-Size of data. The more bigger size of data => the greater speedup.
-Boxing/Unboxing. Primitive values are processed faster than boxed values.
-The number of cores are available at runtime. The more available cores => the greater speedup.
-Cost per element processing. The longer each element is processed => the more efficient parallelization. But it is not recommended to use parallel stream for performing too long operations (for example, network interconnection).
-Source data structure. Usually initial data source is a collection.
- The easier it is splited into parts => the greater speedup.
For example, ArrayList, arrays and IntStream.range() constructor are good sources for data splitting because they support random access.
Other, such as LinkedList, Stream.iterate are bad sources for data splitting.
-Type of operations: stateless or stateful.
Stateless operations (examples: filter, map) are better for parallel processing than stateful operations (examples: distinct, sorted, limit)﻿
 - operations based on order(findFirs, limit, findAny,skip) may perfrom more slowly in a parallel environment;

 */