import java.util.Random;
import java.util.concurrent.*;

public class ForkAndJoin extends RecursiveTask<Double> {
    /**
     * The class should extend RecursiveTask(compute() returns Generic) or RecursiveAction (compute() reruns void)
     * fork() causes a new task to be submitted to the pool, just like submit() in executorService;
     * join() causes the current thread wait for result  of a subtask;
     */

    private int start, end;
    private Double[] weights;

    public ForkAndJoin(Double weight[], int start, int end) {
        this.start = start;
        this.end = end;
        this.weights = weight;
    }

    @Override
    protected Double compute() {
        if (end - start <= 3) {
            double sum = 0;
            for (int i = start; i < end; i++) {
                weights[i] = (double) new Random().nextInt(100);
                sum++;
            }
            return sum;
        } else {
            int middle = start + ((end - start) / 2);
            RecursiveTask<Double> otherTask = new ForkAndJoin(weights, start, middle);
            //instructs the fork/join framework to complete the task in a separate thread
            otherTask.fork();
            //join() causes the current thread to wait for result
            //fork()must be called before current thread starts subtask and join()must be called after it finish retrieving result
            return new ForkAndJoin(weights, middle, end).compute() + otherTask.join();


//            //single thread vatioations
//            RecursiveTask<Double> task1=new ForkAndJoin(weights,0,middle);
//            Double sum1=task1.fork().join();
//            return new ForkAndJoin(weights,middle,end).compute()+sum1;
        }

    }

    public static void main(String[] args) {
        Double[] weights = new Double[10];
        //Create task, pool, start task;
        ForkJoinTask<Double> task = new ForkAndJoin(weights, 0, weights.length);
        ForkJoinPool pool = new ForkJoinPool();
        Double sum = pool.invoke(task);
        System.out.println(sum);

        ExecutorService x = Executors.newFixedThreadPool(2);
        Future<?> future = x.submit(() -> {
            synchronized (pool) {
                synchronized (task) {
                    System.out.println("dfgfdg");
                }
            }
        });
    }
}
