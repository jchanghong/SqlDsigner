package mysqls.graph;

import mysqls.framework.Direction;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * A dotted line that connects a note to its attachment.
 */
public class NoteEdge extends AbstractEdge {
    private static final int DEGREES_180 = 180;
    private static final Stroke DOTTED_STROKE = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
            0.0f, new float[]{3.0f, 3.0f}, 0.0f);

    @Override
    public void draw(Graphics2D pGraphics2D) {
        Stroke oldStroke = pGraphics2D.getStroke();
        pGraphics2D.setStroke(NoteEdge.DOTTED_STROKE);
        pGraphics2D.draw(getShape());
        pGraphics2D.setStroke(oldStroke);
    }

    @Override
    public Line2D getConnectionPoints() {
        Rectangle2D start = getStart().getBounds();
        Rectangle2D end = getEnd().getBounds();
        Direction direction = new Direction(end.getCenterX() - start.getCenterX(),
                end.getCenterY() - start.getCenterY());
        return new Line2D.Double(getStart().getConnectionPoint(direction),
                getEnd().getConnectionPoint(direction.turn(NoteEdge.DEGREES_180)));
    }

    @Override
    protected Shape getShape() {
        GeneralPath path = new GeneralPath();
        Line2D conn = getConnectionPoints();
        path.moveTo((float) conn.getX1(), (float) conn.getY1());
        path.lineTo((float) conn.getX2(), (float) conn.getY2());
        return path;
    }
}
