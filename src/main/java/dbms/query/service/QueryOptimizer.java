package dbms.query.service;

import dbms.query.model.LogicalPlan;
import dbms.query.model.PhysicalPlan;

public class QueryOptimizer {
    public PhysicalPlan optimize(LogicalPlan plan) { return null; }
    public double estimateCost(LogicalPlan plan) { return 0.0; }
    public void chooseJoinOrder(LogicalPlan plan) {}
}
