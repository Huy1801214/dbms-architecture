package dbms.catalog.table.entity;


import java.util.List;

public class Row {
    public String rowId;
    public List<Object> values;

    public Object getColumnValue(String colName) {
        return null;
    }
}
