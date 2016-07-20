
package mysqls.graph;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import mysqls.framework.Grid;
import mysqls.framework.MultiLineString;

/**
 * An interface node in a class diagram that can be composed of three
 * compartments: top (for the name), middle (for attributes, normally unused),
 * and bottom (for methods).
 */
public class InterfaceNode extends RectangularNode {
	protected static final int DEFAULT_COMPARTMENT_HEIGHT = 20;
	protected static final int DEFAULT_WIDTH = 100;
	protected static final int DEFAULT_HEIGHT = 60;

	protected MultiLineString aName;
	protected MultiLineString aMethods;

	/**
	 * Construct an interface node with a default size and the text
	 * <<interface>>.
	 */
	public InterfaceNode() {
		aName = new MultiLineString(true);
		aName.setText("\u00ABinterface\u00BB\n");
		aName.setJustification(MultiLineString.CENTER);
		aMethods = new MultiLineString();
		aMethods.setJustification(MultiLineString.LEFT);
		setBounds(new Rectangle2D.Double(0, 0, InterfaceNode.DEFAULT_WIDTH, InterfaceNode.DEFAULT_HEIGHT));
	}

	@Override
	public void draw(Graphics2D pGraphics2D) {
		super.draw(pGraphics2D);
		double midHeight = computeMiddle(pGraphics2D).getHeight();
		double bottomHeight = computeBottom(pGraphics2D).getHeight();
		Rectangle2D top = new Rectangle2D.Double(getBounds().getX(), getBounds().getY(), getBounds().getWidth(),
				getBounds().getHeight() - midHeight - bottomHeight);
		pGraphics2D.draw(top);
		aName.draw(pGraphics2D, top);
		Rectangle2D mid = new Rectangle2D.Double(top.getX(), top.getMaxY(), top.getWidth(), midHeight);
		pGraphics2D.draw(mid);
		Rectangle2D bot = new Rectangle2D.Double(top.getX(), mid.getMaxY(), top.getWidth(), bottomHeight);
		pGraphics2D.draw(bot);
		aMethods.draw(pGraphics2D, bot);
	}

	/**
	 * The top is computed to be at least the default node size.
	 * 
	 * @param pGraphics2D
	 *            The graphics context
	 * @return The area of the top compartment
	 */
	protected Rectangle2D computeTop(Graphics2D pGraphics2D) {
		Rectangle2D top = aName.getBounds(pGraphics2D);

		double minHeight = InterfaceNode.DEFAULT_COMPARTMENT_HEIGHT;
		if (!needsMiddleCompartment() && !needsBottomCompartment()) {
			minHeight = InterfaceNode.DEFAULT_HEIGHT;
		} else if (needsMiddleCompartment() ^ needsBottomCompartment()) {
			minHeight = 2 * InterfaceNode.DEFAULT_COMPARTMENT_HEIGHT;
		}
		top.add(new Rectangle2D.Double(0, 0, InterfaceNode.DEFAULT_WIDTH, minHeight));

		return top;
	}

	/**
	 * @param pGraphics2D
	 *            The graphics context
	 * @return The area of the middle compartment. The x and y values are
	 *         meaningless.
	 */
	protected Rectangle2D computeMiddle(Graphics2D pGraphics2D) {
		return new Rectangle2D.Double(0, 0, 0, 0);
	}

	/**
	 * @param pGraphics2D
	 *            The graphics context
	 * @return The area of the bottom compartment. The x and y values are
	 *         meaningless.
	 */
	protected Rectangle2D computeBottom(Graphics2D pGraphics2D) {
		if (!needsBottomCompartment()) {
			return new Rectangle2D.Double(0, 0, 0, 0);
		}

		Rectangle2D bottom = aMethods.getBounds(pGraphics2D);
		bottom.add(new Rectangle2D.Double(0, 0, InterfaceNode.DEFAULT_WIDTH, InterfaceNode.DEFAULT_COMPARTMENT_HEIGHT));
		return bottom;
	}

	/**
	 * @return True if the node requires a bottom compartment.
	 */
	protected boolean needsMiddleCompartment() {
		return false;
	}

	/**
	 * @return True if the node requires a bottom compartment.
	 */
	protected boolean needsBottomCompartment() {
		return !aMethods.getText().isEmpty();
	}

	@Override
	public void layout(Graph pGraph, Graphics2D pGraphics2D, Grid pGrid) {
		Rectangle2D top = computeTop(pGraphics2D);
		Rectangle2D middle = computeMiddle(pGraphics2D);
		Rectangle2D bottom = computeBottom(pGraphics2D);

		Rectangle2D bounds = new Rectangle2D.Double(getBounds().getX(), getBounds().getY(),
				Math.max(Math.max(top.getWidth(), middle.getWidth()), bottom.getWidth()),
				top.getHeight() + middle.getHeight() + bottom.getHeight());
		pGrid.snap(bounds);
		setBounds(bounds);
	}

	/**
	 * Sets the name property value.
	 * 
	 * @param pName
	 *            the interface name
	 */
	public void setName(MultiLineString pName) {
		aName = pName;
	}

	/**
	 * Gets the name property value.
	 * 
	 * @return the interface name
	 */
	public MultiLineString getName() {
		return aName;
	}

	/**
	 * Sets the methods property value.
	 * 
	 * @param pMethods
	 *            the methods of this interface
	 */
	public void setMethods(MultiLineString pMethods) {
		aMethods = pMethods;
	}

	/**
	 * Gets the methods property value.
	 * 
	 * @return the methods of this interface
	 */
	public MultiLineString getMethods() {
		return aMethods;
	}

	@Override
	public InterfaceNode clone() {
		InterfaceNode cloned = (InterfaceNode) super.clone();
		cloned.aName = aName.clone();
		cloned.aMethods = aMethods.clone();
		return cloned;
	}

}
