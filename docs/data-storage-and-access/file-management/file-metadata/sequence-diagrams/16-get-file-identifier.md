# Get File Identifier

## Group: Query

## Description

Retrieves the `DatabaseFile` aggregate and extracts its `FileIdentifier` value object, returning the unique identity information to the caller.

---

```mermaid
sequenceDiagram
    actor StorageEngine
    participant FileManagementService
    participant MetadataManager
    participant FileMetadataRepository
    participant DatabaseFile

    StorageEngine->>FileManagementService: getFileIdentifier(fileId)
    FileManagementService->>MetadataManager: getFileIdentifier(fileId)

    MetadataManager->>FileMetadataRepository: findById(fileId)
    FileMetadataRepository-->>MetadataManager: databaseFile

    MetadataManager->>DatabaseFile: extractIdentifier()
    DatabaseFile-->>MetadataManager: fileIdentifier

    MetadataManager-->>FileManagementService: fileIdentifier
    FileManagementService-->>StorageEngine: fileIdentifier
```
