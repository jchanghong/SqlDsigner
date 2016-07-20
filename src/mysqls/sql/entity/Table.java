/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 长宏
 *
 */
public class Table {

	private String name;
	private List<TableColumn> Columnlist;

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

	public void addColumn(TableColumn column) {
		Columnlist.add(column);
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
	 * @return the columnlist
	 */
	public List<TableColumn> getColumnlist() {
		return this.Columnlist;
	}

	/**
	 * @param columnlist
	 *            the columnlist to set
	 */
	public void setColumnlist(List<TableColumn> columnlist) {
		this.Columnlist = columnlist;
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

}
