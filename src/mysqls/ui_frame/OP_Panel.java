package mysqls.ui_frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by 长宏 on 2016/10/2 0002.
 * 操作面板,具体内容由需要用的面板提供
 */
public class OP_Panel extends JPanel{
    public OP_Panel() {
        setLayout(new GridLayout(1, 0));
        setBackground(Color.WHITE);
    }

    public void additem(String opname ,String image,ActionListener opliseter) {
        JPanel itempanel = new JPanel();
        itempanel.setLayout(new BorderLayout());

        Icon icon = new ImageIcon(getClass().getClassLoader().getResource(image));
        JLabel images = new JLabel();
        images.setIcon(icon);
        itempanel.add(images, BorderLayout.WEST);


        JLabel label = new JLabel(opname);
        label.setForeground(Color.blue);
//        label.setHorizontalAlignment(SwingConstants.WEST);
        itempanel.add(label, BorderLayout.CENTER);
        itempanel.setBackground(Color.GRAY);
        itempanel.setOpaque(false);
        itempanel.setForeground(Color.BLUE);
        itempanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                itempanel.setOpaque(true);
                itempanel. updateUI();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
               itempanel. setOpaque(false);
                itempanel.updateUI();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                opliseter.actionPerformed(new ActionEvent(label,0,opname));
            }
        });
        add(itempanel);

    }
}
