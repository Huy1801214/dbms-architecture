package dbms.storage.buffer;

import dbms.storage.page.Page;
import dbms.storage.page.PageManager;

public class BufferManager {

    private BufferPool bufferPool;
    private ReplacementPolicy replacementPolicy;
    private PageManager pageManager;

    public BufferManager() {
    }

    public BufferManager(BufferPool bufferPool,
            ReplacementPolicy replacementPolicy,
            PageManager pageManager) {
        this.bufferPool = bufferPool;
        this.replacementPolicy = replacementPolicy;
        this.pageManager = pageManager;
    }

    public BufferPool getBufferPool() {
        return bufferPool;
    }

    public void setBufferPool(BufferPool bufferPool) {
        this.bufferPool = bufferPool;
    }

    public ReplacementPolicy getReplacementPolicy() {
        return replacementPolicy;
    }

    public void setReplacementPolicy(ReplacementPolicy replacementPolicy) {
        this.replacementPolicy = replacementPolicy;
    }

    public PageManager getPageManager() {
        return pageManager;
    }

    public void setPageManager(PageManager pageManager) {
        this.pageManager = pageManager;
    }

    public Page pinPage(int pageId) {
        return null;
    }

    public void unpinPage(int pageId) {

    }

    public void flushPage(int pageId) {

    }

    public void flushAll() {

    }

}
