package dbms.catalog.index;

import dbms.catalog.table.Column;
import java.util.List;
import java.util.UUID;

public abstract class Index {
    protected UUID indexId;
    protected String name;
    protected UUID tableId;
    protected List<Column> columns;
    protected boolean unique;

    public Index(UUID indexId, String name, UUID tableId, List<Column> columns, boolean unique) {
        this.indexId = indexId;
        this.name = name;
        this.tableId = tableId;
        this.columns = columns;
        this.unique = unique;
    }

    public UUID getId() {
        return indexId;
    }

    public String getName() {
        return name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public abstract List<RowId> search(IndexKey key);
    public abstract void insertKey(IndexKey key, RowId rowId);
    public abstract void deleteKey(IndexKey key, RowId rowId);
    public abstract void rebuild();
}

