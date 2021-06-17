package pers.cocoadel.java.learning.local.retire;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class RetireComponent extends JComponent {
    public static final int PANEL_WIDTH = 400;
    public static final int PANEL_HEIGHT = 200;
    public static final Dimension PREFERRED_SIZE = new Dimension(800,600);
    private RetireInfo info;

    private Color colorPre;
    private Color colorGain;
    private Color colorLoss;

    public RetireComponent() {
        setSize(PANEL_WIDTH, PANEL_HEIGHT);
    }

    public void setInfo(RetireInfo ino) {
        this.info = ino;
        repaint();
    }

    public void  paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (info == null) {
            return;
        }

        double minValue = 0;
        double maxValue = 0;
        int i;
        for(i = info.getCurrentAge(); i <= info.getDeathAge();i++){
            double v = info.getBalance(i);
            if(minValue > v){
                minValue = v;
            }
            if(maxValue < v){
                maxValue = v;
            }
        }

        if (maxValue == minValue) {
            return;
        }

        int barWidth = getWidth() / (info.getDeathAge() - info.getCurrentAge() + 1);
        double scale = getHeight() / (maxValue - minValue);

        for(i = info.getCurrentAge(); i <= info.getDeathAge(); i++){
            double v = info.getBalance(i);
            int x1 = (i - info.getCurrentAge()) * barWidth + 1;
            int yOrigin = (int) (maxValue * scale);
            int y1;
            int height;
            if(v >= 0){
                y1 = (int) ((maxValue - v) * scale);
                height = yOrigin - y1;
            }else{
                y1 = yOrigin;
                height = (int) (-v * scale);
            }

            if(i < info.getRetireAge()){
                g2.setColor(colorPre);
            }else if(v > 0){
                g2.setColor(colorGain);
            }else{
                g2.setColor(colorLoss);
            }

            Rectangle2D.Double bar = new Rectangle2D.Double(x1, y1, barWidth - 2, height);
            g2.fill(bar);
            g2.setPaint(Color.black);
            g2.draw(bar);
        }
    }

    public Color getColorPre() {
        return colorPre;
    }

    public void setColorPre(Color colorPre) {
        this.colorPre = colorPre;
    }

    public Color getColorGain() {
        return colorGain;
    }

    public void setColorGain(Color colorGain) {
        this.colorGain = colorGain;
    }

    public Color getColorLoss() {
        return colorLoss;
    }

    public void setColorLoss(Color colorLoss) {
        this.colorLoss = colorLoss;
    }

    public Dimension getPreferredSize() {
        return PREFERRED_SIZE;
    }

}
