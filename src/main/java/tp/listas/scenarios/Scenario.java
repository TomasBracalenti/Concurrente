package tp.listas.scenarios;

import java.util.concurrent.Semaphore;
import tp.listas.SynchronizedList;

public class Scenario {

    public final SynchronizedList<Integer> myList;
    public static final int ADD = 0;
    public static final int REMOVE = 1;

    public int threads;
    public int operationsPerThread;
    public int probabilityAdd;
    public Semaphore start;
    public Semaphore end;

    public Scenario(int threads, int operationsPerThread, int probabilityAdd, SynchronizedList<Integer> myList) {
        this.threads = threads;
        this.operationsPerThread = operationsPerThread;
        this.probabilityAdd = probabilityAdd;
        this.myList = myList;
        start = new Semaphore(0, true);
        end = new Semaphore(0, true);
    }

    public float run() {
        //System.out.println("Running scenario");
        try {
            Thread.sleep(4000);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        for (int i = 0; i < threads; i++) {
            OperationThread operationThread = new OperationThread((int) (Math.random() * 100) < probabilityAdd ? ADD : REMOVE, operationsPerThread, myList, start, end);
            Thread thread = new Thread(operationThread);
            thread.start();
        }

        long startTime = System.nanoTime();
        start.release(threads);
        end.acquireUninterruptibly(threads);
        long endingTime = System.nanoTime();
        return (float)(endingTime-startTime)/1000000000; 
    }
}






