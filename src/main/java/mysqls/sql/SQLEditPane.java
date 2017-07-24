package mysqls.sql;//？？？？？

import mysqls.ui_mainitem.GraphFrame;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author jiang sql edit面板
 */
@SuppressWarnings("serial")
public class SQLEditPane extends JPanel {

    GraphFrame mFrame;
    private boolean ison = false;
    JButton toggle = null;
    private static final String text_on = ">>>";
    private static final String text_ontip = "隐藏sql面板";
    private static final String text_off = "<<<";
    private static final String textofftip = "显示sql面板";
    // private static final String logend = "\n";

    private String sqlstring = "" + "这只是测试内容，还在完善\n" + "create table user(id int,name char(20));\n"
            + "create view user1(id int,name char(20));\n" + "drop fff fff\n" + "";
    private static final int high = 25;

    public void setsql(final String sqlstring) {
        this.sqlstring = sqlstring;// ？？？？这里只是为了保存sql语句。函数调用完成后，sql还在
        if (msqlpane != null && ison) {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    msqlpane.setText(sqlstring);
                    SQLcolor.setcolor(msqlpane);
                }
            });
        }
    }

    DocumentListener documentListener;
    public JTextPane msqlpane = null;// sql文本编辑区的引用。
    JScrollPane mJScrollPane;// 滚动条。
    JPanel mempty;// jpanel

    private void seton()// 关闭sqledit界面的按钮。//？？
    {
        if (toggle != null) {
            toggle.setText(SQLEditPane.text_off);
            toggle.setToolTipText(SQLEditPane.text_ontip);// 鼠标在该按钮上停顿一下，就会显示“隐藏sql面板”的提示信息
        }
    }

    private void setoff() // 打开sqledit界面的按钮。//？？
    {
        if (toggle != null) {
            toggle.setText(SQLEditPane.text_on);
            toggle.setToolTipText(SQLEditPane.textofftip);// 鼠标在该按钮上停顿一下，就会显示“显示sql面板”的提示信息
        }
    }

    public SQLEditPane(final GraphFrame graphFrame) {

        mFrame = graphFrame;
        ison = false;
        setLayout(new BorderLayout());
        documentListener = new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        SQLcolor.setcolor(msqlpane);
                    }
                });
                //
                graphFrame.getSqLlogPane().appendA_log("remove :" + e.getDocument());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        SQLcolor.setcolor(msqlpane);
                    }
                });

                graphFrame.getSqLlogPane().appendA_log("insert :" + e.getDocument());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub

            }

        };
        createempty();

        createtoggleButton();

    }

    private void createempty() {
        // TODO Auto-generated method stub
        // 创建可编辑的文本区
        if (msqlpane == null) {
            msqlpane = new JTextPane();
            msqlpane.setEditable(true);// 可编辑的。
            StyledDocument document = msqlpane.getStyledDocument();

            document.addDocumentListener(documentListener);// 给sql面板设置的颜色。给关键字改颜色的方法和策略

            JPanel jPanel = new JPanel(new BorderLayout());
            jPanel.add(msqlpane);
            mJScrollPane = new JScrollPane(jPanel);// 添加滚动条。

            // 先将滚动条的长宽封装在Dimension中，如果 width 值或 height 值小于之前调用 setMinimumSize
            // 指定的最小大小，则它将自动增大。
            // mJScrollPane.setMinimumSize(new Dimension(300, (int)
            // mJScrollPane.getPreferredSize().getHeight()));

            mempty = new JPanel(new BorderLayout());
        }
    }

    private void createtoggleButton() {

        toggle = new JButton();
        toggle.setText("<<<");
        toggle.setAlignmentX(Component.CENTER_ALIGNMENT);
        toggle.setToolTipText("显示sql面板");
        toggle.setPreferredSize(new Dimension(SQLEditPane.high, SQLEditPane.high));
        toggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent pEvent) {
                if (ison) {

                    ison = false;
                    setoff();
                    remove(mJScrollPane);
                    add(mempty, BorderLayout.CENTER);
                } else {
                    ison = true;

                    // ui更新，其实可以不用这样，但是有些地方必须要这样
                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            setsqlstring();
                            SQLcolor.setcolor(msqlpane);
                            remove(mempty);
                            add(mJScrollPane, BorderLayout.CENTER);
                            seton();
                            mFrame.validate();
                            mFrame.invalidate();

                        }
                    });
                }
            }

        });

        add(mempty, BorderLayout.CENTER);
        add(toggle, BorderLayout.SOUTH);
    }

    private void setsqlstring() // 设置sqledit面板初始化内容。
    {

        msqlpane.setText(sqlstring);

    }
}
