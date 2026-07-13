# Repair Corrupted Metadata

## Group: Recovery

## Description

Detects corruption within the `DatabaseFile` aggregate using the `IMetadataValidator`, applies targeted repairs to the affected components, and persists the repaired state to disk.

---

```mermaid
sequenceDiagram
    actor StorageEngine
    participant FileManagementService
    participant MetadataManager
    participant FileMetadataRepository
    participant IMetadataValidator
    participant DatabaseFile

    StorageEngine->>FileManagementService: repairMetadata(fileId)
    FileManagementService->>MetadataManager: repairMetadata(fileId)

    MetadataManager->>FileMetadataRepository: findById(fileId)
    FileMetadataRepository-->>MetadataManager: corruptedFile

    MetadataManager->>IMetadataValidator: detectCorruption(corruptedFile)
    IMetadataValidator-->>MetadataManager: corruptionReport

    MetadataManager->>DatabaseFile: applyRepair(corruptionReport)

    MetadataManager->>FileMetadataRepository: save(databaseFile)
    FileMetadataRepository-->>MetadataManager: ok

    MetadataManager-->>FileManagementService: ok
    FileManagementService-->>StorageEngine: ok
```
