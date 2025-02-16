package tp.listas;

public class FineGrainedList<T> implements SynchronizedList<T> {
    public Node<T> head;
    public Node<T> tail;


    public FineGrainedList() {
        head = new Node<T>(null);
        tail = new Node<T>(null);
        head.next = tail;
    }

    public boolean add(T data) {
        if (data == null) {
            return false;
        }
        int key = data.hashCode();
        head.lock();
        Node<T> pred = head;
        try {
            Node<T> curr = pred.next;
            curr.lock();
            try {
                if (curr == tail) return false;
                while (curr.key < key) {
                    pred.unlock();
                    pred = curr;
                    curr = curr.next;
                    curr.lock();
                }
                if (curr.key == key) {
                    return false;
                }
                Node<T> newNode = new Node<T>(data);
                newNode.next = curr;
                pred.next = newNode;
                return true;
            } finally {
                curr.unlock();
            }
        } finally {
            pred.unlock();
        }
    }

    public boolean remove(T data) {
        if (data == null) {
            return false;
        }
        Node<T> pred = null;
        Node<T> curr = null;
        int key = data.hashCode();
        head.lock();
        pred = head;
        try {
            curr = pred.next;
            if (curr == null) System.out.println("ACÁ");
            curr.lock();
            try {
                if (curr == tail) return false;
                while (curr.key < key) {
                    pred.unlock();
                    pred = curr;
                    curr = curr.next;
                    if (curr == null) System.out.println("ACÁ TAMBIÉN?");
                    curr.lock();
                }
                if (curr.key == key) {
                    pred.next = curr.next;
                    return true;
                }
                return false;
            } finally {
                curr.unlock();
            }
        } finally {
            pred.unlock();
        }
    }

    public void print() {
        for (Node<T> node = head.next; node != tail; node = node.next) {
            System.out.println(node.data);
        }
    }



}
