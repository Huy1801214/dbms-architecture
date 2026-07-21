package dbms.catalog;

import java.util.List;
import java.util.ArrayList;

public class Schema {
    public String schemaId;
    public String name;
    public String owner;

    public String getSchemaId() {
        return schemaId;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    private List<DatabaseObject> objects = new ArrayList<>();
    private DatabaseObjectFactory factory = new DefaultDatabaseObjectFactory();

    public Schema() {
    }

    public Schema(String schemaId, String name, String owner) {
        this.schemaId = schemaId;
        this.name = name;
        this.owner = owner;
    }

    public Table createTable(TableCreateRequest request) {
        return null;
    }

    public View createView(ViewCreateRequest request) {
        return null;
    }

    public StoredProcedure createProcedure(ProcedureCreateRequest request) {
        return null;
    }

    public Sequence createSequence(SequenceCreateRequest request) {
        return null;
    }

    public void dropObject(String objectId) {
    }

    public DatabaseObject findObject(String name) {
        return null;
    }

    public List<DatabaseObject> listObjects() {
        return null;
    }

    public void addObject(DatabaseObject obj) {
    }

    public void removeObject(String objectId) {
    }

    private DatabaseObject findObjectById(String objectId) {
        return null;
    }

    // --- Legacy / Backwards Compatibility Methods for SchemaTest ---
    public void createTable(Table table) {
    }

    public Table getTable(String tableId) {
        return null;
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
}