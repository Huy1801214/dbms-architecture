package dbms.catalog.constraint;

import java.util.List;

public class PrimaryKeyRequest {
    public String name;
    public List<String> columnNames;
    public boolean clustered;
    public Integer fillFactor;

    public PrimaryKeyRequest() {}

    public PrimaryKeyRequest(String name, List<String> columnNames) {
        this.name = name;
        this.columnNames = columnNames;
    }

    public PrimaryKeyRequest(String name, List<String> columnNames, boolean clustered, Integer fillFactor) {
        this.name = name;
        this.columnNames = columnNames;
        this.clustered = clustered;
        this.fillFactor = fillFactor;
    }
}
