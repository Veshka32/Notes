import java.util.concurrent.*;

public class Excecutor {
    //ExecutorService is a interface witch creates and manages threads. Extends Executor interface
    public static void main(String[] args) throws InterruptedException,ExecutionException{
        ExecutorService service=null;

        try{
            //При выполнении первого задания ThreadExecutor создает не скрытый поток: пока ThreadExecutor не будет выключен shutdown(), приложение не сможет завершиться.
            service= Executors.newSingleThreadExecutor();
            service.execute(()->System.out.println("start new thread")); //void return type
            service.execute(()->{
                for (int i = 0; i <10 ; i++) {
                    System.out.println(i);
                }
            });
            service.submit(()->System.out.println("Submit Runnable"));
            Future<?> future=service.submit(()->{for(int i=0;i<10;i++);});
            future.get(10,TimeUnit.SECONDS); //check result after 10 sec
            System.out.println("Reached!");
            System.out.println("End try block");
        } catch (TimeoutException e){
            System.out.println("Not reached in time");
        }finally {
            if (service!=null) service.shutdown(); //important!
        }
    }

    public void all(){
        ExecutorService single=Executors.newSingleThreadExecutor();
        ScheduledExecutorService scheduled=Executors.newSingleThreadScheduledExecutor();
        ExecutorService cashed=Executors.newCachedThreadPool(); //create a pool that creates new thread and reuse old when they available
        ExecutorService fixed=Executors.newFixedThreadPool(5); //reused fixed number of threads
        ScheduledExecutorService fixedSheduled=Executors.newScheduledThreadPool(5); //
    }
}
