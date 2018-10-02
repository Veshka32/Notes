import java.util.concurrent.atomic.AtomicBoolean;

public class Examples implements Runnable {
    private int someData;

    //pass info to Runnable
    Examples(int data){
        someData=data;
    }

    //Runnable - functional interface, takes no arg, void return type

    @Override
    public void run(){
        for (int i = 0; i < 5; i++) {
            System.out.println("Running Examples: "+i);
        }
    }

    static class MyThread extends Thread{
        @Override
        public void run(){
            System.out.println("Running Nythread");
        }
    }

    public static void main(String[] args) {
        //one way to define a task that will be excecute in separate Thread: extend Runnable and pass as arg to Thread
        //позволяет расширять другие классы/интерфейсы
        //отделяет "что выполнять" от "выполнения"
        //можно использовать в классах из пакета Concurrency API
        (new Thread(new Examples(1))).start();

        //another way - extend Thread and implementing run
        //полезно, если нужно задать параметры для потока - например, приоритет.
        (new MyThread()).start();


    }

}
