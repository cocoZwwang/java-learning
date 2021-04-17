package pers.cocoadel.learning.algorithm.heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 基于数组的大顶堆
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class ArrayBinaryHeap<T> implements Heap<T> {
    private int size;

    private Object[] array;

    private int capacity;

    private final Comparator<T> comparator;

    public ArrayBinaryHeap(int capacity, Comparator<T> comparator) {
        this.size = 0;
        this.comparator = comparator;
        this.capacity = capacity;
        array = new Object[capacity];
    }

    public ArrayBinaryHeap(int size) {
        this(size, null);
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return (T) array[0];
    }

    @Override
    public T poll() {
        if (isEmpty()) {
            return null;
        }
        T t = (T) array[0];
        array[0] = array[size - 1];
        size--;
        siftDown(0);
        return t;
    }

    @Override
    public T delete(int i) {
        if (isEmpty()) {
            return null;
        }
        T t = (T) array[i];
        array[i] = array[size - 1];
        size--;
        siftDown(i);
        return t;
    }

    @Override
    public boolean insert(T t) {
        if (isFull()) {
            return false;
        }

        array[size] = t;
        size++;
        siftUp(size - 1);
//        System.out.println(Arrays.toString(array));

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
        List<T> list = (List<T>) Arrays.asList(array);
        List<String> strs = list.stream().map(Objects::toString).collect(Collectors.toList());
        System.out.println(strs);
    }

    private void siftUp(int index){
        T value = (T) array[index];
        int parentIndex = parent(index);
        while (index > 0 && compareTo(value, (T) array[parentIndex]) > 0) {
            array[index] = array[parentIndex];
            index = parentIndex;
            parentIndex = parent(index);
        }
        array[index] = value;
    }

    private void siftDown(int index) {
        T value = (T) array[index];
        int maxChild = maxChild(index);
        while (maxChild != -1 && compareTo((T) array[maxChild], value) > 0) {
            array[index] = array[maxChild];
            index = maxChild;
            maxChild = maxChild(index);
        }
        array[index] = value;
    }

    private int maxChild(int index) {
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        if (leftChild >= size) {
            return -1;
        }
        if (rightChild >= size) {
            return leftChild;
        }
        return compareTo((T) array[leftChild], (T) array[rightChild]) > 0 ? leftChild : rightChild;
    }

    private int parent(int index){
        return (index - 1) / 2;
    }

    private int compareTo(T t1, T t2) {
        if (comparator != null) {
            return comparator.compare(t1, t2);
        }
        Comparable<T> c1 = (Comparable<T>) t1;
        return c1.compareTo(t2);
    }
}
