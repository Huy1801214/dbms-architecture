package dbms.catalog;

import java.util.List;

public class DefaultConstraintFactory extends ConstraintFactory {
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
        throw new IllegalArgumentException("Unknown constraint type: " + type);
    }
}
