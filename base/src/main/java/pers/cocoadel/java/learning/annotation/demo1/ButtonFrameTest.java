package pers.cocoadel.java.learning.annotation.demo1;

import javax.swing.*;
import java.awt.*;

public class ButtonFrameTest {
    public static void main(String[] args) {
        ButtonFrame frame = new ButtonFrame();
        EventQueue.invokeLater(() -> {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Annotation add ActionListener test");
            frame.setVisible(true);
        });
    }
}
