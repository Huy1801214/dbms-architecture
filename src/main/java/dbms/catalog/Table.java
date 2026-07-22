package dbms.catalog;

import java.util.List;

public class Table extends DatabaseObject {
    public java.util.UUID tableId;
    public String engine;
    public long rowCount;
    public String collation;
    public CompressionType compression;
    public Boolean encrypted;
    public EncryptionType encryptionType;
    public String comment;
    public TableStatus status;

    private List<Column> columns = new java.util.ArrayList<>();
    private List<Constraint> constraints = new java.util.ArrayList<>();
    private List<Index> indexes = new java.util.ArrayList<>();
    private List<Partition> partitions = new java.util.ArrayList<>();
    private List<Trigger> triggers = new java.util.ArrayList<>();

    public Table() {
    }

    public Table(String tableId, String name, String engine) {
        this.objectId = tableId;
        this.name = name;
        this.engine = engine;
        this.rowCount = 0;
        try {
            this.tableId = java.util.UUID.fromString(tableId);
        } catch (IllegalArgumentException e) {
            this.tableId = java.util.UUID.randomUUID();
        }
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

    public Row findRowById(String rowId) {
        return null;
    }

    public java.util.List<Row> listAllRows() {
        return null;
    }

    public boolean existsPrimaryKey(Object value) {
        return false;
    }

    public boolean existsUniqueValue(String column, Object value) {
        return false;
    }

    public Row findRowByPrimaryKey(Object value) {
        return null;
    }

    public List<Row> getRows() {
        return null;
    }

    public boolean existsReferencedRow(String column, Object value) {
        return false;
    }

    @Override
    public void create() {
    }

    @Override
    public void drop() {
    }

    @Override
    public void rename(String newName) {
        this.name = newName;
    }
}