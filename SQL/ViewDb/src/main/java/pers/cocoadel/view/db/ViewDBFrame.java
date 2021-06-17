package pers.cocoadel.view.db;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class ViewDBFrame extends JFrame{

    private JButton previousButton;

    private JButton nextButton;

    private JButton deleteButton;

    private JButton saveButton;

    private DataPanel dataPanel;

    private Component scrollPage;

    private JComboBox<String> tableNames;

    private Properties props;

    private CachedRowSet crs;

    private Connection conn;

    public ViewDBFrame() {
        tableNames = new JComboBox<>();
        try {
            readProperties();
            conn = getConnection();
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet mrs = metaData.getTables(null, null, null, new String[]{"TABLE"})) {
                while (mrs.next()) {
                    String tableName = mrs.getString(3);
                    System.out.println("tableName = " + tableName);
                    tableNames.addItem(tableName);
                }
            }

            tableNames.addActionListener(event ->{
                showTable((String)tableNames.getSelectedItem(),conn);
            });
            add(tableNames,BorderLayout.NORTH);
            //关闭窗口监听
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (Exception exception) {
                        doException(exception);
                    }
                }
            });

            JPanel buttonPanel = new JPanel();
            add(buttonPanel, BorderLayout.NORTH);

            previousButton = new JButton("previous");
            previousButton.addActionListener(event -> showPreviousRow());
            buttonPanel.add(previousButton);


            nextButton = new JButton("next");
            nextButton.addActionListener(event -> shownNextRow());
            buttonPanel.add(nextButton);

            deleteButton = new JButton("delete");
            deleteButton.addActionListener(event -> deleteRow());
            buttonPanel.add(deleteButton);

            saveButton = new JButton("save");
            saveButton.addActionListener(event -> saveChanges());
            buttonPanel.add(saveButton);

            if (tableNames.getItemCount() > 0) {
                showTable(tableNames.getItemAt(0),conn);
            }

        } catch (Exception e) {
            doException(e);
        }
    }

    private void saveChanges() {
        if (crs != null) {
            return;
        }
        new SwingWorker<Void,Void>(){

            @Override
            protected Void doInBackground() throws Exception {
                dataPanel.setRow(crs);
                crs.acceptChanges(conn);
                return null;
            }
        }.execute();
    }

    private void deleteRow() {
        if (crs != null) {
            return;
        }
        new SwingWorker<Void,Void>(){

            @Override
            protected Void doInBackground() throws Exception {
                crs.deleteRow();
                crs.acceptChanges(conn);
                //
                if (crs.isAfterLast()) {
                    if(!crs.isLast()){
                        crs = null;
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                dataPanel.showRow(crs);
            }
        }.execute();
    }

    private void shownNextRow() {
        try {
            if (crs == null || crs.isLast()) {
                return;
            }
            crs.next();
            dataPanel.showRow(crs);
        } catch (Exception e) {
            doException(e);
        }
    }

    private void showPreviousRow() {
        try {
            if (crs == null || crs.isFirst()) {
                return;
            }
            crs.previous();
            dataPanel.showRow(crs);
        } catch (Exception e) {
            doException(e);
        }
    }

    private void showTable(String tableName, Connection conn) {
        String sql = "select * from " + tableName;
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql);) {
            RowSetFactory rowSetFactory = RowSetProvider.newFactory();
            crs = rowSetFactory.createCachedRowSet();
            crs.setTableName(tableName);
            crs.populate(resultSet);
            if (scrollPage != null) {
                remove(scrollPage);
            }

            dataPanel = new DataPanel(crs);
            scrollPage = new JScrollPane(dataPanel);
            add(scrollPage, BorderLayout.CENTER);

            pack();
            shownNextRow();
        } catch (SQLException e) {
            doException(e);
        }
    }

    private void doException(Exception e){
        if (e instanceof SQLException) {
            SQLException sqlException = (SQLException) e;
            for (Throwable t : sqlException) {
                t.printStackTrace();
            }
        }else{
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        String drivers = props.getProperty("jdbc.driver-class-name");
//        System.out.println("drivers = " + drivers);
        if (drivers != null && drivers.length() > 0) {
            System.setProperty("jdbc.drivers", drivers);
        }
        String url = props.getProperty("jdbc.url");
//        System.out.println("url = " + url);

        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        //注册驱动
        try {
            Class.forName(drivers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url,username,password);
    }

    private void readProperties() {
        props = new Properties();
        ClassLoader classLoader = this.getClass().getClassLoader();
        try (InputStream in = classLoader.getResourceAsStream("database.properties")) {
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String drivers = props.getProperty("jdbc.driver-class-name");
        if (drivers != null && drivers.length() > 0) {
            System.setProperty("jdbc.drivers", drivers);
        }
    }


}
