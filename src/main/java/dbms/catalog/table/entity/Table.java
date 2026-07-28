package dbms.catalog.table.entity;

import dbms.catalog.base.entity.DatabaseObject;
import dbms.catalog.base.enums.DropMode;
import dbms.catalog.base.enums.LifecycleStatus;
import dbms.catalog.base.visitor.DatabaseObjectVisitor;
import dbms.catalog.constraint.entity.Constraint;
import dbms.catalog.index.context.IndexDefinitionContext;
import dbms.catalog.index.entity.Index;
import dbms.catalog.table.enums.TriggerEventType;
import dbms.catalog.table.enums.TriggerTime;
import dbms.catalog.table.service.RowSerializer;
import dbms.catalog.table.service.TableEventListener;
import dbms.catalog.table.validator.ForeignKeyValidator;
import dbms.catalog.table.validator.NullabilityValidator;
import dbms.catalog.table.validator.RowValidationHandler;
import dbms.catalog.table.validator.UniqueValidator;
import dbms.storage.backend.StorageBackend;

import dbms.catalog.base.entity.DatabaseObject;
import dbms.catalog.base.visitor.DatabaseObjectVisitor;
import dbms.catalog.base.enums.LifecycleStatus;
import dbms.catalog.base.enums.DropMode;
import dbms.catalog.constraint.entity.Constraint;
import dbms.catalog.index.entity.Index;
import dbms.catalog.index.context.IndexDefinitionContext;
import dbms.storage.backend.StorageBackend;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class Table extends DatabaseObject {
    protected UUID tableId;
    protected String engine;
    protected long rowCount;
    private StorageBackend storageBackend;
    private RowValidationHandler validationChain;
    private final List<Column> columns = new ArrayList<>();
    private final List<Constraint> constraints = new ArrayList<>();
    private final List<Index> indexes = new ArrayList<>();
    private final List<Row> rows = new ArrayList<>();
    private final List<TableEventListener> triggers = new ArrayList<>();

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
            this.tableId = UUID.fromString(tableId);
        } catch (IllegalArgumentException e) {
            this.tableId = UUID.randomUUID();
        }
        initializeValidationChain();
    }

    protected Table(TableBuilder builder) {
        this.tableId = builder.tableId != null ? builder.tableId : UUID.randomUUID();
        this.objectId = builder.objectId != null ? builder.objectId : this.tableId.toString();
        this.name = builder.name;
        this.owner = builder.owner;
        this.schemaId = builder.schemaId != null ? builder.schemaId.toString() : null;
        this.engine = builder.engine != null ? builder.engine : "InnoDB";
        this.storageBackend = builder.storageBackend;
        this.columns.addAll(builder.columns);
        this.constraints.addAll(builder.constraints);
        this.indexes.addAll(builder.indexes);
        this.lifecycleStatus = LifecycleStatus.ACTIVE;
        initializeValidationChain();
    }

    public static TableBuilder builder() {
        return new TableBuilder();
    }

    public static void clearAllTablesRegistry() {
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

    public String getEngine() {
        return engine;
    }

    public long getRowCount() {
        return rowCount;
    }

    public StorageBackend getStorageBackend() {
        return storageBackend;
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

    public List<Index> getIndexes() {
        return indexes;
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
            byte[] data = RowSerializer.serializeRow(row);
            storageBackend.writeRecord(data);
        }
        notifyTriggers(new TableEvent(TriggerEventType.INSERT, TriggerTime.AFTER, null, row));
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

    public Row findRowById(String rowId) {
        if (rowId == null)
            return null;
        for (Row r : rows) {
            if (rowId.equals(r.rowId)) {
                return r;
            }
        }
        return null;
    }

    public List<Row> getRows() {
        return new ArrayList<>(rows);
    }

    public List<Row> listAllRows() {
        return getRows();
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

    public static class TableBuilder {
        protected UUID tableId = UUID.randomUUID();
        protected String objectId;
        protected String name;
        protected String owner;
        protected UUID schemaId;
        protected String engine = "InnoDB";
        protected StorageBackend storageBackend;

        protected final List<Column> columns = new ArrayList<>();
        protected final List<Constraint> constraints = new ArrayList<>();
        protected final List<Index> indexes = new ArrayList<>();

        public TableBuilder setTableId(String tableId) {
            if (tableId != null) {
                this.objectId = tableId;
                try {
                    this.tableId = UUID.fromString(tableId);
                } catch (IllegalArgumentException e) {
                    // non-UUID string ID
                }
            }
            return this;
        }

        public TableBuilder setTableId(UUID tableId) {
            if (tableId != null) {
                this.tableId = tableId;
                this.objectId = tableId.toString();
            }
            return this;
        }

        public TableBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public TableBuilder setOwner(String owner) {
            this.owner = owner;
            return this;
        }

        public TableBuilder setSchemaId(UUID schemaId) {
            this.schemaId = schemaId;
            return this;
        }

        public TableBuilder setEngine(String engine) {
            this.engine = engine;
            return this;
        }

        public TableBuilder setStorageBackend(StorageBackend storageBackend) {
            this.storageBackend = storageBackend;
            return this;
        }

        public TableBuilder addColumn(Column column) {
            if (column != null) {
                this.columns.add(column);
            }
            return this;
        }

        public TableBuilder addConstraint(Constraint constraint) {
            if (constraint != null) {
                this.constraints.add(constraint);
            }
            return this;
        }

        public TableBuilder addIndex(Index index) {
            if (index != null) {
                this.indexes.add(index);
            }
            return this;
        }

        private void validate() {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalStateException("Table name cannot be empty");
            }
        }

        public Table build() {
            validate();
            return new Table(this);
        }
    }
}
