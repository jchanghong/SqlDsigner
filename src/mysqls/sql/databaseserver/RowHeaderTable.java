package mysqls.sql.databaseserver;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * 用于显示RowHeader的JTable，只需要将其加入JScrollPane的RowHeaderView即可为JTable生成行标题
 */
public class RowHeaderTable extends JTable {
    private JTable refTable;//需要添加rowHeader的JTable

    /**
     * 为JTable添加RowHeader，
     *
     * @param refTable     需要添加rowHeader的JTable
     * @param columnWideth rowHeader的宽度
     */
    public RowHeaderTable(JTable refTable, int columnWidth) {
        super(new RowHeaderTableModel(refTable.getRowCount()));
        this.refTable = refTable;
        this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//不可以调整列宽
        this.getColumnModel().getColumn(0).setPreferredWidth(columnWidth);
        this.setDefaultRenderer(Object.class, new RowHeaderRenderer(refTable, this));//设置渲染器
        this.setPreferredScrollableViewportSize(new Dimension(columnWidth, 0));
    }
}

/**
 * 用于显示RowHeader的JTable的渲染器，可以实现动态增加，删除行，在Table中增加、删除行时RowHeader
 * 一起变化。当选择某行时，该行颜色会发生变化
 */
class RowHeaderRenderer extends JLabel implements TableCellRenderer, ListSelectionListener {
    JTable reftable;//需要添加rowHeader的JTable
    JTable tableShow;//用于显示rowHeader的JTable

    public RowHeaderRenderer(JTable reftable, JTable tableShow) {
        this.reftable = reftable;
        this.tableShow = tableShow;
        //增加监听器，实现当在reftable中选择行时，RowHeader会发生颜色变化
        ListSelectionModel listModel = reftable.getSelectionModel();
        listModel.addListSelectionListener(this);
    }

    public Component getTableCellRendererComponent(JTable table, Object obj,
                                                   boolean isSelected, boolean hasFocus, int row, int col) {
        ((RowHeaderTableModel) table.getModel()).setRowCount(reftable.getRowCount());
        JTableHeader header = reftable.getTableHeader();
        this.setOpaque(true);
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));//设置为TableHeader的边框类型
        setHorizontalAlignment(CENTER);//让text居中显示
        setBackground(header.getBackground());//设置背景色为TableHeader的背景色
        if (isSelect(row))    //当选取单元格时,在row header上设置成选取颜色
        {
            setForeground(Color.blue);
            setBackground(Color.red);
        } else {
            setForeground(header.getForeground());
        }
        setFont(header.getFont());
        setText(String.valueOf(row + 1));
        return this;
    }

    public void valueChanged(ListSelectionEvent e) {
        this.tableShow.repaint();
    }

    private boolean isSelect(int row) {
        int[] sel = reftable.getSelectedRows();
        for (int i = 0; i < sel.length; i++)
            if (sel[i] == row)
                return true;
        return false;
    }
}

/**
 * 用于显示表头RowHeader的JTable的TableModel，不实际存储数据
 */
class RowHeaderTableModel extends AbstractTableModel {
    private int rowCount;//当前JTable的行数，与需要加RowHeader的TableModel同步

    public RowHeaderTableModel(int rowCount) {
        this.rowCount = rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return 1;
    }

    public Object getValueAt(int row, int column) {
        return row;
    }
}

