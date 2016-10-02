package mysqls.ui_mainitem;

import mysqls.contanst.ConnectINFO;
import mysqls.contanst.ConnectINFOListener;
import mysqls.sql.databaseserver2.MYtreeNode;
import mysqls.sql.databaseserver2.MYtreeNodeRoot;

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
    private ObjectSerchPanel() {
        listModel = new DefaultListModel<>();
        jList = new JList(listModel);
        listModel.addElement(new MYtreeNodeRoot("eeee"));
        listModel.addElement(new MYtreeNodeRoot("eeee"));
        listModel.addElement(new MYtreeNodeRoot("eeee"));
        listModel.addElement(new MYtreeNodeRoot("eeee"));
        listModel.addElement(new MYtreeNodeRoot("eeee"));

        setLayout(new BorderLayout());
        add(jList);

    }
}
