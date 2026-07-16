package dbms.storage.buffer;

import java.util.ArrayList;
import java.util.List;

public class BufferPool {

    private int capacity;
    private List<BufferFrame> frames;

    public BufferPool() {
        this.frames = new ArrayList<>();
    }

    public BufferPool(int capacity, List<BufferFrame> frames) {
        this.capacity = capacity;
        this.frames = frames;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<BufferFrame> getFrames() {
        return frames;
    }

    public void setFrames(List<BufferFrame> frames) {
        this.frames = frames;
    }

}
