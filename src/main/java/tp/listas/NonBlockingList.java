package tp.listas;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class NonBlockingList<T> implements SynchronizedList<T>{
    public LocklessNode<T> head;
    public LocklessNode<T> tail;

    public NonBlockingList() {
        head = new LocklessNode<T>(null);
        tail = new LocklessNode<T>(null);
        head.next = new AtomicMarkableReference<LocklessNode<T>>(tail, false);
    }

    public boolean add(T data) {
        if (data == null) {
            return false;
        }
        while (true) {
            LocklessNode<T> nodes[] = oldFind(data);
            LocklessNode<T> pred = nodes[0];
            LocklessNode<T> curr = nodes[1];
            LocklessNode<T> succ = nodes[2];
            if (curr != null)
                return false;
            if (pred == null){
                System.out.println("PRED NULL EN ADICIÓN");
                return false;
            }
            if (succ == null){
                System.out.println("SUCC NULL EN ADICIÓN");
                return false;
            }
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
            LocklessNode<T> nodes[] = oldFind(data);
            LocklessNode<T> pred = nodes[0];
            LocklessNode<T> curr = nodes[1];
            LocklessNode<T> succ = nodes[2];
            if (curr == null){
                //System.out.println("NO SE ELIMINA");
                return false;
            }
            if (pred == null){
                System.out.println("PRED NULL EN ELIMINACIÓN");
                return false;
            }
            if (succ == null){
                System.out.println("SUCC NULL EN ELIMINACIÓN");
                return false;
            }
            //else {
                //System.out.print("SE ENCONTRO Y ESTA POR BORRARSE: ");
                //System.out.println(curr.data);
            //}
            if (!curr.next.attemptMark(succ,true))
                continue;
            pred.next.compareAndSet(curr, succ, false, false);
            return true;
        }
    }

    private LocklessNode<T>[] find(T data){
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
                    LocklessNode<T> nodes[] = new LocklessNode[3];
                    nodes[0] = pred; nodes[1] = null; nodes[2] = curr;
                    return nodes;
                }
                pred = curr;
                curr = succ;
            }
        }
    }

    private LocklessNode<T>[] oldFind(T data) {
        LocklessNode<T> nodes[] = new LocklessNode[3];
        //boolean[] pmark = new boolean[1];
        boolean[] cmark = new boolean[1];
        int key = data.hashCode();
        tryAgain: while (true) {
            nodes[0] = head;
            nodes[1] = nodes[0].next.getReference();
            while (true) {
                if (nodes[1] == tail){
                    //System.out.println("LLEGO AL FINAL");
                    nodes[1] = null;
                    nodes[2] = tail;
                    return nodes;
                }
                nodes[2] = nodes[1].next.get(cmark);
                int ckey = nodes[1].key;
                //if (! (nodes[0].next.compareAndSet(nodes[1], nodes[2], false, false)))
                //    continue tryAgain;
                if (!cmark[0]) {
                    if (nodes[1].data == data){
                        //System.out.println("YA EXISTE");
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

    public boolean contains(T data){
        LocklessNode<T> nodes[]= find(data);
        return (nodes[1] != null);
    }

    public void print() {
        LocklessNode<T> curr = head.next.getReference();
        while (curr != tail) {
            System.out.println(curr.data);
            curr = curr.next.getReference();
        }
    }
}
