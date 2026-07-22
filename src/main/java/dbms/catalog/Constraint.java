package dbms.catalog;

import java.util.List;
import java.util.UUID;
import java.sql.Timestamp;

public abstract class Constraint {
    public UUID constraintId;
    public String constraintName;
    public ConstraintType constraintType;
    public UUID tableId;
    public List<Column> columns;
    public ConstraintStatus status;
    public Boolean enabled;
    public Boolean validated;
    public Boolean deferrable;
    public Boolean initiallyDeferred;
    public String owner;
    public String description;
    public Timestamp createdAt;
    public Timestamp modifiedAt;

    public abstract void validate(Row row, Table table);

    public String getConstraintName() {
        return constraintName;
    }

    public UUID getConstraintId() {
        return constraintId;
    }
}