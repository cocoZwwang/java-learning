package pers.cocoadel.java.learning.local;

import javax.swing.*;
import java.awt.*;

public class NumberFormatTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            NumFormatFrame frame = new NumFormatFrame();
            frame.setTitle("NumberFormatTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
