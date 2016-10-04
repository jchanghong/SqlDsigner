/**
 * 实体关系图和sql生产的实现
 */
package mysqls.contanst;

import mysqls.sql.entity.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 长宏 定义全局可访问的常量，避免方法调用
 *
 */
public class TabeleConstant {
    public static List<Table> alltables;

    static {
        TabeleConstant.alltables = new ArrayList<>();
    }

}
