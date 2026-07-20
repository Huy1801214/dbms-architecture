package dbms.catalog;

public class Schema {
    public String schemaId;
    public String name;
    public String owner;

    public void createTable(Table table) {
    }

    public void dropTable(String tableId) {
    }

    public void createView(View view) {
    }

    public void createProcedure(StoredProcedure procedure) {
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public String getSchemaId() {
        return schemaId;
    }
}