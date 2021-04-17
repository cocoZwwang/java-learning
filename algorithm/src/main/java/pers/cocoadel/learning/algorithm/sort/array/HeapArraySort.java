package pers.cocoadel.learning.algorithm.sort.array;

import java.util.Comparator;
/**
 * 堆排序
 * 最好时间复杂度：O(N*logN)。
 * 最坏时间复杂度：O(N*logN)。
 * 平均时间复杂度：O(N*logN)。
 * 空间复杂度：O(1)
 * 是否是原地排序：是
 * 稳定性：不稳定
 * @param <T>
 */
public class HeapArraySort<T extends Comparable<T>> implements ArrayComparableSort<T> {

    @Override
    public void sort(T[] array, int left, int end, Comparator<T> comparator) {
        heapify(array, left, end,comparator);
        for (int i = end - 1; i >= left; i--) {
            swap(array, i, left);
            siftDown(array, left, left, i, comparator);
        }
    }

    private void heapify(T[] array, int from, int end, Comparator<T> comparator) {
        int len = end - from;
        int i = (len - 1) / 2 + from;
        for (; i >= from; i--) {
            siftDown(array, i, from, len, comparator);
        }
    }

    private void siftDown(T[] array, int index, int from, int end, Comparator<T> comparator) {
        T value = array[index];
        int maxChild = maxChild(array, index, from, end, comparator);
        while (maxChild != -1 && compareTo(array[maxChild], value, comparator) > 0) {
            array[index] = array[maxChild];
            index = maxChild;
            maxChild = maxChild(array, index, from, end, comparator);
        }
        array[index] = value;
    }


    private int maxChild(T[] array, int index, int from, int end, Comparator<T> comparator) {
        index = index - from;
        int leftChild = 2 * index + 1 + from;
        int rightChild = leftChild + 1;
        if (leftChild >= end) {
            return -1;
        }

        if (rightChild >= end) {
            return leftChild;
        }
        return compareTo(array[leftChild], array[rightChild], comparator) >= 0 ? leftChild : rightChild;
    }

}
