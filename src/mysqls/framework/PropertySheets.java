
package mysqls.framework;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.beans.PropertyEditorSupport;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import mysqls.graph.AssociationEdge;
import mysqls.graph.ClassNode;
import mysqls.graph.PropertyOrder;
import mysqls.sql.entity.Table;

@SuppressWarnings("serial")
public class PropertySheets extends JPanel {
	private static final String INVISIBLE_PROPERTY_MARKER = "**INVISIBLE**";
	private static Map<Class<?>, Class<?>> editors;

	JDialog mDialog;
	private static ResourceBundle aPropertyNames = ResourceBundle.getBundle("mysqls.graph.GraphElementProperties");

	private ArrayList<ChangeListener> aChangeListeners = new ArrayList<>();

	static {
		PropertySheets.editors = new HashMap<>();
		PropertySheets.editors.put(String.class, PropertyEditorSupport.class);
	}

	/**
	 * Constructs a property sheet that shows the editable properties of a given
	 * object.
	 *
	 * @param pBean
	 *            the object whose properties are being edited
	 */
	public PropertySheets(final Object pBean) {
		setLayout(new FormLayout());
		try {
			PropertyDescriptor[] descriptors = Introspector.getBeanInfo(pBean.getClass()).getPropertyDescriptors()
					.clone();
			Arrays.sort(descriptors, new Comparator<PropertyDescriptor>() {
				@Override
				public int compare(PropertyDescriptor pDescriptor1, PropertyDescriptor pDescriptor2) {
					int index1 = PropertyOrder.getInstance().getIndex(pBean.getClass(), pDescriptor1.getName());
					int index2 = PropertyOrder.getInstance().getIndex(pBean.getClass(), pDescriptor2.getName());
					if (index1 == index2) {
						return pDescriptor1.getName().compareTo(pDescriptor2.getName());
					} else {
						return index1 - index2;
					}
				}
			});

			for (PropertyDescriptor descriptor : descriptors) {
				PropertyEditor editor = getEditor(pBean, descriptor);
				String propertyName = PropertySheets.getPropertyName(pBean.getClass(), descriptor.getName());
				if (editor != null && !propertyName.equals(PropertySheets.INVISIBLE_PROPERTY_MARKER)) {
					add(new JLabel(propertyName));
					add(getEditorComponent(editor));
				}
			}
		} catch (IntrospectionException exception) {
			// Do nothing
		}
	}

	public void showDialog() {

		JButton oks = new JButton("完成");
		JButton cancel = new JButton("取消");
		oks.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mDialog.dispose();
			}
		});
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mDialog.dispose();
			}
		});
		oks.setBackground(Color.gray);
		oks.setFont(new Font("Default", Font.PLAIN, 18));
		cancel.setBackground(Color.gray);
		cancel.setFont(new Font("Default", Font.PLAIN, 18));
		add(oks);
		add(cancel);
		JPanel option = new JPanel();
		option.setLayout(new GridLayout(1, 2));
		option.add(oks);
		option.add(cancel);
		JPanel root = new JPanel();
		root.setLayout(new BorderLayout());
		root.add(option, BorderLayout.NORTH);
		root.add(this, BorderLayout.CENTER);
		mDialog = new JDialog();
		mDialog.setModal(true);
		mDialog.setContentPane(root);
		mDialog.setTitle("编辑");
		// mDialog.setPreferredSize(root.getPreferredSize());
		mDialog.revalidate();
		mDialog.pack();
		mDialog.setVisible(true);
	}

	/**
	 * Gets the property editor for a given property, and wires it so that it
	 * updates the given object.
	 *
	 * @param pBean
	 *            the object whose properties are being edited
	 * @param pDescriptor
	 *            the descriptor of the property to be edited
	 * @return a property editor that edits the property with the given
	 *         descriptor and updates the given object
	 */
	@SuppressWarnings("unchecked")
	public PropertyEditor getEditor(final Object pBean, PropertyDescriptor pDescriptor) {
		try {
			final Method getter = pDescriptor.getReadMethod();
			final Method setter = pDescriptor.getWriteMethod();
			if (getter == null || setter == null) {
				return null;
			}

			Class<?> type = pDescriptor.getPropertyType();
			final PropertyEditor editor;
			Class<?> editorClass = pDescriptor.getPropertyEditorClass();
			if (editorClass == null && PropertySheets.editors.containsKey(type)) {
				editorClass = PropertySheets.editors.get(type);
			}
			if (editorClass == MYedgeEditor.class) {
				AssociationEdge edge = (AssociationEdge) pBean;
				Table stat = ((ClassNode) edge.getStart()).mTable;
				Table end = ((ClassNode) edge.getEnd()).mTable;
				Constructor<MYedgeEditor> constructor = null;
				try {
					constructor = (Constructor<MYedgeEditor>) editorClass.getConstructor(Table.class);
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (pDescriptor.getName().equals("sTableColumn")) {
					editor = constructor.newInstance(stat);
				} else {
					editor = constructor.newInstance(end);

				}

			}

			else

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

						if (pBean instanceof AssociationEdge) {
							fireStateChanged(new ChangeEvent(pBean));
							return;
						}
						if (pBean instanceof ClassNode) {
							fireStateChanged(new ChangeEvent(pBean));
						}

						fireStateChanged(null);
					} catch (IllegalAccessException | InvocationTargetException exception) {
						exception.printStackTrace();
					}
				}
			});
			return editor;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException exception) {
			// System.out.println(exception.getMessage() +
			// exception.getCause().toString());
			return null;
		}
	}

	/**
	 * Wraps a property editor into a component.
	 *
	 * @param pEditor
	 *            the editor to wrap
	 * @return a button (if there is a custom editor), combo box (if the editor
	 *         has tags), or text field (otherwise)
	 */
	public Component getEditorComponent(final PropertyEditor pEditor) {
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

	/**
	 * Adds a change listener to the list of listeners.
	 *
	 * @param pListener
	 *            the listener to add
	 */
	public void addChangeListener(ChangeListener pListener) {
		aChangeListeners.add(pListener);
	}

	/**
	 * Notifies all listeners of a state change.
	 *
	 * @param pEvent
	 *            the event to propagate
	 */
	private void fireStateChanged(ChangeEvent pEvent) {
		for (ChangeListener listener : aChangeListeners) {
			listener.stateChanged(pEvent);
		}
	}

	/**
	 * @return aEmpty whether this PropertySheets has fields to edit or not.
	 */
	public boolean isEmpty() {
		return getComponentCount() == 0;
	}

	/*
	 * Obtains the externalized name of a property and takes account of property
	 * inheritance: if a property is not found on a class, looks for the
	 * property name is superclasses.
	 */
	private static String getPropertyName(Class<?> pClass, String pProperty) {
		assert pProperty != null;
		if (pClass == null) {
			return pProperty;
		}
		String key = pClass.getSimpleName() + "." + pProperty;
		if (!PropertySheets.aPropertyNames.containsKey(key)) {
			return PropertySheets.getPropertyName(pClass.getSuperclass(), pProperty);
		} else {
			return PropertySheets.aPropertyNames.getString(key);
		}
	}
}
