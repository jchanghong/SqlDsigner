package mysqls.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jiang on 2016/9/30 0030.
 * 工具面板。
 */
public class ToolPanel extends JPanel{
    public ToolPanel(JPanel main) {
        JPanel jPanel = new JPanel();
        jPanel.add(new JButton("jjdjdjjd"));
        setLayout(new GridLayout(1, 0));
        add(new Toolitem("11", "64x64/save.png",jPanel ,main),BorderLayout.NORTH);
        add(new Toolitem("11", "64x64/save.png",jPanel ,main),BorderLayout.NORTH);
        add(new Toolitem("11", "64x64/save.png",jPanel ,main),BorderLayout.NORTH);
        add(new Toolitem("11", "64x64/save.png",jPanel ,main),BorderLayout.NORTH);
        add(new Toolitem("11", "64x64/save.png",jPanel ,main),BorderLayout.NORTH);
        add(new Toolitem("11", "64x64/save.png",jPanel ,main),BorderLayout.NORTH);

    }
}
