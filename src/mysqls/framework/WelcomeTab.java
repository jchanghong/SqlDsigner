package mysqls.framework;

import mysqls.sql.ui.ImagePanle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

/**
 * @author
 */
@SuppressWarnings("serial")
public class WelcomeTab extends JInternalFrame {
    private static final int BORDER_MARGIN = 45;
    private static final int ALTERNATIVE_BORDER_MARGIN = 30;
    private static final int FOOT_BORDER_MARGIN = 10;
    private static final int FONT_SIZE = 25;
    private ResourceBundle aWelcomeResources;
    private JPanel aFootTextPanel;
    private JPanel aRightTitlePanel;
    private JPanel aLeftTitlePanel;
    private JPanel aLeftPanel;
    private JPanel aRightPanel;
    private JMenu aNewFileMenu;
    private JMenu aRecentFileMenu;
    private ImageIcon aLeftPanelIcon;
    private ImageIcon aRightPanelIcon;
    private String aFootText;
    Font font;

    /**
     * @param pNewFileMenu    The NewFileMenu to link to the WelcomeTab.
     * @param pRecentFileMenu The RecentFileMenu to link to the WelcomeTab.
     */
    public WelcomeTab(JMenu pNewFileMenu, JMenu pRecentFileMenu) {
        font = new Font("Default", Font.PLAIN, 24);
        aWelcomeResources = ResourceBundle.getBundle("mysqls.framework.EditorStrings");
        aLeftPanelIcon = new ImageIcon(
                getClass().getClassLoader().getResource(aWelcomeResources.getString("welcome.create.icon")));
        aRightPanelIcon = new ImageIcon(
                getClass().getClassLoader().getResource(aWelcomeResources.getString("welcome.open.icon")));
        setOpaque(false);
        setLayout(new BorderLayout());

        BasicInternalFrameUI ui = (BasicInternalFrameUI) getUI();
        Container north = ui.getNorthPane();
        north.remove(0);
        north.validate();
        north.repaint();

        aNewFileMenu = pNewFileMenu;
        aRecentFileMenu = pRecentFileMenu;
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false);

        JPanel shortcutPanel = new JPanel();
        shortcutPanel.setOpaque(false);
        shortcutPanel.setLayout(new GridLayout(2, 2));
        shortcutPanel.add(getLeftTitlePanel());
        shortcutPanel.add(getRightTitlePanel());
        shortcutPanel.add(getLeftPanel());
        shortcutPanel.add(getRightPanel());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(shortcutPanel, c);

        add(panel, BorderLayout.NORTH);
        add(getFootTextPanel(), BorderLayout.SOUTH);
        setComponentPopupMenu(null); // Removes the system pop-up menu full of

        // disabled buttons.
        ImagePanle.setbj(this, "mainIcon/bj4.png");
    }

    private JPanel getLeftPanel() {
        if (aLeftPanel == null) {
            aLeftPanel = new JPanel();
            aLeftPanel.setOpaque(false);
            aLeftPanel.setLayout(new BoxLayout(aLeftPanel, BoxLayout.Y_AXIS));
            aLeftPanel.setBorder(new EmptyBorder(0, 0, 0, WelcomeTab.BORDER_MARGIN));

            for (int i = 0; i < aNewFileMenu.getItemCount(); i++) {
                final JMenuItem item = aNewFileMenu.getItem(i);
                // String label = item.getText();
                JButton newDiagramShortcut = new JButton("新建模型");
                newDiagramShortcut.setUI(new WelcomeButtonUI());
                newDiagramShortcut.setAlignmentX(Component.RIGHT_ALIGNMENT);
                newDiagramShortcut.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent pEvent) {
                        item.doClick();
                    }
                });
                aLeftPanel.add(newDiagramShortcut);
            }
        }
        return aLeftPanel;
    }

    private JPanel getRightPanel() {
        if (aRightPanel == null) {
            aRightPanel = new JPanel();
            aRightPanel.setOpaque(false);
            aRightPanel.setLayout(new BoxLayout(aRightPanel, BoxLayout.Y_AXIS));
            aRightPanel.setBorder(new EmptyBorder(0, WelcomeTab.BORDER_MARGIN, 0, WelcomeTab.BORDER_MARGIN));

            for (int i = 0; i < aRecentFileMenu.getItemCount(); i++) {
                final JMenuItem item = aRecentFileMenu.getItem(i);
                String label = item.getText().substring(2);
                JButton fileShortcut = new JButton(label);
                fileShortcut.setUI(new WelcomeButtonUI());
                fileShortcut.setAlignmentX(Component.LEFT_ALIGNMENT);
                fileShortcut.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent pEvent) {
                        item.doClick();
                    }
                });
                aRightPanel.add(fileShortcut);
            }

        }
        return this.aRightPanel;
    }

    private JPanel getLeftTitlePanel() {
        if (aLeftTitlePanel == null) {
            JLabel icon = new JLabel();
            icon.setIcon(this.aLeftPanelIcon);

            JLabel title = new JLabel("新建模型");

            title.setFont(font);
            // title.setFont(new Font("Arial", Font.PLAIN,
            // WelcomeTab.FONT_SIZE));
            title.setForeground(Color.DARK_GRAY);
            title.setBorder(new EmptyBorder(0, WelcomeTab.ALTERNATIVE_BORDER_MARGIN, 0, 0));

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.add(icon);
            panel.add(title);
            panel.setOpaque(false);

            aLeftTitlePanel = new JPanel();
            aLeftTitlePanel.setOpaque(false);
            aLeftTitlePanel.setLayout(new BorderLayout());
            aLeftTitlePanel.add(panel, BorderLayout.EAST);
            aLeftTitlePanel
                    .setBorder(new EmptyBorder(0, 0, WelcomeTab.ALTERNATIVE_BORDER_MARGIN, WelcomeTab.BORDER_MARGIN));
        }
        return aLeftTitlePanel;
    }

    private JPanel getRightTitlePanel() {
        if (aRightTitlePanel == null) {
            JLabel icon = new JLabel();
            icon.setIcon(this.aRightPanelIcon);
            icon.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel title = new JLabel("打开文件");
            title.setFont(font);
            title.setForeground(Color.DARK_GRAY);
            title.setBorder(new EmptyBorder(0, 0, 0, WelcomeTab.ALTERNATIVE_BORDER_MARGIN));

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.add(title);
            panel.add(icon);
            panel.setOpaque(false);

            aRightTitlePanel = new JPanel();
            aRightTitlePanel.setOpaque(false);
            aRightTitlePanel.setLayout(new BorderLayout());
            aRightTitlePanel.add(panel, BorderLayout.WEST);
            aRightTitlePanel
                    .setBorder(new EmptyBorder(0, WelcomeTab.BORDER_MARGIN, WelcomeTab.ALTERNATIVE_BORDER_MARGIN, 0));
        }
        return aRightTitlePanel;
    }

    private JPanel getFootTextPanel() {
        if (aFootTextPanel == null) {
            aFootText = aWelcomeResources.getString("welcome.copyright");
            aFootTextPanel = new JPanel();
            aFootTextPanel.setOpaque(false);
            aFootTextPanel.setBorder(new EmptyBorder(0, 0, WelcomeTab.FOOT_BORDER_MARGIN, 0));
            aFootTextPanel.setLayout(new BoxLayout(this.aFootTextPanel, BoxLayout.Y_AXIS));
            aFootTextPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel text = new JLabel(this.aFootText);
            text.setAlignmentX(Component.CENTER_ALIGNMENT);

            aFootTextPanel.add(text);
        }

        return aFootTextPanel;
    }

}
