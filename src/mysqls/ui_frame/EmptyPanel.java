package mysqls.ui_frame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 长宏 on 2016/10/1 0001.
 */
public class EmptyPanel extends JPanel{
    private static EmptyPanel me=null;
    public static EmptyPanel getInstance(String text) {
        if (me == null) {
            me = new EmptyPanel(text);

        } else {
            me.text.setText(text);
        }
        return me;
    }

    public void setText(String s) {
        text.setText(s);

    }
    private JLabel text=null;
    private EmptyPanel(String string) {
        setBackground(Color.WHITE);
        setOpaque(true);
        setLayout(new BorderLayout());
        Icon icon = new ImageIcon(EmptyPanel.class.getClassLoader().getResource("database/datas.png"));
        JLabel image = new JLabel(icon);
       text = new JLabel(string);
        text.setHorizontalAlignment(SwingConstants.CENTER);
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 30);
        text.setFont(font);
        text.setVerticalAlignment(SwingConstants.CENTER);
        add(image, BorderLayout.CENTER);
        add(text, BorderLayout.CENTER);
    }
}
