# Synchronize Memory Metadata with Disk

## Group: Synchronization

## Description

Collects all pending in-memory changes from the `DatabaseFile` aggregate, validates them, and atomically synchronizes the full state with persistent storage.

---

```mermaid
sequenceDiagram
    actor StorageEngine
    participant FileManagementService
    participant MetadataManager
    participant DatabaseFile
    participant IMetadataValidator
    participant FileMetadataRepository

    StorageEngine->>FileManagementService: synchronizeMetadata(fileId)
    FileManagementService->>MetadataManager: synchronizeMetadata(fileId)

    MetadataManager->>FileMetadataRepository: findById(fileId)
    FileMetadataRepository-->>MetadataManager: databaseFile

    MetadataManager->>DatabaseFile: collectPendingChanges()
    DatabaseFile-->>MetadataManager: pendingChanges

    MetadataManager->>IMetadataValidator: validate(pendingChanges)
    IMetadataValidator-->>MetadataManager: valid

    MetadataManager->>FileMetadataRepository: synchronize(databaseFile)
    FileMetadataRepository-->>MetadataManager: ok

    MetadataManager-->>FileManagementService: ok
    FileManagementService-->>StorageEngine: ok
```
