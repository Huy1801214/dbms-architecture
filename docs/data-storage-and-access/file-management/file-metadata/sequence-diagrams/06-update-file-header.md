# Update File Header

## Group: Management

## Description

Loads the `DatabaseFile` aggregate, applies a new `FileHeader` value object, and persists the updated aggregate to disk.

---

```mermaid
sequenceDiagram
    actor StorageEngine
    participant FileManagementService
    participant MetadataManager
    participant DatabaseFile
    participant FileMetadataRepository

    StorageEngine->>FileManagementService: updateFileHeader(fileId, header)
    FileManagementService->>MetadataManager: updateFileHeader(fileId, header)

    MetadataManager->>FileMetadataRepository: findById(fileId)
    FileMetadataRepository-->>MetadataManager: databaseFile

    MetadataManager->>DatabaseFile: updateHeader(header)

    MetadataManager->>FileMetadataRepository: save(databaseFile)
    FileMetadataRepository-->>MetadataManager: ok

    MetadataManager-->>FileManagementService: ok
    FileManagementService-->>StorageEngine: ok
```
