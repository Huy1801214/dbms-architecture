package dbms.catalog.constraint;

import java.util.List;

public class UniqueConstraintRequest {
    public String name;
    public List<String> columnNames;

    public UniqueConstraintRequest() {}

    public UniqueConstraintRequest(String name, List<String> columnNames) {
        this.name = name;
        this.columnNames = columnNames;
    }
}
