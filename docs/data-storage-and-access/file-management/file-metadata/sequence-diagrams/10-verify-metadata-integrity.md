# Verify Metadata Integrity

## Group: Validation

## Description

Loads the `DatabaseFile` aggregate and verifies the physical integrity of its metadata by checking checksums and structural invariants through the `IMetadataValidator`.

---

```mermaid
sequenceDiagram
    actor StorageEngine
    participant FileManagementService
    participant MetadataManager
    participant FileMetadataRepository
    participant IMetadataValidator
    participant DatabaseFile

    StorageEngine->>FileManagementService: verifyIntegrity(fileId)
    FileManagementService->>MetadataManager: verifyIntegrity(fileId)

    MetadataManager->>FileMetadataRepository: findById(fileId)
    FileMetadataRepository-->>MetadataManager: databaseFile

    MetadataManager->>IMetadataValidator: verifyIntegrity(databaseFile)
    IMetadataValidator->>DatabaseFile: computeChecksum()
    DatabaseFile-->>IMetadataValidator: checksum
    IMetadataValidator-->>MetadataManager: integrityResult

    MetadataManager-->>FileManagementService: integrityResult
    FileManagementService-->>StorageEngine: integrityResult
```
