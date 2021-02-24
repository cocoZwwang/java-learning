package pers.cocoadel.learning.algorithm.array;

import java.util.Arrays;

public class SparseArrayTest {

    /**
     * 测试 稀疏数组 和 二维数组 的转换
     */
    public static void main(String[] args) {
        int[][] source = new int[11][11];
        source[1][2] = 1;
        source[2][3] = 2;
        source[3][4] = 2;

        System.out.println("原始数组：");
        printArray(source);

        int[][] sparseArray = SparseArray.toSparseArray(source, 0);
        System.out.println("稀疏数组：");
        printArray(sparseArray);


        System.out.println("还原原始数组：");
        printArray(SparseArray.restoreArray(sparseArray, 0));
    }

    private static void printArray(int[][] array) {
        for (int[] ints : array) {
            System.out.println(Arrays.toString(ints));
        }
    }
}
