
package mysqls.graph;

import java.awt.geom.Point2D;

import mysqls.framework.ArrowHead;
import mysqls.framework.SegmentationStyleFactory;

/**
 * 外检关系
 */
public class AssociationEdge extends ClassRelationshipEdge {
	/**
	 * Possible directionalities for an association.
	 */

	public static enum Directionality {
		// None,
		Start, End,
		// Both
	}

	private Directionality aDirectionality = Directionality.Start;

	/**
	 * Creates an association edge with no labels. and no directionality
	 */
	public AssociationEdge() {
	}

	/**
	 * @param pDirectionality
	 *            The desired directionality.
	 */
	public void setDirectionality(Directionality pDirectionality) {
		aDirectionality = pDirectionality;
	}

	/**
	 * @return The directionality of this association.
	 */
	public Directionality getDirectionality() {
		return aDirectionality;
	}

	@Override
	protected ArrowHead obtainStartArrowHead() {
		if (aDirectionality == Directionality.Start) {
			return ArrowHead.V;
		} else {
			return ArrowHead.NONE;
		}
	}

	@Override
	protected ArrowHead obtainEndArrowHead() {
		if (aDirectionality == Directionality.End) {
			return ArrowHead.V;
		} else {
			return ArrowHead.NONE;
		}
	}

	@Override
	public Point2D[] getPoints() {
		return SegmentationStyleFactory.createHVHStrategy().getPath(getStart(), getEnd());
	}
}
