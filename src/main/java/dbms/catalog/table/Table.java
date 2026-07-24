package dbms.catalog.table;

import dbms.catalog.base.DatabaseObject;
import dbms.catalog.base.DatabaseObjectVisitor;
import dbms.catalog.base.LifecycleStatus;
import dbms.catalog.base.DropMode;
import dbms.catalog.constraint.Constraint;
import dbms.catalog.index.Index;
import dbms.catalog.index.IndexDefinitionContext;
import dbms.catalog.index.IndexOperationContext;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class Table extends DatabaseObject {
    private static final java.util.Map<String, Table> allTables = new java.util.concurrent.ConcurrentHashMap<>();
    private static final java.util.Map<java.util.UUID, Table> tablesById = new java.util.concurrent.ConcurrentHashMap<>();

    public static Table getTableByName(String name) {
        return name != null ? allTables.get(name) : null;
    }

    public static Table getTableById(java.util.UUID id) {
        return id != null ? tablesById.get(id) : null;
    }

    public static void clearAllTablesRegistry() {
        allTables.clear();
        tablesById.clear();
    }

    public java.util.UUID tableId;
    public String engine;
    public long rowCount;

    private List<Column> columns = new java.util.ArrayList<>();
    private List<Constraint> constraints = new java.util.ArrayList<>();
    private List<Index> indexes = new java.util.ArrayList<>();
    private List<Row> rows = new java.util.ArrayList<>();

    public Table() {
        this.lifecycleStatus = LifecycleStatus.ACTIVE;
    }

    public Table(String tableId, String name, String engine) {
        this.objectId = tableId;
        this.name = name;
        this.engine = engine;
        this.rowCount = 0;
        this.lifecycleStatus = LifecycleStatus.ACTIVE;
        try {
            this.tableId = java.util.UUID.fromString(tableId);
        } catch (IllegalArgumentException e) {
            this.tableId = java.util.UUID.randomUUID();
        }
        if (name != null) {
            allTables.put(name, this);
        }
        if (this.tableId != null) {
            tablesById.put(this.tableId, this);
        }
    }

    public void validate(Row row) {
        validateConstraints(row);
    }

    public String getTableId() {
        return tableId != null ? tableId.toString() : objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (this.name != null) {
            allTables.remove(this.name);
        }
        this.name = name;
        if (name != null) {
            allTables.put(name, this);
        }
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
        for (Constraint constraint : constraints) {
            constraint.validate(row, this);
        }
    }

    public void addIndex(Index index) {
        if (index != null) {
            this.indexes.add(index);
        }
    }

    public void addIndex(Index index, IndexDefinitionContext context) {
        if (index != null) {
            index.validateDefinition(context);
            this.indexes.add(index);
        }
    }

    public void dropIndex(UUID indexId) {
        if (indexId == null)
            return;
        this.indexes.removeIf(idx -> indexId.equals(idx.getId()));
    }

    public Index findIndexById(UUID indexId) {
        return null;
    }

    public Index findIndexByName(String name) {
        return null;
    }

    public List<Index> listIndexes() {
        return new ArrayList<>(indexes);
    }

    public List<Index> getIndexes() {
        return indexes;
    }

    public void insertIntoIndexes(Row row, IndexOperationContext context) {
    }

    public void updateIndexes(Row oldRow, Row newRow, IndexOperationContext context) {
    }

    public void deleteFromIndexes(Row row, IndexOperationContext context) {
    }

    public void insert(Row row) {
    }

    public void update(String rowId, Row newRow) {
    }

    public void delete(String rowId) {
    }

    public void truncate() {
        rows.clear();
        rowCount = 0;
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
        return existsUniqueValue(column, value);
    }

    @Override
    public void create() {
    }

    @Override
    public void drop(DropMode mode) {
        this.lifecycleStatus = LifecycleStatus.DROPPED;
    }

    @Override
    public void rename(String newName) {
        this.name = newName;
    }

    @Override
    public void accept(DatabaseObjectVisitor visitor) {
        if (visitor != null) {
            visitor.visit(this);
        }
    }
}
