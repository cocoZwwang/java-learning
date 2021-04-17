package pers.cocoadel.learning.algorithm.sort.array;

public interface ArraySort<T> {

    void sort(T[] array);

    void sort(T[] array,int start);

    void sort(T[] array, int left, int end);

    default void swap(T[] array, int i, int j) {
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}
