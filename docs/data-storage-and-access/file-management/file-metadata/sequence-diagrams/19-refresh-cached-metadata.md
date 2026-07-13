# Refresh Cached Metadata

## Group: Synchronization

## Description

Evicts the stale in-memory metadata cache for a `DatabaseFile`, reloads the latest version from persistent storage, and updates the cache with fresh data.

---

```mermaid
sequenceDiagram
    actor StorageEngine
    participant FileManagementService
    participant MetadataManager
    participant DatabaseFile
    participant FileMetadataRepository

    StorageEngine->>FileManagementService: refreshMetadataCache(fileId)
    FileManagementService->>MetadataManager: refreshMetadataCache(fileId)

    MetadataManager->>DatabaseFile: evictFromCache()

    MetadataManager->>FileMetadataRepository: findById(fileId)
    FileMetadataRepository-->>MetadataManager: freshDatabaseFile

    MetadataManager->>DatabaseFile: updateCache(freshDatabaseFile)

    MetadataManager-->>FileManagementService: ok
    FileManagementService-->>StorageEngine: ok
```
