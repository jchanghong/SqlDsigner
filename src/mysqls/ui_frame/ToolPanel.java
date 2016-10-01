package mysqls.ui_frame;

import mysqls.ui_mainitem.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by jiang on 2016/9/30 0030.
 * 工具面板。
 */
public class ToolPanel extends JPanel implements ToolChangeLister{
    public  static Toolitem current=null;

    public ToolPanel(JPanel main) {

        setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.gray));
        JPanel jPanel = new JPanel();
        jPanel.add(new JLabel("还没有做呢"));
        setLayout(new GridLayout(1, 0));
        setSize(0,20);
        add(new Toolitem("建立链接", "database/datas.png", ConnectPanel.getui(),main,this),BorderLayout.NORTH);
        add(new Toolitem("数据库建模", "database/data.png", ERpanel.getInstance(),main,this),BorderLayout.NORTH);
        add(new Toolitem("MYSQL变量", "database/data_off.png", VariablePanel.getInstance(),main,this),BorderLayout.NORTH);
        add(new Toolitem("SQL开发", "database/data_edit.png", SQLeditPanel.getInstance(),main,this),BorderLayout.NORTH);
        add(new Toolitem("备份", "database/data_backup.png",jPanel ,main,this),BorderLayout.NORTH);
        add(new Toolitem("新建数据库", "database/table_edit.png", TableEditpanel.getInstance(),main,this),BorderLayout.NORTH);
        add(new Toolitem("删除数据库", "database/table_edit.png", TableEditpanel.getInstance(),main,this),BorderLayout.NORTH);
        add(new Toolitem("建表", "database/table_edit.png", TableEditpanel.getInstance(),main,this),BorderLayout.NORTH);
        add(new Toolitem("删除表", "database/table_edit.png", TableEditpanel.getInstance(),main,this),BorderLayout.NORTH);
        add(new Toolitem("云", "database/yun.jpg", TableEditpanel.getInstance(),main,this),BorderLayout.NORTH);

    }

    @Override
    public void onchange(Toolitem toolitem) {
        if (current != null) {

            current.setOpaque(false);
            current.updateUI();
        }
        current=toolitem;
    }
}
