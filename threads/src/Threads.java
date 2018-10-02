import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.System.out;

public class Threads {


    public static void main(String[] args) {
        Thread current = Thread.currentThread(); //текущий поток
        long id = current.getId();
        out.println(current.getName());
        out.println(current.getPriority());
        try {
            Thread.sleep(5000); //остановить можно только самого себя
        } catch (InterruptedException e) {
        }

        current.interrupt(); //разбудить другой поток. Если он спит, то будет исключение InterruptedException. Если нет, то у него изменится флаг isInterrupted(); чтоб прерваться, должен сам себя спросить
        //Если надо остановить текущий поток до окончания другого потока thread, то в текущем надо вызвать thread.join().

        current.isInterrupted();
        Lock lock = new ReentrantLock();
        lock.lock();//?
        lock.unlock(); //?

        Runtime.getRuntime().availableProcessors(); //number of processors available to the JVM

    }

}
