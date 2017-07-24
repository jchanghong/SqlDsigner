package mysqls.ui_frame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jiang on 2016/9/30 0030.
 * 主左边的树
 */
public class MainCenterPanel extends JPanel {
    private static MainCenterPanel me = null;

    public static MainCenterPanel getInstance() {
        if (me == null) {
            me = new MainCenterPanel();
        }
        return me;
    }

    private MainCenterPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setOpaque(true);

    }
}
