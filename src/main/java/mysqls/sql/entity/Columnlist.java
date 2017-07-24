/**
 *
 */
package mysqls.sql.entity;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiang 监听list的变法
 */
public final class Columnlist implements Cloneable {
    public interface Changelistener {
        /**
         * @param column,列表名的变法，其他不监听 不为空就为name
         */
        void onchang(TableColumn column, String newstring);

    }

    public boolean contain(TableColumn column) {
        for (TableColumn tableColumn : list) {
            if (column == tableColumn) {
                return true;
            }
        }
        return false;

    }

    /**
     * //必须有一个表，也就是不能为空
     */
    private Table mtTable;

    /**
     * @param list the list to set
     */
    public void setList(List<TableColumn> list) {
        this.list = list;
    }

    private Changelistener changelistner;

    private PropertyChangeListener listener;

    private List<TableColumn> list;

    /**
     *
     */
    public Columnlist(List<TableColumn> columns, Table table) {
        this.list = columns;
        this.mtTable = table;
        listener = new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                // TODO Auto-generated method stub
                if (changelistner != null) {

                    if (evt.getPropertyName().equals("name")) {
                        changelistner.onchang((TableColumn) evt.getSource(), evt.getNewValue().toString());
                        return;
                    }
                    changelistner.onchang(null, null);
                }

            }
        };

        updatelister();
        // TODO Auto-generated constructor stub
    }

    public void add(TableColumn column) {
        for (TableColumn column2 : list) {
            if (column2.equals(column)) {
                return;
            }
        }

        list.add(column);
        updatelister();
        if (changelistner != null) {
            changelistner.onchang(column, column.getName());
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
    @Override
    public Columnlist clone() {
        // TODO Auto-generated method stub
        List<TableColumn> tableColumns = new ArrayList<>();
        for (TableColumn tableColumn : list) {
            tableColumns.add(tableColumn.clone());
        }
        Columnlist columnlist = new Columnlist(tableColumns, null);

        return columnlist;
    }

    /**
     * @param index
     * @return null 如果不存在index位置的元素
     */
    public TableColumn get(int index) {
        if (index < 0 || index > list.size() - 1) {
            return null;
        }

        return list.get(index);
    }

    /**
     * @return the changelistner
     */
    public Changelistener getChangelistner() {
        return this.changelistner;
    }

    /**
     * @return the list
     */
    public List<TableColumn> getList() {
        return this.list;
    }

    /**
     * @return the mtTable
     */
    public Table getMtTable() {
        return this.mtTable;
    }

    public TableColumn get(String columnname) {

        for (TableColumn tableColumn : list) {
            if (tableColumn.getName().equals(columnname)) {
                return tableColumn;
            }

        }
        return null;

    }

    public void remove(TableColumn column) {
        TableColumn tem = null;
        for (TableColumn column2 : list) {
            if (column2.equals(column)) {
                tem = column2;
                break;
            }
        }
        if (tem == null) {
            return;
        }
        list.remove(tem);
        updatelister();
        if (changelistner != null) {
            changelistner.onchang(null, null);
        }

    }

    /**
     * @param changelistner the changelistner to set
     */
    public void setChangelistner(Changelistener changelistner) {
        this.changelistner = changelistner;
    }

    /**
     * @param mtTable the mtTable to set
     */
    public void setMtTable(Table mtTable) {
        this.mtTable = mtTable;
    }

    public int size() {
        return list.size();
    }

    private void updatelister() {

        for (TableColumn tableColumn : list) {
            tableColumn.removePropertyChangeListener(listener);
        }
        for (TableColumn tableColumn : list) {
            tableColumn.addPropertyChangeListener(listener);
        }

    }

}
