/**
 *  实体关系图和sql生产的实现
 */
package mysqls.sql.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

/**
 * @author 长宏 属性列表
 *
 */
@SuppressWarnings("serial")
public class EditTable extends JPanel {

	private Table mdata;

	private JPanel mhead;
	private List<RowPanel> mrows;
	private JScrollPane mJScrollPane;
	private JPanel mrowall;

	int w;
	int h;
	private Rowchangelister rowchangelister;

	/**
	 * @return the rowchangelister
	 */
	public Rowchangelister getRowchangelister() {
		return this.rowchangelister;
	}

	/**
	 * @param rowchangelister
	 *            the rowchangelister to set
	 */
	public void setRowchangelister(Rowchangelister rowchangelister) {
		this.rowchangelister = rowchangelister;
	}

	public static interface Rowchangelister {
		public void onadd(RowPanel panel);

		public void onremove(RowPanel panel);
	}

	GridBagLayout layout;

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
	public EditTable(Table value) {
		super();
		mdata = value;
		// TODO Auto-generated constructor stub
		layout = new GridBagLayout();
		setLayout(layout);
		inithead();
		initrows();
		JButton button = new JButton("增加新列");
		button.setBackground(Color.gray);
		button.setFont(new Font("Default", Font.PLAIN, 24));
		button.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//
				// JPanel mJPanel = new JPanel();
				// mJPanel.add(new JButton("ddd"));
				// mrowall.add(mJPanel);

				addrow(new RowPanel(EditTable.this));
				// validate();
			}
		});
		add(button);
		add(mhead);
		add(mJScrollPane);
		GridBagConstraints sConstraints = new GridBagConstraints();
		sConstraints.fill = GridBagConstraints.BOTH;
		sConstraints.gridwidth = 0;
		sConstraints.weightx = 1;
		sConstraints.weighty = 0;
		layout.setConstraints(button, sConstraints);
		layout.setConstraints(mhead, sConstraints);
		sConstraints.weightx = 1;
		sConstraints.weighty = 1;
		sConstraints.gridwidth = 0;
		// sConstraints.gridheight = GridBagConstraints.REMAINDER;
		layout.setConstraints(mJScrollPane, sConstraints);

	}

	/**
	 *
	 */
	private void initrows() {
		// TODO Auto-generated method stub

		mrowall = new JPanel();
		mrows = new ArrayList<>();
		mrowall.setLayout(new GridLayout(0, 1));
		mJScrollPane = new JScrollPane(mrowall, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		for (int i = 0; i < mdata.getColumnlist().size(); i++) {
			RowPanel panel = new RowPanel(mdata.getColumnlist().get(i), this);
			mrowall.add(panel);
			mrows.add(panel);
		}

	}

	/**
	 *
	 */
	private void inithead() {
		// TODO Auto-generated method stub

		mhead = new JPanel();
		mhead.setBackground(Color.gray);
		GridLayout gridLayout = new GridLayout(1, 0);
		gridLayout.setHgap(0);
		gridLayout.setVgap(0);
		mhead.setLayout(gridLayout);
		JTextField textField1 = new JTextField();
		textField1.setHorizontalAlignment(SwingConstants.CENTER);

		textField1.setBackground(Color.gray);
		textField1.setOpaque(false);
		textField1.setEditable(false);
		// textField1.setBorder(BorderFactory.createLineBorder(Color.gray, 0));
		textField1.setText("操作");
		mhead.add(textField1);
		for (int i = 0; i < AttubuteList.namelist.size(); i++) {
			JTextField textField = new JTextField();
			textField.setHorizontalAlignment(SwingConstants.CENTER);

			textField.setBackground(Color.gray);
			textField.setOpaque(false);
			// textField.setBorder(BorderFactory.createLineBorder(Color.gray,
			// 0));
			textField.setEditable(false);
			String text = AttubuteList.namelist.get(i);
			textField.setText(AttubuteList.getName(text));
			mhead.add(textField);
		}
	}

	public void addrow(RowPanel panel) {

		for (RowPanel rowPanel : mrows) {
			if (panel.equals(rowPanel)) {
				return;
			}
		}

		mrowall.add(panel);
		mrows.add(panel);
		if (rowchangelister != null) {
			rowchangelister.onadd(panel);
		}
		mdata.addColumn(panel.getmTableColumn());

		SwingUtilities.windowForComponent(this).pack();
		// validate();

	}

	public void removerow(RowPanel panel) {

		mrowall.remove(panel);
		mrows.remove(panel);
		mdata.remove(panel.getmTableColumn());
		if (rowchangelister != null) {
			rowchangelister.onremove(panel);
		}
		SwingUtilities.windowForComponent(this).pack();
		// validate();
	}

}
