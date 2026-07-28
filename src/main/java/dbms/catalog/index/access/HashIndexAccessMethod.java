package dbms.catalog.index.access;

import dbms.catalog.index.context.IndexOperationContext;
import dbms.catalog.index.entity.IndexKey;
import dbms.catalog.index.enums.IndexType;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class HashIndexAccessMethod implements IndexAccessMethod {
    @Override
    public IndexType getType() {
        return IndexType.HASH;
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
        return false;
    }

    @Override
    public List<UUID> rangeSearch(IndexKey fromKey, IndexKey toKey) {
        throw new UnsupportedOperationException("Hash index does not support range search");
    }
}
