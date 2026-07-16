package dbms.query.executor;

import dbms.query.planner.ExecutionPlan;

public class ExecutionEngine {

    private ExecutionContext executionContext;
    private Executor executor;

    public ExecutionEngine() {
    }

    public ExecutionEngine(ExecutionContext executionContext, Executor executor) {
        this.executionContext = executionContext;
        this.executor = executor;
    }

    public ExecutionContext getExecutionContext() {
        return executionContext;
    }

    public void setExecutionContext(ExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public ResultSet execute(ExecutionPlan executionPlan) {
        return null;
    }

}
