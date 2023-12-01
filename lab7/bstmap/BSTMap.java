package bstmap;

import edu.princeton.cs.algs4.BST;

import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>, Iterable<K> {
    private BSTNode root;
    private int size;
    private BSTNode removed;

    private class BSTNode {
        private K key;
        private V value;
        private BSTNode left;
        private BSTNode right;
        BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private class BSTIterator implements Iterator<K> {
        private ArrayList<K> keys;

        public BSTIterator() {
            keys = new ArrayList<>();
            addInOrder(root);
        }

        private void addInOrder(BSTNode node) {
            if (node == null) {
                return;
            }
            addInOrder(node.left);
            keys.add(node.key);
            addInOrder(node.right);
        }

        @Override
        public boolean hasNext() {
            return !keys.isEmpty();
        }

        @Override
        public K next() {
            return keys.removeFirst();
        }
    }

    public BSTMap() {
        size = 0;
        root = null;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return containsKey(root, key);
    }

    private boolean containsKey(BSTNode node, K key) {
        if (node == null) {
            return false;
        }
        int compare = key.compareTo(node.key);
        if (compare == 0) {
            return true;
        } else if (compare > 0) {
            return containsKey(node.right, key);
        } else {
            return containsKey(node.left, key);
        }
    }

    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get(BSTNode node, K key) {
        if (node == null) {
            return null;
        }
        int compare = key.compareTo(node.key);
        if (compare == 0) {
            return node.value;
        } else if (compare > 0) {
            return get(node.right, key);
        } else {
            return get(node.left, key);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private BSTNode put(BSTNode node, K key, V value) {
        if (node == null) {
            size++;
            return new BSTNode(key, value);
        }
        int compare = key.compareTo(node.key);
        if (compare == 0) {
            node.value = value;
        } else if (compare > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.left = put(node.left, key, value);
        }
        return node;
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
        root = remove(root, key);
        if (removed != null) {
            V returnValue = removed.value;
            removed = null;
            return returnValue;
        }
        return null;
    }

    private BSTNode remove(BSTNode node, K key) {
        if (node == null) {
            return null;
        }
        int compare = key.compareTo(node.key);
        if (compare < 0) {
            node.left = remove(node.left, key);
        } else if (compare > 0) {
            node.right = remove(node.right, key);
        } else {
            removed = node;
            size--;
            if (node.left == null) {
                node = node.right;
            } else if (node.right == null) {
                node = node.left;
            } else {
                BSTNode temp = findMin(node.right);
                node.key = temp.key;
                node.value = temp.value;
                node.right = remove(node.right, temp.key);
            }
        }
        return node;
    }

    private BSTNode findMin(BSTNode node) {
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    @Override
    public V remove(K key, V value) {
        root = remove(root, key, value);
        if (removed != null) {
            V returnValue = removed.value;
            removed = null;
            return returnValue;
        }
        return null;
    }

    private BSTNode remove(BSTNode node, K key, V value) {
        if (node == null) {
            return null;
        }
        int compareKey = key.compareTo(node.key);
        if (compareKey > 0) {
            node.right = remove(node.right, key, value);
        } else if (compareKey < 0){
            node.left = remove(node.left, key, value);
        } else {
            if (value.equals(node.value)) {
                removed = node;
                size--;
                if (node.left == null) {
                    node = node.right;
                } else if (node.right == null) {
                    node = node.left;
                } else {
                    BSTNode temp = findMin(node.right);
                    node.key = temp.key;
                    node.value = temp.value;
                    remove(node.right, key, value);
                }
            }
        }
        return node;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTIterator();
    }

    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(BSTNode node) {
        if (node == null) {
            return;
        }
        printInOrder(node.left);
        System.out.print(node.key + " ");
        printInOrder(node.right);
    }
}
