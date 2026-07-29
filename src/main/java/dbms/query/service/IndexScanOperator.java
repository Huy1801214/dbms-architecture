package dbms.query.service;

import dbms.catalog.table.entity.Row;
import java.util.UUID;

public class IndexScanOperator implements PhysicalOperator {
    private final UUID tableId;
    private final UUID indexId;

    public IndexScanOperator(UUID tableId, UUID indexId) {
        this.tableId = tableId;
        this.indexId = indexId;
    }

    public UUID getTableId() {
        return tableId;
    }

    public UUID getIndexId() {
        return indexId;
    }

    @Override
    public String getOperatorName() {
        return "IndexScan";
    }

    @Override
    public void open() {
    }

    @Override
    public Row next() {
        return null;
    }

    @Override
    public void close() {
    }
}
