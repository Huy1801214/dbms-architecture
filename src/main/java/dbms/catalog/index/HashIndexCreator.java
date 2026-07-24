package dbms.catalog.index;

public class HashIndexCreator extends IndexCreator {
    @Override
    protected Index createIndex(CreateIndexRequest request) {
        return new HashIndex(request.indexId, request.name, request.tableId, request.columns, request.unique);
    }
}
