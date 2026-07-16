package dbms.integrations;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import dbms.storage.page.*;
import dbms.storage.buffer.*;

public class DataStorageIntegrationTest {

    private PageTable pageTable;
    private IPageAllocator pageAllocator;
    private IPageIO pageIO;
    private PageManager pageManager;
    private BufferPool bufferPool;
    private ReplacementPolicy replacementPolicy;
    private BufferManager bufferManager;

    void setup() {
        pageTable = new PageTable();
        pageAllocator = new DefaultPageAllocator();
        pageIO = new DiskPageIO();
        pageManager = new PageManager(pageTable, pageAllocator, pageIO);

        bufferPool = new BufferPool();
        replacementPolicy = new LRUReplacer();
        bufferManager = new BufferManager(bufferPool, replacementPolicy, pageManager);
    }

    @Test
    void shouldAllocateFetchAndFlushPage() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    void shouldLoadPageFromDiskOnCacheMiss() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    void shouldReturnCachedPageOnCacheHit() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    void shouldEvictLeastRecentlyUsedPage() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    void shouldPersistDirtyPageToDisk() {
        // Arrange

        // Act

        // Assert
    }
}
