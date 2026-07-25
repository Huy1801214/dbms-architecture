package dbms.storage;

import java.util.UUID;

public interface StorageBackend {
    void writeRecord(byte[] data);
    byte[] readRecord(UUID id);
}
