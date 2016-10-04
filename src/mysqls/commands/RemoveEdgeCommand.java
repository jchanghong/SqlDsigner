package mysqls.commands;

import mysqls.graph.Edge;
import mysqls.graph.Graph;

/**
 * 这些是为了实现copy等这些操作的Represents the removal of an edge to the graph.
 *
 * @author Martin P. Robillard
 */
public class RemoveEdgeCommand extends GraphElementRelatedCommand {
    /**
     * Creates the command.
     *
     * @param pGraph The target graph.
     * @param pEdge  The related edge.
     */
    public RemoveEdgeCommand(Graph pGraph, Edge pEdge) {
        super(pGraph, pEdge);
    }

    /**
     * Undoes the command and adds/deletes the edge.
     */
    @Override
    public void undo() {
        assert aElement instanceof Edge;
        aGraph.insertEdge((Edge) aElement);
    }

    /**
     * Performs the command and adds/deletes the edge.
     */
    @Override
    public void execute() {
        assert aElement instanceof Edge;
        aGraph.removeEdge((Edge) aElement);
    }
}
