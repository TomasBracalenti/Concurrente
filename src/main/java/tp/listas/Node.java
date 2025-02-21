package tp.listas;

import java.util.concurrent.Semaphore;

public class Node<T> {

    private final Semaphore semaphore = new Semaphore(1, true);
    public T data;
    //Hash of the object
    public int key;
    public Node<T> next;

    public Node(T data) {
        this.data = data;
        this.next = null;
        this.key = (data == null) ? 0 : data.hashCode();


    }

    public void lock(){
        semaphore.acquireUninterruptibly();
    }

    public void unlock(){
        semaphore.release();
    }
}
