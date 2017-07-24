/**
 * 实体关系图和sql生产的实现
 */
package mysqls.sql.sqlreader;

import java.util.regex.Pattern;

/**
 * @author ，号前面的句子，可以是列定义，可以是外键定义,最后一列可能是）结尾
 *
 */
public class ColumnStatementUtil {

    public static boolean isColumnStatement(String statement) {

        return !ColumnStatementUtil.isConstraint(statement);
    }

    public static boolean isConstraint(String statement) {
        String pString = "\\s*CONSTRAINT\\s+";
        Pattern pattern = Pattern.compile(pString, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(statement).find();
    }

}
