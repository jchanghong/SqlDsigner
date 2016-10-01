package mysqls.ui_mainitem;

import mysqls.Main;
import mysqls.contanst.ConnectINFO;
import mysqls.ui_frame.ToolPanel;
import org.fife.ui.autocomplete.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.text.JTextComponent;
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
        ac.setAutoActivationDelay(100);
        ac.addAutoCompletionListener(new AutoCompletionListener() {
            @Override
            public void autoCompleteUpdate(AutoCompletionEvent autoCompletionEvent) {
//                System.out.println("shdhdh"+autoCompletionEvent.toString());

            }
        });

        ac.install(textArea);
        setOpaque(false);

    }

    private  class Myproter extends DefaultCompletionProvider {
        @Override
        public boolean isAutoActivateOkay(JTextComponent tc) {
//            return super.isAutoActivateOkay(tc);
            String dd=   getAlreadyEnteredText(tc);
            System.out.println(dd);
            if (dd.equals("")) {
                return  true;
            }
            return  false;
        }
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
        Myproter provider = new Myproter();

        // Add completions for all Java keywords. A BasicCompletion is just
        // a straightforward word completion.
        provider.addCompletion(new BasicCompletion(provider, "abstract"));
        provider.addCompletion(new BasicCompletion(provider, "assert"));
        provider.addCompletion(new BasicCompletion(provider, "break"));
        provider.addCompletion(new BasicCompletion(provider, "case"));
        // ... etc ...
        provider.addCompletion(new BasicCompletion(provider, "transient"));
        provider.addCompletion(new BasicCompletion(provider, "try"));
        provider.addCompletion(new BasicCompletion(provider, "void"));
        provider.addCompletion(new BasicCompletion(provider, "volatile"));
        provider.addCompletion(new BasicCompletion(provider, "while"));

        // Add a couple of "shorthand" completions. These completions don't
        // require the input text to be the same thing as the replacement text.
        provider.addCompletion(new ShorthandCompletion(provider, "sysout",
                "System.out.println(", "System.out.println("));
        provider.addCompletion(new ShorthandCompletion(provider, "syserr",
                "System.err.println(", "System.err.println("));

        return provider;

    }


}
