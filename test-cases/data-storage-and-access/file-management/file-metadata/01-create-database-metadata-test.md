## Target

MetadataManager.createMetadata()

---

## Related Use Case

UC-FM-001 - Create Database Metadata

---

## Purpose

Verify that the Create Database Metadata use case behaves correctly under all normal, boundary, and exceptional conditions.

---

# Components Under Test

## Primary Component

- MetadataManager

## Collaborators

- MetadataFactory
- MetadataValidator
- MetadataRepository
- DatabaseFile

---

# Unit Test Scenarios

| ID | Scenario | Group | Priority |
|----|----------|-------|----------|
| UT-001 | Create metadata successfully | Happy Path | Critical |
| UT-002 | Reject null required fields | Request Validation | High |
| UT-003 | Reject empty string fields | Request Validation | High |
| UT-004 | Reject invalid numeric values (pageSize=0, pageSize<0, initialSize≤0) | Request Validation | High |
| UT-005 | Reject Unsupported Page Size | Request Validation | High |
| UT-006 | Reject maxSize less than initialSize | Request Validation | High |
| UT-007 | Reject Invalid Growth Increment | Request Validation | Medium |
| UT-008 | Abort Creation When Aggregate Factory Fails | Aggregate Creation | High |
| UT-010 | Propagate Metadata Validation Exception | Domain Validation | High |
| UT-012 | Propagate MetadataPersistenceException | Persistence | Critical |
| UT-013 | Initialize FileState to ONLINE| Post-Creation State | High |
| UT-014 | Initialize FileStatistics | Post-Creation State | Medium |
| UT-015 | Initialize File Header | Post-Creation State | Medium |

---

# Integration Test Scenarios

| ID | Scenario | Group | Priority |
|----|----------|-------|----------|
| IT-001 | Full flow — real Factory + Validator + Repository — metadata created and persisted | End-to-End | Critical |
| IT-002 | Invalid aggregate is rejected by the real Validator and no metadata is persisted to disk | Component Integration | High |
| IT-003 | Disk write failure propagates correctly through the real component chain | File System | High |
| IT-004 | Failed metadata creation leaves no partial file or corrupted metadata on disk | Atomicity | High |
| IT-005 | Invalid storage path results in a FileSystemException and the operation is safely aborted | File System | Medium |
---

# Test Coverage Matrix

| Component / Interface | Unit Test Coverage | Integration Test Coverage |
|-----------------------|--------------------|---------------------------|
| `MetadataManager`     | UT-001, UT-008, UT-010, UT-012 | IT-001, IT-002, IT-003, IT-004, IT-005 |
| `IMetadataFactory`    | UT-008 | IT-001 |
| `IMetadataValidator`  | UT-010 | IT-001, IT-002 |
| `IMetadataRepository` | UT-012 | IT-001, IT-004, IT-005, IT-003 |
| `DatabaseFile`        | UT-013, UT-014, UT-015 | IT-001, IT-002 |

---

# Traceability

| Requirement | Sequence Step | Test Cases |
|------------|---------------|------------|
| Reject invalid request | Failure Path 1 | UT-002, UT-003, UT-004, UT-005, UT-006, UT-007 |
| Create Aggregate | Steps 2–5 | UT-001, UT-008, IT-001 |
| Validate Aggregate | Steps 6–7 | UT-010, IT-002 |
| Persist Metadata | Steps 8–11 | UT-012, IT-001, IT-003, IT-004, IT-005 |
| Initialize Aggregate State | Steps 2–5 | UT-013, UT-014, UT-015 |
| Return Aggregate | Step 12 | UT-001, IT-001 |

---

# Unit Test Specifications

## UT-001 - Create Metadata Successfully

### Purpose
Verify that MetadataManager correctly orchestrates the creation, validation, and persistence of a DatabaseFile when a valid metadata request is provided.

### Target
`MetadataManager.createMetadata(CreateDatabaseMetadataRequest)`

### Arrange
1. **Mocks:**
   * `factory`: Mock `IMetadataFactory`
   * `validator`: Mock `IMetadataValidator`
   * `repository`: Mock `IMetadataRepository`
2. **Inputs:**
   * A valid `CreateDatabaseMetadataRequest` (named `request`)
3. **Mock Behavior:**
   * Create a dummy `DatabaseFile` instance (named `expectedFile`).
   * Configure `factory.createDatabaseFile(request)` to return `expectedFile`.
   * Configure `validator.validate(expectedFile)` to return `ValidationResult` with `isValid = true` and empty violations list.
   * Configure `repository.persist(expectedFile)` to complete successfully (no exception).

### Act
* Call: `MetadataManager.createMetadata(request)`

### Expected Outcome
* The method returns the DatabaseFile instance created by MetadataFactory.
* The operation completes successfully.
* No exception is thrown.

### Assert

#### State Verification
* Assert that the returned `DatabaseFile` is reference-equal to `expectedFile` (`returnedFile == expectedFile`).

#### Interaction Verification
* Verify `factory.createDatabaseFile(request)` was called **exactly once** with the input `request`.
* Verify `validator.validate(expectedFile)` was called **exactly once** with `expectedFile`.
* Verify `repository.persist(expectedFile)` was called **exactly once** with `expectedFile`.

### Expected Exception
None

---

## UT-002 - Reject null required fields

### Purpose
Verify that MetadataManager rejects a metadata creation request when one or more required fields are null.

### Target
`MetadataManager.createMetadata(CreateDatabaseMetadataRequest)`

### Arrange
1. **Mocks:**
   * `factory`: Mock `IMetadataFactory`
   * `validator`: Mock `IMetadataValidator`
   * `repository`: Mock `IMetadataRepository`
2. **Inputs:**
   * A `CreateDatabaseMetadataRequest` where one required field is `null`
3. **Mock Behavior:**
   * None

### Act
* Call: `MetadataManager.createMetadata(request)`

### Expected Outcome
* The operation is rejected immediately.
* No `DatabaseFile` is created.

### Assert

#### State Verification
* No `DatabaseFile` is returned.

#### Interaction Verification
* Verify `factory.createDatabaseFile()` was **never** called.
* Verify `validator.validate()` was **never** called.
* Verify `repository.persist()` was **never** called.

### Expected Exception
`InvalidMetadataRequestException`

---

## UT-003 - Reject empty string fields

### Purpose
Verify that MetadataManager rejects a metadata creation request when one or more required string fields are empty or blank.

### Target
`MetadataManager.createMetadata(CreateDatabaseMetadataRequest)`

### Arrange
1. **Mocks:**
   * `factory`: Mock `IMetadataFactory`
   * `validator`: Mock `IMetadataValidator`
   * `repository`: Mock `IMetadataRepository`
2. **Inputs:**
   * A `CreateDatabaseMetadataRequest` where one required string field is empty or blank.
3. **Mock Behavior:**
   * None

### Act
* Call: `MetadataManager.createMetadata(request)`

### Expected Outcome
* The operation is rejected immediately.
* No `DatabaseFile` aggregate is created.

### Assert

#### State Verification
* No `DatabaseFile` is returned.

#### Interaction Verification
* Verify `factory.createDatabaseFile()` was **never** called.
* Verify `validator.validate()` was **never** called.
* Verify `repository.persist()` was **never** called.

### Expected Exception
`InvalidMetadataRequestException`

---

## UT-004 - Reject invalid numeric values

### Purpose
Verify that MetadataManager rejects a metadata creation request when one or more numeric fields contain invalid values.

### Target
`MetadataManager.createMetadata(CreateDatabaseMetadataRequest)`

### Arrange
1. **Mocks:**
   * `factory`: Mock `IMetadataFactory`
   * `validator`: Mock `IMetadataValidator`
   * `repository`: Mock `IMetadataRepository`
2. **Inputs:**
   * A `CreateDatabaseMetadataRequest` containing one or more invalid numeric values.
3. **Mock Behavior:**
   * None

### Act
* Call: `MetadataManager.createMetadata(request)`

### Expected Outcome
* The operation is rejected immediately.
* No `DatabaseFile` aggregate is created.

### Assert

#### State Verification
* No `DatabaseFile` is returned.

#### Interaction Verification
* Verify `factory.createDatabaseFile()` was **never** called.
* Verify `validator.validate()` was **never** called.
* Verify `repository.persist()` was **never** called.

### Expected Exception
`InvalidMetadataRequestException`

---

## UT-005 - Reject Unsupported Page Size

### Purpose
Verify that MetadataManager rejects a metadata creation request when the specified page size is not one of the supported page sizes.

### Target
`MetadataManager.createMetadata(CreateDatabaseMetadataRequest)`

### Arrange
1. **Mocks:**
   * `factory`: Mock `IMetadataFactory`
   * `validator`: Mock `IMetadataValidator`
   * `repository`: Mock `IMetadataRepository`
2. **Inputs:**
   * A `CreateDatabaseMetadataRequest` where `pageSize` is not one of the supported page sizes.
3. **Mock Behavior:**
   * None

### Act
* Call: `MetadataManager.createMetadata(request)`

### Expected Outcome
* The operation is rejected immediately.
* No `DatabaseFile` aggregate is created.

### Assert

#### State Verification
* No `DatabaseFile` is returned.

#### Interaction Verification
* Verify `factory.createDatabaseFile()` was **never** called.
* Verify `validator.validate()` was **never** called.
* Verify `repository.persist()` was **never** called.

### Expected Exception
`InvalidMetadataRequestException`

---

## UT-006 - Reject maxSize less than initialSize

### Purpose
Verify that MetadataManager rejects a metadata creation request when the specified maximum file size is smaller than the initial file size.

### Target
`MetadataManager.createMetadata(CreateDatabaseMetadataRequest)`

### Arrange
1. **Mocks:**
   * `factory`: Mock `IMetadataFactory`
   * `validator`: Mock `IMetadataValidator`
   * `repository`: Mock `IMetadataRepository`
2. **Inputs:**
   * A `CreateDatabaseMetadataRequest` where `maxSize` is smaller than `initialSize`.
3. **Mock Behavior:**
   * None

### Act
* Call: `MetadataManager.createMetadata(request)`

### Expected Outcome
* The operation is rejected immediately.
* No `DatabaseFile` aggregate is created.

### Assert

#### State Verification
* No `DatabaseFile` is returned.

#### Interaction Verification
* Verify `factory.createDatabaseFile()` was **never** called.
* Verify `validator.validate()` was **never** called.
* Verify `repository.persist()` was **never** called.

### Expected Exception
`InvalidMetadataRequestException`

---

## UT-007 - Reject Invalid Growth Increment

### Purpose
Verify that MetadataManager rejects a metadata creation request when auto-growth is enabled but the specified growth increment is not greater than zero.

### Target
`MetadataManager.createMetadata(CreateDatabaseMetadataRequest)`

### Arrange
1. **Mocks:**
   * `factory`: Mock `IMetadataFactory`
   * `validator`: Mock `IMetadataValidator`
   * `repository`: Mock `IMetadataRepository`
2. **Inputs:**
    * A `CreateDatabaseMetadataRequest` where:
     * `autoGrowthEnabled = true`
     * `growthIncrement <= 0`
3. **Mock Behavior:**
   * None

### Act
* Call: `MetadataManager.createMetadata(request)`

### Expected Outcome
 * The operation is rejected immediately.
 * No `DatabaseFile` aggregate is created.

### Assert

#### State Verification
 * No `DatabaseFile` is returned.

#### Interaction Verification
 * Verify `factory.createDatabaseFile()` was **never** called.
 * Verify `validator.validate()` was **never** called.
 * Verify `repository.persist()` was **never** called.

### Expected Exception
`InvalidMetadataRequestException`

---

## UT-008 - Abort Creation When Aggregate Factory Fails

### Purpose
Verify that MetadataManager propagates an AggregateCreationException when MetadataFactory fails to create a valid DatabaseFile aggregate.

### Target
`MetadataManager.createMetadata(CreateDatabaseMetadataRequest)`

### Arrange
1. **Mocks:**
   * `factory`: Mock `IMetadataFactory`
   * `validator`: Mock `IMetadataValidator`
   * `repository`: Mock `IMetadataRepository`
2. **Inputs:**
   * A valid `CreateDatabaseMetadataRequest` 
3. **Mock Behavior:**
   * Configure `factory.createDatabaseFile(request)` to throw `AggregateCreationException`.

### Act
* Call: `MetadataManager.createMetadata(request)`

### Expected Outcome
* The operation is aborted immediately.
* The exception is propagated to the caller.

### Assert

#### State Verification
* No `DatabaseFile` is returned.

#### Interaction Verification
* Verify `factory.createDatabaseFile(request)` was called **exactly once**.
* Verify `validator.validate()` was **never** called.
* Verify `repository.persist()` was **never** called.

### Expected Exception
`AggregateCreationException`

---



## UT-010 - Propagate Metadata Validation Exception

### Purpose
Verify that MetadataManager aborts the metadata creation process and propagates a `MetadataValidationException` when the created `DatabaseFile` fails domain validation.

### Target
`MetadataManager.createMetadata(CreateDatabaseMetadataRequest)`

### Arrange
1. **Mocks:**
   * `factory`: Mock `IMetadataFactory`
   * `validator`: Mock `IMetadataValidator`
   * `repository`: Mock `IMetadataRepository`
2. **Inputs:**
   * A valid `CreateDatabaseMetadataRequest` (named `request`)
3. **Mock Behavior:**
   * Create a dummy `DatabaseFile` instance (named `expectedFile`).
   * Configure `factory.createDatabaseFile(request)` to return `expectedFile`.
   * Configure `validator.validate(expectedFile)` to return `ValidationResult` with `isValid = false` and containing violation messages.

### Act
* Call: `MetadataManager.createMetadata(request)`

### Expected Outcome
* The operation is aborted immediately.
* The validation exception is propagated to the caller.
* The metadata is not persisted.

### Assert

#### State Verification
* No `DatabaseFile` is returned.

#### Interaction Verification
* Verify `factory.createDatabaseFile(request)` was called **exactly once**.
* Verify `validator.validate(expectedFile)` was called **exactly once**.
* Verify `repository.persist()` was **never** called.

### Expected Exception
`MetadataValidationException`

---

## UT-012 - Propagate MetadataPersistenceException

### Purpose
Verify that MetadataManager propagates a `MetadataPersistenceException` when MetadataRepository fails to persist the metadata to disk.

### Target
`MetadataManager.createMetadata(CreateDatabaseMetadataRequest)`

### Arrange
1. **Mocks:**
   * `factory`: Mock `IMetadataFactory`
   * `validator`: Mock `IMetadataValidator`
   * `repository`: Mock `IMetadataRepository`
2. **Inputs:**
   * A valid `CreateDatabaseMetadataRequest` (named `request`)
3. **Mock Behavior:**
   * Create a dummy `DatabaseFile` instance (named `expectedFile`).
   * Configure `factory.createDatabaseFile(request)` to return `expectedFile`.
   * Configure `validator.validate(expectedFile)` to complete successfully.
   * Configure `repository.persist(expectedFile)` to throw `MetadataPersistenceException`.

### Act
* Call: `MetadataManager.createMetadata(request)`

### Expected Outcome
* The operation is aborted.
* The persistence exception is propagated to the caller.
* The metadata is not successfully persisted.

### Assert

#### State Verification
* No `DatabaseFile` is returned.

#### Interaction Verification
* Verify `factory.createDatabaseFile(request)` was called **once**.
* Verify `validator.validate(expectedFile)` was called **once**.
* Verify `repository.persist(expectedFile)` was called **once**.

### Expected Exception
`MetadataPersistenceException`

---

## UT-013 - Initialize FileState to ONLINE

### Purpose
Verify that a newly created `DatabaseFile` is initialized with the default file state `ONLINE`.

### Target
`MetadataFactory.createDatabaseFile(CreateDatabaseMetadataRequest)`

### Arrange
1. **Dependencies:**
   * None
2. **Inputs:**
   * A valid request.
3. **Mock Behavior:**
   * None

### Act
* Call: `databaseFile = factory.createDatabaseFile(request)`

### Expected Outcome
* A valid `DatabaseFile` aggregate is created.
* The initial `FileState` is set to `ONLINE`.

### Assert

#### State Verification
* Verify `databaseFile.getState() == FileState.ONLINE`.

#### Interaction Verification
* None

### Expected Exception
None

---

## UT-014 - Initialize File Statistics

### Purpose
Verify that a newly created `DatabaseFile` initializes its file statistics with the correct default values.

### Target
`MetadataFactory.createDatabaseFile(CreateDatabaseMetadataRequest)`

### Arrange
1. **Dependencies:**
   * None

2. **Inputs:**
   * A valid `CreateDatabaseMetadataRequest` where:
     * `initialSize = 100 MB`

3. **Mock Behavior:**
   * None

### Act
* Call: `databaseFile = factory.createDatabaseFile(request)`

### Expected Outcome
* A valid `DatabaseFile` aggregate is created.
* The file statistics are initialized correctly.

### Assert

#### State Verification
* Verify `databaseFile.getStatistics().getUsedSize() == 0`.
* Verify `databaseFile.getStatistics().getFreeSize() == request.getInitialSize()`.

#### Interaction Verification
* None

### Expected Exception
None

---

## UT-015 - Initialize File Header

### Purpose
Verify that a newly created `DatabaseFile` initializes its file header with the correct default metadata values.

### Target
`MetadataFactory.createDatabaseFile(CreateDatabaseMetadataRequest)`

### Arrange
1. **Dependencies:**
   * None

2. **Inputs:**
   * A valid `CreateDatabaseMetadataRequest`

3. **Mock Behavior:**
   * None

### Act
* Call: `databaseFile = factory.createDatabaseFile(request)`

### Expected Outcome
* A valid `DatabaseFile` aggregate is created.
* The file header is initialized with the correct default values.

### Assert

#### State Verification
* Verify `databaseFile.getHeader().getMagicNumber()` equals the expected database file signature.
* Verify `databaseFile.getHeader().getVersion()` equals the current supported file format version.
* Verify `databaseFile.getHeader().getPageSize() == request.getPageSize()`.
* Verify `databaseFile.getHeader().getPageCount() == 0`.

#### Interaction Verification
* None

### Expected Exception
None

---

# Integration Test Specifications

## IT-001 - Create Metadata Successfully

### Purpose

Verify that all real components collaborate correctly to create, validate, and persist a new `DatabaseFile` aggregate when a valid metadata request is provided.

### Target

`MetadataManager.createMetadata(CreateDatabaseMetadataRequest)`

### Arrange

#### Test Environment

##### Real Components

- `MetadataManager`
- `MetadataFactory`
- `MetadataValidator`
- `MetadataRepository`
- `DatabaseFile`

##### Infrastructure

- A temporary test directory is available.
- The repository is configured to write to the real file system.

#### Inputs

- A valid `CreateDatabaseMetadataRequest`.
- A writable storage path.

### Act

Call:

```java
MetadataManager.createMetadata(request)
```

### Expected Outcome

- The operation completes successfully.
- A valid `DatabaseFile` aggregate is returned.
- Metadata is successfully persisted to disk.
- The metadata file is created at the target location.

### Assert

#### State Verification

Verify that:

- A `DatabaseFile` instance is returned.
- `databaseFile.getState() == FileState.ONLINE`.
- `databaseFile.getHeader()` is initialized correctly.
- `databaseFile.getStatistics()` is initialized correctly.
- The metadata file exists on disk.
- The persisted metadata matches the state of the returned `DatabaseFile`.

#### Component Integration Verification

Verify that the following real components collaborate successfully during the operation:

- `MetadataManager`
- `MetadataFactory`
- `MetadataValidator`
- `MetadataRepository`
- File System

No component reports an unexpected error.

### Expected Exception

None

---

## IT-003 - Disk Write Failure During Metadata Persistence

### Purpose

Verify that a disk write failure occurring during metadata persistence is correctly propagated through the real component chain and causes the entire metadata creation operation to fail safely.

### Target

`MetadataManager.createMetadata(CreateDatabaseMetadataRequest)`

### Arrange

#### Test Environment

##### Real Components

- `MetadataManager`
- `MetadataFactory`
- `MetadataValidator`
- `MetadataRepository`
- `DatabaseFile`

##### Infrastructure

- A storage location configured to fail during file write (e.g., read-only directory, invalid storage device, or simulated I/O failure).

#### Inputs

- A valid `CreateDatabaseMetadataRequest`.
- A storage path that will fail during metadata persistence.

### Act

Call:

```java
MetadataManager.createMetadata(request)
```

### Expected Outcome

- Metadata creation is aborted.
- The metadata is not successfully persisted.
- The persistence failure is propagated back to the caller.

### Assert

#### State Verification

Verify that:

- No valid metadata file exists at the target location.
- No usable metadata has been persisted.
- No partially written metadata is considered valid.

#### Component Integration Verification

Verify that the real components collaborate through the following execution path:

- `MetadataManager`
- `MetadataFactory`
- `MetadataValidator`
- `MetadataRepository`
- File System

The disk write failure is propagated correctly back through the component chain.

### Expected Exception

`MetadataPersistenceException`

---

## IT-004 - Failed metadata creation leaves no partial file or corrupted metadata on disk

### Purpose

Verify that when metadata persistence fails, the storage system does not leave partially written or corrupted metadata on disk, preserving storage consistency.

### Target

`MetadataManager.createMetadata(CreateDatabaseMetadataRequest)`

### Arrange

#### Test Environment

##### Real Components

- `MetadataManager`
- `MetadataFactory`
- `MetadataValidator`
- `MetadataRepository`
- `DatabaseFile`

##### Infrastructure

- A storage environment configured to fail during metadata persistence after partial data has been written.

#### Inputs

- A valid `CreateDatabaseMetadataRequest`.

### Act

Call:

```java
MetadataManager.createMetadata(request)
```

### Expected Outcome

- Metadata creation fails.
- No valid metadata is persisted.
- The storage remains in a consistent state.

### Assert

#### State Verification

Verify that:

- No partially written metadata file exists.
- No corrupted metadata remains on disk.
- The storage directory remains consistent after the failed operation.

#### Component Integration Verification

Verify that the persistence workflow is aborted safely after the write failure and the storage system performs the required cleanup or rollback.

### Expected Exception

`MetadataPersistenceException`

---
