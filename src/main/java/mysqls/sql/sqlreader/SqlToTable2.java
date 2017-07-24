package mysqls.sql.sqlreader;

import mysqls.framework.PersistenceService;
import mysqls.sql.entity.*;
import mysqls.sql.util.SQLCreator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author 版本2, sql语句到table的实现, 正则表达式实现
 */
public final class SqlToTable2 {

    /**
     * 工具类不需要实现
     */
    private SqlToTable2() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {

        String string = "create table dd(" + " in int,ihhh int);";
        List<Table> tables = SqlToTable2.getAllTable(string);
        System.out.println(SQLCreator.create(tables.get(0)));

    }

    /**
     * 外部接口。不能改变函数签名
     *
     * @param sql
     * @return 加关系的表
     */
    public static List<Table> getAllTable(String sql) {
        List<Table> result = SqlToTable2.gettables(sql);
        List<String> sqList = StatementUtil.getAllStatement(sql).stream()
                .filter(a -> StatementUtil.isCreate(a) && StatementUtil.hasConstraint(a)).collect(Collectors.toList());
        PersistenceService.mEdgeDatas.clear();
        sqList.stream().forEach(a -> {
            EdgeData edgeData = new EdgeData();
            int nameindex = a.indexOf("(");
            String name = a.substring(0, nameindex);
            String[] name1 = name.split("\\s+");
            name = name1[name1.length - 1];
            Table table = TableUtil.findtable(result, name);
            Table fTable = null;
            TableColumn column = null;
            TableColumn fColumn = null;
            String constrnt = StatementUtil.getAconstraint(a);
            fTable = TableUtil.findtable(result, ConstraintStatementUtil.getftable(constrnt));
            column = TableUtil.findColumn(table, ConstraintStatementUtil.getcolumn(constrnt));
            fColumn = TableUtil.findColumn(fTable, ConstraintStatementUtil.getfcolumn(constrnt));
            column.setForeignKey(true);
            column.setForigncolumn(fColumn);
            column.setForigntable(fTable);
            edgeData.sColumn = column;
            edgeData.sTable = table;
            edgeData.eColumn = fColumn;
            edgeData.eTable = fTable;
            PersistenceService.mEdgeDatas.add(edgeData);
        });
        return result;
    }

    /**
     * @param sql
     * @return 没有加关系的表
     */
    private static List<Table> gettables(String sql) {
        String[] tables = sql.split("\\s*;\\s*");
        List<String> creates = Arrays.stream(tables).filter(StatementUtil::isCreate).collect(Collectors.toList());
        List<Table> result = new ArrayList<>(creates.size());
        creates.stream().forEach(a -> result.add(SqlToTable2.gettable(a)));
        return result;
    }

    /**
     * @param sqlstring create开头。表）结尾。
     * @return
     */
    private static Table gettable(String sqlstring) {
        Table table = new Table();

        int nameindex = sqlstring.indexOf("(");
        String name = sqlstring.substring(0, nameindex);
        String[] name1 = name.split("\\s+");
        name = name1[name1.length - 1];
        table.setName(name);

        String colues = sqlstring.substring(nameindex + 1, sqlstring.length());
        String[] coluss = colues.split("\\s*,\\s*");// 最后一个列有）。

        for (int i = 0; i < coluss.length; i++) {
            String string = coluss[i];
            if (ColumnStatementUtil.isConstraint(string)) {
                continue;
            }
            table.addColumn(SqlToTable2.getaColumn(string));

        }

        return table;
    }

    /**
     * @param string name int not null 这样的的格式
     * @return
     */
    private static TableColumn getaColumn(String string) {
        // TODO Auto-generated method stub
        TableColumn column = new TableColumn();

        String[] names = string.trim().split("\\s+");
        column.setName(names[0]);
        int index = names[1].lastIndexOf(")");
        if (index == -1) {

            column.setType(DataTypeUI.toenum(names[1]));
        } else {
            column.setType(DataTypeUI.toenum(names[1].substring(0, index)));

        }
        String pnotnull = "\\s*not\\s+null\\s*";
        String pprymary = "\\s*PRIMARY\\s+key\\s*";
        String punik = "\\s+unique\\s*";
        String pforien = "\\s+FOREIGN\\s+key\\s*";
        String pautincrement = "\\s+AUTO_INCREMENT\\s*";
        Pattern pattern = Pattern.compile(pnotnull, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            column.setNotnull(true);
        }

        pattern = Pattern.compile(pprymary, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(string);
        if (matcher.find()) {
            column.setPrimarykey(true);
        }

        pattern = Pattern.compile(punik, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(string);
        if (matcher.find()) {
            column.setUnique(true);
        }
        pattern = Pattern.compile(pforien, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(string);
        if (matcher.find()) {
            column.setForeignKey(true);
        }

        pattern = Pattern.compile(pautincrement, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(string);
        if (matcher.find() && column.getType().equals(DataType.INT)) {
            column.setAutoadd(true);
        }
        return column;
    }

}
