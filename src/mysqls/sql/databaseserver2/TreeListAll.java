/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.databaseserver2;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import mysqls.contanst.ConnectINFO;
import mysqls.contanst.UIconstant;

/**
 * @author 长宏
 *
 */
public class TreeListAll {

	public static JFrame mainui;
	public static List<String> sqList;
	static {
		TreeListAll.sqList = new ArrayList<>();
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

		JFrame jFrame = TreeListAll.getui();
		jFrame.setVisible(true);

	}

	public static JFrame getui() {

		JFrame jFrame = UIconstant.frames.get(UIconstant.TREEUI);
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(500, 400);
			jFrame.setLocation(400, 250);
			jFrame.setLayout(new BorderLayout());
			jFrame.add(TreeLeft.getui(), BorderLayout.WEST);
				jFrame.add(TreeNouthPanel.getMePanel(), BorderLayout.NORTH);
			jFrame.add(TreeCenter.getui(), BorderLayout.CENTER);

			UIconstant.frames.put(UIconstant.TREEUI, jFrame);
			jFrame.pack();
		}

		TreeListAll.mainui = jFrame;
		return jFrame;

	}

}
