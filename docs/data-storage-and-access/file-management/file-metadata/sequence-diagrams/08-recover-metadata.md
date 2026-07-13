# Recover Metadata

## Group: Recovery

## Description

Locates the last known good snapshot of the `DatabaseFile` aggregate from persistent storage, restores the aggregate to that state, and persists the recovered version.

---

```mermaid
sequenceDiagram
    actor StorageEngine
    participant FileManagementService
    participant MetadataManager
    participant FileMetadataRepository
    participant DatabaseFile

    StorageEngine->>FileManagementService: recoverMetadata(fileId)
    FileManagementService->>MetadataManager: recoverMetadata(fileId)

    MetadataManager->>FileMetadataRepository: findLastKnownGoodState(fileId)
    FileMetadataRepository-->>MetadataManager: recoverySnapshot

    MetadataManager->>DatabaseFile: restoreFrom(recoverySnapshot)

    MetadataManager->>FileMetadataRepository: save(databaseFile)
    FileMetadataRepository-->>MetadataManager: ok

    MetadataManager-->>FileManagementService: ok
    FileManagementService-->>StorageEngine: ok
```
