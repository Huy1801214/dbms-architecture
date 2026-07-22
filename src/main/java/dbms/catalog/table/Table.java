package dbms.catalog.table;

import dbms.catalog.base.DatabaseObject;
import dbms.catalog.base.DatabaseObjectVisitor;
import dbms.catalog.constraint.Constraint;
import dbms.catalog.index.Index;

import java.util.List;

public class Table extends DatabaseObject {
    public java.util.UUID tableId;
    public String engine;
    public long rowCount;

    private List<Column> columns = new java.util.ArrayList<>();
    private List<Constraint> constraints = new java.util.ArrayList<>();
    private List<Index> indexes = new java.util.ArrayList<>();
    private List<Row> rows = new java.util.ArrayList<>();

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
        return tableId != null ? tableId.toString() : objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEngine() {
        return engine;
    }

    public long getRowCount() {
        return rowCount;
    }

    public void addColumn(Column column) {
        if (column != null) {
            this.columns.add(column);
        }
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void addConstraint(Constraint constraint) {
        if (constraint != null) {
            this.constraints.add(constraint);
        }
    }

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public void validateConstraints(Row row) {
    }

    public void validate(Row row) {
        validateConstraints(row);
    }

    public void addIndex(Index index) {
        if (index != null) {
            this.indexes.add(index);
        }
    }

    public List<Index> getIndexes() {
        return indexes;
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

    public List<Row> listAllRows() {
        return new java.util.ArrayList<>(rows);
    }

    public List<Row> getRows() {
        return listAllRows();
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

    @Override
    public void accept(DatabaseObjectVisitor visitor) {
    }
}
