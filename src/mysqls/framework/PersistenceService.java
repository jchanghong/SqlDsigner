package mysqls.framework;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mysqls.diagrams.ClassDiagramGraph;
import mysqls.graph.AssociationEdge;
import mysqls.graph.ClassNode;
import mysqls.graph.Graph;
import mysqls.sql.entity.Table;
import mysqls.sql.sqlreader.SqlToTable2;
import mysqls.sql.util.MyIOutil;

/**
 * @author 长宏 sql文件的保存和sql文件，sqlstring 到图形的生成
 *
 */
public final class PersistenceService {

	public static double MAGIN = 5.0;// 图形间距

	private PersistenceService() {
	}

	/**
	 * @param 保存的文件
	 * @return 全新graph
	 */
	public static Graph read(String filename) {
		Graph graph = new ClassDiagramGraph();
		String sql = MyIOutil.read(new File(filename));
		List<Table> list = SqlToTable2.getAllTable(sql);
		double x = 0;
		double y = 50;
		for (Table table : list) {
			ClassNode node = new ClassNode(table);
			Point2D point2d = new Point2D.Double(x, y);
			graph.addNode(node, point2d);
			x = x + node.getBounds().getWidth() + PersistenceService.MAGIN;
			y += 50;

		}

		return graph;

	}

	/**
	 * @param sql
	 * @param graph设置graph
	 * @return
	 */
	public static Graph readSQL(String sql, Graph graph) {

		List<ClassNode> nodes = new ArrayList<>();
		List<Table> list = SqlToTable2.getAllTable(sql);
		for (Table table : list) {
			nodes.add(new ClassNode(table));
		}
		graph.removeall();
		double x = 0;
		double y = 50;
		for (ClassNode node : nodes) {
			Point2D point2d = new Point2D.Double(x, y);
			graph.addNode(node, point2d);
			x = x + node.getBounds().getWidth() + PersistenceService.MAGIN;
			y += 50;

		}
		List<Map<String, Table>> maps = list.stream().filter(Table::hasForeigrnKey).map(a -> {
			Map<String, Table> map = new HashMap<>();
			map.put("s", a);
			map.put("e", a.getForeigenTable());
			return map;
		}).collect(Collectors.toList());

		for (Map<String, Table> map : maps) {
			AssociationEdge edge = new AssociationEdge();
			Table sTable = map.get("s");
			Table eTable = map.get("e");
			ClassNode sClassNode = PersistenceService.findNode(nodes, eTable);
			ClassNode eClassNode = PersistenceService.findNode(nodes, sTable);
			graph.addEdge(edge, PersistenceService.findcpoint(sClassNode), PersistenceService.findcpoint(eClassNode));

		}

		return graph;
	}

	private static Point findcpoint(ClassNode node) {
		Rectangle2D rectangle2d = node.getBounds();
		return new Point((int) rectangle2d.getCenterX(), (int) rectangle2d.getCenterY());
	}

	private static ClassNode findNode(List<ClassNode> list, Table table) {
		for (ClassNode classNode : list) {
			if (classNode.mTable == table) {
				return classNode;
			}
		}
		return null;

	}

	/**
	 * Saves the current graph in a file.
	 *
	 * @param pGraph
	 *            The graph to save
	 * @param pOut
	 *            the file for saving
	 */
	public static void saveFile(Graph pGraph, File file) {
		mysqls.sql.util.MyIOutil.savefile(pGraph, file);

	}
}
