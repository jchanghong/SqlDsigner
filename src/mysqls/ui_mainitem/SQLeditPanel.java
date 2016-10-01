package mysqls.ui_mainitem;

import mysqls.ui_util.sql_complement;
import org.fife.ui.autocomplete.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jiang on 2016/10/1 0001.
 * 下面的面板
 */
public class SQLeditPanel extends JPanel {
    private static SQLeditPanel me=null;
    public static SQLeditPanel getInstance() {
        if (me == null) {
            me=new SQLeditPanel();
        }
        return me;
    }

    private SQLeditPanel() {
       setLayout(new BorderLayout());
        RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
        Font font = new Font(null, 0, 20);
        textArea.setFont(font);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL);


        textArea.setCodeFoldingEnabled(true);
        add(new RTextScrollPane(textArea));

        // A CompletionProvider is what knows of all possible completions, and
        // analyzes the contents of the text area at the caret position to
        // determine what completion choices should be presented. Most instances
        // of CompletionProvider (such as DefaultCompletionProvider) are designed
        // so that they can be shared among multiple text components.
        CompletionProvider provider = createCompletionProvider();

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

    }
    /**
     * Create a simple provider that adds some Java-related completions.
     */
    private CompletionProvider createCompletionProvider() {

        // A DefaultCompletionProvider is the simplest concrete implementation
        // of CompletionProvider. This provider has no understanding of
        // language semantics. It simply checks the text entered up to the
        // caret position for a match against known completions. This is all
        // that is needed in the majority of cases.
        sql_complement provider = new sql_complement();

        // Add completions for all Java keywords. A BasicCompletion is just
        // a straightforward word completion.
        provider.addCompletion(new BasicCompletion(provider, "select"));
        provider.addCompletion(new BasicCompletion(provider, "drop"));
        provider.addCompletion(new BasicCompletion(provider, "table1"));
        provider.addCompletion(new BasicCompletion(provider, "table2"));
        // ... etc ...
        provider.addCompletion(new BasicCompletion(provider, "c1"));
        provider.addCompletion(new BasicCompletion(provider, "c2"));
        provider.addCompletion(new BasicCompletion(provider, "c3"));
        provider.addCompletion(new BasicCompletion(provider, "*"));
        provider.addCompletion(new BasicCompletion(provider, "db1"));
        provider.addCompletion(new BasicCompletion(provider, "db2"));

        // Add a couple of "shorthand" completions. These completions don't
        // require the input text to be the same thing as the replacement text.
        provider.addCompletion(new ShorthandCompletion(provider, "sysout",
                "System.out.println(", "System.out.println("));
        provider.addCompletion(new ShorthandCompletion(provider, "syserr",
                "System.err.println(", "System.err.println("));

        return provider;

    }


}
