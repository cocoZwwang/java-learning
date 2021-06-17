package pers.cocoadel.view.db;

import javax.swing.*;
import java.awt.*;

public class ViewDB {

    public static void main(String[] args) {
        EventQueue.invokeLater(() ->{
            ViewDBFrame viewDBFrame = new ViewDBFrame();
            viewDBFrame.setTitle("ViewDB");
            viewDBFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            viewDBFrame.setVisible(true);
        });
    }
}
