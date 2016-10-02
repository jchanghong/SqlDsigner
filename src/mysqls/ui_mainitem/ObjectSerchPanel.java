package mysqls.ui_mainitem;

import mysqls.sql.databaseserver2.MYtreeNode;
import mysqls.sql.databaseserver2.MYtreeNodeRoot;
import mysqls.ui_util.MYlist_cell_render;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 长宏 on 2016/10/1 0001.
 */
public class ObjectSerchPanel extends JPanel {
    private static ObjectSerchPanel me=null;
    public static ObjectSerchPanel getInstance() {
        if (me == null) {
            me = new ObjectSerchPanel();
        }
        return me;
    }

    private JList<MYtreeNode> jList;
    DefaultListModel<MYtreeNode> listModel;
    ListCellRenderer cellRenderer;
    private ObjectSerchPanel() {
        listModel = new DefaultListModel<>();
        cellRenderer = new MYlist_cell_render();
        jList = new JList(listModel);
        jList.setCellRenderer(cellRenderer);
        listModel.addElement(new MYtreeNodeRoot("eeee"));
        listModel.addElement(new MYtreeNodeRoot("eeee"));
        listModel.addElement(new MYtreeNodeRoot("eeee"));
        listModel.addElement(new MYtreeNodeRoot("eeee"));
        listModel.addElement(new MYtreeNodeRoot("eeee"));

        setLayout(new BorderLayout());
        add(jList);

    }
}
