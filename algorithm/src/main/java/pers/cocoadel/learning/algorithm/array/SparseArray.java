package pers.cocoadel.learning.algorithm.array;


import java.util.Arrays;

public class SparseArray {

    /**
     * 二维数组转稀疏数组
     */
    public static int[][] toSparseArray(int[][] src, int invalid) {
        if (src == null || src.length == 0 || src[0] == null || src[0].length == 0) {
            return new int[0][0];
        }
        int rowCount = src.length;
        int colCount = src[0].length;
        // 获取数组的元素有效个数
        int sum = 0;
        for (int[] ints : src) {
            for (int j = 0; j < colCount; j++) {
                if (ints[j] != invalid) {
                    sum++;
                }
            }
        }

        //创建稀疏数组
        int[][] result = new int[sum + 1][3];
        //初始化第一行
        result[0] = new int[]{rowCount, colCount, sum};

        int r = 1;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (src[i][j] != invalid) {
                    result[r++] = new int[]{i, j, src[i][j]};
                }
            }
        }

        return result;
    }

    /**
     * 稀疏数组 还原 二维数组
     */
    public static int[][] restoreArray(int[][] sparseArray, int invalid) {
        int rowCount = sparseArray[0][0];
        int colCount = sparseArray[0][1];

        int[][] result = new int[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            Arrays.fill(result[i],invalid);
        }

        for (int i = 1; i < sparseArray.length; i++) {
            result[sparseArray[i][0]][sparseArray[i][1]] = sparseArray[i][2];
        }
        return result;
    }
}
