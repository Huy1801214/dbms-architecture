package dbms.catalog;

import java.util.List;
import java.util.UUID;
import java.sql.Timestamp;
import java.util.stream.Collectors;

public class CheckConstraint extends Constraint {
    public String expression;

    public CheckConstraint(String constraintName, List<String> columnNames, String expression) {
        this.constraintId = UUID.randomUUID();
        this.constraintName = constraintName;
        this.constraintType = ConstraintType.CHECK;
        this.status = ConstraintStatus.ACTIVE;
        this.enabled = true;
        this.validated = true;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.modifiedAt = new Timestamp(System.currentTimeMillis());
        if (columnNames != null) {
            this.columns = columnNames.stream().map(name -> {
                Column col = new Column();
                col.name = name;
                return col;
            }).collect(Collectors.toList());
        }
        this.expression = expression;
    }

    public CheckConstraint(String constraintName, List<Column> columns, UUID tableId, String expression) {
        this.constraintId = UUID.randomUUID();
        this.constraintName = constraintName;
        this.constraintType = ConstraintType.CHECK;
        this.columns = columns;
        this.tableId = tableId;
        this.status = ConstraintStatus.ACTIVE;
        this.enabled = true;
        this.validated = true;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.modifiedAt = new Timestamp(System.currentTimeMillis());
        this.expression = expression;
    }

    @Override
    public void validate(Row row, Table table) {

    }
}