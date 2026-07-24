package dbms.catalog.index;

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
