package mysqls.ui_mainitem;

import mysqls.contanst.ConnectINFO;
import mysqls.contanst.ConnectINFOListener;
import mysqls.sql.databaseserver2.*;
import mysqls.ui_frame.EmptyPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 长宏 on 2016/10/1 0001.
 */
public class ObjectSerchPanel extends JPanel implements ConnectINFOListener{
    private static ObjectSerchPanel me=null;
    public static ObjectSerchPanel getInstance() {
        if (me == null) {
            me = new ObjectSerchPanel();
        }
        return me;
    }

    java.util.List<MYtreeNode> mYtreeNodeList = new ArrayList<>();
    private JList<MYtreeNode> jList;
    DefaultListModel<MYtreeNode> listModel;
    ListCellRenderer cellRenderer;
    private ObjectSerchPanel() {
        setBackground(Color.WHITE);
        ConnectINFO.addLister(this);
//        setdata();
        if (ConnectINFO.getInstance().getConnection() == null) {
            JLabel text ;
            Icon icon = new ImageIcon(EmptyPanel.class.getClassLoader().getResource("database/datas.png"));
            JLabel image = new JLabel(icon);
            text = new JLabel("请先链接数据库！");
            text.setHorizontalAlignment(SwingConstants.CENTER);
            Font font = new Font(Font.MONOSPACED, Font.BOLD, 30);
            text.setFont(font);
            text.setVerticalAlignment(SwingConstants.CENTER);
            add(image, BorderLayout.CENTER);
            add(text, BorderLayout.NORTH);
            return;
        }


    }

    private static String seachtext=null;
    private List<MYtreeNode> dosearch(String string) {
        seachtext=string;
        System.out.println(string);
        List<MYtreeNode> list = mYtreeNodeList.stream().filter(ObjectSerchPanel::searchfilster).collect(Collectors.toList());
        return list;
    }

    private static boolean searchfilster(MYtreeNode mYtreeNode) {
        if (mYtreeNode.getName().contains(seachtext)) {
            return true;
        }
        return false;
    }


    private void setdata() {
        if (mYtreeNodeList.size() > 0) {
            return;
        }
        mYtreeNodeList.clear();
        MYtreeNodeRoot mYtreeNodeRoot = new MYtreeNodeRoot("root");
        for (MYtreeNodeDB db : mYtreeNodeRoot.getdbs()) {
            mYtreeNodeList.add(db);
            for (MYtreeNodeTable table : db.geTables()) {
                mYtreeNodeList.add(table);
                for (MYtreeNodeColumn column : table.getcolumns()) {
                    mYtreeNodeList.add(column);
                }

            }
        }

    }

    @Override
    public void onchange(String name, Object news, Object oldies) {
        if (name.equals(ConnectINFO.CONNECTION)) {
            System.out.println(name);
            setuimy();
        }

    }

    private void setuimy() {
        removeAll();
        listModel = new DefaultListModel<>();
        cellRenderer = new MYtreelist_cell_render();
        jList = new JList(listModel);
        jList.setCellRenderer(cellRenderer);

        JTextField field = new JTextField(20);
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                listModel.clear();
                setdata();
                List<MYtreeNode> list= null;
                list = dosearch(field.getText());
                list.forEach(a->listModel.addElement(a));

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                listModel.clear();
                setdata();
                List<MYtreeNode> list= null;
                list = dosearch(field.getText());
                list.forEach(a->listModel.addElement(a));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        setLayout(new BorderLayout());
        add(new JScrollPane(jList));
        JPanel nouth = new JPanel(new GridLayout(1, 0));
        nouth.add(field);
        nouth.add(new JLabel("输入文字搜索"));
        add(nouth, BorderLayout.NORTH);

    }
}
