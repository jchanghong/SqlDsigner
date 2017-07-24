package mysqls.framework;

/**
 * A property editor for the LineStyle type.
 */
public class LineStyleEditor extends PropertySelector {
    private static final String[] NAMES = {"Solid", "Dotted"};
    private static final Object[] VALUES = {LineStyle.SOLID, LineStyle.DOTTED};

    /**
     * Creates a line style editor with the default values.
     */
    public LineStyleEditor() {
        super(LineStyleEditor.NAMES, LineStyleEditor.VALUES);
    }
}
