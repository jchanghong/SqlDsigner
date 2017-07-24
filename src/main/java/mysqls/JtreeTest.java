import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                ProgressMonitor progressMonitor = new ProgressMonitor(JtreeTest.this, "message", "note", 0, 100);
                progressMonitor.setProgress(50);
                progressMonitor.setMillisToDecideToPopup(0);

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