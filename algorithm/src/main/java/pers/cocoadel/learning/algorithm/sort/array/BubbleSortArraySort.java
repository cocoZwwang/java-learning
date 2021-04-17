package pers.cocoadel.learning.algorithm.sort.array;

import java.util.Comparator;

/**
 * 冒泡排序
 *
 * 最好时间复杂度：O(N)，如果数组本来有序，只需要遍历一次。
 * 最坏时间复杂度：O(N^2)，而且每次都需要比较和交换。
 * 平均时间复杂度：O(N^2)
 * 空间复杂度：O(1)
 * 是否是原地排序：是
 * 稳定性：稳定
 * @param <T>
 */
public class BubbleSortArraySort<T extends Comparable<T>> implements ArrayComparableSort<T> {

    @Override
    public void sort(T[] array, int left, int end, Comparator<T> comparator) {
        if (end - left < 2) {
            return;
        }
        for (int maxIndex = end - 1; maxIndex > left; maxIndex--) {
            boolean swap = false;
            for (int i = left; i < maxIndex; i++) {
                if (compareTo(array[i], array[i + 1], comparator) > 0) {
                    swap(array, i, i + 1);
                    swap = true;
                }
            }
            if (!swap) {
                break;
            }
        }
    }
}
