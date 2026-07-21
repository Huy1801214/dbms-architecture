package dbms.catalog;

import java.util.List;

public class UniqueConstraint extends Constraint {
    public List<String> columns;

    @Override
    public void validate(Row row, Table table) {

    }
}