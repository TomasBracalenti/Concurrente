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
    public Semaphore end;

    public Scenario(int threads, int operationsPerThread, int probabilityAdd, SynchronizedList<Integer> myList) {
        this.threads = threads;
        this.operationsPerThread = operationsPerThread;
        this.probabilityAdd = probabilityAdd;
        this.myList = myList;
        end = new Semaphore(0, true);
    }

    public float run() {
        //System.out.println("Running scenario");

        long startTime = System.nanoTime();

        for (int i = 0; i < threads; i++) {
            OperationThread operationThread = new OperationThread((int) (Math.random() * 100) < probabilityAdd ? ADD : REMOVE, operationsPerThread, myList, end);
            Thread thread = new Thread(operationThread);
            thread.start();
        }

        end.acquireUninterruptibly(threads);
        long endingTime = System.nanoTime();
        return (float)(endingTime-startTime)/1000000000; 
    }
}






