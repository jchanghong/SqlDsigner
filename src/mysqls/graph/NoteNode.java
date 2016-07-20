
package mysqls.graph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

import mysqls.framework.Grid;
import mysqls.framework.MultiLineString;

/**
 * A note node in a UML diagram.
 */
public class NoteNode extends RectangularNode {
	private static final int DEFAULT_WIDTH = 60;
	private static final int DEFAULT_HEIGHT = 40;
	private static final Color DEFAULT_COLOR = new Color(0.9f, 0.9f, 0.6f); // pale
																			// yellow
	private static final int FOLD_X = 8;
	private static final int FOLD_Y = 8;

	private MultiLineString aText;

	/**
	 * Construct a note node with a default size and color.
	 */
	public NoteNode() {
		setBounds(new Rectangle2D.Double(0, 0, NoteNode.DEFAULT_WIDTH, NoteNode.DEFAULT_HEIGHT));
		aText = new MultiLineString();
		aText.setJustification(MultiLineString.LEFT);
	}

	@Override
	public void layout(Graph pGraph, Graphics2D pGraphics2D, Grid pGrid) {
		Rectangle2D b = aText.getBounds(pGraphics2D); // getMultiLineBounds(name,
														// g2);
		Rectangle2D bounds = getBounds();
		b = new Rectangle2D.Double(bounds.getX(), bounds.getY(), Math.max(b.getWidth(), NoteNode.DEFAULT_WIDTH),
				Math.max(b.getHeight(), NoteNode.DEFAULT_HEIGHT));
		pGrid.snap(b);
		setBounds(b);
	}

	/**
	 * Gets the value of the text property.
	 * 
	 * @return the text inside the note
	 */
	public MultiLineString getText() {
		return aText;
	}

	/**
	 * Sets the value of the text property.
	 * 
	 * @param pText
	 *            the text inside the note
	 */
	public void setText(MultiLineString pText) {
		aText = pText;
	}

	@Override
	public void draw(Graphics2D pGraphics2D) {
		super.draw(pGraphics2D);
		Color oldColor = pGraphics2D.getColor();
		pGraphics2D.setColor(NoteNode.DEFAULT_COLOR);

		Shape path = getShape();
		pGraphics2D.fill(path);
		pGraphics2D.setColor(oldColor);
		pGraphics2D.draw(path);

		Rectangle2D bounds = getBounds();
		GeneralPath fold = new GeneralPath();
		fold.moveTo((float) (bounds.getMaxX() - NoteNode.FOLD_X), (float) bounds.getY());
		fold.lineTo((float) bounds.getMaxX() - NoteNode.FOLD_X, (float) bounds.getY() + NoteNode.FOLD_X);
		fold.lineTo((float) bounds.getMaxX(), (float) (bounds.getY() + NoteNode.FOLD_Y));
		fold.closePath();
		oldColor = pGraphics2D.getColor();
		pGraphics2D.setColor(pGraphics2D.getBackground());
		pGraphics2D.fill(fold);
		pGraphics2D.setColor(oldColor);
		pGraphics2D.draw(fold);

		aText.draw(pGraphics2D, getBounds());
	}

	@Override
	public Shape getShape() {
		Rectangle2D bounds = getBounds();
		GeneralPath path = new GeneralPath();
		path.moveTo((float) bounds.getX(), (float) bounds.getY());
		path.lineTo((float) (bounds.getMaxX() - NoteNode.FOLD_X), (float) bounds.getY());
		path.lineTo((float) bounds.getMaxX(), (float) (bounds.getY() + NoteNode.FOLD_Y));
		path.lineTo((float) bounds.getMaxX(), (float) bounds.getMaxY());
		path.lineTo((float) bounds.getX(), (float) bounds.getMaxY());
		path.closePath();
		return path;
	}

	@Override
	public NoteNode clone() {
		NoteNode cloned = (NoteNode) super.clone();
		cloned.aText = aText.clone();
		return cloned;
	}
}
