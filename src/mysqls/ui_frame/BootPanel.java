package mysqls.ui_frame;

import mysqls.contanst.ConnectINFO;
import mysqls.contanst.ConnectINFOListener;

import javax.swing.*;
import javax.xml.soap.Text;
import java.awt.*;

/**
 * Created by jiang on 2016/10/1 0001.
 * 下面的面板
 */
public class BootPanel extends JPanel implements ConnectINFOListener {
    private static BootPanel me = null;

    public static BootPanel getInstance() {
        if (me == null) {
            me = new BootPanel();
        }
        return me;
    }

    private JLabel label = null;

    private BootPanel() {
        setLayout(new BorderLayout());
        ConnectINFO.addLister(this);
        setBackground(Color.BLACK);
        setOpaque(true);
//        Font font = new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 16);
        label = new JLabel("mysql");
        label.setForeground(Color.white);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.BOTTOM);
        label.setBackground(Color.BLACK);
        add(label, BorderLayout.CENTER);
    }


    @Override
    public void onchange(String name, Object news, Object oldies) {
        if (name.equals("databasename")) {
//            removeAll();
            label.setText(ConnectINFO.getInstance().getUser() + "  " + ConnectINFO.getInstance().getDatabaseName());
            updateUI();
        }

    }
}
