/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.databaseserver2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import mysqls.ui_mainitem.TreeFrame;

/**
 * @author 长宏 编辑数据
 *
 */
public class TreeTabledit {
	private static JPanel jPanel;

	public static MYtreeNodeTable table;
	private static boolean hasNew = true;

	public static void edittable(MYtreeNodeTable table,JPanel parent) {
		TreeSQLedit.settext("");
		TreeTabledit.table = table;
		try {

			Map<String, Object> map = new HashMap<>();
			// 显示表的内容
			String colMessage = null;
			Statement statement = ConnectINFO.getInstance().getConnection().createStatement();
			statement.execute("use " + table.getDb().getName());
			Vector<String> v1 = new Vector<String>();
			Vector<String> v2 = new Vector<String>();

			ResultSet tableMessage = statement.executeQuery("select * from " + table);
			ResultSetMetaData resultSetMetaData = tableMessage.getMetaData();
			String firstColumnName = resultSetMetaData.getColumnName(1);

			// 得到列的总数
			int columnCount = resultSetMetaData.getColumnCount();
			final JTable jTable = new JTable(0, columnCount);

			DefaultTableModel defaultTableModel = (DefaultTableModel) jTable.getModel();
			// 显示表的内容
			for (int col = 1; col <= columnCount; col++) {

				String colName = resultSetMetaData.getColumnName(col);
				v1.addElement(colName);
			}
			defaultTableModel.setColumnIdentifiers(v1);

			while (tableMessage.next()) {

				Vector<String> v3 = new Vector<String>();
				for (int col = 1; col <= columnCount; col++) {

					colMessage = tableMessage.getString(col);
					v3.addElement(colMessage);
				}
				defaultTableModel.addRow(v3);
			}

			// 增加一行全为null
			Object[] data = new String[columnCount];
			for (int i = 0; i < data.length; i++) {
				data[i] = "null";
			}
			defaultTableModel.addRow(data);

			int key = defaultTableModel.getRowCount() - 1;
			System.out.println("key；is " + key);
			System.out.println("vector is " + defaultTableModel.getDataVector().elementAt(key));

			TreeFrame.tablevalues.clear();
			TreeFrame.sqList.clear();
			TreeFrame.oldfirstvaluesList.clear();
			TreeFrame.keytodelete.clear();
			TreeFrame.tablehead = v1;
			TreeFrame.tablevalues.put(key, (Vector<Object>) defaultTableModel.getDataVector().elementAt(key));
			TreeFrame.oldfirstvaluesList.put(key, "null");

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

						int focusedRowIndex = jTable.rowAtPoint(e.getPoint());// 鼠标点击所在行
						int focusedColIndex = jTable.columnAtPoint(e.getPoint());// 鼠标点击所在列

						String colName = jTable.getColumnName(focusedColIndex);// 得到列名

						Vector<String> vector = (Vector<String>) defaultTableModel.getDataVector()
								.elementAt(focusedRowIndex);
						map.put("v", vector);
						// System.out.println(vector.toString());
						map.put("rowindex", focusedRowIndex);
						map.put("colindex", focusedColIndex);
						map.put("columnName", colName);
					}
				}
			});

			// 添加table监听事件，观察用户的修改情况
			defaultTableModel.addTableModelListener(new TableModelListener() {

				@Override
				public void tableChanged(TableModelEvent e) {
					// TODO Auto-generated method stub
					TableModel model = (TableModel) e.getSource();
					int last = jTable.getSelectedRow();
					int cindex = e.getColumn();
					Object olddata = "null";// 得到原值
					ResultSet resultSet = null;

					if (e.getType() == TableModelEvent.UPDATE) {
						try {
							resultSet = statement.executeQuery("select " + firstColumnName + " from " + table
									+ " limit " + jTable.getSelectedRow() + ",1");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							while (resultSet.next()) {
								olddata = resultSet.getString(1);
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						System.out.println("last is " + last);
						System.out.println("old v is" + olddata);
						TreeFrame.oldfirstvaluesList.put(last, olddata);

						Object newdata = model.getValueAt(e.getLastRow(), e.getColumn());
						Object item = defaultTableModel.getDataVector().elementAt(last);
						TreeFrame.tablevalues.put(last, (Vector<Object>) item);
						System.out.println(item);
						// String updatesql = TreeTabledit.getupdqtesql(item,
						// table, v1, newdata, cindex, olddata);
						// System.out.println(updatesql);
						// TreeFrame.sqList.add(updatesql);
						// TreeSQLedit.settext();
					} else if (e.getType() == TableModelEvent.DELETE) {
						jTable.repaint();
					} else if (e.getType() == TableModelEvent.INSERT) {

						for (int col = 0; col < columnCount; col++) {
							if (!jTable.getValueAt(jTable.getRowCount() - 1, col).equals("null")) {

								List<Object> data2 = new ArrayList<>();
								for (int i = 0; i < columnCount; i++) {
									data2.add(jTable.getValueAt(last, i));
								}

								String insertsql = TreeTabledit.getinsersql(v1, data2, table);
								System.out.println(insertsql);
								// TreeFrame.sqList.add(insertsql);
							}

						}
					}
				}
			});

			// 删除功能
			jMenuItem[0].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					Vector<String> vector = (Vector<String>) map.get("v");
					TreeFrame.keytodelete.add(vector.elementAt(0));
					defaultTableModel.removeRow((int) map.get("rowindex"));
				}
			});

			// 插入功能
			jMenuItem[1].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub

					// 增加一行全为null
					Object[] data = new String[columnCount];
					for (int i = 0; i < data.length; i++) {
						data[i] = "null";
					}
					defaultTableModel.addRow(data);

					int key = defaultTableModel.getRowCount() - 1;
					TreeFrame.tablevalues.put(key, (Vector<Object>) defaultTableModel.getDataVector().elementAt(key));
					TreeFrame.oldfirstvaluesList.put(key, "null");
				}

			});
			parent.add(new JScrollPane(jTable), BorderLayout.CENTER);
			//TreeTabledit.jPanel.removeAll();
//			TreeTabledit.jPanel.add(new JScrollPane(jTable), BorderLayout.CENTER);
//			TreeFrame.me.pack();
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}
	}

	/**
	 * @param elementAt
	 * @param table
	 * @param v1
	 *            根据frame里面的常量数据得到sql语句，只有这个函数才是外部调用
	 * @param newdata
	 * @param cindex
	 * @return
	 */
	public static String getupdqtesql() {
		TreeFrame.sqlmap.clear();
		// TODO Auto-generated method stub
		// Map<Object, String> sqlmap = new HashMap<>();
		StringBuilder builder = new StringBuilder();

		for (Integer rowindex : TreeFrame.tablevalues.keySet()) {
			Vector<Object> rowvaluse = TreeFrame.tablevalues.get(rowindex);
			// 全部是null，bu插入
			if (MYtableUtil.isallnull(rowvaluse)) {
				continue;

			}

			Object old = TreeFrame.oldfirstvaluesList.get(rowindex);
			if (old == null || old.toString().equals("null")) {
				TreeFrame.sqlmap.put(rowindex, TreeTabledit.getinsertsql(rowvaluse, old, rowindex));
				builder.append(TreeTabledit.getinsertsql(rowvaluse, old, rowindex) + "\n");
			} else {

				TreeFrame.sqlmap.put(rowindex, TreeTabledit.getupdatesql(rowvaluse, old, rowindex));
				builder.append(TreeTabledit.getupdatesql(rowvaluse, old, rowindex) + "\n");
			}

		}

		// 删除
		// 删除语句
		for (String key : TreeFrame.keytodelete) {

			String deletesql = "delete from " + TreeTabledit.table.getDb().getName() + "."
					+ TreeTabledit.table.getName() + " where " + TreeFrame.tablehead.elementAt(0) + ""
					+ TreeTabledit.isnull(key) + ";";

			TreeFrame.sqlmap.put(key, deletesql);
			builder.append(deletesql);
		}
		return builder.toString();
	}

	private static String isnull(String where) {
		if (where == null || where.equalsIgnoreCase("null")) {

			return "  IS NULL";
		}
		return "='" + where + "'";
	}

	/**
	 * @param rowvaluse
	 *            现在的行值
	 * @param old
	 *            第一行的old值
	 * @return
	 */
	private static String getupdatesql(Vector<Object> rowvaluse, Object old, Integer rowindex) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append("update " + TreeTabledit.table.getDb().getName() + "." + TreeTabledit.table.getName() + " set ");

		for (int i = 0; i < TreeFrame.tablehead.size(); i++) {
			String v = (String) rowvaluse.elementAt(i);
			if (v == null) {
				continue;
			}
			if (!v.equals("null")) {
				builder.append(TreeFrame.tablehead.elementAt(i) + "='" + v + "',");
			}

		}
		int indexs = builder.lastIndexOf(",");
		if (indexs != -1) {
			builder.deleteCharAt(indexs);

		}
		builder.append("   where " + TreeFrame.tablehead.get(0) + "= '" + old.toString() + "';");
		return builder.toString();
	}

	/**
	 * @param rowvaluse
	 *            现在的行值
	 * @param old
	 *            第一行的old值
	 * @return
	 */
	private static String getinsertsql(Vector<Object> rowvaluse, Object old, Integer rowindex) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append(
				"insert into " + TreeTabledit.table.getDb().getName() + "." + TreeTabledit.table.getName() + "( ");

		for (int i = 0; i < TreeFrame.tablehead.size(); i++) {

			builder.append(TreeFrame.tablehead.elementAt(i) + ",");

		}
		int indexs = builder.lastIndexOf(",");
		if (indexs != -1) {
			builder.deleteCharAt(indexs);

		}
		builder.append(") " + "values( ");
		for (int i = 0; i < rowvaluse.size(); i++) {

			String vvv = (String) rowvaluse.elementAt(i);
			if (vvv == null || vvv.equals("null")) {

				builder.append("" + "NULL" + ",");
			} else
				builder.append("'" + rowvaluse.elementAt(i) + "',");

		}
		int indexs1 = builder.lastIndexOf(",");
		if (indexs != -1) {
			builder.deleteCharAt(indexs1);

		}
		builder.append(") " + ";");
		return builder.toString();
	}

	/**
	 * @param v1
	 * @param data
	 * @return
	 */
	protected static String getinsersql(Vector<String> v1, Object data2, MYtreeNodeTable table) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append("insert into " + table.getDb().getName() + "." + table.getName() + "(");
		for (int i = 0; i < v1.size(); i++) {
			builder.append(v1.elementAt(0));
			if (i != v1.size() - 1) {
				builder.append(",");
			}
		}
		builder.append(")" + " values (");
		for (int i = 0; i < v1.size(); i++) {
			builder.append(data2);
			if (i != v1.size() - 1) {
				builder.append(",");
			}
		}
		builder.append(");");
		return builder.toString();
	}

	public static JPanel getui() {

		if (TreeTabledit.jPanel == null) {
			TreeTabledit.jPanel = new JPanel();
			jPanel.setBackground(Color.WHITE);
		}
		return TreeTabledit.jPanel;
	}

}
