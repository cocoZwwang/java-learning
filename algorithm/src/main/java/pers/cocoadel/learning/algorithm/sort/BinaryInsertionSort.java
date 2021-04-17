package pers.cocoadel.learning.algorithm.sort;


/**
 * 折半插入排序
 */
public class BinaryInsertionSort implements Sort{

    @Override
    public void sort(int[] array) {
        if(array == null || array.length == 0){
            return;
        }
        int len = array.length;
        for (int i = 1; i < len; i++) {
            int target = array[i];
            int firstBigIndex = findFirstBig(array,0,i - 1,target);
//            System.out.println("firstBigIndex: " + firstBigIndex);
            if(firstBigIndex >= 0){
                System.arraycopy(array, firstBigIndex, array, firstBigIndex + 1, i - firstBigIndex);
                array[firstBigIndex] = target;
            }
//            System.out.printf("BinaryInsertionSorting (i: %s, target:%s, firstBigIndex: %s): %s\n" ,
//                    i,target, firstBigIndex,Arrays.toString(array));
        }
    }

    /**
     * 通过二分查找，找到第一个比目标数大的元素的下标,没有则返回 最后一个下标 + 1的负数
     */
    private int findFirstBig(int[] array,int left,int right,int target){
        while(left < right){
            int mid = (left + right) >>> 1;
            if(array[mid] < target){
                left = mid + 1;
            }else{
                right = mid;
            }
        }
        return array[left] > target ? left : -(left + 1);
    }

    public static void main(String[] args) {
        BinaryInsertionSort sort = new BinaryInsertionSort();
//        int[] array = ArrayCreator.randomSortArray(10,0);
        int[] array = new int[]{0,2,4,6,8,10,12};
        int index = sort.findFirstBig(array,0,10,6);
        index = index < 0 ? -index : index;
        System.out.println("index: " + index + " value: " + array[index]);
    }
}
