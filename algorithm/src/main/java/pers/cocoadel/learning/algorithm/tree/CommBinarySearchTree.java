package pers.cocoadel.learning.algorithm.tree;

import java.util.Comparator;

/**
 * 普通 二叉搜索数
 * @param <K>
 * @param <V>
 */
public class CommBinarySearchTree<K, V> extends AbstractBinarySearchTree<K, V> {

    private int size;

    public CommBinarySearchTree() {
        this(null);
    }


    public CommBinarySearchTree(Comparator<K> comparator) {
        super(comparator);
    }


    /**
     * 如果 key 已经存在则更新 value，否则插入新结点
     */
    @Override
    protected BinaryNode<K, V> insert(BinaryNode<K, V> root, K key, V value) {
        if (root == null) {
            size++;
            return new BinaryNode<>(key, value);
        }

        int c = compareTo(key, root.getKey());
        if (c < 0) {
            root.setLeft(insert(root.getLeft(), key, value));
        } else if (c > 0) {
            root.setRight(insert(root.getRight(), key, value));
        } else {
            root.setValue(value);
        }
        return root;
    }

    @Override
    protected BinaryNode<K, V> remove(BinaryNode<K, V> root, K key) {
        if (root == null) {
            return null;
        }

        int c = compareTo(key, root.getKey());
        if (c < 0) {
            root.setLeft(remove(root.getLeft(), key));
            return root;
        }

        if (c > 0) {
            root.setRight(remove(root.getRight(), key));
            return root;
        }

        if (root.getLeft() == null || root.getRight() == null) {
            BinaryNode<K, V> node = root.getLeft() != null ? root.getLeft() : root.getRight();
            root.setLeft(null);
            root.setRight(null);
            size--;
            return node;
        }

        BinaryNode<K, V> successor = rotateMinToRoot(root.getRight());
        root.reset();
        size--;
        return successor;
    }

    protected BinaryNode<K, V> rotateMinToRoot(BinaryNode<K, V> root) {
        if (root == null || root.getLeft() == null) {
            return root;
        }
        BinaryNode<K,V> curr = root;
        BinaryNode<K,V> pre = root;
        while (curr.getLeft() != null) {
            pre = curr;
            curr = curr.getLeft();
        }
        pre.setLeft(curr.getRight());
        curr.setRight(pre);
        return curr;
    }

    private BinaryNode<K, V> removeMin(BinaryNode<K, V> root) {
        if (root == null) {
            return null;
        }
        if (root.getLeft() == null) {
            return root.getRight();
        }
        BinaryNode<K, V> kvBinaryNode = removeMin(root.getLeft());
        root.setLeft(kvBinaryNode);
        size--;
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
}
