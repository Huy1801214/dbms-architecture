package dbms.catalog.index;

public abstract class IndexCreator {
    public final Index create(CreateIndexRequest request) {
        validate(request);
        Index index = createIndex(request);
        initialize(index);
        return index;
    }

    protected abstract Index createIndex(CreateIndexRequest request);

    protected void validate(CreateIndexRequest request) {
        if (request == null || request.name == null) {
            throw new IllegalArgumentException("Request or request name cannot be null");
        }
    }

    protected void initialize(Index index) {
        if (index != null) {
            index.rebuild();
        }
    }
}
