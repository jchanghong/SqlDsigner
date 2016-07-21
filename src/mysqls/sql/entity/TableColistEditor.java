/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.entity;

import java.awt.Component;
import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 长宏
 *
 */
public class TableColistEditor extends PropertyEditorSupport {

	private EditTable meditTable;

	/**
	 *
	 */
	public TableColistEditor() {
		// TODO Auto-generated constructor stub
		super();
		meditTable = new EditTable();
	}

	private List<String> list;
	{
		list = new ArrayList<>();
		list.add("11");
		list.add("222");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.beans.PropertyEditorSupport#supportsCustomEditor()
	 */
	@Override
	public boolean supportsCustomEditor() {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.beans.PropertyEditorSupport#getCustomEditor()
	 */
	@Override
	public Component getCustomEditor() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		ArrayList<TableColumn> list = (ArrayList<TableColumn>) getValue();
		for (int i = 0; i < list.size(); i++) {
			meditTable.addrow();
		}
		return meditTable;
	}

}
