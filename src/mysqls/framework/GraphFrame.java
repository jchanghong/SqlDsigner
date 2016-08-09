
package mysqls.framework;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


import javax.sql.rowset.JdbcRowSet;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CORBA.PUBLIC_MEMBER;

import com.sun.javafx.collections.MappingChange.Map;

import javafx.scene.layout.Border;

import java.util.Properties;

import jdk.internal.dynalink.beans.StaticClass;
import mysqls.graph.ClassNode;
import mysqls.graph.Graph;
import mysqls.sql.SQLEditPane;
import mysqls.sql.SQLlogPane;
import mysqls.sql.databaseserver.Connector;
import mysqls.sql.databaseserver.Users;
import mysqls.sql.sqlreader.StatementUtil;
import mysqls.sql.ui.MyDialog;
import mysqls.sql.util.SQLCreator;
import sun.util.locale.provider.JRELocaleProviderAdapter;
import sun.util.logging.resources.logging;

/**
 * 这是主要的显示类。包括是几乎所有的面板。
 */
@SuppressWarnings("serial")
public class GraphFrame extends JInternalFrame {
	private JTabbedPane aTabbedPane;
	private SQLlogPane sqLlogPane;
	private SQLEditPane msSqlEditPane;
	public GraphPanel aPanel;

	private File aFile; // The file associated with this graph

	/**
	 * Constructs a graph frame with an empty tool bar.
	 *
	 * @param pGraph
	 *            the initial graph
	 * @param pTabbedPane
	 *            the JTabbedPane associated with this GraphFrame.
	 */
	public GraphFrame(Graph pGraph, JTabbedPane pTabbedPane) {
		aTabbedPane = pTabbedPane;
		ToolBar sideBar = new ToolBar(pGraph);
		aPanel = new GraphPanel(pGraph, sideBar);
		Container contentPane = getContentPane();
		contentPane.add(sideBar, BorderLayout.WEST);
		sqLlogPane = new SQLlogPane(this);
		contentPane.add(sqLlogPane, BorderLayout.SOUTH);
		msSqlEditPane = new SQLEditPane(this);
		contentPane.add(msSqlEditPane, BorderLayout.EAST);
		contentPane.add(new JScrollPane(aPanel), BorderLayout.CENTER);
		setComponentPopupMenu(null); // Removes the system pop-up menu full of
										// disabled buttons.
	}

	/**
	 * sql到图形
	 */
	public void sql2graph() {

		String sql = msSqlEditPane.msqlpane.getText();
		if (!StatementUtil.isOKstatement(sql)) {
			JOptionPane.showMessageDialog(null, "sql错误了！！！！");
			return;
		}
		aPanel.aGraph = PersistenceService.readSQL(sql, aPanel.aGraph);

		aPanel.updateui();

	}

	/**
	 * 图形到sql之间的转发，内部框架知道每个pannel。所以这个功能应该在这个类里面实现比较合适
	 */
	public void graph2sql() {
		List<ClassNode> list = aPanel.getClassNOdes();
		if (list.size() < 1) {
			JOptionPane.showMessageDialog(null, "没有sql图形！！！！");
			return;
		}
		StringBuilder builder = new StringBuilder();
		for (ClassNode classNode : list) {
			builder.append(SQLCreator.create(classNode.mTable));
		}
		msSqlEditPane.setsql(builder.toString());

	}

	/**
	 * @return
	 */
	public GraphPanel getaPanel() {
		return aPanel;
	}

	/**
	 * Gets the file property.
	 *
	 * @return the file associated with this graph
	 */
	public File getFileName() {
		return aFile;
	}

	/**
	 * Gets the graph that is being edited in this frame.
	 *
	 * @return the graph
	 */
	public Graph getGraph() {
		return aPanel.getGraph();
	}

	/**
	 * Gets the graph panel that is contained in this frame.
	 *
	 * @return the graph panel
	 */
	public GraphPanel getGraphPanel() {
		return aPanel;
	}

	/**
	 * This association and getter method are needed to display messages using
	 * the copy to clipboard functionality of the Optional ToolBar.
	 *
	 * @return aTabbedPane the JTabbedPane associated with this GraphFrame.
	 */
	public JTabbedPane getJTabbedPane() {
		return aTabbedPane;
	}

	public SQLEditPane getMsSqlEditPane() {
		return msSqlEditPane;
	}

	public SQLlogPane getSqLlogPane() {
		return sqLlogPane;
	}

	/**
	 * Sets the file property.
	 *
	 * @param pFile
	 *            The file associated with this graph
	 */
	public void setFile(File pFile) {
		aFile = pFile;
		setTitle(aFile.getName());
	}

	public void setMsSqlEditPane(SQLEditPane msSqlEditPane) {
		this.msSqlEditPane = msSqlEditPane;
	}

	/**
	 * Sets the title of the frame as the file name if there is a file name.
	 *
	 * @param pModified
	 *            If the file is in modified (unsaved) state, appends an
	 *            asterisk to the frame title.
	 */
	public void setTitle(boolean pModified) {
		if (aFile != null) {
			String title = aFile.getName();
			if (pModified) {
				if (!getTitle().endsWith("*")) {
					setTitle(title + "*");
				}
			} else {
				setTitle(title);
			}
		}
	}

	/**
	 *
	 */
	public void graph2dbmenu() {
		// TODO Auto-generated method stub
		JTextArea jTextArea = new JTextArea("直接把数据库模型加载到数据库中，这样用户不用输入任何sql语句就能建立数据库。一般来说，一个应用程序只需要一个数据库，这样是很方便的。");
		jTextArea.setEditable(false);
		jTextArea.setFont(new Font("Default", Font.PLAIN, 24));
		jTextArea.setLineWrap(true);
		jTextArea.setColumns(20);
		jTextArea.setRows(11);
		jTextArea.setForeground(Color.white);
		jTextArea.setBackground(Color.black);
		JPanel jPanel = new JPanel();
		jPanel.add(new JScrollPane(jTextArea));
		new MyDialog("server").show(jPanel);
	}

	/**
	 *
	 */
	public void loaddatabasealltables() {
		// TODO Auto-generated method stub
		JTextArea jTextArea = new JTextArea("加载数据库中的所有表，如果没有选择数据库，就提示让用户先选择或者重新建立");
		jTextArea.setEditable(false);
		jTextArea.setLineWrap(true);
		jTextArea.setColumns(20);
		jTextArea.setRows(11);
		jTextArea.setForeground(Color.white);
		jTextArea.setBackground(Color.black);
		jTextArea.setFont(new Font("Default", Font.PLAIN, 24));
     	JPanel jPanel = new JPanel();
		jPanel.add(new JScrollPane(jTextArea));
		new MyDialog("server").show(jPanel);
	}

	/**
	 *
	 */
	public void databasemenu() {
		// TODO Auto-generated method stub
//		JFrame jFrame =new JFrame("show databases"); 
//		JPanel jPanel = new JPanel(new BorderLayout());
//		JTextArea showdatabases = new JTextArea();
//		showdatabases.setEditable(false);
//		jPanel.add(showdatabases,BorderLayout.NORTH);
//		jFrame.add(jPanel);
//		jFrame.setSize(300, 300);
//		jFrame.setVisible(true);
	}

	/**
	 * @throws SQLException 
	 *
	 */
	public Users servermenu(){
		java.util.Map<String, String> map=new HashMap<>();
		// TODO Auto-generated method stub
		JFrame jFrame = new JFrame();
		JLabel jLabel1 = new JLabel("选择数据库连接:");
		JLabel uname = new JLabel("用户名");
		JLabel pwd = new JLabel("密    码");
		JLabel ipLabel = new JLabel("ip地址");
		JLabel portLabel = new JLabel("端口");
		JTextField ip = new JTextField("127.0.0.1",21);//ip地址的输入
		JTextField dbport = new JTextField("3306",21);//端口号的输入
		JTextField username = new JTextField(20);
		JPasswordField password = new JPasswordField(21);
		ip.setEditable(true);
		dbport.setEditable(true);
		username.setEditable(true);
		password.setEditable(true);
		JCheckBox cb1=new JCheckBox("mysql");
		JButton link = new JButton("连接");
		JButton cancel = new JButton("取消");
		Users user = new Users(null, null, null);
		
		//录入ip,端口号，用户名和密码
		DocumentListener documentListener = new DocumentListener() {
					
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
			}
					
			@Override
			public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			String username = link.getText();
			String password = cancel.getText();
			user.setName(username);
			user.setPas(password);
			}
					
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
//				String ipaddress = ip.getText();
//				String portname = dbport.getText();
			}
					
		};
		
		String dbDrive="jdbc:mysql://"+ip.getText()+":"+dbport.getText()+
		"/?characterEncoding=utf8&useSSL=true";
		map.put("s", dbDrive);

		ActionListener actionListener = new ActionListener() {
			
			@Override
			public void  actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String choice = ((JButton)e.getSource()).getText();
				if(choice.equals("连接")){
					try {
						Connector.getConnection(map.get("s"), dbDrive, username.getText(), password.getText());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					databasemenu();
					
				}else if(choice.equals("取消")){
					username.setText("");
					password.setText("");
					ip.setText("");
					dbport.setText("");
				}
			}
		};
		link.addActionListener(actionListener);
		cancel.addActionListener(actionListener);
		
		JPanel jPanel = new JPanel(),
				jPanel2 = new JPanel(new FlowLayout()),
				jPanel3 = new JPanel(new FlowLayout()),
				jPanel4 = new JPanel(new FlowLayout());
		jPanel.setLayout(new BorderLayout());
		jPanel.add(jPanel2,BorderLayout.NORTH);
		jPanel.add(jPanel3,BorderLayout.CENTER);
		jPanel.add(jPanel4,BorderLayout.SOUTH);
		jPanel2.add(jLabel1);
		jPanel2.add(cb1);
		jPanel3.add(ipLabel);
		jPanel3.add(ip);
		jPanel3.add(portLabel);
		jPanel3.add(dbport);
		jPanel3.add(uname);
		jPanel3.add(username);
		jPanel3.add(pwd);
		jPanel3.add(password);
		jPanel4.add(link);
		jPanel4.add(cancel);
		jFrame.add(jPanel);
		jFrame.setSize(330, 330);
		jFrame.setVisible(true);
		return user;
	}
	
}
