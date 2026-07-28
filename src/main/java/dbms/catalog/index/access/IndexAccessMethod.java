package dbms.catalog.index.access;

import dbms.catalog.index.context.IndexOperationContext;
import dbms.catalog.index.entity.IndexKey;
import dbms.catalog.index.enums.IndexType;

import java.util.List;
import java.util.UUID;

public interface IndexAccessMethod {
    IndexType getType();
    void build(IndexOperationContext context);
    void insert(IndexKey key, UUID rowId);
    void delete(IndexKey key, UUID rowId);
    List<UUID> search(IndexKey key);
    boolean supportsRangeSearch();
    List<UUID> rangeSearch(IndexKey fromKey, IndexKey toKey);
}
