# Validate Metadata

## Group: Validation

## Description

Loads the `DatabaseFile` aggregate and runs full metadata validation through the `IMetadataValidator` abstraction, returning a structured validation result.

---

```mermaid
sequenceDiagram
    actor StorageEngine
    participant FileManagementService
    participant MetadataManager
    participant FileMetadataRepository
    participant IMetadataValidator

    StorageEngine->>FileManagementService: validateMetadata(fileId)
    FileManagementService->>MetadataManager: validateMetadata(fileId)

    MetadataManager->>FileMetadataRepository: findById(fileId)
    FileMetadataRepository-->>MetadataManager: databaseFile

    MetadataManager->>IMetadataValidator: validate(databaseFile)
    IMetadataValidator-->>MetadataManager: validationResult

    MetadataManager-->>FileManagementService: validationResult
    FileManagementService-->>StorageEngine: validationResult
```
