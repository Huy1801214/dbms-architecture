package dbms.catalog.index.context;

import dbms.catalog.index.entity.Index;
import dbms.catalog.index.enums.IndexType;
import dbms.catalog.table.entity.Column;

import dbms.catalog.table.entity.Column;
import java.util.List;
import java.util.UUID;

public class IndexDefinitionContext {
    private UUID tableId;
    private List<Column> columns;
    private List<Index> existingIndexes;

    public IndexDefinitionContext() {
    }

    public IndexDefinitionContext(UUID tableId, List<Column> columns, List<Index> existingIndexes) {
        this.tableId = tableId;
        this.columns = columns;
        this.existingIndexes = existingIndexes;
    }

    public boolean hasColumn(UUID columnId) {
        return false;
    }

    public boolean hasIndexName(String name) {
        return false;
    }

    public boolean hasEquivalentIndex(List<UUID> columnIds, IndexType type) {
        return false;
    }

    public boolean supportsType(UUID columnId, IndexType type) {
        return true;
    }
}
