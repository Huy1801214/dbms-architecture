## Target

`MetadataManager.persistMetadata(DatabaseFile)`

---

## Related Use Case

UC-FM-003 - Persist Metadata To Disk

---

## Purpose

Verify that the Persist Metadata use case correctly validates, serializes, and persists a valid `DatabaseFile` aggregate to persistent storage while safely handling validation, serialization, and disk write failures.

---

# Components Under Test

## Primary Component

- MetadataManager

## Collaborators

- MetadataValidator
- MetadataRepository
- DatabaseFile
- Disk

---

# Unit Test Scenarios

| ID | Scenario | Group | Priority |
|----|----------|-------|----------|
| UT-001 | Persist metadata successfully | Happy Path | Critical |
| UT-002 | Validation fails → Repository is never called | Validation | High |
| UT-003 | Repository throws `MetadataPersistenceException` | Repository Failure | High |
| UT-004 | Disk write failure propagates correctly | Disk Failure | Critical |

---

# Integration Test Scenarios

| ID | Scenario | Group | Priority |
|----|----------|-------|----------|
| IT-001 | Successfully persist metadata to persistent storage | End-to-End | Critical |
| IT-002 | Disk write failure safely aborts persistence | File System | High |
| IT-003 | Persisted metadata can be loaded without data loss | Round-trip Consistency | Critical |

---

# Test Coverage Matrix

| Component / Interface | Unit Test Coverage | Integration Test Coverage |
|-----------------------|--------------------|---------------------------|
| `MetadataManager` | UT-001, UT-002, UT-003, UT-004 | IT-001, IT-002, IT-003 |
| `IMetadataValidator` | UT-001, UT-002 | IT-001 |
| `IMetadataRepository` | UT-001, UT-003, UT-004 | IT-001, IT-002, IT-003 |
| `DatabaseFile` | UT-001, UT-002 | IT-001, IT-003 |
| `Disk` | UT-004 | IT-001, IT-002, IT-003 |

---

# Traceability

| Requirement | Sequence Step | Test Cases |
|------------|---------------|------------|
| Validate aggregate before persistence | Steps 2–5 | UT-001, UT-002, IT-001 |
| Serialize and persist metadata | Steps 6–10 | UT-001, UT-003, IT-001 |
| Write metadata to disk | Steps 8–10 | UT-004, IT-001, IT-002 |
| Metadata is durably stored | Step 11 | IT-001 |
| Persisted metadata remains recoverable | Persist → Load | IT-003 |


---

# Unit Test Specifications

## UT-001 - Persist Metadata Successfully

### Purpose

Verify that `MetadataManager` correctly orchestrates the metadata persistence workflow by validating a valid `DatabaseFile` aggregate and delegating the persistence operation to the repository.

### Target

`MetadataManager.persistMetadata(DatabaseFile)`

### Arrange

1. **Mocks:**
   * `validator`: Mock `IMetadataValidator`
   * `repository`: Mock `IMetadataRepository`

2. **Inputs:**
   * A valid `DatabaseFile` aggregate (named `databaseFile`).

3. **Mock Behavior:**
   * Configure `validator.validate(databaseFile)` to return a successful `ValidationResult`.
   * Configure `repository.persist(databaseFile)` to complete successfully (no exception).

### Act

* Call:

```java
metadataManager.persistMetadata(databaseFile);
```

### Expected Outcome

* The aggregate is successfully validated.
* The aggregate is successfully persisted.
* The persistence workflow completes successfully.
* No exception is thrown.

### Assert

#### State Verification

* No in-memory state of the `DatabaseFile` aggregate is modified by the persistence workflow.

#### Interaction Verification

* Verify `validator.validate(databaseFile)` was called **exactly once**.
* Verify `repository.persist(databaseFile)` was called **exactly once**.
* Verify the repository is invoked **only after** validation succeeds.

### Expected Exception

None

---

## UT-002 - Validation Fails → Repository Is Never Called

### Purpose

Verify that the persistence workflow stops immediately when the `DatabaseFile` aggregate fails validation.

This test ensures that invalid metadata is **never persisted** and that the repository is **not invoked** if validation fails.

---

### Target

`MetadataManager.persistMetadata(DatabaseFile databaseFile)`

---

### Arrange

#### 1. Mocks

* `MetadataValidator`
* `MetadataRepository`

#### 2. Inputs

* A `DatabaseFile` aggregate containing invalid metadata (e.g., empty database name, invalid page size, missing file identifier, etc.).

#### 3. Mock Behavior

* `validator.validate(databaseFile)` returns a validation failure.
* `repository.persist(...)` is **not configured**, since it must never be called.

---

### Act

Call:

```java
metadataManager.persistMetadata(databaseFile);
```

---

### Expected Outcome

* The aggregate validation fails.
* The persistence workflow terminates immediately.
* The repository is never invoked.
* A validation exception is propagated to the caller.

---

### Assert

#### State Verification

* The `DatabaseFile` aggregate remains unchanged.
* No persistence operation is performed.

#### Interaction Verification

* Verify `validator.validate(databaseFile)` was called **exactly once**.
* Verify `repository.persist(databaseFile)` was **never called**.
* Verify no further interactions occur with the repository after validation fails.

---

### Expected Exception

```java
MetadataValidationException
```

## UT-003 - Repository Throws MetadataPersistenceException

### Purpose

Verify that `MetadataManager` correctly propagates a `MetadataPersistenceException` when the repository fails to persist a valid `DatabaseFile` aggregate.

### Target

`MetadataManager.persistMetadata(DatabaseFile)`

### Arrange

1. **Mocks:**
   * `validator`: Mock `IMetadataValidator`
   * `repository`: Mock `IMetadataRepository`

2. **Inputs:**
   * A valid `DatabaseFile` aggregate (named `databaseFile`).

3. **Mock Behavior:**
   * Configure `validator.validate(databaseFile)` to return a successful `ValidationResult`.
   * Configure `repository.persist(databaseFile)` to throw `MetadataPersistenceException`.

### Act

* Call:

```java
metadataManager.persistMetadata(databaseFile);
```

### Expected Outcome

* The aggregate passes validation.
* The repository fails to persist the metadata.
* The persistence workflow is aborted immediately.
* The exception is propagated to the caller.

### Assert

#### State Verification

* The `DatabaseFile` aggregate remains unchanged.

#### Interaction Verification

* Verify `validator.validate(databaseFile)` was called **exactly once**.
* Verify `repository.persist(databaseFile)` was called **exactly once**.
* Verify the repository is invoked only after validation succeeds.

### Expected Exception

`MetadataPersistenceException`

---

# Integration Test Specifications

## IT-001 - Persist Valid Metadata Successfully

### Purpose

Verify that a valid `DatabaseFile` aggregate can be successfully validated, serialized, and persisted to persistent storage using the real metadata persistence workflow.

### Target

`MetadataManager.persistMetadata(DatabaseFile)`

### Arrange

1. **Real Components**
   * `MetadataManager`
   * `MetadataValidator`
   * `MetadataRepository`
   * `Disk`

2. **Persistent Storage**
   * Prepare an empty storage location for metadata persistence.

3. **Inputs**
   * A valid `DatabaseFile` aggregate.

### Act

```java
metadataManager.persistMetadata(databaseFile);
```

### Expected Outcome

* The metadata persistence workflow completes successfully.
* The metadata is durably written to persistent storage.
* No exception is thrown.

### Assert

#### State Verification

* Verify the metadata file exists in persistent storage.
* Verify the persisted metadata is not empty.
* Verify the persistence operation completes successfully.

#### Component Integration Verification

Verify the complete persistence workflow executes successfully using the real components.

```
MetadataManager
        │
        ▼
MetadataValidator
        │
        ▼
MetadataRepository
        │
        ▼
Disk
```

The metadata must be successfully validated, serialized, and written to persistent storage.

### Expected Exception

None

---

## IT-002 - Disk Write Failure Safely Aborts Persistence

### Purpose

Verify that the metadata persistence workflow safely aborts when the storage device fails to write metadata, and that the failure is correctly propagated to the caller.

### Target

`MetadataManager.persistMetadata(DatabaseFile)`

### Arrange

1. **Real Components**
   * `MetadataManager`
   * `MetadataValidator`
   * `MetadataRepository`
   * `Disk`

2. **Persistent Storage**
   * Configure the storage device to simulate a disk write failure (e.g., read-only storage, unavailable disk, or I/O failure).

3. **Inputs**
   * A valid `DatabaseFile` aggregate.

### Act

```java
metadataManager.persistMetadata(databaseFile);
```

### Expected Outcome

* The persistence workflow is aborted.
* No metadata is successfully written to persistent storage.
* The disk write failure is propagated to the caller.

### Assert

#### State Verification

* Verify no metadata file is created or updated in persistent storage.
* Verify no partial or corrupted metadata remains after the failed operation.

#### Component Integration Verification

Verify that the real persistence workflow correctly propagates the storage failure through the component chain.

The failure occurring in the storage layer must prevent the persistence operation from completing successfully.

### Expected Exception

`DiskWriteException`

---

## IT-003 - Persisted Metadata Can Be Successfully Loaded Without Data Loss

### Purpose

Verify that metadata persisted to storage can be successfully loaded back without loss or corruption, ensuring data consistency across the persistence workflow.

### Target

`MetadataManager.persistMetadata(DatabaseFile)`

and

`MetadataManager.loadMetadata()`

### Arrange

1. **Real Components**
   * `MetadataManager`
   * `MetadataValidator`
   * `MetadataRepository`
   * `Disk`

2. **Persistent Storage**
   * Prepare an empty metadata storage location.

3. **Inputs**
   * A valid `DatabaseFile` aggregate.

### Act

```java
metadataManager.persistMetadata(databaseFile);

DatabaseFile loadedMetadata =
        metadataManager.loadMetadata();
```

### Expected Outcome

* Metadata is successfully persisted.
* Metadata is successfully loaded from persistent storage.
* The loaded metadata accurately represents the originally persisted aggregate.

### Assert

#### State Verification

* Verify the loaded DatabaseFile is not null.
* Verify the loaded metadata preserves all persisted values.
* Verify no metadata corruption or data loss occurs during the persist-load cycle.

#### Component Integration Verification

Verify that the real persistence and loading components work together correctly to maintain metadata consistency across storage.

### Expected Exception

None
