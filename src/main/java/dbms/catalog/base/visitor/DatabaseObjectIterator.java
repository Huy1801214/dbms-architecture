package dbms.catalog.base.visitor;

import dbms.catalog.base.entity.DatabaseObject;

public interface DatabaseObjectIterator {
    boolean hasNext();
    DatabaseObject next();
}
