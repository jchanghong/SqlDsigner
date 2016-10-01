package mysqls.ui_frame;

import mysqls.contanst.ConnectINFO;
import mysqls.sql.databaseserver2.TreeLeft;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jiang on 2016/10/1 0001.
 * 左边的树
 */
public class MainleftPanel extends JPanel {
    private static MainleftPanel me=null;
    public static MainleftPanel getInstance() {
        if (me == null) {
            me=new MainleftPanel();
        }
        return me;

    }
    private MainleftPanel() {
//        setBackground(Color.BLACK);
        if (ConnectINFO.getInstance().getConnection() == null) {

            add(new JLabel("请先建立链接！！！！"));
        } else {
            add(TreeLeft.getui());

        }
    }

    public void onconnect() {
        removeAll();
        add(TreeLeft.getui());
        updateUI();
//        BootPanel.getInstance().onconnect();

    }

}
