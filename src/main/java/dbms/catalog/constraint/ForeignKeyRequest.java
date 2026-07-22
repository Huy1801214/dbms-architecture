package dbms.catalog.constraint;

import java.util.List;

public class ForeignKeyRequest {
    public String name;
    public List<String> columnNames;
    public String referenceTable;
    public String referenceColumns;

    public ForeignKeyRequest() {}

    public ForeignKeyRequest(String name, List<String> columnNames, String referenceTable, String referenceColumns) {
        this.name = name;
        this.columnNames = columnNames;
        this.referenceTable = referenceTable;
        this.referenceColumns = referenceColumns;
    }
}
