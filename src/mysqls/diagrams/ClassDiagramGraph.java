
package mysqls.diagrams;

import java.awt.geom.Point2D;

import mysqls.graph.AssociationEdge;
import mysqls.graph.ClassNode;
import mysqls.graph.Edge;
import mysqls.graph.Graph;
import mysqls.graph.Node;
import mysqls.graph.NoteEdge;
import mysqls.graph.NoteNode;

/**
 * A UML class diagram.
 */
public class ClassDiagramGraph extends Graph {
	private static final Node[] NODE_PROTOTYPES = new Node[] { new ClassNode(),
			// new InterfaceNode(),
			// new PackageNode(),
			new NoteNode() };

	private static final Edge[] EDGE_PROTOTYPES = new Edge[] {
			// new DependencyEdge(),
			// new GeneralizationEdge(),
			// new GeneralizationEdge(GeneralizationEdge.Type.Implementation),
			new AssociationEdge(),
			// new AggregationEdge(),
			// new AggregationEdge(AggregationEdge.Type.Composition),
			new NoteEdge() };

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

		return ".Er";

	}

	@Override
	public String getDescription() {
		// return
		// ResourceBundle.getBundle("uestc.uml.sql.UMLEditorStrings").getString("class.name");
		return "实体关系图";

	}

	// private static boolean canAddNodeAsChild(Node pPotentialChild) {
	// return pPotentialChild instanceof ClassNode || pPotentialChild instanceof
	// NoteEdge
	//
	// ;
	// }

	@Override
	public boolean canConnect(Edge pEdge, Node pNode1, Node pNode2, Point2D pPoint2) {
		if (!super.canConnect(pEdge, pNode1, pNode2, pPoint2)) {
			return false;
		}
		// if( pEdge instanceof GeneralizationEdge && pNode1 == pNode2 )
		// {
		// return false;
		// }

		return true;
	}

	/*
	 * Find if the node to be added should be added to a package. Returns null
	 * if not. If packages overlap, select the last one added, which by default
	 * should be on top. This could be fixed if we ever add a z coordinate to
	 * the graph.
	 */
	// private PackageNode findContainer( List<Node> pNodes, Point2D pPoint)
	// {
	// PackageNode container = null;
	// for( Node node : pNodes )
	// {
	// if( node instanceof PackageNode && node.contains(pPoint) )
	// {
	// container = (PackageNode) node;
	// }
	// }
	// if( container == null )
	// {
	// return null;
	// }
	// @SuppressWarnings({ "unchecked", "rawtypes" })
	// List<Node> children = new ArrayList(container.getChildren());
	// if( children.size() == 0 )
	// {
	// return container;
	// }
	// else
	// {
	// PackageNode deeperContainer = findContainer( children, pPoint );
	// if( deeperContainer == null )
	// {
	// return container;
	// }
	// else
	// {
	// return deeperContainer;
	// }
	// }
	// }

	/*
	 * (non-Javadoc) See if, given its position, the node should be added as a
	 * child of a PackageNode.
	 *
	 * @see uestc.uml.sql.graph.Graph#addNode(uestc.uml.sql.graph .Node,
	 * java.awt.geom.Point2D)
	 */
	@Override
	public boolean addNode(Node pNode, Point2D pPoint) {
		// if( canAddNodeAsChild(pNode))
		// {
		// PackageNode container = findContainer(aRootNodes, pPoint);
		// if( container != null )
		// {
		// container.addChild((ChildNode)pNode);
		// }
		// }
		super.addNode(pNode, pPoint);
		return true;
	}
}
