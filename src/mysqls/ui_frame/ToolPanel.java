package mysqls.ui_frame;

import mysqls.ui_mainitem.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by jiang on 2016/9/30 0030.
 * 工具面板。
 */
public class ToolPanel extends JPanel implements ToolChangeLister{
    static private ToolPanel me=null;

    public Toolitem getCurrent() {
        return current;
    }

    public void setCurrent(Toolitem current) {
        this.current = current;
    }

    public java.util.List<Toolitem> getToolitemList() {
        return toolitemList;
    }

    public void setToolitemList(List<Toolitem> toolitemList) {
        this.toolitemList = toolitemList;
    }

    /**
 * 如果main为null，请确保对象已经建立
 * */
    public static ToolPanel getInstance(JPanel main) {
        if (me == null) {
            me = new ToolPanel(main);
        }
        return me;

    }
    public  static    Toolitem current=null;
    java.util.List<Toolitem> toolitemList = new ArrayList<>();

    public JPanel getMain() {
        return main;
    }

    private  JPanel main;
    private ToolPanel(JPanel main) {
        this.main = main;
        setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.gray));
        JPanel jPanel = new JPanel();
        jPanel.add(new JLabel("还没有做呢"));
        setLayout(new GridLayout(1, 0));
        setSize(0,20);
        add(new Toolitem("建立链接", "database/datas.png", ConnectPanel.getInstance(),main,this),BorderLayout.NORTH);
        add(new Toolitem("数据库建模", "database/data.png", ERpanel.getInstance(),main,this),BorderLayout.NORTH);
        add(new Toolitem("MYSQL变量", "database/data_off.png", VariablePanel.getInstance(),main,this),BorderLayout.NORTH);
        add(new Toolitem("SQL开发", "database/data_edit.png", SQLeditPanel.getInstance(),main,this),BorderLayout.NORTH);
        add(new Toolitem("备份", "database/data_backup.png",EmptyPanel.getInstance("还没有开始做呢。。。") ,main,this),BorderLayout.NORTH);
        add(new Toolitem("新建数据库", "database/table_redo.png", EmptyPanel.getInstance("还没有开始做呢。。。"),main,this),BorderLayout.NORTH);
        add(new Toolitem("删除数据库", "database/table_undo.png", EmptyPanel.getInstance("还没有开始做呢。。。"),main,this),BorderLayout.NORTH);
        add(new Toolitem("查找对象", "database/data_find.png", ObjectSerchPanel.getInstance(),main,this),BorderLayout.NORTH);
        add(new Toolitem("编辑数据", "database/table_edit.png", TableEditpanel.getInstance(),main,this),BorderLayout.NORTH);
        add(new Toolitem("云", "database/yun.jpg", CloudPanel.getInstance(),main,this),BorderLayout.NORTH);
        add(new Toolitem("设置", "database/configure.png", Option_shezhiPanel.getInstance(),main,this),BorderLayout.NORTH);

    }
/**
* 这个事件就是告诉你，toolitem被点击了，其他item要更新*/
    @Override
    public void onchange(Toolitem toolitem) {
        if (current != null) {

            current.setOpaque(false);
            current.updateUI();
        }
        current=toolitem;
        current.setOpaque(true);
        current.updateUI();
    }

    public void changeTo(String toolitemname) {
        if (current.getAction().equals(toolitemname)) {
            return;
        }
        Toolitem change = toolitemList.stream().filter(a->a.getAction().equals(toolitemname)).findAny().orElse(null);
        if (change == null) {
            return;
        } else {
            onchange(change);
            main.removeAll();
            main.add(getpanel(toolitemname));
            main.invalidate();
            main.updateUI();
        }
    }

    private Component getpanel(String toolitemname) {
        if (toolitemname.equals("SQL开发")) {
         return  SQLeditPanel.getInstance();
        }
        return ConnectPanel.getInstance();
    }

    /**
 * 重载更新list*/
    @Override
    public void add(Component comp, Object constraints) {
            toolitemList.add((Toolitem) comp);
        super.add(comp, constraints);
    }
}
