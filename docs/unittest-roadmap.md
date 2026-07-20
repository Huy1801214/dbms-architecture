# 1-Week DBMS Unit Test & Implementation Plan

## Goal
- Complete all Unit Tests for the core DBMS modules.
- Implement the production methods required to make every test pass.
- Keep implementation incremental by following the TDD workflow:
  - Write Test
  - Run Test (Fail)
  - Implement Method
  - Run Test (Pass)
  - Refactor

---

# Day 1 — Database Object (Database)

### Unit Tests
- shouldOpenDatabase()
- shouldCloseDatabase()
- shouldRenameDatabase()
- shouldSetDatabaseOwner()
- shouldRejectOperationWhenClosed()
- shouldRejectOpenWhenAlreadyOnline()
- shouldRejectCloseWhenAlreadyOffline()
- shouldRejectRenameWhenDatabaseIsOpening()
- shouldRejectRenameWhenDatabaseIsClosing()
- shouldRejectEmptyDatabaseName()
- shouldRejectNullOwner()
- shouldRejectNullDatabaseName()
- shouldRejectBlankDatabaseName()
- shouldRejectDatabaseNameWithSpecialCharacters()
- shouldRejectDatabaseNameExceedingMaxLength()
- shouldRejectReservedDatabaseName()
- shouldInitializeOfflineDatabase()
- shouldMaintainStatusTransition()
- shouldKeepCreatedTimeUnchanged()
- shouldRejectNullDatabaseStatus()
- shouldRejectInvalidStatusTransition()
- shouldCloseAndReopenDatabase()

### Implement
- Database
- DatabaseStatus
- DatabaseClosedException
- Basic validation methods

### Deliverables
- Database entity completed
- All DatabaseTest cases passing

---

# Day 2 — Database Object (Schema)

### Unit Tests
- shouldCreateTable()
- shouldDropTable()
- shouldRenameTable()
- shouldCreateView()
- shouldDropView()
- shouldCreateStoredProcedure()
- shouldDropStoredProcedure()
- shouldCreateSequence()
- shouldDropSequence()
- shouldReturnExistingTable()

### Implement
- Schema
- Table registration
- View registration
- Procedure registration
- Sequence registration

### Deliverables
- Schema completed
- All SchemaTest cases passing

---

# Day 3 — Database Object (Table)

### Unit Tests
- shouldInsertRow()
- shouldUpdateRow()
- shouldDeleteRow()
- shouldTruncateTable()
- shouldAnalyzeTable()
- shouldIncreaseRowCount()
- shouldDecreaseRowCount()
- shouldReturnInsertedRow()
- shouldReturnUpdatedRow()

### Implement
- Table
- Row collection
- CRUD operations
- Row count management

### Deliverables
- Table entity completed
- All TableTest cases passing

---

# Day 4 — Database Object (Column + Row)

### ColumnTest
- shouldCreateColumn()
- shouldValidateColumnDefinition()
- shouldRejectInvalidDataType()
- shouldRejectInvalidLength()
- shouldAcceptNullableColumn()
- shouldRejectNullForNotNullColumn()
- shouldUpdateColumnMetadata()
- shouldChangeDefaultValue()

### RowTest
- shouldCreateRow()
- shouldUpdateRow()
- shouldDeleteRow()
- shouldReadRow()
- shouldCloneRowVersion()
- shouldUpdateRowVersion()
- shouldStoreTransactionId()
- shouldReturnColumnValue()
- shouldReplaceColumnValue()
- shouldMarkRowDeleted()

### Implement
- Column
- Row
- Metadata validation

### Deliverables
- Column completed
- Row completed
- All tests passing

---

# Day 5 — Constraint + Index

### ConstraintTest
- shouldValidatePrimaryKey()
- shouldRejectDuplicatePrimaryKey()
- shouldValidateForeignKey()
- shouldRejectBrokenForeignKey()
- shouldValidateUniqueConstraint()
- shouldRejectDuplicateUniqueValue()
- shouldValidateCheckConstraint()
- shouldRejectInvalidCheckConstraint()

### IndexTest
- shouldInsertKey()
- shouldSearchKey()
- shouldDeleteKey()
- shouldUpdateKey()
- shouldHandleDuplicateKey()
- shouldRebuildIndex()
- shouldReturnOrderedKeys()

### Implement
- PrimaryKey
- ForeignKey
- UniqueConstraint
- CheckConstraint
- BTreeIndex
- HashIndex

### Deliverables
- Constraint framework completed
- Basic Index implementation completed

---

# Day 6 — Remaining Database Objects

### Unit Tests
#### PartitionTest
- shouldPartitionTable()
- shouldDropPartition()
- shouldLocatePartition()
- shouldSplitPartition()
- shouldMergePartition()
- shouldMoveRowBetweenPartitions()

#### ViewTest
- shouldCreateView()
- shouldReadUnderlyingTable()
- shouldValidateViewDefinition()
- shouldExecuteViewQuery()
- shouldRefreshViewDefinition()

#### StoredProcedureTest
- shouldCreateProcedure()
- shouldExecuteProcedure()
- shouldPassProcedureParameters()
- shouldReturnProcedureResult()

#### TriggerTest
- shouldCreateTrigger()
- shouldFireTrigger()
- shouldExecuteBeforeInsertTrigger()
- shouldExecuteAfterInsertTrigger()
- shouldExecuteBeforeUpdateTrigger()
- shouldExecuteAfterUpdateTrigger()

#### SequenceTest
- shouldCreateSequence()
- shouldGenerateNextValue()
- shouldIncrementSequence()
- shouldResetSequence()
- shouldRespectStartValue()
- shouldReturnCurrentValue()

### Implement
- Partition
- View
- StoredProcedure
- Trigger
- Sequence

### Deliverables
- All Database Objects completed
- Every Database Objects unit test passing

---

# Day 7 — Storage Engine

### BufferPoolTest
- All unit tests

### PageManagerTest
- All unit tests

### FileManagerTest
- All unit tests

### PageTest
- All unit tests

### Implement
- BufferPool
- PageManager
- Page
- FileManager

### Deliverables
- Storage Engine core completed
- All Storage Engine unit tests passing

---

# Development Workflow

For every test case:

1. Write the Unit Test
2. Execute the test (expected to fail)
3. Implement the minimum production code
4. Execute the test again
5. Make the test pass
6. Refactor the implementation
7. Commit the changes

---

# Expected Outcome After One Week

- ✅ Database Object module fully completed
- ✅ Storage Engine core completed
- ✅ More than 100 unit tests passing
- ✅ Core DBMS domain model established
- ✅ Solid foundation for Query Processing, Transaction Management, Recovery, and Security modules