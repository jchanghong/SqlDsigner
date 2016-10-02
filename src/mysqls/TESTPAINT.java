package mysqls;

import mysqls.ui_frame.EmptyPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by jiang on 2016/10/2 0002.
 */
public class TESTPAINT extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);
        setOpaque(false);
        ImageIcon icon = new ImageIcon(EmptyPanel.class.getClassLoader().getResource("mainIcon/bj6.jpg"));
        //如果设置了背景图片则显示
        if(icon != null)
        {
            int width = this.getWidth();
            int height = this.getHeight();
            g.drawImage(icon.getImage(), 0, 0, width, height, this);

        }
        // fill Ellipse2D.Double
        GradientPaint redtowhite = new GradientPaint(0, 0, Color.white, 100, 0, Color.blue);
        g2.setPaint(redtowhite);
        g2.fill (new Ellipse2D.Double(0, 0, 100, 50));
    }
}
