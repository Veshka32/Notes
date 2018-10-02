import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclilBarrierExample {
    //keep order of operation between threads

    private void firstAction(){System.out.println("firstAction");}
    private void secondAction(){
        System.out.println("Second Action");
    }

    private void thridAction(){
        System.out.println("ThridAction");
    }

    public void performOderedTask(CyclicBarrier c1, CyclicBarrier c2){
        try{
            firstAction();
            c1.await();
            secondAction();
            c2.await();
            thridAction();
        } catch (InterruptedException | BrokenBarrierException e){
            //
        }
    }

    public void performUnoderedTask(){
        firstAction();
        secondAction();
        thridAction();
    }

    public static void main(String[] args) {
        ExecutorService executorService=null;
        try{
            //set at least as many thread as arg in CyclicBarrier
            executorService=Executors.newFixedThreadPool(4);
            CyclilBarrierExample ex=new CyclilBarrierExample();

            for (int i = 0; i < 4; i++) {
                executorService.submit(()->ex.performUnoderedTask());
            }

            CyclicBarrier c1=new CyclicBarrier(4); //wait till number of await() calls=4;
            CyclicBarrier c2=new CyclicBarrier(4,()->System.out.println("action 2 is done!"));
            for (int i = 0; i <4 ; i++) {
                executorService.submit(()->ex.performOderedTask(c1,c2));
            }


        } finally {
            if (executorService!=null)
                executorService.shutdown();
        }
    }
}
