package dbms.catalog.index;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class BitmapIndexAccessMethod implements IndexAccessMethod {
    @Override
    public IndexType getType() {
        return IndexType.BITMAP;
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
        throw new UnsupportedOperationException("Bitmap index does not support range search");
    }
}
