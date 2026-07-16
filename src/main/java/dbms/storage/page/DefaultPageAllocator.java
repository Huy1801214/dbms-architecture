package dbms.storage.page;

public class DefaultPageAllocator implements IPageAllocator {

    private int nextPageId;

    public DefaultPageAllocator() {
    }

    public DefaultPageAllocator(int nextPageId) {
        this.nextPageId = nextPageId;
    }

    public int getNextPageId() {
        return nextPageId;
    }

    public void setNextPageId(int nextPageId) {
        this.nextPageId = nextPageId;
    }

    @Override
    public Page allocate() {
        return null;
    }

    @Override
    public void free(int pageId) {

    }

}
