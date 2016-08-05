package mysqls.sql;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import mysqls.framework.GraphFrame;
import mysqls.framework.PersistenceService;
import mysqls.graph.ClassNode;
import mysqls.sql.util.SQLCreator;

/**
 * @author jiang 这个类目前没用了，保留下来。
 *
 */
@SuppressWarnings("serial")
public class SQLbutton extends JPanel {

	GraphFrame mFrame;
	private JButton mcreateSQL;
	private JButton mcreateNode;

	public SQLbutton(GraphFrame graphFrame)// 构造器。
	{
		// TODO Auto-generated constructor stub
		mFrame = graphFrame;
		setLayout(new GridLayout(0, 2));
		mcreateNode = new JButton("从sql语句生成实体关系图");
		mcreateSQL = new JButton("从下面的实体关系图生成sql");
		add(mcreateSQL);
		add(mcreateNode);
		SetListener();

	}

	private void SetListener() {
		// TODO Auto-generated method stub
		mcreateNode.addActionListener(new ActionListener() // mcreateNode监听器。
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SQLEditPane editPane = mFrame.getMsSqlEditPane();
				String sql = editPane.msqlpane.getText();
				PersistenceService.readSQL(sql, mFrame.aPanel.aGraph);

				mFrame.aPanel.updateui();
				System.out.println(editPane.msqlpane.getText());
			}
		});
		mcreateSQL.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				List<ClassNode> list = mFrame.getaPanel().getClassNOdes();
				if (list.size() < 1) {
					return;
				}
				StringBuilder builder = new StringBuilder();
				for (ClassNode classNode : list) {
					builder.append(SQLCreator.create(classNode.mTable));
				}
				// for (Iterator<ClassNode> iterator = list.iterator();
				// iterator.hasNext();) {
				// ClassNode classNode = (ClassNode) iterator.next();
				// builder.append(SQLcreate.fromNodeObject(classNode));
				// builder.append("\n");
				//
				// }
				// String string =
				// SQLcreate.addsqlassiontion(builder.toString(),
				// mFrame.getGraph().getClassEdge());
				mFrame.getMsSqlEditPane().setsql(builder.toString());
			}
		});

	}

}
