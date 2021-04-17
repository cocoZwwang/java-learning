package pers.cocoadel.learning.algorithm.sort.array;


import pers.cocoadel.learning.algorithm.sort.*;

import java.util.Arrays;
import java.util.Comparator;

import static com.google.common.base.Preconditions.checkArgument;

public class ArraySortTest {

    public static void main(String[] args) {
        for (int cnt = 0; cnt < 1; cnt++) {
//            int[] tmp = ArrayCreator.randomSortArray(1000_000_0,1000_000_0/ 2);
//            int[] tmp = ArrayCreator.randomSortArray(1000_000_0,100);
            int[] tmp = ArrayCreator.randomSortArray(1000_0);
//            int[] tmp = new int[]{1, 2, 10, 5, 4, 6, 7, 8, 9, 9999};
//            System.out.println("排序前数组：" + Arrays.toString(array));

//            ArraySort<Integer> bubbleSort = new BubbleSortArraySort<>();
//            ArraySort<Integer> selectionSort = new SelectionArraySort<>();
            ArraySort<Integer> heapSort = new HeapArraySort2<>();
//            ArraySort<Integer> insertSort = new InsertionArraySort<>();
//            ArraySort<Integer> shellSort = new ShellArraySort<>();
            ArraySort<Integer> mergeSort = new MergeArraySort<>();
            ArraySort<Integer> quickSort = new QuickArraySort<>();


            Integer[] array = new Integer[tmp.length];
            for (int i = 0; i < array.length; i++) {
                array[i] = tmp[i];
            }

            //java 自带 系统排序，用于和自己实现的排序结构进行对比。
            Integer[] checkArray = Arrays.copyOf(array, array.length);
            int left = 100;
            int end = array.length - 100;
            Arrays.sort(checkArray, left, end, Comparator.comparingInt(o -> o));

            Integer[] dest = new Integer[array.length];

//            System.arraycopy(array, 0, dest, 0, array.length);
//            checkSort(bubbleSort, dest, left, end, checkArray);

//            System.arraycopy(array, 0, dest, 0, array.length);
//            checkSort(selectionSort, dest, left, end, checkArray);
//
            System.arraycopy(array, 0, dest, 0, array.length);
            checkSort(heapSort, dest, left, end, checkArray);

//            System.arraycopy(array, 0, dest, 0, array.length);
//            checkSort(insertSort, dest, left, end, checkArray);
//
//            System.arraycopy(array, 0, dest, 0, array.length);
//            checkSort(shellSort, dest, left, end, checkArray);

            System.arraycopy(array, 0, dest, 0, array.length);
            checkSort(mergeSort, dest, left, end, checkArray);

            System.arraycopy(array, 0, dest, 0, array.length);
            checkSort(quickSort, dest, left, end, checkArray);
//
//            System.arraycopy(array,0,dest,0,array.length);
//            checkSort(quickSort,dest);
//
//            System.arraycopy(array,0,dest,0,array.length);
//            checkSort(countSort, dest);
//
//            System.arraycopy(array, 0, dest, 0, array.length);
//            checkSort(radixSort, dest);
//
//            System.arraycopy(array, 0, dest, 0, array.length);
//            checkSort(bucketSort2, dest);
//
//            System.arraycopy(array, 0, dest, 0, array.length);
//            checkSort(bucketSort, dest);
//
//            System.arraycopy(array,0,dest,0,array.length);
//            checkSort(systemSort,dest);
        }
    }

    private static void checkSort(ArraySort<Integer> sort, Integer[] array, int left, int end, Integer[] checkArray) {
        System.out.println("排序器：" + sort.getClass().getName());

        long time = System.currentTimeMillis();
        sort.sort(array, left, end);
        long takeTime = System.currentTimeMillis() - time;
        System.out.println("排序耗时：" + takeTime);

        if (array == null || array.length == 0) {
            System.out.println("测试数组为空！！");
            return;
        }

//        System.out.printf("checkArray：%s\n", Arrays.toString(checkArray));
//        System.out.printf("排序后数组：%s\n", Arrays.toString(array));

        for (int i = 1; i < array.length; i++) {
            checkArgument(checkArray[i].intValue() == array[i].intValue());
        }

        System.out.println("排序通过！");
    }
}
