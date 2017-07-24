/**
 * 实体关系图和sql生产的实现
 */
package mysqls.sql.entity;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * @author 长宏 表列的属性列表。javabean
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
            // PropertyDescriptor fori = new PropertyDescriptor("foreignKey",
            // TableColumn.class);
            PropertyDescriptor notn = new PropertyDescriptor("notnull", TableColumn.class);
            PropertyDescriptor auto = new PropertyDescriptor("autoadd", TableColumn.class);
            PropertyDescriptor uniq = new PropertyDescriptor("unique", TableColumn.class);
            PropertyDescriptor def = new PropertyDescriptor("defaultvalues", TableColumn.class);
            propertyDescriptors = new PropertyDescriptor[]{name, tyPropertyDescriptor, pri, notn, uniq, def, auto};
        } catch (IntrospectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        // TODO Auto-generated method stub
        // super.getPropertyDescriptors();
        return propertyDescriptors;

    }

    private PropertyDescriptor[] propertyDescriptors;

}
