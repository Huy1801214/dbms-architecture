# Check Metadata Consistency

## Group: Validation

## Description

Compares the in-memory `DatabaseFile` aggregate against the persisted version on disk, detecting any divergence between the two states.

---

```mermaid
sequenceDiagram
    actor StorageEngine
    participant FileManagementService
    participant MetadataManager
    participant FileMetadataRepository
    participant IMetadataValidator

    StorageEngine->>FileManagementService: checkConsistency(fileId)
    FileManagementService->>MetadataManager: checkConsistency(fileId)

    MetadataManager->>FileMetadataRepository: findInMemory(fileId)
    FileMetadataRepository-->>MetadataManager: inMemoryFile

    MetadataManager->>FileMetadataRepository: findOnDisk(fileId)
    FileMetadataRepository-->>MetadataManager: persistedFile

    MetadataManager->>IMetadataValidator: checkConsistency(inMemoryFile, persistedFile)
    IMetadataValidator-->>MetadataManager: consistencyResult

    MetadataManager-->>FileManagementService: consistencyResult
    FileManagementService-->>StorageEngine: consistencyResult
```
