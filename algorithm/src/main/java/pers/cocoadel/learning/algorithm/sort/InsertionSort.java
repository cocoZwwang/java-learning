package pers.cocoadel.learning.algorithm.sort;

/**
 * 插入排序
 */
public class InsertionSort implements Sort{

    @Override
    public void sort(int[] array) {
        if(array == null || array.length <= 1){
            return;
        }

        int len = array.length;
        for (int i = 0; i < len; i++) {
            //比 value 大的都往后移动一格
            int value = array[i];
            int j = i;
            while(j > 0 && array[j - 1] > value){
                array[j] = array[--j];
            }
            //后移腾出的位置赋值 value
            array[j] = value;
        }
    }
}
