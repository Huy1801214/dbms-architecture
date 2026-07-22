package dbms.catalog;

import java.util.List;

public class SchemaObjectIterator implements DatabaseObjectIterator {
    private List<DatabaseObject> objects;
    private int currentIndex;

    public SchemaObjectIterator(List<DatabaseObject> objects) {
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public DatabaseObject next() {
        return null;
    }
}
