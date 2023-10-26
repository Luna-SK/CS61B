package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int size;
    private int capacity;
    private int firstIndex;
    private int lastIndex;

    public ArrayDeque() {
        items = (T[]) new Object[16];
        size = 0;
        capacity = 16;
        firstIndex = lastIndex = 0;
    }

    private class ADequeIterator implements Iterator<T> {

        private int currentIndex;
        ADequeIterator() {
            currentIndex = (firstIndex + capacity - 1) % capacity;
        }
        public boolean hasNext() {
            return !(isEmpty() || currentIndex == lastIndex);
        }

        public T next() {
            currentIndex = (currentIndex + 1) % capacity;
            return items[currentIndex];
        }
    }

    private boolean isFull() {
        return size == capacity;
    }


    private void resize(int newCapacity) {
        T[] newArray = (T[]) new Object[newCapacity];
        for (int i = firstIndex, newIndex = 0; newIndex < size; newIndex++) {
            newArray[newIndex] = items[i];
            i = (i + 1) % capacity;
        }
        items = newArray;
        firstIndex = 0;
        lastIndex = size - 1;
        capacity = newCapacity;
    }

    public void addFirst(T item) {
        if (isEmpty()) {
            firstIndex = lastIndex = 0;
            items[firstIndex] = item;
            size += 1;
            return;
        }
        if (isFull()) {
            resize(capacity * 2);
        }
        firstIndex = (firstIndex + capacity - 1) % capacity;
        items[firstIndex] = item;
        size += 1;
    }

    public void addLast(T item) {
        if (isEmpty()) {
            lastIndex = firstIndex = 0;
            items[lastIndex] = item;
            size += 1;
            return;
        }
        if (isFull()) {
            resize(capacity * 2);
        }
        lastIndex = (lastIndex + 1) % capacity;
        items[lastIndex] = item;
        size += 1;
    }

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

    private boolean isReadyToDesc() {
        return capacity > 16 && size * 4 < capacity;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = firstIndex; i < lastIndex; i = (i + 1) % size) {
            System.out.print(items[i] + " ");
        }
        System.out.println(items[lastIndex]);
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        if (isReadyToDesc()) {
            resize(capacity >> 2);
        }
        T returnItem = items[firstIndex];
        items[firstIndex] = null;
        firstIndex = (firstIndex + 1) % capacity;
        size -= 1;
        return returnItem;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (isReadyToDesc()) {
            resize(capacity >> 2);
        }
        T returnItem = items[lastIndex];
        lastIndex = (lastIndex + capacity - 1) % capacity;
        size -= 1;
        return returnItem;
    }

    public T get(int index) {
        if (index > size - 1) {
            return null;
        }
        return items[(firstIndex + index) % capacity];
    }

    public Iterator<T> iterator() {
        return new ADequeIterator();
    }
}
