package dbms.storage.buffer;

public interface ReplacementPolicy {

    BufferFrame victim();

    void recordAccess(BufferFrame frame);

}
