package dbms.catalog;

import java.util.List;

public abstract class ConstraintFactory {
    public abstract Constraint createConstraint(String name, String type, List<String> columns);
}
