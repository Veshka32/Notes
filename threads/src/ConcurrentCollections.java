import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConcurrentCollections {
    public static void main(String[] args) throws InterruptedException{
        Set<String> set=new ConcurrentSkipListSet<>();
        for (String s:set)
            set.remove(s); //can do this with concurrent collection

        ConcurrentMap<String,String> map=new ConcurrentHashMap<>();
        Map<String,String> map1=new ConcurrentHashMap<>();
        Queue<Integer> queue=new ConcurrentLinkedQueue<>();
        Deque<Integer> deque=new ConcurrentLinkedDeque<>();

        BlockingQueue<Integer> blockingQueue=new LinkedBlockingQueue<>();
        boolean result=blockingQueue.offer(1,3,TimeUnit.SECONDS); //adds item to the queue waiting 3 sec, if no space is available - return false;
        Integer result1=blockingQueue.poll(1,TimeUnit.SECONDS); //remove item waiting 1 sec, return null if item is not available

        BlockingDeque<Integer> blockingDeque=new LinkedBlockingDeque<>();
        result=blockingDeque.offerFirst(1);
        result=blockingDeque.offerLast(1);
        result1=blockingDeque.poll(1,TimeUnit.SECONDS);
        result1=blockingDeque.pollLast(1,TimeUnit.SECONDS);

        //SkipList - same as sorted TreeSet, TreeMap
        //CopyOnWriteArrayList, CopyOnWriteArraySet - хороши для частого чтения и редкой вставки.
        // При вставке нового элемента создается новая копия, а другой поток работает со старой копией.

        List<Integer> list=new CopyOnWriteArrayList<>();
        for (Integer i:list
             ) {
            System.out.println(i);
            list.add(5);
        }
        System.out.println(list.size()); //prints 6
    }
}
