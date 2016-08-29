package mysqls.sql;

import java.util.ArrayList;
import java.util.List;

public class SQLkeyword {

	private SQLkeyword() {

	}

	public static List<String> keyword;
	static {
		SQLkeyword.keyword = new ArrayList<>();
		SQLkeyword.keyword.add("create");
		SQLkeyword.keyword.add("drop");
		SQLkeyword.keyword.add("insert");
		SQLkeyword.keyword.add("update");
		SQLkeyword.keyword.add("delete");

	}

}
