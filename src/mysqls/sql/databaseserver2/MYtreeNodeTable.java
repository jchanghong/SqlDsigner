/**
 *
 */
package mysqls.sql.databaseserver2;

import java.util.List;

/**
 * @author jiang
 *
 */
public class MYtreeNodeTable extends MYtreeNode {
	private MYtreeNodeDB db;

	/**
	 * @param db
	 *            the db to set
	 */
	public void setDb(MYtreeNodeDB db) {
		this.db = db;
	}

	/**
	 *
	 */
	public MYtreeNodeTable(String names) {

		name = names;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the db
	 */
	public MYtreeNodeDB getDb() {
		return this.db;
	}

	public List<MYtreeNodeColumn> getcolumns() {
		List<MYtreeNodeColumn> list = DataBaseUtil.getcolumn(db.getName(), name);
		list.stream().forEach(aa -> aa.setTable(MYtreeNodeTable.this));
		return list;

	}

}
