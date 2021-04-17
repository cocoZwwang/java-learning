package pers.cocoadel.learning.algorithm.sort;


import static com.google.common.base.Preconditions.checkArgument;

public class ArraySortTest {

    public static void main(String[] args) {
        for(int cnt = 0; cnt < 1; cnt++){
//            int[] array = ArrayCreator.randomSortArray(1000_000_0,1000_000_0/ 2);
//            int[] array = ArrayCreator.randomSortArray(1000_000_0,100);
//            int[] array = ArrayCreator.randomSortArray(1000_0);
            int[] array = new int[]{1, 2, 10, 5, 4, 6, 7, 8, 9, 9999};
//            System.out.println("排序前数组：" + Arrays.toString(array));

            Sort bubbleSort = new BubbleSort();
            Sort selectionSort = new SelectionSort();
            Sort insertionSort = new InsertionSort();
            Sort binaryInsertionSort = new BinaryInsertionSort();
            Sort shellSort = new ShellSort();
            Sort mergeSort = new MergeSort();
            Sort quickSort = new QuickSort();
            Sort countSort = new CountSort();
            Sort radixSort = new RadixSort();
            Sort bucketSort2 = new BucketSort2();
            Sort bucketSort = new BucketSort();

            Sort systemSort = new SystemSort();

            int[] dest = new int[array.length];

//            System.arraycopy(array,0,dest,0,array.length);
//            checkSort(bubbleSort,dest);
//
//            System.arraycopy(array,0,dest,0,array.length);
//            checkSort(selectionSort,dest);

//            System.arraycopy(array,0,dest,0,array.length);
//            checkSort(insertionSort,dest);
//
//            System.arraycopy(array,0,dest,0,array.length);
//            checkSort(binaryInsertionSort,dest);
//
            System.arraycopy(array,0,dest,0,array.length);
            checkSort(shellSort,dest);

            System.arraycopy(array,0,dest,0,array.length);
            checkSort(mergeSort,dest);

            System.arraycopy(array,0,dest,0,array.length);
            checkSort(quickSort,dest);

            System.arraycopy(array,0,dest,0,array.length);
            checkSort(countSort, dest);

            System.arraycopy(array, 0, dest, 0, array.length);
            checkSort(radixSort, dest);

            System.arraycopy(array, 0, dest, 0, array.length);
            checkSort(bucketSort2, dest);

            System.arraycopy(array, 0, dest, 0, array.length);
            checkSort(bucketSort, dest);

            System.arraycopy(array,0,dest,0,array.length);
            checkSort(systemSort,dest);
        }
    }

    private static void checkSort(Sort sort, int[] array) {
        System.out.println("排序器：" + sort.getClass().getName());

        long time = System.currentTimeMillis();
        sort.sort(array);
        long takeTime = System.currentTimeMillis() - time;
        System.out.println("排序耗时：" + takeTime);

        if(array == null || array.length == 0){
            System.out.println("测试数组为空！！");
            return;
        }

//        System.out.printf("排序后数组：%s\n", Arrays.toString(array));
        int prev = array[0];
        for (int i = 1; i < array.length; i++) {
            checkArgument(prev <= array[i]);
            prev = array[i];
        }

        System.out.println("排序通过！");
    }
}
