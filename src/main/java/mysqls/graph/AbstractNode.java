package mysqls.graph;

import mysqls.framework.Grid;

import java.awt.*;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;

/**
 * A class that supplies convenience implementations for a number of methods in
 * the Node interface.
 */
public abstract class AbstractNode implements Node {
    public static final int SHADOW_GAP = 4;

    private static final Color SHADOW_COLOR = Color.LIGHT_GRAY;

    /**
     * Constructs a node.
     */
    public AbstractNode() {
    }

    @Override
    public AbstractNode clone() {
        try {
            AbstractNode cloned = (AbstractNode) super.clone();
            return cloned;
        } catch (CloneNotSupportedException exception) {
            return null;
        }
    }

    @Override
    public void translate(double pDeltaX, double pDeltaY) {
    }

    @Override
    public void layout(Graph pGraph, Graphics2D pGraphics2D, Grid pGrid) {
    }

    @Override
    public void draw(Graphics2D pGraphics2D) {
//		Shape shape = getShape();
//		if (shape == null) {
//			return;
//		}
        /*
		 * Area shadow = new Area(shape);
		 * shadow.transform(AffineTransform.getTranslateInstance(SHADOW_GAP,
		 * SHADOW_GAP)); shadow.subtract(new Area(shape));
		 */
//		Color oldColor = pGraphics2D.getColor();
//		pGraphics2D.translate(AbstractNode.SHADOW_GAP, AbstractNode.SHADOW_GAP);
//		pGraphics2D.setColor(Color.WHITE);
//		pGraphics2D.fill(shape);
//		pGraphics2D.translate(-AbstractNode.SHADOW_GAP, -AbstractNode.SHADOW_GAP);
//		pGraphics2D.setColor(pGraphics2D.getBackground());
//		pGraphics2D.fill(shape);
//		pGraphics2D.setColor(oldColor);
    }

    /**
     * @return the shape to be used for computing the drop shadow
     */
    public Shape getShape() {
        return null;
    }

    /**
     * Adds a persistence delegate to a given encoder.
     *
     * @param pEncoder the encoder to which to add the delegate
     */
    public static void setPersistenceDelegate(Encoder pEncoder) {
        pEncoder.setPersistenceDelegate(AbstractNode.class, new DefaultPersistenceDelegate() {
            @Override
            protected void initialize(Class<?> pType, Object pOldInstance, Object pNewInstance, Encoder pOut) {
                super.initialize(pType, pOldInstance, pNewInstance, pOut);
            }
        });
    }
}
