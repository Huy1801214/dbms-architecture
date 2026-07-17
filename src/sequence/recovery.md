## Recovery Unit Test 

## WAL Manager

### 1. shouldAppendLogRecord()
```mermaid 
sequenceDiagram
    autonumber
    participant Test as WALManagerTest
    participant WAL as WALManager
    participant Log as LogRecord

    Test->>WAL: append(logRecord)

    WAL->>Log: validate()

    Log-->>WAL: valid

    WAL->>WAL: assignLSN()

    WAL->>WAL: appendToBuffer()

    WAL-->>Test: AppendSuccess=true
```

### 2. shouldFlushLogToDisk()
```mermaid
sequenceDiagram
    autonumber
    participant Test as WALManagerTest
    participant WAL as WALManager

    Test->>WAL: flush()

    WAL->>WAL: writeBufferedLogs()

    WAL->>WAL: updateCurrentLSN()

    WAL-->>Test: FlushSuccess=true
```

### 3. shouldReplayLogRecords()
```mermaid 
sequenceDiagram
    autonumber
    participant Test as WALManagerTest
    participant WAL as WALManager
    participant Log as LogRecord

    Test->>WAL: replay()

    loop Each LogRecord
        WAL->>Log: apply()
        Log-->>WAL: applied
    end

    WAL-->>Test: ReplayCompleted
```

### 4. shouldMaintainIncreasingLSN()
```mermaid
sequenceDiagram
    autonumber
    participant Test as WALManagerTest
    participant WAL as WALManager

    Test->>WAL: append(logRecord)

    WAL->>WAL: generateNextLSN()

    WAL->>WAL: comparePreviousLSN()

    WAL-->>Test: LSNIncreasing=true
```
## Recovery Manager

### 5. shouldRecoverDatabase()
```mermaid 
sequenceDiagram
    autonumber
    participant Test as RecoveryManagerTest
    participant Recovery as RecoveryManager
    participant WAL as WALManager

    Test->>Recovery: recover()

    Recovery->>WAL: replay()

    WAL-->>Recovery: replay completed

    Recovery-->>Test: RecoverySuccess=true
```

### 6. shouldRedoCommittedTransactions()
```mermaid
sequenceDiagram
    autonumber
    participant Test as RecoveryManagerTest
    participant Recovery as RecoveryManager
    participant WAL as WALManager

    Test->>Recovery: recover()

    Recovery->>WAL: replayCommittedLogs()

    WAL-->>Recovery: redo completed

    Recovery-->>Test: RedoCompleted=true
```

### 7. shouldUndoUncommittedTransactions()
```mermaid
sequenceDiagram
    autonumber
    participant Test as RecoveryManagerTest
    participant Recovery as RecoveryManager
    participant WAL as WALManager

    Test->>Recovery: recover()

    Recovery->>WAL: identifyUncommittedLogs()

    WAL-->>Recovery: undo list

    Recovery->>WAL: undo()

    WAL-->>Recovery: undo completed

    Recovery-->>Test: UndoCompleted=true
```

### 8. shouldRestoreConsistentState()
```mermaid
sequenceDiagram
    autonumber
    participant Test as RecoveryManagerTest
    participant Recovery as RecoveryManager
    participant WAL as WALManager

    Test->>Recovery: recover()

    Recovery->>WAL: replay()

    WAL-->>Recovery: recovery completed

    Recovery->>Recovery: verifyConsistency()

    Recovery-->>Test: DatabaseConsistent=true
```

## Log Record

### 9. shouldCreateLogRecord()
```mermaid
sequenceDiagram
    autonumber
    participant Test as LogRecordTest
    participant Log as LogRecord

    Test->>Log: create()

    Log->>Log: initializeFields()

    Log-->>Test: LogRecordCreated
```

### 10. shouldStoreTransactionId()
```mermaid
sequenceDiagram
    autonumber
    participant Test as LogRecordTest
    participant Log as LogRecord

    Test->>Log: setTransactionId(txId)

    Log->>Log: storeTransactionId()

    Log-->>Test: TransactionIdStored
```

### 11. shouldStoreLogSequenceNumber()
```mermaid 
sequenceDiagram
    autonumber
    participant Test as LogRecordTest
    participant Log as LogRecord

    Test->>Log: setLSN(lsn)

    Log->>Log: storeLSN()

    Log-->>Test: LSNStored
```

### 12. shouldStoreOperationType()
```mermaid 
sequenceDiagram
    autonumber
    participant Test as LogRecordTest
    participant Log as LogRecord

    Test->>Log: setOperationType(operation)

    Log->>Log: storeOperation()

    Log-->>Test: OperationStored
```

# Recovery Integration Test

### 13. shouldRecoverDatabaseFromWAL()
```mermaid
sequenceDiagram
    autonumber
    participant Test as RecoveryIntegrationTest
    participant Recovery as RecoveryManager
    participant WAL as WALManager
    participant Log as LogRecord

    Test->>Recovery: recover()

    Recovery->>WAL: replay()

    loop Each LogRecord
        WAL->>Log: apply()
        Log-->>WAL: applied
    end

    WAL-->>Recovery: recovery finished

    Recovery-->>Test: RecoverySuccess=true
```

### 14. shouldReplayLogRecordsInOrder()
```mermaid
sequenceDiagram
    autonumber
    participant Test as RecoveryIntegrationTest
    participant Recovery as RecoveryManager
    participant WAL as WALManager
    participant Log as LogRecord

    Test->>Recovery: recover()

    Recovery->>WAL: replay()

    loop Ordered by LSN
        WAL->>Log: apply()
        Log-->>WAL: applied
    end

    Recovery-->>Test: ReplayOrdered=true
```

### 15. shouldRestoreConsistentDatabaseState()
```mermaid
sequenceDiagram
    autonumber
    participant Test as RecoveryIntegrationTest
    participant Recovery as RecoveryManager
    participant WAL as WALManager

    Test->>Recovery: recover()

    Recovery->>WAL: replay()

    WAL-->>Recovery: replay completed

    Recovery->>Recovery: verifyConsistency()

    Recovery-->>Test: ConsistentStateRestored=true
```

### 16. shouldRecoverAfterSimulatedCrash()
```mermaid
sequenceDiagram
    autonumber
    participant Test as RecoveryIntegrationTest
    participant Recovery as RecoveryManager
    participant WAL as WALManager
    participant Log as LogRecord

    Test->>Recovery: recover()

    Recovery->>WAL: replay()

    loop Recovery Log Records
        WAL->>Log: apply()
        Log-->>WAL: applied
    end

    WAL-->>Recovery: database restored

    Recovery-->>Test: CrashRecoverySuccess=true
```
