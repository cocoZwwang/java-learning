package pers.cocoadel.learning.algorithm.sort.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 归并排序
 * 最好时间复杂度：O(N*logN)。
 * 最坏时间复杂度：O(N*logN)。
 * 平均时间复杂度：O(N*logN)。
 * 空间复杂度：O(N)
 * 是否是原地排序：不是
 * 稳定性：稳定
 * @param <T>
 */
public class MergeArraySort<T extends Comparable<T>> implements ArrayComparableSort<T> {


    @Override
    public void sort(T[] array, int left, int end, Comparator<T> comparator) {
        if (array == null || array.length < 2) {
            return;
        }
        int len = array.length;
        List<T> list = new ArrayList<>(len);
        merge(array, left, end - 1, list, comparator);
    }

    private void merge(T[] array, int left, int right, List<T> list, Comparator<T> comparator) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;
        merge(array, left, mid, list, comparator);
        merge(array, mid + 1, right, list, comparator);
        mergeTwoArray(array, left, mid, right, list, comparator);
    }

    private void mergeTwoArray(T[] array, int left, int mid, int right, List<T> list, Comparator<T> comparator) {
        //left -》 right 本来有序
        if (compareTo(array[mid], array[mid + 1], comparator) <= 0) {
            return;
        }
        list.addAll(Arrays.asList(array).subList(left, right + 1));
        int len = right - left + 1;
        int leftLen = mid - left + 1;
        int i = 0;
        int j = leftLen;
        int index = left;
        while (i < leftLen && j < len) {
            array[index++] = compareTo(list.get(i), list.get(j), comparator) <= 0 ? list.get(i++) : list.get(j++);
        }
        while (i < leftLen) {
            array[index++] = list.get(i++);
        }

        while (j < len) {
            array[index++] = list.get(j++);
        }
        list.clear();
    }
}
