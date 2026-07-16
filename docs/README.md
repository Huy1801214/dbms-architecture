# DBMS System Architecture

```mermaid
flowchart LR
    classDef rootStyle  fill:#1d3557,color:#fff,stroke:#457b9d,font-weight:bold
    classDef modDSA     fill:#2d6a4f,color:#fff,stroke:#1b4332,font-weight:bold
    classDef modTCM     fill:#e76f51,color:#fff,stroke:#c1440e,font-weight:bold
    classDef modBRL     fill:#0077b6,color:#fff,stroke:#023e8a,font-weight:bold
    classDef modAM      fill:#457b9d,color:#fff,stroke:#1d3557,font-weight:bold
    classDef modSUM     fill:#7b2d8b,color:#fff,stroke:#560bad,font-weight:bold
    classDef modDO      fill:#9b2226,color:#fff,stroke:#6a040f,font-weight:bold
    classDef modPS      fill:#c77c08,color:#fff,stroke:#a05f00,font-weight:bold
    classDef modQP      fill:#b5a300,color:#fff,stroke:#8a7a00,font-weight:bold

    classDef subDSA     fill:#d8f3dc,color:#1b4332,stroke:#52b788
    classDef subTCM     fill:#ffddd2,color:#7d2d1a,stroke:#e29578
    classDef subBRL     fill:#caf0f8,color:#023e8a,stroke:#90e0ef
    classDef subAM      fill:#dbe9f5,color:#1d3557,stroke:#457b9d
    classDef subSUM     fill:#e9d8f5,color:#560bad,stroke:#c77dff
    classDef subDO      fill:#ffccd5,color:#5c0a14,stroke:#c9184a
    classDef subPS      fill:#ffe8d6,color:#7c4a03,stroke:#f4a261
    classDef subQP      fill:#fefae0,color:#554400,stroke:#c8aa00

    %% ─────────────────── LEFT SIDE ───────────────────

    %% Data Storage & Access
    DSA1["File Management"]:::subDSA              --> DSA
    DSA2["Page Management"]:::subDSA              --> DSA
    DSA3["Buffer Management"]:::subDSA            --> DSA
    DSA4["Data Access Methods"]:::subDSA          --> DSA
    DSA5["Storage Allocation Management"]:::subDSA --> DSA
    DSA6["Record Management"]:::subDSA            --> DSA
    DSA7["Log Files"]:::subDSA                    --> DSA
    DSA["**Data Storage & Access**"]:::modDSA         --> ROOT

    %% Transaction & Concurrency Management
    TCM1["Transaction Management"]:::subTCM --> TCM
    TCM2["Lock Management"]:::subTCM        --> TCM
    TCM3["Deadlock Management"]:::subTCM    --> TCM
    TCM4["Isolation Management"]:::subTCM   --> TCM
    TCM5["ACID Enforcement"]:::subTCM       --> TCM
    TCM6["Concurrency Control"]:::subTCM    --> TCM
    TCM["**Transaction & Concurrency Management**"]:::modTCM --> ROOT

    %% Backup, Recovery & Logging
    BRL1["Backup Management"]:::subBRL      --> BRL
    BRL2["Restore Management"]:::subBRL     --> BRL
    BRL3["Transaction Logging"]:::subBRL    --> BRL
    BRL4["Recovery Management"]:::subBRL    --> BRL
    BRL5["Checkpoint Management"]:::subBRL  --> BRL
    BRL["Backup, Recovery & Logging"]:::modBRL --> ROOT

    %% Administration & Monitoring
    AM1["System Monitoring"]:::subAM           --> AM
    AM2["Resource Monitoring"]:::subAM         --> AM
    AM3["Configuration Management"]:::subAM    --> AM
    AM4["Database Maintenance"]:::subAM        --> AM
    AM5["Import & Export Management"]:::subAM  --> AM
    AM6["Administrative Tools"]:::subAM        --> AM
    AM["Administration & Monitoring"]:::modAM --> ROOT

    %% ─────────────────── ROOT ───────────────────
    ROOT(["DBMS Analysis"]):::rootStyle

    %% ─────────────────── RIGHT SIDE ───────────────────

    %% Security & User Management
    ROOT --> SUM["Security & User Management"]:::modSUM
    SUM --> SUM1["Authentication Management"]:::subSUM
    SUM --> SUM2["Authorization Management"]:::subSUM
    SUM --> SUM3["User Management"]:::subSUM
    SUM --> SUM4["Role Management"]:::subSUM
    SUM --> SUM5["Data Protection"]:::subSUM
    SUM --> SUM6["Auditing & Compliance"]:::subSUM

    %% Database Objects
    ROOT --> DO["Database Objects"]:::modDO
    DO --> DO1["Schema Management"]:::subDO
    DO --> DO2["Database Management"]:::subDO
    DO --> DO3["Column Management"]:::subDO
    DO --> DO4["Table Management"]:::subDO
    DO --> DO5["View Management"]:::subDO
    DO --> DO6["Programmable Objects Management"]:::subDO
    DO --> DO7["Index Management"]:::subDO
    DO --> DO8["Constraint Management"]:::subDO
    DO --> DO9["Metadata Catalog Management"]:::subDO

    %% Performance & Scalability
    ROOT --> PS["Performance & Scalability"]:::modPS
    PS --> PS1["Memory Management"]:::subPS
    PS --> PS2["Workload Management"]:::subPS
    PS --> PS3["Resource Management"]:::subPS
    PS --> PS4["Data Distribution"]:::subPS
    PS --> PS5["Scalability Management"]:::subPS
    PS --> PS6["Performance Monitoring"]:::subPS
    PS --> PS7["Connection Management"]:::subPS
    PS --> PS8["Parallel Execution"]:::subPS

    %% Query Processing
    ROOT --> QP["**Query Processing**"]:::modQP
    QP --> QP1["Semantic Analyzer"]:::subQP
    QP --> QP2["SQL Parser"]:::subQP
    QP --> QP3["Query Optimizer"]:::subQP
    QP --> QP4["Execution Planner"]:::subQP
    QP --> QP5["Execution Engine"]:::subQP
    QP --> QP6["Result Formatter"]:::subQP
```

---
# DBMS System Architecture Important Modules
![alt text](image-2.png)

---
# DBMS Class Diagram mindmap
![alt text](image.png)

---
# Class Diagram for Core Features
```mermaid
classDiagram
    direction TB

    %% =====================================================
    %% PAGE MANAGEMENT
    %% =====================================================
    class Page {
        +pageId
        +data
        +dirty
    }
    class PageTable {
        +lookupPage()
        +addPage()
        +removePage()
    }
    class PageManager {
        +fetchPage()
        +allocatePage()
        +flushPage()
        +deletePage()
    }
    class IPageAllocator {
        <<interface>>
        +allocate()
        +free()
    }
    class DefaultPageAllocator {
        +allocate()
        +free()
    }
    class IPageIO {
        <<interface>>
        +read()
        +write()
    }
    class DiskPageIO {
        +read()
        +write()
    }

    PageTable *-- Page
    PageManager --> PageTable
    PageManager --> IPageAllocator
    PageManager --> IPageIO
    DefaultPageAllocator ..|> IPageAllocator
    DiskPageIO ..|> IPageIO

    %% =====================================================
    %% BUFFER MANAGEMENT
    %% =====================================================
    class BufferFrame {
        +frameId
        +pinCount
        +dirty
    }
    class BufferPool {
        +capacity
    }
    class ReplacementPolicy {
        <<interface>>
        +victim()
        +recordAccess()
    }
    class LRUReplacer {
        +victim()
        +recordAccess()
    }
    class BufferManager {
        +pinPage()
        +unpinPage()
        +flushPage()
        +flushAll()
    }

    BufferPool *-- BufferFrame
    LRUReplacer ..|> ReplacementPolicy
    BufferManager --> BufferPool
    BufferManager --> ReplacementPolicy
    BufferManager --> PageManager
    BufferFrame --> Page

    %% =====================================================
    %% SQL PARSER
    %% =====================================================
    class SQLParser {
        +parse()
    }
    class Lexer {
        +tokenize()
    }
    class Parser {
        +buildAST()
    }
    class ASTNode {
        <<abstract>>
    }
    class SyntaxError

    SQLParser --> Lexer
    SQLParser --> Parser
    Parser --> ASTNode
    Parser ..> SyntaxError

    %% =====================================================
    %% QUERY PLANNER
    %% =====================================================
    class QueryPlanner {
        +createPlan()
    }
    class ExecutionPlan {
        <<abstract>>
    }
    class SequentialScanPlan
    class FilterPlan

    ExecutionPlan <|-- SequentialScanPlan
    ExecutionPlan <|-- FilterPlan
    QueryPlanner --> ASTNode
    QueryPlanner --> ExecutionPlan

    %% =====================================================
    %% EXECUTION ENGINE
    %% =====================================================
    class ExecutionEngine {
        +execute()
    }
    class ExecutionContext
    class Executor {
        <<abstract>>
        +next()
    }
    class TableScanExecutor {
        +next()
    }
    class FilterExecutor {
        +next()
    }
    class ResultSet

    Executor <|-- TableScanExecutor
    Executor <|-- FilterExecutor
    ExecutionEngine --> ExecutionContext
    ExecutionEngine --> ExecutionPlan
    ExecutionEngine --> Executor
    ExecutionEngine --> ResultSet

    %% =====================================================
    %% TRANSACTION
    %% =====================================================
    class TransactionManager {
        +begin()
        +commit()
        +rollback()
    }
    class Transaction {
        +transactionId
        +state
    }
    class TransactionContext
    class TransactionState {
        <<enumeration>>
        ACTIVE
        COMMITTED
        ABORTED
    }
    class TransactionLog

    Transaction --> TransactionContext
    Transaction --> TransactionState
    TransactionManager --> Transaction
    TransactionManager --> TransactionLog

    %% =====================================================
    %% CONCURRENCY
    %% =====================================================
    class ConcurrencyManager {
        +acquire()
        +release()
    }
    class ILockManager {
        <<interface>>
        +lock()
        +unlock()
    }
    class LockManager {
        +lock()
        +unlock()
    }
    class LockTable
    class Lock
    class LockRequest
    class LockType {
        <<enumeration>>
        SHARED
        EXCLUSIVE
    }

    Lock --> LockType
    LockTable *-- Lock
    LockManager ..|> ILockManager
    ConcurrencyManager --> ILockManager
    LockManager --> LockTable
    LockManager --> LockRequest

    %% =====================================================
    %% CROSS MODULE DEPENDENCIES
    %% =====================================================
    ExecutionEngine --> BufferManager
    ExecutionEngine --> TransactionManager
    TransactionManager --> ConcurrencyManager

    %% =====================================================
    %% STYLING DEFINITIONS (COLOR-CODED BY MODULE)
    %% =====================================================
    %% Page Management (Green)
    style Page fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
    style PageTable fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
    style PageManager fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
    style IPageAllocator fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
    style DefaultPageAllocator fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
    style IPageIO fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
    style DiskPageIO fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132

    %% Buffer Management (Blue)
    style BufferFrame fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
    style BufferPool fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
    style ReplacementPolicy fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
    style LRUReplacer fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
    style BufferManager fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298

    %% Parser & Query Planner (Yellow/Orange)
    style SQLParser fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03
    style Lexer fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03
    style Parser fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03
    style ASTNode fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03
    style SyntaxError fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03
    style QueryPlanner fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03
    style ExecutionPlan fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03
    style SequentialScanPlan fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03
    style FilterPlan fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03

    %% Execution Engine (Purple)
    style ExecutionEngine fill:#f3e5f5,stroke:#6a1b9a,stroke-width:2px,color:#3b0a66
    style ExecutionContext fill:#f3e5f5,stroke:#6a1b9a,stroke-width:2px,color:#3b0a66
    style Executor fill:#f3e5f5,stroke:#6a1b9a,stroke-width:2px,color:#3b0a66
    style TableScanExecutor fill:#f3e5f5,stroke:#6a1b9a,stroke-width:2px,color:#3b0a66
    style FilterExecutor fill:#f3e5f5,stroke:#6a1b9a,stroke-width:2px,color:#3b0a66
    style ResultSet fill:#f3e5f5,stroke:#6a1b9a,stroke-width:2px,color:#3b0a66

    %% Transaction & Concurrency (Red/Coral)
    style TransactionManager fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
    style Transaction fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
    style TransactionContext fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
    style TransactionState fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
    style TransactionLog fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
    style ConcurrencyManager fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
    style ILockManager fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
    style LockManager fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
    style LockTable fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
    style Lock fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
    style LockRequest fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
    style LockType fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
```

--- 
# Unit Test Mindmap for Data Storage & Access
```mermaid
flowchart LR

    %% ─────────────────── LEFT SIDE: PAGE MANAGEMENT ───────────────────
    %% Page
    PageTest1["shouldUpdatePageContent"] --> Page
    PageTest2["shouldMarkPageDirty"] --> Page
    Page["Page"] --> A

    %% Page Table
    PageTableTest1["shouldInsertAndLookupPage"] --> PageTable
    PageTableTest2["shouldRemovePage"] --> PageTable
    PageTableTest3["shouldReplaceExistingPage"] --> PageTable
    PageTable["PageTable"] --> A

    %% Default Page Allocator
    AllocatorTest1["shouldAllocateUniquePage"] --> DefaultPageAllocator
    AllocatorTest2["shouldFreeAllocatedPage"] --> DefaultPageAllocator
    DefaultPageAllocator["DefaultPageAllocator"] --> A

    %% Disk Page IO
    PageIOTest1["shouldWriteAndReadPage"] --> DiskPageIO
    PageIOTest2["shouldOverwriteExistingPage"] --> DiskPageIO
    PageIOTest3["shouldThrowExceptionWhenReadingMissingPage"] --> DiskPageIO
    DiskPageIO["DiskPageIO"] --> A

    %% Page Manager
    PageManagerTest1["shouldAllocateNewPage"] --> PageManager
    PageManagerTest2["shouldFetchExistingPage"] --> PageManager
    PageManagerTest3["shouldFlushDirtyPage"] --> PageManager
    PageManagerTest4["shouldDeletePage"] --> PageManager
    PageManager["PageManager"] --> A

    %% ─────────────────── CENTRAL ROOT ───────────────────
    A(["Data Storage & Access Unit Tests"])

    %% ─────────────────── RIGHT SIDE: BUFFER MANAGEMENT ───────────────────
    %% Buffer Frame
    A --> BufferFrame["BufferFrame"]
    BufferFrame --> BufferFrameTest1["shouldPinAndUnpinFrame"]
    BufferFrame --> BufferFrameTest2["shouldMarkFrameDirty"]

    %% Buffer Pool
    A --> BufferPool["BufferPool"]
    BufferPool --> BufferPoolTest1["shouldAddAndRetrieveFrame"]
    BufferPool --> BufferPoolTest2["shouldRespectConfiguredCapacity"]

    %% LRU Replacer
    A --> LRUReplacer["LRUReplacer"]
    LRUReplacer --> LruTest1["shouldSelectLeastRecentlyUsedFrame"]
    LRUReplacer --> LruTest2["shouldUpdateAccessHistory"]

    %% Buffer Manager
    A --> BufferManager["BufferManager"]
    BufferManager --> BufferManagerTest1["shouldPinExistingPage"]
    BufferManager --> BufferManagerTest2["shouldLoadPageOnCacheMiss"]
    BufferManager --> BufferManagerTest3["shouldReturnCachedPageOnCacheHit"]
    BufferManager --> BufferManagerTest4["shouldEvictPageWhenBufferIsFull"]
    BufferManager --> BufferManagerTest5["shouldFlushDirtyPagesToDisk"]
    BufferManager --> BufferManagerTest6["shouldUnpinPageSuccessfully"]

    %% ─────────────────── STYLING DEFINITIONS ───────────────────
    classDef rootStyle fill:#1d3557,color:#fff,stroke:#457b9d,stroke-width:2px,font-weight:bold
    classDef modStyle fill:#2d6a4f,color:#fff,stroke:#1b4332,stroke-width:1.5px,font-weight:bold
    classDef testStyle fill:#f8f9fa,color:#212529,stroke:#dee2e6,stroke-width:1px

    class A rootStyle;
    class Page,PageTable,DefaultPageAllocator,DiskPageIO,PageManager,BufferFrame,BufferPool,LRUReplacer,BufferManager modStyle;
    class PageTest1,PageTest2,PageTableTest1,PageTableTest2,PageTableTest3,AllocatorTest1,AllocatorTest2,PageIOTest1,PageIOTest2,PageIOTest3,PageManagerTest1,PageManagerTest2,PageManagerTest3,PageManagerTest4,BufferFrameTest1,BufferFrameTest2,BufferPoolTest1,BufferPoolTest2,LruTest1,LruTest2,BufferManagerTest1,BufferManagerTest2,BufferManagerTest3,BufferManagerTest4,BufferManagerTest5,BufferManagerTest6 testStyle;
```

--- 
# Unit Test Mindmap for Query Processing
```mermaid
flowchart LR

    %% ─────────────────── LEFT SIDE: PARSING & PLANNING ───────────────────
    %% Lexer
    LexerTest1["shouldTokenizeValidSQL"] --> Lexer
    LexerTest2["shouldIgnoreWhitespace"] --> Lexer
    LexerTest3["shouldRejectInvalidCharacter"] --> Lexer
    Lexer["Lexer"] --> A

    %% Parser
    ParserTest1["shouldBuildASTFromValidSQL"] --> Parser
    ParserTest2["shouldBuildCorrectASTStructure"] --> Parser
    ParserTest3["shouldThrowSyntaxErrorForInvalidSQL"] --> Parser
    Parser["Parser"] --> A

    %% SQL Parser
    SQLParserTest1["shouldParseValidSQL"] --> SQLParser
    SQLParserTest2["shouldDelegateLexingAndParsing"] --> SQLParser
    SQLParser["SQLParser"] --> A

    %% Query Planner
    PlannerTest1["shouldGenerateExecutionPlan"] --> QueryPlanner
    PlannerTest2["shouldGenerateSequentialScanPlan"] --> QueryPlanner
    PlannerTest3["shouldGenerateFilterPlan"] --> QueryPlanner
    PlannerTest4["shouldRejectUnsupportedAST"] --> QueryPlanner
    QueryPlanner["QueryPlanner"] --> A

    %% Execution Plan
    ExecutionPlanTest1["shouldBuildExecutionPlan"] --> ExecutionPlan
    ExecutionPlan["ExecutionPlan"] --> A

    %% ─────────────────── CENTRAL ROOT ───────────────────
    A(["Query Processing Unit Tests"])

    %% ─────────────────── RIGHT SIDE: EXECUTION ───────────────────
    %% Sequential Scan Plan
    A --> SequentialScanPlan["SequentialScanPlan"]
    SequentialScanPlan --> SeqScanPlanTest1["shouldCreateSequentialScanPlan"]

    %% Filter Plan
    A --> FilterPlan["FilterPlan"]
    FilterPlan --> FilterPlanTest1["shouldCreateFilterPlan"]

    %% Execution Context
    A --> ExecutionContext["ExecutionContext"]
    ExecutionContext --> ContextTest1["shouldProvideExecutionDependencies"]

    %% Table Scan Executor
    A --> TableScanExecutor["TableScanExecutor"]
    TableScanExecutor --> TableScanTest1["shouldScanEntireTable"]
    TableScanExecutor --> TableScanTest2["shouldReturnEndOfTableWhenFinished"]

    %% Filter Executor
    A --> FilterExecutor["FilterExecutor"]
    FilterExecutor --> FilterExecutorTest1["shouldReturnMatchingRows"]
    FilterExecutor --> FilterExecutorTest2["shouldSkipNonMatchingRows"]

    %% Execution Engine
    A --> ExecutionEngine["ExecutionEngine"]
    ExecutionEngine --> EngineTest1["shouldExecuteExecutionPlan"]
    ExecutionEngine --> EngineTest2["shouldExecuteSequentialScan"]
    ExecutionEngine --> EngineTest3["shouldExecuteFilterPlan"]
    ExecutionEngine --> EngineTest4["shouldRejectUnsupportedExecutionPlan"]

    %% Result Set
    A --> ResultSet["ResultSet"]
    ResultSet --> ResultSetTest1["shouldStoreAndRetrieveRows"]
    ResultSet --> ResultSetTest2["shouldIterateResultSet"]

    %% ─────────────────── STYLING DEFINITIONS ───────────────────
    classDef rootStyle fill:#1d3557,color:#fff,stroke:#457b9d,stroke-width:2px,font-weight:bold
    classDef modStyle fill:#2d6a4f,color:#fff,stroke:#1b4332,stroke-width:1.5px,font-weight:bold
    classDef testStyle fill:#f8f9fa,color:#212529,stroke:#dee2e6,stroke-width:1px

    class A rootStyle;
    class Lexer,Parser,SQLParser,QueryPlanner,ExecutionPlan,SequentialScanPlan,FilterPlan,ExecutionContext,TableScanExecutor,FilterExecutor,ExecutionEngine,ResultSet modStyle;
    class LexerTest1,LexerTest2,LexerTest3,ParserTest1,ParserTest2,ParserTest3,SQLParserTest1,SQLParserTest2,PlannerTest1,PlannerTest2,PlannerTest3,PlannerTest4,ExecutionPlanTest1,SeqScanPlanTest1,FilterPlanTest1,ContextTest1,TableScanTest1,TableScanTest2,FilterExecutorTest1,FilterExecutorTest2,EngineTest1,EngineTest2,EngineTest3,EngineTest4,ResultSetTest1,ResultSetTest2 testStyle;
```

--- 
# Unit Test Mindmap for Transaction & Concurrency Management
```mermaid
flowchart LR

    %% ─────────────────── LEFT SIDE: TRANSACTION MANAGEMENT ───────────────────
    %% Transaction
    TransactionTest1["shouldChangeTransactionState"] --> Transaction
    TransactionTest2["shouldAttachTransactionContext"] --> Transaction
    Transaction["Transaction"] --> A

    %% Transaction Context
    ContextTest1["shouldStoreTransactionMetadata"] --> TransactionContext
    TransactionContext["TransactionContext"] --> A

    %% Transaction Log
    LogTest1["shouldAppendTransactionLog"] --> TransactionLog
    LogTest2["shouldRetrieveTransactionLog"] --> TransactionLog
    TransactionLog["TransactionLog"] --> A

    %% Transaction Manager
    TMTest1["shouldBeginTransaction"] --> TransactionManager
    TMTest2["shouldCommitTransaction"] --> TransactionManager
    TMTest3["shouldRollbackTransaction"] --> TransactionManager
    TMTest4["shouldWriteTransactionLog"] --> TransactionManager
    TMTest5["shouldCoordinateConcurrencyManager"] --> TransactionManager
    TransactionManager["TransactionManager"] --> A

    %% ─────────────────── CENTRAL ROOT ───────────────────
    A(["Transaction & Concurrency Management Unit Tests"])

    %% ─────────────────── RIGHT SIDE: CONCURRENCY & LOCK MANAGEMENT ───────────────────
    %% Lock
    A --> Lock["Lock"]
    Lock --> LockTest1["shouldCreateSharedLock"]
    Lock --> LockTest2["shouldCreateExclusiveLock"]

    %% Lock Request
    A --> LockRequest["LockRequest"]
    LockRequest --> LockRequestTest1["shouldCreateLockRequest"]

    %% Lock Table
    A --> LockTable["LockTable"]
    LockTable --> LockTableTest1["shouldRegisterLock"]
    LockTable --> LockTableTest2["shouldLookupLock"]
    LockTable --> LockTableTest3["shouldRemoveLock"]

    %% Lock Manager
    A --> LockManager["LockManager"]
    LockManager --> LockManagerTest1["shouldAcquireSharedLock"]
    LockManager --> LockManagerTest2["shouldAcquireExclusiveLock"]
    LockManager --> LockManagerTest3["shouldReleaseLock"]
    LockManager --> LockManagerTest4["shouldRejectConflictingLock"]

    %% Concurrency Manager
    A --> ConcurrencyManager["ConcurrencyManager"]
    ConcurrencyManager --> CMTest1["shouldAcquireLock"]
    ConcurrencyManager --> CMTest2["shouldReleaseLock"]
    ConcurrencyManager --> CMTest3["shouldDelegateToLockManager"]

    %% ─────────────────── STYLING DEFINITIONS ───────────────────
    classDef rootStyle fill:#1d3557,color:#fff,stroke:#457b9d,stroke-width:2px,font-weight:bold
    classDef modStyle fill:#2d6a4f,color:#fff,stroke:#1b4332,stroke-width:1.5px,font-weight:bold
    classDef testStyle fill:#f8f9fa,color:#212529,stroke:#dee2e6,stroke-width:1px

    class A rootStyle;
    class Transaction,TransactionContext,TransactionLog,TransactionManager,Lock,LockRequest,LockTable,LockManager,ConcurrencyManager modStyle;
    class TransactionTest1,TransactionTest2,ContextTest1,LogTest1,LogTest2,TMTest1,TMTest2,TMTest3,TMTest4,TMTest5,LockTest1,LockTest2,LockRequestTest1,LockTableTest1,LockTableTest2,LockTableTest3,LockManagerTest1,LockManagerTest2,LockManagerTest3,LockManagerTest4,CMTest1,CMTest2,CMTest3 testStyle;
```

# Mindmap for Integration Test
```mermaid
flowchart LR

    %% ─────────────────── LEFT SIDE: DATA STORAGE & QUERY PROCESSING ───────────────────
    %% Data Storage
    StorageTest1["shouldAllocateFetchAndFlushPage"] --> Storage
    StorageTest2["shouldLoadPageFromDiskOnCacheMiss"] --> Storage
    StorageTest3["shouldReturnCachedPageOnCacheHit"] --> Storage
    StorageTest4["shouldEvictLeastRecentlyUsedPage"] --> Storage
    StorageTest5["shouldPersistDirtyPageToDisk"] --> Storage
    Storage["Data Storage & Access"] --> A

    %% Query Processing
    QueryTest1["shouldParsePlanAndExecuteSelectQuery"] --> Query
    QueryTest2["shouldExecuteSequentialScanQuery"] --> Query
    QueryTest3["shouldExecuteFilterQuery"] --> Query
    QueryTest4["shouldReturnExpectedResultSet"] --> Query
    Query["Query Processing"] --> A

    %% ─────────────────── CENTRAL ROOT ───────────────────
    A(["DBMS Integration Tests"])

    %% ─────────────────── RIGHT SIDE: TRANSACTION, CONCURRENCY & END-TO-END ───────────────────
    %% Transaction Management
    A --> Transaction["Transaction Management"]
    Transaction --> TransactionTest1["shouldCommitTransactionSuccessfully"]
    Transaction --> TransactionTest2["shouldRollbackTransactionWhenExecutionFails"]
    Transaction --> TransactionTest3["shouldWriteTransactionLogAfterCommit"]

    %% Concurrency Control
    A --> Concurrency["Concurrency Control"]
    Concurrency --> ConcurrencyTest1["shouldAcquireLockBeforeExecutingQuery"]
    Concurrency --> ConcurrencyTest2["shouldReleaseLockAfterCommit"]
    Concurrency --> ConcurrencyTest3["shouldBlockConflictingTransactions"]

    %% End-to-End Workflow
    A --> EndToEnd["End-to-End Workflow"]
    EndToEnd --> E2ETest1["shouldExecuteCompleteSelectWorkflow"]
    EndToEnd --> E2ETest2["shouldExecuteCompleteTransactionWorkflow"]
    EndToEnd --> E2ETest3["shouldExecuteConcurrentTransactionsSafely"]

    %% ─────────────────── STYLING DEFINITIONS ───────────────────
    classDef rootStyle fill:#1d3557,color:#fff,stroke:#457b9d,stroke-width:2px,font-weight:bold
    classDef modStyle fill:#2d6a4f,color:#fff,stroke:#1b4332,stroke-width:1.5px,font-weight:bold
    classDef testStyle fill:#f8f9fa,color:#212529,stroke:#dee2e6,stroke-width:1px

    class A rootStyle;
    class Storage,Query,Transaction,Concurrency,EndToEnd modStyle;
    class StorageTest1,StorageTest2,StorageTest3,StorageTest4,StorageTest5,QueryTest1,QueryTest2,QueryTest3,QueryTest4,TransactionTest1,TransactionTest2,TransactionTest3,ConcurrencyTest1,ConcurrencyTest2,ConcurrencyTest3,E2ETest1,E2ETest2,E2ETest3 testStyle;
```