# Get File Configuration

## Group: Query

## Description

Retrieves the `DatabaseFile` aggregate and extracts its `FileConfiguration` value object, returning only the configuration information to the caller.

---

```mermaid
sequenceDiagram
    actor StorageEngine
    participant FileManagementService
    participant MetadataManager
    participant FileMetadataRepository
    participant DatabaseFile

    StorageEngine->>FileManagementService: getFileConfiguration(fileId)
    FileManagementService->>MetadataManager: getFileConfiguration(fileId)

    MetadataManager->>FileMetadataRepository: findById(fileId)
    FileMetadataRepository-->>MetadataManager: databaseFile

    MetadataManager->>DatabaseFile: extractConfiguration()
    DatabaseFile-->>MetadataManager: fileConfiguration

    MetadataManager-->>FileManagementService: fileConfiguration
    FileManagementService-->>StorageEngine: fileConfiguration
```
