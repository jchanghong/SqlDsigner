package mysqls.ui_frame;

import mysqls.contanst.ConnectINFO;
import mysqls.contanst.ConnectINFOListener;
import mysqls.sql.databaseserver2.TreeLeft;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jiang on 2016/10/1 0001.
 * 左边的树
 */
public class MainleftPanel extends JPanel implements ConnectINFOListener {
    private static MainleftPanel me=null;
    public static MainleftPanel getInstance() {
        if (me == null) {
            me=new MainleftPanel();
        }
        return me;

    }
   private JTree jTree=null;
    private MainleftPanel() {
        ConnectINFO.addLister(this);
        setBackground(Color.WHITE);
        setOpaque(true);
        setLayout(new BorderLayout());
        if (ConnectINFO.getInstance().getConnection() == null) {
            JLabel text =null;

            Icon icon = new ImageIcon(EmptyPanel.class.getClassLoader().getResource("database/datas.png"));
            JLabel image = new JLabel(icon);
            text = new JLabel("请先建立链接！");
            text.setHorizontalAlignment(SwingConstants.CENTER);
            Font font = new Font(Font.MONOSPACED, Font.BOLD, 30);
            text.setFont(font);
            text.setVerticalAlignment(SwingConstants.CENTER);
            add(image, BorderLayout.CENTER);
            add(text, BorderLayout.NORTH);
        } else {
            add(TreeLeft.getui(),BorderLayout.CENTER);

        }
    }
    @Override
    public void onchange(String name, Object news, Object oldies) {
        if (name.equals("connection")) {
            if (jTree != null) {
                return;
            }
            jTree = TreeLeft.getui();
            removeAll();
            add(new JScrollPane(jTree),BorderLayout.CENTER);
            validate();
            updateUI();
        }

    }
}
