package dbms.catalog;

public class TableCreateRequest {
    private String tableId;
    private String name;
    private String engine;

    public TableCreateRequest(String tableId, String name, String engine) {
        this.tableId = tableId;
        this.name = name;
        this.engine = engine;
    }

    public String getTableId() {
        return tableId;
    }

    public String getName() {
        return name;
    }

    public String getEngine() {
        return engine;
    }
}
