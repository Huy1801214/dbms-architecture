package dbms.catalog.constraint;

import dbms.catalog.table.Column;
import dbms.catalog.table.Row;
import dbms.catalog.table.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public abstract class Constraint {
    public UUID constraintId = UUID.randomUUID();
    public String constraintName;
    public ConstraintType constraintType;
    public UUID tableId;
    public List<Column> columns;
    public boolean enabled = true;
    public ConstraintStatus status = ConstraintStatus.ACTIVE;
    public Boolean validated = true;
    public LocalDateTime createdAt = LocalDateTime.now();
    public LocalDateTime modifiedAt = LocalDateTime.now();

    public abstract void validate(Row row, Table table);

    public UUID getConstraintId() {
        return constraintId;
    }

    public String getConstraintName() {
        return constraintName;
    }

    public ConstraintType getConstraintType() {
        return constraintType;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
