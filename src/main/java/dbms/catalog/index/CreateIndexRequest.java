package dbms.catalog.index;

import dbms.catalog.table.Column;
import java.util.List;
import java.util.UUID;

public class CreateIndexRequest {
    public UUID indexId;
    public String name;
    public IndexType type;
    public UUID tableId;
    public List<UUID> columnIds;
    public List<Column> columns;
    public boolean unique;

    public CreateIndexRequest() {
        this.indexId = UUID.randomUUID();
    }

    public CreateIndexRequest(String name, IndexType type, UUID tableId, List<UUID> columnIds, boolean unique) {
        this.indexId = UUID.randomUUID();
        this.name = name;
        this.type = type;
        this.tableId = tableId;
        this.columnIds = columnIds;
        this.unique = unique;
    }
}

