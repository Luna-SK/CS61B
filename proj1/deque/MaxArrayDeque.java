package deque;

import java.util.Comparator;
import java.util.Iterator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;
    public MaxArrayDeque(Comparator<T> c) {
        super();
        comparator = c;
    }

    public T max() {
        if (isEmpty()) {
            return null;
        }
        Iterator<T> iter = iterator();
        T maxItem = iter.next();
        while (iter.hasNext()) {
            T item = iter.next();
            if (comparator.compare(item, maxItem) > 0) {
                maxItem = item;
            }
        }
        return maxItem;
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        Iterator<T> iter = iterator();
        T maxItem = iter.next();
        while (iter.hasNext()) {
            T item = iter.next();
            if (c.compare(item, maxItem) > 0) {
                maxItem = item;
            }
        }
        return maxItem;
    }

    public Iterator<T> iterator() {
        return super.iterator();
    }
}
