package dbms.catalog.index;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class BTreeIndexAccessMethod implements IndexAccessMethod {
    @Override
    public IndexType getType() {
        return IndexType.BTREE;
    }

    @Override
    public void build(IndexOperationContext context) {
    }

    @Override
    public void insert(IndexKey key, UUID rowId) {
    }

    @Override
    public void delete(IndexKey key, UUID rowId) {
    }

    @Override
    public List<UUID> search(IndexKey key) {
        return new ArrayList<>();
    }

    @Override
    public boolean supportsRangeSearch() {
        return true;
    }

    @Override
    public List<UUID> rangeSearch(IndexKey fromKey, IndexKey toKey) {
        return new ArrayList<>();
    }
}
