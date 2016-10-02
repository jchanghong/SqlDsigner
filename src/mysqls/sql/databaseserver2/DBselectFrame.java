/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.databaseserver2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import mysqls.contanst.ConnectINFO;
import mysqls.contanst.UIconstant;
import mysqls.ui_mainitem.GraphFrame;
import mysqls.graph.ClassNode;
import mysqls.sql.entity.TableCompertor;
import mysqls.sql.util.MYsqlStatementUtil;
import mysqls.sql.util.SQLCreator;

/**
 * @author 长宏 数据库选择面板
 *
 */
public class DBselectFrame {

	public static JFrame getui() {
		JFrame jFrame = UIconstant.frames.get(UIconstant.ALLdatable);
		if (jFrame == null) {
			try {
				jFrame = new JFrame("show databases"); // 此框架用来显示数据库和创建数据库
				JPanel jPanel = new JPanel(new BorderLayout());
				JPanel inputPanel = new JPanel(new BorderLayout());// 用户输入面板
				JPanel choicePanel = new JPanel(new FlowLayout());
				JPanel database = new JPanel();

				JTextArea showdatabases = new JTextArea(10, 20);// 此文本域用来显示所有数据库
				showdatabases.setEditable(false);
				database.add(showdatabases);
				database.add(new JScrollPane(showdatabases));
				JLabel tips = new JLabel("输入数据库名:");// 提示用户选择
				JTextField jTextField = new JTextField(10);// 用户输入操作
				jTextField.setText(ConnectINFO.getInstance().getDatabaseName());
				JButton create = new JButton("创建");
				JButton open = new JButton("确认导入");
				JButton delete = new JButton("删除");

				Statement stat = ConnectINFO.getInstance().getConnection().createStatement();

				// 显示所有的数据库
				ResultSet resultSet = stat.executeQuery("SHOW DATABASES");
				while (resultSet.next()) {
					showdatabases.append(resultSet.getString(1));
					showdatabases.append("\n");
				}

				// 实现鼠标双击选中文本并复制粘贴
				showdatabases.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						int clickTimes = e.getClickCount();
						if (clickTimes == 2) {
							String text = showdatabases.getSelectedText();
							jTextField.setText(text);

//							ConnectINFO.databaseName = text;
//							ConnectINFO.getInstance().setDatabaseName(text);

						}
					}
				});

				ActionListener actionListener = new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String choice = ((JButton) e.getSource()).getText();
						if (choice.equals("创建")) {
							try {

								if (jTextField.getText().length() < 1) {
									JOptionPane.showMessageDialog(null, "数据库名字不能为空!!!");
									return;

								}
								showdatabases.setText("");
								stat.executeUpdate("create database " + jTextField.getText());// 执行用户输入的创建数据库的命令
								JOptionPane.showMessageDialog(null, "创建成功！！！");
//								ConnectINFO.databaseName = jTextField.getText();
//								ConnectINFO.getInstance().setDatabaseName(jTextField.getText());
								ResultSet resultSet = stat.executeQuery("SHOW DATABASES");// 显示已有的数据库

								while (resultSet.next()) {
									showdatabases.append(resultSet.getString(1));
									showdatabases.append("\n");
									jTextField.setText("");
								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								if (jTextField.getText().equals("")) {
									JOptionPane.showMessageDialog(null, "输入需要创建的数据库名");
								}
								e1.printStackTrace();
							}

						} else if (choice.equals("删除")) {
							if (jTextField.getText().length() < 1) {
								JOptionPane.showMessageDialog(null, "数据库不能为空");
								return;
							}
							try {

								showdatabases.setText("");
								stat.executeUpdate("drop database " + jTextField.getText());// 执行用户输入的创建数据库的命令
								JOptionPane.showMessageDialog(null, "删除成功！！！");
								ResultSet resultSet = stat.executeQuery("SHOW DATABASES");// 显示已有的数据库
								while (resultSet.next()) {

									showdatabases.append(resultSet.getString(1));
									showdatabases.append("\n");
								}
								jTextField.setText("");
//								ConnectINFO.databaseName = "";
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								JOptionPane.showMessageDialog(null, e1.getMessage());
							}

						} else if (choice.equals("确认导入")) {
							if (jTextField.getText().length() < 1) {
								JOptionPane.showMessageDialog(null, "数据库不能为空!!!");
								return;
							}
							DBselectFrame.graph2db(jTextField.getText());
							// new OperationTable(jTextField.getText(),
							// ConnectINFO.connection);
							// jFrame.setExtendedState(HIDE_ON_CLOSE);
						}
					}
				};

				create.addActionListener(actionListener);
				open.addActionListener(actionListener);
				delete.addActionListener(actionListener);

				jPanel.add(database, BorderLayout.NORTH);
				jPanel.add(inputPanel, BorderLayout.CENTER);
				jPanel.add(choicePanel, BorderLayout.SOUTH);
				inputPanel.add(tips, BorderLayout.NORTH);
				inputPanel.add(jTextField, BorderLayout.CENTER);
				choicePanel.setLayout(new GridLayout(1, 0));
				choicePanel.add(create);
				choicePanel.add(open);
				choicePanel.add(delete);

				jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				jFrame.add(jPanel);
				jFrame.setSize(500, 500);
				jFrame.setLocation(400, 250);
				jFrame.setTitle("选择或者创建数据库");
				jFrame.pack();
				jFrame.setVisible(true);
				UIconstant.frames.put(UIconstant.ALLdatable, jFrame);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jFrame;

	}

	private static Statement statement;

	/**
	 * @param text
	 */
	protected static void graph2db(String text) {
		// TODO Auto-generated method stub

		List<ClassNode> list = GraphFrame.me.aPanel.getClassNOdes();
		if (list.size() < 1) {
			JOptionPane.showMessageDialog(null, "没有sql图形！！！！");
			return;
		}
		try {
			DBselectFrame.statement = ConnectINFO.getInstance().getConnection().createStatement();
			DBselectFrame.statement.execute("use " + text + "; ");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StringBuilder builder = new StringBuilder();
		list.stream().map(aa -> aa.mTable).sorted(new TableCompertor()).forEach(a -> {
			builder.append(SQLCreator.create(a));
		});
		MYsqlStatementUtil.getsql2exe(builder.toString()).forEach(a -> {

			try {
				System.out.println(a);
				DBselectFrame.statement.execute(a);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace();
			}
		});
		DBselectFrame.getui().setVisible(false);
		JOptionPane.showMessageDialog(null, "导入成功！！！！");

	}

	/**
	 *
	 */
	protected static void graph2db() {
		// TODO Auto-generated method stub

	}

}
