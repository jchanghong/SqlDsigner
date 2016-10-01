package mysqls.ui_frame;

import mysqls.contanst.ConnectINFO;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jiang on 2016/10/1 0001.
 * 下面的面板
 */
public class BootPanel extends JPanel {
    private static BootPanel me=null;
    public static BootPanel getInstance() {
        if (me == null) {
            me=new BootPanel();
        }
        return me;
    }

    private BootPanel() {
        setBackground(Color.blue);
        add(new JLabel("mysql"));
    }

    public void onconnect() {
        removeAll();
        add(new JLabel(ConnectINFO.getInstance().getUser() + "  " + ConnectINFO.getInstance().getDatabaseName()));
    }
}
