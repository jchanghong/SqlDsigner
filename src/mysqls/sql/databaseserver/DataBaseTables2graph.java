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

		return tables;

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
			ResultSet primarycolums = databaseMetaData.getPrimaryKeys(databse, null, tablename);
			primarycolums.next();
			String prymary = primarycolums.getString("COLUMN_NAME");
			System.out.println("prymary;" + prymary);

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			ResultSetMetaData data = resultSet.getMetaData();

			for (int i = 1; i <= data.getColumnCount(); i++) {
				column = new TableColumn();
				// 获得指定列的列名
				String columnName = data.getColumnName(i);
				column.setName(columnName);
				if (columnName.equals(prymary)) {
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

}
