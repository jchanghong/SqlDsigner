/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.entity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;

/**
 * @author 长宏 代表列 主键一定unique，notnull。外键一定有index，但是不一定unique。
 */
@SuppressWarnings("serial")
public class TableColumn implements Serializable, Cloneable {

	private String name;
	private DataType type;
	private boolean primarykey;
	private boolean foreignKey;
	private boolean notnull;
	private boolean unique;
	private boolean autoadd;
	private String defaultvalues;
	private Table forigntable;
	private TableColumn forigncolumn;
	private PropertyChangeSupport ChangeSupport = new PropertyChangeSupport(this);
	private VetoableChangeSupport vetoSupport = new VetoableChangeSupport(this);

	private void copy(TableColumn sColumn, TableColumn dColumn) {
		dColumn.name = new String(sColumn.name);
		dColumn.defaultvalues = new String(sColumn.defaultvalues);
		dColumn.foreignKey = new Boolean(sColumn.foreignKey);
		dColumn.notnull = new Boolean(sColumn.notnull);
		dColumn.autoadd = new Boolean(sColumn.autoadd);
		dColumn.primarykey = new Boolean(sColumn.primarykey);
		dColumn.type = DataTypeUI.toenum(type.toString());
		dColumn.unique = new Boolean(sColumn.unique);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override
	public TableColumn clone() {
		// TODO Auto-generated method stub
		TableColumn column = null;
		column = new TableColumn();
		copy(this, column);
		return column;
	}

	/**
	 *
	 */
	public TableColumn() {
		// TODO Auto-generated constructor stub
		this("null");
	}

	/**
	 * @return the autoadd
	 */
	public boolean isAutoadd() {
		return this.autoadd;
	}

	/**
	 * @param autoadd
	 *            the autoadd to set
	 */
	public void setAutoadd(boolean autoadd) {
		boolean old = this.autoadd;
		this.autoadd = autoadd;
		ChangeSupport.firePropertyChange("autoadd", old, autoadd);
	}

	/**
	 *
	 */
	public TableColumn(String name) {
		// TODO Auto-generated constructor stub
		this(name, false, true, false);
	}

	/**
	 *
	 */
	public TableColumn(String name, boolean prikey, boolean notnull, boolean unique) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.primarykey = prikey;
		autoadd = false;
		foreignKey = false;
		this.notnull = notnull;
		this.unique = unique;
		defaultvalues = "";
		forigntable = null;
		forigncolumn = null;
		type = DataType.VARCHAR;
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
		super.equals(obj);
		if (obj instanceof TableColumn) {
			TableColumn column = (TableColumn) obj;
			if (column.name.equals(this.name)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * @return the defaultvalues
	 */
	public String getDefaultvalues() {
		return this.defaultvalues;
	}

	/**
	 * @return the forigncolumn
	 */
	public TableColumn getForigncolumn() {
		return this.forigncolumn;
	}

	/**
	 * @return the forigntable
	 */
	public Table getForigntable() {
		return this.forigntable;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the foreignKey
	 */
	public boolean isForeignKey() {
		return this.foreignKey;
	}

	/**
	 * @return the notnull
	 */
	public boolean isNotnull() {
		return this.notnull;
	}

	/**
	 * @return the primarykey
	 */
	public boolean isPrimarykey() {
		return this.primarykey;
	}

	/**
	 * @return the unique
	 */
	public boolean isUnique() {
		return this.unique;
	}

	public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		ChangeSupport.removePropertyChangeListener(propertyChangeListener);
	}

	public void removeVetoableChangeListener(VetoableChangeListener listener) {
		vetoSupport.removeVetoableChangeListener(listener);
	}

	/**
	 * @param defaultvalues
	 *            the defaultvalues to set
	 */
	public void setDefaultvalues(String defaultvalues) {
		String old = this.defaultvalues;
		this.defaultvalues = defaultvalues;
		ChangeSupport.firePropertyChange("defaultvalues", old, defaultvalues);
	}

	/**
	 * @param foreignKey
	 *            the foreignKey to set
	 */
	public void setForeignKey(boolean foreignKey) {
		boolean old = this.foreignKey;
		this.foreignKey = foreignKey;
		ChangeSupport.firePropertyChange("foreignKey", old, foreignKey);
		if (foreignKey) {
			forigncolumn = new TableColumn("null");
			forigntable = new Table("null");
		} else {
			forigncolumn = null;
			forigntable = null;
		}
	}

	/**
	 * @param forigncolumn
	 *            the forigncolumn to set
	 */
	public void setForigncolumn(TableColumn forigncolumn) {
		this.forigncolumn = forigncolumn;
	}

	/**
	 * @param forigntable
	 *            the forigntable to set
	 */
	public void setForigntable(Table forigntable) {
		this.forigntable = forigntable;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		String old = this.name;
		this.name = name;
		// System.out.println("setname");
		ChangeSupport.firePropertyChange("name", old, name);
	}

	/**
	 * @param notnull
	 *            the notnull to set
	 */
	public void setNotnull(boolean notnull) {
		boolean old = this.notnull;
		this.notnull = notnull;
		ChangeSupport.firePropertyChange("notnull", old, notnull);
	}

	/**
	 * @param primarykey
	 *            the primarykey to set
	 */
	public void setPrimarykey(boolean primarykey) {
		boolean old = this.primarykey;
		this.primarykey = primarykey;
		ChangeSupport.firePropertyChange("primarykey", old, primarykey);
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(DataType type) {
		DataType old = this.type;
		this.type = type;
		ChangeSupport.firePropertyChange("type", old, type);
	}

	/**
	 * @return the type
	 */
	public DataType getType() {
		return this.type;
	}

	/**
	 * @param unique
	 *            the unique to set
	 */
	public void setUnique(boolean unique) {
		boolean old = this.unique;
		this.unique = unique;
		ChangeSupport.firePropertyChange("unique", old, unique);
	}

	public String toSQL() {

		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append("  ");
		builder.append(DataTypeUI.tostring(type));
		if (notnull || primarykey || foreignKey || unique) {
			builder.append("  " + "NOT NULL");
		}
		if (primarykey) {
			builder.append("  " + "PRIMARY KEY");

		} else {

			if (unique) {
				builder.append("  " + "UNIQUE");

			}

		}
		if (type.equals(DataType.INT) && autoadd) {
			builder.append("  " + "AUTO_INCREMENT");
		}

		if (defaultvalues.trim().length() > 0 && (type.equals(DataType.INT) || type.equals(DataType.DOUBLE))) {
			builder.append("  " + "DEFAULT " + defaultvalues);
		}

		if (type.equals(DataType.TEXT) || type.equals(DataType.VARCHAR)) {

			if (defaultvalues.trim().length() > 0) {
				builder.append("  " + "DEFAULT " + defaultvalues + "");
			}

		}
		// if (foreignKey) {
		// builder.append(" ");
		// builder.append("FOREIGN KEY");
		// builder.append(" ");
		// builder.append("REFERENCES");
		// builder.append(" ");
		// builder.append(forigntable.getName());
		// builder.append("(");
		// builder.append(forigncolumn.getName());
		// builder.append(")");
		//
		// }
		builder.append(",\n");
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
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		if (primarykey || unique || foreignKey || notnull) {
			builder.append("()");
		} else {
			builder.append("\n");
			return builder.toString();
		}
		StringBuilder builder2 = new StringBuilder();
		int index = 0;
		if (primarykey) {
			builder2.append("PK");
		}
		if (unique) {
			if (builder2.length() > 0) {
				builder2.append("," + "UK");
			} else {
				builder2.append("UK");
			}
		}
		if (foreignKey) {
			if (builder2.length() > 0) {
				builder2.append("," + "FK");
			} else {
				builder2.append("FK");
			}
		}
		if (notnull) {
			if (builder2.length() > 0) {
				builder2.append("," + "NN");
			} else {
				builder2.append("NN");
			}
		}
		index = builder.indexOf("(");
		builder.insert(index + 1, builder2);
		builder.append("\n");

		return builder.toString();
	}

}
