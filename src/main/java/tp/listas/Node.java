package tp.listas;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class Node<T> {

    //private final Semaphore semaphore = new Semaphore(1, true);
    private final Lock lock = new ReentrantLock();
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
        //semaphore.acquireUninterruptibly();
        lock.lock();
    }

    public void unlock(){
        //semaphore.release();
        lock.unlock();
    }
}
