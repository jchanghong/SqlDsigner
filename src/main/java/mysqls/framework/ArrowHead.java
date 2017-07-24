package mysqls.framework;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 * This class defines arrowheads of various shapes.
 */
public final class ArrowHead {
    public static final ArrowHead NONE = new ArrowHead();
    public static final ArrowHead TRIANGLE = new ArrowHead();
    public static final ArrowHead BLACK_TRIANGLE = new ArrowHead();
    public static final ArrowHead V = new ArrowHead();
    public static final ArrowHead HALF_V = new ArrowHead();
    public static final ArrowHead DIAMOND = new ArrowHead();
    public static final ArrowHead BLACK_DIAMOND = new ArrowHead();

    // CSOFF:
    private static final double ARROW_ANGLE = Math.PI / 6;
    private static final double ARROW_LENGTH = 10;
    // CSON:

    private ArrowHead() {
    }

    /**
     * Draws the arrowhead.
     *
     * @param pGraphics2D the graphics context
     * @param pPoint1     a point on the axis of the arrow head
     * @param pEnd        the end point of the arrow head
     */
    public void draw(Graphics2D pGraphics2D, Point2D pPoint1, Point2D pEnd) {
        GeneralPath path = getPath(pPoint1, pEnd);
        Color oldColor = pGraphics2D.getColor();
        if (this == ArrowHead.BLACK_DIAMOND || this == ArrowHead.BLACK_TRIANGLE) {
            pGraphics2D.setColor(Color.BLACK);
        } else {
            pGraphics2D.setColor(Color.WHITE);
        }
        pGraphics2D.fill(path);
        pGraphics2D.setColor(oldColor);
        pGraphics2D.draw(path);
    }

    @Override
    public String toString() {
        String lReturn = "Unknown";
        if (this == ArrowHead.NONE) {
            lReturn = "NONE";
        } else if (this == ArrowHead.TRIANGLE) {
            lReturn = "TRIANGLE";
        } else if (this == ArrowHead.BLACK_TRIANGLE) {
            lReturn = "BLACK_TRIANGLE";
        } else if (this == ArrowHead.V) {
            lReturn = "V";
        } else if (this == ArrowHead.HALF_V) {
            lReturn = "HALF_V";
        } else if (this == ArrowHead.DIAMOND) {
            lReturn = "DIAMOND";
        } else if (this == ArrowHead.BLACK_DIAMOND) {
            lReturn = "BLACK_DIAMOND";
        }
        return lReturn;
    }

    /**
     * Gets the path of the arrowhead.
     *
     * @param pPoint1 a point on the axis of the arrow head
     * @param pEnd    the end point of the arrow head
     * @return the path
     */
    public GeneralPath getPath(Point2D pPoint1, Point2D pEnd) {
        GeneralPath path = new GeneralPath();
        if (this == ArrowHead.NONE) {
            return path;
        }

        double dx = pEnd.getX() - pPoint1.getX();
        double dy = pEnd.getY() - pPoint1.getY();
        double angle = Math.atan2(dy, dx);
        double x1 = pEnd.getX() - ArrowHead.ARROW_LENGTH * Math.cos(angle + ArrowHead.ARROW_ANGLE);
        double y1 = pEnd.getY() - ArrowHead.ARROW_LENGTH * Math.sin(angle + ArrowHead.ARROW_ANGLE);
        double x2 = pEnd.getX() - ArrowHead.ARROW_LENGTH * Math.cos(angle - ArrowHead.ARROW_ANGLE);
        double y2 = pEnd.getY() - ArrowHead.ARROW_LENGTH * Math.sin(angle - ArrowHead.ARROW_ANGLE);

        path.moveTo((float) pEnd.getX(), (float) pEnd.getY());
        path.lineTo((float) x1, (float) y1);
        if (this == ArrowHead.V) {
            path.moveTo((float) x2, (float) y2);
            path.lineTo((float) pEnd.getX(), (float) pEnd.getY());
        } else if (this == ArrowHead.TRIANGLE || this == ArrowHead.BLACK_TRIANGLE) {
            path.lineTo((float) x2, (float) y2);
            path.closePath();
        } else if (this == ArrowHead.DIAMOND || this == ArrowHead.BLACK_DIAMOND) {
            double x3 = x2 - ArrowHead.ARROW_LENGTH * Math.cos(angle + ArrowHead.ARROW_ANGLE);
            double y3 = y2 - ArrowHead.ARROW_LENGTH * Math.sin(angle + ArrowHead.ARROW_ANGLE);
            path.lineTo((float) x3, (float) y3);
            path.lineTo((float) x2, (float) y2);
            path.closePath();
        }
        return path;
    }
}
