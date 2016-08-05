
package mysqls.framework;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.io.File;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import mysqls.graph.ClassNode;
import mysqls.graph.Graph;
import mysqls.sql.SQLEditPane;
import mysqls.sql.SQLlogPane;
import mysqls.sql.ui.MyDialog;
import mysqls.sql.util.SQLCreator;

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
		contentPane.add(sqLlogPane, BorderLayout.SOUTH);
		msSqlEditPane = new SQLEditPane(this);
		contentPane.add(msSqlEditPane, BorderLayout.EAST);
		contentPane.add(new JScrollPane(aPanel), BorderLayout.CENTER);
		setComponentPopupMenu(null); // Removes the system pop-up menu full of
										// disabled buttons.
	}

	/**
	 * sql到图形
	 */
	public void sql2graph() {

		String sql = msSqlEditPane.msqlpane.getText();
		if (!sql.trim().startsWith("c")) {
			JOptionPane.showMessageDialog(null, "sql错误了！！！！");
			return;
		}
		PersistenceService.readSQL(sql, aPanel.aGraph);

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
		JTextArea jTextArea = new JTextArea("直接把数据库模型加载到数据库中，这样用户不用输入任何sql语句就能建立数据库。一般来说，一个应用程序只需要一个数据库，这样是很方便的。");
		jTextArea.setEditable(false);
		jTextArea.setFont(new Font("Default", Font.PLAIN, 24));
		jTextArea.setLineWrap(true);
		jTextArea.setColumns(20);
		jTextArea.setRows(11);
		jTextArea.setForeground(Color.white);
		jTextArea.setBackground(Color.black);
		JPanel jPanel = new JPanel();
		jPanel.add(new JScrollPane(jTextArea));
		new MyDialog("server").show(jPanel);
	}

	/**
	 *
	 */
	public void loaddatabasealltables() {
		// TODO Auto-generated method stub
		JTextArea jTextArea = new JTextArea("加载数据库中的所有表，如果没有选择数据库，就提示让用户先选择或者重新建立");
		jTextArea.setEditable(false);
		jTextArea.setLineWrap(true);
		jTextArea.setColumns(20);
		jTextArea.setRows(11);
		jTextArea.setForeground(Color.white);
		jTextArea.setBackground(Color.black);
		jTextArea.setFont(new Font("Default", Font.PLAIN, 24));
		JPanel jPanel = new JPanel();
		jPanel.add(new JScrollPane(jTextArea));
		new MyDialog("server").show(jPanel);
	}

	/**
	 *
	 */
	public void databasemenu() {
		// TODO Auto-generated method stub
		JTextArea jTextArea = new JTextArea(
				"这个功能是，读取数据库server中所有的数据库，如果没有设置数据库server，那么提示让用户先设置server。让用户选择一个可以操作的数据库。这样就可以直接在数据库里面建立表了。"
						+ "这里应该列出所有的数据库，。然后让用户选择一个，也可以新建立一个数据库");
		jTextArea.setEditable(false);
		jTextArea.setLineWrap(true);
		jTextArea.setColumns(20);
		jTextArea.setForeground(Color.white);
		jTextArea.setBackground(Color.black);
		jTextArea.setRows(11);
		jTextArea.setFont(new Font("Default", Font.PLAIN, 24));
		JPanel jPanel = new JPanel();
		jPanel.add(new JScrollPane(jTextArea));
		new MyDialog("server").show(jPanel);
	}

	/**
	 *
	 */
	public void servermenu() {
		// TODO Auto-generated method stub
		JTextArea jTextArea = new JTextArea("这个是让用户设置数据库连接，设置好了以后，还要保存连接，" + "这样下次启动的时候，用户就不用再重新设置连接了，"
				+ "然后，应该检测，如果用户安装了mysql。那么就可以直接让用户输入账号密码就行了。" + "" + "总之，这里应该是设置数据库连接的");
		jTextArea.setEditable(false);
		jTextArea.setColumns(20);
		jTextArea.setRows(11);
		jTextArea.setLineWrap(true);
		jTextArea.setForeground(Color.white);
		jTextArea.setBackground(Color.black);
		jTextArea.setFont(new Font("Default", Font.PLAIN, 24));
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BorderLayout());
		jPanel.add(new JScrollPane(jTextArea), BorderLayout.CENTER);
		new MyDialog("server").show(jPanel);

	}
}
