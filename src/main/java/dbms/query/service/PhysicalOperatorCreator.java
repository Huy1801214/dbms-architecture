package dbms.query.service;

import dbms.query.model.LogicalOperator;

public abstract class PhysicalOperatorCreator {
    public final PhysicalOperator create(LogicalOperator logicalOperator, PlanningContext context) {
        validate(logicalOperator, context);
        PhysicalOperator operator = createOperator(logicalOperator, context);
        initialize(operator);
        return operator;
    }

    protected void validate(LogicalOperator logicalOperator, PlanningContext context) {
    }

    protected void initialize(PhysicalOperator operator) {
    }

    protected abstract PhysicalOperator createOperator(LogicalOperator logicalOperator, PlanningContext context);
}
