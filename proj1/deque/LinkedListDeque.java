package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private int size;
    private final Node sentinel;

    public boolean equals(Object o) {
        if (o instanceof Deque) {
            Deque<T> l = (Deque<T>) o;
            if (l.size() != this.size()) {
                return false;
            }
            for (int i = 0; i < this.size(); i++) {
                if (!l.get(i).equals(this.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private class Node {
        private T item;
        private Node next;
        private Node prev;
    }

    private class LLDequeIterator implements Iterator<T> {

        private Node p;
        LLDequeIterator() {
            p = sentinel.next;
        }
        @Override
        public boolean hasNext() {
            return p != sentinel;
        }

        @Override
        public T next() {
            T returnItem = p.item;
            p = p.next;
            return returnItem;
        }
    }
    public LinkedListDeque() {
        sentinel = new Node();
        sentinel.prev = sentinel.next = sentinel;
        sentinel.item = null;
        size = 0;
    }
    public void addFirst(T item) {
        Node node = new Node();
        node.item = item;
        node.next = sentinel.next;
        node.prev = sentinel;
        sentinel.next.prev = node;
        sentinel.next = node;
        size += 1;
    }

    public void addLast(T item) {
        Node node = new Node();
        node.item = item;
        node.prev = sentinel.prev;
        node.next = sentinel;
        sentinel.prev.next = node;
        sentinel.prev = node;
        size += 1;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (!isEmpty()) {
            Node p = sentinel.next;
            while (p.next != sentinel) {
                System.out.print(p.item.toString() + ' ');
                p = p.next;
            }
            System.out.println(p.item.toString());
        }
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T returnItem = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return returnItem;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T returnItem = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return returnItem;
    }

    public T get(int index) {
        if (index > size - 1) {
            return null;
        }
        Node p = sentinel.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }

    private T getRecursiveHelper(int index, Node p) {
        if (index == 0) {
            return p.item;
        } else {
            return getRecursiveHelper(index - 1, p.next);
        }
    }

    public T getRecursive(int index) {
        if (index > size - 1) {
            return null;
        } else {
            return getRecursiveHelper(index, sentinel.next);
        }
    }

    public Iterator<T> iterator() {
        return new LLDequeIterator();
    }
}
