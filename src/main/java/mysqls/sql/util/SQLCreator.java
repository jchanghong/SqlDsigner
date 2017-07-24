/**
 * 实体关系图和sql生产的实现
 */
package mysqls.sql.util;

import mysqls.graph.AssociationEdge;
import mysqls.graph.ClassNode;
import mysqls.sql.entity.Table;
import mysqls.sql.entity.TableColumn;

/**
 * @author 长宏 生产 sql语句 外键一定是index，一定not null，但是不一定unique，主键一定是unique，not null
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
        if (table.getColumnlist().size() == 0) {
            return "";
        }

        // 先删除表
        builder.append("DROP TABLE IF EXISTS " + table.getName() + ";\n");

        // 常规语句
        builder.append(table.toSQL());
        for (int i = 0; i < table.getColumnlist().size(); i++) {
            builder.append(table.getColumnlist().get(i).toSQL());
        }

        // 外键语句
        for (int i = 0; i < table.getColumnlist().size(); i++) {

            TableColumn column = table.getColumnlist().get(i);
            if (column.isForeignKey() && column.getForigntable() != null && column.getForigncolumn() != null) {
                builder.append("CONSTRAINT  ");
                builder.append("FOREIGN KEY");
                builder.append(" (`" + column.getName() + "`)");
                builder.append("  REFERENCES");
                builder.append(" ");
                builder.append("`" + column.getForigntable().getName() + "`");
                builder.append(" (`");
                builder.append(column.getForigncolumn().getName());
                builder.append("`),\n");
            }

        }
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
            sColumn.setForeignKey(false);
            eColumn.setForigncolumn(sColumn);
            eColumn.setForigntable(sta.mTable);
        } else {

            sColumn.setForeignKey(true);
            eColumn.setForeignKey(false);
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
