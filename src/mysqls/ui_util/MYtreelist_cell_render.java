/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package javax.swing;

import mysqls.sql.databaseserver2.MYtreeNode;
import mysqls.sql.databaseserver2.MYtreeNodeColumn;
import mysqls.sql.databaseserver2.MYtreeNodeDB;
import mysqls.sql.databaseserver2.MYtreeNodeTable;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;


public class MYtreelist_cell_render
        implements ListCellRenderer<Object>, Serializable
{
  static   DefaultTreeModel treeModel = new DefaultTreeModel(null);
    static JTree jTree=null;
    static {
        jTree = new JTree(treeModel);

    }
    public Component getListCellRendererComponent(
            JList<?> list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus)
    {
        if (value instanceof MYtreeNodeDB) {
            DefaultMutableTreeNode root=new DefaultMutableTreeNode(value.toString());
            treeModel.setRoot(root);
            return jTree;
        }
        if (value instanceof MYtreeNodeTable) {
            MYtreeNodeTable mYtreeNodeTable= (MYtreeNodeTable) value;
            DefaultMutableTreeNode root=new DefaultMutableTreeNode(mYtreeNodeTable.getDb().toString());
            DefaultMutableTreeNode defatable = new DefaultMutableTreeNode(mYtreeNodeTable.toString());
            root.add(defatable);
            treeModel.setRoot(root);
            TreePath treePath = new TreePath(new Object[]{root});
            jTree.expandPath(treePath);
            return jTree;

        }
        if (value instanceof MYtreeNodeColumn) {
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(value.toString());
            MYtreeNodeColumn mYtreeNodeTable= (MYtreeNodeColumn) value;
            DefaultMutableTreeNode root=new DefaultMutableTreeNode(mYtreeNodeTable.getTable().getDb().toString());
        DefaultMutableTreeNode defatable = new DefaultMutableTreeNode(mYtreeNodeTable.getTable().toString());
            root.add(defatable);
            defatable.add(child);
            treeModel.setRoot(root);

            TreePath treePath = new TreePath(new Object[]{root, defatable});
            jTree.expandPath(treePath);
            return jTree;

        }
        return jTree;
    }


}
