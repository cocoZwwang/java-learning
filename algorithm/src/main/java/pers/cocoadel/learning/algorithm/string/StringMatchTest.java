package pers.cocoadel.learning.algorithm.string;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkArgument;

public class StringMatchTest {

    public static void main(String[] args) {
//        StringMatch stringMatch = new BMMatch();
//        StringMatch stringMatch = new KMPMatch();
//        StringMatch stringMatch = new SimpleBMStringMatcher();
//        StringMatch stringMatch = new BMStringMatcher();
        StringMatch stringMatch = new KMPStringMatcher();
        matchTest(stringMatch, "cbacabc", "abcacabcbcbacabcbd");
        matchTest(stringMatch, "babbabbabbabbab", "abbabbabbabbabbabbabbabbabba");
        matchTest(stringMatch, "aaaa", "aaaaaaaaaaaaaaaaa");
        matchTest(stringMatch, "aaaa", "baaaaaaaaaaaaaaaaa");
        matchTest(stringMatch, "aaaa", "baaaa");
        matchTest(stringMatch, "", "abcacabcbcbacabc");
        matchTest(stringMatch, "cabcab", "abcacabcbcabcabc");
        matchTest(stringMatch, "eee", "");
//        matchTest(stringMatch, "", "");
        matchTest(stringMatch, "StringMatch stringMatch",
                "private static void  matchTest(StringMatch stringMatch, String pattern, String primary) ");
        matchTest(stringMatch, "StringMatch-stringMatch",
                "private static void  matchTest(StringMatch stringMatch, String pattern, String primary) ");
        Predicate<int[]> predicate = (arr ->{
            return false;
        });
    }

    private static void matchTest(StringMatch stringMatch, String pattern, String primary) {
        int fromIndex = 2;
        int index = stringMatch.indexOf(pattern, primary, fromIndex);
        System.out.println("--------------------------------------------");
        System.out.println("pattern: " + pattern);
        System.out.println("primary: " + primary);
        // 使用 jdk 的字符匹配进行对比
        int checkIndex = primary.indexOf(pattern,fromIndex);
        System.out.println("index: " + index + " checkIndex: " + checkIndex);
        checkArgument(checkIndex == index);
        if (index < 0) {
            System.out.println("没找到需要的字符串");
        } else {
            String result = primary.substring(index, index + pattern.length());
            System.out.println("找到字符串：" + result);
            checkArgument(result.equals(pattern));
        }
    }
}
