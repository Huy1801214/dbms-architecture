package dbms.storage.page;

import java.util.HashMap;
import java.util.Map;

public class PageTable {

    private Map<Integer, Page> pageTable;

    public PageTable() {
        this.pageTable = new HashMap<>();
    }

    public PageTable(Map<Integer, Page> pageTable) {
        this.pageTable = pageTable;
    }

    public Map<Integer, Page> getPageTable() {
        return pageTable;
    }

    public void setPageTable(Map<Integer, Page> pageTable) {
        this.pageTable = pageTable;
    }

    public Page lookup(int pageId) {
        return null;
    }

    public void insert(Page page) {

    }

    public void remove(int pageId) {

    }

}
