package tp.listas;

import java.util.concurrent.atomic.AtomicMarkableReference;

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
        while (true) {
            LocklessNode<T> nodes[] = find(data);
            LocklessNode<T> pred = nodes[0];
            LocklessNode<T> curr = nodes[1];
            LocklessNode<T> succ = nodes[2];
            if (curr != null)
                return false;
            LocklessNode<T> node = new LocklessNode<T>(data);
            node.next = new AtomicMarkableReference<LocklessNode<T>>(succ,false);
            if (pred.next.compareAndSet(succ, node, false, false))
                return true;
        }
    }

    public boolean remove(T data) {
        if (data == null) {
            return false;
        }
        while (true) {
            LocklessNode<T> nodes[] = find(data);
            LocklessNode<T> pred = nodes[0];
            LocklessNode<T> curr = nodes[1];
            LocklessNode<T> succ = nodes[2];
            if (curr == null){
                return false;
            }
            if (!curr.next.attemptMark(succ,true))
                continue;
            pred.next.compareAndSet(curr, succ, false, false);
            return true;
        }
    }

    private LocklessNode<T>[] find(T data) {
        LocklessNode<T> nodes[] = new LocklessNode[3];
        boolean[] cmark = new boolean[1];
        int key = data.hashCode();
        tryAgain: while (true) {
            nodes[0] = head;
            nodes[1] = nodes[0].next.getReference();
            while (true) {
                if (nodes[1] == tail){
                    nodes[1] = null;
                    nodes[2] = tail;
                    return nodes;
                }
                nodes[2] = nodes[1].next.get(cmark);
                int ckey = nodes[1].key;
                if (!cmark[0]) {
                    if (nodes[1].data == data){
                        return nodes;
                    }
                    else
                        if (ckey <= key){
                            nodes[0] = nodes[1];
                            nodes[1] = nodes[0].next.getReference();
                        }
                        else{
                            nodes[2] = nodes[1];
                            nodes[1] = null;
                            return nodes;
                        }
                }
                else {
                    if(nodes[0].next.compareAndSet(nodes[1], nodes[2], false, false)){
                        nodes[1] = nodes[2];
                    }   
                    else
                        continue tryAgain;
                }
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