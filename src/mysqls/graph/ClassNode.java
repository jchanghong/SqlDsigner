
package mysqls.graph;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import mysqls.framework.MultiLineString;
import mysqls.sql.entity.Table;

/**
 * A class node in a class diagram.
 */
public class ClassNode extends InterfaceNode {
	public Table mTable;
	private MultiLineString aAttributes;

	/**
	 * Construct a class node with a default size. 不要直接调用这个函数 table就是数据非clone
	 */
	public ClassNode(Table table) {
		mTable = table;
		mTable.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub

				// aAttributes.setText(mTable.getnodeAttu());
				// aName.setText(mTable.getnodeName());
				// setAttributes(aAttributes);
				// setName(aName);
				System.out.println("ClassNode(Table table)propertyChange");
			}
		});
		aAttributes = mTable.getnodeAttu();
		aAttributes.setJustification(MultiLineString.LEFT);
		aName = mTable.getnodeName();
	}

	@Override
	public void draw(Graphics2D pGraphics2D) {
		super.draw(pGraphics2D);
		double midHeight = computeMiddle(pGraphics2D).getHeight();
		double bottomHeight = computeBottom(pGraphics2D).getHeight();
		Rectangle2D top = new Rectangle2D.Double(getBounds().getX(), getBounds().getY(), getBounds().getWidth(),
				getBounds().getHeight() - midHeight - bottomHeight);
		Rectangle2D mid = new Rectangle2D.Double(top.getX(), top.getMaxY(), top.getWidth(), midHeight);
		aAttributes.draw(pGraphics2D, mid);
	}

	/**
	 * Sets the attributes property value.
	 *
	 * @param pNewValue
	 *            the attributes of this class
	 */
	public void setAttributes(MultiLineString pNewValue) {
		aAttributes = pNewValue;
		pNewValue.toString();
	}

	/**
	 * Gets the attributes property value.
	 *
	 * @return the attributes of this class
	 */
	public MultiLineString getAttributes() {
		return aAttributes;
	}

	/**
	 * @return True if the node requires a bottom compartment.
	 */
	@Override
	protected boolean needsMiddleCompartment() {
		return !aAttributes.getText().isEmpty();
	}

	/**
	 * @param pGraphics2D
	 *            The graphics context
	 * @return The area of the middle compartment. The x and y values are
	 *         meaningless.
	 */
	@Override
	protected Rectangle2D computeMiddle(Graphics2D pGraphics2D) {
		if (!needsMiddleCompartment()) {
			return new Rectangle2D.Double(0, 0, 0, 0);
		}

		Rectangle2D attributes = aAttributes.getBounds(pGraphics2D);
		attributes.add(
				new Rectangle2D.Double(0, 0, InterfaceNode.DEFAULT_WIDTH, InterfaceNode.DEFAULT_COMPARTMENT_HEIGHT));
		return attributes;
	}

	@Override
	public ClassNode clone() {
		ClassNode cloned = (ClassNode) super.clone();
		cloned.aAttributes = aAttributes.clone();
		cloned.mTable = mTable.clone();
		return cloned;
	}
}
