/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.entity;

import java.awt.Component;
import java.beans.PropertyEditorSupport;

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
		// meditTable = new EditTable();
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
		final Columnlist columnlist = (Columnlist) getValue();
		meditTable = new EditTable(columnlist.getMtTable());
		// meditTable.setRowchangelister(new Rowchangelister() {
		//
		// @Override
		// public void onremove(RowPanel panel) {
		// // TODO Auto-generated method stub
		// list.remove(panel.getmTableColumn());
		// firePropertyChange();
		//
		// }
		//
		// @Override
		// public void onadd(RowPanel panel) {
		// // TODO Auto-generated method stub
		// list.add(panel.getmTableColumn());
		// firePropertyChange();
		//
		// }
		// });
		// for (int i = 0; i < list.size(); i++) {
		// meditTable.addrow(new RowPanel(list.get(i), meditTable));
		// }
		return meditTable;
	}

}
