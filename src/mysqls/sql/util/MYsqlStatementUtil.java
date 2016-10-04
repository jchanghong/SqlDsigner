/**
 * 实体关系图和sql生产的实现
 */
package mysqls.sql.util;

import mysqls.framework.PersistenceService;
import mysqls.sql.entity.EdgeData;
import mysqls.sql.sqlreader.SqlToTable2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 长宏 处理sql语句的工具类
 *
 */
public abstract class MYsqlStatementUtil {

    /**
     * @author 长宏 drop语句在前面
     *
     */
    private static class sqlcompertor implements Comparator<String> {

        /*
         * (non-Javadoc)
         *
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(String o1, String o2) {
            // TODO Auto-generated method stub
            String s1 = o1.toLowerCase();
            String s2 = o2.toLowerCase();
            if (s1.contains("drop") && s2.contains("create")) {
                return -1;

            }
            if (s1.contains("create") && s2.contains("drop")) {
                return 1;

            }
            if (s1.contains("drop") && s2.contains("drop")) {
                for (EdgeData a : PersistenceService.mEdgeDatas) {
                    String s = a.sTable.getName();
                    String e = a.eTable.getName();
                    if (s1.contains(s) && s2.contains(e)) {
                        return -1;
                    }
                    if (s1.contains(e) && s2.contains(s)) {
                        return 1;
                    }
                }

            }
            return 0;
        }

    }

    /**
     * @param sql
     *            所有的sql语句，；分开,已经经过排序的sql语句
     * @return 排序好可以执行的sql语句，a参考b，删除和建立的次序不一样. 先执行所有删除，再执行更新
     */
    public static List<String> getsql2exe(String sql) {
        SqlToTable2.getAllTable(sql);// 只是为了更新medegedatas
        List<String> sqlall = Arrays.stream(sql.split(";")).filter(a -> a.trim().length() > 2)
                .sorted(new sqlcompertor()).collect(Collectors.toList());

        return sqlall;
    }

    /**
     * @param sqList
     *            得到显示的sql，\n.
     * @return
     */
    public static String tostring(Set<String> sqList) {
        StringBuilder builder = new StringBuilder("");
        sqList.stream().forEach(a -> {
            builder.append(a + "\n");
        });
        return builder.toString();
    }
}
