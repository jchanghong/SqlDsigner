package mysqls.commands;

import mysqls.graph.Graph;
import mysqls.graph.Node;

/**
 * Represents the removal of a node from the graph.
 *
 * @author Martin P. Robillard
 */
public class DeleteNodeCommand extends GraphElementRelatedCommand {
	/**
	 * Creates the command.
	 * 
	 * @param pGraph
	 *            The graph the node was removed from.
	 * @param pNode
	 *            The node removed.
	 */
	public DeleteNodeCommand(Graph pGraph, Node pNode) {
		super(pGraph, pNode);
	}

	/**
	 * Undoes the command and adds/deletes the node.
	 */
	@Override
	public void undo() {
		aGraph.insertNode((Node) aElement);
	}

	/**
	 * Performs the command and adds/deletes the node.
	 */
	@Override
	public void execute() {
		aGraph.removeNode((Node) aElement);
	}
}
