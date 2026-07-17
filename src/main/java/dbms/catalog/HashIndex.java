package dbms.catalog;
import java.util.List;

public class HashIndex extends Index {
    @Override
    public List<String> search(Object key) { return null; }
    @Override
    public void insertKey(Object key, String rowId) {}
    @Override
    public void deleteKey(Object key, String rowId) {}
    @Override
    public void rebuild() {}
}