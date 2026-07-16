package dbms.storage.buffer;

import dbms.storage.page.Page;

public class BufferFrame {

    private int frameId;
    private int pinCount;
    private boolean dirty;
    private Page page;

    public BufferFrame() {
    }

    public BufferFrame(int frameId, int pinCount, boolean dirty, Page page) {
        this.frameId = frameId;
        this.pinCount = pinCount;
        this.dirty = dirty;
        this.page = page;
    }

    public int getFrameId() {
        return frameId;
    }

    public void setFrameId(int frameId) {
        this.frameId = frameId;
    }

    public int getPinCount() {
        return pinCount;
    }

    public void setPinCount(int pinCount) {
        this.pinCount = pinCount;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

}
