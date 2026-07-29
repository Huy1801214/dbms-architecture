package dbms.query.service;

import dbms.catalog.table.entity.Table;
import dbms.query.model.LogicalOperator;
import java.util.UUID;

public class StatisticsManager {
    public void collect(Table table) {
    }

    public Long estimateRowCount(UUID tableId) {
        return 0L;
    }

    public Double estimateSelectivity(LogicalOperator logicalOperator) {
        return 1.0;
    }
}
