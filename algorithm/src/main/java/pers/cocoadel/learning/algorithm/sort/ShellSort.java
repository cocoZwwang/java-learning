package pers.cocoadel.learning.algorithm.sort;

/**
 * 希尔排序
 */
public class ShellSort implements Sort {

    @Override
    public void sort(int[] array) {
        int len = array.length;
        int gap = getGap(len);
        while (gap > 0) {
            for(int i = 0; i < len; i++){
                sortByGap(array,i,gap);
            }
            gap = getGap(gap);
        }
    }

    protected int getGap(int lastGap) {
        return lastGap / 2;
    }

    protected void sortByGap(int[] array,int end,int gap){
        int tmp = array[end];
        int i = end;
        while(i - gap >= 0 && tmp < array[i - gap]){
            array[i] = array[i - gap];
            i -= gap;
        }
        array[i] = tmp;
    }

}
