/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.databaseserver2;

import java.util.Vector;

/**
 * @author 长宏
 *
 */
public abstract class MYtableUtil {
	/**
	 * @param all
	 * @return所有元素都是null。或者为空
	 */
	public static boolean isallnull(Vector<Object> all) {
		for (Object object : all) {
			if (object != null && !object.toString().equals("null")) {
				return false;
			}
		}
		return true;

	}

}
