package dbms.query.service;

import dbms.catalog.table.entity.Row;

public class HashJoinOperator implements PhysicalOperator {
    @Override
    public String getOperatorName() {
        return "HashJoin";
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
