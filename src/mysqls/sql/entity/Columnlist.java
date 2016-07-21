/**
 *
 */
package mysqls.sql.entity;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 * @author jiang 监听list的变法
 *
 */
public final class Columnlist {
	private Changelistener changelistner;

	/**
	 * @return the changelistner
	 */
	public Changelistener getChangelistner() {
		return this.changelistner;
	}

	/**
	 * @param changelistner
	 *            the changelistner to set
	 */
	public void setChangelistner(Changelistener changelistner) {
		this.changelistner = changelistner;
	}

	public static interface Changelistener {
		public void onchang();

	}

	private void updatelister() {

		for (TableColumn tableColumn : list) {
			tableColumn.removePropertyChangeListener(listener);
		}
		for (TableColumn tableColumn : list) {
			tableColumn.addPropertyChangeListener(listener);
		}

	}

	/**
	 *
	 */
	public Columnlist(List<TableColumn> columns, PropertyChangeSupport support) {
		this.list = columns;
		this.pChangeSupport = support;
		listener = new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				changelistner.onchang();

			}
		};

		updatelister();
		// TODO Auto-generated constructor stub
	}

	PropertyChangeListener listener;
	private List<TableColumn> list;
	PropertyChangeSupport pChangeSupport;

	public TableColumn get(int index) {
		return list.get(index);
	}

	public int size() {
		return list.size();
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

}
