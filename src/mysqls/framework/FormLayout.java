package mysqls.framework;

import java.awt.*;

/**
 * A layout manager that lays out components along a central axis.
 */
class FormLayout implements LayoutManager {
    private static final int GAP = 6;

    private int aLeft;
    private int aRight;
    private int aHeight;

    @Override
    public Dimension preferredLayoutSize(Container pParent) {
        Component[] components = pParent.getComponents();
        aLeft = 0;
        aRight = 0;
        aHeight = 0;
        for (int i = 0; i < components.length; i += 2) {
            Component cleft = components[i];
            Component cright = components[i + 1];
            Dimension dleft = cleft.getPreferredSize();
            Dimension dright = cright.getPreferredSize();
            aLeft = Math.max(aLeft, dleft.width);
            aRight = Math.max(aRight, dright.width);
            aHeight = aHeight + Math.max(dleft.height, dright.height);
        }
        return new Dimension(aLeft + FormLayout.GAP + aRight, aHeight);
    }

    @Override
    public Dimension minimumLayoutSize(Container pParent) {
        return preferredLayoutSize(pParent);
    }

    @Override
    public void layoutContainer(Container pParent) {
        preferredLayoutSize(pParent); // sets left, right
        Component[] components = pParent.getComponents();
        Insets insets = pParent.getInsets();
        int xcenter = insets.left + aLeft;
        int y = insets.top;

        for (int i = 0; i < components.length; i += 2) {
            Component cleft = components[i];
            Component cright = components[i + 1];

            Dimension dleft = cleft.getPreferredSize();
            Dimension dright = cright.getPreferredSize();

            int height = Math.max(dleft.height, dright.height);

            cleft.setBounds(xcenter - dleft.width, y + (height - dleft.height) / 2, dleft.width, dleft.height);
            cright.setBounds(xcenter + FormLayout.GAP, y + (height - dright.height) / 2, dright.width, dright.height);
            y += height;
        }
    }

    @Override
    public void addLayoutComponent(String pName, Component pComponent) {
    }

    @Override
    public void removeLayoutComponent(Component pComponent) {
    }

}
