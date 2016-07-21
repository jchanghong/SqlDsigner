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

/**
 * @author 长宏
 *
 */
public class Table {

	private String name;
	private List<TableColumn> Columnlist;

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
		Columnlist = new ArrayList<>();
	}

	public Table(String name, List<TableColumn> list) {
		this.name = name;
		this.Columnlist = list;
	}

	public void addColumn(TableColumn column) {
		Columnlist.add(column);
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
	public List<TableColumn> getColumnlist() {
		return this.Columnlist;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
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
	public void setColumnlist(List<TableColumn> columnlist) {
		List<TableColumn> old = this.Columnlist;
		this.Columnlist = columnlist;
		ChangeSupport.firePropertyChange("Columnlist", old, columnlist);
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
		builder.append("CREATE  TABLE  ");
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
}
