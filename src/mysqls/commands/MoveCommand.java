package mysqls.commands;

import mysqls.graph.Graph;
import mysqls.graph.Node;

/**
 * 这些是为了实现copy等这些操作的Stores the moving of a node.
 *
 * @author EJBQ
 */
public class MoveCommand implements Command {
	private Node aNode;
	private Graph aGraph;
	private double aDX;
	private double aDY;

	/**
	 * Creates the command.
	 *
	 * @param pGraph
	 *            The panel being moved on
	 * @param pNode
	 *            The node being moved
	 * @param pDX
	 *            The amount moved horizontally
	 * @param pDY
	 *            The amount moved vertically
	 */
	public MoveCommand(Graph pGraph, Node pNode, double pDX, double pDY) {
		aGraph = pGraph;
		aNode = pNode;
		aDX = pDX;
		aDY = pDY;
	}

	/**
	 * Undoes the command and moves the node back where it came from.
	 */
	@Override
	public void undo() {
		aNode.translate(-aDX, -aDY);
		aGraph.layout();
	}

	/**
	 * Performs the command and moves the node.
	 */
	@Override
	public void execute() {
		aNode.translate(aDX, aDY);
		aGraph.layout();
	}

}
