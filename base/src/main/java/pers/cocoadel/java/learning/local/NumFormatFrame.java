package pers.cocoadel.java.learning.local;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

public class NumFormatFrame extends JFrame {
    private Locale[] locales;
    private double currentNumber;
    private JComboBox<String> localeCombo = new JComboBox<>();
    private JButton parseButton = new JButton("parse");
    private JTextField numberText = new JTextField(30);
    private JRadioButton numberRadioButton = new JRadioButton("Number");
    private JRadioButton currencyRadioButton = new JRadioButton("Currency");
    private JRadioButton percentRadioButton = new JRadioButton("percent");
    private ButtonGroup rbGroup = new ButtonGroup();
    private NumberFormat currentNumberFormat;

    public NumFormatFrame(){
        setLayout(new GridBagLayout());
        ActionListener listener = event -> updateDisplay();

        JPanel p = new JPanel();
        addRadioButton(p,numberRadioButton,rbGroup,listener);
        addRadioButton(p, currencyRadioButton, rbGroup, listener);
        addRadioButton(p, percentRadioButton, rbGroup, listener);

        add(new JLabel("Local:"), new GBC(0, 0).setAnchor(GBC.EAST));
        add(p, new GBC(1, 1));
        add(parseButton, new GBC(0, 2).setInsets(2));
        add(localeCombo, new GBC(1, 0).setAnchor(GBC.WEST));
        add(numberText, new GBC(1, 2).setFill(GBC.HORIZONTAL));

        locales = NumberFormat.getAvailableLocales().clone();
        Arrays.sort(locales, Comparator.comparing(Locale::getDisplayName));
        for (Locale locale : locales) {
            localeCombo.addItem(locale.getDisplayName());
        }

        localeCombo.setSelectedItem(Locale.getDefault().getDisplayName());
        currentNumber = 123456.78;
        updateDisplay();

        localeCombo.addActionListener(listener);
        parseButton.addActionListener(event ->{
            String s = numberText.getText().trim();
            try {
                Number n = currentNumberFormat.parse(s);
                currentNumber = n.doubleValue();
                updateDisplay();
            } catch (Exception e) {
                numberText.setText(e.getMessage());
            }
        });
        pack();
    }

    private void addRadioButton(Container p, JRadioButton numberRadioButton, ButtonGroup rbGroup, ActionListener listener) {
        numberRadioButton.setSelected(rbGroup.getButtonCount() == 0);
        numberRadioButton.addActionListener(listener);
        rbGroup.add(numberRadioButton);
        p.add(numberRadioButton);
    }

    private void updateDisplay() {
        Locale currentLocale = locales[localeCombo.getSelectedIndex()];
        currentNumberFormat = null;
        if (numberRadioButton.isSelected()) {
            currentNumberFormat = NumberFormat.getNumberInstance(currentLocale);
        }else if (currencyRadioButton.isSelected()) {
            currentNumberFormat = NumberFormat.getCurrencyInstance(currentLocale);
        }else{
            currentNumberFormat = NumberFormat.getPercentInstance(currentLocale);
        }
        String formatted = currentNumberFormat.format(currentNumber);
        numberText.setText(formatted);
    }
}
