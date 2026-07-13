# Reload Metadata

## Group: Lifecycle

## Description

Invalidates the current in-memory metadata cache and reloads the `DatabaseFile` aggregate fresh from persistent storage, ensuring the in-memory state reflects the latest on-disk state.

---

```mermaid
sequenceDiagram
    actor StorageEngine
    participant FileManagementService
    participant MetadataManager
    participant DatabaseFile
    participant FileMetadataRepository

    StorageEngine->>FileManagementService: reloadMetadata(fileId)
    FileManagementService->>MetadataManager: reloadMetadata(fileId)

    MetadataManager->>DatabaseFile: invalidateCache()

    MetadataManager->>FileMetadataRepository: findById(fileId)
    FileMetadataRepository-->>MetadataManager: freshDatabaseFile

    MetadataManager->>DatabaseFile: refresh(freshDatabaseFile)

    MetadataManager-->>FileManagementService: ok
    FileManagementService-->>StorageEngine: ok
```
