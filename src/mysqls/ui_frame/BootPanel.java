package mysqls.ui_frame;

import mysqls.contanst.ConnectINFO;
import mysqls.contanst.ConnectINFOListener;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jiang on 2016/10/1 0001.
 * 下面的面板
 */
public class BootPanel extends JPanel implements ConnectINFOListener{
    private static BootPanel me=null;
    public static BootPanel getInstance() {
        if (me == null) {
            me=new BootPanel();
        }
        return me;
    }

    private BootPanel() {
        ConnectINFO.addLister(this);
        setBackground(Color.WHITE);
        add(new JLabel("mysql"));
    }


    @Override
    public void onchange(String name, Object news, Object oldies) {
        if (name.equals("databasename")) {
            removeAll();
            add(new JLabel(ConnectINFO.getInstance().getUser() + "  " + ConnectINFO.getInstance().getDatabaseName()));
            updateUI();
        }


    }
}
