package dbms.query;

public class QueryOptimizer {
    public PhysicalPlan optimize(LogicalPlan plan) { return null; }
    public double estimateCost(LogicalPlan plan) { return 0.0; }
    public void chooseJoinOrder(LogicalPlan plan) {}
}