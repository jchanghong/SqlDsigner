/**
 * 实体关系图和sql生产的实现
 */
package mysqls.sql.databaseserver2;

import mysqls.contanst.ConnectINFO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 长宏
 *
 */
public abstract class DataBaseUtil {

    /**
     * @return 只有mysql可以这样
     */
    public static List<MYtreeNodeDB> getdbs() {
        List<MYtreeNodeDB> dbs = new ArrayList<>();
        try {
            Statement stat = ConnectINFO.getInstance().getConnection().createStatement();

            // 显示所有的数据库
            ResultSet resultSet = stat.executeQuery("SHOW DATABASES");
            while (resultSet.next()) {
                dbs.add(new MYtreeNodeDB(resultSet.getString(1)));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dbs;

    }

    public static List<MYtreeNodeTable> gettables(String dbname) {
        List<MYtreeNodeTable> tables = new ArrayList<>();
        try {
            DatabaseMetaData metaData = ConnectINFO.getInstance().getConnection().getMetaData();
            ResultSet resultSet = metaData.getTables(dbname, null, null, new String[]{"TABLE"});
            while (resultSet.next()) {
                tables.add(new MYtreeNodeTable(resultSet.getString("TABLE_NAME")));

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tables;

    }

    public static List<MYtreeNodeColumn> getcolumn(String dbname, String tablename) {
        List<MYtreeNodeColumn> columns = new ArrayList<>();
        String sql = "select * from " + tablename + ";";
        try {

            Statement statement = ConnectINFO.getInstance().getConnection().createStatement();
            statement.execute("use " + dbname + ";");
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData data = resultSet.getMetaData();
            for (int i = 1; i <= data.getColumnCount(); i++) {
                // 获得指定列的列名
                String columnName = data.getColumnName(i);
                columns.add(new MYtreeNodeColumn(columnName));
            }

        } catch (

                Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return columns;

    }
    // // 测试用
    // public static void main(String[] args) {
    //
    // String url = "jdbc:mysql://localhost:3306/changhong";
    // try {
    // Connection connection = DriverManager.getConnection(url, "root", "0000");
    //
    // DataBaseUtil.getdbs(connection);
    // } catch (SQLException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    //
    // // List<Table> list = DataBaseTables2graph.getAlltables("", null);
    // }

}
