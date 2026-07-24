package dbms.catalog.index;

import dbms.catalog.table.Row;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Index {
    private UUID indexId;
    private String name;
    private UUID tableId;
    private List<UUID> columnIds;
    private boolean unique;
    private IndexStatus status;
    private IndexAccessMethod accessMethod;

    public Index() {
        this.indexId = UUID.randomUUID();
        this.status = IndexStatus.ACTIVE;
    }

    public Index(UUID indexId, String name, UUID tableId, List<UUID> columnIds, boolean unique,
            IndexAccessMethod accessMethod) {
        this.indexId = indexId != null ? indexId : UUID.randomUUID();
        this.name = name;
        this.tableId = tableId;
        this.columnIds = columnIds;
        this.unique = unique;
        this.status = IndexStatus.ACTIVE;
        this.accessMethod = accessMethod;
    }

    public UUID getId() {
        return indexId;
    }

    public String getName() {
        return name;
    }

    public UUID getTableId() {
        return tableId;
    }

    public List<UUID> getColumnIds() {
        return columnIds;
    }

    public IndexType getType() {
        return accessMethod != null ? accessMethod.getType() : null;
    }

    public IndexStatus getStatus() {
        return status;
    }

    public boolean isUnique() {
        return unique;
    }

    public void validateDefinition(IndexDefinitionContext context) {
    }

    public void build(IndexOperationContext context) {
    }

    public void rebuild(IndexOperationContext context) {
    }

    public void enable() {
        status = IndexStatus.ACTIVE;
    }

    public void disable() {
        status = IndexStatus.DISABLED;
    }

    public void markInvalid() {
        status = IndexStatus.INVALID;
    }

    public void drop() {
        status = IndexStatus.DROPPED;
    }

    public void insertEntry(Row row, IndexOperationContext context) {
    }

    public void updateEntry(Row oldRow, Row newRow, IndexOperationContext context) {
        ensureActive();
        deleteEntry(oldRow, context);
        insertEntry(newRow, context);
    }

    public void deleteEntry(Row row, IndexOperationContext context) {
    }

    public List<UUID> search(IndexKey key) {
        return null;
    }

    public List<UUID> rangeSearch(IndexKey fromKey, IndexKey toKey) {
        return null;
    }

    private IndexKey extractKey(Row row) {
        return null;
    }

    private void validateUniqueKey(IndexKey key) {
    }

    private void ensureActive() {
    }
}
