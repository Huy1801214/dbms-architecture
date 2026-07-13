# Get File State

## Group: Query

## Description

Retrieves the `DatabaseFile` aggregate and extracts its current `FileState` value object, returning the runtime state information to the caller.

---

```mermaid
sequenceDiagram
    actor StorageEngine
    participant FileManagementService
    participant MetadataManager
    participant FileMetadataRepository
    participant DatabaseFile

    StorageEngine->>FileManagementService: getFileState(fileId)
    FileManagementService->>MetadataManager: getFileState(fileId)

    MetadataManager->>FileMetadataRepository: findById(fileId)
    FileMetadataRepository-->>MetadataManager: databaseFile

    MetadataManager->>DatabaseFile: extractState()
    DatabaseFile-->>MetadataManager: fileState

    MetadataManager-->>FileManagementService: fileState
    FileManagementService-->>StorageEngine: fileState
```
