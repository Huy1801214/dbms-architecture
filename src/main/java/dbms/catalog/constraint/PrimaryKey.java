package dbms.catalog.constraint;

import dbms.catalog.table.Column;
import dbms.catalog.table.Row;
import dbms.catalog.table.Table;
import java.util.List;
import java.util.UUID;

public class PrimaryKey extends Constraint {

    private boolean clustered;
    private Integer fillFactor;

    public PrimaryKey() {
        this.constraintType = ConstraintType.PRIMARY_KEY;
        this.enabled = true;
    }

    public PrimaryKey(PrimaryKeyRequest request) {
        this.constraintType = ConstraintType.PRIMARY_KEY;
        this.enabled = true;
        if (request != null) {
            this.constraintName = request.name;
            this.clustered = request.clustered;
            this.fillFactor = request.fillFactor;
        }
    }

    public PrimaryKey(String constraintName, List<String> columnNames) {
        this.constraintName = constraintName;
        this.constraintType = ConstraintType.PRIMARY_KEY;
        this.enabled = true;
    }

    public PrimaryKey(String constraintName, List<Column> columns, UUID tableId) {
        this.constraintName = constraintName;
        this.columns = columns;
        this.tableId = tableId;
        this.constraintType = ConstraintType.PRIMARY_KEY;
        this.enabled = true;
    }

    @Override
    public void validate(Row row, Table table) {
    }

    public boolean isClustered() {
        return clustered;
    }

    public Integer getFillFactor() {
        return fillFactor;
    }
}
