package mysqls.sql;//？？？？？

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.StyledDocument;

import mysqls.framework.GraphFrame;

/**
 * @author jiang sql edit面板
 */
@SuppressWarnings("serial")
public class SQLEditPane extends JPanel {

	GraphFrame mFrame;
	private boolean ison = false;
	JButton toggle = null;
	private static final String text_on = ">>>";
	private static final String text_ontip = "隐藏sql面板";
	private static final String text_off = "<<<";
	private static final String textofftip = "显示sql面板";
	// private static final String logend = "\n";

	private String sqlstring = "" + "这只是测试内容，还在完善\n" + "create table user(id int,name char(20));\n"
			+ "create view user1(id int,name char(20));\n" + "drop fff fff\n" + "";
	private static final int high = 25;

	public void setsql(final String sqlstring) {
		this.sqlstring = sqlstring;//？？？？
		if (msqlpane != null && ison) {
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					msqlpane.setText(sqlstring);
					SQLcolor.setcolor(msqlpane);
				}
			});
		}
	}

	DocumentListener documentListener;
	public JTextPane msqlpane = null;//sql文本编辑区的引用。
	JScrollPane mJScrollPane;//滚动条。
	JPanel mempty;//jpanel

	private void seton()//关闭sqledit界面的按钮。//？？
	{
		if (toggle != null) {
			toggle.setText(SQLEditPane.text_off);
			toggle.setToolTipText(SQLEditPane.text_ontip);//鼠标在该按钮上停顿一下，就会显示“隐藏sql面板”的提示信息 
		}
	}

	private void setoff() //打开sqledit界面的按钮。//？？
	{
		if (toggle != null) {
			toggle.setText(SQLEditPane.text_on);
			toggle.setToolTipText(SQLEditPane.textofftip);//鼠标在该按钮上停顿一下，就会显示“显示sql面板”的提示信息 
		}
	}

	// private boolean istoggleON() {
	// if (toggle != null) {
	// return toggle.getText().equalsIgnoreCase(SQLEditPane.text_off);
	//
	// } else {
	// return false;
	// }
	//
	// }

	public SQLEditPane(final GraphFrame graphFrame) {

		mFrame = graphFrame;
		ison = false;
		setLayout(new BorderLayout());
		documentListener = new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				EventQueue.invokeLater(new Runnable() {
					@Override
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
					@Override
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
		//创建可编辑的文本区
		if (msqlpane == null) {
			msqlpane = new JTextPane();
			msqlpane.setEditable(true);//可编辑的。
			StyledDocument document = msqlpane.getStyledDocument();

			document.addDocumentListener(documentListener);//给sql面板设置的颜色。给关键字改颜色的方法和策略

			JPanel jPanel = new JPanel(new BorderLayout());
			jPanel.add(msqlpane);
			mJScrollPane = new JScrollPane(jPanel);//添加滚动条。
			
			//先将滚动条的长宽封装在Dimension中，如果 width 值或 height 值小于之前调用 setMinimumSize 指定的最小大小，则它将自动增大。 
			//mJScrollPane.setMinimumSize(new Dimension(300, (int) mJScrollPane.getPreferredSize().getHeight()));

			mempty = new JPanel(new BorderLayout());
		}

		// msqlpane.setText("");
	}

	private void createtoggleButton()
	{

		toggle = new JButton();
		toggle.setText("<<<");
		toggle.setAlignmentX(Component.CENTER_ALIGNMENT);
		toggle.setToolTipText("显示sql面板");
		toggle.setPreferredSize(new Dimension(SQLEditPane.high, SQLEditPane.high));
		toggle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent pEvent) {
				if (ison) {

					ison = false;
					setoff();
					// EventQueue.invokeLater(new Runnable() {
					// public void run() {
					// msqlpane.setText("");
					//// remove(mJScrollPane);
					// }
					// });
					//
					remove(mJScrollPane);
					add(mempty, BorderLayout.CENTER);
				} else {
					ison = true;

					EventQueue.invokeLater(new Runnable() {
						@Override
						public void run() {
							// add(mJScrollPane,BorderLayout.CENTER);
							// add(new JScrollPane(msqlpane),
							// BorderLayout.CENTER);
							// add(msqlpane, BorderLayout.CENTER);
							setsqlstring();
							SQLcolor.setcolor(msqlpane);
							remove(mempty);
							add(mJScrollPane, BorderLayout.CENTER);
							seton();
							mFrame.validate();
							mFrame.invalidate();

						}
					});
					// add(new JScrollPane(emptypane), BorderLayout.CENTER);
					// add(new JButton("111111"),BorderLayout.CENTER);
				}
			}

		});

		add(mempty, BorderLayout.CENTER);
		add(toggle, BorderLayout.SOUTH);
	}

	private void setsqlstring() //设置sqledit面板初始化内容。
	{
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
