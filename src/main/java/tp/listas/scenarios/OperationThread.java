package tp.listas.scenarios;

import java.util.concurrent.Semaphore;
import tp.listas.SynchronizedList;

public class OperationThread implements Runnable {

    public SynchronizedList<Integer> list;
    public int operationType;
    public int operations;
    public Semaphore end;

    public OperationThread(int operationType, int operations, SynchronizedList<Integer> list, Semaphore end) {
        this.operationType = operationType;
        this.operations = operations;
        this.list = list;
        this.end = end;
    }


    @Override
    public void run() {
        //System.out.println("Running operation thread");
        try{
            for (int i = 0; i < operations; i++) {
                if (operationType == Scenario.ADD) {
                    add();
                } else {
                    remove();
                }
                int sleepTime = (int) (Math.random() * 300);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            
            }
        } finally {
            end.release();
        }
    }

    public void add() {
        //System.out.println("Adding");
        list.add(1);
    }

    public void remove() {
        //System.out.println("Removing");
        list.remove(1);
    }
}
