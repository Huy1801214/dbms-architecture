## Target

`MetadataManager.loadMetadata()`

---

## Related Use Case

UC-FM-002 - Load Metadata From Disk

---

## Purpose

Verify that the Load Metadata From Disk use case correctly restores a valid `DatabaseFile` aggregate from persistent storage and properly handles all loading, deserialization, reconstruction, and recovery failures.

---

# Components Under Test

## Primary Component

- `MetadataManager`

## Collaborators

- MetadataRepository
- MetadataFactory
- MetadataValidator
- DatabaseFile

---

# Unit Test Scenarios

| ID | Scenario | Group | Priority |
|----|----------|-------|----------|
| UT-001 | Load metadata successfully | Happy Path | Critical |
| UT-002 | Metadata file not found | Repository | High |
| UT-003 | Metadata file cannot be read | Repository | High |
| UT-004 | Reject invalid file header (invalid magic number or unsupported version) | File Header Validation | High |
| UT-005 | Reject corrupted metadata during deserialization | Deserialization | High |
| UT-006 | Aggregate reconstruction fails | Aggregate Reconstruction | High |
| UT-007 | Repository throws MetadataLoadException | Repository | High |
| UT-008 | Restore FileStatistics correctly | State Restoration | Medium |
| UT-009 | Restore FileHeader correctly | State Restoration | Medium |

---

# Integration Test Scenarios


| ID | Scenario | Group | Priority |
|----|----------|-------|----------|
| IT-001 | Successfully load metadata from persistent storage | End-to-End | Critical |
| IT-002 | Reject corrupted metadata during loading | Data Integrity | High |
| IT-003 | Persisted metadata can be loaded without data loss | Round-trip Consistency | Critical |

---

# Test Coverage Matrix

| Component / Interface | Unit Test Coverage | Integration Test Coverage |
|-----------------------|--------------------|---------------------------|
| `MetadataManager`     | UT-001, UT-007 | IT-001, IT-002, IT-003 |
| `MetadataRepository`  | UT-002, UT-003, UT-007 | IT-001, IT-002, IT-003 |
| `MetadataFactory`     | UT-006 | IT-001, IT-003 |
| `MetadataValidator`   | UT-004 | IT-001, IT-002 |
| `DatabaseFile`        | UT-008, UT-009 | IT-001, IT-003 |

---

# Traceability

| Requirement | Sequence Step | Test Cases |
|------------|---------------|------------|
| Read metadata from persistent storage | Steps 2–4 | IT-001 |
| Deserialize metadata | Steps 5–6 | IT-001, IT-002 |
| Reconstruct DatabaseFile aggregate | Steps 7–8 | IT-001, IT-003 |
| Validate reconstructed aggregate | Step 9 | IT-001, IT-002 |
| Return reconstructed aggregate | Step 10 | IT-001 |
| Preserve metadata consistency after persistence | Round-trip verification | IT-003 |
---

# Unit Test Specifications

## UT-001 - Load Metadata Successfully

### Purpose

Verify that `MetadataManager` correctly orchestrates the loading, reconstruction, validation, and returning of a `DatabaseFile` when a valid metadata file exists.

### Target

`MetadataManager.loadMetadata(LoadMetadataRequest)`

### Arrange

1. **Mocks:**
   * `repository`: Mock `IMetadataRepository`
   * `factory`: Mock `IMetadataFactory`
   * `validator`: Mock `IMetadataValidator`
2. **Inputs:**
   * A valid `LoadMetadataRequest` (named `request`)
3. **Mock Behavior:**
   * Create a dummy `MetadataRecord` (named `metadataRecord`).
   * Create a dummy `DatabaseFile` instance (named `expectedFile`).
   * Configure `repository.load(request)` to return `metadataRecord`.
   * Configure `factory.reconstructDatabaseFile(metadataRecord)` to return `expectedFile`.
   * Configure `validator.validate(expectedFile)` to return a valid `ValidationResult`.

### Act

* Call:

```java
DatabaseFile loadedFile = metadataManager.loadMetadata(request);
```

### Expected Outcome

* The metadata is successfully loaded from the repository.
* The `DatabaseFile` aggregate is reconstructed successfully.
* Domain validation succeeds.
* The reconstructed aggregate is returned.

### Assert

#### State Verification

* Assert that `loadedFile` is reference-equal to `expectedFile`.

#### Interaction Verification

* Verify `repository.load(request)` was called **exactly once**.
* Verify `factory.reconstructDatabaseFile(metadataRecord)` was called **exactly once**.
* Verify `validator.validate(expectedFile)` was called **exactly once**.

### Expected Exception

None

---

## UT-002 - Metadata File Not Found

### Purpose

Verify that `MetadataManager` correctly propagates a `MetadataNotFoundException` when the requested metadata file does not exist and terminates the loading process immediately.

### Target

`MetadataManager.loadMetadata(LoadMetadataRequest)`

### Arrange

1. **Mocks:**
   * `repository`: Mock `IMetadataRepository`
   * `factory`: Mock `IMetadataFactory`
   * `validator`: Mock `IMetadataValidator`

2. **Inputs:**
   * A valid `LoadMetadataRequest` (named `request`) referencing a metadata file that does not exist.

3. **Mock Behavior:**
   * Configure `repository.load(request)` to throw `MetadataNotFoundException`.

### Act

* Call:

```java
metadataManager.loadMetadata(request);
```

### Expected Outcome

* The loading operation is aborted immediately.
* No metadata is reconstructed.
* No validation is performed.

### Assert

#### State Verification

* No `DatabaseFile` is returned.

#### Interaction Verification

* Verify `repository.load(request)` was called **exactly once**.
* Verify `factory.reconstructDatabaseFile(...)` was **never** called.
* Verify `validator.validate(...)` was **never** called.

### Expected Exception

`MetadataNotFoundException`

---

## UT-003 - Metadata File Cannot Be Read

### Purpose

Verify that `MetadataManager` correctly propagates a file system error when the metadata file exists but cannot be read, and terminates the loading process immediately.

### Target

`MetadataManager.loadMetadata(LoadMetadataRequest)`

### Arrange

1. **Mocks:**
   * `repository`: Mock `IMetadataRepository`
   * `factory`: Mock `IMetadataFactory`
   * `validator`: Mock `IMetadataValidator`

2. **Inputs:**
   * A valid `LoadMetadataRequest` (named `request`) referencing an existing metadata file.

3. **Mock Behavior:**
   * Configure `repository.load(request)` to throw `FileSystemException`.

### Act

* Call:

```java
metadataManager.loadMetadata(request);
```

### Expected Outcome

* The loading operation is aborted immediately.
* No metadata is reconstructed.
* No validation is performed.

### Assert

#### State Verification

* No `DatabaseFile` is returned.

#### Interaction Verification

* Verify `repository.load(request)` was called **exactly once**.
* Verify `factory.reconstructDatabaseFile(...)` was **never** called.
* Verify `validator.validate(...)` was **never** called.

### Expected Exception

`FileSystemException`

---

## UT-004 - Reject Invalid File Header

### Purpose

Verify that `MetadataManager` rejects metadata whose file header is invalid (e.g. invalid magic number or unsupported version) before attempting to reconstruct the aggregate.

### Target

`MetadataManager.loadMetadata(LoadMetadataRequest)`

### Arrange

1. **Mocks:**
   * `repository`: Mock `IMetadataRepository`
   * `factory`: Mock `IMetadataFactory`
   * `validator`: Mock `IMetadataValidator`

2. **Inputs:**
   * A valid `LoadMetadataRequest` (named `request`).

3. **Mock Behavior:**
   * Create a dummy `MetadataRecord` containing an invalid file header.
   * Configure `repository.load(request)` to return the invalid `metadataRecord`.
   * Configure the header validation step to throw `InvalidMetadataHeaderException`.

### Act

* Call:

```java
metadataManager.loadMetadata(request);
```

### Expected Outcome

* The loading operation is aborted immediately.
* The metadata is rejected before aggregate reconstruction.

### Assert

#### State Verification

* No `DatabaseFile` is returned.

#### Interaction Verification

* Verify `repository.load(request)` was called **exactly once**.
* Verify `factory.reconstructDatabaseFile(...)` was **never** called.
* Verify `validator.validate(...)` was **never** called.

### Expected Exception

`InvalidMetadataHeaderException`

---

## UT-005 - Reject Corrupted Metadata During Deserialization

### Purpose

Verify that `MetadataManager` correctly rejects corrupted metadata when deserialization fails and terminates the loading process before aggregate reconstruction.

### Target

`MetadataManager.loadMetadata(LoadMetadataRequest)`

### Arrange

1. **Mocks:**
   * `repository`: Mock `IMetadataRepository`
   * `factory`: Mock `IMetadataFactory`
   * `validator`: Mock `IMetadataValidator`

2. **Inputs:**
   * A valid `LoadMetadataRequest` (named `request`).

3. **Mock Behavior:**
   * Configure `repository.load(request)` to throw `MetadataDeserializationException`.

### Act

* Call:

```java
metadataManager.loadMetadata(request);
```

### Expected Outcome

* The loading operation is aborted.
* No aggregate is reconstructed.
* No validation is performed.

### Assert

#### State Verification

* No `DatabaseFile` is returned.

#### Interaction Verification

* Verify `repository.load(request)` was called **exactly once**.
* Verify `factory.reconstructDatabaseFile(...)` was **never** called.
* Verify `validator.validate(...)` was **never** called.

### Expected Exception

`MetadataDeserializationException`

---

## UT-006 - Aggregate Reconstruction Fails

### Purpose

Verify that `MetadataManager` correctly propagates an aggregate reconstruction failure and terminates the loading process before domain validation.

### Target

`MetadataManager.loadMetadata(LoadMetadataRequest)`

### Arrange

1. **Mocks:**
   * `repository`: Mock `IMetadataRepository`
   * `factory`: Mock `IMetadataFactory`
   * `validator`: Mock `IMetadataValidator`

2. **Inputs:**
   * A valid `LoadMetadataRequest` (named `request`).

3. **Mock Behavior:**
   * Create a valid `MetadataRecord`.
   * Configure `repository.load(request)` to return `metadataRecord`.
   * Configure `factory.reconstructDatabaseFile(metadataRecord)` to throw `AggregateReconstructionException`.

### Act

* Call:

```java
metadataManager.loadMetadata(request);
```

### Expected Outcome

* Aggregate reconstruction fails.
* The loading operation is aborted.
* Domain validation is never performed.

### Assert

#### State Verification

* No `DatabaseFile` is returned.

#### Interaction Verification

* Verify `repository.load(request)` was called **exactly once**.
* Verify `factory.reconstructDatabaseFile(metadataRecord)` was called **exactly once**.
* Verify `validator.validate(...)` was **never** called.

### Expected Exception

`AggregateReconstructionException`

---

## UT-007 - Repository Throws MetadataLoadException

### Purpose

Verify that `MetadataManager` correctly propagates `MetadataLoadException` thrown by the repository.

### Target

`MetadataManager.loadMetadata(LoadMetadataRequest)`

### Arrange

1. **Mocks:**
   * `repository`: Mock `IMetadataRepository`
   * `factory`: Mock `IMetadataFactory`
   * `validator`: Mock `IMetadataValidator`

2. **Inputs:**
   * A valid `LoadMetadataRequest`.

3. **Mock Behavior:**
   * Configure `repository.load(request)` to throw `MetadataLoadException`.

### Act

* Call:

```java
metadataManager.loadMetadata(request);
```

### Expected Outcome

* The loading operation is aborted immediately.

### Assert

#### State Verification

* No `DatabaseFile` is returned.

#### Interaction Verification

* Verify `repository.load(request)` was called **exactly once**.
* Verify `factory.reconstructDatabaseFile(...)` was **never** called.
* Verify `validator.validate(...)` was **never** called.

### Expected Exception

`MetadataLoadException`

---

## UT-008 - Restore FileStatistics Correctly

### Purpose

Verify that the loaded `DatabaseFile` correctly restores its `FileStatistics` from persisted metadata.

### Target

`MetadataManager.loadMetadata(LoadMetadataRequest)`

### Arrange

1. **Dependencies:**
   * None

2. **Inputs:**
   * A valid persisted metadata containing predefined file statistics.

3. **Mock Behavior:**
   * None

### Act

* Call:

```java
DatabaseFile loadedFile = metadataManager.loadMetadata(request);
```

### Expected Outcome

* The `FileStatistics` object is restored exactly as persisted.

### Assert

#### State Verification

* Verify `loadedFile.getStatistics().getUsedSize()` equals the persisted value.
* Verify `loadedFile.getStatistics().getFreeSize()` equals the persisted value.
* Verify `loadedFile.getStatistics().getAllocatedPages()` equals the persisted value.

#### Interaction Verification

* None

### Expected Exception

None

---

## UT-009 - Restore FileHeader Correctly

### Purpose

Verify that the loaded `DatabaseFile` correctly restores all persisted file header metadata.

### Target

`MetadataManager.loadMetadata(LoadMetadataRequest)`

### Arrange

1. **Dependencies:**
   * None

2. **Inputs:**
   * A valid persisted metadata containing a predefined file header.

3. **Mock Behavior:**
   * None

### Act

* Call:

```java
DatabaseFile loadedFile = metadataManager.loadMetadata(request);
```

### Expected Outcome

* The file header is restored exactly as persisted.

### Assert

#### State Verification

* Verify `loadedFile.getHeader().getMagicNumber()` equals the persisted value.
* Verify `loadedFile.getHeader().getVersion()` equals the persisted value.
* Verify `loadedFile.getHeader().getPageSize()` equals the persisted value.
* Verify `loadedFile.getHeader().getCreationTime()` equals the persisted value.

#### Interaction Verification

* None

### Expected Exception

None

---

# Integration Test Specifications

## IT-001 - Successfully Load Metadata From Persistent Storages

### Purpose

Verify that the entire metadata loading workflow succeeds using the real implementations of all collaborating components, restoring a valid `DatabaseFile` aggregate from persistent storage.

### Target

`MetadataManager.loadMetadata(LoadMetadataRequest)`

### Arrange

1. **Real Components**
   * `MetadataManager`
   * `MetadataRepository`
   * `MetadataFactory`
   * `MetadataValidator`

2. **Persistent Storage**
   * Prepare a valid metadata file on disk.
   * The metadata file contains a valid file header, configuration, and statistics.

3. **Inputs**
   * A valid `LoadMetadataRequest` referencing the prepared metadata file.

### Act

* Call:

```java
DatabaseFile loadedFile = metadataManager.loadMetadata(request);
```

### Expected Outcome

* The metadata file is successfully read from persistent storage.
* The metadata is successfully deserialized.
* The `DatabaseFile` aggregate is reconstructed successfully.
* Domain validation succeeds.
* A valid `DatabaseFile` aggregate is returned.

### Assert

#### State Verification

* Verify `loadedFile` is not null.
* Verify `loadedFile.getHeader()` is correctly restored.
* Verify `loadedFile.getConfiguration()` is correctly restored.
* Verify `loadedFile.getStatistics()` is correctly restored.

#### Component Integration Verification

Verify that the complete workflow executes successfully through the real components:

```
MetadataManager
        │
        ▼
MetadataRepository
        │
        ▼
MetadataFactory
        │
        ▼
MetadataValidator
        │
        ▼
DatabaseFile
```

No mocked component is involved during the execution.

### Expected Exception

None

---

## IT-002 - Reject Corrupted Metadata During Loading

### Purpose

Verify that the metadata loading workflow correctly detects corrupted metadata, aborts the loading process, and propagates the appropriate exception without reconstructing the aggregate.

### Target

`MetadataManager.loadMetadata(LoadMetadataRequest)`

### Arrange

1. **Real Components**
   * `MetadataManager`
   * `MetadataRepository`
   * `MetadataFactory`
   * `MetadataValidator`

2. **Persistent Storage**
   * Prepare a corrupted metadata file on disk.
   * The metadata file contains invalid or corrupted serialized content.

3. **Inputs**
   * A valid `LoadMetadataRequest` referencing the corrupted metadata file.

### Act

* Call:

```java
metadataManager.loadMetadata(request);
```

### Expected Outcome

* The corrupted metadata is detected during the loading process.
* Aggregate reconstruction is aborted.
* No `DatabaseFile` is returned.
* The loading operation fails safely.

### Assert

#### State Verification

* Verify no `DatabaseFile` is returned.
* Verify no metadata state is restored.

#### Component Integration Verification

Verify that the loading workflow stops immediately after detecting corrupted metadata.

```
MetadataManager
        │
        ▼
MetadataRepository
        │
        ▼
Deserializer
        │
        ▼
Exception
```

The following components must **not** participate in the workflow:

```
MetadataFactory

MetadataValidator
```

### Expected Exception

`MetadataDeserializationException`

---

## IT-003 - Persisted Metadata Can Be Loaded Without Data Loss

### Purpose

Verify that metadata previously persisted to disk can be loaded back without any loss or modification, ensuring the consistency of the persistence and loading workflows.

### Target

`MetadataManager.loadMetadata(LoadMetadataRequest)`

### Arrange

1. **Real Components**
   * `MetadataManager`
   * `MetadataRepository`
   * `MetadataFactory`
   * `MetadataValidator`

2. **Persistent Storage**
   * Create a valid `DatabaseFile`.
   * Persist the metadata to disk using the real persistence workflow.
   * Prepare a `LoadMetadataRequest` referencing the persisted metadata file.

3. **Expected Aggregate**
   * Keep a copy of the original `DatabaseFile` (or its expected metadata values) for comparison after loading.

### Act

```java
DatabaseFile loadedFile = metadataManager.loadMetadata(request);
```

### Expected Outcome

* The persisted metadata is successfully loaded.
* The reconstructed `DatabaseFile` is equivalent to the original persisted metadata.
* No metadata field is lost or modified during the persistence and loading cycle.

### Assert

#### State Verification

Verify that the loaded aggregate matches the original persisted metadata.

* Verify `loadedFile.getHeader()` equals the original header.
* Verify `loadedFile.getConfiguration()` equals the original configuration.
* Verify `loadedFile.getStatistics()` equals the original statistics.
* Verify `loadedFile.getIdentifier()` equals the original identifier.
* Verify `loadedFile.getState()` equals the original state.

#### Component Integration Verification

Verify the complete persistence and loading workflow executes successfully using the real components.

```
Persist Metadata
        │
        ▼
Persistent Storage
        │
        ▼
MetadataRepository
        │
        ▼
MetadataFactory
        │
        ▼
MetadataValidator
        │
        ▼
DatabaseFile
```

The reconstructed aggregate must be semantically equivalent to the originally persisted aggregate.

### Expected Exception

None

---

