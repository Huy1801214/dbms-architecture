package dbms.query.service;

import dbms.query.model.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PhysicalPlanner {
    private final Map<LogicalOperatorType, PhysicalOperatorCreator> creators = new HashMap<>();

    public PhysicalPlanner() {
        creators.put(LogicalOperatorType.SCAN, new ScanOperatorCreator());
        creators.put(LogicalOperatorType.JOIN, new JoinOperatorCreator());
    }

    public PhysicalPlan build(LogicalPlan logicalPlan, PlanningContext context) {
        PhysicalPlan physicalPlan = new PhysicalPlan();
        if (logicalPlan != null) {
            for (LogicalOperator op : logicalPlan.getOperators()) {
                PhysicalOperatorCreator creator = creators.get(op.getType());
                if (creator != null) {
                    physicalPlan.getOperators().add(creator.create(op, context));
                }
            }
        }
        return physicalPlan;
    }

    public PhysicalOperator createScanOperator(LogicalScan logicalScan, boolean useIndex, UUID indexId) {
        PhysicalOperatorCreator creator;
        if (useIndex) {
            creator = new ScanOperatorCreator();
        } else {
            creator = new ScanOperatorCreator();
        }
        return creator.create(logicalScan, null);
    }
}
