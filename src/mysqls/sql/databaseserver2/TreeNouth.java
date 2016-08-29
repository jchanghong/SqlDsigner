/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.databaseserver2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
	private static JPanel jPanel;

	public static void edittable(MYtreeNodeTable table) {
		try {

			Map<String, Object> map = new HashMap<>();
			// 显示表的内容
			String colMessage = null;
			Statement statement = ConnectINFO.connection.createStatement();
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
					int last = e.getLastRow();
					int cindex = e.getColumn();
					if (e.getType() == TableModelEvent.UPDATE) {

						Object newdata = model.getValueAt(e.getLastRow(), e.getColumn());
						String updatesql = TreeNouth.getupdqtesql(defaultTableModel.getDataVector().elementAt(last),
								table, v1, newdata, cindex);
						System.out.println(updatesql);
						TreeFrame.sqList.add(updatesql);
						TreeCenter.settext();
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

					Vector<String> vector = (Vector<String>) map.get("v");
					// 删除语句
					String deletesql = "delete from " + table.getDb().getName() + "." + table.getName() + " where "
							+ v1.elementAt(0) + "=" + vector.elementAt(0) + " and " + v1.elementAt(1) + "  = "
							+ vector.elementAt(1) + ";";
					System.out.println(deletesql);
					TreeFrame.sqList.add(deletesql);
					TreeCenter.settext();
					defaultTableModel.removeRow((int) map.get("rowindex"));
				}
			});

			// 插入功能
			jMenuItem[1].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Object[] data = new String[columnCount];
					for (int i = 0; i < data.length; i++) {
						data[i] = "null";
					}
					defaultTableModel.addRow(data);
					// 插入语句
					String insertsql = TreeNouth.getinsersql(v1, data, table);
					System.out.println(insertsql);
					TreeFrame.sqList.add(insertsql);
					TreeCenter.settext();
				}
			});
			// return showTable;
			TreeNouth.jPanel.removeAll();
			TreeNouth.jPanel.add(new JScrollPane(jTable), BorderLayout.CENTER);
			TreeFrame.me.pack();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param elementAt
	 * @param table
	 * @param v1
	 * @param newdata
	 * @param cindex
	 * @return
	 */
	protected static String getupdqtesql(Object elementAt, MYtreeNodeTable table, Vector<String> v1, Object newdata,
			int cindex) {
		// TODO Auto-generated method stub
		Vector<String> vector = (Vector<String>) elementAt;
		StringBuilder builder = new StringBuilder();
		builder.append("update " + table.getDb().getName() + "." + table.getName() + "  set ");
		builder.append(v1.elementAt(cindex)).append("=" + newdata + " where ");
		for (int i = 0; i < v1.size(); i++) {
			if (i == cindex) {
				continue;
			}
			builder.append(v1.elementAt(i));
			builder.append("=" + vector.elementAt(i) + " ");
			if (i != v1.size() - 1) {
				builder.append("  and ");

			}

		}
		return builder.toString();
	}

	/**
	 * @param v1
	 * @param data
	 * @return
	 */
	protected static String getinsersql(Vector<String> v1, Object[] data, MYtreeNodeTable table) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append("insert into " + table.getDb().getName() + "." + table.getName() + "(");
		for (int i = 0; i < v1.size(); i++) {
			builder.append(v1.elementAt(i));
			if (i != v1.size() - 1) {
				builder.append(",");

			}

		}
		builder.append(")" + " valuse (");
		for (int i = 0; i < data.length; i++) {
			builder.append(data[i]);
			if (i != data.length - 1) {
				builder.append(",");
			}
		}
		builder.append(");");
		return builder.toString();
	}

	public static JPanel getui() {

		if (TreeNouth.jPanel == null) {
			TreeNouth.jPanel = new JPanel();
		}
		return TreeNouth.jPanel;
	}

}
