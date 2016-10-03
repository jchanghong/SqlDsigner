package mysqls.ui_util;

import mysqls.sql.databaseserver2.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * Created by jiang on 2016/10/3 0003.
 */
public class MYdefaulttree_cellrender extends DefaultTreeCellRenderer {
    static Font font = new Font("TimesRoman", Font.PLAIN, 16);
  static   Icon icondb = new ImageIcon(TreeLeft.class.getClassLoader().getResource("database/treedata.png"));
   static Icon icontable = new ImageIcon(TreeLeft.class.getClassLoader().getResource("database/treetable.png"));
   static Icon iconcolumn = new ImageIcon(TreeLeft.class.getClassLoader().getResource("database/treec.jpg"));
    /**
     * Configures the renderer based on the passed in components.
     * The value is set from messaging the tree with
     * <code>convertValueToText</code>, which ultimately invokes
     * <code>toString</code> on <code>value</code>.
     * The foreground color is set based on the selection and the icon
     * is set based on the <code>leaf</code> and <code>expanded</code>
     * parameters.
     *
     * @param tree
     * @param value
     * @param sel
     * @param expanded
     * @param leaf
     * @param row
     * @param hasFocus
     */
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        JLabel jLabel = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        DefaultMutableTreeNode node= (DefaultMutableTreeNode) value;
        MYtreeNode mYtreeNode= (MYtreeNode) node.getUserObject();
        if (mYtreeNode instanceof MYtreeNodeDB) {
            setIcon(icondb);
        }
        if (mYtreeNode instanceof MYtreeNodeTable) {
            setIcon(icontable);
        }
        if (mYtreeNode instanceof MYtreeNodeColumn) {
            setIcon(iconcolumn);
        }
        jLabel.setFont(font);

        return jLabel;

    }
}
