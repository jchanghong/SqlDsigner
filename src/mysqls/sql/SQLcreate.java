package mysqls.sql;

import java.util.Iterator;
import java.util.List;

import com.sun.corba.se.spi.orbutil.fsm.ActionBase;

import mysqls.graph.AssociationEdge;
import mysqls.graph.AssociationEdge.Directionality;
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
		builder.append(" \n(");
		List<String> lista=classNode.getAttributes().getMuilineString();
		for(String aString:lista)
		{
			builder.append(aString);
			builder.append(" varchar(20)");
			if (!aString.equals(lista.get(lista.size()-1))){
				builder.append(",\n");
			}
		}
		
		builder.append(");\n");
		
		return builder.toString();
		
	}
	static public void addsqlassiontion(String sqlstring,List<AssociationEdge> edges)
	{
		
		if (edges.size()<1) {
			return;
		}
		for (Iterator<AssociationEdge> iterator = edges.iterator(); iterator.hasNext();) {
			AssociationEdge associationEdge = (AssociationEdge) iterator.next();
			ClassNode sNode=(ClassNode) associationEdge.getStart();
			ClassNode enNode=(ClassNode) associationEdge.getEnd();
			ClassNode temp=null;
			
			if (associationEdge.getDirectionality()==Directionality.Start) {
				temp=sNode;
				sNode=enNode;
				enNode=sNode;
				
			}
			
			addassocition(sqlstring, sNode, enNode);
			
		}
		
		
		
		
	}
	
	private static void addassocition(String sql,ClassNode sNode,ClassNode eNode) {
		String sname=sNode.getName().getMuilineString().get(0);
		String ename=eNode.getName().getMuilineString().get(0);
		String firstattrubute=sNode.getAttributes().getMuilineString().get(0);
		String endattrubute=eNode.getAttributes().getMuilineString().get(0);
		
		
	}
}
