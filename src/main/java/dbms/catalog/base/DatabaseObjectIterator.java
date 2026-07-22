package dbms.catalog.base;

public interface DatabaseObjectIterator {
    boolean hasNext();
    DatabaseObject next();
}
