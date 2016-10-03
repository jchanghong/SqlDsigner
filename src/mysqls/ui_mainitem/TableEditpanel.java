package mysqls.ui_mainitem;

import mysqls.contanst.ConnectINFO;
import mysqls.contanst.ConnectINFOListener;
import mysqls.sql.databaseserver2.MYtreeNodeTable;
import mysqls.sql.databaseserver2.TreeSQLedit;
import mysqls.sql.databaseserver2.TreeTabledit;
import mysqls.ui_frame.EmptyPanel;

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
    JPanel sqlshow=null;
    private TableEditpanel() {
        ConnectINFO.addLister(this);
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        sqlshow=TreeSQLedit.getui();
        if (ConnectINFO.getInstance().getTable() == null) {
            JLabel text ;
            Icon icon = new ImageIcon(EmptyPanel.class.getClassLoader().getResource("database/datas.png"));
            JLabel image = new JLabel(icon);
            text = new JLabel("请先选择表！");
            text.setHorizontalAlignment(SwingConstants.CENTER);
            Font font = new Font(Font.MONOSPACED, Font.BOLD, 30);
            text.setFont(font);
            text.setVerticalAlignment(SwingConstants.CENTER);
            add(image, BorderLayout.CENTER);
            add(text, BorderLayout.NORTH);
            return;

        } else {
            TreeTabledit.edittable(ConnectINFO.getInstance().getTable(),this);
            add(sqlshow, BorderLayout.SOUTH);
        }
        setOpaque(false);

    }

    @Override
    public void onchange(String name, Object news, Object oldies) {

        if (name.equals("tablename")) {

            removeAll();
            add(sqlshow, BorderLayout.SOUTH);
            TreeTabledit.edittable(ConnectINFO.getInstance().getTable(),this);
            validate();
            updateUI();

        }
    }
}
