package mysqls.graph;

import mysqls.framework.Direction;
import mysqls.framework.Grid;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * A node that has a rectangular shape.
 */
public abstract class RectangularNode extends AbstractNode {
    private Rectangle2D aBounds;

    @Override
    public RectangularNode clone() {
        RectangularNode cloned = (RectangularNode) super.clone();
        cloned.aBounds = (Rectangle2D) aBounds.clone();
        return cloned;
    }

    @Override
    public void translate(double pDeltaX, double pDeltaY) {
        aBounds.setFrame(aBounds.getX() + pDeltaX, aBounds.getY() + pDeltaY, aBounds.getWidth(), aBounds.getHeight());
        super.translate(pDeltaX, pDeltaY);
    }

    @Override
    public boolean contains(Point2D pPoint) {
        return aBounds.contains(pPoint);
    }

    @Override
    public Rectangle2D getBounds() {
        return (Rectangle2D) aBounds.clone();
    }

    /**
     * @param pNewBounds The new bounds for this node.
     */
    public void setBounds(Rectangle2D pNewBounds) {
        aBounds = pNewBounds;
    }

    @Override
    public void layout(Graph pGraph, Graphics2D pGraphics2D, Grid pGrid) {
        pGrid.snap(aBounds);
    }

    @Override
    public Point2D getConnectionPoint(Direction pDirection) {
        double slope = aBounds.getHeight() / aBounds.getWidth();
        double ex = pDirection.getX();
        double ey = pDirection.getY();
        double x = aBounds.getCenterX();
        double y = aBounds.getCenterY();

        if (ex != 0 && -slope <= ey / ex && ey / ex <= slope) {
            // intersects at left or right boundary
            if (ex > 0) {
                x = aBounds.getMaxX();
                y += (aBounds.getWidth() / 2) * ey / ex;
            } else {
                x = aBounds.getX();
                y -= (aBounds.getWidth() / 2) * ey / ex;
            }
        } else if (ey != 0) {
            // intersects at top or bottom
            if (ey > 0) {
                x += (aBounds.getHeight() / 2) * ex / ey;
                y = aBounds.getMaxY();
            } else {
                x -= (aBounds.getHeight() / 2) * ex / ey;
                y = aBounds.getY();
            }
        }
        return new Point2D.Double(x, y);
    }

    @Override
    public Shape getShape() {
        return aBounds;
    }
}
