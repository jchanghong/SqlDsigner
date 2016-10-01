/**
 *  实体关系图和sql生产的实现
 */
package mysqls.contanst;

import mysqls.sql.databaseserver2.MYtreeNodeDB;
import mysqls.sql.databaseserver2.MYtreeNodeTable;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 长宏 常量，所以数据库的链接信息，观察者模式
 *
 */
public class ConnectINFO {

	public static void addLister(ConnectINFOListener listener) {
		ConnectINFO.getInstance().addlister(listener);
	}
	private static ConnectINFO me=null;
	public static ConnectINFO getInstance() {
		if (me == null) {
			me = new ConnectINFO();
		}
		return me;
	}
	private ConnectINFO() {
	}

	public void addlister(ConnectINFOListener listener) {
		listenerSet.add(listener);
	}
	private Set<ConnectINFOListener> listenerSet = new HashSet<>();
	public static   final String historyFilename = "changhong.exe";
	private    Connection connection=null;
	private   String url=null;
	private   String user=null;
	private   String passworld=null;
	private   String databaseType=null;// 数据库类型
	private   String databaseName = null;
	private   String tableName = null;
	public static MYtreeNodeTable tablenode=null;
	public static MYtreeNodeDB db=null;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		Connection old=this.connection;
		this.connection = connection;
		for (ConnectINFOListener listener : listenerSet) {
			listener.onchange("connection", connection, old);
		}

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		String old = this.url;
		this.url = url;
		for (ConnectINFOListener listener : listenerSet) {
			listener.onchange("url", url, old);
		}
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		String old = this.user;
		this.user = user;
		for (ConnectINFOListener listener : listenerSet) {
			listener.onchange("user", user, old);
		}
	}

	public String getPassworld() {
		return passworld;
	}

	public void setPassworld(String passworld) {
		String old = this.passworld;
		this.passworld = passworld;
		for (ConnectINFOListener listener : listenerSet) {
			listener.onchange("passworld", passworld, old);
		}
	}

	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		String old = this.databaseType;
		this.databaseType = databaseType;
		for (ConnectINFOListener listener : listenerSet) {
			listener.onchange("databasetype", databaseType, old);
		}
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		String old = this.databaseName;
		this.databaseName = databaseName;
		for (ConnectINFOListener listener : listenerSet) {
			listener.onchange("databasename", databaseName, old);
		}
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		String old = this.tableName;
		this.tableName = tableName;
		for (ConnectINFOListener listener : listenerSet) {
			listener.onchange("tablename", tableName, old);
		}
	}
}
