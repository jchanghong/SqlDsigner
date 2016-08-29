/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.databaseserver2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import mysqls.contanst.ConnectINFO;

/**
 * @author 长宏 编辑数据
 *
 */
public class TreeNouth {
	public static JPanel getui() throws SQLException {

		Map<String, Object> map = new HashMap<>();
		// 显示表的内容
		String colMessage = null;
		Statement statement = ConnectINFO.connection.createStatement();
		Vector<String> v1 = new Vector<String>();
		Vector<String> v2 = new Vector<String>();

		ResultSet tableMessage = statement.executeQuery("select * from " + ConnectINFO.tableName);
		tableMessage.next();

		ResultSetMetaData resultSetMetaData = tableMessage.getMetaData();
		String firstColumnName = resultSetMetaData.getColumnName(1);

		// 得到列的总数
		int columnCount = resultSetMetaData.getColumnCount();
		JTable jTable = new JTable(0, columnCount);

		DefaultTableModel defaultTableModel = (DefaultTableModel) jTable.getModel();
		JScrollPane jScrollPane = new JScrollPane(jTable);

		JPanel showTable = new JPanel();
		showTable.add(jTable);
		showTable.add(jScrollPane);

		// 显示表的内容
		for (int col = 1; col <= columnCount; col++) {

			String colName = resultSetMetaData.getColumnName(col);
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

		// 弹出删除,插入选项
		JPopupMenu jPopupMenu = new JPopupMenu();
		JMenuItem[] jMenuItem = new JMenuItem[2];
		jMenuItem[0] = new JMenuItem("删除此行");
		jMenuItem[1] = new JMenuItem("插入一行");
		jPopupMenu.add(jMenuItem[0]);
		jPopupMenu.add(jMenuItem[1]);

		jTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.getButton() == MouseEvent.BUTTON3) {
					jPopupMenu.show(jTable, e.getX(), e.getY());
				}

				int focusedRowIndex = jTable.rowAtPoint(e.getPoint());// 鼠标点击所在行
				int focusedColIndex = jTable.columnAtPoint(e.getPoint());// 鼠标点击所在列
				String colName = jTable.getColumnName(focusedColIndex);// 得到列名

				map.put("rowindex", focusedRowIndex);
				map.put("colindex", focusedColIndex);
				map.put("columnName", colName);
				ResultSet resultSet = null;

				try {
					resultSet = statement.executeQuery("select " + firstColumnName + " from " + ConnectINFO.tableName
							+ " limit " + focusedRowIndex + ",1");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					while (resultSet.next()) {
						String rowValue = resultSet.getString(1);
						map.put("rowv", rowValue);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// 添加table监听事件，观察用户的修改情况
		defaultTableModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				TableModel model = (TableModel) e.getSource();
				if (e.getType() == TableModelEvent.UPDATE) {

					Object newdata = model.getValueAt(e.getFirstRow(), e.getColumn());
					map.put("newv", newdata);

					// 更新语句
					String updatesql = " update " + ConnectINFO.databaseName + "." + ConnectINFO.tableName + " set "
							+ map.get("columnName") + "=" + "'" + map.get("newv") + "'" + " where " + firstColumnName
							+ "=" + "'" + map.get("rowv") + "'";

					TreeListAll.sqList.add(updatesql);
				} else if (e.getType() == TableModelEvent.DELETE) {
					jTable.repaint();
				} else if (e.getType() == TableModelEvent.INSERT) {
					jTable.repaint();
				}
			}
		});

		// 删除功能
		jMenuItem[0].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// 删除语句
				String deletesql = "delete from " + ConnectINFO.databaseName + "." + ConnectINFO.tableName + " where "
						+ firstColumnName + "=" + defaultTableModel.getValueAt((int) map.get("colindex"), 0);
				TreeListAll.sqList.add(deletesql);
				defaultTableModel.removeRow((int) map.get("roeindex"));
			}
		});

		// 插入功能
		jMenuItem[1].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Object[] data = new Object[columnCount];
				defaultTableModel.addRow(data);
				try {

					// 插入语句
					String insertsql = " insert into " + ConnectINFO.databaseName + "." + ConnectINFO.tableName + "("
							+ map.get("columnName") + ")" + " values ('" + null + "')";
					TreeListAll.sqList.add(insertsql);

					statement.execute(insertsql);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		return showTable;

	}

}
