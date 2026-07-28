package dbms.catalog.table.dto;

import dbms.catalog.table.entity.Column;

import java.util.List;

public class TableCreateRequest {
    public String name;
    public String engine;
    public List<Column> columns;

    public TableCreateRequest() {}

    public TableCreateRequest(String name, String engine, List<Column> columns) {
        this.name = name;
        this.engine = engine;
        this.columns = columns;
    }
}
