package pers.cocoadel.learning.algorithm.sort.array;

import java.util.Comparator;

public interface ArrayComparableSort<T extends Comparable<T>> extends ArraySort<T> {

    @Override
    default void sort(T[] array) {
        sort(array, null);
    }

    @Override
    default void sort(T[] array, int start) {
        sort(array, start, null);
    }

    @Override
    default void sort(T[] array, int left, int end) {
        sort(array, left, end, null);
    }

    default void sort(T[] array, Comparator<T> comparator) {
        sort(array, 0, comparator);
    }

    default void sort(T[] array, int start, Comparator<T> comparator) {
        sort(array, start, array.length, comparator);
    }

    void sort(T[] array, int left, int end, Comparator<T> comparator);

    default int compareTo(T t1, T t2, Comparator<T> comparator) {
        if (comparator == null) {
            return t1.compareTo(t2);
        }
        return comparator.compare(t1, t2);
    }
}
