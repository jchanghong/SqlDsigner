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
 *
 */
public final class Columnlist implements Cloneable {
	/**
	 * @return the list
	 */
	public List<TableColumn> getList() {
		return this.list;
	}

	public static interface Changelistener {
		public void onchang();

	}

	private Changelistener changelistner;

	private PropertyChangeListener listener;

	private List<TableColumn> list;

	/**
	 *
	 */
	public Columnlist(List<TableColumn> columns) {
		this.list = columns;
		listener = new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				if (changelistner != null) {

					changelistner.onchang();
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
			changelistner.onchang();
		}
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

	public void remove(TableColumn column) {
		TableColumn tem = null;
		for (TableColumn column2 : list) {
			if (column2.equals(column)) {
				tem = column2;
				break;
			}
		}
		list.remove(tem);
		updatelister();
		if (changelistner != null) {
			changelistner.onchang();
		}

	}

	/**
	 * @param changelistner
	 *            the changelistner to set
	 */
	public void setChangelistner(Changelistener changelistner) {
		this.changelistner = changelistner;
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
		Columnlist columnlist = new Columnlist(tableColumns);
		return columnlist;
	}

}
