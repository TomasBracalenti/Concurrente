package tp.listas;

import java.util.concurrent.atomic.AtomicMarkableReference;

import tp.listas.NonBlockingList.Window;

public class ModifiedNonBlockingList<T> implements SynchronizedList<T> {

    public LocklessNode<T> head;
    public LocklessNode<T> tail;

    public ModifiedNonBlockingList() {
        head = new LocklessNode<T>(null);
        head.key = Integer.MIN_VALUE;
        tail = new LocklessNode<T>(null);
        tail.key = Integer.MAX_VALUE;
        head.next = new AtomicMarkableReference<LocklessNode<T>>(tail, false);
    }

    public boolean add(T data) {
        if (data == null) {
            return false;
        }
        int key = data.hashCode();
        while (true) {
            LocklessNode<T> nodes[] = find(data);
            LocklessNode<T> pred = nodes[0], curr = nodes[1];
            if (curr.key == key) {
                return false;
            } else {
                LocklessNode<T> node = new LocklessNode<T>(data);
                node.next = new AtomicMarkableReference<LocklessNode<T>>(curr, false);
                if (pred.next.compareAndSet(curr, node, false, false)) {
                    return true;
                }
            }
        }
    }

    public boolean remove(T data) {
        if (data == null) {
            return false;
        }
        int key = data.hashCode();
        boolean snip;
        while (true) {
            LocklessNode<T> nodes[] = find(data);
            LocklessNode<T> pred = nodes[0], curr = nodes[1];
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

    private LocklessNode<T>[] find(T data) {
        LocklessNode<T> pred = null, curr = null, succ = null;
        boolean[] marked = {false};
        boolean snip;
        int key = data.hashCode();
        retry: while (true) {
            pred = head;
            curr = pred.next.getReference();
            while (true) {
                succ = curr.next.get(marked);
                while (marked[0]) {
                    snip = pred.next.compareAndSet(curr, succ, false, false);
                    if (!snip) continue retry;
                    curr = succ;
                    succ = curr.next.get(marked);
                }
                if (curr.key >= key){
                    LocklessNode<T> nodes[] = new LocklessNode[2];
                    nodes[0] = pred; nodes[1] = curr;
                    return nodes;
                }
                pred = curr;
                curr = succ;
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