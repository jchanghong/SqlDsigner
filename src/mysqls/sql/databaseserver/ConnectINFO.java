/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.databaseserver;

import java.sql.Connection;

/**
 * @author 长宏 链接信息，全局有效。所有链接相关的操作都只操作这个对象
 *
 */
public class ConnectINFO {
	public static Connection connection = null;

}
