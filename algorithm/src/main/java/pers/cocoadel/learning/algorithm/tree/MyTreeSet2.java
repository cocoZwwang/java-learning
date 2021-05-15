package pers.cocoadel.learning.algorithm.tree;

import java.util.*;

/**
 * 编写 TreeSet 类的实现程序，其中相关的迭代器使用二叉搜索树，在每个节点上添加通向下一个最小节点和下一个最大节点的链。
 * @param <AnyType>
 */
public class MyTreeSet2<AnyType extends Comparable<? super AnyType>> {

    private static class BinaryNode<AnyType> {
        BinaryNode(AnyType theElement) {
            this(theElement, null, null, null, null);
        }

        BinaryNode(AnyType theElement,
                   BinaryNode<AnyType> lt, BinaryNode<AnyType> rt,
                   BinaryNode<AnyType> nt, BinaryNode<AnyType> pv) {
            element = theElement;
            left = lt;
            right = rt;
            next = nt;
            prev = pv;
        }

        AnyType element;
        BinaryNode<AnyType> left;
        BinaryNode<AnyType> right;
        BinaryNode<AnyType> next;
        BinaryNode<AnyType> prev;
    }

    private BinaryNode<AnyType> root;
    int modCount = 0;

    public MyTreeSet2() {
        root = null;
    }

    public void makeEmpty() {
        modCount++;
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains(AnyType x) {
        return contains(x, root);
    }

    public AnyType findMin() throws UnderflowException {
        if (isEmpty())
            throw new UnderflowException();
        else
            return findMin(root).element;
    }

    public AnyType findMax() throws UnderflowException {
        if (isEmpty())
            throw new UnderflowException();
        else
            return findMax(root).element;
    }

    public void insert(AnyType x) {
        root = insert(x, root, null, null);
    }

    public void remove(AnyType x) {
        root = remove(x, root);
    }


    public void printTree() {
        if (isEmpty())
            System.out.println("Empty tree");
        else
            printTree(root);
    }

    private void printTree(BinaryNode<AnyType> t) {
        if (t != null) {
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }

    private boolean contains(AnyType x, BinaryNode<AnyType> t) {
        if (t == null)
            return false;
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0)
            return contains(x, t.left);
        else if (compareResult > 0)
            return contains(x, t.right);
        else
            return true; // match
    }

    private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
        if (t == null)
            return null;
        else if (t.left == null)
            return t;
        return findMin(t.left);
    }

    private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t) {
        if (t == null)
            return null;
        else if (t.right == null)
            return t;
        return findMax(t.right);
    }

    private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> t,
                                       BinaryNode<AnyType> nt, BinaryNode<AnyType> pv) {
        if (t == null) {
            modCount++;
            BinaryNode<AnyType> newNode = new BinaryNode<AnyType>(x, null, null, nt, pv);
            if (nt != null) {
                nt.prev = newNode;
            }
            if (pv != null) {
                pv.next = newNode;
            }
            return newNode;
        }
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0)
            t.left = insert(x, t.left, t, pv);
        else if (compareResult > 0) {
            t.right = insert(x, t.right, nt, t);
        } else
            ; // duplicate
        return t;
    }

    private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) {
        if (t == null)
            return t; // not found
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0)
            t.left = remove(x, t.left);
        else if (compareResult > 0)
            t.right = remove(x, t.right);
        else if (t.left != null && t.right != null) // two children
        {
            t.element = findMin(t.right).element;
            t.right = remove(t.element, t.right);
        } else {
            modCount++;
            t.prev.next = t.next; // update next and prev links
            t.next.prev = t.prev;
            t = (t.left != null) ? t.left : t.right;
        }
        return t;
    }

    public java.util.Iterator<AnyType> iterator() {
        return new MyTreeSet2Iterator();
    }

    private class MyTreeSet2Iterator implements java.util.Iterator<AnyType> {
        private BinaryNode<AnyType> current = findMin(root);
        private BinaryNode<AnyType> previous;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;
        private boolean atEnd = false;

        public boolean hasNext() {
            return !atEnd;
        }

        public AnyType next() {
            if (modCount != expectedModCount)
                throw new java.util.ConcurrentModificationException();
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            AnyType nextItem = current.element;
            previous = current;
            current = current.next;
            if (current == null)
                atEnd = true;
            okToRemove = true;
            return nextItem;
        }

        public void remove() {
            if (modCount != expectedModCount)
                throw new java.util.ConcurrentModificationException();
            if (!okToRemove)
                throw new IllegalStateException();
            MyTreeSet2.this.remove(previous.element);
            okToRemove = false;
        }
    }
}