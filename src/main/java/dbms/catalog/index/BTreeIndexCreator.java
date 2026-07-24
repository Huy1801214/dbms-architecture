package dbms.catalog.index;

public class BTreeIndexCreator extends IndexCreator {
    @Override
    protected Index createIndex(CreateIndexRequest request) {
        return new BTreeIndex(request.indexId, request.name, request.tableId, request.columns, request.unique);
    }
}
