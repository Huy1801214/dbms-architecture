package dbms.catalog.table;

import dbms.catalog.base.DatabaseObject;
import dbms.catalog.base.DatabaseObjectVisitor;
import dbms.catalog.base.LifecycleStatus;
import dbms.catalog.base.DropMode;
import dbms.catalog.constraint.Constraint;
import dbms.catalog.index.Index;
import dbms.catalog.index.IndexDefinitionContext;
import dbms.catalog.index.IndexOperationContext;

import dbms.storage.StorageBackend;

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
    private StorageBackend storageBackend;
    private RowValidationHandler validationChain;

    private List<Column> columns = new java.util.ArrayList<>();
    private List<Constraint> constraints = new java.util.ArrayList<>();
    private List<Index> indexes = new java.util.ArrayList<>();
    private List<Row> rows = new java.util.ArrayList<>();
    private List<TableEventListener> triggers = new java.util.ArrayList<>();

    private void initializeValidationChain() {
        this.validationChain = new NullabilityValidator();
        this.validationChain.setNext(new UniqueValidator())
                .setNext(new ForeignKeyValidator());
    }

    public Table() {
        this.lifecycleStatus = LifecycleStatus.ACTIVE;
        initializeValidationChain();
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
        initializeValidationChain();
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

    public StorageBackend getStorageBackend() {
        return storageBackend;
    }

    public void setStorageBackend(StorageBackend storageBackend) {
        this.storageBackend = storageBackend;
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
        if (validationChain != null) {
            validationChain.validate(row, this);
        }
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

    public void registerTrigger(TableEventListener listener) {
        if (listener != null && !triggers.contains(listener)) {
            triggers.add(listener);
        }
    }

    public void unregisterTrigger(TableEventListener listener) {
        triggers.remove(listener);
    }

    public void notifyTriggers(TableEvent event) {
        for (TableEventListener listener : triggers) {
            listener.onEvent(event, this);
        }
    }

    public List<TableEventListener> getTriggers() {
        return triggers;
    }

    public void insert(Row row) {
        if (row == null || row.rowId == null) {
            return;
        }
        notifyTriggers(new TableEvent(TriggerEventType.INSERT, TriggerTime.BEFORE, null, row));
        for (Row r : rows) {
            if (row.rowId.equals(r.rowId)) {
                throw new IllegalStateException("Duplicate row ID: " + row.rowId);
            }
        }
        rows.add(row);
        rowCount = rows.size();
        if (storageBackend != null) {
            byte[] data = serializeRow(row);
            storageBackend.writeRecord(data);
        }
        notifyTriggers(new TableEvent(TriggerEventType.INSERT, TriggerTime.AFTER, null, row));
    }

    private byte[] serializeRow(Row row) {
        if (row.rowId == null) {
            return new byte[0];
        }
        return row.rowId.getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }

    public void update(String rowId, Row newRow) {
        if (rowId == null || newRow == null) {
            return;
        }
        boolean found = false;
        for (int i = 0; i < rows.size(); i++) {
            if (rowId.equals(rows.get(i).rowId)) {
                rows.set(i, newRow);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalStateException("Row not found for update: " + rowId);
        }
    }

    public void delete(String rowId) {
        if (rowId == null) {
            return;
        }
        rows.removeIf(r -> rowId.equals(r.rowId));
        rowCount = rows.size();
    }

    public void truncate() {
        rows.clear();
        rowCount = 0;
    }

    public void analyze() {
    }

    public Row findRowById(String rowId) {
        if (rowId == null) {
            return null;
        }
        for (Row r : rows) {
            if (rowId.equals(r.rowId)) {
                return r;
            }
        }
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
