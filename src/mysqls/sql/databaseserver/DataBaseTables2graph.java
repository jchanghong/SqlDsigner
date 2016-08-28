/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.databaseserver;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mysqls.contanst.TabeleConstant;
import mysqls.sql.entity.DataTypeUI;
import mysqls.sql.entity.Table;
import mysqls.sql.entity.TableColumn;
import mysqls.sql.util.SQLCreator;

/**
 * @author 长宏 数据库中所有的表用图形表示。
 *
 *
 */
abstract public class DataBaseTables2graph {

	// 测试用
	public static void main(String[] args) {

		String url = "jdbc:mysql://localhost:3306/changhong";
		try {
			Connection connection = DriverManager.getConnection(url, "root", "0000");

			DataBaseTables2graph.getAlltables("changhong", connection).stream().forEach(aa -> {
				System.out.println(SQLCreator.create(aa));
			});
			;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// List<Table> list = DataBaseTables2graph.getAlltables("", null);
	}

	public static List<Table> getAlltables(String database, Connection connection) {

		List<Table> tables = new ArrayList<>();
		Table table = null;
		DatabaseMetaData databaseMetaData = null;
		try {
			databaseMetaData = connection.getMetaData();
			ResultSet alltable = databaseMetaData.getTables(database, null, null, new String[] { "TABLE" });
			while (alltable.next()) {

				String tablename = alltable.getString("table_name");
				table = new Table(tablename);
				DataBaseTables2graph.settablecolumn(databaseMetaData, database, table, connection);// 设置表的列
				tables.add(table);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataBaseTables2graph.settables(databaseMetaData, database, tables, connection);// 增加关系

		TabeleConstant.alltables = tables;
		return tables;

	}

	/**
	 * @param databaseMetaData
	 * @param database
	 * @param tables增加外键
	 * @param connection
	 */
	private static void settables(DatabaseMetaData databaseMetaData, String database, List<Table> tables,
			Connection connection) {
		// TODO Auto-generated method stub
		try {

			tables.stream().forEach(aa -> {
				try {
					ResultSet fkSet = databaseMetaData.getExportedKeys(database, null, aa.getName());
					while (fkSet.next()) {
						String pktable = fkSet.getString("PKTABLE_NAME");
						String fktable = fkSet.getString("FKTABLE_NAME");
						String pkcolumn = fkSet.getString("PKCOLUMN_NAME");
						String fkcolumn = fkSet.getString("FKCOLUMN_NAME");
						Table pTable = DataBaseTables2graph.findtable(pktable, tables);
						Table fTable = DataBaseTables2graph.findtable(fktable, tables);
						TableColumn pColumn = DataBaseTables2graph.findcolumn(pkcolumn, pTable);
						TableColumn fColumn = DataBaseTables2graph.findcolumn(fkcolumn, fTable);
						fColumn.setForeignKey(true);
						fColumn.setForigncolumn(pColumn);
						fColumn.setForigntable(pTable);

					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @param table
	 * @param databaseMetaData
	 */
	private static void settablecolumn(DatabaseMetaData databaseMetaData, String databse, Table table,
			Connection connection) {
		// TODO Auto-generated method stub
		String tablename = table.getName();
		String sql = "select * from " + tablename + ";";
		TableColumn column = null;
		try {
			List<String> pkcolumns = new ArrayList<>();
			ResultSet primarycolums = databaseMetaData.getPrimaryKeys(databse, null, tablename);
			String prymary = null;
			while (primarycolums.next()) {

				prymary = primarycolums.getString("COLUMN_NAME");
				pkcolumns.add(prymary);
				System.out.println("prymary;" + prymary);
			}

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			ResultSetMetaData data = resultSet.getMetaData();

			for (int i = 1; i <= data.getColumnCount(); i++) {
				column = new TableColumn();
				// 获得指定列的列名
				String columnName = data.getColumnName(i);
				column.setName(columnName);
				if (pkcolumns.contains(columnName)) {
					column.setPrimarykey(true);
				}
				// 获得指定列的数据类型名
				String columnTypeName = data.getColumnTypeName(i);
				// 在数据库中类型的最大字符个数
				int columnDisplaySize = data.getColumnDisplaySize(i);
				column.setType(DataTypeUI.toenum(columnTypeName));
				// 某列类型的精确度(类型的长度)
				int precision = data.getPrecision(i);
				// 小数点后的位数
				int scale = data.getScale(i);
				// 是否自动递增
				boolean isAutoInctement = data.isAutoIncrement(i);
				column.setAutoadd(isAutoInctement);
				// 在数据库中是否为货币型
				// boolean isCurrency = data.isCurrency(i);
				// 是否为空
				int isNullable = data.isNullable(i);
				column.setNotnull(isAutoInctement);
				// 是否为只读
				// boolean isReadOnly = data.isReadOnly(i);
				// 能否出现在where中
				// boolean isSearchable = data.isSearchable(i);
				table.addColumn(column);
			}

		} catch (

		Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static Table findtable(String tablename, List<Table> tables) {
		for (Table table : tables) {
			if (table.getName().equals(tablename)) {
				return table;
			}
		}
		return null;
	}

	private static TableColumn findcolumn(String columnname, Table table) {
		for (TableColumn coulmn : table.getColumnlist().getList()) {
			if (coulmn.getName().equals(columnname)) {
				return coulmn;
			}
		}
		return null;
	}

}
