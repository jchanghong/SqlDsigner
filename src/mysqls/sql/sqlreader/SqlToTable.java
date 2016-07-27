package mysqls.sql.sqlreader;

import java.util.ArrayList;
import java.util.List;

import mysqls.framework.GraphFrame;
import mysqls.graph.AssociationEdge;
import mysqls.graph.ClassNode;
import mysqls.sql.entity.Columnlist;
import mysqls.sql.entity.Table;
import mysqls.sql.entity.TableColumn;

/**
 * @author Administrator string sql to table class object util
 *
 */
public class SqlToTable {

	static GraphFrame mFrame;
	static String teString = "" + "CREATE TABLE gfh4fsaa4\n" + "(\n" + "444null varchar(40)\n" + ")\n"
			+ "CREATE  TABLE  null555\n" + "(\n" + "null666  varchar(40)  NOT NULL  UNIQUE\n" + ")\n";

	public static Table from(String sqlline) {
		return null;

	}

	public static void main(String[] args) {
		// System.out.println("start test");
		List<Table> qq = SqlToTable.gettable(SqlToTable.teString);
		System.out.println(qq.get(0).getName());
	}

	private static Table SqlReader(List<String> sqllist, int start, int end) {
		Table table = new Table();
		String string = sqllist.get(start);

		int index = string.indexOf("TABLE");
		table.setName(string.substring(index + 6, string.length()));// 表名

		Columnlist columnlist = table.getColumnlist();

		for (int i = start + 2; i < end + 1; i++) {
			TableColumn column = new TableColumn();
			String liString = sqllist.get(i);
			String[] row = liString.split(" ");// 去除空格后
			String columnname = row[0];
			column.setName(columnname);// 列名
			if (row.length < 2) {
				continue;
			}
			String type = row[1];
			column.setType(type);// 列的类型
			for (String string2 : row) {
				if (string2.startsWith("PRIMARYKEY")) {// 判断第三个字符是不是Primarykey
					column.setPrimarykey(true);
					column.setNotnull(true);
					column.setUnique(true);
				}
				if (string2.startsWith("NULL")) {// 判断是不是notnull
					column.setNotnull(true);
				}
				if (string2.startsWith("UNIQUE")) {// 接着判断是不是unique
					column.setUnique(true);
				}

				if (string2.startsWith("REFERENCES")) {
					column.setForeignKey(true);

				}
			}
			columnlist.add(column);
		}

		return table;
	}

	public static List<AssociationEdge> getEdge(List<Table> tables) {
		List<AssociationEdge> list = new ArrayList<>();

		AssociationEdge associationEdge = new AssociationEdge();
		associationEdge.connect(null, null);
		ClassNode classNode = new ClassNode(new Table());

		return list;
	}

	public static List<Table> getAllTable(String sql) {
		List<Table> list = SqlToTable.gettable(sql);
		for (Table table : list) {
			for (TableColumn column : table.getColumnlist().getList()) {

				if (column.isForeignKey() == true) {
					Table re = null;
					TableColumn column2 = null;

					column.setForigntable(table);
					column.setForigncolumn(column2);
				}
			}

		}

		return list;
	}

	private static List<Table> gettable(String sqlstring) {
		List<Table> listabl = new ArrayList<>();
		List<String> list = new ArrayList<String>();
		String[] lid = sqlstring.split("\n");
		for (String string : lid) {
			list.add(string);
		}
		int star = 0;
		int end = 0;
		int postion = 0;
		for (int i = 0; i < list.size(); i++) {
			String string = list.get(i);
			if (string.equals("(")) {
				star = i - 1;

			}
			if (string.equals(")")) {

				end = i;
				listabl.add(SqlToTable.SqlReader(list, star, end - 1));
			}

		}

		return listabl;
	}

}
