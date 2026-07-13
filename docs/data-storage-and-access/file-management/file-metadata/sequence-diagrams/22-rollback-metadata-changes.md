# Rollback Metadata Changes

## Group: Recovery

## Description

Reverts the `DatabaseFile` aggregate to a previously saved checkpoint snapshot, discarding all changes made after that checkpoint, and persists the rolled-back state.

---

```mermaid
sequenceDiagram
    actor StorageEngine
    participant FileManagementService
    participant MetadataManager
    participant FileMetadataRepository
    participant DatabaseFile

    StorageEngine->>FileManagementService: rollbackMetadata(fileId, checkpoint)
    FileManagementService->>MetadataManager: rollbackMetadata(fileId, checkpoint)

    MetadataManager->>FileMetadataRepository: findByCheckpoint(fileId, checkpoint)
    FileMetadataRepository-->>MetadataManager: snapshotFile

    MetadataManager->>DatabaseFile: rollbackTo(snapshotFile)

    MetadataManager->>FileMetadataRepository: save(databaseFile)
    FileMetadataRepository-->>MetadataManager: ok

    MetadataManager-->>FileManagementService: ok
    FileManagementService-->>StorageEngine: ok
```
