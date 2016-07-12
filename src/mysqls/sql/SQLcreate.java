package mysqls.sql;

import java.util.Iterator;
import java.util.List;

import mysqls.graph.AssociationEdge;
import mysqls.graph.AssociationEdge.Directionality;
import mysqls.graph.ClassNode;

/**
 * @author jiang 生成create语句
 */
public class SQLcreate {

	private SQLcreate() {
	}

	public static String fromxml(String xml) {
		return xml;

	}

	public static String fromNodeObject(ClassNode classNode) {
		StringBuilder builder = new StringBuilder();
		String name = classNode.getName().getMuilineString().get(0);
		builder.append("create ");
		builder.append(" table ");
		builder.append(name);
		builder.append(" \n(");
		List<String> lista = classNode.getAttributes().getMuilineString();
		for (String aString : lista) {
			builder.append(aString);
			builder.append(" varchar(20)");
			if (!aString.equals(lista.get(lista.size() - 1))) {
				builder.append(",\n");
			}
		}

		builder.append(");\n");

		return builder.toString();

	}

	static public String addsqlassiontion(String sqlstring, List<AssociationEdge> edges) {

		if (edges.size() < 1) {
			return sqlstring;
		}
		StringBuilder builder = new StringBuilder(sqlstring);
		for (Iterator<AssociationEdge> iterator = edges.iterator(); iterator.hasNext();) {
			AssociationEdge associationEdge = (AssociationEdge) iterator.next();
			ClassNode sNode = (ClassNode) associationEdge.getStart();
			ClassNode enNode = (ClassNode) associationEdge.getEnd();
			ClassNode temp = null;

			if (associationEdge.getDirectionality() == Directionality.Start) {
				temp = sNode;
				sNode = enNode;
				enNode = temp;

			}

			builder = addassocition(sqlstring, sNode, enNode);

		}

		return builder.toString();

	}

	private static StringBuilder addassocition(String sql, ClassNode sNode, ClassNode eNode) {
		String sname = sNode.getName().getMuilineString().get(0);
		String ename = eNode.getName().getMuilineString().get(0);
		String sfirstattrubute = sNode.getAttributes().getMuilineString().get(0);
		String efirsttrubute = eNode.getAttributes().getMuilineString().get(0);
		int index = 0;
		index = sql.indexOf(sname);
		index = sql.indexOf(");", index + sname.length());
		StringBuilder builder = new StringBuilder(sql);
		int i;
		int j;
		i = builder.length();
		builder.insert(index, ",\n PRIMARY KEY (" + sfirstattrubute + ")");
		j = builder.length();
		builder.insert(index + j - i,
				",\n FOREIGN KEY (" + efirsttrubute + ")" + " REFERENCES " + ename + "(" + efirsttrubute + ")");

		index = builder.indexOf(ename);
		index = builder.indexOf(");", index);
		builder.insert(index, ",\n PRIMARY KEY (" + efirsttrubute + ")");
		return builder;

	}
}
