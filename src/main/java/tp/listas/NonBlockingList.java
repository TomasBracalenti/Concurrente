package tp.listas;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class NonBlockingList<T> implements SynchronizedList<T> {

    public LocklessNode<T> head;
    public LocklessNode<T> tail;

    public NonBlockingList() {
        head = new LocklessNode<T>(null);
        head.key = Integer.MIN_VALUE;
        tail = new LocklessNode<T>(null);
        tail.key = Integer.MAX_VALUE;
        head.next = new AtomicMarkableReference<LocklessNode<T>>(tail, false);
    }

    class Window {
        public LocklessNode<T> pred, curr;

        Window(LocklessNode<T> myPred, LocklessNode<T> myCurr) {
            pred = myPred;
            curr = myCurr;
        }
    }

    Window find(LocklessNode<T> head, int key) {
        LocklessNode<T> pred = null, curr = null, succ = null;
        boolean[] marked = {false};
        boolean snip;
        retry:
        while (true) {
            pred = head;
            curr = pred.next.getReference();
            while (true) {
                succ = curr.next.get(marked);
                while (marked[0]) {
                    snip = pred.next.compareAndSet(curr, succ, false, false);
                    if (!snip)
                        continue retry;
                    curr = succ;
                    succ = curr.next.get(marked);
                }
                if (curr.key >= key)
                    return new Window(pred, curr);
                pred = curr;
                curr = succ;
            }
        }
    }

    public boolean add(T item) {
        int key = item.hashCode();
        while (true) {
            Window window = find(head, key);
            LocklessNode<T> pred = window.pred, curr = window.curr;
            if (curr.key == key) {
                return false;
            } else {
                LocklessNode<T> node = new LocklessNode<T>(item);
                node.next = new AtomicMarkableReference<LocklessNode<T>>(curr, false);
                if (pred.next.compareAndSet(curr, node, false, false)) {
                    return true;
                }
            }
        }
    }

    public boolean remove(T item) {
        int key = item.hashCode();
        boolean snip;
        while (true) {
            Window window = find(head, key);
            LocklessNode<T> pred = window.pred, curr = window.curr;
            if (curr.key != key) {
                return false;
            } else {
                LocklessNode<T> succ = curr.next.getReference();
                snip = curr.next.compareAndSet(succ, succ, false, true);
                if (!snip)
                    continue;
                pred.next.compareAndSet(curr, succ, false, false);
                return true;
            }
        }
    }

    public void print() {
        LocklessNode<T> curr = head.next.getReference();
        while (curr != tail) {
            System.out.println(curr.data);
            curr = curr.next.getReference();
        }
    }
}
