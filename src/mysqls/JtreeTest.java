import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class JtreeTest extends JFrame {
    private static int i = 1;

    private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;
    private JTree tree;

    public JtreeTest() {
        root = new DefaultMutableTreeNode("默认根");

        // 利用根节点创建TreeModel
        this.treeModel = new DefaultTreeModel(root);

        tree = new JTree(treeModel);
        tree.setRootVisible(false); // 设置树的系统根不可见

        DefaultMutableTreeNode node0 = new DefaultMutableTreeNode("文件0");
        treeModel.insertNodeInto(node0, root, root.getChildCount());
        tree.expandRow(0);

        JScrollPane treeScrollPane = new JScrollPane(tree);
        this.getContentPane().add(treeScrollPane, BorderLayout.SOUTH);

        JButton btn = new JButton("增加树枝");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String temp = "文件" + i++;
                DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(temp);

                JtreeTest.this.treeModel.insertNodeInto(node1, root, root.getChildCount());

                JtreeTest.this.tree.updateUI();

                System.out.println(temp);
            }
        });
        this.getContentPane().add(btn, BorderLayout.NORTH);

        this.setTitle("树形菜单");
        this.pack();
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new JtreeTest();
    }
}