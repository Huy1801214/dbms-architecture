package dbms.catalog.table;

import dbms.catalog.constraint.Constraint;
import dbms.catalog.index.Index;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TableBuilder {
    private UUID tableId;
    private String name;
    private String owner;
    private UUID schemaId;
    private String engine;

    private List<Column> columns = new ArrayList<>();
    private List<Constraint> constraints = new ArrayList<>();
    private List<Index> indexes = new ArrayList<>();

    public TableBuilder setTableId(UUID tableId) {
        this.tableId = tableId;
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
    }

    public Table build() {
        validate();
        Table table = new Table(tableId != null ? tableId.toString() : UUID.randomUUID().toString(), name, engine);
        table.owner = owner;
        if (schemaId != null) {
            table.schemaId = schemaId.toString();
        }
        for (Column col : columns) {
            table.addColumn(col);
        }
        for (Constraint c : constraints) {
            table.addConstraint(c);
        }
        for (Index idx : indexes) {
            table.addIndex(idx);
        }
        return table;
    }
}
