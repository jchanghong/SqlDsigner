
package mysqls.framework;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 * A layout manager that aligns components top down and moves to a second row if
 * there isn't sufficient space. The manager required the parent to have a
 * defined, non-zero height to work properly.
 *
 * @author Martin P. Robillard
 */
public class VerticalLayout implements LayoutManager {
	@Override
	public void addLayoutComponent(String pString, Component pComponent) {
	} // Not used on purpose.

	@Override
	public void removeLayoutComponent(Component pComponent) {
	} // Not used on purpose.

	@Override
	public void layoutContainer(Container pParent) {
		int accumulatedHeight = 0;
		int xOffset = 0;
		int maxWidth = 0;

		for (Component child : pParent.getComponents()) {
			if (accumulatedHeight + child.getPreferredSize().getHeight() > pParent.getHeight()) {
				accumulatedHeight = 0;
				xOffset = xOffset + maxWidth;
				maxWidth = 0;
			}
			child.setBounds(xOffset, accumulatedHeight, (int) child.getPreferredSize().getWidth(),
					(int) child.getPreferredSize().getHeight());
			accumulatedHeight += child.getPreferredSize().getHeight();
			maxWidth = Math.max(maxWidth, (int) child.getPreferredSize().getWidth());
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container pParent) {
		int accumulatedHeight = 0;
		int xOffset = 0;
		int maxWidth = 0;

		for (Component child : pParent.getComponents()) {
			if (accumulatedHeight + child.getPreferredSize().getHeight() > pParent.getHeight()) {
				accumulatedHeight = 0;
				xOffset = xOffset + maxWidth;
				maxWidth = 0;
			}
			accumulatedHeight += child.getPreferredSize().getHeight();
			maxWidth = Math.max(maxWidth, (int) child.getPreferredSize().getWidth());
		}
		return new Dimension(xOffset + maxWidth, pParent.getHeight());
	}

	@Override
	public Dimension preferredLayoutSize(Container pParent) {
		return minimumLayoutSize(pParent);
	}
}