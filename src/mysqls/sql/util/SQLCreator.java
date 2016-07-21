/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.util;

import mysqls.sql.entity.Table;
import mysqls.sql.entity.TableColumn;

/**
 * @author 长宏
 *
 */
public class SQLCreator {

	/**
	 *
	 */
	private SQLCreator() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Table type = new Table("changhong");
		mysqls.sql.entity.TableColumn tableColumn = new mysqls.sql.entity.TableColumn("changhong", true, true, true);
		tableColumn.setForeignKey(true);
		tableColumn.setForigncolumn(tableColumn);
		tableColumn.setForigntable(type);
		type.addColumn(tableColumn);
		type.addColumn(new TableColumn("1111"));
		System.out.println(type.toSQL());
		System.out.println(type.toString());
		System.out.println(tableColumn.toSQL());
		System.out.println(tableColumn.toString());
		System.out.println(SQLCreator.create(type));

	}

	public static String create(Table table) {
		StringBuilder builder = new StringBuilder();
		builder.append(table.toSQL());
		for (int i = 0; i < table.getColumnlist().size(); i++) {
			builder.append(table.getColumnlist().get(i).toSQL());
		}
		// for (TableColumn column : table.getColumnlist()) {
		// builder.append(column.toSQL());
		// }
		builder.append(")\n");
		int index = builder.lastIndexOf(",");
		builder.deleteCharAt(index);
		return builder.toString();

	}
}
