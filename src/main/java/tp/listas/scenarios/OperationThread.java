package tp.listas.scenarios;

import tp.listas.SynchronizedList;

public class OperationThread implements Runnable {

    public SynchronizedList<Integer> list;
    public int operationType;
    public int operations;

    public OperationThread(int operationType, int operations, SynchronizedList<Integer> list) {
        this.operationType = operationType;
        this.operations = operations;
        this.list = list;
    }


    @Override
    public void run() {
        System.out.println("Running operation thread");
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

    }

    public void add() {
        System.out.println("Adding");

    }

    public void remove() {
        System.out.println("Removing");
    }
}
