package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private int size;
    private final Node sentinel;
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

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean equals(Object o) {
        if (o instanceof LinkedListDeque) {
            LinkedListDeque<T> l = (LinkedListDeque<T>) o;
            if (l.size() != size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!l.get(i).equals(this.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
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
        size -= 1;
        return returnItem;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T returnItem = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
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

    public T getRecursive(int index) {
        if (index == 0) {
            return sentinel.next.item;
        }
        else {
            return getRecursive(index - 1);
        }
    }

    public Iterator<T> iterator() {
        return new LLDequeIterator();
    }
}
