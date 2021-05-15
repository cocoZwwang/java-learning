package pers.cocoadel.learning.algorithm.tree;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class AbstractBinarySearchTree2<K, V> implements BinarySearchTree<K, V> {

    private final Comparator<K> comparator;

    private BinaryNode<K, V> root;

    /**
     * the number of structural modification to the tree
     */
    private int modCount = 0;

    private int size;

    public AbstractBinarySearchTree2(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    public AbstractBinarySearchTree2() {
        this(null);
    }

    @Override
    public void put(K key, V value) {
        root = insert(key, value, root);
    }

    protected BinaryNode<K, V> insert(K key, V value, BinaryNode<K, V> root) {
        if (root == null) {
            modCount++;
            size++;
            return new BinaryNode<>(key, value);
        }

        int c = compareTo(key, root.getKey());
        if (c == 0) {
            root.setValue(value);
        } else if (c < 0) {
            root.setLeft(insert(key, value, root.getLeft()));
            root.getLeft().setParent(root);
        } else {
            root.setRight(insert(key, value, root.getRight()));
            root.getRight().setParent(root);
        }
        return root;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public V get(K key) {
        BinaryNode<K, V> node = find(key, root);
        return node == null ? null : node.getValue();
    }

    protected BinaryNode<K, V> find(K key, BinaryNode<K, V> root) {
        if (root == null) {
            return null;
        }
        int c = compareTo(key, root.getKey());
        if (c == 0) {
            return root;
        }

        if (c < 0) {
            return find(key, root.getLeft());
        }
        return find(key, root.getRight());
    }

    @Override
    public boolean contains(K key) {
        BinaryNode<K, V> node = find(key, root);
        return node != null;
    }

    @Override
    public void delete(K key) {
        root = remove(key, root);
    }

    protected BinaryNode<K, V> remove(K key, BinaryNode<K, V> root) {
        if (root == null) {
            return null;
        }

        int c = compareTo(key, root.getKey());
        if (c < 0) {
            root.setLeft(remove(key, root.getLeft()));
        } else if (c > 0) {
            root.setRight(remove(key, root.getRight()));
        } else if (root.getLeft() != null && root.getRight() != null) {
            BinaryNode<K, V> minNode = findMin(root.getRight());
            root.setKey(minNode.getKey());
            root.setValue(minNode.getValue());
            remove(minNode.getKey(), root.getRight());
        } else {
            BinaryNode<K, V> oneChild = root.getLeft() != null ? root.getLeft() : root.getRight();
            if (oneChild != null) {
                oneChild.setParent(root.getParent());
            }
            root.reset();
            root = oneChild;
            size--;
            modCount++;
        }
        return root;
    }

    @Override
    public K minimum() {
        BinaryNode<K, V> node = findMin(root);
        return node == null ? null : node.getKey();
    }

    protected BinaryNode<K, V> findMin(BinaryNode<K, V> root) {
        if (root == null || root.getLeft() == null) {
            return root;
        }
        return findMin(root.getLeft());
    }

    @Override
    public K maximum() {
        BinaryNode<K, V> node = findMax(root);
        return node == null ? null : node.getKey();
    }

    protected BinaryNode<K, V> findMax(BinaryNode<K, V> root) {
        if (root == null || root.getRight() == null) {
            return root;
        }
        return findMax(root.getRight());
    }

    @Override
    public K floor(K key) {
        BinaryNode<K, V> node = floor(key, root);
        return node == null ? null : node.getKey();
    }

    protected BinaryNode<K, V> floor(K key, BinaryNode<K, V> root) {
        if (root == null) {
            return null;
        }

        int c = compareTo(key, root.getKey());
        if (c == 0) {
            return root;
        }
        if (c < 0) {
            return floor(key, root.getLeft());
        }
        BinaryNode<K, V> node = floor(key, root.getRight());
        return node == null ? root : node;
    }

    @Override
    public K ceiling(K key) {
        BinaryNode<K, V> node = ceiling(key, root);
        return node == null ? null : node.getKey();
    }

    protected BinaryNode<K, V> ceiling(K key, BinaryNode<K, V> root) {
        if (root == null) {
            return null;
        }
        int c = compareTo(key, root.getKey());
        if (c == 0) {
            return root;
        }
        if (c > 0) {
            return ceiling(key, root.getRight());
        }
        BinaryNode<K, V> node = ceiling(key, root.getLeft());
        return node == null ? root : node;
    }

    @Override
    public K lower(K key) {
        BinaryNode<K, V> node = lower(key, root);
        return node == null ? null : node.getKey();
    }

    protected BinaryNode<K, V> lower(K key, BinaryNode<K, V> root) {
        if (root == null) {
            return null;
        }

        int c = compareTo(key, root.getKey());
        if (c <= 0) {
            return lower(key, root.getLeft());
        }

        BinaryNode<K, V> node = lower(key, root.getRight());
        return node == null ? root : node;
    }

    @Override
    public K higher(K key) {
        BinaryNode<K, V> node = higher(key, root);
        return node == null ? null : node.getKey();
    }

    protected BinaryNode<K, V> higher(K key, BinaryNode<K, V> root) {
        if (root == null) {
            return null;
        }

        int c = compareTo(key, root.getKey());
        if (c >= 0) {
            return higher(key, root.getRight());
        }
        BinaryNode<K, V> node = higher(key, root.getLeft());
        return node == null ? root : node;
    }

    @Override
    public int rank(K key) {
        KeyIterator keyIterator = new KeyIterator();
        int rank = 0;
        boolean has = false;
        while (keyIterator.hasNext()) {
            rank++;
            if (compareTo(key, keyIterator.next()) == 0) {
                has = true;
                break;
            }
        }
        return has ? rank : -1;
    }

    @Override
    public K select(int k) {
        KeyIterator keyIterator = new KeyIterator();
        K res = null;
        while (k > 0 && keyIterator.hasNext()) {
            res = keyIterator.next();
            k--;
        }
        return k == 0 ? res : null;
    }

    @Override
    public List<V> values() {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new KeyIterator();
    }

    protected class KeyIterator implements Iterator<K> {
        private int exceptedModCount = modCount;
        private BinaryNode<K, V> curr = findMin(root);
        //iterator remove
        private BinaryNode<K, V> previous;
        private boolean atEnd = curr == null;
        private boolean okToRemove = false;


        @Override
        public boolean hasNext() {
            return !atEnd;
        }

        @Override
        public K next() {
            if (modCount != exceptedModCount) {
                throw new UnsupportedOperationException();
            }

            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            K nextItem = curr.getKey();
            previous = curr;
            if (curr.getRight() != null) {
                curr = findMin(curr.getRight());
            } else {
                BinaryNode<K, V> child = curr;
                curr = curr.getParent();
                while (curr != null && curr.getLeft() != child) {
                    child = curr;
                    curr = curr.getParent();
                }
                if (curr == null) {
                    atEnd = true;
                }
            }
            okToRemove = true;
            return nextItem;
        }

        @Override
        public void remove() {
            if (modCount != exceptedModCount) {
                throw new UnsupportedOperationException();
            }

            if (!okToRemove) {
                throw new IllegalStateException();
            }

            AbstractBinarySearchTree2.this.remove(previous.getKey(), root);
            okToRemove = false;
            exceptedModCount = modCount;
        }
    }

    protected int compareTo(K o1, K o2) {
        if (comparator != null) {
            return comparator.compare(o1, o2);
        }
        Comparable<K> comparable = (Comparable<K>) o1;
        return comparable.compareTo(o2);
    }
}
