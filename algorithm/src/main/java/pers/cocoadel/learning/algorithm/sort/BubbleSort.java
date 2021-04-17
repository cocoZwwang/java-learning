package pers.cocoadel.learning.algorithm.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 */
public class BubbleSort implements Sort{

    @Override
    public void sort(int[] array) {
        if(array == null || array.length <= 1){
            return;
        }
        int len = array.length;
        for(int i = len - 1; i > 0; i--){
            boolean swap = false;
            for (int j = 0; j < i; j++) {
                if(array[j] > array[j + 1]){
                    swap(array,j, j+ 1);
                    swap = true;
                }
            }
            //如果数组没发生交换则证明数据已经有序
            if (!swap) {
                break;
            }
//            System.out.println("BubbleSorting: " + Arrays.toString(array));
        }
    }
}
