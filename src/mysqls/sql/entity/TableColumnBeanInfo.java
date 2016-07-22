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
public class TableColumnBeanInfo extends SimpleBeanInfo {

	/**
	 *
	 */
	public TableColumnBeanInfo() {
		// TODO Auto-generated constructor stub
		try {
			PropertyDescriptor name = new PropertyDescriptor("name", TableColumn.class);
			PropertyDescriptor tyPropertyDescriptor = new PropertyDescriptor("type", TableColumn.class);
			PropertyDescriptor pri = new PropertyDescriptor("primarykey", TableColumn.class);
			PropertyDescriptor fori = new PropertyDescriptor("foreignKey", TableColumn.class);
			PropertyDescriptor notn = new PropertyDescriptor("notnull", TableColumn.class);
			PropertyDescriptor uniq = new PropertyDescriptor("unique", TableColumn.class);
			PropertyDescriptor def = new PropertyDescriptor("defaultvalues", TableColumn.class);
			propertyDescriptors = new PropertyDescriptor[] { name, tyPropertyDescriptor, pri, fori, notn, uniq, def };
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// private String name;
	// private String type;
	// private boolean primarykey;
	// private boolean foreignKey;
	// private boolean notnull;
	// private boolean unique;
	// private String defaultvalues;
	// private Table forigntable;
	// private TableColumn forigncolumn;
	/*
	 * (non-Javadoc) private String name; private String type; private boolean
	 * primarykey; private boolean foreignKey; private boolean notnull; private
	 * boolean unique; private String defaultvalues; private Table forigntable;
	 * private TableColumn forigncolumn;
	 *
	 * @see java.beans.SimpleBeanInfo#getPropertyDescriptors()
	 */
	@Override
	public PropertyDescriptor[] getPropertyDescriptors() {
		// TODO Auto-generated method stub
		// super.getPropertyDescriptors();
		return propertyDescriptors;

	}

	private PropertyDescriptor[] propertyDescriptors;

}
