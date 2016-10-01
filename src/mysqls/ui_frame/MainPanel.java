package mysqls.ui_frame;

import mysqls.contanst.ConnectINFO;
import mysqls.contanst.ConnectINFOListener;
import mysqls.sql.databaseserver2.TreeLeft;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 长宏 on 2016/10/1 0001.
 * 中间
 */
public class MainPanel extends JPanel implements ConnectINFOListener{
    JPanel left=null;
    JPanel center=null;
private JSplitPane splitPane;
    public MainPanel(JPanel left, JPanel center) {
        setLayout(new BorderLayout());
         splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, center);
        splitPane.setDividerSize(5);
        splitPane.setBackground(Color.WHITE);
        splitPane.setOpaque(true);

        add(splitPane, BorderLayout.CENTER);
        splitPane.getLeftComponent().setBackground(Color.WHITE);

    }

    @Override
    public void onchange(String name, Object news, Object oldies) {
        if (name.equals("connection")) {
            validate();
            updateUI();
        }
    }
}
