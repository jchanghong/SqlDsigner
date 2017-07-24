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

package mysqls.ui_util;

import mysqls.sql.databaseserver2.MYtreeNodeColumn;
import mysqls.sql.databaseserver2.MYtreeNodeDB;
import mysqls.sql.databaseserver2.MYtreeNodeTable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.Serializable;


public class MYtreelist_cell_render
        implements ListCellRenderer<Object>, Serializable {
    static DefaultTreeModel treeModel = new DefaultTreeModel(null);
    static JTree jTree = null;

    static {
        jTree = new JTree(treeModel);
        jTree.setCellRenderer(new MYdefaulttree_cellrender());

    }

    public Component getListCellRendererComponent(
            JList<?> list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        if (value instanceof MYtreeNodeDB) {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode(value);
            treeModel.setRoot(root);
            return jTree;
        }
        if (value instanceof MYtreeNodeTable) {
            MYtreeNodeTable mYtreeNodeTable = (MYtreeNodeTable) value;
            DefaultMutableTreeNode root = new DefaultMutableTreeNode(mYtreeNodeTable.getDb());
            DefaultMutableTreeNode defatable = new DefaultMutableTreeNode(mYtreeNodeTable);
            root.add(defatable);
            treeModel.setRoot(root);
            TreePath treePath = new TreePath(new Object[]{root});
            jTree.expandPath(treePath);
            return jTree;

        }
        if (value instanceof MYtreeNodeColumn) {
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(value);
            MYtreeNodeColumn mYtreeNodeTable = (MYtreeNodeColumn) value;
            DefaultMutableTreeNode root = new DefaultMutableTreeNode(mYtreeNodeTable.getTable().getDb());
            DefaultMutableTreeNode defatable = new DefaultMutableTreeNode(mYtreeNodeTable.getTable());
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
