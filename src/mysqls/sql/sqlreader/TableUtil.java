/**
 * 实体关系图和sql生产的实现
 */
package mysqls.sql.sqlreader;

import mysqls.sql.entity.Table;
import mysqls.sql.entity.TableColumn;

import java.util.List;
import java.util.Optional;

/**
 * @author 长宏
 *
 */
public class TableUtil {

    public static Table findtable(List<Table> tables, String tablename) {

        Optional<Table> table = tables.stream().filter(a -> a.getName().equals(tablename)).findAny();
        return table.get();
    }

    public static TableColumn findColumn(Table table, String columnname) {

        List<TableColumn> list = table.getColumnlist().getList();
        Optional<TableColumn> column = list.stream().filter(a -> a.getName().equals(columnname)).findAny();
        return column.get();

    }
}
