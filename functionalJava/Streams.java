package stepik.functionalJava;

import java.math.BigInteger;
import java.util.*;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.*;

import static java.util.stream.Collectors.averagingLong;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;

public class Streams {
    /**
     * A stream is a sequence of data.
     * A stream pipeline is the operations that run on a stream to produce result
     * Source - > intermediate operations -> terminate operation
     * Stream use lazy evaluation: intermediate operations do not run until terminate one runs.
     * Intermediate returns stream; terminate return value/object. Terminate 'kill' stream.
     *
     *
     */

    //creating finite Stream
    Stream<Integer> stream=Stream.empty();
    Stream<Integer> stream1=Stream.of(1);
    Stream<Integer> stream2=Stream.of(1,2,3);

    Set<String> usefulConcepts = new HashSet<>(Arrays.asList("functions", "lazy", "immutability"));
    Stream<String> conceptsStream = usefulConcepts.stream();

    Stream<Double> doubleStream = Arrays.stream(new Double[]{1.01, 1d, 0.99, 1.02, 1d, 0.99});

    IntStream stream3 = "aibohphobia".chars(); // It returns IntStream!

    LongStream longStream = LongStream.of(111_111_111L, 333_333_333L);

    Stream<BigInteger> concatedStream = Stream.concat(Stream.empty(), Stream.empty());

    LongStream rangedStream = LongStream.rangeClosed(100_000, 1_000_000);

    //infinite Stream
    Stream<String> userStream = Stream.generate(String::new);
    DoubleStream randomStream = DoubleStream.generate(Math::random);
    IntStream oddNumbersStream = IntStream.iterate(1, x -> x + 2);

    //There are also other ways to create a stream: from a file, from I/O stream and so on.

    public static void main(String[] args) {
        List<Integer> famousNumbers = Arrays.asList(0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
        Stream<Integer> numbersStream = famousNumbers.stream();
        boolean hasValue10 = IntStream.range(1, 10).anyMatch(x -> x == 10);


        /* Terminal opertions */
        //You can use Comparator with min()/max(), return Optional
        Stream<String> stringStream=Stream.of("1","12","sdff");
        Optional<String> min=stringStream.min((s,s1)->s.length()-s1.length());
        min.ifPresent(System.out::print);

        //findAny(), findFirst()
        Stream<String> infinite=Stream.generate(()->"String");
        infinite.findAny().ifPresent(System.out::print); // break infinite stream, print "String"

        //anyMatch, allMatch(), noneMatch() take Predicate, return boolean
        infinite.anyMatch(s->s.length()==6);

        //forEach() take Consumer
        infinite.limit(10).forEach(System.out::print);

        //reduce() take initial value and BinaryOperator
        long factorialOf10=LongStream.rangeClosed(1, 10).reduce(1, (acc, elem) -> acc * elem);

        Collection<IntPredicate> predicates=new ArrayList<>();
        IntPredicate result=predicates.stream()
                .map(IntPredicate::negate)
                .reduce(n -> true, IntPredicate::and); //initial value - Predicate that is always true. Negate each predicate using a map operator and then conjunct all predicates into one using a reduce operator.

        List<Integer> numbers = Arrays.asList(1, 2, 3, 5);
        int sum = numbers.stream().reduce(0, (acc, elem) -> acc + elem);

        // collect(). First parameter - Supplier that creates an object where collect; the second is a BiConsumer that adding to collection; the third is a BiConsumer that merge 2 collections;
        Stream<String> str=Stream.of("s","sd","sd","fgfg");
        StringBuilder sb=str.collect(StringBuilder::new,StringBuilder::append,StringBuilder::append);

        List<Account> accounts = new ArrayList<>();
        Map<String, Long> numberToBalanceMap = accounts.stream()
                .collect(Collectors.toMap(Account::getId, Account::getBalance));

        /* Intermediate opertions */
        //filter()
        String text = "list of words";
        List<String> badWords = new ArrayList<>();
        Stream<String> result1 = Stream.of(text.split(" ")).distinct().filter(w -> badWords.contains(w)).sorted();
        Stream<String> result2 = Arrays.stream(text.split(" ")).filter(badWords::contains).distinct().sorted();

        IntStream one = IntStream.range(1, 5);
        IntStream two = IntStream.range(1, 5);
        IntStream result3 = IntStream.concat(one, two).filter(x -> x % 3 == 0 && x % 5 == 0).sorted().skip(2);

        long l=LongStream.rangeClosed(1, 100).filter(x -> x % 2 != 0).sum();

        //distinct(). Use equals()

        //limit() and skip()

        //map() and flatMap()

        //sorted() Can use Comparator

        //peek()

        //Working with primitives: IntStream, DoubleStream, LongStream
        Stream<String> stream4=Stream.of("sdf","sdfsf");
        IntStream toInt=stream4.mapToInt(s->s.length());

        //Use mapToInt, mapToDouble(), mapToObject(), mapToLong(), map() to create stream of certain type


        /**All stream operations are divided into two groups:

        Intermediate operations that are always lazy and returns a new stream, such as filter, map, limit, sorted, ...
        Terminal operations that returns a result or produce a side-effect, such as min, max, count, reduce, collect, ...
        Let's consider some common used operations.

        Intermediate operations
        filter returns a stream including the elements that match a predicate
        map returns a stream consisting of the elements that was obtained by applying a function (transforms each element)
        limit return a stream consisting of first N elements of this stream
        distinct returns a stream that have only unique elements according to method equals
        sorted returns a stream consisting of the elements, sorted according to natural order / the given comparator
        flatMap allows to replace a value with a stream and concatenates all streams together

        Terminal operations
        -collect(toList) returns a list from the values in a stream, in general cases collect is a more complex operation
        -toArray returns an array from the values in a stream
        -max / min returns maximum / minimum element of the stream according to the given comparator
        -count returns the count of elements in the stream
        -forEach performs an operation for each elements of the stream (be careful with side-effects!)
        -reduce combines values from the stream into a single value (aggregate value)
        -anyMatch returns true if at least one element matches a predicate (see also: allMatch, noneMatch)

        Such operations as filter, map, flatMap, reduce, forEach and some others are called higher-order functions because they accept another functions as arguments.

        For more details, see:
        https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html
        https://www.tutorialspoint.com/java8/java8_streams.htm
         */
    }


    /*Examples*/

    /**
     * Calculates the number of employees with salary >= threshold (only for 111- departments)
     */

    class Department {
        String code = "111-";
        List<Employee> employees = new ArrayList<>();

        String getCode() {
            return code;
        }

        List<Employee> getEmployees() {
            return employees;
        }
    }

    class Employee {
        int salary = 10000;

        int getSalary() {
            return salary;
        }
    }

    public static long calcNumberOfEmployees(List<Department> departments, long threshold) {
        return departments.stream()
                .filter(x -> x.getCode().startsWith("111-")).flatMap(x -> x.getEmployees().stream()).
                        filter(x -> x.getSalary() >= threshold).count();

    }

    /**
     * calculates the total sum of canceled transactions for all non-empty accounts (balance > 0)
     */
    enum State {
        CANCELED
    }

    class Transaction {
        State state = State.CANCELED;
        long sum = 0;
        Account account;

        State getState() {
            return state;
        }

        long getSum() {
            return sum;
        }

        Account getAccount(){
            return account;
        }
    }

    class Account {
        List<Transaction> trs = new ArrayList<>();
        long balance = 0;
        String id;

        long getBalance() {
            return balance;
        }

        List<Transaction> getTransactions() {
            return trs;
        }

        String getId() {
            return id;
        }
    }

    public static long calcSumOfCanceledTransOnNonEmptyAccounts(List<Account> accounts) {
        return accounts.stream()
                .filter(x -> x.getBalance() > 0)
                .flatMap(x -> x.getTransactions().stream())
                .filter(x -> x.getState() == State.CANCELED)
                .mapToLong(Transaction::getSum).sum();
    }

    void collect() {

    }

    //detect prime number
    public static boolean isPrime(final long number) {
        return !LongStream.range(2,number).anyMatch(x->number%x==0 );

    }

    /**
    Partitioning is an operation that splits your data by a predicate into map of two collections.
     The key of the map has a type Boolean.
     */
    void partition(List<Account> accounts){
        Map<Boolean, List<Account>> partByBalance = accounts.stream()
                .collect(Collectors.partitioningBy(a -> a.getBalance() >= 10000));

        long summary = accounts.stream()
                .collect(Collectors.summingLong(Account::getBalance));

        double average = accounts.stream()
                .collect(Collectors.averagingLong(Account::getBalance));

        String meganumber = accounts.stream()
                .collect(Collectors.reducing("", Account::getId, String::concat));
    }

    /**
     *Grouping is a more general operation than partitioning.
     * Instead of splitting your data into two groups (true and false), you can use any numbers of any groups.
     */
    void grouping(List<Transaction> transactions){
        Map<State, List<Transaction>> groupingByState = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getState));
    }

    /**
     *To calculate the total sums of blocked, active and removed accounts we can use downstream collectors like this:
     */
    void downstreams(List<Transaction> accounts){
        Map<State, Long> sumByStates = accounts.stream()
                .collect(Collectors.groupingBy(Transaction::getState, Collectors.summingLong(Transaction::getSum)));
    }
    //result like { REMOVED=0, ACTIVE=24133, BLOCKED=60000 }

    //collector that evaluates the product of squares of integer numbers in a Stream<Integer>.
    long val = (new ArrayList<Integer>()).stream().collect(Collectors.reducing(1,(acc,elem)->acc*elem*elem));

    //collector that partitions all words in a stream into two groups: palindromes (true) and usual words (false).
    Map<Boolean, List<String>> palindromeOrNoMap =
            Arrays.stream(new String[5])
                    .collect(Collectors.partitioningBy(s->((new StringBuilder(s).reverse().toString()).equals(s))?true:false));

    //collector that calculates the total sum of transactions (long type, not integer) by each account (i.e. by account number).
    // The collector will be applied to a stream of transactions.
    List<Transaction> transactions = new ArrayList<>();
    Map<String, Long> totalSumOfTransByEachAccount =
            transactions.stream()
                    .collect(Collectors.groupingBy(tr->tr.getAccount().getId(),Collectors.summingLong(Transaction::getSum)));

    //collector that calculates how many times was clicked each url by users
    class LogEntry{
        String url;
        String getUrl(){
            return url;
        }
    }

    Map<String, Long> clickCount =(new ArrayList<LogEntry>()).stream()
                    .collect(Collectors.groupingBy(LogEntry::getUrl,Collectors.summingLong(x->1)));
}
