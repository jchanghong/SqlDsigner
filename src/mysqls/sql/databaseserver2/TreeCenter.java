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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.StyledDocument;

import mysqls.contanst.ConnectINFO;

/**
 * @author 长宏 sql执行和编辑
 *
 */
public class TreeCenter {
	private static JPanel jPanel;

	public static JPanel getui() {

		if (TreeCenter.jPanel == null) {
			TreeCenter.jPanel = new JPanel(new BorderLayout());

			TreeCenter.gettextpanel();
			TreeCenter.jPanel.add(new JScrollPane(TreeCenter.textPane), BorderLayout.CENTER);
			JPanel buttons = new JPanel();
			buttons.setLayout(new GridLayout(1, 0));
			JButton apply = new JButton("确定"), cancel = new JButton("取消");
			buttons.add(apply);
			buttons.add(cancel);

			TreeCenter.jPanel.add(buttons, BorderLayout.SOUTH);
			// sqlEdit记录所要操作的sql语句

			TreeCenter.settext();
			// 确定按钮执行所有的sql语句
			apply.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						Statement statement = ConnectINFO.connection.createStatement();
						TreeFrame.sqList.stream().forEach(sql -> {
							try {
								statement.execute(sql);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						});
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

			// 取消按钮取消之前的操作
			cancel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					TreeCenter.textPane.setText("");
					TreeFrame.sqList.clear();

				}
			});
		}

		return TreeCenter.jPanel;
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
		TreeCenter.textPane = msqlpane;
		return msqlpane;

	}

	private static JTextPane textPane;

	public static void settext() {
		StringBuilder builder = new StringBuilder();
		TreeFrame.sqList.stream().forEach(a -> {
			builder.append(a + "\n");
		});
		TreeCenter.textPane.setText(builder.toString());
		SQLcolorSmall.setcolor(TreeCenter.textPane);

		if (TreeFrame.me != null) {

			TreeFrame.me.pack();
		}
	}

}
