package pers.cocoadel.learning.algorithm.sort.array;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HeapArraySort2<T extends Comparable<T>> implements ArrayComparableSort<T> {

    @Override
    public void sort(T[] array, int left, int end, Comparator<T> comparator) {
        //创建大顶堆
        buildHeap(array, left, end, comparator);
        for (int i = end - 1; i > left; i--) {
            swap(array, left, i);
            percolateDown(array, left, left, i, comparator);
        }
    }

    private void buildHeap(T[] array, int left, int end, Comparator<T> comparator) {
        int len = end - left;
        int i = (len - 2) / 2 + left;
        for (; i >= left; i--) {
            percolateDown(array, left, i, end, comparator);
        }
    }

    private void percolateDown(T[] array, int left, int i, int end, Comparator<T> comparator) {
        T t = array[i];
        int child;
        for (; (child = child(left, i)) < end; i = child) {
            if (child != end - 1 && compareTo(array[child + 1], array[child], comparator) > 0) {
                child++;
            }
            if (compareTo(array[child], t, comparator) > 0) {
                array[i] = array[child];
            } else {
                break;
            }
        }
        array[i] = t;
    }

    private int child(int left, int i) {
        //(i - left) * 2 + 1 + left
        return 2 * i - left + 1;
    }
}
