/**
 *
 */
package mysqls.sql.mytest;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * @author jiang
 *
 */
public class MBeanBeanInfo extends SimpleBeanInfo {

	/*
	 * (non-Javadoc)
	 *
	 * @see java.beans.SimpleBeanInfo#getPropertyDescriptors()
	 */
	@Override
	public PropertyDescriptor[] getPropertyDescriptors() {
		// TODO Auto-generated method stub
		// return super.getPropertyDescriptors();
		PropertyDescriptor propertyDescriptor = null;
		try {
			propertyDescriptor = new PropertyDescriptor("name", MBean.class);
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		descriptors = new PropertyDescriptor[] { propertyDescriptor };
		return descriptors;
	}

	private PropertyDescriptor[] descriptors;

}
