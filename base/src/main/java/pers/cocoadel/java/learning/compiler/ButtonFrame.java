package pers.cocoadel.java.learning.compiler;

import javax.swing.*;
import java.awt.*;

public abstract class ButtonFrame extends JFrame {

    public static final int default_width = 300;
    public static final int default_height = 200;

    protected JPanel jPanel;
    protected JButton yellowButton;
    protected JButton blueButton;
    protected JButton redButton;

    protected abstract void addEventHandlers();

    public ButtonFrame() throws HeadlessException {
        setSize(default_width, default_height);

        jPanel = new JPanel();
        add(jPanel);

        yellowButton = new JButton("Yellow");
        blueButton = new JButton("Blue");
        redButton = new JButton("Red");

        jPanel.add(yellowButton);
        jPanel.add(blueButton);
        jPanel.add(redButton);

        addEventHandlers();
    }
}
