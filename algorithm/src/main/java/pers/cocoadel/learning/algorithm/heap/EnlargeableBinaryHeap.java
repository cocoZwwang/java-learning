package pers.cocoadel.learning.algorithm.heap;

import java.util.Comparator;

/**
 * 可动态扩容的二叉堆
 * 默认是最小堆
 *
 * @param <T>
 */
public class EnlargeableBinaryHeap<T> implements Heap<T> {
    private static final int DEFAULT_CAPACITY = 10;

    private int size = 0;

    private T[] array;

    private Comparator<T> comparator;

    public EnlargeableBinaryHeap() {
        this(DEFAULT_CAPACITY, null);
    }

    public EnlargeableBinaryHeap(Comparator<T> comparator) {
        this(DEFAULT_CAPACITY, comparator);
    }

    @SuppressWarnings("unchecked")
    public EnlargeableBinaryHeap(int capacity, Comparator<T> comparator) {
        array = (T[]) new Object[capacity + 1];
        this.comparator = comparator;
    }

    @SuppressWarnings("unchecked")
    public EnlargeableBinaryHeap(T[] array) {
        size = array.length;
        int capacity = (size * 2) * 11 / 10;
        this.array = (T[]) new Object[capacity];
        System.arraycopy(array, 0, this.array, 0, size);
        buildHeap();
    }

    public EnlargeableBinaryHeap(T[] array, Comparator comparator) {
        this.comparator = comparator;
        size = array.length;
        int capacity = (size * 2) * 11 / 10;
        this.array = (T[]) new Object[capacity];
        System.arraycopy(array, 0, this.array, 1, size);
        buildHeap();
    }


    @Override
    public boolean insert(T t) {
        if (size == array.length - 1) {
            enlargeCapacity(size * 2 + 1);

        }
        int hole = ++size;
        for (array[0] = t; compareTo(t, array[hole / 2]) < 0; hole /= 2) {
            array[hole] = array[hole / 2];
        }
        array[hole] = t;
        return true;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return array[1];
    }

    @Override
    public T poll() {
        if (isEmpty()) {
            return null;
        }
        T value = array[1];
        array[1] = array[size--];
        percolateDown(1);
        return value;
    }

    @Override
    public T delete(int i) {
        if (size < i) {
            return null;
        }
        T res = array[i];
        array[i] = array[size--];
        percolateDown(i);
        return res;
    }

    @Override
    public boolean isFull() {
        return false;
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
        for (int i = 1; i < size; i++) {
            Object t = array[i];
            System.out.print(t.toString() + ", ");
        }
        System.out.print(array[size].toString());
        System.out.println("]");
    }

    private void buildHeap() {
        for (int i = size / 2; i > 0; i--) {
            percolateDown(i);
        }
    }

    @SuppressWarnings("unchecked")
    private void enlargeCapacity(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        System.arraycopy(array, 0, newArray, 0, array.length);
        array = newArray;
    }

    private void percolateDown(int i) {
        int child;
        T t = array[i];
        for (; i * 2 <= size; i = child) {
            child = i * 2;
            if (child != size && compareTo(array[child + 1], array[child]) < 0) {
                child++;
            }
            if (compareTo(array[child],t) < 0) {
                array[i] = array[child];
            }else{
                break;
            }
        }
        array[i] = t;
    }

    private void percolateUp(int i) {
        int p = i;
        T t = array[i];
        for (array[0] = t; compareTo(t, array[p / 2]) < 0; p /= 2) {
            array[p] = array[p / 2];
        }
        array[p] = t;
    }

    private int compareTo(T t1, T t2) {
        if (comparator != null) {
            return comparator.compare(t1, t2);
        }
        Comparable c = (Comparable) t1;
        return c.compareTo(t2);
    }
}
