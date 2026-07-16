package dbms.query.executor;

import dbms.query.planner.FilterPlan;

public class FilterExecutor extends Executor {

    private FilterPlan plan;

    public FilterExecutor() {
    }

    public FilterExecutor(FilterPlan plan) {
        super(plan);
        this.plan = plan;
    }

    public FilterPlan getPlan() {
        return plan;
    }

    public void setPlan(FilterPlan plan) {
        this.plan = plan;
    }

    @Override
    public Object next() {
        return null;
    }

}
