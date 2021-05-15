package pers.cocoadel.learning.algorithm.tree;

/**
 * 搜索树结点
 *
 * @param <K>
 * @param <V>
 */
public class BinaryNode<K, V> {
    private K key;
    private V value;

    private int height;

    private BinaryNode<K, V> left;

    private BinaryNode<K, V> right;

    public BinaryNode<K, V> getParent() {
        return parent;
    }

    public void setParent(BinaryNode<K, V> parent) {
        this.parent = parent;
    }

    private BinaryNode<K,V> parent;

    BinaryNode(K key, V v) {
        this.key = key;
        this.value = v;
        this.height = 0;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public BinaryNode<K, V> getLeft() {
        return left;
    }

    public void setLeft(BinaryNode<K, V> left) {
        this.left = left;
    }

    public BinaryNode<K, V> getRight() {
        return right;
    }

    public void setRight(BinaryNode<K, V> right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void reset() {
        left = null;
        right = null;
        key = null;
        value = null;
        parent = null;
    }
}
