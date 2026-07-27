package dbms.catalog.table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MetadataLoader {
    public List<Column> loadColumns(UUID tableId) {
        System.out.println("Disk I/O: Reading column metadata for table ID: " + tableId);
        List<Column> columns = new ArrayList<>();
        // Load columns using correct DataType enum values
        columns.add(new Column("id", DataType.INT, false));
        columns.add(new Column("name", DataType.VARCHAR, true));
        return columns;
    }
}
