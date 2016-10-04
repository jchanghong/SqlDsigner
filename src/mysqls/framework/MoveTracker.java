package mysqls.framework;

import mysqls.commands.CompoundCommand;
import mysqls.commands.MoveCommand;
import mysqls.graph.Graph;
import mysqls.graph.GraphElement;
import mysqls.graph.Node;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Tracks the movement of a set of selected graph elements.
 *
 * @author Martin P. Robillard
 */
public class MoveTracker {
    private List<Node> aTrackedNodes = new ArrayList<>();
    private List<Rectangle2D> aOriginalBounds = new ArrayList<>();

    /**
     * Records the elements in pSelectedElements and their position at the time
     * where the method is called.
     *
     * @param pSelectedElements The elements that are being moved. Not null.
     */
    public void startTrackingMove(SelectionList pSelectedElements) {
        assert pSelectedElements != null;

        aTrackedNodes.clear();
        aOriginalBounds.clear();

        for (GraphElement element : pSelectedElements) {
            assert element != null;
            if (element instanceof Node) {
                aTrackedNodes.add((Node) element);
                aOriginalBounds.add(element.getBounds());
            }
        }
    }

    /**
     * Creates and returns a CompoundCommand that represents the movement of all
     * tracked nodes between the time where startTrackingMove was called and the
     * time endTrackingMove was called.
     *
     * @param pGraph The Graph containing the selected elements.
     * @return A CompoundCommand describing the move.
     */
    public CompoundCommand endTrackingMove(Graph pGraph) {
        CompoundCommand command = new CompoundCommand();
        Rectangle2D[] selectionBounds2 = new Rectangle2D[aOriginalBounds.size()];
        int i = 0;
        for (Node node : aTrackedNodes) {
            selectionBounds2[i] = node.getBounds();
            i++;
        }
        for (i = 0; i < aOriginalBounds.size(); i++) {
            double dY = selectionBounds2[i].getY() - aOriginalBounds.get(i).getY();
            double dX = selectionBounds2[i].getX() - aOriginalBounds.get(i).getX();
            if (dX != 0 || dY != 0) {
                command.add(new MoveCommand(pGraph, aTrackedNodes.get(i), dX, dY));
            }
        }
        return command;
    }
}
