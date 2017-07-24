package mysqls.sql.databaseserver;

import mysqls.sql.entity.Table;
import mysqls.ui_mainitem.GraphFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class OperationTable {

    // 查看数据库中的表
    public OperationTable(String databaseName, Connection connection) throws SQLException {

        Statement statement = connection.createStatement();
        JFrame jTables = new JFrame("show tables");
        JPanel jPanel = new JPanel(new BorderLayout());
        JPanel jpanelnorth = new JPanel();
        JPanel jpanelcenter = new JPanel();
        JPanel jPanelsouth = new JPanel(new FlowLayout());

        JTextArea showTables = new JTextArea(10, 32);
        showTables.setEditable(false);
        jpanelnorth.add(showTables);
        jpanelnorth.add(new JScrollPane(showTables));

        JTextField selectField = new JTextField(35);
        // JButton loadTable = new JButton("创建语句");
        JButton viewTable = new JButton("查看表");
        JButton deleteTable = new JButton("删除表");
        JButton loadgTable = new JButton("加载表到图形");

        // 显示选中数据库中所有的表
        try {

            statement.executeQuery("USE " + databaseName);
            ResultSet resultSet = statement.executeQuery("SHOW TABLES");
            while (resultSet.next()) {

                showTables.append(resultSet.getString(1));
                showTables.append("\n");
            }
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        showTables.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                int clickTimes = e.getClickCount();
                if (clickTimes == 2) {

                    String selectTable = showTables.getSelectedText();
                    selectField.setText(selectTable);
                }
            }
        });

        ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String choice = ((JButton) e.getSource()).getText();
                if (choice.equals("创建语句")) {

                    // try {
                    // loadTable(databaseName, selectField.getText(),
                    // connection);
                    // } catch (SQLException e1) {
                    // // TODO Auto-generated catch block
                    // e1.printStackTrace();
                    // }

                } else if (choice.equals("删除表")) {
                    try {

                        statement.executeQuery("USE " + databaseName);
                        showTables.setText("");
                        statement.executeUpdate("drop table " + selectField.getText());// 执行用户输入的表
                        selectField.setText("");
                        ResultSet resultSet = statement.executeQuery("SHOW TABLES");// 显示已有的表
                        while (resultSet.next()) {

                            showTables.append(resultSet.getString(1));
                            showTables.append("\n");
                        }
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                } else if (choice.equals("查看表")) {

                    try {

                        new ViewTable(databaseName, selectField.getText(), connection);
                        jTables.setExtendedState(Frame.ICONIFIED);
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        };

        // loadTable.addActionListener(actionListener);
        deleteTable.addActionListener(actionListener);
        loadgTable.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub

                List<Table> tables = DataBaseTables2graph.getAlltables(databaseName, connection);
                GraphFrame.me.sql2graph(tables);
            }
        });
        viewTable.addActionListener(actionListener);

        jpanelcenter.add(selectField);
        // bv jPanelsouth.add(loadTable);
        jPanelsouth.add(viewTable);
        jPanelsouth.add(deleteTable);
        jPanelsouth.add(loadgTable);

        jPanel.add(jpanelnorth, BorderLayout.NORTH);
        jPanel.add(jpanelcenter, BorderLayout.CENTER);
        jPanel.add(jPanelsouth, BorderLayout.SOUTH);

        jTables.setSize(400, 300);
        jTables.add(jPanel);
        jTables.setDefaultCloseOperation(1);
        jTables.setLocation(400, 250);
        jTables.pack();
        jTables.setVisible(true);
    }

    // 加载选择的表格的DDL语句
    // public void loadTable(String databaseName, String tableName, Connection
    // connection) throws SQLException {
    //
    // Statement statement = connection.createStatement();
    // JFrame loadTable = new JFrame("loadTable");
    // JPanel viewPanel = new JPanel();
    // JTextArea sqltext = new JTextArea(10, 10);
    //
    // statement.executeQuery("USE " + databaseName);
    // String sql = "show create table " + tableName;
    // ResultSet text = statement.executeQuery(sql);
    //
    // while (text.next()) {
    // sqltext.append(text.getString(2));
    // }
    //
    // viewPanel.add(sqltext);
    // loadTable.add(viewPanel);
    // loadTable.setSize(400, 400);
    // loadTable.setVisible(true);
    // }
}
