/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.util;

import mysqls.graph.AssociationEdge;
import mysqls.graph.ClassNode;
import mysqls.sql.entity.Table;
import mysqls.sql.entity.TableColumn;

/**
 * @author 长宏 生产 sql语句
 *
 */
public class SQLCreator {

	/**
	 *
	 */
	private SQLCreator() {
		// TODO Auto-generated constructor stub
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
		builder.append(");\n");
		int index = builder.lastIndexOf(",");
		builder.deleteCharAt(index);

		return builder.toString();

	}

	/**
	 * @param aEdge变更新后设置节点
	 */
	public static void setTable(AssociationEdge aEdge) {
		ClassNode sta = (ClassNode) aEdge.getStart();
		ClassNode eNode = (ClassNode) aEdge.getEnd();
		String string = aEdge.getStartLabel();
		String eString = aEdge.getEndLabel();
		// if (string.length() < 1 || eString.length() < 1) {
		// return;
		// }
		TableColumn sColumn = aEdge.sTableColumn;
		TableColumn eColumn = aEdge.eTableColumn;
		if (sColumn == null && eColumn == null) {
			return;
		}

		if (aEdge.getDirectionality().equals(AssociationEdge.Directionality.Start)) {

			eColumn.setForeignKey(true);
			eColumn.setForigncolumn(sColumn);
			eColumn.setForigntable(sta.mTable);
		} else {

			sColumn.setForeignKey(false);
			sColumn.setForigncolumn(eColumn);
			sColumn.setForigntable(eNode.mTable);
		}

	}

	/**
	 * @param 借点改变后更新边
	 */
	public static void setEdge(ClassNode node, TableColumn column, AssociationEdge edge) {
		if (edge == null || node == null || column == null) {
			return;
		}
		if (edge.sTableColumn == column) {
			edge.setStartLabel(column.getName());
		} else if (edge.eTableColumn == column) {
			edge.setEndLabel(column.getName());

		}

	}
}
