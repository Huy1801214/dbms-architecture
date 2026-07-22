package dbms.catalog.table;

import dbms.catalog.constraint.Constraint;
import dbms.catalog.index.Index;

public interface TableBuilder {
    TableBuilder setName(String name);
    TableBuilder setEngine(String engine);
    TableBuilder addColumn(Column column);
    TableBuilder addConstraint(Constraint constraint);
    TableBuilder addIndex(Index index);
    Table build();
}
