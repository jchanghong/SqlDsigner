/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.entity;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 长宏
 *
 */
public final class AttubuteList {
	/**
	 *
	 */
	private AttubuteList() {
		// TODO Auto-generated constructor stub
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

	public final static List<String> namelist = new ArrayList<>();
	public final static List<PropertyDescriptor> pdList = new ArrayList<>();
	static {
		PropertyDescriptor[] pd = null;
		try {
			pd = Introspector.getBeanInfo(TableColumn.class).getPropertyDescriptors();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String temps = "";
		PropertyDescriptor temp = null;
		for (int i = 0; i < pd.length; i++) {
			String name = pd[i].getName();

			AttubuteList.namelist.add(name);
			AttubuteList.pdList.add(pd[i]);
			if (name.equals("name")) {
				temps = AttubuteList.namelist.get(0);
				temp = AttubuteList.pdList.get(0);
				AttubuteList.namelist.set(0, name);
				AttubuteList.pdList.set(0, pd[i]);
				AttubuteList.namelist.set(i, temps);
				AttubuteList.pdList.set(i, temp);

			}

		}
	}
}
