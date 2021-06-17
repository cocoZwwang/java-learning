package pers.cocoadel.java.learning.local.retire;

import pers.cocoadel.java.learning.local.GBC;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 1.getBundle 获取包资源的顺序：
 * baseName_currentLocaleLanguage_currentLocaleCountry
 * baseName_currentLocaleLanguage
 * baseName_currentLocaleLanguage_defaultLocalCountry
 * baseName_defaultLocaleLanguage
 * baseName
 *
 * 所以这里的 默认属性要修改中文（zh），而英文资源要加 _en 后缀。
 * 如果和《java 核心技术卷II》中，把英文设为默认，那么 Locale.US 会找到 baseName_defaultLocaleLanguage 这一步直接使用中文的，而不会走到最后一步 baseName
 */
public class RetireFrame extends JFrame {

    private Locale[] locales = {Locale.US, Locale.GERMANY,Locale.CHINA};
    private Locale currentLocate;
    private ResourceBundle res;
    private ResourceBundle resStrings;
    private NumberFormat numberFormat;
    private NumberFormat currencyFormat;
    private NumberFormat percentFormat;
    private RetireInfo info = new RetireInfo();

    private JLabel languageLabel = new JLabel();
    private JComboBox<Locale> localCombo = new JComboBox<>(locales);

    private JLabel savingLabel = new JLabel();
    private JTextField savingTextField = new JTextField(10);

    private JLabel contribLabel = new JLabel();
    private JTextField contribTextField = new JTextField(10);

    private JLabel incomeLabel = new JLabel();
    private JTextField incomeTextField = new JTextField(10);

    private JLabel currentAgeLabel = new JLabel();
    private JTextField currentAgeTextField = new JTextField(4);

    private JLabel retireAgeLabel = new JLabel();
    private JTextField retireAgeTextField = new JTextField(4);

    private JLabel deathAgeLabel = new JLabel();
    private JTextField deathAgeTextField = new JTextField(4);

    private JLabel inflationLabel = new JLabel();
    private JTextField inflationTextField = new JTextField(6);

    private JLabel investmentLabel = new JLabel();
    private JTextField investmentTextField = new JTextField(6);

    private JButton computeButton = new JButton();

    private RetireComponent retireComponent = new RetireComponent();

    private JTextArea retireTextArea = new JTextArea(10, 25);



    public RetireFrame() throws HeadlessException {
        setLayout(new GridBagLayout());

        add(languageLabel, new GBC(0, 0).setAnchor(GBC.EAST));
        add(localCombo, new GBC(1, 0, 3, 1));

        add(savingLabel, new GBC(0, 1).setAnchor(GBC.EAST));
        add(savingTextField, new GBC(1, 1).setWeight(100, 0).setFill(GBC.HORIZONTAL));

        add(contribLabel, new GBC(2, 1).setAnchor(GBC.EAST));
        add(contribTextField, new GBC(3, 1).setWeight(100, 0).setFill(GBC.HORIZONTAL));

        add(incomeLabel, new GBC(4, 1).setAnchor(GBC.EAST));
        add(incomeTextField, new GBC(5, 1).setWeight(100, 0).setFill(GBC.HORIZONTAL));

        add(currentAgeLabel, new GBC(0, 2).setAnchor(GBC.EAST));
        add(currentAgeTextField, new GBC(1, 2).setWeight(100, 0).setFill(GBC.HORIZONTAL));

        add(retireAgeLabel, new GBC(2, 2).setAnchor(GBC.EAST));
        add(retireAgeTextField, new GBC(3, 2).setWeight(100, 0).setFill(GBC.HORIZONTAL));

        add(deathAgeLabel, new GBC(4, 2).setAnchor(GBC.EAST));
        add(deathAgeTextField, new GBC(5, 2).setWeight(100, 0).setFill(GBC.HORIZONTAL));

        add(inflationLabel, new GBC(0, 3).setAnchor(GBC.EAST));
        add(inflationTextField, new GBC(1, 3).setWeight(100, 0).setFill(GBC.HORIZONTAL));

        add(investmentLabel, new GBC(2, 3).setAnchor(GBC.EAST));
        add(investmentTextField, new GBC(3, 3).setWeight(100, 0).setFill(GBC.HORIZONTAL));

        add(computeButton, new GBC(5, 3).setAnchor(GBC.EAST));

        add(retireComponent, new GBC(0, 4, 4, 1).setWeight(100, 100).setFill(GBC.BOTH));
        add(new JScrollPane(retireTextArea), new GBC(4, 4, 2, 1).setWeight(0, 100).setFill(GBC.BOTH));

        computeButton.setName("compute");
        computeButton.addActionListener(e -> {
            getInfo();
            updateData();
            updateGraph();
        });

        retireTextArea.setEnabled(false);
        retireAgeTextField.setFont(new Font("Monospaced", Font.PLAIN, 10));

        initInfo();

        int localeIndex = 0;
        for (int i = 0; i < locales.length; i++) {
            if (getLocale().equals(locales[i])) {
                localeIndex = i;
            }
        }
        setCurrentLocale(locales[localeIndex]);
        localCombo.addActionListener(e -> {
            setCurrentLocale(locales[localCombo.getSelectedIndex()]);
            validate();
        });

        pack();
    }

    private void setCurrentLocale(Locale locale) {

        currentLocate = locale;
        localCombo.setLocale(currentLocate);
        localCombo.setSelectedItem(currentLocate);

        numberFormat = NumberFormat.getNumberInstance(currentLocate);
        currencyFormat = NumberFormat.getCurrencyInstance(currentLocate);
        percentFormat = NumberFormat.getPercentInstance(currentLocate);
        res = ResourceBundle.getBundle("pers.cocoadel.java.learning.local.retire.resources.RetireResources", currentLocate);
        resStrings = ResourceBundle.getBundle("pers.cocoadel.java.learning.local.retire.resources.RetireStrings", currentLocate);
        updateDisplay();
        updateInfo();
        updateData();
        updateGraph();
    }

    private void updateInfo() {
        savingTextField.setText(currencyFormat.format(info.getSaving()));
        contribTextField.setText(currencyFormat.format(info.getContrib()));
        incomeTextField.setText(currencyFormat.format(info.getIncome()));

        currentAgeTextField.setText(numberFormat.format(info.getCurrentAge()));
        retireAgeTextField.setText(numberFormat.format(info.getRetireAge()));
        deathAgeTextField.setText(numberFormat.format(info.getDeathAge()));

        inflationTextField.setText(percentFormat.format(info.getInflationPercent()));
        investmentTextField.setText(percentFormat.format(info.getInvestPercent()));
    }

    private void updateDisplay() {
        languageLabel.setText(getStringUtf8("language"));
        savingLabel.setText(getStringUtf8("saving"));
        contribLabel.setText(getStringUtf8("contrib"));
        incomeLabel.setText(getStringUtf8("income"));
        currentAgeLabel.setText(getStringUtf8("currentAge"));
        retireAgeLabel.setText(getStringUtf8("retireAge"));
        deathAgeLabel.setText(getStringUtf8("deathAge"));
        inflationLabel.setText(getStringUtf8("inflationPercent"));
        investmentLabel.setText(getStringUtf8("investPercent"));
        computeButton.setText(getStringUtf8("computeButton"));
    }

    /**
     * ResourceBundle是按照iso8859来读取原属性文件的，如果项目使用的 utf-8编码，需要手动转化未 utf-8。
     */
    private String getStringUtf8(String keyName){
        byte[] bytes = resStrings.getString(keyName).getBytes(StandardCharsets.ISO_8859_1);
        return new String(bytes);
    }


    private void updateGraph() {
        retireComponent.setColorPre((Color) res.getObject("colorPre"));
        retireComponent.setColorGain((Color) res.getObject("colorGain"));
        retireComponent.setColorLoss((Color) res.getObject("colorLoss"));
        retireComponent.setInfo(info);
        repaint();
    }

    private void updateData() {
        retireTextArea.setText("");
        MessageFormat messageFormat = new MessageFormat("");
        messageFormat.setLocale(currentLocate);
        messageFormat.applyPattern(getStringUtf8("retire"));
        for(int i = info.getCurrentAge(); i <= info.getDeathAge(); i++){
            Object[] args =  new Object[]{i, info.getBalance(i)};
            retireTextArea.append(messageFormat.format(args) + "\n");
        }
    }

    private void initInfo() {
        info.setSaving(0);
        info.setContrib(9000);
        info.setIncome(60000);

        info.setCurrentAge(35);
        info.setRetireAge(65);
        info.setDeathAge(85);

        info.setInflationPercent(0.05);
        info.setInvestPercent(0.1);
    }

    private void getInfo() {
        try {
            info.setSaving(currencyFormat.parse(savingTextField.getText()).doubleValue());
            info.setContrib(currencyFormat.parse(contribTextField.getText()).doubleValue());
            info.setIncome(currencyFormat.parse(incomeTextField.getText()).doubleValue());

            info.setCurrentAge(numberFormat.parse(currentAgeTextField.getText()).intValue());
            info.setRetireAge(numberFormat.parse(retireAgeTextField.getText()).intValue());
            info.setDeathAge(numberFormat.parse(deathAgeTextField.getText()).intValue());

            info.setInflationPercent(percentFormat.parse(inflationTextField.getText()).doubleValue());
            info.setInvestPercent(percentFormat.parse(investmentTextField.getText()).doubleValue());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
