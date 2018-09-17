package stepik.functionalJava;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Monads {
    /**
     * Java 8 doesn't have a built-in general monad interface, but it has Stream<T> and Optional<T> classes with the same methods (and a lot of others).
     * Both of them may be considered as monads.
     * Any monad has two operations: bind (also known as flatMap) and unit (also known as pure).
     */
    interface Monad<T> {

        /**
         * Takes a value T and returns Monad<T> wrapping the value
         */
        Monad<T> unit(T value);

        /**
         * Takes a function f: T -> Monad<U> and returns a Monad<U>
         */
        <U> Monad<U> bind(Function<? super T, Monad<U>> f);

        /**
         * Takes a function f: T -> U and returns a Monad<U>
         * May be defined as a combination of unit and bind
         */
        <U> Monad<U> map(Function<? super T, ? extends U> f);

        /**
         * Returns the value back to the world
         */
        T get();
    }

    /**
     * For example, we have three classes.
     * A user may ﻿have an account and may not be. It's strange, but an account may have null in the bank field and a bank ﻿may have no name.
     */
    class User {
        List<Account> accounts;
        Account account;
        String login;

        List<Account> getAccounts(){
            return accounts;
        }

        Account getAccount(){
            return account;
        }

        String getLogin(){
            return login;
        }
    }

    class Account {
        Bank bank;
        long balance;

        Bank getBank(){
            return bank;
        }
        long getBalance(){
            return balance;
        }
    }

    class Bank {
        String name;

        String getName(){
            return name;
        }
    }

    //to avoid nulls using Optional monad
    public static String getBankNameWhereUserHasAccount2(User user) {
        return Optional.ofNullable(user)
                .map(User::getAccount)
                .map(Account::getBank)
                .map(Bank::getName)
                .orElse("<No bank found>");
    }

    //then
    public static void main(String[] args) {
        final List<User> users = new ArrayList<>();
        final Optional<Account> optAccount = findAccountOfUserWithBalance(users, 3581321);
        optAccount.ifPresent(System.out::println);
    }

    //et's write a method that finds an account of a user with the given balance. A user can have more than one account.
    public static Optional<Account> findAccountOfUserWithBalance(List<User> users, long balance) {
        return users.stream()
                .flatMap(u -> u.getAccounts().stream())
                .filter(account -> account.getBalance() == balance)
                .findAny();
    }


    //if no user with such login, return nothing
    public static Optional<User> findUserByLogin(String login, Set<User> users) {
        return users.stream().filter(x->x.getLogin().equals(login)).findAny();

    }

    //finding an user by their login, write a new method printBalanceIfNotEmpty(String userLogin)that prints an account balance for an existing user if `balance > 0`.
    public static void printBalanceIfNotEmpty(String userLogin,Set<User> users) {
       Optional<User> user=findUserByLogin(userLogin,users);
       user.ifPresent(x->Optional.ofNullable(x)
               .map(User::getAccount)
               .map(Account::getBalance)
               .ifPresent(balance->{if (balance>0) System.out.println(userLogin+": "+balance);}));
    }


    /**
     * Another monad in Java8: Optional, Stream; CompletableFuture<T> (Promise monad);
     */

}
