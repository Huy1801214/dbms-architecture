package dbms.storage.buffer;

import java.util.LinkedList;
import java.util.List;

public class LRUReplacer implements ReplacementPolicy {

    private List<BufferFrame> accessHistory;

    public LRUReplacer() {
        this.accessHistory = new LinkedList<>();
    }

    public LRUReplacer(List<BufferFrame> accessHistory) {
        this.accessHistory = accessHistory;
    }

    public List<BufferFrame> getAccessHistory() {
        return accessHistory;
    }

    public void setAccessHistory(List<BufferFrame> accessHistory) {
        this.accessHistory = accessHistory;
    }

    @Override
    public BufferFrame victim() {
        return null;
    }

    @Override
    public void recordAccess(BufferFrame frame) {

    }

}
