package pers.cocoadel.learning.algorithm.sort;

public interface Sort {

    void sort(int[] array);

    default void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

}
