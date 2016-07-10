package mysqls.sql;

import java.util.ArrayList;
import java.util.List;


public class SQLkeyword {

	
	
	private  SQLkeyword() {
		
	}
	public static  List<String> keyword;
	static{
		keyword=new ArrayList<>();
		keyword.add("create");
		keyword.add("drop");
	
		
	}
	
}
