
package mysqls.graph;

import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import mysqls.framework.Direction;

/**
 * Supplies convenience implementations for a number of methods in the Edge
 * interface. In particular, the class implements support for "containment
 * testing" of edges, i.e., testing whether a point falls on a edge. This is
 * done by obtaining the shape of the edge and stroking it with a fat stroke.
 * NOTE: Ideally, you should be able to draw the same shape that is used for
 * containment testing. However, in JDK 1.4, BasicStroke.createStrokedShape
 * returned shitty-looking shapes, so drawing the stroked shapes should be
 * visually tested for each edge type.
 */
abstract class AbstractEdge implements Edge {
	private static final int DEGREES_180 = 180;
	private static final double MAX_DISTANCE = 3.0;
	private Node aStart;
	private Node aEnd;

	/**
	 * Returns the path that should be stroked to draw this edge. The path does
	 * not include arrow tips or labels.
	 * 
	 * @return a path along the edge
	 */
	protected abstract Shape getShape();

	@Override
	public Rectangle2D getBounds() {
		return getShape().getBounds(); // Note that this returns an integer
										// rectangle
	}

	@Override
	public boolean contains(Point2D pPoint) {
		// The end points may contain small nodes, so don't match them
		Line2D conn = getConnectionPoints();
		if (pPoint.distance(conn.getP1()) <= AbstractEdge.MAX_DISTANCE
				|| pPoint.distance(conn.getP2()) <= AbstractEdge.MAX_DISTANCE) {
			return false;
		}

		Shape fatPath = new BasicStroke((float) (2 * AbstractEdge.MAX_DISTANCE)).createStrokedShape(getShape());
		return fatPath.contains(pPoint);
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException exception) {
			return null;
		}
	}

	@Override
	public void connect(Node pStart, Node pEnd) {
		assert pStart != null && pEnd != null;
		aStart = pStart;
		aEnd = pEnd;
	}

	@Override
	public Node getStart() {
		return aStart;
	}

	@Override
	public Node getEnd() {
		return aEnd;
	}

	@Override
	public Line2D getConnectionPoints() {
		Rectangle2D startBounds = aStart.getBounds();
		Rectangle2D endBounds = aEnd.getBounds();
		Point2D startCenter = new Point2D.Double(startBounds.getCenterX(), startBounds.getCenterY());
		Point2D endCenter = new Point2D.Double(endBounds.getCenterX(), endBounds.getCenterY());
		Direction toEnd = new Direction(startCenter, endCenter);
		return new Line2D.Double(aStart.getConnectionPoint(toEnd),
				aEnd.getConnectionPoint(toEnd.turn(AbstractEdge.DEGREES_180)));
	}
}
