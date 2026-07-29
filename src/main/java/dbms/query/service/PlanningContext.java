package dbms.query.service;

import dbms.query.model.LogicalOperator;
import java.util.List;
import java.util.UUID;

public class PlanningContext {
    private final StatisticsManager statisticsManager;

    public PlanningContext(StatisticsManager statisticsManager) {
        this.statisticsManager = statisticsManager;
    }

    public boolean hasUsableIndex(UUID tableId, List<UUID> columnIds) {
        return false;
    }

    public Long estimateRowCount(UUID tableId) {
        return statisticsManager != null ? statisticsManager.estimateRowCount(tableId) : 0L;
    }

    public Double estimateSelectivity(LogicalOperator logicalOperator) {
        return statisticsManager != null ? statisticsManager.estimateSelectivity(logicalOperator) : 1.0;
    }
}
