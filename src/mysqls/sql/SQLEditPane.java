package mysqls.sql;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.StyledDocument;

import mysqls.framework.GraphFrame;

/**
 * @author jiang sql日志面板
 */
@SuppressWarnings("serial")
public class SQLEditPane extends JPanel {

	private boolean ison = false;
	JButton toggle = null;
	private static final String text_on = ">>>";
	private static final String text_ontip = "隐藏sql面板";
	private static final String text_off = "<<<";
	private static final String textofftip = "显示sql面板";
	private static final String logend = "\n";

	private String sqlstring = "" + "这只是测试内容，还在完善\n" + "create table user(id int,name char(20));\n"
			+ "create view user1(id int,name char(20));\n" + "drop fff fff\n" + "";
	private static final int high = 25;

	DocumentListener documentListener;
	JTextPane msqlpane = null;

	private void seton() {
		if (toggle != null) {
			toggle.setText(text_off);
			toggle.setToolTipText(text_ontip);
		}
	}

	private void setoff() {
		if (toggle != null) {
			toggle.setText(text_on);
			toggle.setToolTipText(textofftip);
		}
	}

	private boolean istoggleON() {
		if (toggle != null) {
			return toggle.getText().equalsIgnoreCase(text_off);

		} else {
			return false;
		}

	}

	public SQLEditPane(GraphFrame graphFrame) {

		ison = false;
		setLayout(new BorderLayout());
		documentListener = new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						SQLcolor.setcolor(msqlpane);
					}
				});
				//
				graphFrame.getSqLlogPane().appendA_log("remove :" + e.getDocument());
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						SQLcolor.setcolor(msqlpane);
					}
				});

				graphFrame.getSqLlogPane().appendA_log("insert :" + e.getDocument());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub

			}

		};
		createempty();

		createtoggleButton();

	}

	private void createempty() {
		// TODO Auto-generated method stub
		if (msqlpane == null) {
			msqlpane = new JTextPane();
			msqlpane.setEditable(true);
			StyledDocument document = msqlpane.getStyledDocument();
			document.addDocumentListener(documentListener);
		}

		// msqlpane.setText("");
	}

	private void createtoggleButton() {
		toggle = new JButton();
		toggle.setText("<<<");
		toggle.setAlignmentX(CENTER_ALIGNMENT);
		toggle.setToolTipText("显示sql面板");
		toggle.setPreferredSize(new Dimension(high, high));
		toggle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent pEvent) {
				if (ison) {

					ison = false;
					setoff();
					// createempty();
					// remove(msqlpane);
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							msqlpane.setText("");
						}
					});
				} else {
					ison=true;
					// add(new JScrollPane(msqlpane), BorderLayout.CENTER);
//					add(msqlpane, BorderLayout.CENTER);
					setsqlstring();
					SQLcolor.setcolor(msqlpane);
					seton();
					// add(new JScrollPane(emptypane), BorderLayout.CENTER);
					// add(new JButton("111111"),BorderLayout.CENTER);
				}
			}

		});

		add(msqlpane, BorderLayout.CENTER);
		add(toggle, BorderLayout.SOUTH);
	}

	private void setsqlstring() {
		// TODO Auto-generated method stub

		msqlpane.setText(sqlstring);
		// StyledDocument styledDocument=msqlpane.getStyledDocument();
		// SimpleAttributeSet attributeSet=new SimpleAttributeSet();
		// StyleConstants.setForeground(attributeSet, Color.yellow);
		// int index=sqlstring.indexOf("2");
		// int last=sqlstring.lastIndexOf("2");
		//
		// styledDocument.setCharacterAttributes(index, last-index+1,
		// attributeSet, false);
		//

	}
}
