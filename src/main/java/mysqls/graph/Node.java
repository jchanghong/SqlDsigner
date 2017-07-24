package mysqls.graph;

import mysqls.framework.Direction;
import mysqls.framework.Grid;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * A node in a graph.
 */
public interface Node extends GraphElement {
    /**
     * Draw the node.
     *
     * @param pGraphics2D the graphics context
     */
    void draw(Graphics2D pGraphics2D);

    /**
     * Translates the node by a given amount.
     *
     * @param pDeltaX the amount to translate in the x-direction
     * @param pDeltaY the amount to translate in the y-direction
     */
    void translate(double pDeltaX, double pDeltaY);

    /**
     * Tests whether the node contains a point.
     *
     * @param pPoint the point to test
     * @return true if this node contains aPoint
     */
    boolean contains(Point2D pPoint);

    /**
     * Get the best connection point to connect this node with another node.
     * This should be a point on the boundary of the shape of this node.
     *
     * @param pDirection the direction from the center of the bounding rectangle
     *                   towards the boundary
     * @return the recommended connection point
     */
    Point2D getConnectionPoint(Direction pDirection);

    /**
     * Lays out the node and its children.
     *
     * @param pGraph      the ambient graph
     * @param pGraphics2D the graphics context
     * @param pGrid       the grid to snap to
     */
    void layout(Graph pGraph, Graphics2D pGraphics2D, Grid pGrid);

    /**
     * @return A clone of the node.
     */
    Node clone();
}
