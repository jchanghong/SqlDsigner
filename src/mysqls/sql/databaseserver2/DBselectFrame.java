/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.databaseserver2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
import mysqls.framework.GraphFrame;
import mysqls.graph.ClassNode;
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
				JButton create = new JButton("创建");
				JButton open = new JButton("确认导入");
				JButton delete = new JButton("删除");

				Statement stat = ConnectINFO.connection.createStatement();

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
							try {

								showdatabases.setText("");
								stat.executeUpdate("drop database " + jTextField.getText());// 执行用户输入的创建数据库的命令
								JOptionPane.showMessageDialog(null, "删除成功！！！");
								ResultSet resultSet = stat.executeQuery("SHOW DATABASES");// 显示已有的数据库
								while (resultSet.next()) {

									showdatabases.append(resultSet.getString(1));
									showdatabases.append("\n");
									jTextField.setText("");
								}
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
		Statement statement = null;
		try {
			statement = ConnectINFO.connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (ClassNode classNode : list) {
			try {
				String create = SQLCreator.create(classNode.mTable);
				System.out.println(create);
				statement.execute(create);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 *
	 */
	protected static void graph2db() {
		// TODO Auto-generated method stub

	}

}
