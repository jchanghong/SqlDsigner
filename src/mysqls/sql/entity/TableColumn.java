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
 * @author 长宏 约束用于限制加入表的数据的类型。 可以在创建表时规定约束（通过 CREATE TABLE 语句），或者在表创建之后也可以（通过
 *         ALTER TABLE 语句）。 我们将主要探讨以下几种约束： NOT NULL UNIQUE PRIMARY KEY FOREIGN
 *         KEY CHECK DEFAULT
 */
@SuppressWarnings("serial")
public class TableColumn implements Serializable {

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		String old = this.type;
		this.type = type;
		ChangeSupport.firePropertyChange("type", old, type);
	}

	private String name;
	private String type;
	private boolean primarykey;
	private boolean foreignKey;
	private boolean notnull;
	private boolean unique;
	private String defaultvalues;
	private Table forigntable;
	private TableColumn forigncolumn;
	private PropertyChangeSupport ChangeSupport = new PropertyChangeSupport(this);
	private VetoableChangeSupport vetoSupport = new VetoableChangeSupport(this);

	public void addVetoableChangeListener(VetoableChangeListener listener) {
		vetoSupport.addVetoableChangeListener(listener);
	}

	public void removeVetoableChangeListener(VetoableChangeListener listener) {
		vetoSupport.removeVetoableChangeListener(listener);
	}

	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		ChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}

	public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		ChangeSupport.removePropertyChangeListener(propertyChangeListener);
	}

	public String toSQL() {

		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append("  ");
		builder.append(type);
		if (primarykey) {
			builder.append("  " + "PRIMARY KEY");

		} else {
			if (notnull) {
				builder.append("  " + "NOT NULL");
			}
			if (unique) {
				builder.append("  " + "UNIQUE");

			}

		}

		if (foreignKey) {
			builder.append("  ");
			builder.append("FOREIGN KEY");
			builder.append("  ");
			builder.append("REFERENCES");
			builder.append("  ");
			builder.append(forigntable.getName());
			builder.append("(");
			builder.append(forigncolumn.getName());
			builder.append(")");

		}
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

		return builder.toString();
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
	 *
	 */
	public TableColumn(String name) {
		// TODO Auto-generated constructor stub
		this(name, false, false, false);
	}

	/**
	 *
	 */
	public TableColumn(String name, boolean prikey, boolean notnull, boolean unique) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.primarykey = prikey;
		foreignKey = false;
		this.notnull = notnull;
		this.unique = unique;
		defaultvalues = "";
		forigntable = null;
		forigncolumn = null;
		type = "varchar(40)";
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
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

	/**
	 * @return the primarykey
	 */
	public boolean isPrimarykey() {
		return this.primarykey;
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
	 * @return the foreignKey
	 */
	public boolean isForeignKey() {
		return this.foreignKey;
	}

	/**
	 * @param foreignKey
	 *            the foreignKey to set
	 */
	public void setForeignKey(boolean foreignKey) {
		boolean old = this.foreignKey;
		this.foreignKey = foreignKey;
		ChangeSupport.firePropertyChange("foreignKey", old, foreignKey);
	}

	/**
	 * @return the notnull
	 */
	public boolean isNotnull() {
		return this.notnull;
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
	 * @return the unique
	 */
	public boolean isUnique() {
		return this.unique;
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

	/**
	 * @return the defaultvalues
	 */
	public String getDefaultvalues() {
		return this.defaultvalues;
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
	 * @return the forigntable
	 */
	public Table getForigntable() {
		return this.forigntable;
	}

	/**
	 * @param forigntable
	 *            the forigntable to set
	 */
	public void setForigntable(Table forigntable) {
		this.forigntable = forigntable;
	}

	/**
	 * @return the forigncolumn
	 */
	public TableColumn getForigncolumn() {
		return this.forigncolumn;
	}

	/**
	 * @param forigncolumn
	 *            the forigncolumn to set
	 */
	public void setForigncolumn(TableColumn forigncolumn) {
		this.forigncolumn = forigncolumn;
	}

}
