package dbms.catalog.index;

import dbms.catalog.table.Column;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

class BTreeMap<K, V> {
}

public class BTreeIndex extends Index {
    private final BTreeMap<IndexKey, List<RowId>> btree;
    private final int maxDegree; 

    public BTreeIndex(UUID indexId, String name, UUID tableId, List<Column> columns, boolean unique) {
        super(indexId, name, tableId, columns, unique);
        this.btree = new BTreeMap<>();
        this.maxDegree = 4; 
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

