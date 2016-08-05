package mysqls.sql.sqlreader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mysqls.sql.entity.Table;
import mysqls.sql.entity.TableColumn;
import mysqls.sql.util.SQLCreator;

/**
 * @author 版本2,sql语句到table的实现,正则表达式实现
 *
 */
public final class SqlToTable2 {

	/**
	 * 工具类不需要实现
	 */
	private SqlToTable2() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		String string = "create table dd(" + " in int,ihhh int);";
		List<Table> tables = SqlToTable2.getAllTable(string);
		System.out.println(SQLCreator.create(tables.get(0)));

	}

	/**
	 * 外部接口。不能改变函数签名
	 *
	 * @param sql
	 * @return
	 */
	public static List<Table> getAllTable(String sql) {
		String[] tables = sql.split("\\s*;\\s*");
		List<Table> result = new ArrayList<>(tables.length);
		for (int i = 0; i < tables.length; i++) {
			String string = tables[i];
			result.add(SqlToTable2.gettable(string));

		}
		return result;
	}

	/**
	 * @param sqlstring
	 *            create开头。表）结尾。
	 * @return
	 */
	private static Table gettable(String sqlstring) {
		Table table = new Table();

		int nameindex = sqlstring.indexOf("(");
		String name = sqlstring.substring(0, nameindex);
		String[] name1 = name.split("\\s+");
		name = name1[name1.length - 1];
		table.setName(name);

		String colues = sqlstring.substring(nameindex + 1, sqlstring.length());
		String[] coluss = colues.split("\\s*,\\s*");// 最后一个列有）。

		for (int i = 0; i < coluss.length; i++) {
			String string = coluss[i];
			table.addColumn(SqlToTable2.getaColumn(string));

		}

		return table;
	}

	/**
	 * @param string
	 *            name int not null 这样的
	 * @return
	 */
	private static TableColumn getaColumn(String string) {
		// TODO Auto-generated method stub
		TableColumn column = new TableColumn();

		String[] names = string.trim().split("\\s+");
		column.setName(names[0]);
		int index = names[1].lastIndexOf(")");
		if (index == -1) {

			column.setType(names[1]);
		} else {
			column.setType(names[1].substring(0, index));

		}
		String pnotnull = "\\s*not\\s+null\\s*";
		String pprymary = "\\s*PRIMARY\\s+key\\s*";
		String punik = "\\s+unique\\s*";
		String pforien = "\\s+FOREIGN\\s+key\\s*";
		Pattern pattern = Pattern.compile(pnotnull, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(string);
		if (matcher.find()) {
			column.setNotnull(true);
		}

		pattern = Pattern.compile(pprymary, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(string);
		if (matcher.find()) {
			column.setPrimarykey(true);
		}

		pattern = Pattern.compile(punik, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(string);
		if (matcher.find()) {
			column.setUnique(true);
		}
		pattern = Pattern.compile(pforien, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(string);
		if (matcher.find()) {
			column.setForeignKey(true);
		}
		return column;
	}

}
