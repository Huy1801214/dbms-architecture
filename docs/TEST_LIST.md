# DBMS Architecture Test Cases List

* **Integration Tests**:

---

## Table of Contents
1. [Catalog Module](#1-catalog-module)
2. [Database Objects Module](#2-database-objects-module)
3. [Database Server Module](#3-database-server-module)
4. [Query Processing Module](#4-query-processing-module)
5. [Recovery Module](#5-recovery-module)
6. [Replication Module](#6-replication-module)
7. [Security Module](#7-security-module)
8. [Storage Engine Module](#8-storage-engine-module)
9. [Transaction & Lock Module](#9-transaction--lock-module)

---

## 1. Catalog Module
* **Unit Tests**:
  * **CatalogManagerTest**
    * `shouldRegisterDatabase()`
    * `shouldRegisterSchema()`
    * `shouldRegisterTable()`
    * `shouldRegisterIndex()`
    * `shouldFindDatabase()`
    * `shouldFindSchema()`
    * `shouldFindTable()`
    * `shouldFindIndex()`
    * `shouldRefreshMetadata()`
    * `shouldInvalidateMetadataCache()`
    * `shouldLoadMetadataFromDisk()`
    * `shouldCacheFrequentlyUsedMetadata()`
    * `shouldUpdateTableMetadata()`
    * `shouldUpdateIndexMetadata()`
    * `shouldRemoveTableMetadata()`
    * `shouldRemoveSchemaMetadata()`
    * `shouldRemoveDatabaseMetadata()`
    * `shouldDetectDuplicateTableRegistration()`
    * `shouldRejectUnknownDatabase()`
    * `shouldReturnCachedMetadata()`
* **Integration Tests**:
  * `shouldRegisterDatabaseMetadata()`
  * `shouldRegisterSchemaMetadata()`
  * `shouldRegisterTableMetadata()`
  * `shouldRegisterIndexMetadata()`
  * `shouldUpdateCatalogAfterSchemaChange()`
  * `shouldRefreshMetadataAfterDDL()`
  * `shouldSynchronizeMetadataCache()`
  * `shouldReloadMetadataAfterRestart()`

---

## 2. Database Objects Module
* **Unit Tests**:
  * **DatabaseTest**
    * `shouldOpenDatabase()`
    * `shouldCloseDatabase()`
    * `shouldRenameDatabase()`
    * `shouldSetDatabaseOwner()`
    * `shouldRejectOperationWhenClosed()`
    * `shouldRejectOpenWhenAlreadyOnline()`
    * `shouldRejectCloseWhenAlreadyOffline()`
    * `shouldRejectRenameWhenDatabaseIsOpening()`
    * `shouldRejectRenameWhenDatabaseIsClosing()`
    * `shouldRejectEmptyDatabaseName()`
    * `shouldRejectNullOwner()`
    * `shouldRejectNullDatabaseName()`
    * `shouldRejectBlankDatabaseName()`
    * `shouldRejectDatabaseNameWithSpecialCharacters()`
    * `shouldRejectDatabaseNameExceedingMaxLength()`
    * `shouldRejectReservedDatabaseName()`
    * `shouldInitializeOfflineDatabase()`
    * `shouldMaintainStatusTransition()`
    * `shouldKeepCreatedTimeUnchanged()`
    * `shouldRejectNullDatabaseStatus()`
    * `shouldRejectInvalidStatusTransition()`
    * `shouldCreateSchema()`
    * `shouldDropSchema()`
    * `shouldGetSchemaByName()`
    * `shouldListAllSchemas()`
    * `shouldRejectDuplicateSchemaName()`
    * `shouldRejectUnknownSchema()`
    * `shouldRejectSchemaOperationWhenClosed()`
    * `shouldRejectNullSchemaName()`
    * `shouldRejectBlankSchemaName()`
    * `shouldRejectSchemaNameWithSpecialCharacters()`
    * `shouldRejectSchemaNameExceedingMaxLength()`
    * `shouldRejectReservedSchemaName()`
    * `shouldRejectNullSchemaOwner()`
  * **SchemaTest**
    * `shouldCreateTable()`
    * `shouldDropTable()`
    * `shouldRenameTable()`
    * `shouldCreateView()`
    * `shouldDropView()`
    * `shouldCreateStoredProcedure()`
    * `shouldDropStoredProcedure()`
    * `shouldCreateSequence()`
    * `shouldDropSequence()`
    * `shouldReturnExistingTable()`
  * **TableTest**
    * `shouldInsertRow()`
    * `shouldUpdateRow()`
    * `shouldDeleteRow()`
    * `shouldTruncateTable()`
    * `shouldAnalyzeTable()`
    * `shouldIncreaseRowCount()`
    * `shouldDecreaseRowCount()`
    * `shouldReturnInsertedRow()`
    * `shouldReturnUpdatedRow()`
  * **ColumnTest**
    * `shouldCreateColumn()`
    * `shouldValidateColumnDefinition()`
    * `shouldRejectInvalidDataType()`
    * `shouldRejectInvalidLength()`
    * `shouldAcceptNullableColumn()`
    * `shouldRejectNullForNotNullColumn()`
    * `shouldUpdateColumnMetadata()`
    * `shouldChangeDefaultValue()`
  * **RowTest**
    * `shouldCreateRow()`
    * `shouldUpdateRow()`
    * `shouldMarkRowDeleted()`
    * `shouldReadRow()`
    * `shouldCloneRowVersion()`
    * `shouldUpdateRowVersion()`
    * `shouldStoreTransactionId()`
    * `shouldReturnColumnValue()`
    * `shouldReplaceColumnValue()`
    * `shouldMarkRowDeleted()`
  * **ConstraintTest**
    * `shouldValidatePrimaryKey()`
    * `shouldRejectDuplicatePrimaryKey()`
    * `shouldValidateForeignKey()`
    * `shouldRejectBrokenForeignKey()`
    * `shouldValidateUniqueConstraint()`
    * `shouldRejectDuplicateUniqueValue()`
    * `shouldValidateCheckConstraint()`
    * `shouldRejectInvalidCheckConstraint()`
  * **BTreeIndexTest**
    * `shouldInsertKey()`
    * `shouldSearchKey()`
    * `shouldDeleteKey()`
    * `shouldUpdateKey()`
    * `shouldHandleDuplicateKey()`
    * `shouldRebuildIndex()`
  * **HashIndexTest**   
    * `shouldInsertKey()`
    * `shouldSearchKey()`
    * `shouldDeleteKey()` 
  * **BitmapIndexTest**  
    * `shouldSearchBitmap()`
    * `shouldUpdateBitmap()` 
  * **PartitionTest**
    * `shouldPartitionTable()`
    * `shouldDropPartition()`
    * `shouldLocatePartition()`
    * `shouldSplitPartition()`
    * `shouldMergePartition()`
    * `shouldMoveRowBetweenPartitions()`
  * **ViewTest**
    * `shouldCreateView()`
    * `shouldValidateViewDefinition()`
    * `shouldExecuteViewQuery()`
    * `shouldRefreshViewDefinition()`
    * `shouldRejectInvalidViewDefinition()`
    * `shouldReturnViewResult()`
  * **StoredProcedureTest**
    * `shouldCreateProcedure()`
    * `shouldExecuteProcedure()`
    * `shouldPassProcedureParameters()`
    * `shouldReturnProcedureResult()`
    * `shouldHandleProcedureException()`
  * **TriggerTest**
    * `shouldCreateTrigger()`
    * `shouldFireTrigger()`
    * `shouldExecuteBeforeInsertTrigger()`
    * `shouldExecuteAfterInsertTrigger()`
    * `shouldExecuteBeforeUpdateTrigger()`
    * `shouldExecuteAfterUpdateTrigger()`
    * `shouldExecuteBeforeDeleteTrigger()`
    * `shouldExecuteAfterDeleteTrigger()`
    * `shouldCancelOperationWhenTriggerFails()`
  * **SequenceTest**
    * `shouldCreateSequence()`
    * `shouldGenerateNextValue()`
    * `shouldIncrementSequence()`
    * `shouldResetSequence()`
    * `shouldRespectStartValue()`
    * `shouldReturnCurrentValue()`
    * `shouldRespectIncrementStep()`
* **Integration Tests**:
  * `shouldCreateDatabaseWithSchemaAndTable()`
  * `shouldInsertRowWithConstraints()`
  * `shouldUpdateRowAndRefreshIndex()`
  * `shouldDeleteRowAndUpdateIndex()`
  * `shouldUseIndexForQueryExecution()`
  * `shouldExecuteTriggerDuringInsert()`
  * `shouldExecuteTriggerDuringUpdate()`
  * `shouldExecuteStoredProcedureSuccessfully()`
  * `shouldReadDataFromView()`
  * `shouldGenerateSequenceValueForInsert()`
  * `shouldValidateForeignKeyAcrossTables()`
  * `shouldRefreshViewAfterTableUpdate()`

---

## 3. Database Server Module
* **Unit Tests**:
  * **DatabaseServerTest**
    * `shouldStartDatabaseServer()`
    * `shouldStopDatabaseServer()`
    * `shouldRestartDatabaseServer()`
    * `shouldInitializeManagersOnStartup()`
    * `shouldShutdownManagersGracefully()`
    * `shouldAcceptClientConnection()`
    * `shouldRejectConnectionWhenStopped()`
    * `shouldTrackActiveConnections()`
  * **DatabaseManagerTest**
    * `shouldCreateDatabase()`
    * `shouldDropDatabase()`
    * `shouldRenameDatabase()`


    * `shouldGetDatabaseByName()`
    * `shouldListAllDatabases()`
    * `shouldRejectDuplicateDatabaseName()`
    * `shouldRejectUnknownDatabase()`
    * `shouldIncreaseDatabaseCountAfterCreation()`
    * `shouldDecreaseDatabaseCountAfterDrop()`
    * `shouldAssignUniqueDatabaseId()`
  * **ConfigurationRepositoryTest**
    * `shouldSaveConfigurationToDisk()`
  * **SecurityManagerTest**
    * `shouldInitializeSecurityManager()`
    * `shouldAuthenticateUser()`
    * `shouldRejectInvalidCredential()`
    * `shouldAuthorizeUser()`
    * `shouldGrantPermission()`
    * `shouldRevokePermission()`
  * **SecurityValidatorTest**
    * `shouldRejectInvalidPassword()`
    * `shouldRejectLockedUser()`
    * `shouldRejectDisabledUser()`
    * `shouldCheckRolePermission()`
    * `shouldGrantRolePermission()`
    * `shouldVerifyPermissionInheritance()`

* **MonitoringManagerTest**
    * `shouldCollectServerMetrics()`
    * `shouldCollectMemoryUsage()`
    * `shouldCollectCPUUsage()`
    * `shouldCollectDiskUsage()`
    * `shouldCollectConnectionStatistics()`
    * `shouldReportServerStatus()`
    * `shouldReportDatabaseStatistics()`
    * `shouldResetStatistics()`
* **Integration Tests**:
  * `shouldStartEntireDatabaseServer()`
  * `shouldInitializeAllManagers()`
  * `shouldCreateDatabaseThroughServer()`
  * `shouldOpenExistingDatabase()`
  * `shouldShutdownServerGracefully()`
  * `shouldRestartServerWithoutDataLoss()`
  * `shouldRejectDatabaseOperationWhenServerStopped()`

---

## 4. Query Processing Module
* **Unit Tests**:
  * **SQLParserTest**
    * `shouldParseValidSQL()`
    * `shouldParseSelectStatement()`
    * `shouldParseInsertStatement()`
    * `shouldParseUpdateStatement()`
    * `shouldParseDeleteStatement()`
    * `shouldParseCreateTableStatement()`
    * `shouldParseDropTableStatement()`
    * `shouldTokenizeSQL()`
    * `shouldValidateSQLSyntax()`
    * `shouldRejectInvalidSQLSyntax()`
    * `shouldRejectUnsupportedSQL()`
    * `shouldHandleNestedQuery()`
    * `shouldHandleAlias()`
    * `shouldHandleJoinClause()`
    * `shouldHandleGroupByClause()`
    * `shouldHandleOrderByClause()`
    * `shouldHandleLimitClause()`
  * **LexerTest**
    * `shouldTokenizeSQLStatement()`
    * `shouldIgnoreWhitespace()`
    * `shouldIgnoreComments()`
    * `shouldRecognizeKeywords()`
    * `shouldRecognizeIdentifiers()`
    * `shouldRecognizeOperators()`
    * `shouldRecognizeNumbers()`
    * `shouldRecognizeStringLiteral()`
    * `shouldRecognizeBooleanLiteral()`
    * `shouldRecognizeDelimiter()`
  * **ASTTest**
    * `shouldBuildASTFromSQL()`
    * `shouldStoreASTRootNode()`
    * `shouldBuildSelectNode()`
    * `shouldBuildInsertNode()`
    * `shouldBuildUpdateNode()`
    * `shouldBuildDeleteNode()`
    * `shouldBuildJoinNode()`
    * `shouldBuildWhereNode()`
    * `shouldBuildGroupByNode()`
    * `shouldBuildOrderByNode()`
  * **LogicalPlanTest**
    * `shouldCreateLogicalPlan()`
    * `shouldAddLogicalOperators()`
    * `shouldCreateScanOperator()`
    * `shouldCreateFilterOperator()`
    * `shouldCreateProjectionOperator()`
    * `shouldCreateJoinOperator()`
    * `shouldCreateAggregationOperator()`
    * `shouldCreateSortOperator()`
    * `shouldCreateLimitOperator()`
    * `shouldLinkLogicalOperators()`
  * **QueryOptimizerTest**
    * `shouldOptimizeLogicalPlan()`
    * `shouldEstimateQueryCost()`
    * `shouldChooseJoinOrder()`
    * `shouldGeneratePhysicalPlan()`
    * `shouldPushDownPredicate()`
    * `shouldEliminateUnusedProjection()`
    * `shouldSimplifyExpression()`
    * `shouldChooseIndexScan()`
    * `shouldChooseTableScan()`
    * `shouldOptimizeJoinStrategy()`
    * `shouldOptimizeAggregation()`
    * `shouldReuseStatistics()`
  * **PhysicalPlanTest**
    * `shouldCreatePhysicalPlan()`
    * `shouldStoreExecutionOperators()`
    * `shouldCreateSequentialScan()`
    * `shouldCreateIndexScan()`
    * `shouldCreateNestedLoopJoin()`
    * `shouldCreateHashJoin()`
    * `shouldCreateMergeJoin()`
    * `shouldCreateSortOperator()`
    * `shouldCreateAggregateOperator()`
    * `shouldLinkExecutionTree()`
  * **QueryExecutorTest**
    * `shouldExecutePhysicalPlan()`
    * `shouldExecuteSequentialScan()`
    * `shouldExecuteIndexScan()`
    * `shouldExecuteJoin()`
    * `shouldExecuteAggregation()`
    * `shouldExecuteSort()`
    * `shouldExecuteProjection()`
    * `shouldExecuteFilter()`
    * `shouldFetchResultRows()`
    * `shouldReturnEmptyResult()`
    * `shouldCancelRunningQuery()`
    * `shouldReleaseExecutionResources()`
  * **StatisticsManagerTest**
    * `shouldCollectTableStatistics()`
    * `shouldCollectColumnStatistics()`
    * `shouldUpdateStatistics()`
    * `shouldDeleteStatistics()`
    * `shouldEstimateRowCount()`
    * `shouldEstimateSelectivity()`
    * `shouldEstimateDistinctValues()`
    * `shouldEstimateJoinCost()`
    * `shouldProvideStatisticsForOptimizer()`
    * `shouldPersistStatistics()`
* **Integration Tests**:
  * `shouldParseOptimizeAndExecuteQuery()`
  * `shouldGenerateLogicalAndPhysicalPlan()`
  * `shouldExecuteOptimizedQueryPlan()`
  * `shouldCollectStatisticsDuringExecution()`
  * `shouldRejectInvalidSQLQuery()`
  * `shouldOptimizeJoinQuery()`
  * `shouldExecuteIndexBasedQuery()`
  * `shouldExecuteAggregateQuery()`
  * `shouldExecuteNestedQuery()`
  * `shouldExecuteMultiTableJoin()`
  * `shouldExecuteDDLStatement()`
  * `shouldExecuteDMLStatement()`

---

## 5. Recovery Module
* **Unit Tests**:
  * **WALManagerTest**
    * `shouldAppendLog()`
    * `shouldFlushLog()`
    * `shouldReplayLog()`
    * `shouldOrderLSN()`
    * `shouldAssignIncreasingLSN()`
    * `shouldWriteCommitRecord()`
    * `shouldWriteAbortRecord()`
    * `shouldRecoverLogFile()`
    * `shouldPersistLogRecord()`
    * `shouldVerifyWriteAheadLoggingRule()`
  * **RecoveryManagerTest**
    * `shouldRecover()`
    * `shouldRedo()`
    * `shouldUndo()`
    * `shouldRecoverCheckpoint()`
    * `shouldScanLogRecords()`
    * `shouldRedoCommittedTransactions()`
    * `shouldUndoIncompleteTransactions()`
    * `shouldRestoreDatabaseState()`
    * `shouldSkipCommittedTransaction()`
    * `shouldFinishRecovery()`
* **Integration Tests**:
  * `shouldRecoverAfterCrash()`
  * `shouldCommitTransactionWithWAL()`
  * `shouldRollbackTransactionWithMVCC()`
  * `shouldAcquireAndReleaseLocks()`
  * `shouldRecoverCommittedTransactions()`
  * `shouldRecoverRolledBackTransactions()`
  * `shouldHandleConcurrentTransactions()`
  * `shouldResolveDeadlockAutomatically()`

---

## 6. Replication Module
* **Unit Tests**:
  * **ReplicationManagerTest**
    * `shouldReplicateData()`
    * `shouldSynchronizeReplicas()`
    * `shouldElectLeader()`
    * `shouldHandleReplicaFailure()`
    * `shouldReplicateCommittedTransaction()`
    * `shouldRetryReplication()`
    * `shouldSynchronizeReplicaState()`
    * `shouldSynchronizeMissingLogs()`
    * `shouldRejectReplicationWhenLeaderUnavailable()`
    * `shouldUpdateClusterLeader()`
  * **ClusterNode**
    * `shouldCreateClusterNode()`
    * `shouldConnectToCluster()`
    * `shouldDisconnectFromCluster()`
    * `shouldUpdateNodeStatus()`
    * `shouldHeartbeat()`
    * `shouldRecoverNode()`
    * `shouldDetectNodeFailure()`
    * `shouldSynchronizeNodeState()`
* **Integration Tests**:
  * `shouldReplicateDataToReplica()`
  * `shouldSynchronizeClusterNodes()`
  * `shouldElectLeaderSuccessfully()`
  * `shouldRecoverReplicationAfterNodeFailure()`
  * `shouldReplicateCommittedTransactionAcrossCluster()`
  * `shouldSynchronizeReplicaUsingWAL()`
  * `shouldContinueReplicationAfterLeaderElection()`
  * `shouldRecoverReplicaAfterFailure()`
---

## 7. Security Module
* **Unit Tests**:
  * **SecurityManager**
    * `shouldAuthenticateUser()`
    * `shouldAuthorizeUser()`
    * `shouldGrantPermission()`
    * `shouldRevokePermission()`
    * `shouldRejectInvalidPassword()`
    * `shouldRejectLockedUser()`
    * `shouldRejectDisabledUser()`
    * `shouldCheckRolePermission()`
    * `shouldGrantRolePermission()`
    * `shouldVerifyPermissionInheritance()`
  * **User**
    * `shouldCreateUser()`
    * `shouldUpdatePassword()`
    * `shouldLockUser()`
    * `shouldUnlockUser()`
    * `shouldEnableUser()`
    * `shouldDisableUser()`
    * `shouldValidatePasswordHash()`
  * **Role**
    * `shouldCreateRole()`
    * `shouldAssignPermission()`
    * `shouldRemovePermission()`
    * `shouldDeleteRole()`
    * `shouldRenameRole()`
    * `shouldListPermissions()`
  * **Permission**
    * `shouldCreatePermission()`
    * `shouldComparePermissions()`
    * `shouldValidatePermission()`
    * `shouldStoreAction()`
    * `shouldStoreResource()`
* **Integration Tests**:
  * `shouldAuthenticateAndAuthorizeUser()`
  * `shouldAssignRoleAndGrantPermission()`
  * `shouldRevokePermissionSuccessfully()`
  * `shouldRejectUnauthorizedAccess()`
  * `shouldAuthenticateGrantAndAccess()`
  * `shouldGrantRoleThenAuthorize()`
  * `shouldRevokePermissionAndRejectAccess()`
---

## 8. Storage Engine Module
* **Unit Tests**:
  * **BufferPoolTest**
    * `shouldPinPage()`
    * `shouldUnpinPage()`
    * `shouldFetchExistingPage()`
    * `shouldFlushDirtyPage()`
    * `shouldEvictPage()`
    * `shouldRejectEvictPinnedPage()`
    * `shouldReplaceVictimPage()`
    * `shouldClearDirtyFlagAfterFlush()`
    * `shouldTrackPinCount()`
    * `shouldReturnCachedPage()`
  * **PageManagerTest**
    * `shouldReadPage()`
    * `shouldWritePage()`
    * `shouldAllocatePage()`
    * `shouldDeallocatePage()`
    * `shouldReuseFreedPage()`
    * `shouldAssignUniquePageId()`
    * `shouldMaintainPageMetadata()`
  * **FileManagerTest**
    * `shouldCreateDataFile()`
    * `shouldOpenDataFile()`
    * `shouldCloseDataFile()`
    * `shouldReadDataFile()`
    * `shouldWriteDataFile()`
    * `shouldDeleteDataFile()`
    * `shouldRenameDataFile()`
    * `shouldExpandDataFile()`
    * `shouldShrinkDataFile()`
    * `shouldCheckFileExistence()`
    * `shouldSynchronizeFileToDisk()`
    * `shouldHandleMissingFile()`
  * **PageTest**
    * `shouldCreatePage()`
    * `shouldReadPageData()`
    * `shouldWritePageData()`
    * `shouldUpdatePageHeader()`
    * `shouldMarkPageDirty()`
    * `shouldClearDirtyFlag()`
    * `shouldIncrementPageLSN()`
    * `shouldResetPage()`
* **Integration Tests**:
  * `shouldAllocateAndWritePage()`
  * `shouldReadPageFromDisk()`
  * `shouldFlushDirtyPageToDisk()`
  * `shouldReloadPageIntoBufferPool()`
  * `shouldEvictPageUsingReplacementPolicy()`
  * `shouldPersistPageAcrossRestart()`
  * `shouldSynchronizeBufferPoolAndDisk()`

---

## 9. Transaction & Lock Module
* **Unit Tests**:
  * **TransactionManagerTest**
    * `shouldBeginTransaction()`
    * `shouldCommitTransaction()`
    * `shouldRollbackTransaction()`
    * `shouldRecoverTransactions()`
    * `shouldSuspendTransaction()`
    * `shouldResumeTransaction()`
    * `shouldRegisterActiveTransaction()`
    * `shouldRemoveCommittedTransaction()`
    * `shouldRemoveRolledBackTransaction()`
    * `shouldAssignTransactionId()`
    * `shouldTrackTransactionState()`
    * `shouldRejectDuplicateTransaction()`
  * **TransactionTest**
    * `shouldBegin()`
    * `shouldCommit()`
    * `shouldRollback()`
    * `shouldCreateSavepoint()`
    * `shouldRollbackToSavepoint()`
    * `shouldReleaseSavepoint()`
    * `shouldHandleStateTransition()`
    * `shouldEnforceIsolationLevel()`
    * `shouldRecordStartTime()`
    * `shouldRecordCommitTime()`
    * `shouldRejectCommitAfterRollback()`
    * `shouldRejectRollbackAfterCommit()`
  * **LockManagerTest**
    * `shouldAcquireSharedLock()`
    * `shouldAcquireExclusiveLock()`
    * `shouldReleaseLock()`
    * `shouldUpgradeLock()`
    * `shouldDowngradeLock()`
    * `shouldCheckCompatibility()`
    * `shouldRejectConflictingLock()`
    * `shouldQueueWaitingTransaction()`
    * `shouldWakeWaitingTransaction()`
    * `shouldDetectDeadlock()`
    * `shouldResolveDeadlock()`
    * `shouldReleaseAllLocksOnCommit()`
  * **MVCCManagerTest**
    * `shouldCreateVersion()`
    * `shouldReadVisibleVersion()`
    * `shouldHideUncommittedVersion()`
    * `shouldEnforceVisibilityRule()`
    * `shouldCreateSnapshot()`
    * `shouldReturnSnapshotVersion()`
    * `shouldDeleteObsoleteVersion()`
    * `shouldGarbageCollect()`
    * `shouldRemoveExpiredVersion()`
    * `shouldRejectInvisibleVersion()`
* **Integration Tests**:
  * `shouldCommitTransactionSuccessfully()`
  * `shouldRollbackTransactionSuccessfully()`
  * `shouldRecoverAfterCrash()`
  * `shouldCommitTransactionAndFlushWAL()`
  * `shouldRollbackTransactionAndReleaseLocks()`
  * `shouldRecoverDatabaseUsingWAL()`
  * `shouldRecoverMultipleTransactions()`
  * `shouldRecoverAfterPowerFailure()`
---
