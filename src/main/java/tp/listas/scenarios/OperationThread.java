package tp.listas.scenarios;

import java.util.concurrent.Semaphore;

import tp.listas.SynchronizedList;

public class OperationThread implements Runnable {

    public SynchronizedList<Integer> list;
    public int operationType;
    public int operations;
    public Semaphore start;
    public Semaphore end;

    public OperationThread(int operationType, int operations, SynchronizedList<Integer> list, Semaphore start, Semaphore end) {
        this.operationType = operationType;
        this.operations = operations;
        this.list = list;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        start.acquireUninterruptibly();
        try {
            for (int i = 0; i < operations; i++) {
                if (operationType == Scenario.ADD) {
                    add();
                } else {
                    remove();
                }
            }
        } finally {
            end.release();
        }
    }

    public void add() {
        list.add(Integer.valueOf((int) (Math.random() * 1000)));
    }

    public void remove() {
        list.remove(Integer.valueOf((int) (Math.random() * 1000)));
    }
}
