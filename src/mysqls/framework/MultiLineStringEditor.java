package mysqls.framework;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.beans.PropertyEditorSupport;
import java.util.HashSet;
import java.util.Set;

/**
 * A property editor for the MultiLineString type.
 */
public class MultiLineStringEditor extends PropertyEditorSupport {
    private static final int ROWS = 5;
    private static final int COLUMNS = 30;

    private static Set<AWTKeyStroke> tab = new HashSet<>(1);
    private static Set<AWTKeyStroke> shiftTab = new HashSet<>(1);

    static {
        MultiLineStringEditor.tab.add(KeyStroke.getKeyStroke("TAB"));
        MultiLineStringEditor.shiftTab.add(KeyStroke.getKeyStroke("shift TAB"));
    }

    @Override
    public boolean supportsCustomEditor() {
        return true;
    }

    @Override
    public Component getCustomEditor() {
        final MultiLineString value = (MultiLineString) getValue();
        final JTextArea textArea = new JTextArea(MultiLineStringEditor.ROWS, MultiLineStringEditor.COLUMNS);

        textArea.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, MultiLineStringEditor.tab);
        textArea.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, MultiLineStringEditor.shiftTab);

        textArea.setText(value.getText());
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent pEvent) {
                value.setText(textArea.getText());
                firePropertyChange();
            }

            @Override
            public void removeUpdate(DocumentEvent pEvent) {
                value.setText(textArea.getText());
                firePropertyChange();
            }

            @Override
            public void changedUpdate(DocumentEvent pEvent) {
            }
        });
        return new JScrollPane(textArea);
    }
}
