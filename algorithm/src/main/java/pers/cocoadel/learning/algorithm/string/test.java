package pers.cocoadel.learning.algorithm.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class test {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int maxW = Integer.parseInt(reader.readLine());
        String line2 = reader.readLine();
        String line3 = reader.readLine();

        String[] array2 = line2.split(" ");
        String[] array3 = line3.split(" ");

        int[] weights = new int[array2.length];
        int[] values = new int[array3.length];

        for (int i = 0; i < array2.length; i++) {
            weights[i] = Integer.parseInt(array2[i]);
        }
//        System.out.println("weights:" + Arrays.toString(weights));

        for (int i = 0; i < array3.length; i++) {
            values[i] = Integer.parseInt(array3[i]);
        }

//        System.out.println("values:" + Arrays.toString(values));

        int len = weights.length;
        int[][] f = new int[len + 1][maxW + 1];

        for (int i = 1; i <= len; i++) {
            for (int w = weights[i - 1]; w <= maxW; w++) {
                f[i][w] = Math.max(f[i - 1][w], f[i - 1][w - weights[i - 1]] + values[i - 1]);
            }
        }

        System.out.println(f[len][maxW]);
    }
}
