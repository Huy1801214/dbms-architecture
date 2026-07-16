package dbms.storage.page;

public class PageManager {

    private PageTable pageTable;
    private IPageAllocator pageAllocator;
    private IPageIO pageIO;

    public PageManager() {
    }

    public PageManager(PageTable pageTable,
            IPageAllocator pageAllocator,
            IPageIO pageIO) {
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

    public IPageAllocator getPageAllocator() {
        return pageAllocator;
    }

    public void setPageAllocator(IPageAllocator pageAllocator) {
        this.pageAllocator = pageAllocator;
    }

    public IPageIO getPageIO() {
        return pageIO;
    }

    public void setPageIO(IPageIO pageIO) {
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