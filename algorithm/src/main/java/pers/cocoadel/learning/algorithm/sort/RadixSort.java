package pers.cocoadel.learning.algorithm.sort;

import java.util.Arrays;

public class RadixSort implements Sort {

    @Override
    public void sort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        int max = findMax(array);
        int maxLen = maxLen(max);

        int[] tmp = new int[array.length];
        int[] counts = new int[10];

        int divisor = 1;
        for (int i = 0; i < maxLen; i++) {
            countingSort(array, tmp, counts, divisor);
            System.arraycopy(tmp, 0, array, 0,array.length);
            divisor *= 10;
        }
    }

    private int findMax(int[] array) {
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            max = Math.max(max, array[i]);
        }
        return max;
    }

    private int maxLen(int max) {
        int len = 0;
        while (max > 0) {
            len++;
            max /= 10;
        }
        return len;
    }

    private void countingSort(int[] array, int[] tmp, int[] count, int divisor) {
        for (int i : array) {
            int n = (i / divisor) % 10;
            count[n]++;
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        for (int i = array.length - 1; i >= 0; i--) {
            int n = (array[i] / divisor) % 10;
            if (count[n]-- > 0) {
                tmp[count[n]] = array[i];
            }
        }
        Arrays.fill(count, 0);
    }

}
