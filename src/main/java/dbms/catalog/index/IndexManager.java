package dbms.catalog.index;

import dbms.catalog.table.Table;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class IndexManager {
    private final Map<UUID, Index> indexes = new ConcurrentHashMap<>();
    private final Map<IndexType, IndexCreator> creators = new HashMap<>();

    public IndexManager() {
        creators.put(IndexType.BTREE, new BTreeIndexCreator());
        creators.put(IndexType.HASH, new HashIndexCreator());
        creators.put(IndexType.BITMAP, new BitmapIndexCreator());
    }

    public Index createIndex(CreateIndexRequest request, Table table) {
        return null;
    }

    public void dropIndex(UUID indexId, Table table) {

    }

    public Index findIndex(UUID indexId) {
        return null;
    }

    public List<Index> listIndexes(UUID tableId) {
        return null;
    }

    public List<RowId> search(UUID indexId, IndexKey key) {
        return null;
    }

    public void insertEntry(UUID indexId, IndexKey key, RowId rowId) {

    }

    public void deleteEntry(UUID indexId, IndexKey key, RowId rowId) {

    }

    public void rebuildIndex(UUID indexId) {
    }
}
