package dbms.storage;

import java.util.UUID;

public class RowStorageBackend implements StorageBackend {
    @Override
    public void writeRecord(byte[] data) {
    }

    @Override
    public byte[] readRecord(UUID id) {
        return null;
    }
}
