package mysqls.ui_frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by jiang on 2016/9/30 0030.
 */
public class Toolitem extends JPanel {
    String action;
    String imgae;
    JPanel actionpanel;
    JPanel maincenter;


    public Toolitem(String action, String image,JPanel panel,JPanel mainparent,ToolChangeLister lister) {
        super();
        this.action=action;
        this.imgae=image;
        this.actionpanel=panel;
        this.maincenter=mainparent;
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

                lister.onchange(Toolitem.this);
                mainparent.removeAll();
                mainparent.add(panel);
                mainparent.invalidate();
//                mainparent.repaint();
                mainparent.updateUI();

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                Toolitem.this.setBackground(Color.gray);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Toolitem.this.setBackground(Color.blue);

                super.mousePressed(e);
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
                if (ToolPanel.current != null && ToolPanel.current == Toolitem.this) {
                    return;
                }
                Toolitem.this.setOpaque(false);
                Toolitem.this.updateUI();

            }
        });


    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getImgae() {
        return imgae;
    }

    public void setImgae(String imgae) {
        this.imgae = imgae;
    }

    public JPanel getActionpanel() {
        return actionpanel;
    }

    public void setActionpanel(JPanel actionpanel) {
        this.actionpanel = actionpanel;
    }

    public JPanel getMaincenter() {
        return maincenter;
    }

    public void setMaincenter(JPanel maincenter) {
        this.maincenter = maincenter;
    }


}
