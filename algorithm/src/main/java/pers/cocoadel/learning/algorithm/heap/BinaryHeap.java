package pers.cocoadel.learning.algorithm.heap;

import java.util.*;

/**
 * 完全二叉堆
 * 默认大堆
 * 小堆通过 comparator 参数实现
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class BinaryHeap<T> implements Heap<T> {
    private final int capacity;
    private final Object[] array;
    private int size;

    private final Comparator<? super T> comparator;

    public BinaryHeap(int capacity) {
        this(capacity, null);
    }

    public BinaryHeap(int capacity, Comparator<? super T> comparator) {
        this.capacity = capacity;
        array = new Object[capacity];
        this.size = 0;
        this.comparator = comparator;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("the heap is empty");
        }
        return (T) array[0];
    }


    @Override
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("the heap is empty");
        }
        return delete(0);
    }

    @Override
    public T delete(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("index " + i);
        }
        T res = (T) array[i];
        array[i] = array[size - 1];
        size--;
        siftDown(i);
        return res;
    }

    @Override
    public boolean insert(T t) {
        if (isFull()) {
            throw new IllegalArgumentException("the heap is full");
        }
        array[size] = t;
        size++;
        siftUp(size - 1);
        return true;
    }

    @Override
    public boolean isFull() {
        return size == capacity;
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
    public void printHeap() {
        System.out.print("[");
        for (int i = 0; i < size - 1; i++) {
            Object t = array[i];
            System.out.print(t.toString() + ", ");
        }
        System.out.print(array[size - 1].toString());
        System.out.println("]");
    }

    private void siftDown(int i) {
        Object value = array[i];
        while (true) {
            int maxChildIndex = maxChild(i);
            if (maxChildIndex == -1 || compare(value, array[maxChildIndex]) >= 0) {
                break;
            }

            array[i] = array[maxChildIndex];
            i = maxChildIndex;
        }
        array[i] = value;
    }

    private void siftUp(int i) {
        Object value = array[i];
        while (true) {
            int parentIndex = (i - 1) / 2;
            if (i <= 0 || compare(value, array[parentIndex]) <= 0) {
                break;
            }
            array[i] = array[parentIndex];
            i = parentIndex;
        }
        array[i] = value;
    }

    /**
     * 返回索引 i 结点的最大孩子结点的索引
     * 如果 i 是叶子结点则返回 -1
     */
    private int maxChild(int i) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left >= size) {
            return -1;
        }
        if (right >= size) {
            return left;
        }

        return compare(array[left], array[right]) > 0 ? left : right;
    }


    private int compare(Object t1, Object t2) {
        if (comparator == null) {
            Comparable<T> c1 = (Comparable<T>) t1;
            T c2 = (T) t2;
            return c1.compareTo(c2);
        }
        return comparator.compare((T) t1, (T) t2);

    }
}
