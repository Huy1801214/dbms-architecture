package dbms.catalog;

import java.util.List;

public class Schema {
    public String schemaId;
    public String name;
    public String owner;

    public void createTable(Table table) {
    }

    public Table findTableByName(String name) {
        return null;
    }

    public List<Table> listAllTables() {
        return null;
    }

    public void dropTable(String tableId) {
    }

    public void renameTable(String oldName, String newName) {
    }

    public void createView(View view) {
    }

    public void dropView(String viewId) {
    }

    public View getView(String viewId) {
        return null;
    }

    public void createProcedure(StoredProcedure procedure) {
    }

    public void dropProcedure(String procId) {
    }

    public StoredProcedure getProcedure(String procId) {
        return null;
    }

    public void createSequence(Sequence sequence) {
    }

    public void dropSequence(String seqId) {
    }

    public Sequence getSequence(String seqId) {
        return null;
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

    public Table getTable(String tableId) {
        return null;
    }
}