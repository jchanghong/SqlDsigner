/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.entity;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 长宏
 *
 */
public class TableColistEditor extends PropertyEditorSupport {

	private List<String> list;
	{
		list = new ArrayList<>();
		list.add("11");
		list.add("222");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.beans.PropertyEditorSupport#getValue()
	 */
	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return super.getValue();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.beans.PropertyEditorSupport#isPaintable()
	 */
	@Override
	public boolean isPaintable() {
		// TODO Auto-generated method stub
		return super.isPaintable();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.beans.PropertyEditorSupport#paintValue(java.awt.Graphics,
	 * java.awt.Rectangle)
	 */
	@Override
	public void paintValue(Graphics gfx, Rectangle box) {
		// TODO Auto-generated method stub
		super.paintValue(gfx, box);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.beans.PropertyEditorSupport#getAsText()
	 */
	@Override
	public String getAsText() {
		// TODO Auto-generated method stub
		// return super.getAsText();
		List<TableColumn> columns = (List<TableColumn>) getValue();
		if (columns.size() == 0) {
			return "null";
		}
		return (columns.get(0) == null) ? "null" : columns.get(0).getName();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		List<TableColumn> av = (List<TableColumn>) getValue();
		av.add(new TableColumn(text));
		setValue(av);
		// super.setAsText(text);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.beans.PropertyEditorSupport#getTags()
	 */
	@Override
	public String[] getTags() {
		// TODO Auto-generated method stub
		return (String[]) list.toArray(new String[list.size()]);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.beans.PropertyEditorSupport#getCustomEditor()
	 */
	@Override
	public Component getCustomEditor() {
		// TODO Auto-generated method stub
		return super.getCustomEditor();
	}

}
