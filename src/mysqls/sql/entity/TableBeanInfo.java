/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.entity;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * @author 长宏
 *
 */
public class TableBeanInfo extends SimpleBeanInfo {

	// private String name;
	// private List<TableColumn> Columnlist;

	/**
	 *
	 */
	public TableBeanInfo() {
		// TODO Auto-generated constructor stub
		try {
			PropertyDescriptor nanme = new PropertyDescriptor("name", Table.class);
			PropertyDescriptor list = new PropertyDescriptor("columnlist", Table.class);
			list.setPropertyEditorClass(TableColistEditor.class);
			propertyDescriptors = new PropertyDescriptor[] { nanme, list };
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
		return propertyDescriptors;
	}

	private PropertyDescriptor[] propertyDescriptors;

}
