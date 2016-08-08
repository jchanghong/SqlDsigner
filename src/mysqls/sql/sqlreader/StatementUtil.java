/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.sqlreader;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author 长宏 ；前的语句，可以是drop语句，可以是create语句比如 ：creaa table(ddd)
 *
 */
public class StatementUtil {

	public static void main(String[] args) {

		String teString = "CONSTRAINT FOREIGN KEY (`CountryCode`) REFERENCES `Country` (`Code`)";
		System.out.println(ColumnStatementUtil.isConstraint(" conSTRAINT  `city_ibfk_1` FOREIGN KE"));
		System.out.println(ConstraintStatementUtil.getcolumn(teString));
		System.out.println(ConstraintStatementUtil.getfcolumn(teString));
		System.out.println(ConstraintStatementUtil.getftable(teString));
	}

	/**
	 * @param statement
	 *            dorp table ff fff
	 * @return
	 */
	public static boolean isDrop(String statement) {
		String patte = "\\s*drop\\s+table\\s+";
		Pattern pattern = Pattern.compile(patte, Pattern.CASE_INSENSITIVE);
		if (pattern.matcher(statement).find()) {
			return true;
		}
		return false;
	}

	public static boolean isCreate(String statement) {
		String patte = "\\s*create\\s+table\\s+";
		Pattern pattern = Pattern.compile(patte, Pattern.CASE_INSENSITIVE);
		if (pattern.matcher(statement).find()) {
			return true;
		}
		return false;
	}

	/**
	 * @param sql
	 *            ;为分隔符断开语句
	 * @return
	 */
	public static List<String> getAllStatement(String sql) {
		String[] tables = sql.split("\\s*;\\s*");
		List<String> creates = Arrays.stream(tables).filter(a -> a.trim().length() > 1).collect(Collectors.toList());
		return creates;
	}

	public static boolean hasConstraint(String statement) {
		int nameindex = statement.indexOf("(");
		String colues = statement.substring(nameindex + 1, statement.length());
		String[] coluss = colues.split("\\s*,\\s*");// 最后一个列有）。

		long l = Arrays.stream(coluss).filter(ColumnStatementUtil::isConstraint).count();
		return l > 0;
	}

	public static boolean isOKstatement(String sql) {
		if (sql.indexOf(";") == -1) {
			return false;
		}
		List<String> list = StatementUtil.getAllStatement(sql);
		List<String> list2 = list.stream().filter(a -> StatementUtil.isDrop(a) || StatementUtil.isCreate(a))
				.collect(Collectors.toList());
		return list.size() == list2.size();
	}

	/**
	 * @param creatstatement
	 *            必须是crate
	 * @return
	 */
	public static List<String> getAllColumn(String creatstatement) {
		StringBuilder builder = new StringBuilder(creatstatement.trim());
		List<String> list = null;
		int nameindex = builder.indexOf("(");
		String colues = builder.substring(nameindex + 1, builder.length() - 1);
		String[] coluss = colues.split("\\s*,\\s*");// 最后一个列有）。
		list = Arrays.stream(coluss).collect(Collectors.toList());
		return list;
	}

	/**
	 * @param stateent
	 *            create语句
	 * @return 得到一个constraint语句。
	 */
	public static String getAconstraint(String stateent) {

		Optional<String> stri = StatementUtil.getAllColumn(stateent).stream().filter(ColumnStatementUtil::isConstraint)
				.findFirst();
		return stri.get();

	}

}
