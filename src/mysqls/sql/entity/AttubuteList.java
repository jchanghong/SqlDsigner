/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 长宏
 *
 */
public final class AttubuteList {
	/**
	 *
	 */
	private AttubuteList() {
		// TODO Auto-generated constructor stub
	}
	// private String name;
	// private String type;
	// private boolean primarykey;
	// private boolean foreignKey;
	// private boolean notnull;
	// private boolean unique;
	// private String defaultvalues;
	// private Table forigntable;
	// private TableColumn forigncolumn;

	public final static List<String> list = new ArrayList<>();
	static {

		AttubuteList.list.add("name");
		AttubuteList.list.add("type");
		AttubuteList.list.add("primarykey");
		AttubuteList.list.add("foreignKey");
		AttubuteList.list.add("notnull");
		AttubuteList.list.add("unique");
		AttubuteList.list.add("defaultvalues");
	}
}
