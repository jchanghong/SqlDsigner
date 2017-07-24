package mysqls.graph;

import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by jiang on 2016/10/3 0003.
 */
public class Rect_Util {
    public static RoundRectangle2D toround(Rectangle2D r) {
        return new RoundRectangle2D.Double(r.getX(), r.getY(), r.getWidth(), r.getHeight(), 10, 10);
    }

    public static Rectangle2D torect(RoundRectangle2D r) {
        return new Rectangle2D.Double(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

}
