package mysqls.ui_mainitem;

import mysqls.contanst.ConnectINFO;
import mysqls.contanst.ConnectINFOListener;
import mysqls.ui_frame.EmptyPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by 长宏 on 2016/10/1 0001.
 */
public class VariablePanel extends JPanel implements ConnectINFOListener{
    private static VariablePanel me=null;
    public static VariablePanel getInstance() {
        if (me == null) {
            me = new VariablePanel();
        }
        return me;
    }

    private VariablePanel() {
        ConnectINFO.addLister(this);
        setLayout(new BorderLayout());
        if (ConnectINFO.getInstance().getConnection() == null) {
            JLabel text ;
            Icon icon = new ImageIcon(EmptyPanel.class.getClassLoader().getResource("database/datas.png"));
            JLabel image = new JLabel(icon);
            text = new JLabel("请先建立链接！");
            text.setHorizontalAlignment(SwingConstants.CENTER);
            Font font = new Font(Font.MONOSPACED, Font.BOLD, 30);
            text.setFont(font);
            text.setVerticalAlignment(SwingConstants.CENTER);
            add(image, BorderLayout.CENTER);
            add(text, BorderLayout.NORTH);
            return;
        }
        getui();

    }
        private  String serch = "";
        private  List<String> variables;
        private  List<String> variablestoshow;
         {
            variables = new ArrayList<>();
          variablestoshow = new ArrayList<>();
        }
    
        /**
         * @param
         */
        private  JPanel getui() {
            // TODO Auto-generated method stub


            JPanel jPanel = new JPanel();
            jPanel.setLayout(new GridLayout(1, 0));
            JLabel serchlable = new JLabel("输入任意文字开始搜索：");
            final JTextField[] serch = {new JTextField(20)};
            jPanel.add(serchlable);
            jPanel.add(serch[0]);
            serch[0].setText(this.serch);
            JTextArea variables = new JTextArea(10, 20);// 此文本域用来显示所有数据库

            JPanel edit = new JPanel();// edit 下面的
            edit.setLayout(new GridLayout(1, 0));
            JTextField edifid = new JTextField(20);
            JTextField edifidnewvalues = new JTextField(20);
            JButton editbutton = new JButton("确定更改");
            JButton shuaxinv = new JButton("刷新变量");
            jPanel.add(shuaxinv);

            edit.add(new JLabel("变量；"));
            edit.add(edifid);
            edit.add(new JLabel("新值；"));
            edit.add(edifidnewvalues);
            edit.add(editbutton);
            editbutton.addActionListener(e -> {
                // TODO Auto-generated method stub
                if (edifid.getText().length() < 2 || edifidnewvalues.getText().length() < 1) {
                    JOptionPane.showMessageDialog(null, "不能为空！！！");
                    return;
                }
                setnewvalues(edifid.getText(), edifidnewvalues.getText());

            });
            jPanel.setBackground(Color.WHITE);
            add(jPanel, BorderLayout.NORTH);
            variables.setBackground(Color.WHITE);
            add(new JScrollPane(variables), BorderLayout.CENTER);
            edit.setBackground(Color.WHITE);
            add(edit, BorderLayout.SOUTH);
            try {
                Statement statement = ConnectINFO.getInstance().getConnection().createStatement();
                ResultSet set = statement.executeQuery("show variables");
                while (set.next()) {
                    String e = set.getString("Variable_name") + "::" + set.getString("Value");
                    VariablePanel.this.variables.add(e);
                    // variables.append(e + "\n");
                }
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            serch[0].getDocument().addDocumentListener(new DocumentListener() {

                @Override
                public void removeUpdate(DocumentEvent e) {
                    // TODO Auto-generated method stub
                    String string = serch[0].getText();
                    VariablePanel.this.serch = string;
                    if (string == null || string.length() < 1) {
                        StringBuilder builder = new StringBuilder();
                        VariablePanel.this.variables.stream().forEach(a -> builder.append(a + "\n"));
                        variables.setText(builder.toString());
                    } else {
                        StringBuilder builder = new StringBuilder();
                        VariablePanel.this.variables.stream().filter(aa -> myfilter(aa))
                                .forEach(a -> builder.append(a + "\n"));
                        variables.setText(builder.toString());
                    }
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    // TODO Auto-generated method stub
                    String string = serch[0].getText();
                    VariablePanel.this.serch = string;
                    if (string == null || string.length() < 1) {
                        StringBuilder builder = new StringBuilder();
                        VariablePanel.this.variables.stream().forEach(a -> builder.append(a + "\n"));
                        variables.setText(builder.toString());
                    } else {
                        StringBuilder builder = new StringBuilder();
                        VariablePanel.this.variables.stream().filter(aa -> myfilter(aa))
                                .forEach(a -> builder.append(a + "\n"));
                        variables.setText(builder.toString());
                    }

                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    // TODO Auto-generated method stub
                    variables.setText(e.getType() + "");

                }
            });
            shuaxinv.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    try {
                        Statement statement = ConnectINFO.getInstance().getConnection().createStatement();
                        ResultSet set = statement.executeQuery("show variables");
                        VariablePanel.this.variables.clear();
                        while (set.next()) {
                            String eString = set.getString("Variable_name") + "====" + set.getString("Value");
                            VariablePanel.this.variables.add(eString);
                            StringBuilder builder = new StringBuilder();
                            VariablePanel.this.variables.stream().forEach(a -> builder.append(a + "\n"));
                            variables.setText(builder.toString());
                            // variables.append(e + "\n");
                        }
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });
            variables.setEditable(false);

            StringBuilder builder = new StringBuilder();
            VariablePanel.this.variables.stream().forEach(a -> builder.append(a + "\n"));
            variables.setText(builder.toString());
            // 实现鼠标双击选中文本并复制粘贴
            variables.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub
                    int clickTimes = e.getClickCount();
                    if (clickTimes == 2) {

                        String text = variables.getSelectedText();
                        edifid.setText(text);

                    }
                }
            });
            return null;
        }

        /**
         * @param text
         * @param text2
         */
        protected  void setnewvalues(String text, String text2) {
            // TODO Auto-generated method stub
            try {
                Statement statement = ConnectINFO.getInstance().getConnection().createStatement();
                statement.execute("set global " + text + "=" + text2);

                JOptionPane.showMessageDialog(null, "设置成功");
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                JOptionPane.showMessageDialog(null, "设置失败\n" + e1.getMessage());
                e1.printStackTrace();
            }

        }
    public   boolean myfilter(String aString) {
        if ( serch == null ||  serch.length() < 1) {
            return true;
        }
        List<Pattern> patterns = new ArrayList<>();
        Arrays.asList( serch.split("\\s+")).stream().forEach(serch -> {
            patterns.add(Pattern.compile(serch, Pattern.CASE_INSENSITIVE));
        });
        for (Pattern pattern : patterns) {
            if (pattern.matcher(aString).find()) {
                continue;

            } else {
                return false;
            }
        }
        return true;

    }

    @Override
    public void onchange(String name, Object news, Object oldies) {
        if (name.equals("connection")) {
            removeAll();
            getui();

        }
    }
}



