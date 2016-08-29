/**
 *
 */
package mysqls.sql.databaseserver2;

/**
 * @author jiang
 *
 */
public class MYtreeNodeColumn extends MYtreeNode {

	private MYtreeNodeTable table;

	/**
	 *
	 */
	public MYtreeNodeColumn(String names) {
		name = names;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the table
	 */
	public MYtreeNodeTable getTable() {
		return this.table;
	}

	/**
	 * @param table
	 *            the table to set
	 */
	public void setTable(MYtreeNodeTable table) {
		this.table = table;
	}

}
