package mysqls.sql.databaseserver;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.management.relation.RoleUnresolvedList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.sun.glass.ui.TouchInputSupport;
import com.sun.jndi.toolkit.ctx.StringHeadTail;
import com.sun.webkit.ContextMenu.ShowContext;

import mysqls.framework.GraphFrame;

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
		
		JTextArea showdatabases = new JTextArea(20,20);//此文本域用来显示所有数据库
		showdatabases.setEditable(false);
		JScrollPane jScrollPane = new JScrollPane(jPanel);
		JLabel tips = new JLabel("输入创建数据库或者已有的数据库:");//提示用户选择
		JTextField jTextField = new JTextField(10);//用户输入操作
		JButton create = new JButton("创建数据库");
		JButton open = new JButton("打开数据库");
		
		Statement stat = connection.createStatement();
		//显示所有的数据库
		ResultSet resultSet = stat.executeQuery("SHOW DATABASES");
		while(resultSet.next()){
			showdatabases.append(resultSet.getString(1));
			showdatabases .append("\n");
		}
		
		ActionListener actionListener  = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String choice = ((JButton)e.getSource()).getText();
				if(choice.equals("创建数据库")){
					try {
						stat.execute(jTextField.getText());//执行用户输入的创建数据库的命令
						ResultSet resultSet = stat.executeQuery("SHOW DATABASES");//显示已有的数据库
						while(resultSet.next()){
							showdatabases.replaceRange(resultSet.getString(1), 0, 20);
							showdatabases .append("\n");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		};
		
		create.addActionListener(actionListener);
		open.addActionListener(actionListener);
		
		jPanel.add(showdatabases,BorderLayout.NORTH);
		jPanel.add(inputPanel,BorderLayout.CENTER);
		jPanel.add(choicePanel,BorderLayout.SOUTH);
		inputPanel.add(tips,BorderLayout.NORTH);
		inputPanel.add(jTextField,BorderLayout.CENTER);
		choicePanel.add(create);
		choicePanel.add(open);
		
		jFrame.add(jScrollPane);
		jFrame.setSize(300, 300);
		jFrame.setVisible(true);
	}

public static Connection getConnection(String db,String dburl,String userName,String passWord) throws SQLException{
	
	String dbDriver = null;
	System.setProperty("jdbc.drives", db);
	Connection connection=null;
	connection= DriverManager.getConnection(dburl,userName,passWord);
	runtext(connection);
	return connection;
	
	}

}
