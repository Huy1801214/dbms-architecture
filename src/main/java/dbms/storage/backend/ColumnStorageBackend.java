package dbms.storage.backend;


import java.util.UUID;

public class ColumnStorageBackend implements StorageBackend {
    @Override
    public void writeRecord(byte[] data) {
    }

    @Override
    public byte[] readRecord(UUID id) {
        return null;
    }
}
