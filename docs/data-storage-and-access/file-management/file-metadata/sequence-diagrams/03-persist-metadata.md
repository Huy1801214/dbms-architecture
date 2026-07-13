# UC-FM-003 - Persist Metadata To Disk

## Group

Metadata Persistence

---

## Purpose

Persist the current DatabaseFile aggregate to persistent storage so that all metadata changes are durably stored and recoverable.

---

## Preconditions

- A valid DatabaseFile aggregate exists in memory.
- The aggregate has passed domain validation.
- The storage device is available.

---

## Postconditions

- The metadata has been successfully written to persistent storage.
- The stored metadata reflects the current state of the DatabaseFile aggregate.

---

## Happy Path

```mermaid
sequenceDiagram
    autonumber

    participant SE as Storage Engine
    participant MM as MetadataManager
    participant MV as MetadataValidator
    participant DF as DatabaseFile
    participant MR as MetadataRepository
    participant Disk as Disk

    SE->>MM: persistMetadata(DatabaseFile)

    MM->>MV: validate(DatabaseFile)

    MV->>DF: validate()

    DF-->>MV: validation passed

    MV-->>MM: validation passed

    MM->>MR: persist(DatabaseFile)

    Note over MR: Serialize DatabaseFile into binary metadata

    MR->>Disk: writeMetadata(binaryMetadata)

    Disk-->>MR: success

    MR-->>MM: persisted

    MM-->>SE: persistence succeeded
```

## Failure Paths

### Failure Path 1 - Metadata Validation Failed

**Purpose**

Abort the operation because the DatabaseFile aggregate violates domain validation rules and cannot be safely persisted.

---

```mermaid
sequenceDiagram
    autonumber

    participant SE as Storage Engine
    participant MM as MetadataManager
    participant MV as MetadataValidator
    participant DF as DatabaseFile

    SE->>MM: persistMetadata(DatabaseFile)

    MM->>MV: validate(DatabaseFile)

    MV->>DF: validate()

    DF-->>MV: validation failed

    MV-->>MM: MetadataValidationException

    MM-->>SE: MetadataValidationException
```

---

### Failure Path 2 - Metadata Persistence Failed

**Purpose**

Abort the operation because the DatabaseFile aggregate cannot be converted into a valid persistent metadata format.

---
```mermaid
sequenceDiagram
    autonumber

    participant SE as Storage Engine
    participant MM as MetadataManager
    participant MV as MetadataValidator
    participant DF as DatabaseFile
    participant MR as MetadataRepository

    SE->>MM: persistMetadata(DatabaseFile)

    MM->>MV: validate(DatabaseFile)

    MV->>DF: validate()
    DF-->>MV: validation passed
    MV-->>MM: validation passed

    MM->>MR: persist(DatabaseFile)

    Note over MR: Serialize DatabaseFile

    MR-->>MM: MetadataPersistenceException

    MM-->>SE: MetadataPersistenceException
```
---

### Failure Path 3 - Disk Write Failed

**Purpose**

Abort the operation because the metadata cannot be written to persistent storage.

---
```mermaid
sequenceDiagram
    autonumber

    participant SE as Storage Engine
    participant MM as MetadataManager
    participant MV as MetadataValidator
    participant DF as DatabaseFile
    participant MR as MetadataRepository
    participant Disk as Disk

    SE->>MM: persistMetadata(DatabaseFile)

    MM->>MV: validate(DatabaseFile)

    MV->>DF: validate()
    DF-->>MV: validation passed
    MV-->>MM: validation passed

    MM->>MR: persist(DatabaseFile)

    Note over MR: Serialize DatabaseFile

    MR->>Disk: writeMetadata(binaryMetadata)

    Disk-->>MR: DiskWriteException

    MR-->>MM: DiskWriteException

    MM-->>SE: DiskWriteException
```
