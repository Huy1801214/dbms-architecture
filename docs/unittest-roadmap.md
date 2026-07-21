# 2-Day DBMS Core Testing Roadmap

## Goal

- Complete the unit test specification for all modules.
- Implement only the core unit and integration tests that demonstrate the fundamental capabilities of the DBMS.

---

# Day 1 — Database Management & Database Objects

## Database Server Module

### Critical Unit Tests

#### DatabaseTest
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
- shouldCreateSchema()
- shouldDropSchema()
- shouldRejectDuplicateSchemaName()
- shouldRejectSchemaOperationWhenClosed()
- shouldRejectSchemaNameWithSpecialCharacters()

#### DatabaseManagerTest
- shouldCreateDatabase()
- shouldOpenDatabase()
- shouldCloseDatabase()
- shouldDropDatabase()
- shouldRejectDuplicateDatabaseName()
- shouldRejectUnknownDatabase()
- shouldIncreaseDatabaseCountAfterCreation()
- shouldDecreaseDatabaseCountAfterDrop()
- shouldAssignUniqueDatabaseId()

### Critical Integration Tests

- shouldCreateDatabaseThroughServer()
- shouldOpenExistingDatabase()

---

## Database Objects Module

### Critical Unit Tests

#### SchemaTest
- shouldCreateTable()
- shouldDropTable()
- shouldRenameTable()
- shouldFindTableByName()
- shouldListAllTables()
- shouldRejectDuplicateTableName()
- shouldRejectUnknownTable()
- shouldCreateView()
- shouldDropView()
- shouldCreateStoredProcedure()
- shouldDropStoredProcedure()
- shouldCreateSequence()
- shouldDropSequence()

#### TableTest
- shouldInsertRow()
- shouldUpdateRow()
- shouldDeleteRow()
- shouldTruncateTable()
- shouldFindRowById()
- shouldListAllRows()
- shouldRejectDuplicateRow()
- shouldRejectUnknownRow()

#### ConstraintTest
- shouldValidatePrimaryKey()
- shouldValidateForeignKey()

#### IndexTest
- shouldInsertKey()
- shouldSearchKey()

### Critical Integration Tests

- shouldCreateDatabaseWithSchemaAndTable()
- shouldInsertRowWithConstraints()
- shouldUseIndexForQueryExecution()
- shouldValidateForeignKeyAcrossTables()

---

# Day 2 — Storage Engine & Query Processing

## Storage Engine Module

### Critical Unit Tests

#### BufferPoolTest
- shouldFetchExistingPage()
- shouldAllocateNewPage()
- shouldFlushDirtyPage()
- shouldEvictPage()

#### PageManagerTest
- shouldAllocatePage()
- shouldReadPage()
- shouldWritePage()

#### FileManagerTest
- shouldCreateDataFile()
- shouldReadDataFile()
- shouldWriteDataFile()

### Critical Integration Tests

- shouldAllocateAndWritePage()
- shouldReadPageFromDisk()
- shouldFlushDirtyPageToDisk()
- shouldPersistPageAcrossRestart()

---

## Query Processing Module

### Critical Unit Tests

#### SQLParserTest
- shouldParseValidSQL()
- shouldRejectInvalidSQLSyntax()

#### QueryOptimizerTest
- shouldOptimizeLogicalPlan()
- shouldGeneratePhysicalPlan()

#### QueryExecutorTest
- shouldExecutePhysicalPlan()
- shouldFetchResultRows()

### Critical Integration Tests

- shouldParseOptimizeAndExecuteQuery()
- shouldGenerateLogicalAndPhysicalPlan()
- shouldExecuteOptimizedQueryPlan()

---

# Remaining Modules

The following modules will have complete test specifications but will be implemented in later phases:

- Metadata
- Transaction Management
- Recovery
- Replication
- Security
- Monitoring
- Administration

---

# Deliverables

- Complete unit test specification for every module.
- Implement all critical unit tests.
- Implement all critical integration tests.
- Complete sequence diagrams for implemented tests.
- Supporting production code for implemented tests.