
package mysqls.framework;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import mysqls.graph.Graph;
import mysqls.sql.SQLEditPane;
import mysqls.sql.SQLbutton;
import mysqls.sql.SQLlogPane;

/**
 * A frame for showing a graphical editor.
 */
@SuppressWarnings("serial")
public class GraphFrame extends JInternalFrame {
	private JTabbedPane aTabbedPane;
	private SQLlogPane sqLlogPane;
	private SQLEditPane msSqlEditPane;
	public GraphPanel aPanel;

	private File aFile; // The file associated with this graph

	/**
	 * Constructs a graph frame with an empty tool bar.
	 *
	 * @param pGraph
	 *            the initial graph
	 * @param pTabbedPane
	 *            the JTabbedPane associated with this GraphFrame.
	 */
	public GraphFrame(Graph pGraph, JTabbedPane pTabbedPane) {
		aTabbedPane = pTabbedPane;
		ToolBar sideBar = new ToolBar(pGraph);
		aPanel = new GraphPanel(pGraph, sideBar);
		Container contentPane = getContentPane();
		contentPane.add(sideBar, BorderLayout.WEST);
		sqLlogPane = new SQLlogPane(this);
		contentPane.add(new SQLbutton(this), BorderLayout.NORTH);
		contentPane.add(sqLlogPane, BorderLayout.SOUTH);
		msSqlEditPane = new SQLEditPane(this);
		contentPane.add(msSqlEditPane, BorderLayout.EAST);
		contentPane.add(new JScrollPane(aPanel), BorderLayout.CENTER);
		setComponentPopupMenu(null); // Removes the system pop-up menu full of
										// disabled buttons.
	}

	public GraphPanel getaPanel() {
		return aPanel;
	}

	/**
	 * Gets the file property.
	 *
	 * @return the file associated with this graph
	 */
	public File getFileName() {
		return aFile;
	}

	/**
	 * Gets the graph that is being edited in this frame.
	 *
	 * @return the graph
	 */
	public Graph getGraph() {
		return aPanel.getGraph();
	}

	/**
	 * Gets the graph panel that is contained in this frame.
	 *
	 * @return the graph panel
	 */
	public GraphPanel getGraphPanel() {
		return aPanel;
	}

	/**
	 * This association and getter method are needed to display messages using
	 * the copy to clipboard functionality of the Optional ToolBar.
	 *
	 * @return aTabbedPane the JTabbedPane associated with this GraphFrame.
	 */
	public JTabbedPane getJTabbedPane() {
		return aTabbedPane;
	}

	public SQLEditPane getMsSqlEditPane() {
		return msSqlEditPane;
	}

	public SQLlogPane getSqLlogPane() {
		return sqLlogPane;
	}

	/**
	 * Sets the file property.
	 *
	 * @param pFile
	 *            The file associated with this graph
	 */
	public void setFile(File pFile) {
		aFile = pFile;
		setTitle(aFile.getName());
	}

	public void setMsSqlEditPane(SQLEditPane msSqlEditPane) {
		this.msSqlEditPane = msSqlEditPane;
	}

	/**
	 * Sets the title of the frame as the file name if there is a file name.
	 *
	 * @param pModified
	 *            If the file is in modified (unsaved) state, appends an
	 *            asterisk to the frame title.
	 */
	public void setTitle(boolean pModified) {
		if (aFile != null) {
			String title = aFile.getName();
			if (pModified) {
				if (!getTitle().endsWith("*")) {
					setTitle(title + "*");
				}
			} else {
				setTitle(title);
			}
		}
	}
}
