package mysqls.ui_util;

import mysqls.contanst.ConnectINFO;
import mysqls.contanst.ConnectINFOListener;
import mysqls.sql.databaseserver2.MYtreeNodeDB;
import mysqls.sql.databaseserver2.MYtreeNodeTable;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.Completion;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.Util;

import javax.swing.text.JTextComponent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by jiang on 2016/10/1 0001.
 */
public class sql_complementProvider extends DefaultCompletionProvider implements ConnectINFOListener{
    private List<Completion> moprations = new ArrayList<>();
    private List<Completion> mobjectname = new ArrayList<>();

    private List<Completion> dbs = new ArrayList<>();
    private List<Completion> tables = new ArrayList<>();
    private List<Completion> columns = new ArrayList<>();

    public sql_complementProvider() {
        super();
        ConnectINFO.addLister(this);
        addCompletion(getaitem("where"));
        addCompletion(getaitem("from"));
        addCompletion(getaitem("like"));
        addCompletion(getaitem("not"));
        addCompletion(getaitem("exists"));
        setops();
        setopjectname();
    }

    private void setopjectname() {
        if (mobjectname.size() > 0) {
            return;
        }
        List<String> list= Arrays.asList("table","database","view","triger","function");
        list.stream().forEach(aa->mobjectname.add(getaitem(aa)));
        addCompletions(mobjectname);
    }

    @Override

    public String getAlreadyEnteredText(JTextComponent comp) {
        return super.getAlreadyEnteredText(comp);
    }

    @Override
    protected List<Completion> getCompletionsImpl(JTextComponent comp) {
        String text=comp.getText().trim();
        System.out.println(getlaststring(text));
        String last = getlaststring(text);
        /*如果前面是空或者；号结尾*/
        if (isemptyORstatement(last)) {
            return getCompletion_parent(comp,moprations);
        }
        if (last.equalsIgnoreCase("where") || last.equalsIgnoreCase("like")) {

            return getCompletion_parent(comp,columns);
        }
        if (lastisselect(last)) {
            return getCompletion_parent(comp,columns);
        }
        if (lastisfrom(last)) {
            return getCompletion_parent(comp, tables);
        }
        if (lastisOP(last)) {
            return getCompletion_parent(comp,mobjectname);

        }
        if (lastisObject(last)) {
            return getCompletion_parent(comp,completions);

        }
        if (lastisDB(last)) {
            return getCompletion_parent(comp,completions);

        }
        if (lastistable(last)) {
            return getCompletion_parent(comp,completions);

        }
        if (lastiscolumns(last)) {
            return getCompletion_parent(comp,completions);
        }
        return getCompletion_parent(comp,completions);
    }

    private boolean lastisfrom(String text) {
        if (text.equalsIgnoreCase("from")) {
            return true;
        }
        return false;
    }

    /*如果前面是空或者；号结尾*/
    private boolean isemptyORstatement(String text) {
        if (text.length() < 1) {
            return true;
        }
        if (text.endsWith(";")) {
            return true;
        }
        return false;
    }

    private boolean lastisOP(String text) {

        for (Completion mopration : moprations) {
            if (mopration.getReplacementText().equalsIgnoreCase(text)) {
                return true;
            }
        }
        return false;
    }

    private String getlaststring(String text) {
        String[] strings = text.split("\\s+");
        if (strings.length == 0) {
            return "";
        } else {
            return strings[strings.length - 1];
        }
    }

    private boolean lastisObject(String text) {
        return false;
    }

    private boolean lastisDB(String text) {
        return false;
    }

    private boolean lastistable(String text) {
        return false;
    }

    private boolean lastiscolumns(String text) {
        return false;
    }

    private boolean lastisselect(String text) {
        if (text.equalsIgnoreCase("select")) {
            return true;
        }
        return false;
    }

    private List<Completion> getCompletion_parent(JTextComponent comp,List<Completion> list) {
        List<Completion> retVal = new ArrayList<Completion>();
        String text = getAlreadyEnteredText(comp);


        if (text!=null) {

            int index = Collections.binarySearch(list, text, comparator);
            if (index<0) { // No exact match
                index = -index - 1;
            }
            else {
                // If there are several overloads for the function being
                // completed, Collections.binarySearch() will return the index
                // of one of those overloads, but we must return all of them,
                // so search backward until we find the first one.
                int pos = index - 1;
                while (pos>0 &&
                        comparator.compare(list.get(pos), text)==0) {
                    retVal.add(list.get(pos));
                    pos--;
                }
            }

            while (index<list.size()) {
                Completion c = list.get(index);
                if (Util.startsWithIgnoreCase(c.getInputText(), text)) {
                    retVal.add(c);
                    index++;
                }
                else {
                    break;
                }
            }

        }

        return retVal;
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

    @Override
    public void onchange(String name, Object news, Object oldies) {
        if (moprations == null) {
            return;
        }
        if (name.equals("databasename")) {
//            setops();
            setdbs();
            settables();
            setcolumns();
        }

    }

    private void setcolumns() {
        completions.removeAll(columns);
        columns.clear();
        MYtreeNodeDB db=ConnectINFO.getInstance().getDatabase();
        for (MYtreeNodeTable table : db.geTables()) {

            table.getcolumns().stream().forEach(aa->columns.add(getaitem(aa.getName())));
        }
        columns.sort(this.comparator);
        addCompletions(columns);
    }

    private void settables() {
        completions.removeAll(tables);
        tables.clear();
        MYtreeNodeDB db=ConnectINFO.getInstance().getDatabase();
        db.geTables().stream().forEach(aa->tables.add(getaitem(aa.getName())));
        tables.sort(this.comparator);
        addCompletions(tables);

    }

    private void setdbs() {
        completions.removeAll(dbs);
        dbs.clear();
        MYtreeNodeDB db=ConnectINFO.getInstance().getDatabase();
        dbs.add(getaitem(db.getName()));
        addCompletions(dbs);
    }


    private void setops() {
        if (moprations.size() > 0) {
            return;
        }
        List<String> list= Arrays.asList("select","drop","create","update");
        list.stream().forEach(aa->moprations.add(getaitem(aa)));
        moprations.sort(this.comparator);
        addCompletions(moprations);


    }

    private Completion getaitem(String aa) {
        return new BasicCompletion(this, aa);
    }

}
