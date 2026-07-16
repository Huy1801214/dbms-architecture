package dbms.query.executor;

import dbms.query.planner.ExecutionPlan;

public abstract class Executor {

    private ExecutionPlan executionPlan;

    public Executor() {
    }

    public Executor(ExecutionPlan executionPlan) {
        this.executionPlan = executionPlan;
    }

    public ExecutionPlan getExecutionPlan() {
        return executionPlan;
    }

    public void setExecutionPlan(ExecutionPlan executionPlan) {
        this.executionPlan = executionPlan;
    }

    public abstract Object next();

}