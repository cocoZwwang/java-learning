package pers.cocoadel.java.learning.regex;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 测试正则表达式：是否匹配
 */
public class RegexTest {

    /**
     * 加入输入正则表达式：(([1-9]|1[0-2]):([0-5][0-9]))[ap]m
     * 输入需要匹配的字符串：11:59am
     * 输出：((11):(59))am
     */
    public static void main(String[] args) throws Exception{
        InputStream in = System.in;
        Scanner scanner = new Scanner(in);
        System.out.println("请输入一个正则表达式：");
        String patternString = scanner.nextLine();
        Pattern pattern = Pattern.compile(patternString);
        System.out.println("pattern = " + pattern);

        while (true) {
            System.out.println("请输入你需要匹配的字符串：");
            String matcherString  = scanner.nextLine();
            Matcher matcher = pattern.matcher(matcherString);
            if (matcher.matches()) {
                System.out.println("Matcher!!");
                int gCnt = matcher.groupCount();
                if (gCnt > 0) {
                    for(int i = 0; i < matcherString.length(); i++){
                        for(int j = 1; j <= gCnt; j++){
                            //说明这是一个空的匹配组
                            if(matcher.start(j) == i && matcher.end(j) == i){
                                System.out.print("()");
                            }
                        }
                        //匹配组的左边界
                        for(int j = 1; j <= gCnt; j++){
                            if (matcher.start(j) == i && matcher.end(j) != i) {
                                System.out.print("(");
                            }
                        }

                        System.out.print(matcherString.charAt(i));
                        //匹配组的右边界
                        for(int j = 1; j <= gCnt; j++){
                            if (matcher.start(j) != i + 1 && matcher.end(j) == i + 1) {
                                System.out.print(")");
                            }
                        }
                    }
                    System.out.println();
                }
            }else{
                System.out.println("匹配失败！！");
            }
        }
    }
}
