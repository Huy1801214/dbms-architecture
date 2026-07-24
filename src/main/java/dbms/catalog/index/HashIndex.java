package dbms.catalog.index;

import dbms.catalog.table.Column;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class HashIndex extends Index {
    public HashIndex(UUID indexId, String name, UUID tableId, List<Column> columns, boolean unique) {
        super(indexId, name, tableId, columns, unique);
    }

    @Override
    public List<RowId> search(IndexKey key) {
        return new ArrayList<>();
    }

    @Override
    public void insertKey(IndexKey key, RowId rowId) {        
    }

    @Override
    public void deleteKey(IndexKey key, RowId rowId) {       
    }

    @Override
    public void rebuild() {
    }
}

