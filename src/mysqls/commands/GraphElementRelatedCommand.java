package mysqls.commands;

import mysqls.graph.Graph;
import mysqls.graph.GraphElement;

/**
 * 这些是为了实现copy等这些操作的A command that involves a single graph element.
 *
 * @author Martin P. Robillard
 */
abstract class GraphElementRelatedCommand implements Command {
	protected GraphElement aElement;
	protected Graph aGraph;

	/**
	 * Creates the command.
	 *
	 * @param pGraph
	 *            The target graph.
	 * @param pElement
	 *            The related element
	 */
	protected GraphElementRelatedCommand(Graph pGraph, GraphElement pElement) {
		aGraph = pGraph;
		aElement = pElement;
	}
}
