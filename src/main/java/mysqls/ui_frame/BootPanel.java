package mysqls.ui_frame;

import mysqls.contanst.ConnectINFO;
import mysqls.contanst.ConnectINFOListener;

import javax.swing.*;
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
        if (name.equals(ConnectINFO.USER) || name.equals(ConnectINFO.TABLE)) {
//            removeAll();
            ConnectINFO INFO = ConnectINFO.getInstance();
            String dbname = INFO.getTable() != null ? INFO.getTable().getDb().getName() : null;
            label.setText(getstatustext(INFO.getUser(), dbname, INFO.getTable() != null ? INFO.getTable().getName() : null));
            updateUI();
        }
        if (name.equals(ConnectINFO.DATABASE)) {
            ConnectINFO INFO = ConnectINFO.getInstance();
            label.setText(getstatustext(INFO.getUser(), INFO.getDatabase().getName(), null));
            updateUI();

        }

    }

    private String getstatustext(String user, String databaseName, String tableName) {
        String u = user != null ? "用户：" + user : "";
        String db = databaseName != null ? "数据库：" + databaseName : "";
        String table = tableName != null ? "表名：" + tableName : "";
        return "当前状态：" + u + db + table;

    }
}
