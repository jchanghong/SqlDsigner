package mysqls.sql;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.naming.NamingEnumeration;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * @author jiang sql日志面板
 */
@SuppressWarnings("serial")
public class SQLlogPane extends JPanel {

	JButton toggle = null;
	JButton mclearlog;
	private boolean logon=false;//日志显示
	private static final String text_on = "显示日志";
	private static final String text_off = "隐藏日志";
	private static final String logend = "\n";
	private String logstring = "";
	private static final int high = 25;
	JTextArea mlog = null;
	JScrollPane mJScrollPane;
	JPanel mempty;
	JPanel mlogpanel;
	
	
	public void appendA_log(String log) {

		StringBuilder builder = new StringBuilder();

		Date time = new Date(System.currentTimeMillis());
		builder.append(time.toLocaleString());
		builder.append(":::");
		builder.append(log);
		builder.append(logend);
		logstring = logstring + builder.toString();
		if (istoggleON()) {
			mlog.setText(logstring);
		}

	}

	

	private void seton() {
		if (toggle != null) {
			toggle.setText(text_off);
			toggle.setToolTipText(text_off);
		}
	}

	private void setoff() {
		if (toggle != null) {
			toggle.setText(text_on);
			toggle.setToolTipText(text_on);
		}
	}

	private boolean istoggleON() {
		if (toggle != null) {
			return toggle.getText().equalsIgnoreCase(text_off);

		} else {
			return false;
		}

	}

	public SQLlogPane() {

		setLayout(new BorderLayout());

		logon=false;
		createempty();
		createtoggleButton();
		createclearbutton();
		this.setMaximumSize(new Dimension(10000,300));

	}

	private void createclearbutton() {
		// TODO Auto-generated method stub
	
			mclearlog = new JButton();
			mclearlog.setText("...");
			mclearlog.setAlignmentY(CENTER_ALIGNMENT);
			mclearlog.setToolTipText("clear sql日志");
			 mclearlog.setPreferredSize(new Dimension(high,high));
			 mclearlog.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					logstring="";
//					if (logon) {
						mlog.setText(logstring);
					 remove(mlogpanel);
					 mlogpanel.setBounds(mempty.getBounds());
					 add(mempty,BorderLayout.CENTER);
//					}
				}
			});
			 add(mclearlog, BorderLayout.WEST);
	}



	private void createempty() {
		// TODO Auto-generated method stub
		if (mlog == null) {
			mlog = new JTextArea();
			mlog.setEditable(false);
			mlog.setLineWrap(true);
			mlog.setText("");
			mempty=new JPanel(new BorderLayout());
			
			JTextArea jTextField=new JTextArea();
			jTextField.setEnabled(false);
			jTextField.setText("点击右边显示日志");
			mempty.add(jTextField,BorderLayout.CENTER);
			
			mJScrollPane=new JScrollPane(mlog);
			mlogpanel=new JPanel(new BorderLayout());
			mlogpanel.add(mJScrollPane,BorderLayout.CENTER);
			
			
		}

		
	}

	private void createtoggleButton() {
		toggle = new JButton();
		toggle.setText(text_on);

		toggle.setAlignmentY(CENTER_ALIGNMENT);
		toggle.setToolTipText("显示sql日志");
		 toggle.setPreferredSize(new Dimension(high, high));

		toggle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent pEvent) {
				if (logon) {

					logon=false;
					setoff();
					mempty.setBounds(mempty.getBounds());
					remove(mlogpanel);
					add(mempty,BorderLayout.CENTER);
//					createempty();
//					remove(mlog);
//					SwingUtilities.invokeLater(new Runnable() {
//						public void run() {
//							mlog.setText("点击右边显示日志");
//							
//						}
//					});
//					
				} else {
					logon=true;
//					mlog.setBounds(mempty.getBounds());
					remove(mempty);
					setlogpane();
					add(mlogpanel);
//					seton();
					// add(new JScrollPane(emptypane), BorderLayout.CENTER);
					// add(new JButton("111111"),BorderLayout.CENTER);
				}
			}

		});
	
		add(mempty, BorderLayout.CENTER);
		add(toggle, BorderLayout.EAST);
	}

	private void setlogpane() {
		// TODO Auto-generated method stub

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mlog.setText(logstring);
				seton();
			}
		});
	
		

	}
	
	
}
