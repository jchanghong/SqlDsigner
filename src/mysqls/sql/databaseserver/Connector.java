package mysqls.sql.databaseserver;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;

import javax.management.relation.RoleUnresolvedList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;
import com.sun.glass.ui.TouchInputSupport;
import com.sun.jndi.toolkit.ctx.StringHeadTail;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.sun.org.apache.regexp.internal.recompile;
import com.sun.webkit.ContextMenu.ShowContext;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister.Pack;

import mysqls.framework.GraphFrame;
import sun.reflect.generics.tree.ReturnType;

/**
 * @author Administrator连接server
 *
 */
public class Connector extends JInternalFrame{
	
	public static void runtext(Connection connection) throws SQLException{
		
		JFrame jFrame =new JFrame("show databases"); //此框架用来显示数据库和创建数据库
		JPanel jPanel = new JPanel(new BorderLayout());
		JPanel inputPanel = new JPanel(new BorderLayout());//用户输入面板
		JPanel choicePanel = new JPanel(new FlowLayout());
		
		JTextArea showdatabases = new JTextArea(10,10);//此文本域用来显示所有数据库
		showdatabases.setEditable(false);
		JScrollPane jScrollPane = new JScrollPane(jPanel);
		JLabel tips = new JLabel("输入数据库名:");//提示用户选择
		JTextField jTextField = new JTextField(10);//用户输入操作
		JButton create = new JButton("创建");
		JButton open = new JButton("打开");
		JButton delete = new JButton("删除");
		
		Statement stat = connection.createStatement();
		
		//显示所有的数据库
		ResultSet resultSet = stat.executeQuery("SHOW DATABASES");
		while(resultSet.next()){
			showdatabases.append(resultSet.getString(1));
			showdatabases .append("\n");
		}
		
		//实现鼠标双击选中文本并复制粘贴
		showdatabases.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					int clickTimes = e.getClickCount();
					if(clickTimes==2){
					  String text =showdatabases .getSelectedText();
					  jTextField.setText(text);
					}
					
				}
			});
		
		ActionListener actionListener  = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String choice = ((JButton)e.getSource()).getText();
				if(choice.equals("创建")){
					try {
						showdatabases.setText("");
						stat.executeUpdate("create database "+jTextField.getText());//执行用户输入的创建数据库的命令
						ResultSet resultSet = stat.executeQuery("SHOW DATABASES");//显示已有的数据库
						while(resultSet.next()){
							showdatabases.append(resultSet.getString(1));
							showdatabases .append("\n");
							jTextField.setText("");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}else if(choice.equals("删除")){
					try {
						showdatabases.setText("");
						stat.executeUpdate("drop database "+jTextField.getText());//执行用户输入的创建数据库的命令
						ResultSet resultSet = stat.executeQuery("SHOW DATABASES");//显示已有的数据库
						while(resultSet.next()){
							showdatabases.append(resultSet.getString(1));
							showdatabases .append("\n");
							jTextField.setText("");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}else if(choice.equals("打开")){
					JFrame jTables = new JFrame("show tables");
					JPanel jPanel = new JPanel(new BorderLayout());
					JPanel jpanelnorth = new JPanel();
					JPanel jpanelcenter = new JPanel();
					JPanel jPanelsouth = new JPanel(new FlowLayout());
				    JTextArea showTables = new JTextArea(10,10);
				    JScrollPane tablescrollpane = new JScrollPane(jPanel);
				    JTextField selectField = new JTextField(10);
				    JButton changeTable = new JButton("修改表");
				    JButton deleteTable = new JButton("删除表");
				     
					//显示选中数据库中所有的表
				    try {
						stat.executeQuery("USE "+jTextField.getText());
						ResultSet resultSet = stat.executeQuery("SHOW TABLES");
						while(resultSet.next()){
							showTables.append(resultSet.getString(1));
							showTables.append("\n");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					};
					showTables.addMouseListener(new MouseListener() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void mousePressed(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void mouseExited(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void mouseEntered(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void mouseClicked(MouseEvent e) {
							// TODO Auto-generated method stub
							int clickTimes = e.getClickCount();
							if(clickTimes==2){
							  String  selecttable =showTables .getSelectedText();
							  selectField.setText(selecttable);
							  }
						}
					});
					
				    ActionListener actionListener = new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							String choice = ((JButton)e.getSource()).getText();
							if(choice.equals("修改表")){
								
								try {
									viewTable(selectField.getText(),connection);
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								}else if(choice.equals("删除表")){
								try {
									
									stat.executeQuery("USE "+jTextField.getText());
									showTables.setText("");
									stat.executeUpdate("drop table "+selectField.getText());//执行用户输入的表
									selectField.setText("");
									ResultSet resultSet = stat.executeQuery("SHOW TABLES");//显示已有的表
									while(resultSet.next()){
										showTables.append(resultSet.getString(1));
										showTables .append("\n");
										}
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
							}
						}
				    	
				    };
				    
				    changeTable.addActionListener(actionListener);
				    deleteTable.addActionListener(actionListener);
				    
				    jpanelnorth.add(showTables);
				    jpanelcenter.add(selectField);
				    jPanelsouth.add(changeTable);
				    jPanelsouth.add(deleteTable);
				    
				    jPanel.add(jpanelnorth,BorderLayout.NORTH);
				    jPanel.add(jpanelcenter,BorderLayout.CENTER);
				    jPanel.add(jPanelsouth,BorderLayout.SOUTH);
				    
				    jTables.add(tablescrollpane);
				    jTables.setSize(400, 600);
				    jTables.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				    jTables.setVisible(true);

				}
			}
		};
		
		create.addActionListener(actionListener);
		open.addActionListener(actionListener);
		delete.addActionListener(actionListener);
		
		jPanel.add(showdatabases,BorderLayout.NORTH);
		jPanel.add(inputPanel,BorderLayout.CENTER);
		jPanel.add(choicePanel,BorderLayout.SOUTH);
		inputPanel.add(tips,BorderLayout.NORTH);
		inputPanel.add(jTextField,BorderLayout.CENTER);
		choicePanel.add(create);
		choicePanel.add(open);
		choicePanel.add(delete);
		
		jFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		jFrame.add(jScrollPane);
		jFrame.setSize(500, 500);
		jFrame.setVisible(true);
	}

public static Connection getConnection(String db,String dburl,String userName,String passWord) throws SQLException, IOException {
	
	String dbDriver = null;
	System.setProperty("jdbc.drives", db);
	Connection connection=null;
	try {
		connection= DriverManager.getConnection(dburl,userName,passWord);
		OutputStreamWriter outputStreamWriter;
		try {
			outputStreamWriter = new OutputStreamWriter(new FileOutputStream("D:\\360data\\重要数据\\桌面\\学习软件\\text\\mysql"+userName +".txt"),"UTF-8");
			BufferedWriter  bufferedWriter = new BufferedWriter(outputStreamWriter);
			bufferedWriter.write(dburl);
			bufferedWriter .write("*");
			bufferedWriter.newLine();
			bufferedWriter.write(userName);
			bufferedWriter.write(")");
			bufferedWriter.newLine();
			bufferedWriter.write(passWord);
			bufferedWriter.close();
			outputStreamWriter.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		JOptionPane.showMessageDialog(null, "连接失败,请输入正确信息！");
		e.printStackTrace();
	}
	
	runtext(connection);
	return connection;
	}

public static void viewTable(String tableName ,Connection connection) throws SQLException{
	
//	Object[][] columnName;
	String colMessage = null;
	String colName;
	Statement  statement = connection.createStatement();
	Vector<String> v1 = new Vector<String>();
	Vector<String> v2= new Vector<String>();
	
	JFrame  viewTable = new JFrame("view"+tableName);
	JPanel viewTablePanel = new JPanel();
	ResultSet tableMessage = statement.executeQuery("select * from "+tableName);
	tableMessage .next();
	int rowCount = tableMessage.getInt(1);
	ResultSetMetaData resultSetMetaData = tableMessage.getMetaData();
	int columnCount = resultSetMetaData.getColumnCount();
	
	JTable jTable = new JTable(0, columnCount);
	DefaultTableModel defaultTableModel =(DefaultTableModel) jTable.getModel();
	for(int col=columnCount;col>0;col--){
		colName = resultSetMetaData.getColumnName(col);
		v1.addElement(colName);
	}
	defaultTableModel.addRow(v1);
	
	for(int col=1;col<=columnCount;col++){
		tableMessage.first();
		colMessage = tableMessage.getString(col);
		v2.addElement(colMessage);
		}
		defaultTableModel.addRow(v2);
		
	while(tableMessage.next()){
		Vector<String> v3 = new Vector<String>();
		for(int col=1;col<=columnCount;col++){
			colMessage = tableMessage.getString(col);
			v3.addElement(colMessage);
		}
		defaultTableModel.addRow(v3);
	}
	
	jTable.setModel(defaultTableModel);
	JScrollPane jScrollPane = new JScrollPane(jTable);
	viewTablePanel.add(jScrollPane);
	viewTable.add(viewTablePanel);
	viewTable.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	viewTable.setSize(500, 500);
	viewTable.add(jScrollPane);
	viewTable.setVisible(true);
	connection.close();
}
}
