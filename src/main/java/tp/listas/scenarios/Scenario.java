package tp.listas.scenarios;
import tp.listas.SynchronizedList;

public class Scenario implements Runnable {

    public final SynchronizedList<Integer> myList;
    public static final int ADD = 0;
    public static final int REMOVE = 1;

    public int threads;
    public int operationsPerThread;
    public int probabilityAdd;

    public Scenario(int threads, int operationsPerThread, int probabilityAdd, SynchronizedList<Integer> myList) {
        this.threads = threads;
        this.operationsPerThread = operationsPerThread;
        this.probabilityAdd = probabilityAdd;
        this.myList = myList;
    }

    public void run() {
        System.out.println("Running scenario");

        for (int i = 0; i < threads; i++) {
            OperationThread operationThread = new OperationThread((int) (Math.random() * 100) < probabilityAdd ? ADD : REMOVE, operationsPerThread, myList);
            Thread thread = new Thread(operationThread);
            thread.start();
        }

    }
}






