package dbms.catalog;

public interface DatabaseObjectIterator {
    boolean hasNext();
    DatabaseObject next();
}
