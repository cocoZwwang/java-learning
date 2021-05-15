package pers.cocoadel.learning.algorithm.tree;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractBinarySearchTree<K, V> implements BinarySearchTree<K, V> {
    protected BinaryNode<K, V> root;

    protected Comparator<K> comparator;

    public AbstractBinarySearchTree(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void put(K key, V value) {
        root = insert(root, key, value);
    }

    protected abstract BinaryNode<K, V> insert(BinaryNode<K, V> root, K key, V value);


    @Override
    public V get(K key) {
        BinaryNode<K, V> node = find(root, key);
        return node == null ? null : node.getValue();
    }

    protected BinaryNode<K, V> find(BinaryNode<K, V> root, K key) {
        if (root == null) {
            return null;
        }
        int c = compareTo(key, root.getKey());
        if (c == 0) {
            return root;
        }
        if (c < 0) {
            return find(root.getLeft(), key);
        }

        return find(root.getRight(), key);
    }

    @Override
    public boolean contains(K key) {
        return find(root, key) != null;
    }

    @Override
    public void delete(K key) {
        root = remove(root, key);
    }

    protected abstract BinaryNode<K, V> remove(BinaryNode<K, V> root, K key);

    @Override
    public K minimum() {
        BinaryNode<K, V> min = findMin(root);
        return min == null ? null : min.getKey();
    }

    protected BinaryNode<K, V> findMin(BinaryNode<K, V> root) {
        while (root != null && root.getLeft() != null) {
            root = root.getLeft();
        }
        return root;
    }

    @Override
    public K maximum() {
        BinaryNode<K, V> max = findMax(root);
        return max == null ? null : max.getKey();
    }

    protected BinaryNode<K, V> findMax(BinaryNode<K, V> root) {
        while (root != null && root.getRight() != null) {
            root = root.getRight();
        }
        return root;
    }

    @Override
    public K floor(K key) {
        BinaryNode<K, V> node = floorNode(root, key);
        return node == null ? null : node.getKey();
    }

    protected BinaryNode<K, V> floorNode(BinaryNode<K, V> root, K key) {
        if (root == null) {
            return null;
        }
        int c = compareTo(key, root.getKey());
        if (c == 0) {
            return root;
        }

        if (c < 0) {
            return floorNode(root.getLeft(), key);
        }

        BinaryNode<K, V> node = floorNode(root.getRight(), key);
        return node == null ? root : node;
    }

    @Override
    public K ceiling(K key) {
        BinaryNode<K, V> node = ceilingNode(root, key);
        return node == null ? null : node.getKey();
    }

    protected BinaryNode<K, V> ceilingNode(BinaryNode<K, V> root, K key) {
        if (root == null) {
            return null;
        }
        int c = compareTo(key, root.getKey());
        if (c == 0) {
            return root;
        }

        if (c > 0) {
            return ceilingNode(root.getRight(), key);
        }

        BinaryNode<K, V> node = ceilingNode(root.getLeft(), key);
        return node == null ? root : node;
    }

    @Override
    public K lower(K key) {
        BinaryNode<K, V> lower = lower(root, key);
        return lower == null ? null : lower.getKey();
    }

    protected BinaryNode<K, V> lower(BinaryNode<K, V> root, K key) {
        if (root == null) {
            return null;
        }

        int c = compareTo(key, root.getKey());
        if (c == 0) {
            return root.getLeft();
        }

        if (c < 0) {
            return lower(root.getLeft(), key);
        }

        BinaryNode<K, V> lower = lower(root.getRight(), key);
        return lower == null ? root : lower;
    }

    @Override
    public K higher(K key) {
        BinaryNode<K, V> node = higherNode(root, key);
        return node == null ? null : node.getKey();
    }

    protected BinaryNode<K, V> higherNode(BinaryNode<K, V> root, K key) {
        if (root == null) {
            return null;
        }

        int c = compareTo(key, root.getKey());
        if (c == 0) {
            return root.getRight();
        }


        if (c > 0) {
            return higherNode(root.getRight(), key);
        }

        BinaryNode<K, V> node = higherNode(root.getLeft(), key);
        return node == null ? root : node;
    }

    @Override
    public int rank(K key) {
        NodeIterator iterator = new NodeIterator();
        int res = 0;
        while (iterator.hasNext()) {
            res++;
            if (compareTo(iterator.next().getKey(), key) == 0) {
                break;
            }
        }
        return res;
    }

    @Override
    public K select(int k) {
        NodeIterator nodeIterator = new NodeIterator();
        for (int i = 1; i < k && nodeIterator.hasNext(); i++) {
            nodeIterator.next();
        }
        return nodeIterator.hasNext() ? nodeIterator.next().getKey() : null;
    }

    @Override
    public List<V> values() {
        NodeIterator nodeIterator = new NodeIterator();
        List<V> res = new LinkedList<>();
        while (nodeIterator.hasNext()) {
            res.add(nodeIterator.next().getValue());
        }
        return res;
    }

    @Override
    public Iterator<K> iterator() {
        return new KeyIterator();
    }

    protected int compareTo(K k1, K k2) {
        if (comparator != null) {
            return comparator.compare(k1, k2);
        }
        Comparable comparable = (Comparable) k1;
        return comparable.compareTo(k2);
    }


    protected class NodeIterator implements Iterator<BinaryNode<K, V>> {
        private BinaryNode<K,V> curr = root;
        private final LinkedList<BinaryNode<K, V>> dq = new LinkedList<>();

        @Override
        public boolean hasNext() {
            return curr != null || dq.size() > 0;
        }

        @Override
        public BinaryNode<K, V> next() {
            while (curr != null) {
                dq.addLast(curr);
                curr = curr.getLeft();
            }
            if (dq.size() > 0) {
                BinaryNode<K, V> node = dq.removeLast();
                curr = node.getRight();
                return node;
            }
            return null;
        }
    }

    protected class KeyIterator implements Iterator<K> {

        private final NodeIterator nodeIterator = new NodeIterator();

        @Override
        public boolean hasNext() {
            return nodeIterator.hasNext();
        }

        @Override
        public K next() {
            return nodeIterator.next().getKey();
        }
    }

}
