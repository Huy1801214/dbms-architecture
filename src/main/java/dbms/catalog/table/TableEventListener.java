package dbms.catalog.table;

public interface TableEventListener {
    void onEvent(TableEvent event, Table table);
}
