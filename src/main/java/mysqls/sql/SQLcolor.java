package mysqls.sql;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;


/**
 * @author jiang sql面板的颜色。给关键字改颜色的方法和策略
 */
public class SQLcolor {
    JTextPane jTextPane;
    StyledDocument styledDocument;
    private static SQLcolor me;

    private SQLcolor(JTextPane jTextPane) {
        // TODO Auto-generated constructor stub
        this.jTextPane = jTextPane;
        styledDocument = jTextPane.getStyledDocument();
    }

    public static void setcolor(JTextPane jTextPanes) {
        me = new SQLcolor(jTextPanes);
        me.setcolor();
    }

    private void setcolor() {

        SimpleAttributeSet all = new SimpleAttributeSet();
        StyleConstants.setForeground(all, Color.black);
        StyleConstants.setFontSize(all, 20);
        styledDocument.setCharacterAttributes(0, styledDocument.getLength(), all, true);


        SimpleAttributeSet setkey = new SimpleAttributeSet();
        StyleConstants.setFontSize(setkey, 20);
        StyleConstants.setForeground(setkey, Color.blue);
        final String sqlstrings = jTextPane.getText();
        int index = 0;// 当前处理的位置
        for (String key : SQLkeyword.keyword) {
            int start = 0;
            for (index = 0; index < sqlstrings.length(); ) {
                start = sqlstrings.indexOf(key, index);
                if (start == -1) {

                    break;
                } else {
                    styledDocument.setCharacterAttributes(start, key.length(), setkey, true);
                    index = start + key.length() - 1;
                }

            }

        }
        SimpleAttributeSet setkey2 = new SimpleAttributeSet();
        StyleConstants.setFontSize(setkey2, 22);
        StyleConstants.setForeground(setkey2, Color.green);

        for (String key : SQLword_name.LIST) {
            int start = 0;
            for (index = 0; index < sqlstrings.length(); ) {

                start = sqlstrings.indexOf(key, index);
                if (start == -1) {

                    break;
                } else {
                    styledDocument.setCharacterAttributes(start, key.length(), setkey2, true);
                    index = start + key.length() - 1;
                }

            }

        }
    }

}
