/**
 *
 */
package mysqls.sql.databaseserver2;

import java.util.List;

/**
 * @author jiang 不现实的跟节点,可以得到所有的数据库
 */
public class MYtreeNodeRoot extends MYtreeNode {

    /**
     *
     */
    public MYtreeNodeRoot(String names) {
        super();
        name = names;
        // TODO Auto-generated constructor stub
    }

    public List<MYtreeNodeDB> getdbs() {
        return DataBaseUtil.getdbs();

    }

}
