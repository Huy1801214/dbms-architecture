package dbms.catalog.schema.service;

import dbms.catalog.base.entity.DatabaseObject;
import dbms.catalog.base.visitor.DatabaseObjectIterator;

import dbms.catalog.base.entity.DatabaseObject;
import dbms.catalog.base.visitor.DatabaseObjectIterator;
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
