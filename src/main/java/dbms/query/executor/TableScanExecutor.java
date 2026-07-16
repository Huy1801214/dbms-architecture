package dbms.query.executor;

import dbms.query.planner.SequentialScanPlan;

public class TableScanExecutor extends Executor {

    private SequentialScanPlan plan;

    public TableScanExecutor() {
    }

    public TableScanExecutor(SequentialScanPlan plan) {
        super(plan);
        this.plan = plan;
    }

    public SequentialScanPlan getPlan() {
        return plan;
    }

    public void setPlan(SequentialScanPlan plan) {
        this.plan = plan;
    }

    @Override
    public Object next() {
        return null;
    }

}
