package dbms.catalog.table;

import dbms.catalog.constraint.Constraint;
import dbms.catalog.index.Index;

public class DefaultTableBuilder implements TableBuilder {
    private Table table;

    @Override
    public TableBuilder setName(String name) {
        return null;
    }

    @Override
    public TableBuilder setEngine(String engine) {
        return null;
    }

    @Override
    public TableBuilder addColumn(Column column) {
        return null;
    }

    @Override
    public TableBuilder addConstraint(Constraint constraint) {
        return null;
    }

    @Override
    public TableBuilder addIndex(Index index) {
        return null;
    }

    @Override
    public Table build() {
        return null;
    }
}
