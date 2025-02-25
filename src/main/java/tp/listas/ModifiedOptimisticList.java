package tp.listas;

public class ModifiedOptimisticList<T> implements SynchronizedList<T> {

    public Node<T> head;
    public Node<T> tail;

    public ModifiedOptimisticList() {
        head = new Node<T>(null);
        head.key = Integer.MIN_VALUE;
        tail = new Node<T>(null);
        tail.key = Integer.MAX_VALUE;
        head.next = tail;
    }

    public boolean add(T data) {
        if (data == null) {
            return false;
        }
        int key = data.hashCode();
        while (true) {
            Node<T> pred = head;
            Node<T> curr = pred.next;
            if (curr == tail) return false;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            pred.lock();
            try {
                curr.lock();
                try {
                    if (validate(pred, curr)) {
                        if (curr.key == key) {
                            return false;
                        }
                        Node<T> newNode = new Node<T>(data);
                        newNode.next = curr;
                        pred.next = newNode;
                        return true;
                    }
                } finally {
                    curr.unlock();
                }
            } finally {
                pred.unlock();
            }
        }
    }

    public boolean remove(T data) {
        if (data == null) {
            return false;
        }
        int key = data.hashCode();
        while (true) {
            Node<T> pred = head;
            Node<T> curr = pred.next;
            if (curr == tail) return false;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            pred.lock();
            try {
                curr.lock();
                try {
                    if (validate(pred, curr)) {
                        if (curr.key == key) {
                            pred.next = curr.next;
                            return true;
                        } else {
                            return false;
                        }
                    }
                } finally {
                    curr.unlock();
                }
            } finally {
                pred.unlock();
            }
        }
    }

    private boolean validate(Node<T> pred, Node<T> curr) {
        Node<T> node = head;
        while (node.key <= pred.key) {
            if (node == pred) {
                return pred.next == curr;
            }
            node = node.next;
        }
        return false;
    }

    public void print() {
        Node<T> curr = head.next;
        while (curr != tail) {
            System.out.println(curr.data);
            curr = curr.next;
        }
    }
}