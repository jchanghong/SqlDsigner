/**
 *
 */
package mysqls.sql.databaseserver2;

/**
 * @author jiang
 *
 */
public abstract class MYtreeNode {

	protected String name;

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

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}

}
