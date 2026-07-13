# UC-FM-002 - Load Metadata From Disk

## Group

Metadata Lifecycle

---

## Purpose

Load an existing `DatabaseFile` aggregate from persistent storage, reconstruct all metadata components, validate the reconstructed aggregate, and make it available for use by the Storage Engine.

---

# Request DTO

## LoadDatabaseMetadataRequest

```mermaid
classDiagram

class LoadDatabaseMetadataRequest{
    +fileIdentifier : UUID
}
```

---

## Preconditions

- The target metadata file exists on disk.
- The Storage Engine provides a valid metadata file location.
- The storage device is accessible.

---

## Postconditions

- The metadata has been successfully loaded from disk.
- A valid `DatabaseFile` aggregate has been reconstructed.
- The reconstructed aggregate has passed domain validation.
- The initialized `DatabaseFile` is returned to the Storage Engine.

---

## Happy Path

```mermaid
sequenceDiagram
    autonumber

    participant SE as StorageEngine
    participant MM as MetadataManager
    participant IMR as IMetadataRepository
    participant Disk as Disk
    participant IMF as IMetadataFactory
    participant DF as DatabaseFile
    participant IMV as IMetadataValidator

    SE->>MM: loadMetadata(LoadDatabaseMetadataRequest)

    MM->>IMR: findById(fileIdentifier)

    IMR->>Disk: read(fileIdentifier)
    Disk-->>IMR: PersistedMetadata

    IMR->>IMF: reconstructDatabaseFile(PersistedMetadata)
    IMF->>DF: <<create>>
    DF-->>IMF: DatabaseFile
    IMF-->>IMR: DatabaseFile

    IMR-->>MM: DatabaseFile

    MM->>IMV: validate(DatabaseFile)
    IMV-->>MM: ValidationResult

    MM-->>SE: DatabaseFile
```

## Failure Paths

### Failure Path 1 - Metadata File Not Found

**Purpose**

Abort the operation because the requested metadata file does not exist on disk.

---

```mermaid
sequenceDiagram
    autonumber

    participant SE as StorageEngine
    participant MM as MetadataManager
    participant IMR as IMetadataRepository
    participant Disk as Disk

    SE->>MM: loadMetadata(LoadDatabaseMetadataRequest)

    MM->>IMR: findById(fileIdentifier)

    IMR->>Disk: read(fileIdentifier)

    Disk-->>IMR: MetadataFileNotFoundException

    IMR-->>MM: MetadataFileNotFoundException

    MM-->>SE: MetadataFileNotFoundException
```

---

### Failure Path 2 - Disk Read Failed

**Purpose**

Abort the operation because metadata cannot be read from the storage device.

---

```mermaid
sequenceDiagram
    autonumber

    participant SE as StorageEngine
    participant MM as MetadataManager
    participant IMR as IMetadataRepository
    participant Disk as Disk

    SE->>MM: loadMetadata(LoadDatabaseMetadataRequest)

    MM->>IMR: findById(fileIdentifier)

    IMR->>Disk: read(fileIdentifier)

    Disk-->>IMR: DiskReadException

    IMR-->>MM: DiskReadException

    MM-->>SE: DiskReadException
```

---

### Failure Path 3 - Metadata Reconstruction Failed

**Purpose**

Abort the operation because `IMetadataFactory` cannot reconstruct a valid `DatabaseFile` aggregate from the persisted data.

---

```mermaid
sequenceDiagram
    autonumber

    participant SE as StorageEngine
    participant MM as MetadataManager
    participant IMR as IMetadataRepository
    participant Disk as Disk
    participant IMF as IMetadataFactory

    SE->>MM: loadMetadata(LoadDatabaseMetadataRequest)

    MM->>IMR: findById(fileIdentifier)

    IMR->>Disk: read(fileIdentifier)
    Disk-->>IMR: PersistedMetadata

    IMR->>IMF: reconstructDatabaseFile(PersistedMetadata)
    IMF-->>IMR: MetadataReconstructionException

    IMR-->>MM: MetadataReconstructionException

    MM-->>SE: MetadataReconstructionException
```

---

### Failure Path 4 - Metadata Validation Failed

**Purpose**

Abort the operation because the reconstructed `DatabaseFile` aggregate violates domain validation rules.

---

```mermaid
sequenceDiagram
    autonumber

    participant SE as StorageEngine
    participant MM as MetadataManager
    participant IMR as IMetadataRepository
    participant Disk as Disk
    participant IMF as IMetadataFactory
    participant DF as DatabaseFile
    participant IMV as IMetadataValidator

    SE->>MM: loadMetadata(LoadDatabaseMetadataRequest)

    MM->>IMR: findById(fileIdentifier)

    IMR->>Disk: read(fileIdentifier)
    Disk-->>IMR: PersistedMetadata

    IMR->>IMF: reconstructDatabaseFile(PersistedMetadata)
    IMF->>DF: <<create>>
    DF-->>IMF: DatabaseFile
    IMF-->>IMR: DatabaseFile

    IMR-->>MM: DatabaseFile

    MM->>IMV: validate(DatabaseFile)
    IMV-->>MM: MetadataValidationException

    MM-->>SE: MetadataValidationException
```

---