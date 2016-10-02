package mysqls.ui_mainitem;

import mysqls.ui_frame.OP_Panel;
import mysqls.ui_util.sql_complementProvider;
import org.fife.ui.autocomplete.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jiang on 2016/10/1 0001.
 * 下面的面板
 */
public class SQLeditPanel extends JPanel implements ActionListener {
    private static SQLeditPanel me=null;
    private OP_Panel oppanel;

    public static SQLeditPanel getInstance() {
        if (me == null) {
            me=new SQLeditPanel();
        }
        return me;
    }
   private RSyntaxTextArea textArea;

    private SQLeditPanel() {
        setLayout(new BorderLayout());
        textArea = new RSyntaxTextArea(20, 60);
        Font font = new Font(null, 0, 20);
        textArea.setFont(font);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL);
        textArea.setCodeFoldingEnabled(true);

        // A CompletionProvider is what knows of all possible completions, and
        // analyzes the contents of the text area at the caret position to
        // determine what completion choices should be presented. Most instances
        // of CompletionProvider (such as DefaultCompletionProvider) are designed
        // so that they can be shared among multiple text components.
        CompletionProvider provider = new sql_complementProvider();
        // An AutoCompletion acts as a "middle-man" between a text component
        // and a CompletionProvider. It manages any options associated with
        // the auto-completion (the popup trigger key, whether to display a
        // documentation window along with completion choices, etc.). Unlike
        // CompletionProviders, instances of AutoCompletion cannot be shared
        // among multiple text components.
        AutoCompletion ac = new AutoCompletion(provider);
        ac.setAutoCompleteEnabled(true);
        ac.setAutoActivationEnabled(true);
        ac.setAutoActivationDelay(10);
        ac.install(textArea);
        setOpaque(false);
        OP_Panel op_panel = new OP_Panel();
        setoppanel(op_panel);
        add(new RTextScrollPane(textArea), BorderLayout.CENTER);
        add(op_panel, BorderLayout.NORTH);

    }


    private void setoppanel(OP_Panel oppanel) {
        oppanel.additem("清空编辑器",this);
        oppanel.additem("op2",this);
        oppanel.additem("op3",this);
        oppanel.additem("op4",this);
        this.oppanel = oppanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("清空编辑器")) {
            textArea.setText("");
        }
        JOptionPane.showMessageDialog(null,e.getActionCommand());

    }
}
