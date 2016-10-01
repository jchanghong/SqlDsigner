package mysqls.ui_util;

import org.fife.ui.autocomplete.Completion;
import org.fife.ui.autocomplete.DefaultCompletionProvider;

import javax.swing.text.JTextComponent;
import java.util.List;

/**
 * Created by jiang on 2016/10/1 0001.
 */
public class sql_complement extends DefaultCompletionProvider{
    @Override
    public String getAlreadyEnteredText(JTextComponent comp) {
        return super.getAlreadyEnteredText(comp);
    }

    @Override
    protected List<Completion> getCompletionsImpl(JTextComponent comp) {

        return SQL_complte_serch.find(completions, comp);
//        addCompletion(new BasicCompletion(this, "22222222222222"));
//        System.out.println(comp.getText()+"============================");
//        return super.getCompletionsImpl(comp);
    }

    @Override
    protected boolean isValidChar(char ch) {

        return super.isValidChar(ch);
//        return true;
    }


    @Override
    public boolean isAutoActivateOkay(JTextComponent tc) {
//        return super.isAutoActivateOkay(tc);
        return true;
    }

    @Override
    public List<Completion> getCompletionByInputText(String inputText) {
        System.out.println(inputText);
        return super.getCompletionByInputText(inputText);
    }
}
