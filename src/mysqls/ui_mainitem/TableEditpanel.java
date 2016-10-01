package mysqls.ui_mainitem;

import mysqls.contanst.ConnectINFO;
import mysqls.contanst.ConnectINFOListener;
import mysqls.sql.databaseserver2.MYtreeNodeTable;
import mysqls.sql.databaseserver2.TreeSQLedit;
import mysqls.sql.databaseserver2.TreeTabledit;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 长宏 on 2016/10/1 0001.
 */
public class TableEditpanel extends JPanel implements ConnectINFOListener{
    private static TableEditpanel me=null;
    public static TableEditpanel getInstance() {
        if (me == null) {
            me = new TableEditpanel();
        }
        return me;
    }

    JPanel table;
    JPanel sqlshow;
    private TableEditpanel() {
        ConnectINFO.addLister(this);
        setLayout(new BorderLayout());
        table= TreeTabledit.getui();
        sqlshow= TreeSQLedit.getui();
        if (ConnectINFO.getInstance().getTableName() == null) {
            add(new JLabel("请先选择要编辑的表！！！"),BorderLayout.CENTER);
        } else {
            add(table, BorderLayout.CENTER);
            add(sqlshow, BorderLayout.SOUTH);
        }

    }

    @Override
    public void onchange(String name, Object news, Object oldies) {

        if (name.equals("tablename")) {
            removeAll();
            add(table, BorderLayout.CENTER);
            add(sqlshow, BorderLayout.SOUTH);
            TreeTabledit.edittable(ConnectINFO.tablenode);
            validate();
            updateUI();

        }
    }
}
