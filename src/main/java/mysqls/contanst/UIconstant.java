/**
 * 实体关系图和sql生产的实现
 */
package mysqls.contanst;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 长宏 保存所有的server，frame，避免每次重新生成，还可以返回上一层
 *
 */
public class UIconstant {

    public static final String MAIN = "MAIN";
    /**
     * 如何到main界面的,1是自己，2是数据库，3是tree frame，4是mysqlvariable
     */
    public static String WHY_Main = "1";
    public static final String ALLdatable = "alldatables";
    public static final String Framevariable = "VARIABLES";
    public static final String ALLtable = "alltable";
    public static final String viewTABLE = "view";
    public static final String TREEUI = "viewTREE";
    public static Map<String, JFrame> frames;

    static {
        UIconstant.frames = new HashMap<>();
        UIconstant.frames.put(UIconstant.MAIN, null);
        UIconstant.frames.put(UIconstant.Framevariable, null);
        UIconstant.frames.put(UIconstant.ALLdatable, null);
        UIconstant.frames.put(UIconstant.ALLtable, null);
        UIconstant.frames.put(UIconstant.viewTABLE, null);
        UIconstant.frames.put(UIconstant.TREEUI, null);
    }

}
