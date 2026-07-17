package dbms.storage;
import java.util.List;

public class StorageEngine {
    public int pageSize;
    public List<Object> dataFiles;
    public Object freeSpaceMap;

    public Page readPage(int pageId) { return null; }
    public void writePage(Page page) {}
    public Page allocatePage() { return null; }
    public void checkpoint() {}
}