/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.entity;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

/**
 * @author 长宏
 *
 */
public class EditTable extends JPanel {

	private JPanel mhead;
	private List<JPanel> mrows;
	private JScrollPane mJScrollPane;
	private JPanel mrowall;

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

				JPanel mJPanel = new JPanel();
				mJPanel.add(new JButton("ddd"));
				mrowall.add(mJPanel);

				validate();
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
		for (int i = 0; i < 10; i++) {
			JPanel mJPanel = new JPanel();
			mJPanel.add(new JButton("ddd"));
			mrowall.add(mJPanel);
		}
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
		for (int i = 0; i < AttubuteList.list.size(); i++) {
			JTextField textField = new JTextField();
			textField.setHorizontalAlignment(SwingConstants.CENTER);

			textField.setEditable(false);
			textField.setText(AttubuteList.list.get(i));
			mhead.add(textField);
		}
	}

	public void addrow() {

	}

}
