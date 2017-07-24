package mysqls.framework;

import mysqls.ui_frame.MainFrame;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;

/**
 * Component to be used as tabComponent. Contains a JLabel to show the text and
 * a JButton to close the tab it belongs to
 */
@SuppressWarnings("serial")
public class ButtonTabComponent extends JPanel {
    private static final int TAB_SIZE = 17;
    private static final int LABEL_BORDER = 5;
    private static final int DELTA = 6;
    private static final MouseListener BUTTON_MOUSE_LISTENER = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent pEvent) {
            Component component = pEvent.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        @Override
        public void mouseExited(MouseEvent pEvent) {
            Component component = pEvent.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };
    private final MainFrame aMainFrame;
    private final JInternalFrame aJInternalFrame;
    private final JTabbedPane aPane;

    /**
     * @param pMainFrame      The MainFrame that houses the JTabbedView.
     * @param pJInternalFrame The JInternalFrame that maps to this Button.
     * @param pJTabbedPane    The JTabbedPane where this button will be placed.
     */
    public ButtonTabComponent(MainFrame pMainFrame, JInternalFrame pJInternalFrame,
                              final JTabbedPane pJTabbedPane) {
        // unset default FlowLayout' gaps
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        if (pMainFrame == null) {
            throw new NullPointerException("MainFrame is null");
        }
        aMainFrame = pMainFrame;
        if (pJInternalFrame == null) {
            throw new NullPointerException("GraphFrame is null");
        }
        aJInternalFrame = pJInternalFrame;
        if (pJTabbedPane == null) {
            throw new NullPointerException("TabbedPane is null");
        }
        aPane = pJTabbedPane;
        setOpaque(false);

        // make JLabel read titles from JTabbedPane
        JLabel label = new JLabel() {
            @Override
            public String getText() {
                int i = aPane.indexOfTabComponent(ButtonTabComponent.this);
                if (i != -1) {
                    return aPane.getTitleAt(i);
                }
                return null;
            }
        };

        add(label);
        // add more space between the label and the button
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, ButtonTabComponent.LABEL_BORDER));
        // tab button
        JButton button = new TabButton();
        add(button);
        // add more space to the top of the component
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }

    private class TabButton extends JButton implements ActionListener {
        TabButton() {
            int size = ButtonTabComponent.TAB_SIZE;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("close this tab");
            // Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());
            // Make it transparent
            setContentAreaFilled(false);
            // No need to be focusable
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            // Making nice rollover effect
            // we use the same listener for all buttons
            addMouseListener(ButtonTabComponent.BUTTON_MOUSE_LISTENER);
            setRolloverEnabled(true);
            // Close the proper tab by clicking the button
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent pEvent) {
            int i = aPane.indexOfTabComponent(ButtonTabComponent.this);
            if (i != -1) {
//                aMainFrame.close(aJInternalFrame);
            }
        }

        // we don't want to update UI for this button
        @Override
        public void updateUI() {
        }

        // paint the cross
        @Override
        protected void paintComponent(Graphics pGraphics) {
            super.paintComponent(pGraphics);
            Graphics2D g2 = (Graphics2D) pGraphics.create();
            // shift the image for pressed buttons
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.MAGENTA);
            }
            int delta = ButtonTabComponent.DELTA;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }
}