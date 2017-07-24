package mysqls.framework;

/**
 * A property editor for the ArrowHead type.
 */
public class ArrowHeadEditor extends PropertySelector {
    private static final String[] NAMES = {"None", "Triangle", "V", "Diamond", "Black Diamond"};
    private static final Object[] VALUES = {ArrowHead.NONE, ArrowHead.TRIANGLE, ArrowHead.V, ArrowHead.DIAMOND,
            ArrowHead.BLACK_DIAMOND};

    /**
     * Creates a default editor.
     */
    public ArrowHeadEditor() {
        super(ArrowHeadEditor.NAMES, ArrowHeadEditor.VALUES);
    }
}
