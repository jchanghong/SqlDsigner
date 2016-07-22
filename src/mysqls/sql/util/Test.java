/**
 *
 */
package mysqls.sql.util;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
public class Test extends JFrame {

	public static TableColumn column = new TableColumn("hello1");
	public static Table table = new Table("table");

	public static void main(String[] args) throws Exception {

		Test.table.addColumn(Test.column);
		Test.column.setForigntable(Test.table);
		Test.column.setForigncolumn(Test.column);
		Test.column.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				System.out.println(evt.getPropertyName() + "changed!!!===" + evt.getNewValue().toString());
			}
		});
		Test.table.addColumn(new TableColumn("222"));
		Test.table.addColumn(new TableColumn("23444"));
		Test.table.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				System.out.println(SQLCreator.create(Test.table));
			}
		});
		Test.testui();
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
