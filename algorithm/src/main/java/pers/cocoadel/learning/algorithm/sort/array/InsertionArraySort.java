package pers.cocoadel.learning.algorithm.sort.array;

import java.util.Comparator;

/**
 * 插入排序：内循环可以提前终止；如果数组本来比较有序，则性能较好；小规模数组可以视为接近有序数组。
 * 最好时间复杂度：O(N)。如果数组本来就有序，则不需要往前插入。
 * 最坏时间复杂度：O(N^2)。
 * 平均时间复杂度：O(N^2).
 * 空间复杂度：O(1)
 * 是否是原地排序：是
 * 稳定性：稳定
 * @param <T>
 */
public class InsertionArraySort<T extends Comparable<T>> implements ArrayComparableSort<T> {

    @Override
    public void sort(T[] array, int left, int end, Comparator<T> comparator) {
        for(int i = left + 1; i < end; i++){
            T value = array[i];
            while(i > left && compareTo(array[i - 1],value,comparator) > 0){
                array[i] = array[i - 1];
                i--;
            }
            array[i] = value;
        }
    }
}
