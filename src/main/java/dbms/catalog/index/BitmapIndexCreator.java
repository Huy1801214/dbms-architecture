package dbms.catalog.index;

public class BitmapIndexCreator extends IndexCreator {
    @Override
    protected Index createIndex(CreateIndexRequest request) {
        return new BitmapIndex(request.indexId, request.name, request.tableId, request.columns, request.unique);
    }
}
