/**
 * 实体关系图和sql生产的实现
 */
package mysqls.sql.entity;

import java.util.Comparator;

/**
 * @author 长宏
 *
 */
public class TableCompertor implements Comparator<Table> {

    /*
     * (non-Javadoc)
     *
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Table o1, Table o2) {
        // TODO Auto-generated method stub
        if (o1.hasForeigrnKey()) {
            return 1;
        }
        if (o2.hasForeigrnKey()) {
            return -1;

        }

        return o1.getName().compareTo(o2.getName());
    }

}
