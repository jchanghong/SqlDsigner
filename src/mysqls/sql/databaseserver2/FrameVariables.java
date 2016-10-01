/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.databaseserver2;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import mysqls.contanst.ConnectINFO;
import mysqls.contanst.UIconstant;

/**
 * @author 长宏 显示mysql的所有变量
 *
 */
public class FrameVariables {

	public static String serch = "";
	public static List<String> variables;
	public static List<String> variablestoshow;
	static {
		FrameVariables.variables = new ArrayList<>();
		FrameVariables.variablestoshow = new ArrayList<>();
	}

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/mysql";
		try {
			Connection connection = DriverManager.getConnection(url, "root", "0000");
//			ConnectINFO.connection = connection;
			ConnectINFO.getInstance().setConnection(connection);
		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JFrame jFrame = FrameVariables.getui();
		jFrame.setVisible(true);

	}

	public static JFrame getui() {
		JFrame jFrame = UIconstant.frames.get(UIconstant.Framevariable);
		if (jFrame == null) {
			jFrame = new JFrame("mysql变量");
			UIconstant.frames.put(UIconstant.Framevariable, jFrame);

			jFrame.setSize(500, 500);
			jFrame.setLocation(400, 250);
			jFrame.setLayout(new BorderLayout());
			FrameVariables.setui(jFrame);

			jFrame.setResizable(false);
			// jFrame.pack();
		}
		return jFrame;

	}

	/**
	 * @param jFrame
	 */
	private static void setui(JFrame jFrame) {
		// TODO Auto-generated method stub

		JPanel jPanel = new JPanel();
		jPanel.setLayout(new GridLayout(1, 0));
		JLabel serchlable = new JLabel("输入任意文字开始搜索：");
		JTextField serch = new JTextField(20);
		jPanel.add(serchlable);
		jPanel.add(serch);
		serch.setText(FrameVariables.serch);
		JTextArea variables = new JTextArea(10, 20);// 此文本域用来显示所有数据库

		JPanel edit = new JPanel();// edit 下面的
		edit.setLayout(new GridLayout(1, 0));
		JTextField edifid = new JTextField(20);
		JTextField edifidnewvalues = new JTextField(20);
		JButton editbutton = new JButton("确定更改");
		JButton shuaxinv = new JButton("刷新变量");
		jPanel.add(shuaxinv);

		edit.add(new JLabel("变量；"));
		edit.add(edifid);
		edit.add(new JLabel("新值；"));
		edit.add(edifidnewvalues);
		edit.add(editbutton);
		editbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (edifid.getText().length() < 2 || edifidnewvalues.getText().length() < 1) {
					JOptionPane.showMessageDialog(null, "不能为空！！！");
					return;
				}
				FrameVariables.setnewvalues(edifid.getText(), edifidnewvalues.getText());

			}
		});
		jFrame.add(jPanel, BorderLayout.NORTH);
		jFrame.add(new JScrollPane(variables), BorderLayout.CENTER);
		jFrame.add(edit, BorderLayout.SOUTH);
		try {
			Statement statement = ConnectINFO.getInstance().getConnection().createStatement();
			ResultSet set = statement.executeQuery("show variables");
			while (set.next()) {
				String e = set.getString("Variable_name") + "::" + set.getString("Value");
				FrameVariables.variables.add(e);
				// variables.append(e + "\n");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		serch.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				String string = serch.getText();
				FrameVariables.serch = string;
				if (string == null || string.length() < 1) {
					StringBuilder builder = new StringBuilder();
					FrameVariables.variables.stream().forEach(a -> builder.append(a + "\n"));
					variables.setText(builder.toString());
				} else {
					StringBuilder builder = new StringBuilder();
					FrameVariables.variables.stream().filter(FrameVariables::myfilter)
							.forEach(a -> builder.append(a + "\n"));
					variables.setText(builder.toString());
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				String string = serch.getText();
				FrameVariables.serch = string;
				if (string == null || string.length() < 1) {
					StringBuilder builder = new StringBuilder();
					FrameVariables.variables.stream().forEach(a -> builder.append(a + "\n"));
					variables.setText(builder.toString());
				} else {
					StringBuilder builder = new StringBuilder();
					FrameVariables.variables.stream().filter(FrameVariables::myfilter)
							.forEach(a -> builder.append(a + "\n"));
					variables.setText(builder.toString());
				}

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				variables.setText(e.getType() + "");

			}
		});
		shuaxinv.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					Statement statement = ConnectINFO.getInstance().getConnection().createStatement();
					ResultSet set = statement.executeQuery("show variables");
					FrameVariables.variables.clear();
					while (set.next()) {
						String eString = set.getString("Variable_name") + "====" + set.getString("Value");
						FrameVariables.variables.add(eString);
						StringBuilder builder = new StringBuilder();
						FrameVariables.variables.stream().forEach(a -> builder.append(a + "\n"));
						variables.setText(builder.toString());
						// variables.append(e + "\n");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		variables.setEditable(false);

		StringBuilder builder = new StringBuilder();
		FrameVariables.variables.stream().forEach(a -> builder.append(a + "\n"));
		variables.setText(builder.toString());
		// 实现鼠标双击选中文本并复制粘贴
		variables.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int clickTimes = e.getClickCount();
				if (clickTimes == 2) {

					String text = variables.getSelectedText();
					edifid.setText(text);

				}
			}
		});

	}

	/**
	 * @param text
	 * @param text2
	 */
	protected static void setnewvalues(String text, String text2) {
		// TODO Auto-generated method stub
		try {
			Statement statement = ConnectINFO.getInstance().getConnection().createStatement();
			statement.execute("set global " + text + "=" + text2);

			JOptionPane.showMessageDialog(null, "设置成功");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "设置失败\n" + e1.getMessage());
			e1.printStackTrace();
		}

	}

	private static boolean myfilter(String aString) {
		if (FrameVariables.serch == null || FrameVariables.serch.length() < 1) {
			return true;
		}
		List<Pattern> patterns = new ArrayList<>();
		Arrays.asList(FrameVariables.serch.split("\\s+")).stream().forEach(serch -> {
			patterns.add(Pattern.compile(serch, Pattern.CASE_INSENSITIVE));
		});
		for (Pattern pattern : patterns) {
			if (pattern.matcher(aString).find()) {
				continue;

			} else {
				return false;
			}
		}
		return true;

	}

}
