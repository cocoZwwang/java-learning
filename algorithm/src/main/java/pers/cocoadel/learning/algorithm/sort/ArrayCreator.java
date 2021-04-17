package pers.cocoadel.learning.algorithm.sort;

import java.util.Random;
import java.util.stream.Stream;

public class ArrayCreator {

    private static final Random random = new Random();

    /**
     * 产生一个随机数组
     * @param n 数组元素值分别是 1...n
     * @param randomCnt 随机两个元素交换位置的次数，次数越大，数组顺序也乱。
     * @return 返回随机数组
     */
    public static int[] randomSortArray(int n,int randomCnt){
        int[] array = Stream
                .iterate(1, k -> k + 1)
                .limit(n).mapToInt(Integer::intValue)
                .toArray();
        while (randomCnt -- > 0){
            int index1 = random.nextInt(n);
            int index2 = random.nextInt(n);
            swap(array,index1,index2);
        }
        return array;
    }

    public static int[] randomSortArray(int n){
        int[] array = new int[n];
        for(int i = 0; i < n; i++){
            array[i] = random.nextInt(n + 1);
        }
        return array;
    }

    /**
     * 返回一个降序的数组
     * @param n 数组的元素范围是 n ... 1
     * @return 返回降序数组
     */
    public static int[] reverseOrderArray(int n){
        return Stream
                .iterate(n,k -> k - 1)
                .limit(n)
                .mapToInt(Integer::intValue)
                .toArray();
    }

    private static void swap(int[] array,int i,int j){
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}
