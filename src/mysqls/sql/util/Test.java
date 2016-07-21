/**
 *
 */
package mysqls.sql.util;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import mysqls.framework.PropertySheets;
import mysqls.sql.entity.EditTable;
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

		// Test.testbeand();
		// Test.testui();
		Test.tst();
	}

	/**
	 *
	 */
	private static void tst() {
		// TODO Auto-generated method stub
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					Test ui = new Test();
					ui.setContentPane(new EditTable());
					ui.setSize(400, 300);
					ui.setVisible(true);
				}
			});
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testbeand() {

		Test.column.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				System.out.println(evt.getPropertyName() + ":" + evt.getOldValue());
			}
		});
		Test.column.setName("fff");

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
