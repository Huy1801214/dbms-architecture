package dbms.storage.page;

public class Page {
    private int pageId;
    private byte[] data;
    private boolean dirty;

    public Page() {
    }

    public Page(int pageId, byte[] data, boolean dirty) {
        this.pageId = pageId;
        this.data = data;
        this.dirty = dirty;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
}
