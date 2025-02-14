package tp.listas;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class LocklessNode<T> {
    public T data;
    //Hash of the object
    public int key;
    public AtomicMarkableReference<LocklessNode<T>> next;

    public LocklessNode(T data) {
        this.data = data;
        this.next = new AtomicMarkableReference<LocklessNode<T>>(null, false);
        this.key = (data == null) ? 0 : data.hashCode();


    }
}
