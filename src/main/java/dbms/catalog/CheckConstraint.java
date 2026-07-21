package dbms.catalog;

import java.util.List;

public class CheckConstraint extends Constraint {
    public String expression;

    public CheckConstraint(String constraintName, List<String> columns, String expression) {
        this.constraintName = constraintName;
        this.columns = columns;
        this.expression = expression;
    }

    @Override
    public void validate(Row row, Table table) {

    }
}