/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.databaseserver2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import mysqls.contanst.ConnectINFO;
import mysqls.ui_mainitem.GraphFrame;
import mysqls.ui_mainitem.TreeFrame;

/**
 * @author 长宏树形 tree列表
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

	public static JPanel me;

	public static JPanel getui() {
		JPanel panel = new JPanel();
		TreeLeft.me = panel;
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
		JMenuItem jItemresh = new JMenuItem("刷新");
		JMenuItem jItemdelete = new JMenuItem("删除");
		JMenuItem jitemedit = new JMenuItem("编辑");
		JMenuItem jMenuload = new JMenuItem("加载到图形");
		ActionListener actionListener = ee -> {

			MYtreeNode node = TreeLeft.getmynode(jTree);
			DefaultMutableTreeNode node2ui = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();
			Object object = ee.getSource();
			if (object == jitemedit) {
				MYtreeNodeTable table = (MYtreeNodeTable) node;
				System.out.println("edit table:" + table.getName());
				TreeTabledit.edittable(table);
			}
			if (object == jMenuload) {
				MYtreeNodeDB db = (MYtreeNodeDB) node;
				TreeFrame.me.setVisible(false);
				GraphFrame.me.sql2graph(db.geTablesdata());
			}
			if (object == jItemresh) {

				TreeLeft.shuaxinnode(node, jTree, node2ui);
			}
			if (object == jItemdelete) {

				TreeLeft.deletenode(node, jTree, node2ui);
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

						TreeLeft.showpopmenu(popupMenu);
					}
					if (node instanceof MYtreeNodeTable) {

						TreeLeft.showpopmenu(popupMenu, 0, 1, 2);
					}
					if (node instanceof MYtreeNodeRoot) {
						TreeLeft.showpopmenu(popupMenu, 1);

					}
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

	/**
	 * @param node
	 * @param jTree
	 * @param node2ui
	 */
	private static void deletenode(MYtreeNode node, JTree jTree, DefaultMutableTreeNode node2ui) {
		// TODO Auto-generated method stub
		final DefaultTreeModel model = (DefaultTreeModel) jTree.getModel();
		if (node instanceof MYtreeNodeTable) {
			MYtreeNodeTable table = (MYtreeNodeTable) node;
			String dbname = table.getDb().getName();
			try {
				Statement statement = ConnectINFO.connection.createStatement();
				statement.executeQuery("use " + dbname);
				statement.execute("drop table " + table.getName());
				JOptionPane.showMessageDialog(null, "删除成功!!!");
				model.removeNodeFromParent(node2ui);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "删除失败!!!\n" + e.getMessage());
				e.printStackTrace();
			}
			// node2ui.
		}
		if (node instanceof MYtreeNodeDB) {
			MYtreeNodeDB db = (MYtreeNodeDB) node;
			try {
				Statement statement = ConnectINFO.connection.createStatement();
				statement.execute("drop database " + db.getName());
				JOptionPane.showMessageDialog(null, "删除成功!!!");
				model.removeNodeFromParent(node2ui);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "删除失败!!!\n" + e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/**
	 * @param node
	 * @param jTree
	 * @param node2ui
	 */
	private static void shuaxinnode(MYtreeNode node, JTree jTree, DefaultMutableTreeNode node2ui) {
		// TODO Auto-generated method stub
		final DefaultTreeModel model = (DefaultTreeModel) jTree.getModel();
		if (node instanceof MYtreeNodeTable) {
			while (node2ui.getChildCount() > 0) {

				model.removeNodeFromParent((MutableTreeNode) node2ui.getChildAt(0));

			}
			MYtreeNodeTable table = (MYtreeNodeTable) node;
			table.getcolumns().stream().forEach(a -> {
				model.insertNodeInto(new DefaultMutableTreeNode(a), node2ui, node2ui.getChildCount());
			});
			// jTree.repaint();
			// TreeLeft.me.invalidate();
			// 显示
			TreeNode[] nodes = model.getPathToRoot(node2ui.getChildAt(0));
			TreePath path = new TreePath(nodes);
			jTree.scrollPathToVisible(path);
		}

		if (node instanceof MYtreeNodeDB) {
			while (node2ui.getChildCount() > 0) {

				model.removeNodeFromParent((MutableTreeNode) node2ui.getChildAt(0));

			}
			MYtreeNodeDB db = (MYtreeNodeDB) node;
			for (MYtreeNodeTable table : db.geTables()) {
				DefaultMutableTreeNode mutableTreeNode = new DefaultMutableTreeNode(table);

				model.insertNodeInto(mutableTreeNode, node2ui, 0);
				for (MYtreeNodeColumn column : table.getcolumns()) {
					DefaultMutableTreeNode c = new DefaultMutableTreeNode(column);
					model.insertNodeInto(c, mutableTreeNode, 0);
				}

			}

			// jTree.repaint();
			// TreeLeft.me.invalidate();
			// 显示
			TreeNode[] nodes = model.getPathToRoot(node2ui.getChildAt(0));
			TreePath path = new TreePath(nodes);
			jTree.scrollPathToVisible(path);

		}
		if (node instanceof MYtreeNodeRoot) {
			while (node2ui.getChildCount() > 0) {

				model.removeNodeFromParent((MutableTreeNode) node2ui.getChildAt(0));

			}
			MYtreeNodeRoot root = (MYtreeNodeRoot) node;
			for (MYtreeNodeDB db : root.getdbs()) {

				DefaultMutableTreeNode dbNode = new DefaultMutableTreeNode(db);
				model.insertNodeInto(dbNode, node2ui, 0);
				for (MYtreeNodeTable table : db.geTables()) {
					DefaultMutableTreeNode mutableTreeNode = new DefaultMutableTreeNode(table);

					model.insertNodeInto(mutableTreeNode, dbNode, 0);
					for (MYtreeNodeColumn column : table.getcolumns()) {
						DefaultMutableTreeNode c = new DefaultMutableTreeNode(column);
						model.insertNodeInto(c, mutableTreeNode, 0);
					}

				}
			}

			// jTree.repaint();
			// TreeLeft.me.invalidate();
			// 显示
			TreeNode[] nodes = model.getPathToRoot(node2ui.getChildAt(0).getChildAt(0));
			TreePath path = new TreePath(nodes);
			jTree.scrollPathToVisible(path);

		}

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
