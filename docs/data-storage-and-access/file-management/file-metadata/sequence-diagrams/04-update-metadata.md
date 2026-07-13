# UC-FM-004 - Update Metadata

## Group

Metadata Lifecycle

---

## Purpose

Update the metadata of an existing DatabaseFile aggregate, validate the changes, and persist the updated metadata to disk.

---

# Request DTO

## UpdateDatabaseMetadataRequest
```mermaid
classDiagram

class UpdateDatabaseMetadataRequest{
    +fileIdentifier: UUID
    +newConfiguration: FileConfiguration
}
```
---

## Preconditions

- The target DatabaseFile exists.
- The metadata has been loaded into memory.
- A valid metadata update request is received.
---

## Postconditions

- The DatabaseFile aggregate has been updated.
- The updated metadata has passed validation.
- The updated metadata has been persisted.

---

## Happy Path

```mermaid
sequenceDiagram
    autonumber

    participant SE as Storage Engine
    participant MM as MetadataManager
    participant MR as MetadataRepository
    participant DF as DatabaseFile
    participant MV as MetadataValidator
    participant Disk as Disk

    SE->>MM: updateMetadata(UpdateDatabaseMetadataRequest)

    MM->>MR: loadMetadata(fileIdentifier)

    MR->>Disk: readMetadata(fileIdentifier)
    Disk-->>MR: PersistedMetadata
    MR-->>MM: DatabaseFile

    MM->>DF: update(UpdateDatabaseMetadataRequest)

    DF-->>MM: updated

    MM->>MV: validate(DatabaseFile)

    MV->>DF: validate()

    DF-->>MV: validation passed
    MV-->>MM: validation passed

    MM->>MR: persist(DatabaseFile)

    MR->>Disk: writeMetadata(binaryMetadata)

    Disk-->>MR: success

    MR-->>MM: persisted

    MM-->>SE: Updated DatabaseFile
```

## Failure Paths

### Failure Path 1 - Metadata Not Found

**Purpose**

Abort the operation because the requested metadata cannot be found.

---

```mermaid
sequenceDiagram
    autonumber

    participant SE as Storage Engine
    participant MM as MetadataManager
    participant MR as MetadataRepository
    participant Disk as Disk

    SE->>MM: updateMetadata(UpdateDatabaseMetadataRequest)

    MM->>MR: loadMetadata(fileIdentifier)

    MR->>Disk: readMetadata(fileIdentifier)

    Disk-->>MR: MetadataNotFoundException

    MR-->>MM: MetadataNotFoundException

    MM-->>SE: MetadataNotFoundException
```

---

### Failure Path 2 - Metadata Update Failed

**Purpose**

Abort the operation because the DatabaseFile aggregate rejects the requested metadata changes.

---
```mermaid
sequenceDiagram
    autonumber

    participant SE as Storage Engine
    participant MM as MetadataManager
    participant MR as MetadataRepository
    participant Disk as Disk
    participant DF as DatabaseFile

    SE->>MM: updateMetadata(UpdateDatabaseMetadataRequest)

    MM->>MR: loadMetadata(fileIdentifier)

    MR->>Disk: readMetadata(fileIdentifier)
    Disk-->>MR: PersistedMetadata

    MR-->>MM: DatabaseFile

    MM->>DF: update(UpdateDatabaseMetadataRequest)

    DF-->>MM: MetadataUpdateException

    MM-->>SE: MetadataUpdateException
```
---

### Failure Path 3 - Metadata Validation Failed

**Purpose**

Abort the operation because the updated DatabaseFile aggregate violates domain validation rules.

---
```mermaid
sequenceDiagram
    autonumber

    participant SE as Storage Engine
    participant MM as MetadataManager
    participant MR as MetadataRepository
    participant Disk as Disk
    participant DF as DatabaseFile
    participant MV as MetadataValidator

    SE->>MM: updateMetadata(UpdateDatabaseMetadataRequest)

    MM->>MR: loadMetadata(fileIdentifier)

    MR->>Disk: readMetadata(fileIdentifier)
    Disk-->>MR: PersistedMetadata
    MR-->>MM: DatabaseFile

    MM->>DF: update(UpdateDatabaseMetadataRequest)

    DF-->>MM: updated

    MM->>MV: validate(DatabaseFile)

    MV->>DF: validate()

    DF-->>MV: validation failed

    MV-->>MM: MetadataValidationException

    MM-->>SE: MetadataValidationException
```

---

### Failure Path 4 - Metadata Persistence Failed

**Purpose**

Abort the operation because the updated metadata cannot be persisted successfully.

---

```mermaid
sequenceDiagram
    autonumber

    participant SE as Storage Engine
    participant MM as MetadataManager
    participant MR as MetadataRepository
    participant Disk as Disk
    participant DF as DatabaseFile
    participant MV as MetadataValidator

    SE->>MM: updateMetadata(UpdateDatabaseMetadataRequest)

    MM->>MR: loadMetadata(fileIdentifier)

    MR->>Disk: readMetadata(fileIdentifier)
    Disk-->>MR: PersistedMetadata
    MR-->>MM: DatabaseFile

    MM->>DF: update(UpdateDatabaseMetadataRequest)

    DF-->>MM: updated

    MM->>MV: validate(DatabaseFile)

    MV->>DF: validate()

    DF-->>MV: validation passed

    MV-->>MM: validation passed

    MM->>MR: persist(DatabaseFile)

    MR-->>MM: MetadataPersistenceException

    MM-->>SE: MetadataPersistenceException
```

