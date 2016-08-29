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
import mysqls.sql.databaseserver.OperationTable;

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
				JButton open = new JButton("打开");
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

								showdatabases.setText("");
								stat.executeUpdate("create database " + jTextField.getText());// 执行用户输入的创建数据库的命令
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
								ResultSet resultSet = stat.executeQuery("SHOW DATABASES");// 显示已有的数据库
								while (resultSet.next()) {

									showdatabases.append(resultSet.getString(1));
									showdatabases.append("\n");
									jTextField.setText("");
								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						} else if (choice.equals("打开")) {
							try {

								new OperationTable(jTextField.getText(), ConnectINFO.connection);
								// jFrame.setExtendedState(HIDE_ON_CLOSE);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
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

}
