# UC-FM-001 - Create Database Metadata

## Group

Metadata Lifecycle

---

## Purpose

Create a new `DatabaseFile` aggregate, validate its metadata, and persist it to disk so that the database file is fully initialized and ready for use.

---

# Request DTO

## CreateDatabaseMetadataRequest


```mermaid
classDiagram

class CreateDatabaseMetadataRequest{
    +databaseName: String
    +logicalFileName: String
    +physicalPath: String
    +pageSize: Integer
    +initialSize: Long
    +maximumSize: Long
    +autoGrowthEnabled: Boolean
    +growthIncrement: Long
}

class DatabaseFile

CreateDatabaseMetadataRequest --> DatabaseFile : creates
```
---

## Preconditions

- A valid database creation request is received.
- The requested database can be created in the target location.
- Required metadata information is available.

---

## Postconditions

- A valid `DatabaseFile` aggregate has been created.
- The metadata has been successfully validated.
- The metadata has been persisted to disk.
- The initialized `DatabaseFile` is returned to the caller.

---

## Happy Path

```mermaid
sequenceDiagram
    autonumber

    participant SE as StorageEngine
    participant MM as MetadataManager
    participant IMF as IMetadataFactory
    participant DF as DatabaseFile
    participant IMV as IMetadataValidator
    participant IMR as IMetadataRepository
    participant Disk as Disk

    SE->>MM: createMetadata(CreateDatabaseMetadataRequest)

    MM->>IMF: createDatabaseFile(CreateDatabaseMetadataRequest)
    IMF->>DF: <<create>>
    DF-->>IMF: DatabaseFile
    IMF-->>MM: DatabaseFile

    MM->>IMV: validate(DatabaseFile)
    IMV-->>MM: ValidationResult

    MM->>IMR: persist(DatabaseFile)
    IMR->>Disk: writeMetadata(DatabaseFile)
    Disk-->>IMR: success
    IMR-->>MM: ok

    MM-->>SE: DatabaseFile
```
## Failure Paths
### Failure Path 1 - Invalid Request
**Purpose**

Reject the request because one or more input parameters are invalid before any domain object is created.

---
```mermaid
sequenceDiagram
    autonumber

    participant SE as Storage Engine
    participant MM as MetadataManager

    SE->>MM: createMetadata(CreateDatabaseMetadataRequest)

    Note over MM: Validate request parameters

    MM-->>SE: InvalidMetadataRequestException
```
### Failure Path 2 - Aggregate Creation Failed
**Purpose**

Abort the operation because MetadataFactory cannot create a valid DatabaseFile aggregate.

---
```mermaid
sequenceDiagram
    autonumber

    participant SE as Storage Engine
    participant MM as MetadataManager
    participant MF as MetadataFactory

    SE->>MM: createMetadata(CreateDatabaseMetadataRequest)

    MM->>MF: createDatabaseFile(CreateDatabaseMetadataRequest)

    MF-->>MM: AggregateCreationException

    MM-->>SE: AggregateCreationException
```
### Failure Path 3 - Metadata Validation Failed
**Purpose**

Abort the operation because the created DatabaseFile violates domain validation rules.

---
```mermaid
sequenceDiagram
    autonumber

    participant SE as Storage Engine
    participant MM as MetadataManager
    participant MF as MetadataFactory
    participant MV as MetadataValidator
    participant DF as DatabaseFile

    SE->>MM: createMetadata(CreateDatabaseMetadataRequest)

    MM->>MF: createDatabaseFile(CreateDatabaseMetadataRequest)
    MF->>DF: <<create>>
    DF-->>MF: DatabaseFile
    MF-->>MM: DatabaseFile

    MM->>MV: validate(DatabaseFile)
    MV->>DF: validate()
    DF-->>MV: validation failed

    MV-->>MM: MetadataValidationException
    MM-->>SE: MetadataValidationException
```
### Failure Path 4 - Metadata Persistence Failed
**Purpose**

Abort the operation because metadata cannot be persisted to disk.

---
```mermaid
sequenceDiagram
    autonumber

    participant SE as Storage Engine
    participant MM as MetadataManager
    participant MF as MetadataFactory
    participant MV as MetadataValidator
    participant DF as DatabaseFile
    participant MR as MetadataRepository
    participant Disk as Disk

    SE->>MM: createMetadata(CreateDatabaseMetadataRequest)

    MM->>MF: createDatabaseFile(CreateDatabaseMetadataRequest)
    MF->>DF: <<create>>
    DF-->>MF: DatabaseFile
    MF-->>MM: DatabaseFile

    MM->>MV: validate(DatabaseFile)
    MV->>DF: validate()
    DF-->>MV: validation passed
    MV-->>MM: validation passed

    MM->>MR: persist(DatabaseFile)
    MR->>Disk: writeMetadata(DatabaseFile)

    Disk-->>MR: I/O Error

    MR-->>MM: MetadataPersistenceException
    MM-->>SE: MetadataPersistenceException
```