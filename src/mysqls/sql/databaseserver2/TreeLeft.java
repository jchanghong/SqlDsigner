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
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("chanh");
		for (int i = 0; i < 5; i++) {
			root.add(new DefaultMutableTreeNode(panel));
		}
		DefaultTreeModel model = new DefaultTreeModel(root);
		JTree jTree = new JTree(model);
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
