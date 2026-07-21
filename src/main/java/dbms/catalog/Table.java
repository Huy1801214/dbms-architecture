package dbms.catalog;

public class Table {
    public String tableId;
    public String name;
    public String engine;
    public long rowCount;

    public Table() {
    }

    public Table(String tableId, String name, String engine) {
        this.tableId = tableId;
        this.name = name;
        this.engine = engine;
        this.rowCount = 0;
    }

    public String getTableId() {
        return null;
    }

    public String getName() {
        return null;
    }

    public void setName(String name) {
    }

    public String getEngine() {
        return null;
    }

    public long getRowCount() {
        return 0;
    }

    public void insert(Row row) {
    }

    public void update(String rowId, Row newRow) {
    }

    public void delete(String rowId) {
    }

    public void truncate() {
    }

    public void analyze() {
    }
}