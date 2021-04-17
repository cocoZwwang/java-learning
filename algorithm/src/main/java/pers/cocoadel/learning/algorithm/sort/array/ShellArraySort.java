package pers.cocoadel.learning.algorithm.sort.array;

import java.util.Comparator;

public class ShellArraySort<T extends Comparable<T>> implements ArrayComparableSort<T>{
    @Override
    public void sort(T[] array, int left, int end, Comparator<T> comparator) {
        if (array == null || array.length < 2) {
            return;
        }
        int len = end - left;
        for (int gap = gap(len); gap > 0; gap = gap(gap)) {
            sortByGap(array, left, end, gap, comparator);
        }
    }

    protected int gap(int gap){
        return gap / 2;
    }

    protected void sortByGap(T[] array, int left, int end, int gap, Comparator<T> comparator) {
        for (int i = left; i < end; i++) {
            T value = array[i];
            while (i - gap >= left && compareTo(array[i - gap], value, comparator) > 0) {
                array[i] = array[i - gap];
                i = i - gap;
            }
            array[i] = value;
        }
    }
}
