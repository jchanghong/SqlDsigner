/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.databaseserver2;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * @author 长宏
 *
 */
public class TreeListAll {

	public static void main(String[] args) {
		JFrame jFrame = TreeListAll.getui();
		jFrame.setVisible(true);

	}

	public static JFrame getui() {
		JFrame jFrame = new JFrame();
		jFrame.setSize(500, 400);

		jFrame.setLayout(new BorderLayout());
		jFrame.add(TreeLeft.getui(), BorderLayout.WEST);

		return jFrame;

	}

}
