/**
 * 实体关系图和sql生产的实现
 */
package mysqls.graph;

import mysqls.framework.Direction;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * @author 长宏
 *
 */
public class PointNode extends AbstractNode {

    private static final double EPSILON = 0.01;

    private Point2D aPoint;

    /**
     * Constructs a point node with coordinates (0, 0).
     */
    public PointNode() {
        aPoint = new Point2D.Double();
    }

    @Override
    public void draw(Graphics2D pGraphics2D) {
    }

    @Override
    public void translate(double pDeltaX, double pDeltaY) {
        aPoint.setLocation(aPoint.getX() + pDeltaX, aPoint.getY() + pDeltaY);
    }

    @Override
    public boolean contains(Point2D pPoint) {
        final double threshold = 5;
        return aPoint.distance(pPoint) < threshold;
    }

    @Override
    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(aPoint.getX(), aPoint.getY(), PointNode.EPSILON, PointNode.EPSILON);
    }

    @Override
    public Point2D getConnectionPoint(Direction pDirection) {
        return aPoint;
    }

}
