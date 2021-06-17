package pers.cocoadel.java.learning.local.retire;

import javax.swing.*;
import java.awt.*;

public class Retire {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            RetireFrame retireFrame = new RetireFrame();
            retireFrame.setTitle("Retire computer");
            retireFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            retireFrame.setVisible(true);
        });
    }
}
