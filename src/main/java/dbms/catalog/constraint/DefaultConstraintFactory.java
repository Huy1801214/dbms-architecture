package dbms.catalog.constraint;

import dbms.catalog.table.Column;
import java.util.List;
import java.util.UUID;

public class DefaultConstraintFactory extends ConstraintFactory {
    @Override
    public PrimaryKey createPrimaryKey(PrimaryKeyRequest request) {
        return new PrimaryKey(request);
    }

    @Override
    public ForeignKey createForeignKey(ForeignKeyRequest request) {
        return new ForeignKey(request);
    }

    @Override
    public UniqueConstraint createUnique(UniqueConstraintRequest request) {
        return new UniqueConstraint(request);
    }

    @Override
    public CheckConstraint createCheck(CheckConstraintRequest request) {
        return new CheckConstraint(request);
    }

    @Override
    public Constraint createConstraint(String name, String type, List<String> columns) {
        if ("PRIMARY_KEY".equalsIgnoreCase(type)) {
            return new PrimaryKey(name, columns);
        } else if ("UNIQUE".equalsIgnoreCase(type)) {
            return new UniqueConstraint(name, columns);
        } else if ("FOREIGN_KEY".equalsIgnoreCase(type)) {
            return new ForeignKey(name, columns, "", "");
        } else if ("CHECK".equalsIgnoreCase(type)) {
            return new CheckConstraint(name, columns, "");
        }
        return null;
    }

    @Override
    public Constraint createConstraint(String name, ConstraintType type, List<Column> columns, UUID tableId) {
        if (type == ConstraintType.PRIMARY_KEY) {
            return new PrimaryKey(name, columns, tableId);
        } else if (type == ConstraintType.UNIQUE) {
            return new UniqueConstraint(name, columns, tableId);
        } else if (type == ConstraintType.FOREIGN_KEY) {
            return new ForeignKey(name, columns, tableId, "", "");
        } else if (type == ConstraintType.CHECK) {
            return new CheckConstraint(name, columns, tableId, "");
        }
        return null;
    }
}
