package dbms.catalog;

import java.util.List;
import java.util.UUID;
import java.sql.Timestamp;
import java.util.stream.Collectors;

public class PrimaryKey extends Constraint {

    public PrimaryKey(String constraintName, List<String> columnNames) {
        this.constraintId = UUID.randomUUID();
        this.constraintName = constraintName;
        this.constraintType = ConstraintType.PRIMARY_KEY;
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
    }

    public PrimaryKey(String constraintName, List<Column> columns, UUID tableId) {
        this.constraintId = UUID.randomUUID();
        this.constraintName = constraintName;
        this.constraintType = ConstraintType.PRIMARY_KEY;
        this.columns = columns;
        this.tableId = tableId;
        this.status = ConstraintStatus.ACTIVE;
        this.enabled = true;
        this.validated = true;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.modifiedAt = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public void validate(Row row, Table table) {

    }
}