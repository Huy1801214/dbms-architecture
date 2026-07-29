package dbms.query.service;

import dbms.query.model.LogicalOperator;

public class JoinOperatorCreator extends PhysicalOperatorCreator {
    @Override
    protected PhysicalOperator createOperator(LogicalOperator logicalOperator, PlanningContext context) {
        return new NestedLoopJoinOperator();
    }
}
