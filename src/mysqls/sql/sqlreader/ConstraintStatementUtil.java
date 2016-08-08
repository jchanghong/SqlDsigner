/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.sqlreader;

/**
 * @author 长宏
 *
 *
 */
public class ConstraintStatementUtil {

	// CONSTRAINT FOREIGN KEY (`CountryCode`) REFERENCES `Country` (`Code`)
	/**
	 * @param statement
	 * @return名字
	 */
	public static String getcolumn(String statement) {
		int s = statement.indexOf('(');
		int e = statement.indexOf(')');

		return ConstraintStatementUtil.mytrim(statement.substring(s + 1, e));
	}

	/**
	 * @param statement
	 * @return名字
	 */
	public static String getfcolumn(String statement) {

		int s = statement.lastIndexOf('(');
		int e = statement.lastIndexOf(')');

		return ConstraintStatementUtil.mytrim(statement.substring(s + 1, e));
	}

	/**
	 * @param statement
	 * @return名字
	 */
	public static String getftable(String statement) {

		String temp = statement.trim();
		String temp2 = "REFERENCES";
		int s = temp.toUpperCase().lastIndexOf(temp2);
		int e = temp.lastIndexOf('(');
		return ConstraintStatementUtil.mytrim(temp.substring(s + temp2.length(), e));
	}

	/**
	 * @param name
	 * @return如果有`符号就去掉，没有就不处理
	 */
	public static String mytrim(String name) {
		StringBuilder builder = new StringBuilder(name.trim());
		if (builder.charAt(0) == '`') {

			builder.setCharAt(0, ' ');
		}
		if (builder.charAt(builder.length() - 1) == '`') {
			builder.setCharAt(builder.length() - 1, ' ');
		}
		return builder.toString().trim();

	}
}
