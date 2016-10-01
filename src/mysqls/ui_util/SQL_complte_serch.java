package mysqls.ui_util;

import org.fife.ui.autocomplete.Completion;
import org.fife.ui.autocomplete.Util;

import javax.swing.text.JTextComponent;
import java.util.*;

/**
 * Created by jiang on 2016/10/2 0002.
 */
public class SQL_complte_serch {
    private static List<Completion> empty = new ArrayList<>();
    private static List<Completion>  dbs = new ArrayList<>();
    private static List<Completion> tables = new ArrayList<>();
    private static List<Completion> columns = new ArrayList<>();
    private static List<Completion> oprations = new ArrayList<>();
    public static List<Completion> find(List<Completion> all, JTextComponent textComponent) {
        sortkey(all);
        String text=textComponent.getText();
        System.out.println(text + text.length());
        if (text.endsWith(";")) {
            return empty;
        }
        List<Completion> retVal = new ArrayList<Completion>();

        if (text!=null) {

            int index = Collections.binarySearch(completions, text, comparator);
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
                        comparator.compare(completions.get(pos), text)==0) {
                    retVal.add(completions.get(pos));
                    pos--;
                }
            }

            while (index<completions.size()) {
                Completion c = completions.get(index);
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

    private static void sortkey(List<Completion> all) {
        if (oprations.size() > 0) {
            return;
        }
        all.forEach(aa->{
            if (isdb(aa)) {
                dbs.add(aa);
            }
            if (istables(aa)) {
                tables.add(aa);
            }
            if (isoparetion(aa)) {
                oprations.add(aa);
            }
            if (iscolumns(aa)) {
                columns.add(aa);
            }
                
        });
    }

    private static boolean iscolumns(Completion aa) {
        return false;

    }

    private static Set<String> opsets = new HashSet<>();
    private static Set<String> dbsset = new HashSet<>();
    private static Set<String> tablesset = new HashSet<>();
    private static Set<String> columnsset = new HashSet<>();

    static {
        opsets.addAll(Arrays.asList("select", "delete","drop","update"));
    }
    private static boolean isoparetion(Completion aa) {
        return opsets.contains(aa.getReplacementText());
    }
    private static boolean istables(Completion aa) {
        return true;
    }
    

    private static boolean isdb(Completion aa) {
        return true;
    }
}
