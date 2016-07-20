
package mysqls.framework;

import java.awt.BasicStroke;
import java.awt.Stroke;

/**
 * This class defines line styles of various shapes.
 */
public final class LineStyle {
	public static final LineStyle SOLID = new LineStyle();
	public static final LineStyle DOTTED = new LineStyle();

	private static final Stroke SOLID_STROKE = new BasicStroke();
	private static final Stroke DOTTED_STROKE = new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,
			10.0f, new float[] { 3.0f, 3.0f }, 0.0f);

	private LineStyle() {
	}

	/**
	 * Gets a stroke with which to draw this line style.
	 * 
	 * @return the stroke object that strokes this line style
	 */
	public Stroke getStroke() {
		if (this == LineStyle.DOTTED) {
			return LineStyle.DOTTED_STROKE;
		}
		return LineStyle.SOLID_STROKE;
	}

	@Override
	public String toString() {
		if (this == LineStyle.SOLID) {
			return "SOLID";
		} else if (this == LineStyle.DOTTED) {
			return "DOTTED";
		} else {
			return "Unknown";
		}
	}
}
