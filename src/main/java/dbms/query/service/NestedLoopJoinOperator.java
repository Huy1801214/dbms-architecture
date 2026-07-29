package dbms.query.service;

import dbms.catalog.table.entity.Row;

public class NestedLoopJoinOperator implements PhysicalOperator {
    @Override
    public String getOperatorName() {
        return "NestedLoopJoin";
    }

    @Override
    public void open() {
    }

    @Override
    public Row next() {
        return null;
    }

    @Override
    public void close() {
    }
}
