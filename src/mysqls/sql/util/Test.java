/**
 *
 */
package mysqls.sql.util;

import java.awt.Button;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import mysqls.framework.PropertySheets;
import mysqls.sql.entity.TableColumn;

/**
 * @author jiang
 *
 */
public class Test extends JFrame {

	public static TableColumn column;

	/**
	 *
	 */
	public Test() {
		// TODO Auto-generated constructor stub
		Test.column = new TableColumn("name");
		Test.column.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				add(new Button(evt.getPropertyName() + evt.getOldValue() + evt.getNewValue()));
				validate();
			}
		});

	}

	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {

					Test test = new Test();
					test.setVisible(true);
					PropertySheets propertySheets = new PropertySheets(Test.column);

					String[] options = { "OK" };
					if (propertySheets != null) {

						JOptionPane.showOptionDialog(null, propertySheets,
								ResourceBundle.getBundle("mysqls.framework.EditorStrings")
										.getString("dialog.properties"),
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
					}
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
}
