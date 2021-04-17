package pers.cocoadel.learning.algorithm.sort;


/**
 * 归并排序
 */
public class MergeSort implements Sort {

    /**
     * 子区间长度小于这个阈值的时候，使用插入排序
     */
    private static final int INSERTION_SORT_THRESHOLD = 47;

    @Override
    public void sort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        //申请一个辅助数组用于归并操作
        int[] tmp = new int[array.length];
        mergeSort(array, 0, array.length - 1,tmp);
    }

    private void mergeSort(int[] array, int left, int right,int[] tmp) {
        //子区间小于长度阈值，直接使用插入排序
        //测试中原始数组长度超过 1000_000 的情况下会明显比不用阈值插入要快
        if (right - left <= INSERTION_SORT_THRESHOLD) {
            insertSort(array,left,right);
            return;
        }
        int mid = (left + right) >>> 1;
        mergeSort(array, left, mid,tmp);
        mergeSort(array, mid + 1, right,tmp);
        mergeTwoSort(array, left, mid, right,tmp);
    }

    /**
     * 合并两部分有序数组区间
     */
    private void mergeTwoSort(int[] array, int left, int mid, int right,int[] tmp) {
        //两个子区间本身有序则直接返回
        if(array[mid] <= array[mid + 1]){
            return;
        }
        int len = right - left + 1;
        System.arraycopy(array, left, tmp, 0, len);
        int k = 0;
        int i = 0;
        int leftLen = mid + 1 - left;
        int j = leftLen;
        while (i < leftLen && j < len) {
            //这里的判断一定要带 =  ，否则会破坏归并排序的稳定性。
            array[left + k] = tmp[i] <= tmp[j] ? tmp[i++] : tmp[j++];
            k++;
        }
        if (i < leftLen) {
            System.arraycopy(tmp, i, array, left + k, leftLen - i);
        } else if (j < len) {
            System.arraycopy(tmp, j, array, left + k, len - j);
        }
    }

    /**
     * 插入排序
     */
    private void insertSort(int[] array,int left,int right){
        for(int i = left + 1; i <= right; i++){
            int tmp = array[i];
            int j = i;
            while(j > left && tmp < array[j - 1]){
                array[j] = array[--j];
            }
            array[j] = tmp;
        }
    }


    private String space(int cnt){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < cnt; i++){
            sb.append(" ");
        }
        return sb.toString();
    }

}
