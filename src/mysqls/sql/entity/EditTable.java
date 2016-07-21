/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.entity;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import mysqls.sql.util.Util;

/**
 * @author 长宏 属性列表
 *
 */
public class EditTable extends JPanel {

	private JPanel mhead;
	private List<JPanel> mrows;
	private JScrollPane mJScrollPane;
	private JPanel mrowall;

	public static interface rowchangelister {
		public void onadd();

		public void onremove();
	}

	// private String name;
	// private String type;
	// private boolean primarykey;
	// private boolean foreignKey;
	// private boolean notnull;
	// private boolean unique;
	// private String defaultvalues;
	// private Table forigntable;
	// private TableColumn forigncolumn;
	/**
	 *
	 */
	public EditTable() {
		super();
		// TODO Auto-generated constructor stub
		setLayout(new GridLayout(0, 1));
		inithead();
		initrows();
		JButton button = new JButton("addrow");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//
				// JPanel mJPanel = new JPanel();
				// mJPanel.add(new JButton("ddd"));
				// mrowall.add(mJPanel);

				addrow();
				// validate();
			}
		});
		add(button);
		add(mhead);
		add(mJScrollPane);
	}

	/**
	 *
	 */
	private void initrows() {
		// TODO Auto-generated method stub

		mrowall = new JPanel();
		mrowall.setLayout(new GridLayout(0, 1));
		mJScrollPane = new JScrollPane(mrowall, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		mrows = new ArrayList<>();
	}

	/**
	 *
	 */
	private void inithead() {
		// TODO Auto-generated method stub

		mhead = new JPanel();
		mhead.setLayout(new GridLayout(1, 0));
		for (int i = 0; i < AttubuteList.namelist.size(); i++) {
			JTextField textField = new JTextField();
			textField.setHorizontalAlignment(SwingConstants.CENTER);

			textField.setEditable(false);
			textField.setText(AttubuteList.namelist.get(i));
			mhead.add(textField);
		}
	}

	public void addrow() {

		JPanel arow = new JPanel();
		arow.setLayout(new GridLayout(1, 0));
		for (int i = 0; i < AttubuteList.pdList.size(); i++) {

			PropertyDescriptor propertyDescriptor = AttubuteList.pdList.get(i);
			Component component = Util.getEditorComponent(propertyDescriptor);
			arow.add(component);
		}
		mrowall.add(arow);
		mrows.add(arow);
		validate();

	}

}
