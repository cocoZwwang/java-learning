package pers.cocoadel.learning.algorithm.tree;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultBinarySearchTree<K, E> implements BinarySearchTree<K, E> {

    private int size;

    private Node root;

    private final Comparator<K> comparator;

    public DefaultBinarySearchTree(Comparator<K> comparator) {
        this.comparator = comparator;
        size = 0;
    }

    public DefaultBinarySearchTree() {
        this(null);
    }

    @Override
    public void put(K key, E value) {
        root = insert(root, key, value);
    }

    private Node insert(Node root, K key, E e) {
        if (root == null) {
            size++;
            return new Node(key, e);
        }

        int c = compareTo(root.key, key);
        if (c == 0) {
            root.val = e;
            return root;
        }

        if (c < 0) {
            root.right = insert(root.right, key, e);
        } else {
            root.left = insert(root.left, key, e);
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
    public E get(K key) {
        Node node = getNode(root, key);
        return node == null ? null : node.val;
    }

    private Node getNode(Node root, K key) {
        if (root == null) {
            return null;
        }

        int c = compareTo(root.key, key);
        if (c == 0) {
            return root;
        }
        if (c < 0) {
            return getNode(root.right, key);
        }

        return getNode(root.left, key);
    }

    @Override
    public boolean contains(K key) {
        if (isEmpty()) {
            return false;
        }
        return getNode(root, key) != null;
    }

    @Override
    public void delete(K key) {
        root = removeNode(root, key);
    }

    private Node removeNode(Node root, K key) {
        if (root == null) {
            return null;
        }

        int c = compareTo(root.key, key);
        if (c < 0) {
            root.right = removeNode(root.right, key);
            return root;
        } else if (c > 0) {
            root.left = removeNode(root.left, key);
            return root;
        }

        if (root.left == null || root.right == null) {
            Node node = root.left == null ? root.right : root.left;
            size--;
            root.right = null;
            root.left = null;
            return node;
        }

        Node successor = getMinNode(root.right);
        successor.right = removeMinNode(root.right);
        successor.left = root.left;
        size++;
        root.left = null;
        root.right = null;
        size--;
        return successor;
    }

    @Override
    public K minimum() {
        Node node = getMinNode(root);
        return node == null ? null : node.key;
    }

    private Node getMinNode(Node root) {
        while (root != null && root.left != null) {
            root = root.left;
        }
        return root;
    }

    private Node removeMinNode(Node root) {
        if (root.left == null) {
            Node node = root.right;
            size--;
            root.right = null;
            return node;
        }
        root.left = removeMinNode(root.left);
        return root;
    }

    @Override
    public K maximum() {
        Node node = getMaxNode(root);
        return node == null ? null : node.key;
    }

    private Node getMaxNode(Node root) {
        while (root != null && root.right != null) {
            root = root.right;
        }
        return root;
    }

    @Override
    public K floor(K key) {
        Node node = floorNode(root, key);
        return node == null ? null : node.key;
    }

    private Node floorNode(Node root, K key) {
        if (root == null) {
            return null;
        }

        int c = compareTo(root.key, key);
        if (c == 0) {
            return root;
        }
        if (c > 0) {
            return floorNode(root.left, key);
        }
        Node node = floorNode(root.right, key);
        return node != null ? node : root;
    }

    @Override
    public K lower(K key) {
        Node node = lowerNode(root, key);
        return node == null ? null : node.key;
    }

    private Node lowerNode(Node root, K key) {
        if (root == null) {
            return null;
        }

        int c = compareTo(root.key, key);
        if (c >= 0) {
            return lowerNode(root.left, key);
        }

        Node node = lowerNode(root.right, key);
        return node == null ? root : node;
    }

    @Override
    public K higher(K key) {
        Node node = higherNode(root, key);
        return node == null ? null : node.key;
    }

    private Node higherNode(Node root, K key) {
        if (root == null) {
            return null;
        }
        int c = compareTo(root.key, key);
        if (c <= 0) {
            return higherNode(root.right, key);
        }

        Node node = higherNode(root.left, key);
        return node == null ? root : node;
    }

    @Override
    public K ceiling(K key) {
        Node node = ceilingNode(root, key);
        return node == null ? null : node.key;
    }

    private Node ceilingNode(Node root, K key) {
        if (root == null) {
            return null;
        }
        int c = compareTo(root.key, key);
        if (c < 0) {
            return ceilingNode(root.right, key);
        }
        Node node = ceilingNode(root.left, key);
        return node != null ? node : root;
    }

    @Override
    public int rank(K key) {
        return rankNode(root, key);
    }

    /**
     * 返回 key 的排名，排名从 1 开始
     */
    private int rankNode(Node root, K key) {
        if (root == null) {
            return 0;
        }
        int cnt = rankNode(root.left, key);
        int c = compareTo(root.key, key);
        cnt += c <= 0 ? 1 : 0;
        if (c < 0) {
            cnt += rankNode(root.right, key);
        }
        return cnt;
    }

    @Override
    public K select(int k) {
        Node node = findKthNode(root, new int[]{k});
        return node == null ? null : node.key;
    }

    private Node findKthNode(Node root, int[] k) {
        if (root == null) {
            return null;
        }

        Node left = findKthNode(root.left, k);
        if (left != null) {
            return left;
        }
        k[0]--;
        if (k[0] == 0) {
            return root;
        }
        return findKthNode(root.right, k);
    }

    @Override
    public List<E> values() {
        List<Node> list = new LinkedList<>();
        listValue(root, list);
        return list
                .stream()
                .map(n -> n.val)
                .collect(Collectors.toList());
    }

    private void listValue(Node root, List<Node> list) {
        if (root == null) {
            return;
        }

        listValue(root.left, list);
        list.add(root);
        listValue(root.right, list);
    }

    @Override
    public Iterator<K> iterator() {
        return new InnerIterator(root);
    }


    @SuppressWarnings("unchecked")
    private int compareTo(K k1, K k2) {
        if (comparator != null) {
            return comparator.compare(k1, k2);
        }
        Comparable<K> c1 = (Comparable<K>) k1;
        return c1.compareTo(k2);
    }

    private class InnerIterator implements Iterator<K> {
        private Node curr;

        private LinkedList<Node> deque = new LinkedList<>();

        InnerIterator(Node root) {
            curr = root;
        }

        @Override
        public boolean hasNext() {
            return curr != null || deque.size() > 0;
        }

        @Override
        public K next() {
            while (curr != null) {
                deque.addLast(curr);
                curr = curr.left;
            }
            if (deque.size() > 0) {
                Node node = deque.removeLast();
                curr = node.right;
                return node.key;
            }
            return null;
        }
    }


    private class Node {
        private E val;

        private K key;

        private Node left;

        private Node right;

        Node(K key, E val) {
            this.key = key;
            this.val = val;
        }

    }
}
