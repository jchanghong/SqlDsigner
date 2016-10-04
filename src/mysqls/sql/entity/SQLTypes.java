/**
 *
 */
package mysqls.sql.entity;

/**
 * @author jiang 目前没有用到，预计以后有用
 */
public class SQLTypes {
    /**
     *
     */
    private SQLTypes() {
        // TODO Auto-generated constructor stub
    }

    public static String INT = "int";
    public static String LONG = "long";

    public static String VARCHAR(int l) {
        StringBuilder buffer = new StringBuilder("");
        buffer.append("VARCHAR");
        buffer.append("(" + l);
        buffer.append(")");
        return buffer.toString();
    }

}
