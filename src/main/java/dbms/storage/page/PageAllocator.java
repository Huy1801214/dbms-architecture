package dbms.storage.page;

public class PageAllocator {

    private int nextPageId;

    public PageAllocator() {
    }

    public PageAllocator(int nextPageId) {
        this.nextPageId = nextPageId;
    }

    public int getNextPageId() {
        return nextPageId;
    }

    public void setNextPageId(int nextPageId) {
        this.nextPageId = nextPageId;
    }

    public Page allocate() {
        return null;
    }

    public void free(int pageId) {

    }

}
