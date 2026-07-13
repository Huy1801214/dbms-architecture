# Update File State

## Group: Management

## Description

Loads the `DatabaseFile` aggregate, transitions its `FileState` value object to a new state, and persists the updated aggregate to disk.

---

```mermaid
sequenceDiagram
    actor StorageEngine
    participant FileManagementService
    participant MetadataManager
    participant DatabaseFile
    participant FileMetadataRepository

    StorageEngine->>FileManagementService: updateFileState(fileId, state)
    FileManagementService->>MetadataManager: updateFileState(fileId, state)

    MetadataManager->>FileMetadataRepository: findById(fileId)
    FileMetadataRepository-->>MetadataManager: databaseFile

    MetadataManager->>DatabaseFile: transitionState(state)

    MetadataManager->>FileMetadataRepository: save(databaseFile)
    FileMetadataRepository-->>MetadataManager: ok

    MetadataManager-->>FileManagementService: ok
    FileManagementService-->>StorageEngine: ok
```
