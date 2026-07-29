package dbms.query.model;

import java.util.UUID;

public class LogicalScan extends LogicalOperator {
    private final UUID tableId;
    private final Object predicate;

    public LogicalScan(UUID tableId, Object predicate) {
        this.tableId = tableId;
        this.predicate = predicate;
    }

    public UUID getTableId() {
        return tableId;
    }

    public Object getPredicate() {
        return predicate;
    }

    @Override
    public LogicalOperatorType getType() {
        return LogicalOperatorType.SCAN;
    }
}
