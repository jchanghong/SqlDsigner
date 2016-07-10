package mysqls.sql;

import java.util.List;

import com.sun.corba.se.spi.orbutil.fsm.ActionBase;

import mysqls.graph.ClassNode;

/**
 * @author jiang
 *生成create语句
 */
public class SQLcreate {
	
	private SQLcreate(){}
	public static String fromxml(String xml) {
		return xml;
		
	}

	public static String fromNodeObject(ClassNode classNode) {
		StringBuilder builder=new StringBuilder();
		String name=classNode.getName().getMuilineString().get(0);
		builder.append("create ");
		builder.append(" table ");
		builder.append(name);
		builder.append(" (");
		List<String> lista=classNode.getAttributes().getMuilineString();
		for(String aString:lista)
		{
			builder.append(aString);
			builder.append(" varchar(20)");
			if (!aString.equals(lista.get(lista.size()-1))){
				builder.append(",");
			}
		}
		
		builder.append(")");
		
		return builder.toString();
		
	}
}
