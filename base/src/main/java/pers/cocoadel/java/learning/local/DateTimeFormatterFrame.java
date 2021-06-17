package pers.cocoadel.java.learning.local;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

/**
 * 日期格式化和国际化测试
 */
public class DateTimeFormatterFrame extends JFrame {

    private Locale[] locales;

    private LocalDate currentDate;

    private LocalTime currentTime;

    private ZonedDateTime currentDateTime;

    private DateTimeFormatter currentDateFormatter;

    private DateTimeFormatter currentTimeFormatter;

    private DateTimeFormatter currentDateTimeFormatter;

    private JComboBox<String> localComboBox = new JComboBox<>();

    //Date
    private JButton dateParseButton = new JButton("Parse");

    private JTextField dateTextField = new JTextField(30);

    private EnumCombo<FormatStyle> dateStyleCombo = new EnumCombo<>(FormatStyle.class,"Short","Medium","Long","Full");

    //Time
    private JButton timeParseButton = new JButton("Parse");

    private JTextField timeTextField = new JTextField(30);

    private EnumCombo<FormatStyle> timeStyleCombo = new EnumCombo<>(FormatStyle.class,"Short","Medium");

    //DateTime
    private JButton dateTimeParseButton = new JButton("Parse");

    private JTextField dateTimeField = new JTextField(30);

    private EnumCombo<FormatStyle> dateTimeStyleCombo = new EnumCombo<>(FormatStyle.class, "Short","Medium","Long","Full");


    public DateTimeFormatterFrame() throws HeadlessException {
        setLayout(new GridBagLayout());

        add(new JLabel("Local"), new GBC(0, 0).setAnchor(GBC.EAST));
        add(localComboBox, new GBC(1, 0, 2, 1).setAnchor(GBC.WEST));

        add(new JLabel("Date"), new GBC(0, 1).setAnchor(GBC.EAST));
        add(dateStyleCombo, new GBC(1, 1).setAnchor(GBC.WEST));
        add(dateTextField, new GBC(2, 1, 2, 1).setFill(GBC.HORIZONTAL));
        add(dateParseButton, new GBC(4, 1).setAnchor(GBC.WEST));


        add(new JLabel("Time"), new GBC(0, 2).setAnchor(GBC.EAST));
        add(timeStyleCombo, new GBC(1, 2).setAnchor(GBC.WEST));
        add(timeTextField, new GBC(2, 2, 2, 1).setFill(GBC.HORIZONTAL));
        add(timeParseButton, new GBC(4, 2).setAnchor(GBC.WEST));

        add(new JLabel("Date And Time"), new GBC(0, 3).setAnchor(GBC.EAST));
        add(dateTimeStyleCombo, new GBC(1, 3).setAnchor(GBC.WEST));
        add(dateTimeField, new GBC(2, 3, 2, 1).setFill(GBC.HORIZONTAL));
        add(dateTimeParseButton, new GBC(4, 3).setAnchor(GBC.WEST));

        locales = Locale.getAvailableLocales().clone();
        Arrays.sort(locales, Comparator.comparing(Locale::getDisplayName));
        for (Locale locale : locales) {
            localComboBox.addItem(locale.getDisplayName());
        }
        localComboBox.setSelectedItem(Locale.getDefault().getDisplayName());

        currentDate = LocalDate.now();
        currentTime = LocalTime.now();
        currentDateTime = ZonedDateTime.now();
        updateDisplay();

        ActionListener listener = e -> updateDisplay();
        localComboBox.addActionListener(listener);
        dateStyleCombo.addActionListener(listener);
        timeStyleCombo.addActionListener(listener);
        dateTimeStyleCombo.addActionListener(listener);

        addAction(dateParseButton, () -> {
            currentDate = LocalDate.parse(dateTextField.getText().trim(),currentDateFormatter);
        });

        addAction(timeParseButton, () -> {
            currentTime = LocalTime.parse(timeTextField.getText().trim(), currentTimeFormatter);
        });

        addAction(dateTimeParseButton, () -> {
            currentDateTime = ZonedDateTime.parse(dateTimeField.getText().trim(),currentDateTimeFormatter);
        });

        pack();
    }

    private void addAction(JButton button, Runnable action) {
        button.addActionListener(e -> {
            try {
                action.run();
                updateDisplay();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        });
    }

    private void updateDisplay() {
        Locale locale = locales[localComboBox.getSelectedIndex()];

        FormatStyle dateStyle = dateStyleCombo.getValue();
        currentDateFormatter = DateTimeFormatter.ofLocalizedDate(dateStyle).withLocale(locale);
        String dateFormatted = currentDate.format(currentDateFormatter);
        dateTextField.setText(dateFormatted);

        FormatStyle timeStyle = timeStyleCombo.getValue();
        currentTimeFormatter = DateTimeFormatter.ofLocalizedTime(timeStyle).withLocale(locale);
        String timeFormatted = currentTime.format(currentTimeFormatter);
        timeTextField.setText(timeFormatted);

        FormatStyle dateTimeStyle = dateTimeStyleCombo.getValue();
        currentDateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(dateTimeStyle).withLocale(locale);
        String dateTimeFormatted = currentDateTime.format(currentDateTimeFormatter);
        dateTimeField.setText(dateTimeFormatted);
    }
}
