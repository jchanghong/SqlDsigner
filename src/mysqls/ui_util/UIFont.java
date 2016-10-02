package mysqls.ui_util;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 长宏 on 2016/10/2 0002.
 */
public class UIFont {
    public static Font font = new Font("Default", Font.PLAIN, 20);
    public static void setfontR(JComponent jPanel) {
        if (jPanel.getComponents().length == 0) {
            jPanel.setFont(font);
            return;
        }
        for (Component con : jPanel.getComponents()) {
            setfontR((JComponent) con);
        }


    }
}
