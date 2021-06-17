package pers.cocoadel.view.db;

import javax.sql.RowSet;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataPanel extends JPanel {

    private List<JTextField> fields;

    public DataPanel(RowSet rs) throws SQLException {
        fields = new ArrayList<>();
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;

        ResultSetMetaData metaData = rs.getMetaData();
        for(int i = 1; i <= metaData.getColumnCount(); i++){
            gridBagConstraints.gridy = i - 1;
            String columnName = metaData.getColumnLabel(i);
            gridBagConstraints.gridx = 0;
            gridBagConstraints.anchor = GridBagConstraints.EAST;
            add(new JLabel(columnName), gridBagConstraints);

            int columnWith = metaData.getColumnDisplaySize(i);
            JTextField jTextField = new JTextField(columnWith);
            if(!"java.lang.String".equals(metaData.getColumnClassName(i))){
                jTextField.setEnabled(false);
            }

            fields.add(jTextField);
            gridBagConstraints.gridx = 1;
            gridBagConstraints.anchor = GridBagConstraints.WEST;
            add(jTextField, gridBagConstraints);
        }
    }

    public void showRow(ResultSet resultSet){
        if (resultSet != null) {
            for (int i = 1; i <= fields.size(); i++) {
                try {
                    String field = resultSet == null ? "" : resultSet.getString(i);
                    JTextField jTextField = fields.get(i - 1);
                    jTextField.setText(field);
                } catch (SQLException e) {
                    for (Throwable t : e) {
                        t.printStackTrace();
                    }
                }
            }
        }
    }

    public void setRow(ResultSet set) {
        if (set != null) {
            for(int i = 1; i <= fields.size();i++){
                try {
                    String field = set.getString(i);
                    JTextField jTextField = fields.get(i);
                    if (!field.equals(jTextField.getText())) {
                        set.updateString(i, jTextField.getText());
                    }
                } catch (SQLException e) {
                    for (Throwable t : e) {
                        t.printStackTrace();
                    }
                }
            }
        }
    }

}
