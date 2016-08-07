/**
 *
 */
package mysqls.sql.mytest;

import java.beans.Introspector;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import mysqls.sql.util.MUiUtil;

/**
 * @author jiang 测试
 *
 */
public class Main {

	public static MBean bean = new MBean();

	public static void main(String[] args) throws Exception {

		Main.bean.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				System.out.println(evt.getPropertyName() + "PropertyChangeEvent" + evt.getNewValue());

			}
		});
		Main.bean.setName("name");
		java.beans.PropertyDescriptor descriptor = Introspector.getBeanInfo(MBean.class).getPropertyDescriptors()[0];
		System.out.println(descriptor.getName());

		String[] options = { "OK" };

		JOptionPane.showOptionDialog(null, MUiUtil.getEditorComponent(Main.bean, descriptor),
				ResourceBundle.getBundle("mysqls.framework.EditorStrings").getString("dialog.properties"),
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
	}
}
