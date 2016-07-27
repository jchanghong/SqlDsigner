/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.entity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.util.ArrayList;
import java.util.List;

import mysqls.framework.MultiLineString;
import mysqls.graph.ClassNode;
import mysqls.sql.entity.Columnlist.Changelistener;

/**
 * @author 长宏
 *
 */
public class Table {

	private String name;
	private Columnlist columnlist;
	// private List<TableColumn> list;
	private PropertyChangeSupport ChangeSupport = new PropertyChangeSupport(this);
	private VetoableChangeSupport vetoSupport = new VetoableChangeSupport(this);

	/**
	 *
	 */
	public Table() {
		// TODO Auto-generated constructor stub
		this("null");
	}

	/**
	 *
	 */
	public Table(String aname) {
		// TODO Auto-generated constructor stub
		name = aname;
		List<TableColumn> list = new ArrayList<>();
		columnlist = new Columnlist(list, this);
		columnlist.setChangelistner(new Changelistener() {

			@Override
			public void onchang(TableColumn column, String news) {
				// TODO Auto-generated method stub
				if (column != null || news != null) {
					ChangeSupport.firePropertyChange("columnlist", column, news);
					return;
				}
				ChangeSupport.firePropertyChange("columnlist", null, columnlist);

			}
		});
	}

	public Table(String name, List<TableColumn> list) {
		this.name = name;
		columnlist = new Columnlist(list, this);
		columnlist.setChangelistner(new Changelistener() {

			@Override
			public void onchang(TableColumn column, String news) {
				// TODO Auto-generated method stub
				if (column != null || news != null) {
					ChangeSupport.firePropertyChange("columnlist", column, news);
					return;
				}
				ChangeSupport.firePropertyChange("columnlist", null, columnlist);

			}
		});
	}

	public void addColumn(TableColumn column) {
		columnlist.add(column);
	}

	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		ChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}

	public void addVetoableChangeListener(VetoableChangeListener listener) {
		vetoSupport.addVetoableChangeListener(listener);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Table clone() {
		// TODO Auto-generated method stub
		Table m = new Table();
		m.name = new String(this.name);
		for (int i = 0; i < columnlist.size(); i++) {
			TableColumn column = columnlist.get(i);
			m.addColumn(column.clone());
		}
		// m.list = m.columnlist.getList();
		return m;
	}

	public ClassNode createNote() {
		ClassNode node = new ClassNode(this.clone());
		return node;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof Table) {

			Table temp = (Table) obj;
			if (temp.name.equals(this.name)) {
				return true;

			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * @return the columnlist
	 */
	public Columnlist getColumnlist() {
		return this.columnlist;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	public MultiLineString getnodeAttu() {
		MultiLineString multiLineString = new MultiLineString();

		StringBuilder builder = new StringBuilder("");
		for (TableColumn tableColumn : columnlist.getList()) {
			builder.append(tableColumn.toString());
		}
		multiLineString.setText(builder.toString());
		return multiLineString;
	}

	public MultiLineString getnodeName() {
		MultiLineString multiLineString = new MultiLineString();
		multiLineString.setText(this.toString());
		return multiLineString;
	}

	public void removecolumn(TableColumn column) {
		columnlist.remove(column);

	}

	public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		ChangeSupport.removePropertyChangeListener(propertyChangeListener);
	}

	public void removeVetoableChangeListener(VetoableChangeListener listener) {
		vetoSupport.removeVetoableChangeListener(listener);
	}

	/**
	 * @param columnlist
	 *            the columnlist to set
	 */
	public void setColumnlist(Columnlist columnlist) {
		Columnlist ol = this.columnlist;
		this.columnlist = columnlist;
		System.out.println("setColumnlist(Columnlist columnlist) ");
		ChangeSupport.firePropertyChange("columnlist", null, columnlist);

		System.out.println("setColumnlist(Columnlist columnlist) {");
		// this.list = columnlist.getList();
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		String old = this.name;
		this.name = name;
		ChangeSupport.firePropertyChange("name", old, name);
	}

	// CREATE TABLE Orders
	// (
	// Id_O int NOT NULL,
	// OrderNo int NOT NULL,
	// Id_P int,
	// PRIMARY KEY (Id_O),
	// CONSTRAINT fk_PerOrders FOREIGN KEY (Id_P)
	// REFERENCES Persons(Id_P)
	// )
	public String toSQL() {
		StringBuilder builder = new StringBuilder();
		builder.append("create  table  ");
		builder.append(name);
		builder.append("\n");
		builder.append("(");
		builder.append("\n");
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		super.toString();
		StringBuilder builder = new StringBuilder();
		builder.append(name + "\n");
		return builder.toString();

	}

	/**
	 * @param getmTableColumn
	 */
	public void remove(TableColumn getmTableColumn) {
		// TODO Auto-generated method stub
		columnlist.remove(getmTableColumn);

	}
}
