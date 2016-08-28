
package mysqls.framework;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
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

import mysqls.graph.ClassNode;
import mysqls.graph.Graph;
import mysqls.sql.SQLEditPane;
import mysqls.sql.SQLlogPane;
import mysqls.sql.databaseserver.Connector;
import mysqls.sql.databaseserver.Users;
import mysqls.sql.sqlreader.StatementUtil;
import mysqls.sql.util.SQLCreator;

/**
 * 这是主要的显示类。包括是几乎所有的面板。
 */
@SuppressWarnings("serial")
public class GraphFrame extends JInternalFrame {
	private JTabbedPane aTabbedPane;
	private SQLlogPane sqLlogPane;
	private SQLEditPane msSqlEditPane;
	public GraphPanel aPanel;
	String database;
	String dbDrive;
	String username;
	String password;
	List<String> list = new ArrayList<String>();
	
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
		
	}

	/**
	 *
	 */
	public void loaddatabasealltables() {

	}

	/**
	 *
	 */
	public void databasemenu() {

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
		JRadioButton mysql =new JRadioButton("mysql");
		JRadioButton oracle =new JRadioButton("oracle");
		JRadioButton sqlsever =new JRadioButton("SQL Sever");
		ButtonGroup dbsevergroup = new ButtonGroup();
		JLabel uname = new JLabel("用户名");
		JLabel pwd = new JLabel("密    码");
		JLabel ipLabel = new JLabel("ip地址");
		JLabel portLabel = new JLabel("端   口");
		JTextField ipField = new JTextField("127.0.0.1",10);//ip地址的输入
		JTextField dbportField = new JTextField("3306",10);//端口号的输入
		JTextField usernameField = new JTextField(10);
		JPasswordField passwordField = new JPasswordField(10);
		JLabel recentLink = new JLabel("最近连接");
		JTextArea linkRecord = new JTextArea(7,7);
		linkRecord.setBounds(200, 50, 7, 0);
		linkRecord.setEditable(false);
		ipField.setEditable(true);
		dbportField.setEditable(true);
		usernameField.setEditable(true);
		passwordField.setEditable(true);
		JButton link = new JButton("连接");
		JButton cancel = new JButton("重新输入");
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
			String ipaddress = ipField.getText();
			String port = cancel.getText();
			user.setName(ipaddress);
			user.setName(port);
			user.setName(username);
			user.setPas(password);
			}
					
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
			}
					
		};
		
		//读取最近的链接
		File rectFile = new File("D:\\360data\\重要数据\\桌面\\学习软件\\text\\");
		File[] array = rectFile.listFiles();
		for(int i=0;i<array.length;i++){
			if(array[i].isFile()){
				linkRecord.append(array[i].getName());
				linkRecord.append("\n");
			}
		}
		
		linkRecord.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int clickTimes = e.getClickCount();
				if(clickTimes==2){
					Scanner scanner =null;
					try {
						
						scanner = new Scanner(new File("D:\\360data\\重要数据\\桌面\\学习软件\\text\\"+
					linkRecord.getSelectedText()));
						String str = null;
					    while (scanner.hasNextLine()) {
					    	
					          str = scanner.nextLine();
					          list.add(str);
					        }
					    String savemessage = list.toString();
					    dbDrive = savemessage.substring(1, savemessage.indexOf("*"));
					    username = savemessage.substring(savemessage.indexOf("*")+3, savemessage.indexOf(")"));
					    password = savemessage .substring(savemessage.indexOf(")")+3, savemessage.length()-1);
					    Connector.getConnection(dbDrive, dbDrive, username, password);
					} catch (FileNotFoundException e1) {
						
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
					    if(scanner !=null)
					          scanner.close();
					}
				}
			}
		});
		
		//监听选择的数据库服务器
		ActionListener radioButtonListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(mysql.isSelected()){
					database = "mysql";
				}else if(oracle.isSelected()){
					//后续加入oracle的驱动语句
				}else if(sqlsever.isSelected()){
					//后续加入sqlsever驱动语句
				}
			}
		};
		
		//监听选择的是连接还是重新输入
		ActionListener actionListener = new ActionListener() {
			
			@Override
			public void  actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			
			String choice = ((JButton)e.getSource()).getText();
				if(choice.equals("连接")){
					try {
						
						try {
							
							dbDrive="jdbc:"+database+"://"+ipField.getText()+":"+dbportField.getText()+
									"/?characterEncoding=utf8&useSSL=true";
							map.put("s", dbDrive);
							Connector.getConnection(map.get("s"), dbDrive, usernameField.getText(), passwordField.getText());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}else if(choice.equals("重新输入")){
					usernameField.setText("");
					passwordField.setText("");
				}
			}
		};
		
		link.addActionListener(actionListener);
		cancel.addActionListener(actionListener);
		mysql.addActionListener(radioButtonListener);
		oracle.addActionListener(radioButtonListener);
		sqlsever.addActionListener(radioButtonListener);
		
		JPanel jPanel = new JPanel(),
				jPanelNorth = new JPanel(new FlowLayout()),
				jPanelCenter = new JPanel(new GridLayout(4,2,-25,1)),
				jpanelEast = new JPanel(new BorderLayout()),
				jPanelSouth = new JPanel(new FlowLayout());
		
		jPanel.setLayout(new BorderLayout());
		jPanel.add(jPanelNorth,BorderLayout.NORTH);
		jPanel.add(jPanelCenter,BorderLayout.CENTER);
		jPanel.add(jpanelEast,BorderLayout.EAST);
		jPanel.add(jPanelSouth,BorderLayout.SOUTH);
		
		jPanelNorth.add(jLabel1);
		jPanelNorth.add(mysql);
		jPanelNorth.add(oracle);
		jPanelNorth.add(sqlsever);
		
		dbsevergroup.add(mysql);
		dbsevergroup.add(oracle);
		dbsevergroup.add(sqlsever);
		
		jPanelCenter.add(ipLabel);
		jPanelCenter.add(ipField);
		jPanelCenter.add(portLabel);
		jPanelCenter.add(dbportField);
		jPanelCenter.add(uname);
		jPanelCenter.add(usernameField);
		jPanelCenter.add(pwd);
		jPanelCenter.add(passwordField);
		
		jpanelEast.add(recentLink,BorderLayout.NORTH);
		jpanelEast.add(linkRecord,BorderLayout.CENTER);
		
		jPanelSouth.add(link);
		jPanelSouth.add(cancel);
		
		jFrame.add(jPanel);
		jFrame.setSize(400, 330);
		jFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		jFrame.setVisible(true);
		jFrame.pack();
		return user;
	}
	
}
