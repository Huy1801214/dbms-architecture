# Create Database Metadata

## Group: Lifecycle

## Description

Creates a new `DatabaseFile` aggregate with all associated metadata components (header, identifier, configuration, state) and persists it to disk.

---

```mermaid
sequenceDiagram
    actor StorageEngine
    participant FileManagementService
    participant MetadataManager
    participant IMetadataFactory
    participant DatabaseFile
    participant FileMetadataRepository

    StorageEngine->>FileManagementService: createMetadata(config)
    FileManagementService->>MetadataManager: createMetadata(config)

    MetadataManager->>IMetadataFactory: createDatabaseFile(config)
    IMetadataFactory-->>DatabaseFile: <<create>>
    IMetadataFactory-->>MetadataManager: databaseFile

    MetadataManager->>FileMetadataRepository: persist(databaseFile)
    FileMetadataRepository-->>MetadataManager: ok

    MetadataManager-->>FileManagementService: databaseFile
    FileManagementService-->>StorageEngine: databaseFile
```
