package mysqls.ui_mainitem;

import mysqls.contanst.ConnectINFO;
import mysqls.sql.databaseserver2.TreeSQLedit;
import mysqls.sql.databaseserver2.TreeTabledit;

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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.List;

/**
 * Created by 长宏 on 2016/10/2 0002.
 * 执行sql语句后的结果面板，必须是table结果，其他没有结果的语句直接dialog
 */
public class SQL_resultPanel extends JPanel {

    public void setResultSet(ResultSet resultSet) {
        removeAll();
        this.resultSet = resultSet;
        add(getajTable(), BorderLayout.CENTER);
        updateUI();
    }

    private ResultSet resultSet;
    private JTable table;
    public SQL_resultPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(new JLabel("no data!!!"));
        setMaximumSize(new JLabel("1111").getPreferredSize());
    }

    private JTable getajTable() {


        try {
            Vector<String> heads = new Vector<String>();
            Vector<String> adata = new Vector<String>();

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            // 得到列的总数
            int columnCount = resultSetMetaData.getColumnCount();
            final JTable jTable = new JTable(0, columnCount);

            DefaultTableModel defaultTableModel = (DefaultTableModel) jTable.getModel();
            // 显示表的内容
            for (int col = 1; col <= columnCount; col++) {

                String colName = resultSetMetaData.getColumnName(col);
                heads.addElement(colName);
            }
            defaultTableModel.setColumnIdentifiers(heads);

            defaultTableModel.addRow(heads);
            while (resultSet.next()) {

                Vector<String> v3 = new Vector<String>();
                for (int col = 1; col <= columnCount; col++) {

                    String colMessage = resultSet.getString(col);
                    v3.addElement(colMessage);
                }
                defaultTableModel.addRow(v3);
            }
            return jTable;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
            e.printStackTrace();
        }
        return new JTable(null, new Object[]{"no", "no"});
    }
}