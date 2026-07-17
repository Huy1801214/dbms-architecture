package dbms.catalog;

public class Table {
    public String tableId;
    public String name;
    public String engine;
    public long rowCount;

    public void insert(Row row) {}
    public void update(String rowId, Row newRow) {}
    public void delete(String rowId) {}
    public void truncate() {}
    public void analyze() {}
}