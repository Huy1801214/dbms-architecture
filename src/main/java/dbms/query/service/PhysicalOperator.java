package dbms.query.service;

import dbms.catalog.table.entity.Row;

public interface PhysicalOperator {
    void open();
    Row next();
    void close();
    String getOperatorName();
}
