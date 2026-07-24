package dbms.catalog.table;

import dbms.catalog.base.DatabaseObject;
import dbms.catalog.base.DatabaseObjectVisitor;
import dbms.catalog.base.LifecycleStatus;
import dbms.catalog.base.DropMode;
import dbms.catalog.constraint.Constraint;
import dbms.catalog.index.Index;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class Table extends DatabaseObject {
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
        for (Constraint constraint : constraints) {
            constraint.validate(row, this);
        }
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
        if (row == null) return;
        for (Row r : rows) {
            if (row.rowId != null && row.rowId.equals(r.rowId)) {
                throw new IllegalStateException("Duplicate row ID: " + row.rowId);
            }
        }
        validateConstraints(row);
        rows.add(row);
        rowCount++;
    }

    public void update(String rowId, Row newRow) {
        if (rowId == null || newRow == null) return;
        int index = -1;
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).rowId != null && rows.get(i).rowId.equals(rowId)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new IllegalStateException("Row not found: " + rowId);
        }
        validateConstraints(newRow);
        rows.set(index, newRow);
    }

    public void delete(String rowId) {
        if (rowId == null) return;
        Row found = null;
        for (Row r : rows) {
            if (r.rowId != null && r.rowId.equals(rowId)) {
                found = r;
                break;
            }
        }
        if (found != null) {
            rows.remove(found);
            rowCount--;
        }
    }

    public void truncate() {
        rows.clear();
        rowCount = 0;
    }

    public void analyze() {
    }

    public Row findRowById(String rowId) {
        if (rowId == null) return null;
        for (Row r : rows) {
            if (r.rowId != null && r.rowId.equals(rowId)) {
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
        if (value == null) return false;
        int pkIndex = 0;
        for (Constraint c : constraints) {
            if (c.getConstraintType() == dbms.catalog.constraint.ConstraintType.PRIMARY_KEY && c.getColumns() != null && !c.getColumns().isEmpty()) {
                Column pkCol = c.getColumns().get(0);
                for (int i = 0; i < columns.size(); i++) {
                    if (columns.get(i).name.equals(pkCol.name)) {
                        pkIndex = i;
                        break;
                    }
                }
            }
        }
        for (Row r : rows) {
            if (r.values != null && pkIndex < r.values.size()) {
                if (value.equals(r.values.get(pkIndex))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean existsUniqueValue(String column, Object value) {
        if (column == null || value == null) return false;
        int colIndex = -1;
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).name.equalsIgnoreCase(column)) {
                colIndex = i;
                break;
            }
        }
        if (colIndex == -1) {
            for (Row r : rows) {
                if (r.values != null) {
                    for (Object v : r.values) {
                        if (value.equals(v)) return true;
                    }
                }
            }
            return false;
        }
        for (Row r : rows) {
            if (r.values != null && colIndex < r.values.size()) {
                if (value.equals(r.values.get(colIndex))) {
                    return true;
                }
            }
        }
        return false;
    }

    public Row findRowByPrimaryKey(Object value) {
        if (value == null) return null;
        int pkIndex = 0;
        for (Constraint c : constraints) {
            if (c.getConstraintType() == dbms.catalog.constraint.ConstraintType.PRIMARY_KEY && c.getColumns() != null && !c.getColumns().isEmpty()) {
                Column pkCol = c.getColumns().get(0);
                for (int i = 0; i < columns.size(); i++) {
                    if (columns.get(i).name.equals(pkCol.name)) {
                        pkIndex = i;
                        break;
                    }
                }
            }
        }
        for (Row r : rows) {
            if (r.values != null && pkIndex < r.values.size()) {
                if (value.equals(r.values.get(pkIndex))) {
                    return r;
                }
            }
        }
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

