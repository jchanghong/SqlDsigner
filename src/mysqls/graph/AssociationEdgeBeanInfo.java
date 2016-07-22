/**
 *
 */
package mysqls.graph;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

import mysqls.framework.DirectionalityEditor;
import mysqls.framework.MYedgeEditor;

/**
 * @author jiang
 *
 */
public class AssociationEdgeBeanInfo extends SimpleBeanInfo {
	private PropertyDescriptor[] prop;

	/**
	 *
	 */
	public AssociationEdgeBeanInfo() {
		// TODO Auto-generated constructor stub
		try {
			PropertyDescriptor dire = new PropertyDescriptor("directionality", AssociationEdge.class);
			dire.setPropertyEditorClass(DirectionalityEditor.class);
			PropertyDescriptor start = new PropertyDescriptor("startLabel", AssociationEdge.class);
			start.setPropertyEditorClass(MYedgeEditor.class);

			PropertyDescriptor enDescriptor = new PropertyDescriptor("endLabel", AssociationEdge.class);
			enDescriptor.setPropertyEditorClass(MYedgeEditor.class);
			prop = new PropertyDescriptor[] { dire, start, enDescriptor };
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.beans.SimpleBeanInfo#getPropertyDescriptors()
	 */
	@Override
	public PropertyDescriptor[] getPropertyDescriptors() {

		// TODO Auto-generated method stub
		super.getPropertyDescriptors();
		return prop;
	}

}
