package dbms.catalog.schema;

import dbms.catalog.base.DatabaseObject;
import dbms.catalog.base.DatabaseObjectIterator;
import java.util.List;

public class SchemaObjectIterator implements DatabaseObjectIterator {
    private List<DatabaseObject> objects;
    private int position = 0;

    public SchemaObjectIterator(List<DatabaseObject> objects) {
        this.objects = objects;
    }

    @Override
    public boolean hasNext() {
        return position < objects.size();
    }

    @Override
    public DatabaseObject next() {
        return objects.get(position++);
    }
}
