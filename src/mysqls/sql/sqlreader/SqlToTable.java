package mysqls.sql.sqlreader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import com.sun.org.apache.bcel.internal.generic.BIPUSH;
import com.sun.org.apache.regexp.internal.recompile;
import com.sun.org.glassfish.external.statistics.Statistic;

import javafx.scene.control.Tab;
import mysqls.framework.GraphFrame;
import mysqls.sql.SQLEditPane;
import mysqls.sql.test;
import mysqls.sql.entity.Columnlist;
import mysqls.sql.entity.Table;
import mysqls.sql.entity.TableColumn;

/**
 * @author Administrator
 * string sql to table class object util 
 *
 */
public class SqlToTable {
	
	static GraphFrame mFrame;
	public static Table from(String sqlline) {
		return null;
		
	}
	public static void main(String[] args) {
		System.out.println("start test");
		System.out.println(gettable("11111").get(0).getName());
	}
	private static Table SqlReader(List<String> sqllist,int start,int end){ 
		Table table=new Table();
		String string=sqllist.get(start);
	    int index=	string.indexOf("create  table");
	table.setName(string.substring(index+1,string.length()));//表名
	
	Columnlist columnlist=table.getColumnlist();
		for (int i = start+1; i < end; i++) {
			TableColumn column=new TableColumn();
			String liString=sqllist.get(i);
			String[] row=liString.split(" ");//去除空格后
			String columnname=row[0];
			column.setName(columnname);//列名
			String type=row[1];
			column.setType(type);//列的类型
			for (String string2 : row) {
				if(string2.startsWith("PRIMARYKEY")){//判断第三个字符是不是Primarykey
					column.setPrimarykey(true);
					column.setNotnull(true);
					column.setUnique(true);
				}
				if(string2.startsWith("NOTNULL")){//判断是不是notnull
					column.setNotnull(true);
				}
				if(string2.startsWith("UNIQUE")){//接着判断是不是unique
					column.setUnique(true);

//				if(string2.startsWith("REFERENCES")){
//					Table forigntable = row[3];
//					column.setForigntable(forigntable); 			
//						
//						TableColumn forigncolumn = row[4];
//						column.setForigncolumn(forigncolumn); 
//			}
				}
			columnlist.add(column);
			}
		}
		return table;
}
	
	public static List<Table> gettable(String sqlstring) {
		List<Table> listabl=new ArrayList<>();
		List<String> list=new ArrayList<String>();
		String[] lid=sqlstring.split("\n");
		for (String string : lid) {
			list.add(string);
		}
		int star=0;
		int end=0;
		int postion=0;
		for (int i = 0; i < list.size();i++) {
			String string = list.get(i);
			if (string.equals("(")) {
				star=i-1;
			
			continue;
			}
			if (string.equals(");")) {
				
				end=i;
				
				
			}
			
		listabl.add(SqlReader(list, star, end));
		
			
		}
		return listabl;
	}

	}
