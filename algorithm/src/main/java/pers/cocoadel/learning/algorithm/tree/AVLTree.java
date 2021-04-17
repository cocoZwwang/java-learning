package pers.cocoadel.learning.algorithm.tree;


import java.lang.reflect.Array;
import java.util.*;

/**
 * AVL 实现
 * @param <K>
 * @param <T>
 */
public class AVLTree<K, T> implements BinarySearchTree<K, T> {
    private int size;

    private Node root;

    private final Comparator<K> comparator;

    public AVLTree(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    public AVLTree() {
        this(null);
    }

    @Override
    public void put(K key, T value) {
        Comparator<int[]> comparator = new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return 0;
            }
        };
        insertAVLNode(key, value);
    }

    private void insertAVLNode(K key, T value) {
        if (root == null) {
            root = new Node(key, value);
            size++;
            return;
        }

        Node s = new Node(key, value);

        Node a = root;
        Node p = root;
        Node q = null;
        //a 的父亲节点
        Node f = null;

        //找到 s 需要插入的位置的父节点 q
        //找到离 s 需要插入的位置最近的并且平衡因子不是 0 的节点 a
        while (p != null) {
            if (p.bf != 0) {
                a = p;
                f = q;
            }
            q = p;
            int c = compareTo(s.key, p.key);
            if (c == 0) {
                p.value = value;
                return;
            }
            p = c < 0 ? p.left : p.right;
        }

        // 插入 s 节点
        if (compareTo(s.key, q.key) < 0) {
            q.left = s;
        } else {
            q.right = s;
        }

        //修改 a -> s 路径上各个节点的平衡因子
        Node b = null;
        //平衡因子增长
        int d = 0;
        if (compareTo(s.key, a.key) < 0) {
            p = a.left;
            b = p;
            d = 1;
        } else {
            p = a.right;
            b = p;
            d = -1;
        }
        //迭代修改路径上每个节点的 bf 值
        while (p != s) {
            if (compareTo(s.key, p.key) < 0) {
                p.bf = 1;
                p = p.left;
            } else {
                p.bf = -1;
                p = p.right;
            }
        }

        //判断树是否失去了平衡
        boolean balanced = true;
        if (a.bf == 0) {
            a.bf = d;
        } else if (a.bf + d == 0) {
            a.bf = 0;
        } else {
            //失去平衡
            balanced = false;
            //调整平衡树
            if (d == 1) {
                if (b.bf == 1) {
                    b = doLLRotation(a, b);
                } else if (b.bf == -1) {
                    b = doLRRotation(a, b, s);
                }
            } else {
                if (b.bf == 1) {
                    b = doRLRotation(a, b, s);
                } else if (b.bf == -1) {
                    b = doRRRotation(a, b);
                }
            }
        }

        //修改a的双亲 f 的指针域
        if (!balanced) {
            if (f == null) {
                root = b;
            } else if (f.left == a) {
                f.left = b;
            } else {
                f.right = b;
            }
        }
        size++;
    }

    private Node doLLRotation(Node a, Node b) {
        a.left = b.right;
        b.right = a;
        a.bf = 0;
        b.bf = 0;
        return b;
    }

    private Node doRRRotation(Node a, Node b) {
        a.right = b.left;
        b.left = a;
        a.bf = 0;
        b.bf = 0;
        return b;
    }

    private Node doLRRotation(Node a, Node b, Node s) {
        Node c = b.right;
        b.right = c.left;
        a.left = c.right;
        c.left = b;
        c.right = a;
        int diff = compareTo(s.key, c.key);
        if (diff == 0) {
            a.bf = 0;
            b.bf = 0;
        } else if (diff < 0) {
            a.bf = -1;
            b.bf = 0;
        } else {
            a.bf = 0;
            b.bf = 1;
        }
        c.bf = 0;
        return c;
    }

    private Node doRLRotation(Node a, Node b, Node s) {
        Node c = b.left;
        a.right = c.left;
        b.left = c.right;
        c.left = a;
        c.right = b;
        int diff = compareTo(s.key, c.key);
        if (diff == 0) {
            a.bf = 0;
            b.bf = 0;
        } else if (diff < 0) {
            a.bf = 0;
            b.bf = -1;
        } else {
            a.bf = 1;
            b.bf = 0;
        }
        c.bf = 0;
        return c;
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
    public T get(K key) {
        if (isEmpty()) {
            return null;
        }
        Node node = findNode(root, key);
        return node == null ? null : node.value;
    }

    private Node findNode(Node root, K key) {
        if (root == null) {
            return null;
        }

        int c = compareTo(key, root.key);
        if (c == 0) {
            return root;
        } else if (c < 0) {
            return findNode(root.left, key);
        } else {
            return findNode(root.right, key);
        }
    }

    @Override
    public boolean contains(K key) {
        return false;
    }

    @Override
    public void delete(K key) {

    }

    @Override
    public K minimum() {
        return null;
    }

    @Override
    public K maximum() {
        return null;
    }

    @Override
    public K floor(K key) {
        return null;
    }

    @Override
    public K ceiling(K key) {
        return null;
    }

    @Override
    public K lower(K key) {
        return null;
    }

    @Override
    public K higher(K key) {
        return null;
    }

    @Override
    public int rank(K key) {
        return 0;
    }

    @Override
    public K select(int k) {
        return null;
    }

    @Override
    public List<T> values() {
        List<T> list = new LinkedList<>();
        for (K k : this) {
            list.add(get(k));
        }
        return list;
    }

    @Override
    public Iterator<K> iterator() {
        return new InnerIterator(root);
    }

    private int compareTo(K k1, K k2) {
        if (comparator != null) {
            return comparator.compare(k1, k2);
        }
        Comparable<K> c1 = (Comparable<K>) k1;
        return c1.compareTo(k2);
    }

    private class InnerIterator implements Iterator<K> {
        private Node curr;

        private final LinkedList<Node> deque = new LinkedList<>();

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
        private final K key;
        private T value;
        // 平衡因子
        private int bf;

        Node(K key, T value) {
            this.key = key;
            this.value = value;
            this.bf = 0;
        }

        private Node left;

        private Node right;
    }

    protected boolean testAVLTree(){
        return testAVLTree(root) >= 0;
    }

    protected int testAVLTree(Node root){
        if(root == null){
            return 0;
        }

        int left = testAVLTree(root.left);
        int right = testAVLTree(root.right);
        if(left < 0 || right < 0){
            return -1;
        }
        if(Math.abs(left - right) > 1){
            return -1;
        }
        return Math.max(left,right) + 1;
    }
}
