package tp.listas;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class Node<T> {

    private final Lock lock = new ReentrantLock();
    public T data;
    public int key;
    public Node<T> next;

    public Node(T data) {
        this.data = data;
        this.next = null;
        this.key = (data == null) ? 0 : data.hashCode();
    }

    public void lock(){
        lock.lock();
    }

    public void unlock(){
        lock.unlock();
    }
}
