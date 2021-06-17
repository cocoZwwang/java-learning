package pers.cocoadel.java.learning.regex;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 测试正则表达式：多个匹配
 */
public class HrefMatch {
    public static void main(String[] args) throws Exception {
        try (InputStream in = System.in;
             Scanner scanner = new Scanner(in);) {

            System.out.println("请输入一个网址：");
            String url = scanner.nextLine();
            if (url == null || url.length() == 0) {
                url = "https://baidu.com";
            }

            try (
                    InputStream urlIn = new URL(url).openStream();
                    BufferedInputStream bis = new BufferedInputStream(urlIn);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ) {
                byte[] bytes = new byte[1024];

                int read = -1;
                while ((read = bis.read(bytes)) != -1) {
                    bos.write(bytes, 0, read);
                }
                String text = bos.toString(String.valueOf(StandardCharsets.UTF_8));
//                System.out.println("text = " + text);
                //
                String patternString = "<a\\s+href\\s*=\\s*(\"[^\"]*\"|[^\\s>]*)\\s*>";
                Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(text);
                while (matcher.find()) {
                    String s = matcher.group();
                    System.out.println(s);
                }
            }
        }
    }
}
