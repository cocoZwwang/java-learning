package pers.cocoadel.learning.algorithm.sort.array;

import java.util.Comparator;

public class QuickArraySort<T extends Comparable<T>> implements ArrayComparableSort<T> {
    private final static int CUTOFF = 10;

    @Override
    public void sort(T[] array, int left, int end, Comparator<T> comparator) {
        quickSort(array, left, end - 1, comparator);
    }

    private void quickSort(T[] array, int left, int right, Comparator<T> comparator) {
        if (left + CUTOFF <= right) {
            T pivot = median3(array, left, right, comparator);
            int i = left;
            int j = right - 1;
            for (; ; ) {
                while (compareTo(array[++i], pivot, comparator) < 0) ;
                while (compareTo(array[--j], pivot, comparator) > 0) ;
                if (i < j) {
                    swap(array, i, j);
                } else {
                    break;
                }
            }
            swap(array, i, right - 1);
            quickSort(array, left, i - 1, comparator);
            quickSort(array, i + 1, right, comparator);
        } else {
            insertSort(array, left, right, comparator);
        }
    }

    private void insertSort(T[] array, int left, int right, Comparator<T> comparator) {
        for (int i = left + 1; i <= right; i++) {
            T value = array[i];
            int j = i;
            while (j > left && compareTo(value, array[j - 1], comparator) < 0) {
                array[j] = array[j - 1];
                j--;
            }
            array[j] = value;
        }
    }

    private T median3(T[] array, int left, int right, Comparator<T> comparator) {
        int mid = (left + right) >>> 1;
        if (compareTo(array[mid], array[left], comparator) < 0) {
            swap(array, left, mid);
        }

        if (compareTo(array[right], array[left], comparator) < 0) {
            swap(array, left, right);
        }

        if (compareTo(array[right], array[mid], comparator) < 0) {
            swap(array, right, mid);
        }
        //left  和 right 相当已经排好序了，只需要排 [left + 1,right - 1]，所以把 pivot 放到 right - 1的位置。
        swap(array, mid, right - 1);
        return array[right - 1];
    }

    //让第 k 个元素成为排序后的 pivot
    public void findKth(T[] array, int left, int right, Comparator<T> comparator, int k) {
        if (left + CUTOFF <= right) {
            T pivot = median3(array, left, right, comparator);
            int i = left;
            int j = right - 1;
            for (; ; ) {
                while (compareTo(array[++i], pivot, comparator) < 0) ;
                while (compareTo(array[--j], pivot, comparator) > 0) ;
                if (i < j) {
                    swap(array, i, j);
                } else {
                    break;
                }
            }
            swap(array, i, right - 1);
            //不包含 pivot 左边的数量大于 k - 1 个
            if (i - 1 + 1 > k - 1) {
                findKth(array, left, i - 1, comparator, k);
            } else if (i + 1 >= k - 1) {// 包含 pivot 的左边数量 >= k - 1个
                findKth(array, i + 1, right, comparator, k);
            }
        } else {
            insertSort(array, left, right, comparator);
        }
    }
}
