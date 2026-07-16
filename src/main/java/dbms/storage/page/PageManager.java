package dbms.storage.page;

public class PageManager {

    private PageTable pageTable;
    private PageAllocator pageAllocator;
    private PageIO pageIO;

    public PageManager() {
    }

    public PageManager(PageTable pageTable, PageAllocator pageAllocator, PageIO pageIO) {
        this.pageTable = pageTable;
        this.pageAllocator = pageAllocator;
        this.pageIO = pageIO;
    }

    public PageTable getPageTable() {
        return pageTable;
    }

    public void setPageTable(PageTable pageTable) {
        this.pageTable = pageTable;
    }

    public PageAllocator getPageAllocator() {
        return pageAllocator;
    }

    public void setPageAllocator(PageAllocator pageAllocator) {
        this.pageAllocator = pageAllocator;
    }

    public PageIO getPageIO() {
        return pageIO;
    }

    public void setPageIO(PageIO pageIO) {
        this.pageIO = pageIO;
    }

    public Page fetchPage(int pageId) {
        return null;
    }

    public Page allocatePage() {
        return null;
    }

    public void flushPage(int pageId) {

    }

    public void deletePage(int pageId) {

    }

}
