package mysqls.graph;

/**
 * An edge that is shaped like a line with up to three segments and with up to
 * three labels and optional directions.
 */
public abstract class ClassRelationshipEdge extends SegmentedLabeledEdge {
    private String aStartLabel = "";
    private String aMiddleLabel = "";
    private String aEndLabel = "";

    @Override
    protected String obtainStartLabel() {
        return aStartLabel;
    }

    @Override
    protected String obtainMiddleLabel() {
        return aMiddleLabel;
    }

    @Override
    protected String obtainEndLabel() {
        return aEndLabel;
    }

    /**
     * @param pLabel The new start label.
     */
    public void setStartLabel(String pLabel) {
        aStartLabel = pLabel;
    }

    /**
     * @param pLabel The new middle label.
     */
    public void setMiddleLabel(String pLabel) {
        aMiddleLabel = pLabel;
    }

    /**
     * @param pLabel The new end label.
     */
    public void setEndLabel(String pLabel) {
        aEndLabel = pLabel;
    }

    /**
     * @return The start label.
     */
    public String getStartLabel() {
        return aStartLabel;
    }

    /**
     * @return The middle label.
     */
    public String getMiddleLabel() {
        return aMiddleLabel;
    }

    /**
     * @return The middle label.
     */
    public String getEndLabel() {
        return aEndLabel;
    }
}
