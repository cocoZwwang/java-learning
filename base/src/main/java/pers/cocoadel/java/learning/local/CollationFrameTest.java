package pers.cocoadel.java.learning.local;

import javax.swing.*;
import java.awt.*;
import java.text.Collator;
import java.util.Locale;

/**
 * 字符排序 国际化 测试
 * {@link Collator} 对{@link Locale}敏感的比较器
 */
public class CollationFrameTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            CollationFrame frame = new CollationFrame();
            frame.setTitle("CollationFrame Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
