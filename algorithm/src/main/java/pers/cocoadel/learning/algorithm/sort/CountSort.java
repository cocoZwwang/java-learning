package pers.cocoadel.learning.algorithm.sort;

import java.util.Arrays;

public class CountSort implements Sort {

    @Override
    public void sort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        int max = findMax(array);
        int[] counts = new int[max + 1];
        count(array, counts);
        countPres(counts);
        sort(array, counts);
    }

    private int findMax(int[] array) {
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            max = Math.max(max, array[i]);
        }
        return max;
    }

    private void count(int[] array, int[] counts) {
        for (int n : array) {
            counts[n]++;
        }
    }

    private void countPres(int[] counts) {
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }
    }

    private void sort(int[] array, int[] counts) {
        int[] tmp = Arrays.copyOf(array, array.length);
        for (int i = tmp.length - 1; i >= 0; i--) {
            int t = tmp[i];
            if (counts[t]-- > 0) {
                array[counts[t]] = t;
            }
        }
    }
}
