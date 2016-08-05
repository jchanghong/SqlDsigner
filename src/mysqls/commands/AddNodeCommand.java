package mysqls.commands;

import mysqls.graph.Graph;
import mysqls.graph.Node;

/**
 * 这些是为了实现copy等这些操作的Represents the addition of a node to the graph.
 *
 * @author Martin P. Robillard
 */
public class AddNodeCommand extends GraphElementRelatedCommand {
	/**
	 * Creates the command.
	 *
	 * @param pGraph
	 *            The graph the node was added to.
	 * @param pNode
	 *            The node added.
	 */
	public AddNodeCommand(Graph pGraph, Node pNode) {
		super(pGraph, pNode);
	}

	/**
	 * @see uestc.uml.sql.commands.Command#undo()
	 */
	@Override
	public void undo() {
		assert aElement instanceof Node;
		aGraph.removeNode((Node) aElement);
		aGraph.layout();
	}

	/**
	 * Performs the command and adds/deletes the node.
	 */
	@Override
	public void execute() {
		assert aElement instanceof Node;
		aGraph.insertNode((Node) aElement);
		aGraph.layout();
	}
}
