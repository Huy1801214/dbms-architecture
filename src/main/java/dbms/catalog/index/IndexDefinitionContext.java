package dbms.catalog.index;

import dbms.catalog.table.Column;
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
