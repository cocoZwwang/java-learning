package pers.cocoadel.learning.algorithm.tree;

import java.util.Comparator;
import java.util.Iterator;

public class TreeISet<T> implements ISet<T> {
    private static class Node<T> {
        private Node<T> left;
        private Node<T> right;
        private Node<T> parent;

        private T element;

        Node(T element) {
            this.element = element;
        }
    }

    private Node<T> root;

    private Comparator<T> comparator;

    TreeISet(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    TreeISet() {
        this(null);
    }

    @Override
    public void makeEmpty() {
        root = null;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean contains(T t) {
        return false;
    }

    private boolean contains(T t, Node<T> root) {
        if (root == null) {
            return false;
        }

        int c = compareTo(t, root.element);
        if (c == 0) {
            return true;
        }

        if (c < 0) {
            return contains(t,root.left);
        }
        return contains(t, root.right);
    }

    @Override
    public T findMin() {
        Node<T> curr = findMin(root);
        return curr == null ? null : curr.element;
    }

    public Node<T> findMin(Node<T> root) {
        if (root == null || root.left == null) {
            return root;
        }
        return findMin(root.left);
    }

    @Override
    public T findMax() {
        Node<T> curr = findMax(root);
        return curr == null ? null : curr.element;
    }

    private Node<T> findMax(Node<T> root) {
        if (root == null || root.right == null) {
            return root;
        }
        return findMax(root.right);
    }

    @Override
    public void insert(T t) {
        root = insert(t, root);
    }

    private Node<T> insert(T t, Node<T> root) {
        if (root == null) {
            return new Node<>(t);
        }
        int c = compareTo(t, root.element);
        if (c < 0) {
            root.left =  insert(t, root.left);
            root.left.parent = root;
        }else if(c > 0){
            root.right = insert(t, root.right);
            root.right.parent = root;
        }
        return root;
    }

    @Override
    public void remove(T t) {
        root = remove(t, root);
    }

    private Node<T> remove(T t, Node<T> root) {
        int c = compareTo(t, root.element);
        if (c < 0) {
            root.left =  remove(t, root.left);
        } else if (c > 0) {
            root.right =  remove(t, root.right);
        }else if (root.left != null && root.right != null) {
            root.element = findMin(root.right).element;
            root.right = remove(root.element, root.right);
        }else {
            Node<T> oneChild = root.left != null ? root.left : root.right;
            oneChild.parent = root.parent;
            root.parent = null;
            root.left = null;
            root.right = null;
            root = oneChild;
        }
        return root;

    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    private int compareTo(T t1, T t2) {
        if (comparator != null) {
            return comparator.compare(t1, t2);
        }

        Comparable comparable = (Comparable) t1;
        return comparable.compareTo(t2);
    }
}
