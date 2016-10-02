/**
 *
 */
package mysqls.sql.databaseserver2;

import java.util.List;

import mysqls.contanst.ConnectINFO;
import mysqls.sql.databaseserver.DataBaseTables2graph;
import mysqls.sql.entity.Table;

/**
 * @author jiang 一个节点代表一个数据库
 *
 */
public class MYtreeNodeDB extends MYtreeNode {

	/**
	 * @param dbname
	 */
	public MYtreeNodeDB(String dbname) {
		super();
		name = dbname;
	}

	public List<MYtreeNodeTable> geTables() {
		List<MYtreeNodeTable> list = DataBaseUtil.gettables(name);
		list.stream().forEach(aa -> aa.setDb(MYtreeNodeDB.this));
		return list;
	}

	public List<Table> geTablesdata() {
		return DataBaseTables2graph.getAlltables(name, ConnectINFO.getInstance().getConnection());

	}

}
