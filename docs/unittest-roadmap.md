# DBMS Unit Test Roadmap

## Phase 1 – Database Entity 
- `DatabaseTest`
  - `shouldOpenDatabase()`
  - `shouldCloseDatabase()`
  - `shouldRenameDatabase()`
  - `shouldSetDatabaseOwner()`
  - `shouldRejectOperationWhenClosed()`

---

## Phase 2 – Schema & Table
- `SchemaTest`
- `TableTest`

---

## Phase 3 – Column & Row
- `ColumnTest`
- `RowTest`

---

## Phase 4 – Constraints
- `ConstraintTest`
  - Primary Key
  - Foreign Key
  - Unique
  - Check

---

## Phase 5 – Index
- `IndexTest`

---

## Phase 6 – Other Database Objects
- `PartitionTest`
- `ViewTest`
- `StoredProcedureTest`
- `TriggerTest`
- `SequenceTest`

---

## Phase 7 – Database Objects Integration
- `DatabaseObjectsIntegrationTest`

---

## Phase 8 – Storage Engine
- `PageTest`
- `FileManagerTest`
- `BufferPoolTest`
- `PageManagerTest`
- `StorageIntegrationTest`

---

## Phase 9 – Database Server
- `ConfigurationManagerTest`
- `SecurityManagerTest`
- `MonitoringManagerTest`
- `DatabaseManagerTest`
- `DatabaseServerTest`
- `DatabaseServerIntegrationTest`

---

## Phase 10 – Query Processing
- `LexerTest`
- `SQLParserTest`
- `ASTTest`
- `LogicalPlanTest`
- `PhysicalPlanTest`
- `StatisticsManagerTest`
- `QueryOptimizerTest`
- `QueryExecutorTest`
- `QueryProcessingIntegrationTest`

---

## Phase 11 – Transaction Management
- Lock Manager
- Transaction Manager
- MVCC
- Scheduler
- WAL
- Transaction Integration Tests

---

## Phase 12 – Recovery
- Log Manager
- Checkpoint Manager
- Recovery Manager
- Recovery Integration Tests

---

## Phase 13 – Security
- User
- Role
- Permission
- Authentication
- Authorization
- Security Integration Tests

---

## Phase 14 – Replication
- Replication Manager
- Primary/Replica
- Log Shipping
- Replication Integration Tests