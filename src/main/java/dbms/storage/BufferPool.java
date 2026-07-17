package dbms.storage;
import java.util.List;

public class BufferPool {
    public List<Page> pages;
    public int capacity;
    public Object replacementPolicy;

    public void pinPage(int pageId) {}
    public void unpinPage(int pageId) {}
    public void flushPage(int pageId) {}
    public void evictPage() {}
}