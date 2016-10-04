package mysqls.ui_mainitem;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 长宏 on 2016/10/1 0001.
 * sql面板右边的提示面板
 */
public class SQL_edit_right_tishiPanel extends JPanel {
    JButton toggle = null;
    private static final String text_on = ">>>";
    private static final String text_ontip = "隐藏帮助";
    private static final String text_off = "<<<";
    private static final String textofftip = "显示帮助";
    private static final int high = 25;
    JScrollPane mJScrollPane;// 滚动条。
    private boolean ison = false;

    private void seton()// 关闭sqledit界面的按钮。//？？
    {
        if (toggle != null) {
            toggle.setText(text_off);
            toggle.setToolTipText(text_ontip);// 鼠标在该按钮上停顿一下，就会显示“隐藏sql面板”的提示信息
        }
    }

    private void setoff() // 打开sqledit界面的按钮。//？？
    {
        if (toggle != null) {
            toggle.setText(text_on);
            toggle.setToolTipText(textofftip);// 鼠标在该按钮上停顿一下，就会显示“显示sql面板”的提示信息
        }
    }

    public SQL_edit_right_tishiPanel() {
        setLayout(new BorderLayout());
        createempty();

    }

    JLabel help = null;
    JButton off = null;

    private void createempty() {
        if (help != null) {
            add(off, BorderLayout.SOUTH);
            add(help, BorderLayout.CENTER);
            return;
        }
        // TODO Auto-generated method stub
        setLayout(new BorderLayout());

        help = new JLabel("帮助");

        off = new JButton(text_off);
        off.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                creatcentent();
                validate();
                updateUI();
            }
        });
        off.setToolTipText(textofftip);
        add(off, BorderLayout.SOUTH);
        add(help, BorderLayout.CENTER);
    }

    JTree jTree = null;
    JButton on = null;

    private void creatcentent() {
        // TODO Auto-generated method stub
        setLayout(new BorderLayout());
        if (jTree != null) {
            add(new JScrollPane(jTree), BorderLayout.CENTER);
            add(on, BorderLayout.SOUTH);
            return;
        }

        jTree = createjtree();
        add(new JScrollPane(jTree), BorderLayout.CENTER);
        on = new JButton(text_on);
        on.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                createempty();
                revalidate();
                validate();
                updateUI();
            }
        });
        on.setToolTipText(text_ontip);
        add(on, BorderLayout.SOUTH);
    }

    private JTree createjtree() {
        initmapdata();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        jTree = new JTree(root);
        DefaultMutableTreeNode firstk = new DefaultMutableTreeNode("create table");
        root.add(firstk);
        DefaultMutableTreeNode firstv = new DefaultMutableTreeNode("create table a(a int);");
        firstk.add(firstv);
        ker2centent.forEach((myk, myv) -> {
            DefaultMutableTreeNode k = new DefaultMutableTreeNode(myk);
            root.add(k);
            DefaultMutableTreeNode v = new DefaultMutableTreeNode(myv);
            k.add(v);
        });
//        jTree.expandRow(0);
        TreePath path = new TreePath(new Object[]{root,firstk});
        jTree.expandPath(path);
        jTree.setRootVisible(false);
        jTree.addTreeExpansionListener(new TreeExpansionListener() {
            @Override
            public void treeExpanded(TreeExpansionEvent event) {
                if (prepath != null) {
                    jTree.collapsePath(prepath);
                }
                prepath = event.getPath();
                SQL_edit_right_tishiPanel.this.updateUI();
            }

            @Override
            public void treeCollapsed(TreeExpansionEvent event) {

            }
        });
        return jTree;
    }

    TreePath prepath = null;
    Map<String, String> ker2centent = new HashMap<>();

    private void initmapdata() {
        ker2centent.put("create database", "create database changhong");
        ker2centent.put("select", "select * from table1");
        ker2centent.put("update", "update table1 set c=1 where id=44;");
        ker2centent.put("crea3te", "cre------------------------");
        ker2centent.put("cre4ate", "cre------------------------");
        ker2centent.put("cr5eate", "cre------------------------");
        ker2centent.put("cr5eate", "cre------------------------");
        ker2centent.put("cr6eate", "cre------------------------");
        ker2centent.put("cr6eate", "cre------------------------");
        ker2centent.put("create", "cre------------------------");
        ker2centent.put("create", "cre------------------------");
        ker2centent.put("create", "cre------------------------");
        ker2centent.put("create", "cre------------------------");
        ker2centent.put("create", "cre------------------------");
        ker2centent.put("create", "cre------------------------");


    }
}
