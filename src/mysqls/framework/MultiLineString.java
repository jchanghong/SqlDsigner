
package mysqls.framework;

import mysqls.ui_frame.EmptyPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 这个其实就是为了得到string的大小，方便画图. 增加。得到每行的string
 */
public class MultiLineString implements Cloneable {


	/**
	 * @return 一行一个值。以后可能还会变格式
	 */
	public List<String> getMuilineString() {
		List<String> list = new ArrayList<>();
		String string = toString();
		for (String aString : string.split("\\|")) {

			list.add(aString);
		}

		return list;

	}

	private enum Align {
		LEFT, CENTER, RIGHT
	}

	// Eventually these should be ported to the enum as well
	// For the moment they are kept as ints to preserve the
	// backward compatibility with the serialized version of
	// the test graphs.
	public static final int LEFT = 0;
	public static final int CENTER = 1;
	public static final int RIGHT = 2;

	private String aText = "";
	private Align aJustification = Align.CENTER;
	private boolean aBold = false;
	private boolean aUnderlined = false;

	/**
	 * Constructs an empty, centered, normal size multi-line string that is not
	 * underlined and not bold.
	 */
	public MultiLineString() {
	}

	/**
	 * Constructs an empty, centered, normal size multi-line string that is not
	 * underlined. pBold determines if it is bold.
	 *
	 * @param pBold
	 *            True if the string should be bold.
	 */
	public MultiLineString(boolean pBold) {
		aBold = pBold;
	}

	/**
	 * Sets the value of the text property.
	 *
	 * @param pText
	 *            the text of the multiline string
	 */
	public void setText(String pText) {
		aText = pText;
		getLabel();
	}

	/**
	 * Gets the value of the text property.
	 *
	 * @return the text of the multi-line string
	 */
	public String getText() {
		return aText;
	}

	/**
	 * Sets the value of the justification property.
	 *
	 * @param pJustification
	 *            the justification, one of LEFT, CENTER, RIGHT
	 */
	public void setJustification(int pJustification) {
		assert pJustification >= 0 && pJustification < Align.values().length;
		aJustification = Align.values()[pJustification];
	}

	/**
	 * Gets the value of the justification property.
	 *
	 * @return the justification, one of LEFT, CENTER, RIGHT
	 */
	public int getJustification() {
		return aJustification.ordinal();
	}

	/**
	 * Gets the value of the underlined property.
	 *
	 * @return true if the text is underlined
	 */
	public boolean isUnderlined() {
		return aUnderlined;
	}

	/**
	 * @return The value of the bold property.
	 */
	public boolean isBold() {
		return aBold;
	}

	/**
	 * Sets the value of the underlined property.
	 *
	 * @param pUnderlined
	 *            true to underline the text
	 */
	public void setUnderlined(boolean pUnderlined) {
		aUnderlined = pUnderlined;
	}

	@Override
	public String toString() {
		return aText.replace('\n', '|');

	}

	private JLabel getLabel() {
		JLabel label = new JLabel(convertToHtml().toString());
		Font font=new Font("Default", Font.PLAIN, 20);
		label.setFont(font);
		if (aJustification == Align.LEFT) {
			label.setHorizontalAlignment(SwingConstants.LEFT);
		} else if (aJustification == Align.CENTER) {
			label.setHorizontalAlignment(SwingConstants.CENTER);
		} else if (aJustification == Align.RIGHT) {
			label.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return label;
	}

	/*
	 * @return an HTML version of the text of the string, taking into account
	 * the properties (underline, bold, etc.)
	 */
	StringBuffer convertToHtml() {
		StringBuffer prefix = new StringBuffer();
		StringBuffer suffix = new StringBuffer();
		StringBuffer htmlText = new StringBuffer();

		// Add some spacing before and after the text.
		prefix.append("&nbsp;");
		suffix.insert(0, "&nbsp;");
		if (aUnderlined) {
			prefix.append("<u>");
			suffix.insert(0, "</u>");
		}
		if (aBold) {
			prefix.append("<b>");
			suffix.insert(0, "</b>");
		}

		htmlText.append("<html><p align=\"" + aJustification.toString().toLowerCase() + "\">");
		StringTokenizer tokenizer = new StringTokenizer(aText, "\n");
		boolean first = true;
		while (tokenizer.hasMoreTokens()) {
			if (first) {
				first = false;
			} else {
				htmlText.append("<br>");
			}
			htmlText.append(prefix);
			String next = tokenizer.nextToken();
			String next1 = next.replaceAll("<", "&lt;");
			String next2 = next1.replaceAll(">", "&gt;");
			htmlText.append(next2);
			htmlText.append(suffix);
		}
		htmlText.append("</p></html>");
		return htmlText;
	}

	/**
	 * Gets the bounding rectangle for this multiline string.
	 *
	 * @param pGraphics2D
	 *            the graphics context
	 * @return the bounding rectangle (with top left corner (0,0))
	 */
	public Rectangle2D getBounds(Graphics2D pGraphics2D) {
		if (aText.length() == 0) {
			return new Rectangle2D.Double();
		}
		Dimension dim = getLabel().getPreferredSize();
		return new Rectangle2D.Double(0, 0, dim.getWidth(), dim.getHeight());
	}

	/**
	 * Draws this multi-line string inside a given rectangle.
	 *
	 * @param pGraphics2D
	 *            the graphics context
	 * @param pRectangle
	 *            the rectangle into which to place this multi-line string
	 *            最后一个是真，说明就是一行
	 */
	public void draw(Graphics2D pGraphics2D, Rectangle2D pRectangle,boolean isname) {
//		Font font=new Font("Default", Font.PLAIN, 20);
//		JLabel mylable=new JLabel();
//		mylable.setFont(font);
//		List<String> list=getMuilineString();
//		int height= (int) (pRectangle.getHeight()/list.size());
//		Rectangle2D rec=new Rectangle2D.Double(0,0,pRectangle.getWidth(),0);
//		for (int i = 0; i < list.size(); i++) {
//			rec.setFrame(0,height*(i),rec.getWidth(),height);
//            mylable.setText(list.get(i));
//			mylable.setBounds((int) rec.getX(), (int) rec.getY(), (int) rec.getWidth(), (int) rec.getHeight());
//            pGraphics2D.translate(rec.getX(), rec.getY());
//            mylable.paint(pGraphics2D);
//            pGraphics2D.translate(-rec.getX(), -rec.getY());
//
//        }
        JLabel label = getLabel();
        if (isname) {
            Icon icon = new ImageIcon(EmptyPanel.class.getClassLoader().getResource("database/treedata.png"));
            label.setIcon(icon);
        }
		label.setBounds(0, 0, (int) pRectangle.getWidth(), (int) pRectangle.getHeight());
		pGraphics2D.translate(pRectangle.getX(), pRectangle.getY());
		label.paint(pGraphics2D);
		pGraphics2D.translate(-pRectangle.getX(), -pRectangle.getY());
	}

	@Override
	public boolean equals(Object pObject) {
		if (this == pObject) {
			return true;
		}
		if (pObject == null) {
			return false;
		}
		if (pObject.getClass() != getClass()) {
			return false;
		}
		MultiLineString theString = (MultiLineString) pObject;
		return aText.equals(theString.aText) && aJustification == theString.aJustification && aBold == theString.aBold
				&& aUnderlined == theString.aUnderlined;
	}

	@Override
	public int hashCode() {
		return convertToHtml().toString().hashCode();
	}

	@Override
	public MultiLineString clone() {
		try {
			return (MultiLineString) super.clone();
		} catch (CloneNotSupportedException exception) {
			return null;
		}
	}
}
