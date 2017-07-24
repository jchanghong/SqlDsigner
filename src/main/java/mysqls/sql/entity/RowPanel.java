/**
 *
 */
package mysqls.sql.entity;

import mysqls.sql.util.MUiUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyDescriptor;

/**
 * @author jiang 编辑一列
 */
@SuppressWarnings("serial")
public class RowPanel extends JPanel {
    private TableColumn mTableColumn;

    /**
     * @return the mTableColumn
     */
    public TableColumn getmTableColumn() {
        return this.mTableColumn;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (obj instanceof RowPanel) {
            RowPanel rowPanel = (RowPanel) obj;
            return rowPanel.getmTableColumn().equals(mTableColumn);
        } else {
            return false;
        }
    }

    /**
     * @param mTableColumn
     */
    public RowPanel(TableColumn mTableColumn, final EditTable root) {
        super();
        this.mTableColumn = mTableColumn;
        setLayout(new GridLayout(1, 0));
        JButton button = new JButton("删除");
        button.setBackground(Color.red);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                root.removerow(RowPanel.this);
            }
        });
        add(button);
        for (int i = 0; i < AttubuteList.pdList.size(); i++) {

            PropertyDescriptor propertyDescriptor = AttubuteList.pdList.get(i);

            Component component = MUiUtil.getEditorComponent(mTableColumn, propertyDescriptor);
            add(component);

        }
    }

    /**
     *
     */
    public RowPanel(EditTable root) {
        this(new TableColumn("null"), root);
        // TODO Auto-generated constructor stub
    }

}
