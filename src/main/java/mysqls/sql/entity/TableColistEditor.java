/**
 * 实体关系图和sql生产的实现
 */
package mysqls.sql.entity;

import java.awt.*;
import java.beans.PropertyEditorSupport;

/**
 * @author 长宏 javabean编辑器
 *
 */
public class TableColistEditor extends PropertyEditorSupport {

    private EditTable meditTable;

    public TableColistEditor() {
        super();
    }

    @Override
    public boolean supportsCustomEditor() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public Component getCustomEditor() {
        // TODO Auto-generated method stub
        final Columnlist columnlist = (Columnlist) getValue();
        meditTable = new EditTable(columnlist.getMtTable());
        return meditTable;
    }

}
