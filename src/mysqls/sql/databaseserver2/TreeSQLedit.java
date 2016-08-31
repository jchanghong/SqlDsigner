/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.databaseserver2;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.StyledDocument;

import mysqls.contanst.ConnectINFO;
import mysqls.sql.ui.MYdialogSwing;

/**
 * @author 长宏 sql执行和编辑
 *
 */
public class TreeSQLedit {
	private static JPanel jPanel;

	public static JPanel getui() {

		if (TreeSQLedit.jPanel == null) {
			TreeSQLedit.jPanel = new JPanel(new BorderLayout());

			TreeSQLedit.gettextpanel();
			TreeSQLedit.jPanel.add(new JScrollPane(TreeSQLedit.textPane), BorderLayout.CENTER);
			JPanel buttons = new JPanel();
			buttons.setLayout(new GridLayout(1, 0));
			JButton apply = new JButton("确定"), exe1 = new JButton("执行");
			buttons.add(apply);
			buttons.add(exe1);

			TreeSQLedit.jPanel.add(buttons, BorderLayout.SOUTH);
			// sqlEdit记录所要操作的sql语句

			// 确定按钮产生sql语句
			apply.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						TreeFrame.sqList.clear();
						TreeSQLedit.sql = TreeTabledit.getupdqtesql();
						for (String string : TreeSQLedit.sql.split(";")) {

							String temp = string.trim();
							if (temp != null && temp.length() > 2) {
								TreeFrame.sqList.add(temp);
							}
						}
						// TreeSQLedit.settext(MYsqlStatementUtil.tostring(TreeFrame.sqList));
						TreeSQLedit.settext(TreeSQLedit.sql);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						MYdialogSwing.showError(e1.getMessage());
						e1.printStackTrace();
					}
				}
			});

			// // 执行按钮执行之前的操作
			exe1.addActionListener(TreeSQLedit::exesql);
		}
		return TreeSQLedit.jPanel;
	}

	private static String sql;

	/**
	 * @param sql
	 */
	protected static void settext(String sql) {
		// TODO Auto-generated method stub

		TreeSQLedit.sql = sql;
		TreeSQLedit.textPane.setText(sql);
		SQLcolorSmall.setcolor(TreeSQLedit.textPane);

		if (TreeFrame.me != null) {

			TreeFrame.me.pack();
		}
	}

	private static JTextPane gettextpanel() {
		JTextPane msqlpane = new JTextPane();
		msqlpane.setEditable(true);// 可编辑的。
		StyledDocument document = msqlpane.getStyledDocument();

		DocumentListener documentListener = null;
		document.addDocumentListener(documentListener);// 给sql面板设置的颜色。给关键字改颜色的方法和策略

		documentListener = new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						SQLcolorSmall.setcolor(msqlpane);
					}
				});
				//
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						SQLcolorSmall.setcolor(msqlpane);
					}
				});

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub

			}

		};
		TreeSQLedit.textPane = msqlpane;
		return msqlpane;

	}

	private static JTextPane textPane;

	/**
	 * @param sql
	 *            执行sql语句
	 */
	public static void exesql(ActionEvent actionEvent) {

		boolean nowrong = true;
		if (TreeFrame.sqList.size() < 0) {
			JOptionPane.showMessageDialog(null, "没有sql语句！！！");
			return;

		}
		Statement statement = null;
		try {
			statement = ConnectINFO.connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage());
			nowrong = false;
			e.printStackTrace();
		}
		try {
			statement.execute("use " + TreeTabledit.table.getDb().getName());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage());
			nowrong = false;
			e.printStackTrace();
		}

		for (Object key : TreeFrame.sqlmap.keySet()) {
			String vString = TreeFrame.sqlmap.get(key);
			try {
				statement.execute(vString);
				TreeFrame.keytodelete.remove(key);
				TreeFrame.oldfirstvaluesList.remove(key);
				// TreeFrame.sqList.clear();
				TreeFrame.tablevalues.remove(key);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());
				nowrong = false;
			}
		}
		// List<String> templist = new ArrayList<>(TreeFrame.sqList);
		// for (String aString : templist) {
		// try {
		// statement.execute(aString);
		//
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// JOptionPane.showMessageDialog(null, e.getMessage());
		// nowrong = false;
		// }
		// }
		TreeSQLedit.settext(TreeTabledit.getupdqtesql());
		if (nowrong) {

			JOptionPane.showMessageDialog(null, "执行sql成功！！！");
		}
		// TreeFrame.keytodelete.clear();
		// TreeFrame.oldfirstvaluesList.clear();
		// TreeFrame.sqList.clear();
		// TreeFrame.tablevalues.clear();

	}

}
