package mysqls.ui_mainitem;

import mysqls.ui_frame.EmptyPanel;
import mysqls.ui_util.UIFont;
import mysqls.ui_util.UI_color;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by 长宏 on 2016/10/1 0001.
 */
public class Option_shezhiPanel extends JPanel {
    private ActionListener linclister;
    private ActionListener cancellister;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setOpaque(false);
        ImageIcon icon = new ImageIcon(EmptyPanel.class.getClassLoader().getResource("mainIcon/bj7.jpg"));
        //如果设置了背景图片则显示
        if (icon != null) {
            int width = this.getWidth();
            int height = this.getHeight();
            g.drawImage(icon.getImage(), 0, 0, width, height, this);

        }
    }

    private static Option_shezhiPanel panel = null;

    private Option_shezhiPanel() {
        inilister();
        setLayout(new GridLayout(2, 2));
        add(getui(), BorderLayout.WEST);
        add(new JLabel(" "));
        add(new JLabel(" "));
        add(new JLabel(" "));
    }

    private void inilister() {
        linclister = e -> {

            dolinc();
        };
        cancellister = e -> {
            docanle();

        };

    }

    private void docanle() {
        usernameField.setText("");
        passwordField.setText("");
    }

    private void dolinc() {

        if (usernameField.getText().trim().length() < 1 || passwordField.getText().trim().length() < 1) {
            JOptionPane.showMessageDialog(null, "名字和密码都不能为空！！！");
        } else {
            JOptionPane.showMessageDialog(null, "用户名或者密码错误，请从新输入");
        }
    }

    public static Option_shezhiPanel getInstance() {
        if (panel == null) {
            panel = new Option_shezhiPanel();
        }
        return panel;
    }
   private JTextField usernameField ;
   private JPasswordField passwordField ;
    /*设置ui*/
    private JPanel getui() {
        JPanel jPanel = new JPanel(new BorderLayout());
        jPanel.setBackground(Color.WHITE);
        JLabel uname = new JLabel("用户名：");
        JLabel pwd = new JLabel("密    码：");
         usernameField = new JTextField(15);
         passwordField = new JPasswordField(15);
        usernameField.setEditable(true);
        passwordField.setEditable(true);
        JButton link = new JButton("登录");
        link.addActionListener(linclister);
        JButton cancel = new JButton("重置");
        cancel.addActionListener(cancellister);

/*--------------------------------------------------------dd*/
        JPanel jPanelNorth = new JPanel(new GridLayout(1, 0)),
                jPanelCenter = new JPanel(new GridLayout(0, 1)),
                jPanelSouth = new JPanel();
        jPanelCenter.setOpaque(false);
        jPanelNorth.setOpaque(false);
        jPanelSouth.setOpaque(false);

        JLabel label = new JLabel("登录服务器");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel.add(label, BorderLayout.NORTH);
        jPanel.add(jPanelCenter, BorderLayout.CENTER);
        jPanelCenter.add(gethpanel(uname, usernameField));
        jPanelCenter.add(gethpanel(pwd, passwordField));
        jPanelCenter.add(gethpanel(link, cancel));
        UI_color.setcolorR(jPanel);
        UI_color.setcolorR2(jPanel);
        UIFont.setfontR(jPanel);
        jPanel.setOpaque(false);
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 40);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(font);
        label.setForeground(Color.white);
        return jPanel;
    }

    JPanel gethpanel(JComponent left, JComponent rigth) {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0;
        constraints.gridx = 1;
        jPanel.setOpaque(false);
        jPanel.add(left);
        constraints.weightx = 1;
        constraints.gridx = constraints.RELATIVE;
        jPanel.add(rigth);
        return jPanel;
    }
}
