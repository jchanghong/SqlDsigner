package mysqls.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiang
 *table 
 *view datablese 等颜色不同
 */
public class SQLword_name {

	public static final List<String> LIST;
	static{
		LIST=new ArrayList<>();
		LIST.add("table");
		LIST.add("view");
		LIST.add("database");
	}
}
