/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.databaseserver2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * @author 长宏
 *
 */
public class TreeLeft {

	public static JPanel getui() {
		JPanel panel = new JPanel();
		JScrollPane jScrollPane = new JScrollPane();
		MYtreeNodeRoot mYtreeNodeRoot = new MYtreeNodeRoot("root");
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(mYtreeNodeRoot);
		for (MYtreeNodeDB db : mYtreeNodeRoot.getdbs()) {
			DefaultMutableTreeNode dbNode = new DefaultMutableTreeNode(db);
			root.add(dbNode);
			for (MYtreeNodeTable table : db.geTables()) {
				DefaultMutableTreeNode mutableTreeNode = new DefaultMutableTreeNode(table);
				dbNode.add(mutableTreeNode);
				for (MYtreeNodeColumn column : table.getcolumns()) {
					DefaultMutableTreeNode c = new DefaultMutableTreeNode(column);
					mutableTreeNode.add(c);
				}

			}
		}
		DefaultTreeModel model = new DefaultTreeModel(root);
		JTree jTree = new JTree(model);
		jTree.setRootVisible(false);
		jScrollPane = new JScrollPane(jTree);

		panel.add(jScrollPane, BorderLayout.WEST);
		JButton button = new JButton("dddd");
		panel.add(button, BorderLayout.EAST);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();
				System.out.println(node.getUserObject().getClass().toString());
			}
		});

		return panel;
	}
}
