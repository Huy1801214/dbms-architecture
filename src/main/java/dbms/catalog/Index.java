package dbms.catalog;
import java.util.List;

public abstract class Index {
    public abstract List<String> search(Object key);
    public abstract void insertKey(Object key, String rowId);
    public abstract void deleteKey(Object key, String rowId);
    public abstract void rebuild();
}