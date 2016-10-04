package mysqls.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiang 关键字。 table view datablese 等颜色不同
 */
public class SQLword_name {

    public static final List<String> LIST;

    static {
        LIST = new ArrayList<>();
        SQLword_name.LIST.add("table");
        SQLword_name.LIST.add("view");
        SQLword_name.LIST.add("database");
    }
}
