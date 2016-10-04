package mysqls.framework;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * 这个其实就是设置格子的A grid to which points and rectangles can be "snapped". The
 * snapping operation moves a point to the nearest grid point.
 */
public class Grid {
    private static final Color GRID_COLOR = new Color(220, 220, 220);
    private static final double GRID_SIZE = 10.0;

    /**
     * Draws this grid inside a rectangle.
     *
     * @param pGraphics2D the graphics context
     * @param pBounds     the bounding rectangle
     */
    public static void draw(Graphics2D pGraphics2D, Rectangle2D pBounds) {
        Color oldColor = pGraphics2D.getColor();
        pGraphics2D.setColor(Grid.GRID_COLOR);
        Stroke oldStroke = pGraphics2D.getStroke();
        for (double x = pBounds.getX(); x < pBounds.getMaxX(); x += Grid.GRID_SIZE) {
            pGraphics2D.draw(new Line2D.Double(x, pBounds.getY(), x, pBounds.getMaxY()));
        }
        for (double y = pBounds.getY(); y < pBounds.getMaxY(); y += Grid.GRID_SIZE) {
            pGraphics2D.draw(new Line2D.Double(pBounds.getX(), y, pBounds.getMaxX(), y));
        }
        pGraphics2D.setStroke(oldStroke);
        pGraphics2D.setColor(oldColor);
    }

    /**
     * Snaps a rectangle to the nearest grid points.
     *
     * @param pRectangle the rectangle to snap. After the call, the coordinates of r
     *                   are changed so that all of its corners falls on the grid.
     */
    public void snap(Rectangle2D pRectangle) {
        double x = Math.round(pRectangle.getX() / Grid.GRID_SIZE) * Grid.GRID_SIZE;
        double w = Math.ceil(pRectangle.getWidth() / (2 * Grid.GRID_SIZE)) * (2 * Grid.GRID_SIZE);
        double y = Math.round(pRectangle.getY() / Grid.GRID_SIZE) * Grid.GRID_SIZE;
        double h = Math.ceil(pRectangle.getHeight() / (2 * Grid.GRID_SIZE)) * (2 * Grid.GRID_SIZE);
        pRectangle.setFrame(x, y, w, h);
    }
}
