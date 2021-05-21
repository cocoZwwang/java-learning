package pers.cocoadel.learning.algorithm.hash.consistent;


import java.util.List;
import java.util.Random;

public class Utils {

    private static final Random random = new Random();

    /**
     * 计算标准差
     */
    public static double stdDeviation(List<Double> list) {
        int m = list.size();
        double dAve = list.stream().mapToDouble(Double::doubleValue).average().orElse(0d);
        double dVar = 0.0d;

        for (Double aDouble : list) {
            dVar += (aDouble - dAve) * (aDouble - dAve);
        }
//        return Math.sqrt(dVar / (m - 1)); // 样本标准差
        return Math.sqrt(dVar / m);
    }

    public static String randomString(int len) {
        char[] chars = new char[len];
        int charSize = 90 - 65;
        for(int i = 0; i < len; i++){
            chars[i] = (char)((random.nextInt(charSize) + 65));
        }
        return new String(chars);
    }

}
