package mysqls.sql.databaseserver;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Vector;

public class ViewTable {

    String text = null;//记录sql语句
    String colName = null;//记录选择的列
    Object rowValue = null;//选中行的首列的值（一般为id）
    Object newdata;//修改后的新数据
    int focusedRowIndex = 0;//选中的行
    int focusedColIndex = 0;//选中的列

    //显示表的内容
    public ViewTable(String databaseName, String tableName, Connection connection) throws SQLException {

        String colMessage = null;
        Statement statement = connection.createStatement();
        Vector<String> v1 = new Vector<String>();
        Vector<String> v2 = new Vector<String>();

        JFrame viewTable = new JFrame("View" + tableName);
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JTextArea changeText = new JTextArea(10, 10);
        JButton apply = new JButton("apply"),
                insertRow = new JButton("insert");

        ResultSet tableMessage = statement.executeQuery("select * from " + tableName);
        tableMessage.next();

        ResultSetMetaData resultSetMetaData = tableMessage.getMetaData();
        String firstColumnName = resultSetMetaData.getColumnName(1);

        //得到列的总数
        int columnCount = resultSetMetaData.getColumnCount();
        JTable jTable = new JTable(0, columnCount);

        DefaultTableModel defaultTableModel = (DefaultTableModel) jTable.getModel();
        JScrollPane jScrollPane = new JScrollPane(jTable);

        //显示表的内容
        for (int col = 1; col <= columnCount; col++) {

            colName = resultSetMetaData.getColumnName(col);
            v1.addElement(colName);
        }
        defaultTableModel.setColumnIdentifiers(v1);

        for (int col = 1; col <= columnCount; col++) {

            tableMessage.first();
            colMessage = tableMessage.getString(col);
            v2.addElement(colMessage);
        }
        defaultTableModel.addRow(v2);

        while (tableMessage.next()) {

            Vector<String> v3 = new Vector<String>();
            for (int col = 1; col <= columnCount; col++) {

                colMessage = tableMessage.getString(col);
                v3.addElement(colMessage);
            }
            defaultTableModel.addRow(v3);
        }

        //弹出删除选项
        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem delMenItem = new JMenuItem();
        delMenItem.setText("删除 ");
        jPopupMenu.add(delMenItem);

        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                if (e.getButton() == MouseEvent.BUTTON3) {
                    jPopupMenu.show(jTable, e.getX(), e.getY());
                }

                focusedRowIndex = jTable.rowAtPoint(e.getPoint());
                focusedColIndex = jTable.columnAtPoint(e.getPoint());
                colName = jTable.getColumnName(focusedColIndex);
                ResultSet resultSet = null;

                try {
                    resultSet = statement.executeQuery("select " + firstColumnName + " from " + tableName + " limit " + focusedRowIndex + ",1");
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                try {
                    while (resultSet.next()) {
                        rowValue = resultSet.getString(1);
                    }
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        //添加table监听事件，观察用户的修改情况
        defaultTableModel.addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                // TODO Auto-generated method stub
                TableModel model = (TableModel) e.getSource();
                if (e.getType() == TableModelEvent.UPDATE) {

                    newdata = model.getValueAt(e.getFirstRow(), e.getColumn());
                    text = " update " + databaseName + "." + tableName + " set " + colName + "=" + "'" + newdata + "'"
                            + " where " + firstColumnName + "=" + "'" + rowValue + "'";
                    changeText.setText(text);//在文本框中显示用户的操作
                    changeText.append("\n");
                } else if (e.getType() == TableModelEvent.DELETE) {
                    jTable.repaint();
                } else if (e.getType() == TableModelEvent.INSERT) {
                    jTable.repaint();
                }
            }
        });

        //删除功能
        delMenItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                text = "delete from " + databaseName + "." + tableName + " where " +
                        firstColumnName + "=" + defaultTableModel.getValueAt(focusedRowIndex, 0);
                JOptionPane.showMessageDialog(null, "确定删除");
                defaultTableModel.removeRow(focusedRowIndex);
            }
        });

        ActionListener addActionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String choice = ((JButton) e.getSource()).getText();
                if (choice.equals("apply")) {
                    try {

                        String[] sqlString = changeText.getText().split("\n");
                        for (String i : sqlString) {
                            statement.executeUpdate(i);
                        }
                        JOptionPane.showMessageDialog(null, "更新成功");
                        changeText.setText("");
                        jTable.repaint();
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                } else if (choice.equals("insert")) {

                    Object[] data = new Object[columnCount];
                    defaultTableModel.addRow(data);
                    try {
                        String text = " insert into " + databaseName + "." + tableName + "(" + colName + ")" + " values ('" + null + "')";
                        JOptionPane.showMessageDialog(null, "插入成功");
                        statement.execute(text);
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        };

        apply.addActionListener(addActionListener);
        insertRow.addActionListener(addActionListener);

        buttonPanel.add(apply);
        buttonPanel.add(insertRow);

        viewTable.add(changeText, BorderLayout.CENTER);
        viewTable.add(buttonPanel, BorderLayout.SOUTH);
        viewTable.add(jScrollPane, BorderLayout.NORTH);
        viewTable.setDefaultCloseOperation(1);
        viewTable.setSize(400, 600);
        viewTable.setLocation(400, 10);
        viewTable.pack();
        viewTable.setVisible(true);

        //设置行表头
//			jScrollPane .setRowHeaderView(new RowHeaderTable(jTable, 40));
    }
} 
