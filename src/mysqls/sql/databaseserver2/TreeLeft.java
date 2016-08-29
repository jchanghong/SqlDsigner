/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.databaseserver2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import mysqls.framework.GraphFrame;

/**
 * @author 长宏
 *
 */
public class TreeLeft {

	/**
	 * @param popupMenu
	 * @param integers要显示的
	 */
	private static void showpopmenu(JPopupMenu popupMenu, Integer... integers) {
		int count = popupMenu.getComponentCount();
		for (int i = 0; i < count; i++) {

			popupMenu.getComponent(i).setVisible(false);

		}
		for (Integer integer : integers) {
			if (integer >= count) {
				continue;
			}
			popupMenu.getComponent(integer).setVisible(true);
		}

	}

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
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem jItemresh = new JMenuItem("shuxin");
		JMenuItem jItemdelete = new JMenuItem("delete");
		JMenuItem jitemedit = new JMenuItem("edit");
		JMenuItem jMenuload = new JMenuItem("load2graph");
		ActionListener actionListener = ee -> {

			MYtreeNode node = TreeLeft.getmynode(jTree);
			Object object = ee.getSource();
			if (object == jitemedit) {
				MYtreeNodeTable table = (MYtreeNodeTable) node;
				System.out.println("edit table:" + table.getName());
				TreeNouth.edittable(table);
			}
			if (object == jMenuload) {
				MYtreeNodeDB db = (MYtreeNodeDB) node;
				GraphFrame.me.sql2graph(db.geTablesdata());
			}

		};
		jitemedit.addActionListener(actionListener);
		jItemresh.addActionListener(actionListener);
		jItemdelete.addActionListener(actionListener);
		jMenuload.addActionListener(actionListener);
		popupMenu.add(jItemdelete);
		popupMenu.add(jItemresh);
		popupMenu.add(jitemedit);
		popupMenu.add(jMenuload);
		jTree.addMouseListener(new MouseAdapter() {

			/*
			 * (non-Javadoc)
			 *
			 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.
			 * MouseEvent)
			 */
			@Override // 右键点击
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);

				if (e.getButton() == 3) {
					MYtreeNode node = TreeLeft.getmynode(e, jTree);
					if (node instanceof MYtreeNodeDB) {

						TreeLeft.showpopmenu(popupMenu, 0, 1, 3);
					}
					if (node instanceof MYtreeNodeColumn) {

						TreeLeft.showpopmenu(popupMenu, 0);
					}
					if (node instanceof MYtreeNodeTable) {

						TreeLeft.showpopmenu(popupMenu, 0, 1, 2);
					}
					if (node instanceof MYtreeNodeRoot) {
						TreeLeft.showpopmenu(popupMenu, 1);

					}
					popupMenu.getComponent(1).setVisible(false);
					popupMenu.show(jTree, e.getX(), e.getY());
				}

			}

			/*
			 * (non-Javadoc)
			 *
			 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.
			 * MouseEvent)
			 */
			@Override // 双击
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				if (e.getClickCount() == 2) {
					// popupMenu.getComponent(1).setVisible(true);
					// popupMenu.show(jTree, e.getX(), e.getY());
				}
			}

		});

		// jTree.setRootVisible(false);
		jScrollPane = new JScrollPane(jTree);

		JButton button = new JButton("dddd");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();
				System.out.println(node.getUserObject().getClass().toString());
			}
		});

		panel.add(jScrollPane, BorderLayout.CENTER);
		// panel.add(button, BorderLayout.EAST);
		return panel;
	}

	private static MYtreeNode getmynode(MouseEvent event, JTree jTree) {
		DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode) jTree
				.getPathForLocation(event.getX(), event.getY()).getLastPathComponent();
		return (MYtreeNode) defaultMutableTreeNode.getUserObject();

	}

	private static MYtreeNode getmynode(JTree jTree) {
		DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();
		return (MYtreeNode) defaultMutableTreeNode.getUserObject();

	}

}
