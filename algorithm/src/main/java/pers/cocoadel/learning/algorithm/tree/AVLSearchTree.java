package pers.cocoadel.learning.algorithm.tree;

import java.util.Comparator;

/**
 * AVL 树的简单实现
 * @param <K>
 * @param <V>
 */
public class AVLSearchTree<K, V> extends AbstractBinarySearchTree<K, V> {
    private static final int ALLOW_IMBALANCE = 1;

    private int size;

    public AVLSearchTree() {
        this(null);
    }

    public AVLSearchTree(Comparator<K> comparator) {
        super(comparator);
    }

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
        }
        return balance(root);
    }

    @Override
    protected BinaryNode<K, V> remove(BinaryNode<K, V> root, K key) {
        if (root == null) {
            return null;
        }
        int c = compareTo(key, root.getKey());
        if (c < 0) {
            root.setLeft(remove(root.getLeft(), key));
        } else if (c > 0) {
            root.setRight(remove(root.getRight(), key));
        } else {
            if (root.getLeft() == null || root.getRight() == null) {
                BinaryNode<K, V> del = root;
                root = root.getLeft() != null ? root.getLeft() : root.getRight();
                del.reset();
            } else {
                BinaryNode<K, V> t = root;
                root = rotateMinToRoot(root.getRight());
                root.setLeft(t.getLeft());
                t.reset();
            }
            size--;
        }
        return balance(root);
    }

    protected BinaryNode<K, V> rotateMinToRoot(BinaryNode<K, V> root) {
        if (root == null || root.getLeft() == null) {
            return root;
        }
        BinaryNode<K, V> curr = root;
        BinaryNode<K, V> pre = root;
        while (curr.getLeft() != null) {
            pre = curr;
            curr = curr.getLeft();
        }
        pre.setLeft(curr.getRight());
        curr.setRight(pre);
        return curr;
    }


    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return size;
    }

    protected int height(BinaryNode<K, V> node) {
        return node == null ? -1 : node.getHeight();
    }


    protected BinaryNode<K, V> balance(BinaryNode<K, V> node) {
        if (node == null) {
            return null;
        }
        //左边高
        int leftHeight = height(node.getLeft());
        int rightHeight = height(node.getRight());
        BinaryNode<K, V> t = node;
        if (leftHeight - rightHeight > ALLOW_IMBALANCE) {
            if (leftHeight >= rightHeight) {
                t = rotateWithLeftChild(node);
            } else {
                t = doubleRotateWithLeftChild(node);
            }
        } else if (rightHeight - leftHeight > ALLOW_IMBALANCE) {
            if (rightHeight >= leftHeight) {
                t = rotateWithRightChild(node);
            } else {
                t = doubleRotateWithRightChild(node);
            }
        }
        t.setHeight(Math.max(height(t.getLeft()), height(t.getRight())) + 1);
        return t;
    }

    protected BinaryNode<K, V> rotateWithLeftChild(BinaryNode<K, V> k2) {
        BinaryNode<K, V> k1 = k2.getLeft();
        k2.setLeft(k1.getRight());
        k1.setRight(k2);
        int k2H = Math.max(height(k2.getRight()), height(k2.getLeft())) + 1;
        k2.setHeight(k2H);
        int k1H = Math.max(k2H, height(k1.getLeft()) + 1);
        k1.setHeight(k1H);
        return k1;
    }

    protected BinaryNode<K, V> doubleRotateWithLeftChild(BinaryNode<K, V> k3) {
        k3.setLeft(rotateWithRightChild(k3.getLeft()));
        return rotateWithLeftChild(k3);
    }

    protected BinaryNode<K, V> rotateWithRightChild(BinaryNode<K, V> k2) {
        BinaryNode<K, V> k1 = k2.getRight();
        k2.setRight(k1.getLeft());
        k1.setLeft(k2);
        int k2H = Math.max(height(k2.getLeft()), height(k2.getRight())) + 1;
        k2.setHeight(k2H);
        int k1H = Math.max(k2H, height(k1.getRight())) + 1;
        k1.setHeight(k1H);
        return k1;
    }

    protected BinaryNode<K, V> doubleRotateWithRightChild(BinaryNode<K, V> k3) {
        k3.setRight(rotateWithLeftChild(k3.getRight()));
        return rotateWithRightChild(k3);
    }

    /**
     * Internal method to print a subtree in sorted order.
     *
     * @param t the node that roots the subtree.
     */
    private void printTree(BinaryNode<K, V> t) {
        if (t != null) {
            printTree(t.getLeft());
            System.out.println(t.getKey());
            printTree(t.getRight());
        }
    }

    private void printTree() {
        if (isEmpty()) {
            System.out.println("tree is empty!");
        } else {
            printTree(root);
        }
    }


    public static void main(String[] args) {
        AVLSearchTree<Integer, Integer> t = new AVLSearchTree<>();
        final int NUMS = 40;
        final int GAP = 37;

        System.out.println("Checking... (no more output means success)");

        for (int i = GAP; i != 0; i = (i + GAP) % NUMS) {
            System.out.println(i);
            t.put(i, i);
        }
        System.out.println("-------------------------------");
        if (NUMS <= 40)
            t.printTree();
        System.out.println("+++++++++++++++++++++++++++++++++");
        for (int i = 1; i < NUMS; i += 2)
            t.delete(i);

        if (NUMS <= 40)
            t.printTree();
        if (t.minimum() != 2 || t.maximum() != NUMS - 2)
            System.out.println("FindMin or FindMax error!");

        for (int i = 2; i < NUMS; i += 2)
            if (!t.contains(i))
                System.out.println("Find error1!");

        for (int i = 1; i < NUMS; i += 2) {
            if (t.contains(i))
                System.out.println("Find error2!");
        }
    }

}
