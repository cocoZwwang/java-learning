package pers.cocoadel.java.learning.local;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.Collator;
import java.util.*;
import java.util.List;

/**
 * 字符排序 国际化 测试
 * {@link Collator} 对{@link Locale}敏感的比较器
 */
public class CollationFrame extends JFrame {
    private Collator collator = Collator.getInstance(Locale.getDefault());
    private Locale[] locales;
    private JComboBox<String> localeCombo = new JComboBox<>();
    private EnumCombo<Integer> strengthCombo = new EnumCombo<>(Collator.class, "Primary", "Secondary", "Tertiary");
    private EnumCombo<Integer> decompositionCombo = new EnumCombo<>(Collator.class,
            "Canonical Decomposition","Full Decomposition","No Decomposition");
    private JButton addButton = new JButton("add");
    private JTextField addWordTextField = new JTextField(20);
    private JTextArea sortedWords = new JTextArea(20,20);

    private Collator currentCollator;
    private List<String> strings = new ArrayList<>();

    public CollationFrame() throws HeadlessException {

        setLayout(new GridBagLayout());

        add(new Label("locales"), new GBC(0, 0).setAnchor(GBC.EAST));
        add(localeCombo, new GBC(1, 0).setAnchor(GBC.EAST));

        add(new Label("Strength"), new GBC(0, 1).setAnchor(GBC.EAST));
        add(strengthCombo, new GBC(1, 1).setAnchor(GBC.WEST));

        add(new Label("Decomposition"), new GBC(0, 2).setAnchor(GBC.EAST));
        add(decompositionCombo, new GBC(1, 2).setAnchor(GBC.WEST));

        add(addButton, new GBC(0, 3).setAnchor(GBC.EAST));
        add(addWordTextField, new GBC(1, 3).setFill(GBC.HORIZONTAL));

        add(new JScrollPane(sortedWords), new GBC(0, 4, 2, 1).setFill(GBC.BOTH));

        locales = Collator.getAvailableLocales().clone();
        Arrays.sort(locales, (l1, l2) -> collator.compare(l1.getDisplayName(), l2.getDisplayName()));
        for (Locale locale : locales) {
            localeCombo.addItem(locale.getDisplayName());
        }
        localeCombo.setSelectedItem(Locale.getDefault().getDisplayName());

        initStrings();

        addButton.addActionListener(e -> {
            strings.add(addWordTextField.getText());
            addWordTextField.setText("");
            updateDisplay();
        });

        ActionListener listener = e -> updateDisplay();
        strengthCombo.addActionListener(listener);
        decompositionCombo.addActionListener(listener);
        localeCombo.addActionListener(listener);
        pack();
    }

    private void initStrings() {
        strings.add("America");
        strings.add("able");
        strings.add("Zulu");
        strings.add("zebra");
        strings.add("\00C5ngstr\u00F6m");
        strings.add("ActionListenerFor\u030angstro\u0308m");
        strings.add("Angstrom");
        strings.add("Able");
        strings.add("office");
        strings.add("o\uFB03ce");
        strings.add("Java\u2122");
        strings.add("JavaTM");
        updateDisplay();
    }

    private void updateDisplay() {
        int selectedIndex = localeCombo.getSelectedIndex();
        Locale currentLocale = locales[selectedIndex];

        int strength = strengthCombo.getValue();
        int decomposition = decompositionCombo.getValue();

        currentCollator = Collator.getInstance(currentLocale);
        currentCollator.setStrength(strength);
        currentCollator.setDecomposition(decomposition);

        strings.sort(currentCollator);
        sortedWords.setText("");
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < strings.size(); i++){
            String s = strings.get(i);
            if (i > 0 && currentCollator.compare(s, strings.get(i - 1)) == 0) {
                sb.append("=");
            }
            sb.append(s).append("\n");
        }
        sortedWords.setText(sb.toString());
        pack();
    }


}
