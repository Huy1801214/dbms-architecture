package dbms.query.service;

import dbms.catalog.table.entity.Row;
import java.util.UUID;

public class SequentialScanOperator implements PhysicalOperator {
    private final UUID tableId;

    public SequentialScanOperator(UUID tableId) {
        this.tableId = tableId;
    }

    public UUID getTableId() {
        return tableId;
    }

    @Override
    public String getOperatorName() {
        return "SequentialScan";
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
