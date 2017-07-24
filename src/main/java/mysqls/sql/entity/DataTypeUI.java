/**
 * 实体关系图和sql生产的实现
 */
package mysqls.sql.entity;

/**
 * @author 长宏 datatype 和string的转化
 *
 */
public class DataTypeUI {

    public static void main(String[] args) {
        System.out.println(DataTypeUI.toenum("varchar(0)"));
    }

    /**
     *
     */
    private DataTypeUI() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param type"int(22)'
     * @return
     */
    public static DataType toenum(String type) {
        String mString = type.toUpperCase();
        if (mString.startsWith(DataType.INT.toString())) {
            return DataType.INT;
        }
        if (mString.startsWith(DataType.DOUBLE.toString())) {
            return DataType.DOUBLE;
        }
        if (mString.startsWith(DataType.DATETIME.toString())) {
            return DataType.DATETIME;
        }
        if (mString.startsWith(DataType.VARCHAR.toString())) {
            return DataType.VARCHAR;
        }
        if (mString.startsWith(DataType.TEXT.toString())) {
            return DataType.TEXT;
        }
        if (mString.startsWith(DataType.BLOB.toString())) {
            return DataType.BLOB;
        }

        return DataType.TIMESTAMP;
    }

    public static String tostring(DataType dataType) {
        switch (dataType) {
            case INT:
                return dataType.toString() + "(11)";
            case VARCHAR:
                return dataType.toString() + "(30)";
            case DOUBLE:
            case TIMESTAMP:
            case DATETIME:
            case BLOB:
            case TEXT:
                return dataType.toString();
            default:
                return dataType.toString();
        }

    }

}
