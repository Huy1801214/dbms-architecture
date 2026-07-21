package dbms.catalog;

import java.util.List;

public abstract class Constraint {
    protected String constraintName;
    protected List<String> columns;

    public abstract void validate(Row row, Table table);
}