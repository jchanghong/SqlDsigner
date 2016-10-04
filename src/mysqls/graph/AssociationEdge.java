package mysqls.graph;

import mysqls.framework.ArrowHead;
import mysqls.framework.SegmentationStyleFactory;
import mysqls.sql.entity.TableColumn;

import java.awt.geom.Point2D;

/**
 * 外检关系,数据在tableccolumn里面，这里主要是显示
 */
public class AssociationEdge extends ClassRelationshipEdge {
    public enum Directionality {
        // None,
        Start, End,
        // Both
    }

    public TableColumn sTableColumn;

    public TableColumn eTableColumn;

    private Directionality aDirectionality = Directionality.End;

    /**
     * Creates an association edge with no labels. and no directionality
     */
    public AssociationEdge() {
        sTableColumn = new TableColumn("");
        eTableColumn = new TableColumn("");
    }

    /**
     * @return The directionality of this association.
     */
    public Directionality getDirectionality() {
        return aDirectionality;
    }

    /**
     * @return the eTableColumn
     */
    public TableColumn geteTableColumn() {
        return this.eTableColumn;
    }

    @Override
    public Point2D[] getPoints() {
        return SegmentationStyleFactory.createHVHStrategy().getPath(getStart(), getEnd());
    }

    /**
     * @return the sTableColumn
     */
    public TableColumn getsTableColumn() {
        return this.sTableColumn;
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
    protected ArrowHead obtainStartArrowHead() {
        if (aDirectionality == Directionality.Start) {
            return ArrowHead.V;
        } else {
            return ArrowHead.NONE;
        }
    }

    /**
     * @param pDirectionality The desired directionality.
     */
    public void setDirectionality(Directionality pDirectionality) {
        aDirectionality = pDirectionality;
        if (aDirectionality == Directionality.Start) {
            sTableColumn.setForeignKey(false);
            eTableColumn.setForeignKey(true);
        } else {
            eTableColumn.setForeignKey(false);
            sTableColumn.setForeignKey(true);
        }
    }

    /**
     * @param eTableColumn the eTableColumn to set
     */
    public void seteTableColumn(TableColumn eTableColumn) {
        this.eTableColumn = eTableColumn;
    }

    /**
     * @param sTableColumn the sTableColumn to set
     */
    public void setsTableColumn(TableColumn sTableColumn) {
        this.sTableColumn = sTableColumn;
    }
}
