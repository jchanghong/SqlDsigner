/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.entity;

/**
 * @author 长宏 约束用于限制加入表的数据的类型。 可以在创建表时规定约束（通过 CREATE TABLE 语句），或者在表创建之后也可以（通过
 *         ALTER TABLE 语句）。 我们将主要探讨以下几种约束： NOT NULL UNIQUE PRIMARY KEY FOREIGN
 *         KEY CHECK DEFAULT
 */
public class TableColumn {
	private String name;
	private boolean primarykey;
	private boolean foreignKey;
	private boolean notnull;
	private boolean unique;
	private String defaultvalues;
	private Table forigntable;
	private TableColumn forigncolumn;

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
		this.name = name;
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
		this.primarykey = primarykey;
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
		this.foreignKey = foreignKey;
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
		this.notnull = notnull;
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
		this.unique = unique;
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
		this.defaultvalues = defaultvalues;
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
