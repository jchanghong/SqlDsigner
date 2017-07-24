/**
 * 实体关系图和sql生产的实现
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

    public static void removeLister(ConnectINFOListener listener) {
        ConnectINFO.getInstance().removelister(listener);
    }

    private static ConnectINFO me = null;

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

    public void removelister(ConnectINFOListener listener) {
        listenerSet.remove(listener);
    }

    private Set<ConnectINFOListener> listenerSet = new HashSet<>();
    public static final String historyFilename = "changhong.exe";
    private Connection connection = null;
    public static String CONNECTION = "connection";
    private String url = null;
    public static String URL = "url";
    private String user = null;
    public static String USER = "user";
    private String passworld = null;
    public static String PASSWORLD = "passworld";
    private String databaseType = "mysql";// 数据库类型
    public static String DATABASETYPE = "databasetype";
    private MYtreeNodeDB database = null;
    public static String DATABASE = "databasename";
    private MYtreeNodeTable table = null;
    public static String TABLE = "tablename";

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        if (this.connection == connection) {
            return;
        }
        Connection old = this.connection;
        this.connection = connection;
        for (ConnectINFOListener listener : listenerSet) {
            listener.onchange(CONNECTION, connection, old);
        }

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (this.url != null && this.url.equals(url)) {
            return;
        }
        String old = this.url;
        this.url = url;
        for (ConnectINFOListener listener : listenerSet) {
            listener.onchange(URL, url, old);
        }
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        if (this.user != null && this.user.equals(user)) {
            return;
        }

        String old = this.user;
        this.user = user;
        for (ConnectINFOListener listener : listenerSet) {
            listener.onchange(USER, user, old);
        }
    }

    public String getPassworld() {
        return passworld;
    }

    public void setPassworld(String passworld) {
        if (this.passworld != null && this.passworld.equals(passworld)) {
            return;
        }
        String old = this.passworld;
        this.passworld = passworld;
        for (ConnectINFOListener listener : listenerSet) {
            listener.onchange(PASSWORLD, passworld, old);
        }
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        if (this.databaseType != null && this.databaseType.equals(databaseType)) {
            return;
        }
        String old = this.databaseType;
        this.databaseType = databaseType;
        for (ConnectINFOListener listener : listenerSet) {
            listener.onchange(DATABASETYPE, databaseType, old);
        }
    }

    public MYtreeNodeDB getDatabase() {
        return database;
    }

    public void setDatabase(MYtreeNodeDB database) {
//        if (this.database== database) {
//            return;
//        }
        MYtreeNodeDB old = this.database;
        this.database = database;
        for (ConnectINFOListener listener : listenerSet) {
            listener.onchange(DATABASE, databaseType, old);
        }
    }

    public MYtreeNodeTable getTable() {
        return table;
    }

    public void setTable(MYtreeNodeTable table) {
        if (this.table == table) {
            return;
        }
        MYtreeNodeTable old = this.table;
        this.table = table;
        for (ConnectINFOListener listener : listenerSet) {
            listener.onchange(TABLE, databaseType, old);
        }
    }
}
