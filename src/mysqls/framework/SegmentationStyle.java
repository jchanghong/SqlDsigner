package mysqls.framework;

import mysqls.graph.Node;

import java.awt.geom.Point2D;

/**
 * A strategy for drawing a segmented line between two nodes.
 *
 * @author Martin P. Robillard
 */
public interface SegmentationStyle {
    /**
     * Gets the points at which a line joining two nodes is bent according to
     * this strategy.
     *
     * @param pStart the starting node
     * @param pEnd   the ending node
     * @return an array list of points at which to bend the segmented line
     * joining the two nodes
     */
    Point2D[] getPath(Node pStart, Node pEnd);
}
