package dbms.catalog.constraint;

import dbms.catalog.table.Column;
import java.util.List;
import java.util.UUID;

public abstract class ConstraintFactory {
    public abstract PrimaryKey createPrimaryKey(PrimaryKeyRequest request);
    public abstract ForeignKey createForeignKey(ForeignKeyRequest request);
    public abstract UniqueConstraint createUnique(UniqueConstraintRequest request);
    public abstract CheckConstraint createCheck(CheckConstraintRequest request);

    public abstract Constraint createConstraint(String name, String type, List<String> columns);

    public Constraint createConstraint(String name, ConstraintType type, List<Column> columns, UUID tableId) {
        return null;
    }
}
