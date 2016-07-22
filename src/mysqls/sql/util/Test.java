/**
 *
 */
package mysqls.sql.util;

import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import mysqls.framework.PropertySheets;
import mysqls.sql.entity.Table;
import mysqls.sql.entity.TableColumn;

/**
 * @author jiang
 *
 */
@SuppressWarnings("serial")
public class Test extends JFrame {

	public static TableColumn column = new TableColumn("hello1");
	public static Table table = new Table("table");

	public static void main(String[] args) throws Exception {

		Test.table.addColumn(Test.column);
		Test.table.addColumn(new TableColumn("222"));
		Test.table.addColumn(new TableColumn("23444"));
		System.out.println(SQLCreator.create(Test.table));
		System.out.println("clone is:\n");
		Table dTable = Test.table.clone();
		System.out.println(SQLCreator.create(dTable));
		dTable.setName("ccccccccccccccc");
		dTable.getColumnlist().get(0).setName("ccccccnnnn");
		System.out.println("ori is:\n");
		System.out.println(SQLCreator.create(Test.table));
		System.out.println("cloen is:]\n");
		System.out.println(SQLCreator.create(dTable));

		// Test.table.addPropertyChangeListener(new PropertyChangeListener() {
		//
		// @Override
		// public void propertyChange(PropertyChangeEvent evt) {
		// // TODO Auto-generated method stub
		// System.out.println(SQLCreator.create(Test.table));
		// }
		// });
		// MyIOutil.copy(SQLCreator.create(Test.table), new File("ee.sql"));
		// System.out.println(MyIOutil.read(new File("ee.sql")));
		// Test.testui();
		// Test.tst();
	}

	public static void testui() throws Exception {
		SwingUtilities.invokeAndWait(new Runnable() {

			@Override
			public void run() {

				PropertySheets propertySheets = new PropertySheets(Test.table);

				String[] options = { "OK" };
				if (propertySheets != null) {

					JOptionPane.showOptionDialog(null, propertySheets,
							ResourceBundle.getBundle("mysqls.framework.EditorStrings").getString("dialog.properties"),
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
				}
			}
		});

	}
}
