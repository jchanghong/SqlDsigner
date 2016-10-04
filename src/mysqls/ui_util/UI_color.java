package mysqls.ui_util;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 长宏 on 2016/10/1 0001.
 */
public class UI_color {

    /*背景颜色*/
    public static void setcolorR(JComponent jPanel) {
        if (jPanel.getComponents().length == 0) {
            jPanel.setBackground(Color.black);
            jPanel.setOpaque(false);
            return;
        }
        for (Component con : jPanel.getComponents()) {
            setcolorR((JComponent) con);
        }


    }

    /*字体颜色*/
    public static void setcolorR2(JComponent jPanel) {
        if (jPanel.getComponents().length == 0) {
            jPanel.setForeground(Color.white);
            return;
        }
        for (Component con : jPanel.getComponents()) {
            setcolorR2((JComponent) con);
        }


    }

}
