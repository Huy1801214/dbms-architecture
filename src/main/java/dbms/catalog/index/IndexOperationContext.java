package dbms.catalog.index;

import dbms.catalog.table.Row;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class IndexOperationContext {
    private UUID tableId;
    private UUID transactionId;

    public IndexOperationContext() {
    }

    public IndexOperationContext(UUID tableId, UUID transactionId) {
        this.tableId = tableId;
        this.transactionId = transactionId;
    }

    public UUID getTableId() {
        return tableId;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public List<Row> scanRows() {
        return new ArrayList<>();
    }
}
