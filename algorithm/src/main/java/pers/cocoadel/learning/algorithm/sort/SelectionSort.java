package pers.cocoadel.learning.algorithm.sort;

/**
 * 选择排序
 */
public class SelectionSort implements Sort{

    @Override
    public void sort(int[] array) {
        if(array == null || array.length <= 1){
            return;
        }

        int len = array.length;
        for(int i = 0; i < len - 1; i++){
            int minIndex = i;
            for (int j = i + 1; j < len; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            swap(array,i,minIndex);
        }
    }
}
