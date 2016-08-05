
package mysqls.diagrams;

import java.awt.geom.Point2D;

import mysqls.graph.AssociationEdge;
import mysqls.graph.ClassNode;
import mysqls.graph.Edge;
import mysqls.graph.Graph;
import mysqls.graph.Node;
import mysqls.graph.NoteEdge;
import mysqls.graph.NoteNode;
import mysqls.sql.entity.Table;

/**
 * uml实体关系图，也就是我们实际需要的图，大部分画图的基类里面
 */
public class ClassDiagramGraph extends Graph {
	private static final Node[] NODE_PROTOTYPES = new Node[] { new mysqls.graph.ClassNode(new Table()),
			new NoteNode() };

	private static final Edge[] EDGE_PROTOTYPES = new Edge[] { new AssociationEdge(), new NoteEdge() };

	@Override
	public Node[] getNodePrototypes() {
		return ClassDiagramGraph.NODE_PROTOTYPES;
	}

	@Override
	public Edge[] getEdgePrototypes() {
		return ClassDiagramGraph.EDGE_PROTOTYPES;
	}

	@Override
	public String getFileExtension() {

		return ".sql";

	}

	/**
	 * @param 借点
	 * @return 和借点关联的边
	 */
	public AssociationEdge findEdge(ClassNode node) {

		for (AssociationEdge edge : getClassEdge()) {
			if (edge.getStart() == node || edge.getEnd() == node) {
				return edge;
			}

		}
		return null;

	}

	@Override
	public String getDescription() {
		return "SQL文件";

	}

	@Override
	public boolean canConnect(Edge pEdge, Node pNode1, Node pNode2, Point2D pPoint2) {
		if (!super.canConnect(pEdge, pNode1, pNode2, pPoint2)) {
			return false;
		}
		return true;
	}
}
