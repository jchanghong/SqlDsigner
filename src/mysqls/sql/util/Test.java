/**
 *
 */
package mysqls.sql.util;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import mysqls.framework.PropertySheets;
import mysqls.sql.entity.TableColumn;

/**
 * @author jiang
 *
 */
public class Test {

	public static void main(String[] args) throws Exception {

		Test.testbeand();
	}

	public static void testbeand() {

		TableColumn tableColumn = new TableColumn("new");
		tableColumn.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				System.out.println(evt.getPropertyName() + ":" + evt.getOldValue());
			}
		});
		tableColumn.setName("fff");

	}

	public static void testui() throws Exception {
		SwingUtilities.invokeAndWait(new Runnable() {

			@Override
			public void run() {

				PropertySheets propertySheets = new PropertySheets(new TableColumn("jjh"));

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
