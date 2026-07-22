package dbms.catalog.constraint;

import java.util.List;

public class CheckConstraintRequest {
    public String name;
    public List<String> columnNames;
    public String expression;

    public CheckConstraintRequest() {}

    public CheckConstraintRequest(String name, List<String> columnNames, String expression) {
        this.name = name;
        this.columnNames = columnNames;
        this.expression = expression;
    }
}
