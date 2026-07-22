package dbms.catalog.constraint;

import dbms.catalog.table.Column;
import dbms.catalog.table.Row;
import dbms.catalog.table.Table;
import java.util.List;
import java.util.UUID;

public class UniqueConstraint extends Constraint {
    public UniqueConstraint() {
        this.constraintType = ConstraintType.UNIQUE;
        this.enabled = true;
    }

    public UniqueConstraint(UniqueConstraintRequest request) {
        this.constraintType = ConstraintType.UNIQUE;
        this.enabled = true;
        if (request != null) {
            this.constraintName = request.name;
        }
    }

    public UniqueConstraint(String constraintName, List<String> columnNames) {
        this.constraintName = constraintName;
        this.constraintType = ConstraintType.UNIQUE;
        this.enabled = true;
    }

    public UniqueConstraint(String constraintName, List<Column> columns, UUID tableId) {
        this.constraintName = constraintName;
        this.columns = columns;
        this.constraintType = ConstraintType.UNIQUE;
        this.enabled = true;
    }

    @Override
    public void validate(Row row, Table table) {
    }
}
