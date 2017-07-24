package mysqls.sql;

import mysqls.ui_mainitem.GraphFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * @author jiang sql日志面板
 */
@SuppressWarnings("serial")
public class SQLlogPane extends JPanel {

    JButton toggle = null;
    GraphFrame mFrame;
    JButton mclearlog;
    private boolean logon = false;// 日志显示
    private static final String text_on = "显示日志";
    private static final String text_off = "隐藏日志";
    private static final String logend = "\n";
    private String logstring = "";
    private static final int high = 25;
    JTextArea mlog = null;
    JScrollPane mJScrollPane;
    JPanel mempty;
    JPanel mlogpanel;

    @SuppressWarnings("deprecation")
    public void appendA_log(String log) {

        StringBuilder builder = new StringBuilder();

        Date time = new Date(System.currentTimeMillis());
        builder.append(time.toLocaleString());
        builder.append(":::");
        builder.append(log);
        builder.append(SQLlogPane.logend);
        logstring = logstring + builder.toString();
        if (logon) {
            mlog.setText(logstring);
        }

    }

    private void seton() {
        if (toggle != null) {
            toggle.setText(SQLlogPane.text_off);
            toggle.setToolTipText(SQLlogPane.text_off);
        }
    }

    private void setoff() {
        if (toggle != null) {
            toggle.setText(SQLlogPane.text_on);
            toggle.setToolTipText(SQLlogPane.text_on);
        }
    }

    public SQLlogPane(GraphFrame graphFrame) {

        mFrame = graphFrame;

        setLayout(new BorderLayout());

        logon = false;
        createempty();
        createtoggleButton();
        createclearbutton();
        this.setMaximumSize(new Dimension(10000, 300));

    }

    private void createclearbutton() {
        // TODO Auto-generated method stub

        mclearlog = new JButton();
        mclearlog.setText("...");
        mclearlog.setAlignmentY(Component.CENTER_ALIGNMENT);
        mclearlog.setToolTipText("clear sql日志");
        mclearlog.setPreferredSize(new Dimension(SQLlogPane.high, SQLlogPane.high));
        mclearlog.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                logstring = "";
                mlog.setText(logstring);
            }
        });
        add(mclearlog, BorderLayout.WEST);
    }

    private void createempty() {
        // TODO Auto-generated method stub
        if (mlog == null) {
            mlog = new JTextArea();
            mlog.setEditable(false);
            mlog.setLineWrap(true);
            mlog.setText("");
            mempty = new JPanel(new BorderLayout());

            JTextArea jTextField = new JTextArea();
            jTextField.setEnabled(false);
            jTextField.setText("点击右边显示日志");
            mempty.add(jTextField, BorderLayout.CENTER);

            mJScrollPane = new JScrollPane(mlog);
            mJScrollPane.setPreferredSize(new Dimension(mJScrollPane.getPreferredSize().width, 300));
            mJScrollPane.setMaximumSize(new Dimension(getPreferredSize().width, 600));

            mlogpanel = new JPanel(new BorderLayout());
            mlogpanel.add(mJScrollPane, BorderLayout.CENTER);

        }

    }

    private void createtoggleButton() {
        toggle = new JButton();
        toggle.setText(SQLlogPane.text_on);

        toggle.setAlignmentY(Component.CENTER_ALIGNMENT);
        toggle.setToolTipText("显示sql日志");
        toggle.setPreferredSize(new Dimension(SQLlogPane.high, SQLlogPane.high));

        toggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent pEvent) {
                if (logon) {

                    logon = false;
                    setoff();
                    mempty.setBounds(mempty.getBounds());
                    remove(mlogpanel);
                    add(mempty, BorderLayout.CENTER);
                } else {
                    logon = true;
                    remove(mempty);
                    mlog.setText(logstring);
                    seton();
                    add(mlogpanel);

                }
            }

        });

        add(mempty, BorderLayout.CENTER);
        add(toggle, BorderLayout.EAST);
    }

}
