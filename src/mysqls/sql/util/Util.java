/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.util;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @author 长宏 属性编辑器工具
 *
 */
public final class Util {
	/**
	 *
	 */
	private Util() {
		// TODO Auto-generated constructor stub
	}

	public static Component getEditorComponent(Object v, final PropertyDescriptor propertyDescriptor)

	{
		return Util.getEditorComponent(Util.getEditor(v, propertyDescriptor));
	}

	public static PropertyEditor getEditor(final Object pBean, PropertyDescriptor pDescriptor) {
		try {
			final Method getter = pDescriptor.getReadMethod();
			final Method setter = pDescriptor.getWriteMethod();
			if (getter == null || setter == null) {
				return null;
			}

			Class<?> type = pDescriptor.getPropertyType();
			final PropertyEditor editor;
			Class<?> editorClass = pDescriptor.getPropertyEditorClass();

			if (editorClass != null) {
				editor = (PropertyEditor) editorClass.newInstance();
			} else {
				editor = PropertyEditorManager.findEditor(type);
			}
			if (editor == null) {
				return null;
			}

			Object value = getter.invoke(pBean, new Object[] {});
			editor.setValue(value);
			editor.addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent pEvent) {
					try {
						setter.invoke(pBean, new Object[] { editor.getValue() });
					} catch (IllegalAccessException | InvocationTargetException exception) {
						exception.printStackTrace();
					}
				}
			});
			return editor;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException exception) {
			return null;
		}
	}

	public static Component getEditorComponent(final PropertyEditor pEditor) {
		String[] tags = pEditor.getTags();
		String text = pEditor.getAsText();
		if (pEditor.supportsCustomEditor()) {
			return pEditor.getCustomEditor();

		} else if (tags != null) {
			// make a combo box that shows all tags
			final JComboBox<String> comboBox = new JComboBox<>(tags);
			comboBox.setSelectedItem(text);
			comboBox.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent pEvent) {
					if (pEvent.getStateChange() == ItemEvent.SELECTED) {
						pEditor.setAsText((String) comboBox.getSelectedItem());
					}
				}
			});
			return comboBox;
		} else {
			final JTextField textField = new JTextField(text, 10);
			textField.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void insertUpdate(DocumentEvent pEvent) {
					pEditor.setAsText(textField.getText());
				}

				@Override
				public void removeUpdate(DocumentEvent pEvent) {
					pEditor.setAsText(textField.getText());
				}

				@Override
				public void changedUpdate(DocumentEvent pEvent) {
				}
			});
			return textField;
		}
	}

}
