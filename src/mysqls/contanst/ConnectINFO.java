/**
 *  实体关系图和sql生产的实现
 */
package mysqls.contanst;

import java.sql.Connection;

/**
 * @author 长宏 常量
 *
 */
public class ConnectINFO {

	public static final String historyFilename = "changhong.exe";
	public static Connection connection;
	public static String url;
	public static String user;
	public static String passworld;
	public static String databaseType;// 数据库类型
	public static String databaseName = "";
	public static String tableName = "";

}
