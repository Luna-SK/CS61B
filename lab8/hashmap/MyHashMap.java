package hashmap;

import java.lang.reflect.Array;
import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int tableSize;
    private int elementsCount;
    private double loadFactor;
    private double maxLoad;

    /** Constructors */
    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = createTable(initialSize);
        this.maxLoad = maxLoad;
        tableSize = initialSize;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        elementsCount++;
        updateLoadFactor();
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        updateLoadFactor();
        return new ArrayList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    // Your code won't compile until you do so!

    private void updateLoadFactor() {
        loadFactor = (double) elementsCount / tableSize;
    }

    private int hash(K key) {
        return Math.floorMod(key.hashCode(), maxPrime(tableSize));
    }

    private void resize(int size) {
        Collection<Node>[] oldBuckets = buckets;
        tableSize = size;
        buckets = createTable(size);
        clear();
        for (var bucket : oldBuckets) {
            if (bucket != null) {
                bucket.forEach(node -> put(node.key, node.value));
            }
        }
    }

    private int maxPrime(int num) {
        for (int i = num; i > 1; i--) {
            if (isPrime(i)) {
                return i;
            }
        }
        return num;
    }

    private boolean isPrime(int num) {
        if (num < 1) {
            return false;
        }
        for (int i = 2; i * i < num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        Arrays.fill(buckets, null);
        elementsCount = 0;
        loadFactor = 0.0;
    }

    @Override
    public boolean containsKey(K key) {
        return buckets[hash(key)] != null;
    }

    @Override
    public V get(K key) {
        int index = hash(key);
        if (buckets[index] != null) {
            for (var item : buckets[index]) {
                if (key.equals(item.key)) {
                    return item.value;
                }
            }
        }
        return null;
    }

    @Override
    public int size() {
        return elementsCount;
    }

    @Override
    public void put(K key, V value) {
        if (loadFactor > maxLoad) {
            resize(tableSize * 2);
        }
        int index = hash(key);
        if (buckets[index] == null) {
            buckets[index] = createBucket();
        }
        for (Node item : buckets[index]) {
            if (key.equals(item.key)) {
                item.value = value;
                return;
            }
        }
        buckets[index].add(createNode(key, value));
        updateLoadFactor();
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (K key : this) {
            set.add(key);
        }
        return set;
    }

    @Override
    public V remove(K key) {
        int index = hash(key);
        if (buckets[index] == null) {
            return null;
        }
        Node result = findNode(buckets[index], key);
        buckets[index].remove(result);
        if (buckets[index].isEmpty()) {
            buckets[index] = null;
        }
        elementsCount--;
        updateLoadFactor();
        return result.value;
    }

    @Override
    public V remove(K key, V value) {
        int index = hash(key);
        if (buckets[index] == null) {
            return null;
        }
        Node result = findNode(buckets[index], key, value);
        if (result == null) {
            return null;
        } else {
            buckets[index].remove(result);
            if (buckets[index].isEmpty()) {
                buckets[index] = null;
            }
            elementsCount--;
            updateLoadFactor();
            return result.value;
        }
    }

    private Node findNode(Collection<Node> nodes, K key) {
        for (Node node : nodes) {
            if (key.equals(node.key)) {
                return node;
            }
        }
        return null;
    }

    private Node findNode(Collection<Node> nodes, K key, V value) {
        for (Node node : nodes) {
            if (key.equals(node.key)) {
                if (value.equals(node.value)) {
                    return node;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new KeyIterator<>();
    }

    private class KeyIterator<K> implements Iterator<K> {
        int size = elementsCount;
        ArrayList<K> keys = new ArrayList<>();
        KeyIterator() {
            for (var bucket : buckets) {
                if (bucket != null) {
                    bucket.forEach(node -> keys.add((K) node.key));
                }
            }
        }

        @Override
        public boolean hasNext() {
            return size > 0;
        }

        @Override
        public K next() {
            size--;
            return keys.remove(0);
        }
    }
}
