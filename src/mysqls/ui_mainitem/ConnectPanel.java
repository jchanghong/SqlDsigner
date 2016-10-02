package mysqls.ui_mainitem;
/**
 * 实体关系图和sql生产的实现
 */

import mysqls.contanst.ConnectINFO;
import mysqls.sql.util.MyIOutil;
import mysqls.ui_frame.EmptyPanel;
import mysqls.ui_util.UIFont;
import mysqls.ui_util.UI_color;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author 长宏 链接服务器的主面板。
 *
 */
public class ConnectPanel extends JPanel{
    @Override
    protected void paintComponent(Graphics g) {
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
    }

    private static ConnectPanel panel = null;
    private ConnectPanel() {
        setLayout(new GridLayout(2,2));
        add(getui(), BorderLayout.WEST);
        add(new JLabel(" "));
        add(new JLabel(" "));
        add(new JLabel(" "));
    }
    public static ConnectPanel getInstance() {
        if (panel == null) {
            panel = new ConnectPanel();
        }
        return panel;
    }
    /*设置ui*/
    private  JPanel getui() {
        JPanel jPanel = new JPanel(new BorderLayout());
        jPanel.setBackground(Color.WHITE);
            JLabel jLabel1 = new JLabel("选择数据库类型：");
            JRadioButton mysql = new JRadioButton("mysql");
            JRadioButton oracle = new JRadioButton("oracle");
            JRadioButton sqlsever = new JRadioButton("SQL Sever");
            ButtonGroup dbsevergroup = new ButtonGroup();
            JLabel uname = new JLabel("用户名：");
            JLabel pwd = new JLabel("密    码：");
            JLabel ipLabel = new JLabel("ip地址：");
            JLabel portLabel = new JLabel("端   口：");
            JTextField ipField = new JTextField("127.0.0.1", 15);// ip地址的输入
            JTextField dbportField = new JTextField("3306", 15);// 端口号的输入
            JTextField usernameField = new JTextField(15);
            JPasswordField passwordField = new JPasswordField(15);
            JLabel recentLink = new JLabel("最近连接：");
        recentLink.setBorder(BorderFactory.createEmptyBorder());
            JLabel linkRecord = new JLabel();
//            linkRecord.setBounds(200, 50, 7, 0);
//            linkRecord.setEditable(false);
            ipField.setEditable(true);
            dbportField.setEditable(true);
            usernameField.setEditable(true);
            passwordField.setEditable(true);
            JButton link = new JButton("连接");
            JButton cancel = new JButton("重新输入");

            // url|user|password 一行
            // 读取最近的链接
            File rectFile = new File(ConnectINFO.historyFilename);
            List<String> allrecord = new ArrayList<>();
            if (rectFile.exists()) {
                try {
                    Scanner reader = new Scanner(new BufferedInputStream(new FileInputStream(rectFile)));
                    while (reader.hasNextLine()) {
                        allrecord.add(reader.nextLine());
                    }
                    reader.close();
                } catch (FileNotFoundException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
            }
        StringBuilder builder = new StringBuilder();
            for (String string : allrecord) {
                builder.append(getrect2(string)+"\n");
//                linkRecord.append(getrect2(string));
//                linkRecord.append("\n");
            }
        if (allrecord.size() > 0) {
            linkRecord.setText(getrect2(allrecord.get(0)));
        }
            linkRecord.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub
                    int clickTimes = e.getClickCount();
                    if (clickTimes == 2) {
                        Scanner scanner = null;
                        try {

                            String select = linkRecord.getText();

                            String str = null;
                            str = geturl(select, allrecord);
                            if (str == null) {
                                return;
                            }
                            String[] urls = str.split("\\|");
                            ConnectINFO.getInstance().setConnection(DriverManager.getConnection(urls[0], urls[1], urls[2]));

                            ConnectINFO.getInstance().setUrl(urls[0]);
                            ConnectINFO.getInstance().setUrl(urls[1]);
                            ConnectINFO.getInstance().setPassworld(urls[2]);
                            JOptionPane.showMessageDialog(null, "链接成功\n");
                        } catch (Exception e1) {
                            JOptionPane.showMessageDialog(null, "链接失败\n" + e1.getMessage());
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        } finally {
                            if (scanner != null)
                                scanner.close();
                        }
                    }
                }
            });

            // 监听选择的数据库服务器
            ActionListener radioButtonListener = e -> {
                // TODO Auto-generated method stub
                if (mysql.isSelected()) {
                    ConnectINFO.getInstance().setDatabaseType("mysql");
                } else if (oracle.isSelected()) {
                    // 后续加入oracle的驱动语句
                } else if (sqlsever.isSelected()) {
                    // 后续加入sqlsever驱动语句
                }
            };

            // 监听选择的是连接还是重新输入
            ActionListener actionListener = e -> {
                // TODO Auto-generated method stub

                String choice = ((JButton) e.getSource()).getText();
                if (choice.equals("连接")) {
                    try {

                        try {


                            ConnectINFO.getInstance().setUser(usernameField.getText());
                            ConnectINFO.getInstance().setPassworld(passwordField.getText());
                            ConnectINFO.getInstance().setUrl("jdbc:" + ConnectINFO.getInstance().getDatabaseType() + "://" + ipField.getText() + ":"
                                    + dbportField.getText() + "/?characterEncoding=utf8&useSSL=true");
                            Connection c=DriverManager.getConnection(ConnectINFO.getInstance().getUrl(),
                                    usernameField.getText(), passwordField.getText());

                            if (c != null) {
                                ConnectINFO.getInstance().setConnection(c);
                                JOptionPane.showMessageDialog(null,"链接成功");
                                savainfo(ConnectINFO.getInstance().getUrl(), ConnectINFO.getInstance().getUser(), ConnectINFO.getInstance().getPassworld());

                            }

                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            JOptionPane.showMessageDialog(null,"链接失败\n"+e1.getMessage());
                            e1.printStackTrace();
                        }
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                } else if (choice.equals("重新输入")) {
                    usernameField.setText("");
                    passwordField.setText("");
                }
            };

            link.addActionListener(actionListener);
            cancel.addActionListener(actionListener);
            mysql.addActionListener(radioButtonListener);
            oracle.addActionListener(radioButtonListener);
            sqlsever.addActionListener(radioButtonListener);

          JPanel   jPanelNorth = new JPanel(new GridLayout(1,0)),
                    jPanelCenter = new JPanel(new GridLayout(0, 1)),
                    jpanelEast = new JPanel(new BorderLayout()), jPanelSouth = new JPanel();
        jpanelEast.setOpaque(false);
        jPanelCenter.setOpaque(false);
        jPanelNorth.setOpaque(false);
        jPanelSouth.setOpaque(false);

        JPanel mynouth = new JPanel(new BorderLayout());
        mynouth.add(jPanelNorth,BorderLayout.CENTER);
        mynouth.setOpaque(false);
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 30);
        JLabel label = new JLabel("你可以双击最近链接列表的选项直接链接！！！");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(font);
        mynouth.add(label, BorderLayout.NORTH);

           jPanel. add(mynouth, BorderLayout.NORTH);
        jPanel.   add(jPanelCenter, BorderLayout.CENTER);
//        jPanel.    add(jpanelEast, BorderLayout.EAST);
        jPanel.   add(jPanelSouth, BorderLayout.SOUTH);


            jPanelNorth.add(jLabel1);
            jPanelNorth.add(mysql);
            jPanelNorth.add(oracle);
            jPanelNorth.add(sqlsever);

            dbsevergroup.add(mysql);
            dbsevergroup.add(oracle);
            dbsevergroup.add(sqlsever);
        jPanelCenter.add(gethpanel(ipLabel,ipField));
        jPanelCenter.add(gethpanel(portLabel   ,dbportField));
        jPanelCenter.add(gethpanel(uname   ,usernameField));
        jPanelCenter.add(gethpanel(pwd   ,passwordField));
        jPanelCenter.add(gethpanel(recentLink   ,linkRecord));

//            jpanelEast.add(recentLink, BorderLayout.NORTH);
//            jpanelEast.add(linkRecord, BorderLayout.CENTER);

            jPanelSouth.setLayout(new GridLayout(1, 0));
            jPanelSouth.add(link);
            jPanelSouth.add(cancel);

        UI_color.setcolorR(jPanel);
        UI_color.setcolorR2(jPanel);
        UIFont.setfontR(jPanel);
        jPanel.setOpaque(false);
        return  jPanel;
        }
    private  void savainfo(String url, String user, String passworld) {
        // TODO Auto-generated method stub

        String rec = url + "|" + user + "|" + passworld + "\n";
        File rectFile = new File(ConnectINFO.historyFilename);

        List<String> allrecord = new ArrayList<>();
        if (rectFile.exists()) {
            try {
                Scanner reader = new Scanner(new BufferedInputStream(new FileInputStream(rectFile)));
                while (reader.hasNextLine()) {
                    allrecord.add(reader.nextLine());
                }
                reader.close();
            } catch (FileNotFoundException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
        }
        if (allrecord.contains(rec)) {
            return;

        }
        if (!rectFile.exists()) {
            try {
                rectFile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (rectFile.exists()) {
            try {

                MyIOutil.copy(rec, rectFile);
            } catch (Exception e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
        }

    }

    /**
     * @param url
     * @param user
     *            把mysql和user链接起来 格式；mysql2changhong
     * @param passworld
     */
    private  String getrect(String url, String user) {
        // TODO Auto-generated method stub
        String[] strings = url.split(":");
        return strings[1] + "2" + user;

    }

    private  String getrect2(String urString) {
        // TODO Auto-generated method stub
        String[] strings = urString.split("\\|");
        return getrect(strings[0], strings[1]);

    }

    /**
     * @param rec
     *            mysql2changhong
     * @return url，user，pass
     */
    private  String geturl(String rec, List<String> strings) {

        String[] all2 = rec.split("2");
        for (String string : strings) {
            if (string.contains(all2[0]) && string.contains(all2[1])) {
                return string;
            }
        }

        return null;
    }
    JPanel gethpanel(JComponent left,JComponent rigth) {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx=0;
        constraints.gridx=1;
        jPanel.setOpaque(false);
        jPanel.add(left,constraints);
        constraints.weightx=1;
        constraints.gridx=constraints.RELATIVE;
        jPanel.add(rigth,constraints);
        return jPanel;
    }
}

