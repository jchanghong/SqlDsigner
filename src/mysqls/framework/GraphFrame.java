
package mysqls.framework;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import mysqls.contanst.UIconstant;
import mysqls.graph.ClassNode;
import mysqls.graph.Graph;
import mysqls.sql.SQLEditPane;
import mysqls.sql.SQLlogPane;
import mysqls.sql.databaseserver.MainUI;
import mysqls.sql.entity.Table;
import mysqls.sql.sqlreader.StatementUtil;
import mysqls.sql.util.SQLCreator;

/**
 * 这是主要的显示类。包括是几乎所有的面板。
 */
@SuppressWarnings("serial")
public class GraphFrame extends JInternalFrame {
	public static GraphFrame me = null;
	private JTabbedPane aTabbedPane;
	private SQLlogPane sqLlogPane;
	private SQLEditPane msSqlEditPane;
	public GraphPanel aPanel;
	List<String> list = new ArrayList<String>();

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
		contentPane.add(sqLlogPane, BorderLayout.SOUTH);
		msSqlEditPane = new SQLEditPane(this);
		contentPane.add(msSqlEditPane, BorderLayout.EAST);
		contentPane.add(new JScrollPane(aPanel), BorderLayout.CENTER);
		setComponentPopupMenu(null); // Removes the system pop-up menu full of
		GraphFrame.me = this;
		// disabled buttons.
	}

	/**
	 * sql到图形
	 */
	public void sql2graph() {

		String sql = msSqlEditPane.msqlpane.getText();
		if (!StatementUtil.isOKstatement(sql)) {
			JOptionPane.showMessageDialog(null, "sql错误了！！！！");
			return;
		}
		aPanel.aGraph = PersistenceService.readSQL(sql, aPanel.aGraph);

		aPanel.updateui();

	}

	/**
	 * sql到图形
	 */
	public void sql2graph(List<Table> list) {

		aPanel.aGraph = PersistenceService.readSQL(list, aPanel.aGraph);

		aPanel.updateui();

	}

	/**
	 * 图形到sql之间的转发，内部框架知道每个pannel。所以这个功能应该在这个类里面实现比较合适
	 */
	public void graph2sql() {
		List<ClassNode> list = aPanel.getClassNOdes();
		if (list.size() < 1) {
			JOptionPane.showMessageDialog(null, "没有sql图形！！！！");
			return;
		}
		StringBuilder builder = new StringBuilder();
		for (ClassNode classNode : list) {
			builder.append(SQLCreator.create(classNode.mTable));
		}
		msSqlEditPane.setsql(builder.toString());

	}

	/**
	 * @return
	 */
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

	/**
	 *
	 */
	public void graph2dbmenu() {
		// TODO Auto-generated method stub

	}

	/**
	 *
	 */
	public void loaddatabasealltables() {

	}

	/**
	 *
	 */
	public void databasemenu() {

	}

	/**
	 * @throws SQLException
	 *
	 */
	public void servermenu() {
		JFrame ui = MainUI.getui();
		for (JFrame jrame : UIconstant.frames.values()) {
			if (jrame != null) {
				jrame.setVisible(false);
			}
		}
		ui.setVisible(true);
	}

}
