
package mysqls.graph;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * An edge in a graph.
 */
public interface Edge extends GraphElement {
	/**
	 * Draw the edge.
	 * 
	 * @param pGraphics2D
	 *            the graphics context
	 */
	void draw(Graphics2D pGraphics2D);

	/**
	 * Tests whether the edge contains a point.
	 * 
	 * @param pPoint
	 *            the point to test
	 * @return true if this edge contains aPoint
	 */
	boolean contains(Point2D pPoint);

	/**
	 * Connect this edge to two nodes.
	 * 
	 * @param pStart
	 *            the starting node
	 * @param pEnd
	 *            the ending node
	 */
	void connect(Node pStart, Node pEnd);

	/**
	 * Gets the starting node.
	 * 
	 * @return the starting node
	 */
	Node getStart();

	/**
	 * Gets the ending node.
	 * 
	 * @return the ending node
	 */
	Node getEnd();

	/**
	 * Gets the points at which this edge is connected to its nodes.
	 * 
	 * @return a line joining the two connection points
	 */
	Line2D getConnectionPoints();

	/**
	 * @return A clone of this edge, with shallow cloning of the start and end
	 *         nodes (i.e., the start and end nodes are not cloned).
	 */
	Object clone();
}
