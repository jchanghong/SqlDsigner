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
		TableColumn column = new TableColumn();
		column.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				System.out.println(evt.getPropertyName() + evt.getNewValue());
			}
		});
		column.setName("hell");
		Thread.currentThread();
		Thread.sleep(1000);
		column.setName("hell2");
		column.setName("hell3");
		System.out.println("afterset");
		SwingUtilities.invokeAndWait(new Runnable() {

			@Override
			public void run() {

				PropertySheets propertySheets = new PropertySheets(Test.column);

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
