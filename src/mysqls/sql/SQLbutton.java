package mysqls.sql;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.sun.javafx.collections.SetListenerHelper;

import mysqls.framework.GraphFrame;

public class SQLbutton extends JPanel{

	GraphFrame mFrame;
	private JButton mcreateSQL;
	private JButton mcreateNode;
	public SQLbutton(GraphFrame graphFrame) {
		// TODO Auto-generated constructor stub
		mFrame=graphFrame;
		setLayout(new GridLayout(0, 2));
		mcreateNode=new JButton("从sql语句生成实体关系图");
		mcreateSQL=new JButton("从下面的实体关系图生成sql");
		add(mcreateSQL);
		add(mcreateNode);
		SetListener();
				
	}
	private void SetListener() {
		// TODO Auto-generated method stub
		mcreateNode.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		mcreateSQL.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	
}
