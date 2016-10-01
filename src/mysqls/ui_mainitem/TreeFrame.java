/**
 *  实体关系图和sql生产的实现
 */
package mysqls.ui_mainitem;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import mysqls.contanst.ConnectINFO;
import mysqls.contanst.UIconstant;
import mysqls.sql.databaseserver2.TreeLeft;
import mysqls.sql.databaseserver2.TreeSQLedit;
import mysqls.sql.databaseserver2.TreeTabledit;

/**
 * @author 长宏 tree Frame
 *
 */
public class TreeFrame {

	public static JFrame me;
	private static JPanel right;// 右边
	/**
	 * 记录sql语句，判断哪些语句执行成功,清空的时候这个list只清空执行成功的语句
	 */
	public static Set<String> sqList;
	public static Map<Object, String> sqlmap;

	/**
	 * 记录下需要删除的行的第一列
	 */
	public static Set<String> keytodelete;
	/**
	 * 表头
	 */
	public static Vector<String> tablehead;// 表格
	/**
	 * 每个元素代表一个行，这个行肯定是已经改变过了，不是空行 根据行得到整个行值
	 */
	public static Map<Integer, Vector<Object>> tablevalues;
	/**
	 * 根据行得到原来的第一列值，
	 */
	public static Map<Integer, Object> oldfirstvaluesList;
	static {
		TreeFrame.sqlmap = new HashMap<>();
		TreeFrame.sqList = new HashSet<>();
		TreeFrame.oldfirstvaluesList = new HashMap<>();
		TreeFrame.keytodelete = new HashSet<>();
		TreeFrame.tablehead = new Vector<>();
		TreeFrame.tablevalues = new HashMap<>();
	}

	/**
	 * @return the right
	 */
	public static JPanel getRight() {
		return TreeFrame.right;
	}

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/changhong";
		try {
			Connection connection = DriverManager.getConnection(url, "root", "0000");
			ConnectINFO.connection = connection;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JFrame jFrame = TreeFrame.getui();
		jFrame.setVisible(true);

	}

	public static JFrame getui() {

		JFrame jFrame = UIconstant.frames.get(UIconstant.TREEUI);
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(500, 400);

			JPanel rigtht = new JPanel();
			GridBagLayout bagLayout = new GridBagLayout();
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.fill = GridBagConstraints.BOTH;
			constraints.gridwidth = GridBagConstraints.REMAINDER;
			constraints.gridheight = 1;
			constraints.weighty = 0.9;
			constraints.weightx = 1.0;

			rigtht.setLayout(bagLayout);
			rigtht.add(TreeTabledit.getui(), constraints);

			constraints.gridwidth = GridBagConstraints.REMAINDER;
			constraints.weighty = 0.1;
			rigtht.add(TreeSQLedit.getui(), constraints);

			TreeFrame.right = rigtht;
			jFrame.setLocation(400, 250);
			jFrame.setLayout(new BorderLayout());

			UIconstant.frames.put(UIconstant.TREEUI, jFrame);
			jFrame.setTitle("操作数据库");
			jFrame.add(TreeLeft.getui(), BorderLayout.WEST);
			jFrame.add(rigtht, BorderLayout.CENTER);
			jFrame.pack();
			// jFrame.setResizable(false);
		}

		TreeFrame.me = jFrame;
		return jFrame;

	}

}
