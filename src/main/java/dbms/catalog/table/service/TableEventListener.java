package dbms.catalog.table.service;

import dbms.catalog.table.entity.Table;
import dbms.catalog.table.entity.TableEvent;

public interface TableEventListener {
    void onEvent(TableEvent event, Table table);
}
