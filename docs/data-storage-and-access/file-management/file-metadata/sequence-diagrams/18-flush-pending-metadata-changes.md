# Flush Pending Metadata Changes

## Group: Synchronization

## Description

Extracts dirty (unsaved) metadata changes from the `DatabaseFile` aggregate, flushes them to persistent storage, and marks the aggregate as clean.

---

```mermaid
sequenceDiagram
    actor StorageEngine
    participant FileManagementService
    participant MetadataManager
    participant DatabaseFile
    participant FileMetadataRepository

    StorageEngine->>FileManagementService: flushMetadataChanges(fileId)
    FileManagementService->>MetadataManager: flushMetadataChanges(fileId)

    MetadataManager->>FileMetadataRepository: findById(fileId)
    FileMetadataRepository-->>MetadataManager: databaseFile

    MetadataManager->>DatabaseFile: extractDirtyState()
    DatabaseFile-->>MetadataManager: dirtyChanges

    MetadataManager->>FileMetadataRepository: flush(dirtyChanges)
    FileMetadataRepository-->>MetadataManager: ok

    MetadataManager->>DatabaseFile: markAsClean()

    MetadataManager-->>FileManagementService: ok
    FileManagementService-->>StorageEngine: ok
```
