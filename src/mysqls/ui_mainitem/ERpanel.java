package mysqls.ui_mainitem;

import mysqls.contanst.ConnectINFO;
import mysqls.contanst.ConnectINFOListener;
import mysqls.framework.PersistenceService;
import mysqls.framework.ToolBar;
import mysqls.graph.ClassNode;
import mysqls.graph.Graph;
import mysqls.sql.databaseserver2.MYtreeNodeDB;
import mysqls.sql.entity.TableCompertor;
import mysqls.sql.util.MYsqlStatementUtil;
import mysqls.sql.util.SQLCreator;
import mysqls.ui_frame.EmptyPanel;
import mysqls.ui_frame.OP_Panel;
import mysqls.ui_frame.ToolPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by jiang on 2016/10/1 0001.
 * 下面的面板
 */
public class ERpanel extends JPanel implements ConnectINFOListener, ActionListener {
    private OP_Panel op_panel = null;
    private static ERpanel me = null;
    private GraphPanel aPanel = null;
    private Graph pGraph = null;

    public static ERpanel getInstance() {
        if (me == null) {
            me = new ERpanel();
        }
        return me;
    }

    private ERpanel() {
        setLayout(new BorderLayout());

        ConnectINFO.addLister(this);
        setOpaque(false);
        if (ConnectINFO.getInstance().getDatabase() == null) {
            JLabel text;
            Icon icon = new ImageIcon(EmptyPanel.class.getClassLoader().getResource("database/datas.png"));
            JLabel image = new JLabel(icon);
            text = new JLabel("请先选择数据库！");
            text.setHorizontalAlignment(SwingConstants.CENTER);
            Font font = new Font(Font.MONOSPACED, Font.BOLD, 30);
            text.setFont(font);
            text.setVerticalAlignment(SwingConstants.CENTER);
            add(image, BorderLayout.CENTER);
            add(text, BorderLayout.NORTH);
            return;
        }
        MYtreeNodeDB db = ConnectINFO.getInstance().getDatabase();
        StringBuilder builder = new StringBuilder();
        db.geTablesdata().stream().forEach(a -> builder.append(SQLCreator.create(a)));
        pGraph = PersistenceService.readSQL(builder.toString(), aPanel.aGraph);
        ToolBar sideBar = new ToolBar(pGraph);
        aPanel = new GraphPanel(pGraph, sideBar);
        op_panel = new OP_Panel();
        setop_panel(op_panel);
        add(op_panel, BorderLayout.NORTH);
        add(sideBar, BorderLayout.EAST);
        add(aPanel, BorderLayout.CENTER);
    }

    final static String STOSQL = "图形到SQL";
    final static String SUPDATE = "还没有想好1";
    final static String SEXESQL = "直接把模型加载到当前数据库";
    final static String OTHER = "还没有想好！";

    private void setop_panel(OP_Panel op_panel) {
        op_panel.additem(STOSQL, "22x22/copy.png", this);
        op_panel.additem(SUPDATE, "22x22/cut.png", this);
        op_panel.additem(SEXESQL, "22x22/redo.png", this);
        op_panel.additem(OTHER, "22x22/cut.png", this);
        op_panel.additem(OTHER, "22x22/cut.png", this);

    }


    @Override
    public void onchange(String name, Object news, Object oldies) {
        if (name.equals(ConnectINFO.DATABASE)) {
            if (aPanel == null) {
                removeAll();
                MYtreeNodeDB db = ConnectINFO.getInstance().getDatabase();
                StringBuilder builder = new StringBuilder();
                db.geTablesdata().stream().forEach(a -> builder.append(SQLCreator.create(a)));
                pGraph = PersistenceService.readSQL(builder.toString(), null);
//        aPanel.updateui();
                ToolBar sideBar = new ToolBar(pGraph);
                aPanel = new GraphPanel(pGraph, sideBar);
                op_panel = new OP_Panel();
                setop_panel(op_panel);
                add(op_panel, BorderLayout.NORTH);
                add(sideBar, BorderLayout.EAST);
                add(aPanel, BorderLayout.CENTER);
                validate();
                updateUI();
            } else {

                MYtreeNodeDB db = ConnectINFO.getInstance().getDatabase();
                StringBuilder builder = new StringBuilder();
                db.geTablesdata().stream().forEach(a -> builder.append(SQLCreator.create(a)));
                pGraph = PersistenceService.readSQL(builder.toString(), aPanel.aGraph);
                aPanel.aGraph = pGraph;
//                aPanel.updateui();
                validate();
                updateUI();
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case SEXESQL:
                sexesql();
                break;
            case SUPDATE:
                updated();
                break;
            case OTHER:
                doopther();
                break;
            case STOSQL:
                this2sql();
                break;
        }

    }

    private void updated() {
        JOptionPane.showMessageDialog(null, "4");
    }

    private void doopther() {
//        try {
//            Statement statement = ConnectINFO.getInstance().getConnection().createStatement();
//            String sql="insert into t1(id,name) values(1,'hello changhong');";
//            for (int i = 0; i < 50; i++) {
//                statement.execute(sql);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        JOptionPane.showMessageDialog(null, "3");
    }

    /**
     * 图形到sql之间的转发，内部框架知道每个pannel。所以这个功能应该在这个类里面实现比较合适
     */
    private void this2sql() {
        ToolPanel.getInstance(null).changeTo("SQL开发");
        List<ClassNode> list = aPanel.getClassNOdes();
        if (list.size() < 1) {
            JOptionPane.showMessageDialog(null, "没有sql图形！！！！");
            return;
        }
        StringBuilder builder = new StringBuilder();
        list.stream().map(a -> a.mTable).sorted(new TableCompertor()).forEach(b -> {

            builder.append(SQLCreator.create(b));
        });

        StringBuilder sql2exe = new StringBuilder();
        MYsqlStatementUtil.getsql2exe(builder.toString()).forEach(a -> sql2exe.append(a + ";"));
        SQLeditPanel.getInstance().setsql(sql2exe.toString());

    }

    /**
     * 直接把图形加载到数据库，
     *
     * @param text 数据库名字
     */
    private void graph2db(String dbname) {
        // TODO Auto-generated method stub

        Statement statement = null;
        List<ClassNode> list = aPanel.getClassNOdes();
        if (list.size() < 1) {
            JOptionPane.showMessageDialog(null, "没有sql图形！！！！");
            return;
        }
        try {
            statement = ConnectINFO.getInstance().getConnection().createStatement();
            statement.execute("use " + dbname + "; ");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        StringBuilder builder = new StringBuilder();
        list.stream().map(aa -> aa.mTable).sorted(new TableCompertor()).forEach(a -> {
            builder.append(SQLCreator.create(a));
        });
        Statement finalStatement = statement;
        MYsqlStatementUtil.getsql2exe(builder.toString()).forEach(a -> {

            try {
//                System.out.println(a);
                finalStatement.execute(a);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                JOptionPane.showMessageDialog(null, e.getMessage());
                e.printStackTrace();
            }
        });
//        DBselectFrame.getui().setVisible(false);
        JOptionPane.showMessageDialog(null, "导入成功！！！！");

    }

    private void sexesql() {
        graph2db(ConnectINFO.getInstance().getDatabase().getName());

    }
}
