/**
 *
 */
package mysqls.framework;

import mysqls.sql.entity.Table;

/**
 * @author jiang 从表中选择列
 */
public class MYedgeEditor extends PropertySelector {

    private Table mTable;

    /**
     * @param pTags
     * @param pValues
     * @param mTable
     */
    public MYedgeEditor(Table mTable) {
        super(null, null);
        int size = mTable.getColumnlist().size();
        String[] n = new String[size];
        Object[] pValues = new Object[size];
        for (int i = 0; i < size; i++) {
            n[i] = mTable.getColumnlist().get(i).getName();
            pValues[i] = mTable.getColumnlist().get(i);

        }
        this.aNames = n;
        this.aValues = pValues;
        this.mTable = mTable;
    }

}
