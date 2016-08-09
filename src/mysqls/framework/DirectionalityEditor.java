/**
 *
 */
package mysqls.framework;

import mysqls.graph.AssociationEdge;

/**
 * @author jiang
 *
 */
public class DirectionalityEditor extends PropertySelector {
	private static final String[] name = { "反向", "正向" };
	private static final Object[] values = { AssociationEdge.Directionality.Start, AssociationEdge.Directionality.End };

	/**
	 *
	 */
	public DirectionalityEditor() {
		// TODO Auto-generated constructor stub
		super(DirectionalityEditor.name, DirectionalityEditor.values);
	}

}
