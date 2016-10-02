package mysqls.ui_frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by 长宏 on 2016/10/2 0002.
 * 操作面板,具体内容由需要用的面板提供
 */
public class OP_Panel extends JPanel{
    public OP_Panel() {
        setLayout(new GridLayout(1, 0));
        setBackground(Color.WHITE);
    }

    public void additem(String opname, ActionListener opliseter) {
        JButton button = new JButton(opname);
        button.addActionListener(opliseter);
        add(button);

    }
}
