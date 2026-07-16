package dbms.storage.buffer;

import java.util.LinkedList;
import java.util.List;

public class LRUReplacer implements ReplacementPolicy {

    private List<BufferFrame> bufferFrames;

    public LRUReplacer() {
        this.bufferFrames = new LinkedList<>();
    }

    public LRUReplacer(List<BufferFrame> bufferFrames) {
        this.bufferFrames = bufferFrames;
    }

    public List<BufferFrame> getBufferFrames() {
        return bufferFrames;
    }

    public void setBufferFrames(List<BufferFrame> bufferFrames) {
        this.bufferFrames = bufferFrames;
    }

    @Override
    public BufferFrame victim() {
        return null;
    }

    @Override
    public void recordAccess(BufferFrame frame) {

    }

}
