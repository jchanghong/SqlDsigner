/**
 * 实体关系图和sql生产的实现
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

    public static List<String> namelist = null;
    public static List<PropertyDescriptor> pdList = null;

    public static String getName(String property) {
        String string = "null";
        if (property.equals("name")) {
            return "列名";
        }
        if (property.equals("type")) {
            return "类型";
        }
        if (property.equals("primarykey")) {
            return "主键";
        }
        if (property.equals("notnull")) {
            return "非空";
        }
        if (property.equals("unique")) {
            return "唯一";
        }
        if (property.equals("defaultvalues")) {
            return "默认值";
        }
        if (property.equals("foreignKey")) {
            return "外键";
        }
        if (property.equals("autoadd")) {
            return "自增";
        }

        return string;
    }

    static {
        PropertyDescriptor[] pd = null;
        try {
            pd = Introspector.getBeanInfo(TableColumn.class).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        AttubuteList.namelist = new ArrayList<>(pd.length);
        AttubuteList.pdList = new ArrayList<>(pd.length);
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
