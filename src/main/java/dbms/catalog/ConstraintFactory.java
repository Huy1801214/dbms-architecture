package dbms.catalog;

import java.util.List;
import java.util.UUID;

public abstract class ConstraintFactory {
    public abstract Constraint createConstraint(String name, String type, List<String> columns);

    public Constraint createConstraint(String name, ConstraintType type, List<Column> columns, UUID tableId) {
        return createConstraint(name, type != null ? type.name() : null, (List<String>) null);
    }
}

