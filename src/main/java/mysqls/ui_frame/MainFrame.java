package mysqls.ui_frame;

import mysqls.UMLEditor;
import mysqls.diagrams.ClassDiagramGraph;
import mysqls.framework.*;
import mysqls.graph.Edge;
import mysqls.graph.Graph;
import mysqls.graph.GraphElement;
import mysqls.graph.Node;
import mysqls.sql.util.MyIOutil;
import mysqls.ui_mainitem.GraphFrame;
import mysqls.ui_mainitem.GraphPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.*;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * 主框架，里面可以有很多内部框架，tab
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {
    private static final int FRAME_GAP = 20;
    private static final int ESTIMATED_FRAMES = 5;
    private static final int MAX_RECENT_FILES = 8;
    private static final int MARGIN_SCREEN = 8; // Fraction of the screen to
    // leave around the sides
    private static final int MARGIN_IMAGE = 2; // Number of pixels to leave
    // around the graph when
    // exporting it as an image
    private static final int HELP_MENU_TEXT_WIDTH = 10; // Number of pixels to
    // give to the width of
    // the text area of the
    // Help Menu.
    private static final int HELP_MENU_TEXT_HEIGHT = 40; // Number of pixels to
    // give to the
    // height of the
    // text area of the
    // Help Menu.

    private MenuFactory aAppFactory;
    private ResourceBundle aAppResources;
    private ResourceBundle aVersionResources;
    private ResourceBundle aEditorResources;
    private JTabbedPane aTabbedPane;// 选项卡窗体。
    private ArrayList<JInternalFrame> aTabs = new ArrayList<>();
    private JMenu aNewMenu;
    private Clipboard aClipboard = new Clipboard();

    private RecentFilesQueue aRecentFiles = new RecentFilesQueue();
    private JMenu aRecentFilesMenu;

    private WelcomeTab aWelcomeTab;

    // Menus or menu items that must be disabled if there is no current diagram.
    private final List<JMenuItem> aDiagramRelevantMenus = new ArrayList<>();

    /**
     * Constructs a blank frame with a desktop pane but no graph windows.
     *
     */
    public MainFrame() {
        aAppResources = ResourceBundle.getBundle("UMLEditorStrings");
        aAppFactory = new MenuFactory(aAppResources);
        aVersionResources = ResourceBundle.getBundle("UMLEditor" + "Version");
        aEditorResources = ResourceBundle.getBundle("UMLEditorStrings");

        setTitle(aAppResources.getString("app.name"));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        setBounds(screenWidth / (MainFrame.MARGIN_SCREEN * 2), screenHeight / (MainFrame.MARGIN_SCREEN * 2),
                (screenWidth * (MainFrame.MARGIN_SCREEN - 1)) / MainFrame.MARGIN_SCREEN,
                (screenHeight * (MainFrame.MARGIN_SCREEN - 1)) / MainFrame.MARGIN_SCREEN);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent pEvent) {
                exit();
            }
        });

        getmymainpanel();
    }


    //	主要的内容
    private void getmymainpanel() {
        getContentPane().setLayout(new BorderLayout());
        BootPanel bootPanel = BootPanel.getInstance();
        MainleftPanel mainleftPanel = MainleftPanel.getInstance();
        MainCenterPanel centerPanel = MainCenterPanel.getInstance();
        ToolPanel toolPanel = ToolPanel.getInstance(centerPanel);
        MainPanel mainPanel = new MainPanel(mainleftPanel, centerPanel);

        getContentPane().add(bootPanel, BorderLayout.SOUTH);
        getContentPane().add(toolPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

    }
    /**
     * Sets the TaskBar icon for the application.
     */
    public void setIcon() {
        try {
            java.net.URL url = getClass().getClassLoader().getResource(aAppResources.getString("app.icon"));
            Toolkit kit = Toolkit.getDefaultToolkit();
            Image img = kit.createImage(url);
            setIconImage(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cuts the current selection of the current panel and puts the content into
     * the application-specific clipboard.
     */
    public void cut() {
        if (noCurrentGraphFrame()) {
            return;
        }
        GraphFrame frame = (GraphFrame) aTabbedPane.getSelectedComponent();
        GraphPanel panel = frame.getGraphPanel();
        Graph curGraph = frame.getGraph();
        if (panel.getSelectionList().size() > 0) {
            SelectionList currentSelection = panel.getSelectionList();
            aClipboard.copy(currentSelection);
            Iterator<GraphElement> iter = currentSelection.iterator();
            panel.startCompoundListening();
            while (iter.hasNext()) {
                GraphElement element = iter.next();
                if (element instanceof Edge) {
                    curGraph.removeEdge((Edge) element);
                } else {
                    curGraph.removeNode((Node) element);
                }
                iter.remove();
            }
            panel.endCompoundListening();
        }
        panel.repaint();
    }

    /**
     * Copies the current selection of the current panel and puts the content
     * into the application-specific clipboard.
     */
    public void copy() {
        if (noCurrentGraphFrame()) {
            return;
        }
        GraphFrame frame = (GraphFrame) aTabbedPane.getSelectedComponent();
        GraphPanel panel = frame.getGraphPanel();
        if (panel.getSelectionList().size() > 0) {
            SelectionList currentSelection = panel.getSelectionList();
            aClipboard.copy(currentSelection);
        }
    }

    /**
     * Pastes a past selection from the application-specific Clipboard into
     * current panel. All the logic is done in the application-specific
     * CutPasteBehavior.
     */
    public void paste() {
        if (noCurrentGraphFrame()) {
            return;
        }
        GraphFrame frame = (GraphFrame) aTabbedPane.getSelectedComponent();

        GraphPanel panel = frame.getGraphPanel();

        SelectionList updatedSelectionList = aClipboard.paste(panel);
        panel.setSelectionList(updatedSelectionList);
        panel.repaint();

    }

    /**
     * Copies the current image to the clipboard.
     */
    public void copyToClipboard() {
        if (noCurrentGraphFrame()) {
            return;
        }
        GraphFrame frame = (GraphFrame) aTabbedPane.getSelectedComponent();
        final BufferedImage image = MainFrame.getImage(frame.getGraph());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new Transferable() {
            @Override
            public boolean isDataFlavorSupported(DataFlavor pFlavor) {
                return DataFlavor.imageFlavor.equals(pFlavor);
            }

            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[]{DataFlavor.imageFlavor};
            }

            @Override
            public Object getTransferData(DataFlavor pFlavor) throws UnsupportedFlavorException, IOException {
                if (DataFlavor.imageFlavor.equals(pFlavor)) {
                    return image;
                } else {
                    throw new UnsupportedFlavorException(pFlavor);
                }
            }
        }, null);
        JOptionPane.showInternalMessageDialog(aTabbedPane, aEditorResources.getString("dialog.to_clipboard.message"),
                aEditorResources.getString("dialog.to_clipboard.title"), JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean noCurrentGraphFrame() {
        return aTabbedPane.getSelectedComponent() == null
                || !(aTabbedPane.getSelectedComponent() instanceof GraphFrame);
    }

    /*
     * Return the image corresponding to the graph.
     *
     * @param pGraph The graph to convert to an image.
     *
     * @return bufferedImage. To convert it into an image, use the syntax :
     * Toolkit.getDefaultToolkit().createImage(bufferedImage.getSource());
     */
    private static BufferedImage getImage(Graph pGraph) {
        Rectangle2D bounds = pGraph.getBounds();
        BufferedImage image = new BufferedImage((int) (bounds.getWidth() + MainFrame.MARGIN_IMAGE * 2),
                (int) (bounds.getHeight() + MainFrame.MARGIN_IMAGE * 2), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.translate(-bounds.getX(), -bounds.getY());
        g2.setColor(Color.WHITE);
        g2.fill(new Rectangle2D.Double(bounds.getX(), bounds.getY(), bounds.getWidth() + MainFrame.MARGIN_IMAGE * 2,
                bounds.getHeight() + MainFrame.MARGIN_IMAGE * 2));
        g2.translate(MainFrame.MARGIN_IMAGE, MainFrame.MARGIN_IMAGE);
        g2.setColor(Color.BLACK);
        g2.setBackground(Color.WHITE);
        pGraph.draw(g2, null);
        return image;
    }

    /**
     * Exits the program if no graphs have been modified or if the user agrees
     * to abandon modified graphs.
     */
    public void exit() {
//        int modcount = 0;
//        for (int i = 0; i < aTabs.size(); i++) {
//            if (aTabs.get(i) instanceof GraphFrame) {
//                GraphFrame frame = (GraphFrame) aTabs.get(i);
//                if (frame.getGraphPanel().isModified()) {
//                    modcount++;
//                }
//            }
//        }
//        if (modcount > 0) {
//            // ask user if it is ok to close
//            int result = JOptionPane.showInternalConfirmDialog(aTabbedPane, MessageFormat
//                            .format(aEditorResources.getString("dialog.exit.ok"), new Integer(modcount)), null,
//                    JOptionPane.YES_NO_OPTION);
//
//            // if the user doesn't agree, veto the close
//            if (result != JOptionPane.YES_OPTION) {
//                return;
//            }
//        }
//        Preferences.userNodeForPackage(UMLEditor.class).put("recent", aRecentFiles.serialize());
        System.exit(0);
    }
}
