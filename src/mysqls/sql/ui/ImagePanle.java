/**
 * 实体关系图和sql生产的实现
 */
package mysqls.sql.ui;

import mysqls.sql.databaseserver2.FrameVariables;

import javax.swing.*;
import java.awt.*;

/**
 * @author 长宏
 *
 */
public class ImagePanle extends JPanel {

    /**
     * @param imagepath
     *            设置背景
     */
    public static void setbj(JInternalFrame frame, String imagepath) {
        // 加载图片
        ImageIcon icon = new ImageIcon(FrameVariables.class.getClassLoader().getResource(imagepath));
        // 将图片放入label中
        JLabel label = new JLabel(icon);

        // 设置label的大小
        label.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // label.setBounds(0, 0, screenSize.width, screenSize.height);

        // 获取窗口的第二层，将label放入
        frame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));

        // 获取frame的顶层容器,并设置为透明
        JPanel j = (JPanel) frame.getContentPane();
        j.setOpaque(false);

        for (Component component : frame.getContentPane().getComponents()) {
            if (component instanceof JComponent) {
                JComponent panel = (JComponent) component;
                panel.setOpaque(false);
            }
        }
        // JPanel panel = new JPanel();
        // JTextField jta = new JTextField(10);
        // // JTextArea jta=new JTextArea(10,60);
        // JButton jb = new JButton("确定");
        // JButton jb2 = new JButton("重置");
        //
        // panel.add(jta);
        // panel.add(jb);
        // panel.add(jb2);
        //
        // // 必须设置为透明的。否则看不到图片
        // panel.setOpaque(false);
        frame.setSize(icon.getIconWidth(), icon.getIconHeight());

    }

}
