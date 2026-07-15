## Target

`MetadataManager.updateMetadata(UpdateDatabaseMetadataRequest)`

---

## Related Use Case

- UC-FM-002 - Load Metadata From Disk
- UC-FM-003 - Persist Metadata To Disk

---

## Purpose

Verify that an existing `DatabaseFile` aggregate can be updated correctly, validated, and persisted while ensuring that invalid update requests or update failures are safely handled.

---

# Components Under Test

## Primary Component

- `MetadataManager`

## Collaborators

- `MetadataRepository`
- `DatabaseFile`
- `MetadataValidator`

---

# Unit Test Scenarios

| ID | Scenario | Group | Priority |
|----|----------|-------|----------|
| UT-001 | Update metadata successfully | Happy Path | Critical |
| UT-002 | Metadata not found | Loading | High |
| UT-003 | Aggregate rejects metadata update | Domain Update | High |

---

# Integration Test Scenarios

| ID | Scenario | Group | Priority |
|----|----------|-------|----------|
| IT-001 | Update valid metadata successfully using the real component chain | End-to-End | Critical |
| IT-002 | Disk write failure safely aborts the update operation | File System | High |
---

# Test Coverage Matrix

| Component / Interface | Unit Test Coverage | Integration Test Coverage |
|-----------------------|--------------------|---------------------------|
| `MetadataManager` | UT-001, UT-002 | IT-001, IT-002 |
| `DatabaseFile` | UT-001, UT-003 | IT-001 |
| `MetadataValidator` | - | IT-001 |
| `MetadataRepository` | UT-002 | IT-001, IT-002 |
| `Disk` | - | IT-001, IT-002 |
---

# Traceability

| Requirement | Sequence Step | Test Cases |
|------------|---------------|------------|
| Load existing metadata | Steps 2–5 | UT-001, UT-002, IT-001 |
| Update aggregate | Step 6 | UT-001 |
| Persist updated metadata | Steps 7–12 | UT-001, IT-001 |
| Abort when metadata does not exist | Failure Path 1 | UT-002 |
| Abort when aggregate rejects update | Failure Path 2 |
| Abort when persistence fails | Failure Path 4 | IT-002 |
| Updated metadata remains consistent after persistence | Happy Path |

---

# Unit Test Specifications

## UT-001 - Update Metadata Successfully

### Purpose

Verify that `MetadataManager` correctly orchestrates the metadata update workflow by loading an existing `DatabaseFile` aggregate, applying the requested metadata changes, validating the updated aggregate, and persisting the updated metadata.

### Target

`MetadataManager.updateMetadata(UpdateDatabaseMetadataRequest)`

### Arrange

1. **Mocks:**
   * `repository`: Mock `IMetadataRepository`
   * `validator`: Mock `IMetadataValidator`

2. **Inputs:**
   * A valid `UpdateDatabaseMetadataRequest`.
   * An existing `DatabaseFile` aggregate (named `databaseFile`).

3. **Mock Behavior:**
   * Configure `repository.loadMetadata(request.getFileIdentifier())` to return `databaseFile`.
   * Configure `databaseFile.update(request)` to complete successfully.
   * Configure `validator.validate(databaseFile)` to return a successful `ValidationResult`.
   * Configure `repository.persist(databaseFile)` to complete successfully.

### Act

```java
DatabaseFile updated =
    metadataManager.updateMetadata(request);
```

### Expected Outcome

* The existing metadata is successfully loaded.
* The requested metadata changes are successfully applied.
* The updated aggregate passes validation.
* The updated metadata is successfully persisted.
* The updated `DatabaseFile` aggregate is returned.

### Assert

#### State Verification

* Verify the returned `DatabaseFile` is not null.
* Verify the returned aggregate reflects the requested metadata changes.

#### Interaction Verification

* Verify `repository.loadMetadata(request.getFileIdentifier())` was called **exactly once**.
* Verify `databaseFile.update(request)` was called **exactly once**.
* Verify `validator.validate(databaseFile)` was called **exactly once**.
* Verify `repository.persist(databaseFile)` was called **exactly once**.
* Verify the collaborators are invoked in the following order:

```
loadMetadata
      ↓
update
      ↓
validate
      ↓
persist
```

### Expected Exception

None

---

## UT-002 - Metadata Not Found

### Purpose

Verify that `MetadataManager` aborts the metadata update workflow when the requested `DatabaseFile` cannot be found.

### Target

`MetadataManager.updateMetadata(UpdateDatabaseMetadataRequest)`

### Arrange

1. **Mocks:**
   * `repository`: Mock `IMetadataRepository`
   * `validator`: Mock `IMetadataValidator`

2. **Inputs:**
   * A valid `UpdateDatabaseMetadataRequest`.

3. **Mock Behavior:**
   * Configure `repository.loadMetadata(request.getFileIdentifier())` to throw `MetadataNotFoundException`.

### Act

```java
metadataManager.updateMetadata(request);
```

### Expected Outcome

* The metadata loading operation fails because the requested metadata does not exist.
* The update workflow is aborted immediately.
* The exception is propagated to the caller.

### Assert

#### State Verification

* Verify no metadata is updated.
* Verify no metadata is persisted.

#### Interaction Verification

* Verify `repository.loadMetadata(request.getFileIdentifier())` was called **exactly once**.
* Verify `validator.validate(...)` was **never called**.
* Verify `repository.persist(...)` was **never called**.

### Expected Exception

`MetadataNotFoundException`

---

## UT-003 - Aggregate Rejects Metadata Update

### Purpose

Verify that `MetadataManager` aborts the update workflow when the `DatabaseFile` aggregate rejects the requested metadata changes.

### Target

`MetadataManager.updateMetadata(UpdateDatabaseMetadataRequest)`

### Arrange

1. **Mocks:**
   * `repository`: Mock `IMetadataRepository`
   * `validator`: Mock `IMetadataValidator`

2. **Inputs:**
   * A valid `UpdateDatabaseMetadataRequest`.
   * An existing `DatabaseFile` aggregate (named `databaseFile`).

3. **Mock Behavior:**
   * Configure `repository.loadMetadata(request.getFileIdentifier())` to return `databaseFile`.
   * Configure `databaseFile.update(request)` to throw `MetadataUpdateException`.

### Act

```java
metadataManager.updateMetadata(request);
```

### Expected Outcome

* The existing metadata is successfully loaded.
* The aggregate rejects the requested metadata update.
* The update workflow is aborted immediately.
* The exception is propagated to the caller.

### Assert

#### State Verification

* Verify the `DatabaseFile` aggregate remains unchanged.
* Verify no metadata is persisted.

#### Interaction Verification

* Verify `repository.loadMetadata(request.getFileIdentifier())` was called **exactly once**.
* Verify `databaseFile.update(request)` was called **exactly once**.
* Verify `validator.validate(databaseFile)` was **never called**.
* Verify `repository.persist(databaseFile)` was **never called**.

### Expected Exception

`MetadataUpdateException`

---

# Integration Test Specifications

## IT-001 - Update Metadata Successfully


### Purpose

Verify that an existing `DatabaseFile` can be successfully updated, validated, persisted, and subsequently reloaded using the real metadata persistence workflow.

### Target

`MetadataManager.updateMetadata(UpdateDatabaseMetadataRequest)`

### Arrange

1. **Real Components**
   * `MetadataManager`
   * `MetadataValidator`
   * `MetadataRepository`
   * `Disk`

2. **Persistent Storage**
   * Prepare a metadata file that already exists in persistent storage.

3. **Inputs**
   * A valid `UpdateDatabaseMetadataRequest` containing the metadata changes.

### Act

```java
DatabaseFile updated =
    metadataManager.updateMetadata(request);

DatabaseFile reloaded =
    metadataManager.loadMetadata(updated.getFileIdentifier());
```

### Expected Outcome

* The existing metadata is successfully loaded.
* The requested metadata changes are successfully applied.
* The updated metadata is successfully persisted.
* The persisted metadata can be successfully reloaded.
* The reloaded metadata accurately reflects the requested updates.

### Assert

#### State Verification

* Verify the updated `DatabaseFile` is not null.
* Verify the reloaded `DatabaseFile` is not null.
* Verify the reloaded metadata reflects all requested updates.
* Verify no existing metadata unrelated to the update is lost or corrupted.

#### Component Integration Verification

Verify that the real implementations of:

* `MetadataManager`
* `MetadataValidator`
* `MetadataRepository`
* `Disk`

work together successfully to complete the metadata update workflow.

### Expected Exception

None

---

## IT-002 - Failed Metadata Update Does Not Corrupt Existing Metadata

### Purpose

Verify that if the updated metadata cannot be persisted, the existing persisted metadata remains unchanged and no partial update is written to persistent storage.

### Target

`MetadataManager.updateMetadata(UpdateDatabaseMetadataRequest)`

### Arrange

1. **Real Components**
   * `MetadataManager`
   * `MetadataValidator`
   * `MetadataRepository`
   * `Disk`

2. **Persistent Storage**
   * Prepare an existing metadata file in persistent storage.

3. **Inputs**
   * A valid `UpdateDatabaseMetadataRequest`.

4. **Failure Setup**
   * Configure the storage device to fail during metadata writing.

### Act

```java
metadataManager.updateMetadata(request);
```

### Expected Outcome

* The update workflow is aborted.
* The persistence failure is propagated.
* The previously persisted metadata remains unchanged.

### Assert

#### State Verification

* Verify the original metadata can still be successfully loaded.
* Verify none of the requested metadata updates were persisted.
* Verify no partial or corrupted metadata exists in persistent storage.

#### Component Integration Verification

Verify that the real metadata persistence workflow safely aborts without corrupting existing metadata when the storage layer fails.

### Expected Exception

`DiskWriteException`

---



