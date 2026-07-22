package dbms.catalog.constraint;

import dbms.catalog.table.Column;
import dbms.catalog.table.Row;
import dbms.catalog.table.Table;
import java.util.List;
import java.util.UUID;

public class CheckConstraint extends Constraint {
    public String expression;

    public CheckConstraint() {
        this.constraintType = ConstraintType.CHECK;
        this.enabled = true;
    }

    public CheckConstraint(CheckConstraintRequest request) {
        this.constraintType = ConstraintType.CHECK;
        this.enabled = true;
        if (request != null) {
            this.constraintName = request.name;
            this.expression = request.expression;
        }
    }

    public CheckConstraint(String constraintName, List<String> columnNames, String expression) {
        this.constraintName = constraintName;
        this.constraintType = ConstraintType.CHECK;
        this.expression = expression;
        this.enabled = true;
    }

    public CheckConstraint(String constraintName, List<Column> columns, UUID tableId, String expression) {
        this.constraintName = constraintName;
        this.columns = columns;
        this.constraintType = ConstraintType.CHECK;
        this.expression = expression;
        this.enabled = true;
    }

    @Override
    public void validate(Row row, Table table) {
    }
}
