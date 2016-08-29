/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.databaseserver2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import mysqls.contanst.ConnectINFO;

/**
 * @author 长宏
 *
 */
public class TreeCenter {
	public static JPanel getui() {

		JPanel jPanel = new JPanel(new BorderLayout());

		JTextArea sqlEdit = new JTextArea(20, 40);
		JButton apply = new JButton("确定"), cancel = new JButton("取消");
		jPanel.add(apply, BorderLayout.CENTER);
		jPanel.add(cancel, BorderLayout.CENTER);
		jPanel.add(sqlEdit, BorderLayout.NORTH);

		StringBuilder builder = new StringBuilder();
		TreeListAll.sqList.stream().forEach(sql -> {
			builder.append(sql);
		});
		sqlEdit.setText(builder.toString());
		// sqlEdit记录所要操作的sql语句

		// 确定按钮执行所有的sql语句
		apply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					Statement statement = ConnectINFO.connection.createStatement();
					// for(sqlEdit.hasnextLine){
					// statement.execute(sql);
					// }
					TreeListAll.sqList.stream().forEach(sql -> {
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

			}
		});
		return jPanel;
	}

}
