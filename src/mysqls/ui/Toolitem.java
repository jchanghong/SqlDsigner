package mysqls.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by jiang on 2016/9/30 0030.
 */
public class Toolitem extends JPanel {

    public Toolitem(String action, String image,JPanel panel,JPanel mainparent) {
        super();
        setBackground(Color.gray);
        setLayout(new BorderLayout());
        JLabel button = new JLabel();
        Icon icon = new ImageIcon(getClass().getClassLoader().getResource(image));
        button.setIcon(icon);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        add(button, BorderLayout.CENTER);
        JLabel label = new JLabel(action);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.SOUTH);
        setOpaque(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("jdjdjj");

                mainparent.removeAll();
                mainparent.add(panel);
                mainparent.invalidate();
                mainparent.updateUI();

            }


            @Override
            public void mouseEntered(MouseEvent e) {

                super.mouseEntered(e);
                Toolitem.this.setOpaque(true);
//                Toolitem.this.invalidate();
                Toolitem.this.updateUI();
//                Toolitem.this.invalidate();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                Toolitem.this.setOpaque(false);
                Toolitem.this.updateUI();

            }
        });


    }

}
