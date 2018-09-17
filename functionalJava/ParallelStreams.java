package stepik.functionalJava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStreams {
    /**There are several ways exist to create parallel streams:*/

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
    }

}

//There are several factors to evaluate performance of a parallel stream.
//Size of data. The more bigger size of data => the greater speedup.
//Boxing/Unboxing. Primitive values are processed faster than boxed values.
//The number of cores are available at runtime. The more available cores => the greater speedup.
//Cost per element processing. The longer each element is processed => the more efficient parallelization. But it is not recommended to use parallel stream for performing too long operations (for example, network interconnection).
//Source data structure. Usually initial data source is a collection.
// The easier it is splited into parts => the greater speedup.
// For example, ArrayList, arrays and IntStream.range() constructor are good sources for data splitting because they support random access.
// Other, such as LinkedList, Stream.iterate are bad sources for data splitting.
//Type of operations: stateless or stateful.
// Stateless operations (examples: filter, map) are better for parallel processing than stateful operations (examples: distinct, sorted, limit)﻿