package dbms.query.service;

import dbms.query.model.LogicalOperator;
import dbms.query.model.LogicalScan;
import java.util.UUID;

public class ScanOperatorCreator extends PhysicalOperatorCreator {
    @Override
    protected PhysicalOperator createOperator(LogicalOperator logicalOperator, PlanningContext context) {
        if (logicalOperator instanceof LogicalScan scan) {
            if (context != null && context.hasUsableIndex(scan.getTableId(), null)) {
                return new IndexScanOperator(scan.getTableId(), UUID.randomUUID());
            }
            return new SequentialScanOperator(scan.getTableId());
        }
        return null;
    }
}
