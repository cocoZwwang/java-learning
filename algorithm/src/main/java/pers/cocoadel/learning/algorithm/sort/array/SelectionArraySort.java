package pers.cocoadel.learning.algorithm.sort.array;

import java.util.Comparator;

/**
 * 以升序为默认排序
 * 选择排序：从左向右遍历，每次【当前位置的元素】和【后面区域的最大元素】做比较，交换较小的元素到当前位置，然后继续往下执行，直到最后一个元素。
 * 特点：交换次数较少，如果交换成本很高，可以考虑旋转排序；排序时间和输入数据的顺序无关。
 * 最好时间复杂度：O(N^2)。
 * 最坏时间复杂度：O(N^2)。
 * 平均时间复杂度：O(N^2).
 * 空间复杂度：O(1)
 * 是否是原地排序：是
 * 稳定性：不稳定
 *
 * @param <T>
 */
public class SelectionArraySort<T extends Comparable<T>> implements ArrayComparableSort<T> {

    @Override
    public void sort(T[] array, int left, int end, Comparator<T> comparator) {
        for (int i = left; i < end - 1; i++) {
            int j = i + 1;
            int minIndex = j;
            while (j < end) {
                if (compareTo(array[j], array[minIndex], comparator) < 0) {
                    minIndex = j;
                }
                j++;
            }
            if (compareTo(array[minIndex], array[i], comparator) < 0) {
                swap(array, i, minIndex);
            }
        }
    }
}
