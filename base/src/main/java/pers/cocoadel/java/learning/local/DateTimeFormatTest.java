package pers.cocoadel.java.learning.local;

import javax.swing.*;
import java.awt.*;

public class DateTimeFormatTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            DateTimeFormatterFrame frame = new DateTimeFormatterFrame();
            frame.setTitle("DateFormatTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
