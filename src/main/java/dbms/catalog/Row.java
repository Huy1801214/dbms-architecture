package dbms.catalog;

import java.util.List;

public class Row {
    public String rowId;
    public List<Object> values;
    public String transactionId;
    public long version;

    public Object getColumnValue(String columnName) {
        return null;
    }
}