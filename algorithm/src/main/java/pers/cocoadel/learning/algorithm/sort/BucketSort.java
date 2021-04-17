package pers.cocoadel.learning.algorithm.sort;

import java.util.Arrays;

public class BucketSort implements Sort {

    @Override
    public void sort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        int len = array.length;

        //计算桶的数量
        int max = findMax(array);
        int maxLen = maxLen(max);

        int step = 1000;
        if (maxLen < 5) {
            step = (int) Math.pow(10, maxLen - 1);
        }
        System.out.println("step : " + step);

        int bucketLen = max / step + 1;

        System.out.println("bucket len: " + bucketLen);

        int[][] buckets = new int[bucketLen][len];
        int[] indexs = new int[bucketLen];

        //把数据散落到不同的桶
        for (int i = 0; i < len; i++) {
            int bucketIndex = array[i] / step;
            buckets[bucketIndex][indexs[bucketIndex]++] = array[i];
        }

        // 对每个桶进行排序
        for (int i = 0; i < bucketLen; i++) {
            insertSort(buckets[i], indexs[i]);
        }

        //把桶里面的数据依次放置到原始数组
        int index = 0;
        for (int i = 0; i < bucketLen; i++) {
            int bLen = indexs[i];
            for (int j = 0; j < bLen; j++) {
                array[index++] = buckets[i][j];
            }
        }
    }

    private int findMax(int[] array) {
        int max = array[0];
        for (int n : array) {
            max = Math.max(max, n);
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

    private Sort quickSort = new QuickSort();

    private void insertSort(int[] array, int len) {
        int[] tmp = Arrays.copyOf(array,len);
        quickSort.sort(tmp);
        System.arraycopy(tmp,0,array,0,len);
//        for (int i = 1; i < len; i++) {
//            int value = array[i];
//            int j = i;
//            while (j > 0 && array[j - 1] > value) {
//                array[j] = array[j - 1];
//                j--;
//            }
//            array[j] = value;
//        }
    }
}
