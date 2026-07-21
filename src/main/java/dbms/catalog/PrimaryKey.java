package dbms.catalog;

import java.util.List;

public class PrimaryKey extends Constraint {

    public PrimaryKey(String constraintName, List<String> columns) {
        this.constraintName = constraintName;
        this.columns = columns;
    }

    @Override
    public void validate(Row row, Table table) {

    }
}