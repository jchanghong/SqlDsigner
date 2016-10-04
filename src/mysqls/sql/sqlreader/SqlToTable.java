package mysqls.sql.sqlreader;

import mysqls.sql.entity.Columnlist;
import mysqls.sql.entity.DataTypeUI;
import mysqls.sql.entity.Table;
import mysqls.sql.entity.TableColumn;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sql语句到table的实现
 */
public final class SqlToTable {

    /**
     * 工具类不需要实现
     */
    private SqlToTable() {
        // TODO Auto-generated constructor stub
    }

    private static Table SqlReader(List<String> sqllist, int start, int end) {
        Table table = new Table();
        String string = sqllist.get(start);

        int index = string.indexOf("table");
        table.setName(string.substring(index + 6, string.length()).trim());// 表名

        Columnlist columnlist = table.getColumnlist();

        for (int i = start + 2; i <= end; i++) {
            TableColumn column = new TableColumn();
            String liString = sqllist.get(i);
            String[] row = liString.split("\\s+");// 去除空格后
            String columnname = row[0];
            column.setName(columnname);// 列名
            if (row.length < 2) {
                continue;
            }
            String type = row[1];
            column.setType(DataTypeUI.toenum(type));// 列的类型
            for (String string2 : row) {
                if (string2.startsWith("PRIMARYKEY")) {// 判断第三个字符是不是Primarykey
                    column.setPrimarykey(true);
                    column.setNotnull(true);
                    column.setUnique(true);
                }
                if (string2.startsWith("NULL")) {// 判断是不是notnull
                    column.setNotnull(true);
                }
                if (string2.startsWith("UNIQUE")) {// 接着判断是不是unique
                    column.setUnique(true);
                }

                if (string2.startsWith("REFERENCES")) {
                    column.setForeignKey(true);

                }
            }
            columnlist.add(column);
        }

        return table;
    }

    public static List<Table> getAllTable(String sql) {
        List<Table> list = SqlToTable.gettable(sql);
        return list;
    }

    private static List<Table> gettable(String sqlstring) {
        List<Table> listabl = new ArrayList<>();
        List<String> list = new ArrayList<String>();
        String[] lid = sqlstring.split("\n");
        for (String string : lid) {
            list.add(string);
        }
        if (list.size() < 1) {
            throw new NullPointerException("sql is null");
        }
        int star = 0;
        int end = 0;
        for (int i = 0; i < list.size(); i++) {
            String string = list.get(i);
            if (string.equals("(")) {
                star = i - 1;

            }
            if (string.equals(");")) {

                end = i;
                listabl.add(SqlToTable.SqlReader(list, star, end - 1));
            }

        }

        return listabl;
    }

}
