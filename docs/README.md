# DBMS System Architecture

```mermaid
flowchart LR
    %% =====================================================
    %% NODE DECLARATIONS & STYLING (Declared exactly once)
    %% =====================================================
    DBMS((DBMS)):::rootStyle
    
    %% Left-side Branches
    Server["Database Server"]:::branchAdmin
    Security["Security"]:::branchAdmin
    Replication["Replication"]:::branchAdmin
    Recovery["Recovery"]:::branchTx
    
    DatabaseServer["DatabaseServer"]:::leafStyle
    DatabaseManager["DatabaseManager"]:::leafStyle
    ConfigurationManager["ConfigurationManager"]:::leafStyle
    SecurityManager["SecurityManager"]:::leafStyle
    MonitoringManager["MonitoringManager"]:::leafStyle
    
    User["User"]:::leafStyle
    Role["Role"]:::leafStyle
    Permission["Permission"]:::leafStyle
    ClusterNode["Cluster Node"]:::leafStyle
    LogRecord["Log Record"]:::leafStyle

    %% Right-side Branches
    Database["Database"]:::branchCatalog
    Storage["Storage Engine"]:::branchStorage
    Query["Query Processing"]:::branchQuery
    Transaction["Transaction"]:::branchTx
    Metadata["Metadata"]:::branchCatalog
    
    Schema["Schema"]:::leafStyle
    Table["Table"]:::leafStyle
    Column["Column"]:::leafStyle
    Row["Row"]:::leafStyle
    Index["Index"]:::leafStyle
    
    BufferPool["Buffer Pool"]:::leafStyle
    PageManager["Page Manager"]:::leafStyle
    FileManager["File Manager"]:::leafStyle
    
    SQLParser["SQL Parser"]:::leafStyle
    Lexer["Lexer"]:::leafStyle
    AST["AST"]:::leafStyle
    
    QueryOptimizer["Query Optimizer"]:::leafStyle
    LogicalPlan["Logical Plan"]:::leafStyle
    PhysicalPlan["Physical Plan"]:::leafStyle
    StatisticsManager["Statistics Manager"]:::leafStyle
    
    QueryExecutor["Query Executor"]:::leafStyle
    RuntimeContext["Runtime Context"]:::leafStyle
    
    TransactionObject["Transaction Object"]:::leafStyle
    TransactionManager["Transaction Manager"]:::leafStyle
    LockManager["Lock Manager"]:::leafStyle
    MVCCManager["MVCC Manager"]:::leafStyle
    WALManager["WAL Manager"]:::leafStyle
    
    CatalogManager["Catalog Manager"]:::leafStyle

    %% =====================================================
    %% CONNECTIONS (Simple Node IDs only)
    %% =====================================================
    
    %% Left Side Connections (pointing left-to-right into DBMS)
    Server --> DBMS
    Security --> DBMS
    Replication --> DBMS
    Recovery --> DBMS

    DatabaseServer --> Server
    DatabaseManager --> Server
    ConfigurationManager --> Server
    SecurityManager --> Server
    MonitoringManager --> Server

    User --> Security
    Role --> Security
    Permission --> Security

    ClusterNode --> Replication
    LogRecord --> Recovery

    %% Right Side Connections (pointing right)
    DBMS --> Database
    DBMS --> Storage
    DBMS --> Query
    DBMS --> Transaction
    DBMS --> Metadata

    Database --> Schema
    Database --> Table
    Database --> Column
    Database --> Row
    Database --> Index

    Storage --> BufferPool
    Storage --> PageManager
    Storage --> FileManager

    Query --> SQLParser
    SQLParser --> Lexer
    SQLParser --> AST

    Query --> QueryOptimizer
    QueryOptimizer --> LogicalPlan
    QueryOptimizer --> PhysicalPlan
    QueryOptimizer --> StatisticsManager

    Query --> QueryExecutor
    QueryExecutor --> RuntimeContext
    QueryExecutor --> PhysicalPlan

    Transaction --> TransactionObject
    Transaction --> TransactionManager
    Transaction --> LockManager
    Transaction --> MVCCManager
    Transaction --> WALManager

    Metadata --> CatalogManager

    %% =====================================================
    %% STYLING DEFINITIONS (HSL Colors)
    %% =====================================================
    classDef rootStyle fill:#1d3557,stroke:#457b9d,stroke-width:3px,color:#fff,font-weight:bold,font-size:16px;
    classDef branchAdmin fill:#e1f5fe,stroke:#0288d1,stroke-width:2px,color:#01579b,font-weight:bold;
    classDef branchQuery fill:#fff3e0,stroke:#f57c00,stroke-width:2px,color:#e65100,font-weight:bold;
    classDef branchCatalog fill:#e8f5e9,stroke:#388e3c,stroke-width:2px,color:#1b5e20,font-weight:bold;
    classDef branchStorage fill:#e0f2f1,stroke:#00695c,stroke-width:2px,color:#004d40,font-weight:bold;
    classDef branchTx fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029,font-weight:bold;
    classDef leafStyle fill:#ffffff,stroke:#b0bec5,stroke-width:1px,color:#37474f;
```

---
# Overview Class Diagram

```mermaid
classDiagram
direction TB

class DatabaseServer{
    +serverId
    +version
    +status
    +start()
    +stop()
    +restart()
}

class DatabaseManager{
    +createDatabase()
    +dropDatabase()
    +getDatabase()
    +listDatabases()
}

class Database{
    +databaseId
    +name
    +owner
    +open()
    +close()
}

class Schema{
    +schemaId
    +name
    +createTable()
    +dropTable()
}

class Table{
    +tableId
    +name
    +insert()
    +update()
    +delete()
}

class Column{
    +columnId
    +name
    +dataType
    +nullable
}

class Row{
    +rowId
    +values
    +version
}

class DataType{
    <<enumeration>>
}

class Constraint{
    <<abstract>>
    +validate()
}

class PrimaryKey{
    +columns
}

class ForeignKey{
    +referenceTable
}

class UniqueConstraint

class CheckConstraint

class Index{
    <<abstract>>
    +search()
    +insertKey()
    +deleteKey()
}

class BTreeIndex

class HashIndex

class BitmapIndex

class Partition{
    +partitionKey
}

class View{
    +queryDefinition
}

class StoredProcedure{
    +execute()
}

class Trigger{
    +fire()
}

class Sequence{
    +nextValue()
}

class Transaction{
    +transactionId
    +begin()
    +commit()
    +rollback()
}

class TransactionManager{
    +beginTransaction()
    +commit()
    +rollback()
}

class LockManager{
    +acquireLock()
    +releaseLock()
}

class MVCCManager{
    +createVersion()
    +garbageCollect()
}

class BufferPool{
    +pinPage()
    +flushPage()
}

class Page{
    +pageId
}

class StorageEngine{
    +readPage()
    +writePage()
}

class FileManager{
    +read()
    +write()
}

class WALManager{
    +appendLog()
    +flush()
}

class RecoveryManager{
    +recover()
}

class CatalogManager{
    +registerTable()
    +findTable()
}

class SQLParser{
    +parse()
}

class Lexer{
    +tokenize()
}

class AST{
    +root
}

class QueryOptimizer{
    +optimize()
}

class LogicalPlan{
    +operators
}

class PhysicalPlan{
    +operators
}

class QueryExecutor{
    +execute()
}

class StatisticsManager{
    +collect()
}

class SecurityManager{
    +authenticate()
    +authorize()
}

class User

class Role

class Permission

DatabaseServer --> DatabaseManager
DatabaseServer --> TransactionManager
DatabaseServer --> StorageEngine
DatabaseServer --> CatalogManager
DatabaseServer --> SecurityManager

DatabaseManager --> Database
Database --> Schema
Schema --> Table
Schema --> View
Schema --> StoredProcedure
Schema --> Sequence

Table --> Column
Table --> Row
Table --> Index
Table --> Constraint
Table --> Partition
Table --> Trigger

Constraint <|-- PrimaryKey
Constraint <|-- ForeignKey
Constraint <|-- UniqueConstraint
Constraint <|-- CheckConstraint

Index <|-- BTreeIndex
Index <|-- HashIndex
Index <|-- BitmapIndex

Column --> DataType
ForeignKey --> Table

TransactionManager --> Transaction
TransactionManager --> LockManager
TransactionManager --> MVCCManager
TransactionManager --> WALManager

StorageEngine --> BufferPool
StorageEngine --> FileManager
BufferPool --> Page

RecoveryManager --> WALManager

CatalogManager --> Table
CatalogManager --> Index
CatalogManager --> Schema

SQLParser --> Lexer
SQLParser --> AST
AST --> LogicalPlan
QueryOptimizer --> LogicalPlan
QueryOptimizer --> PhysicalPlan
QueryExecutor --> PhysicalPlan
QueryExecutor --> Transaction

StatisticsManager --> Table
QueryOptimizer --> StatisticsManager

SecurityManager --> User
SecurityManager --> Role
Role --> Permission

%% =====================================================
%% STYLING DEFINITIONS (Color-coded by Domain Group)
%% =====================================================
%% Server & Security (Blue)
style DatabaseServer fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style DatabaseManager fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style SecurityManager fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style User fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style Role fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style Permission fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298

%% Catalog & Database Objects (Green)
style Database fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Schema fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Table fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Column fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Row fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style DataType fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Constraint fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style PrimaryKey fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style ForeignKey fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style UniqueConstraint fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style CheckConstraint fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Index fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style BTreeIndex fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style HashIndex fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style BitmapIndex fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Partition fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style View fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style StoredProcedure fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Trigger fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Sequence fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style CatalogManager fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132

%% Query Engine (Yellow/Orange)
style SQLParser fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03
style Lexer fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03
style AST fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03
style QueryOptimizer fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03
style LogicalPlan fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03
style PhysicalPlan fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03
style QueryExecutor fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03
style StatisticsManager fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03

%% Storage Engine (Teal)
style StorageEngine fill:#e0f2f1,stroke:#00695c,stroke-width:2px,color:#004d40
style BufferPool fill:#e0f2f1,stroke:#00695c,stroke-width:2px,color:#004d40
style Page fill:#e0f2f1,stroke:#00695c,stroke-width:2px,color:#004d40
style FileManager fill:#e0f2f1,stroke:#00695c,stroke-width:2px,color:#004d40

%% Transaction & Recovery (Red/Coral)
style Transaction fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
style TransactionManager fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
style LockManager fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
style MVCCManager fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
style WALManager fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
style RecoveryManager fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
```

--- 

# Database Server Module
```mermaid
classDiagram
direction TB

class DatabaseServer{
    +serverId
    +version
    +status
    +configuration
    +startTime

    +start()
    +stop()
    +restart()
}

class DatabaseManager{
    +createDatabase()
    +dropDatabase()
    +getDatabase()
    +listDatabases()
}

class ConfigurationManager

class SecurityManager{
    +authenticate()
    +authorize()
    +grantPermission()
    +revokePermission()
}

class MonitoringManager

class Database{
    +databaseId
    +name
    +owner
    +status
    +createdAt

    +open()
    +close()
}

DatabaseServer --> DatabaseManager
DatabaseServer --> ConfigurationManager
DatabaseServer --> SecurityManager
DatabaseServer --> MonitoringManager

DatabaseManager --> Database

%% =====================================================
%% STYLING DEFINITIONS
%% =====================================================
style DatabaseServer fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style DatabaseManager fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style ConfigurationRepository fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style SecurityManager fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style MonitoringManager fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style Database fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
```
--- 

# Database Server Test
```mermaid
flowchart LR

    %% =====================================================
    %% Root
    %% =====================================================
    ROOT((Database Server Module Test)):::rootStyle

    %% =====================================================
    %% Left-side Branches (Flow: Leaf --> Branch --> ROOT)
    %% =====================================================
    INT_TEST["IntegrationTest"]:::branchTx
    INT_TEST --> ROOT

    CONFIGURATIONREPOSITORYTEST["ConfigurationRepositoryTest"]:::branchAdmin
    CONFIGURATIONREPOSITORYTEST --> ROOT

    SECURITYVALIDATORTEST["SecurityValidatorTest"]:::branchAdmin
    SECURITYVALIDATORTEST --> ROOT

    %% =====================================================
    %% Right-side Branches (Flow: ROOT --> Branch --> Leaf)
    %% =====================================================
    DATABASESERVERTEST["DatabaseServerTest"]:::branchAdmin
    ROOT --> DATABASESERVERTEST

    DATABASEMANAGERTEST["DatabaseManagerTest"]:::branchAdmin
    ROOT --> DATABASEMANAGERTEST

    SECURITYMANAGERTEST["SecurityManagerTest"]:::branchAdmin
    ROOT --> SECURITYMANAGERTEST

    MONITORINGMANAGERTEST["MonitoringManagerTest"]:::branchAdmin
    ROOT --> MONITORINGMANAGERTEST

    %% =====================================================
    %% LEFT-SIDE TESTS (Leaf --> Branch)
    %% =====================================================

    %% Integration Test methods
    it1["shouldStartEntireDatabaseServer()"]:::leafStyle
    it1 --> INT_TEST
    it2["shouldInitializeAllManagers()"]:::leafStyle
    it2 --> INT_TEST
    it3["shouldCreateDatabaseThroughServer()"]:::leafStyle
    it3 --> INT_TEST
    it4["shouldOpenExistingDatabase()"]:::leafStyle
    it4 --> INT_TEST
    it5["shouldShutdownServerGracefully()"]:::leafStyle
    it5 --> INT_TEST
    it6["shouldRestartServerWithoutDataLoss()"]:::leafStyle
    it6 --> INT_TEST
    it7["shouldRejectDatabaseOperationWhenServerStopped()"]:::leafStyle
    it7 --> INT_TEST

    %% ConfigurationRepositoryTest methods
    configurationrepositorytest_1["shouldSaveConfigurationToDisk()"]:::leafStyle
    configurationrepositorytest_1 --> CONFIGURATIONREPOSITORYTEST

    %% SecurityValidatorTest methods
    securityvalidatortest_1["shouldRejectInvalidPassword()"]:::leafStyle
    securityvalidatortest_1 --> SECURITYVALIDATORTEST
    securityvalidatortest_2["shouldRejectLockedUser()"]:::leafStyle
    securityvalidatortest_2 --> SECURITYVALIDATORTEST
    securityvalidatortest_3["shouldRejectDisabledUser()"]:::leafStyle
    securityvalidatortest_3 --> SECURITYVALIDATORTEST
    securityvalidatortest_4["shouldCheckRolePermission()"]:::leafStyle
    securityvalidatortest_4 --> SECURITYVALIDATORTEST
    securityvalidatortest_5["shouldGrantRolePermission()"]:::leafStyle
    securityvalidatortest_5 --> SECURITYVALIDATORTEST
    securityvalidatortest_6["shouldVerifyPermissionInheritance()"]:::leafStyle
    securityvalidatortest_6 --> SECURITYVALIDATORTEST

    %% =====================================================
    %% RIGHT-SIDE TESTS (Branch --> Leaf)
    %% =====================================================

    %% DatabaseServerTest methods
    databaseservertest_1["shouldStartDatabaseServer()"]:::leafStyle
    DATABASESERVERTEST --> databaseservertest_1
    databaseservertest_2["shouldStopDatabaseServer()"]:::leafStyle
    DATABASESERVERTEST --> databaseservertest_2
    databaseservertest_3["shouldRestartDatabaseServer()"]:::leafStyle
    DATABASESERVERTEST --> databaseservertest_3
    databaseservertest_4["shouldInitializeManagersOnStartup()"]:::leafStyle
    DATABASESERVERTEST --> databaseservertest_4
    databaseservertest_5["shouldShutdownManagersGracefully()"]:::leafStyle
    DATABASESERVERTEST --> databaseservertest_5
    databaseservertest_6["shouldAcceptClientConnection()"]:::leafStyle
    DATABASESERVERTEST --> databaseservertest_6
    databaseservertest_7["shouldRejectConnectionWhenStopped()"]:::leafStyle
    DATABASESERVERTEST --> databaseservertest_7
    databaseservertest_8["shouldTrackActiveConnections()"]:::leafStyle
    DATABASESERVERTEST --> databaseservertest_8

    %% DatabaseManagerTest methods
    databasemanagertest_1["shouldCreateDatabase()"]:::leafStyle
    DATABASEMANAGERTEST --> databasemanagertest_1
    databasemanagertest_2["shouldDropDatabase()"]:::leafStyle
    DATABASEMANAGERTEST --> databasemanagertest_2
    databasemanagertest_3["shouldRenameDatabase()"]:::leafStyle
    DATABASEMANAGERTEST --> databasemanagertest_3
    databasemanagertest_4["shouldGetDatabaseByName()"]:::leafStyle
    DATABASEMANAGERTEST --> databasemanagertest_4
    databasemanagertest_5["shouldListAllDatabases()"]:::leafStyle
    DATABASEMANAGERTEST --> databasemanagertest_5
    databasemanagertest_6["shouldRejectDuplicateDatabaseName()"]:::leafStyle
    DATABASEMANAGERTEST --> databasemanagertest_6
    databasemanagertest_7["shouldRejectUnknownDatabase()"]:::leafStyle
    DATABASEMANAGERTEST --> databasemanagertest_7

    %% SecurityManagerTest methods
    securitymanagertest_1["shouldInitializeSecurityManager()"]:::leafStyle
    SECURITYMANAGERTEST --> securitymanagertest_1
    securitymanagertest_2["shouldAuthenticateUser()"]:::leafStyle
    SECURITYMANAGERTEST --> securitymanagertest_2
    securitymanagertest_3["shouldRejectInvalidCredential()"]:::leafStyle
    SECURITYMANAGERTEST --> securitymanagertest_3
    securitymanagertest_4["shouldAuthorizeUser()"]:::leafStyle
    SECURITYMANAGERTEST --> securitymanagertest_4
    securitymanagertest_5["shouldGrantPermission()"]:::leafStyle
    SECURITYMANAGERTEST --> securitymanagertest_5
    securitymanagertest_6["shouldRevokePermission()"]:::leafStyle
    SECURITYMANAGERTEST --> securitymanagertest_6

    %% MonitoringManagerTest methods
    monitoringmanagertest_1["shouldCollectServerMetrics()"]:::leafStyle
    MONITORINGMANAGERTEST --> monitoringmanagertest_1
    monitoringmanagertest_2["shouldCollectMemoryUsage()"]:::leafStyle
    MONITORINGMANAGERTEST --> monitoringmanagertest_2
    monitoringmanagertest_3["shouldCollectCPUUsage()"]:::leafStyle
    MONITORINGMANAGERTEST --> monitoringmanagertest_3
    monitoringmanagertest_4["shouldCollectDiskUsage()"]:::leafStyle
    MONITORINGMANAGERTEST --> monitoringmanagertest_4
    monitoringmanagertest_5["shouldCollectConnectionStatistics()"]:::leafStyle
    MONITORINGMANAGERTEST --> monitoringmanagertest_5
    monitoringmanagertest_6["shouldReportServerStatus()"]:::leafStyle
    MONITORINGMANAGERTEST --> monitoringmanagertest_6
    monitoringmanagertest_7["shouldReportDatabaseStatistics()"]:::leafStyle
    MONITORINGMANAGERTEST --> monitoringmanagertest_7
    monitoringmanagertest_8["shouldResetStatistics()"]:::leafStyle
    MONITORINGMANAGERTEST --> monitoringmanagertest_8

    %% =====================================================
    %% Styling
    %% =====================================================
    classDef rootStyle fill:#1d3557,stroke:#457b9d,stroke-width:3px,color:#fff,font-weight:bold,font-size:16px;
    classDef branchAdmin fill:#e1f5fe,stroke:#0288d1,stroke-width:2px,color:#01579b,font-weight:bold;
    classDef branchCatalog fill:#e8f5e9,stroke:#388e3c,stroke-width:2px,color:#1b5e20,font-weight:bold;
    classDef branchStorage fill:#e0f2f1,stroke:#00695c,stroke-width:2px,color:#004d40,font-weight:bold;
    classDef branchQuery fill:#fff3e0,stroke:#f57c00,stroke-width:2px,color:#e65100,font-weight:bold;
    classDef branchTx fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029,font-weight:bold;
    classDef leafStyle fill:#ffffff,stroke:#b0bec5,stroke-width:1px,color:#37474f;
```
---

# Database Objects Module

```mermaid
classDiagram
direction TB

class Database{
    +databaseId
    +name
    +owner
    +status
}

class Schema{
    +schemaId
    +name
    +owner

    +createTable()
    +dropTable()
    +createView()
    +createProcedure()
}

class Table{
    +tableId
    +name
    +engine
    +rowCount

    +insert()
    +update()
    +delete()
    +truncate()
    +analyze()
}

class Column{
    +columnId
    +name
    +dataType
    +nullable
    +defaultValue
    +length
    +precision
    +scale
}

class Row{
    +rowId
    +values
    +transactionId
    +version
}

class DataType{
    <<enumeration>>
}

class DatabaseStatus{
    <<enumeration>>
    ONLINE
    OFFLINE
    OPENING
    CLOSING
}

class Constraint{
    <<abstract>>
    +validate()
}

class PrimaryKey
class ForeignKey
class UniqueConstraint
class CheckConstraint

class Index{
    <<abstract>>
    +search()
    +insertKey()
    +deleteKey()
    +rebuild()
}

class BTreeIndex
class HashIndex
class BitmapIndex

class Partition
class View
class StoredProcedure
class Trigger
class Sequence

Database --> Schema
Database --> DatabaseStatus

Schema --> Table
Schema --> View
Schema --> StoredProcedure
Schema --> Sequence

Table --> Column
Table --> Row
Table --> Index
Table --> Constraint
Table --> Partition
Table --> Trigger

Column --> DataType

Constraint <|-- PrimaryKey
Constraint <|-- ForeignKey
Constraint <|-- UniqueConstraint
Constraint <|-- CheckConstraint

Index <|-- BTreeIndex
Index <|-- HashIndex
Index <|-- BitmapIndex

ForeignKey --> Table

%% =====================================================
%% STYLING DEFINITIONS
%% =====================================================
style Database fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Schema fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Table fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Column fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Row fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style DataType fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style DatabaseStatus fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Constraint fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style PrimaryKey fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style ForeignKey fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style UniqueConstraint fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style CheckConstraint fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Index fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style BTreeIndex fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style HashIndex fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style BitmapIndex fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Partition fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style View fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style StoredProcedure fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Trigger fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Sequence fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
```
---

# Database Objects Test
```mermaid
flowchart LR

    %% =====================================================
    %% Root
    %% =====================================================
    ROOT((Database Objects Module Test)):::rootStyle

    %% =====================================================
    %% Integration Tests
    %% =====================================================
    INT_TEST["IntegrationTest"]:::branchTx

    it1["shouldCreateDatabaseWithSchemaAndTable()"]:::leafStyle
    it1 --> INT_TEST
    it2["shouldInsertRowWithConstraints()"]:::leafStyle
    it2 --> INT_TEST
    it3["shouldUpdateRowAndRefreshIndex()"]:::leafStyle
    it3 --> INT_TEST
    it4["shouldDeleteRowAndUpdateIndex()"]:::leafStyle
    it4 --> INT_TEST
    it5["shouldUseIndexForQueryExecution()"]:::leafStyle
    it5 --> INT_TEST
    it6["shouldExecuteTriggerDuringInsert()"]:::leafStyle
    it6 --> INT_TEST
    it7["shouldExecuteTriggerDuringUpdate()"]:::leafStyle
    it7 --> INT_TEST
    it8["shouldExecuteStoredProcedureSuccessfully()"]:::leafStyle
    it8 --> INT_TEST
    it9["shouldReadDataFromView()"]:::leafStyle
    it9 --> INT_TEST
    it10["shouldGenerateSequenceValueForInsert()"]:::leafStyle
    it10 --> INT_TEST
    it11["shouldValidateForeignKeyAcrossTables()"]:::leafStyle
    it11 --> INT_TEST
    it12["shouldRefreshViewAfterTableUpdate()"]:::leafStyle
    it12 --> INT_TEST
    INT_TEST --> ROOT

    %% =====================================================
    %% Unit Tests
    %% =====================================================
    DATABASETEST["DatabaseTest"]:::branchCatalog
    ROOT --> DATABASETEST
    SCHEMATEST["SchemaTest"]:::branchCatalog
    ROOT --> SCHEMATEST
    TABLETEST["TableTest"]:::branchCatalog
    ROOT --> TABLETEST
    COLUMNTEST["ColumnTest"]:::branchCatalog
    ROOT --> COLUMNTEST
    ROWTEST["RowTest"]:::branchCatalog
    ROOT --> ROWTEST
    CONSTRAINTTEST["ConstraintTest"]:::branchCatalog
    ROOT --> CONSTRAINTTEST
    BTREEINDEXTEST["BTreeIndexTest"]:::branchCatalog
    ROOT --> BTREEINDEXTEST
    HASHINDEXTEST["HashIndexTest"]:::branchCatalog
    ROOT --> HASHINDEXTEST
    BITMAPINDEXTEST["BitmapIndexTest"]:::branchCatalog
    ROOT --> BITMAPINDEXTEST
    PARTITIONTEST["PartitionTest"]:::branchCatalog
    ROOT --> PARTITIONTEST
    VIEWTEST["ViewTest"]:::branchCatalog
    ROOT --> VIEWTEST
    STOREDPROCEDURETEST["StoredProcedureTest"]:::branchCatalog
    ROOT --> STOREDPROCEDURETEST
    TRIGGERTEST["TriggerTest"]:::branchCatalog
    ROOT --> TRIGGERTEST
    SEQUENCETEST["SequenceTest"]:::branchCatalog
    ROOT --> SEQUENCETEST

    %% DatabaseTest methods
    databasetest_1["shouldOpenDatabase()"]:::leafStyle
    DATABASETEST --> databasetest_1
    databasetest_2["shouldCloseDatabase()"]:::leafStyle
    DATABASETEST --> databasetest_2
    databasetest_3["shouldRenameDatabase()"]:::leafStyle
    DATABASETEST --> databasetest_3
    databasetest_4["shouldSetDatabaseOwner()"]:::leafStyle
    DATABASETEST --> databasetest_4
    databasetest_5["shouldRejectOperationWhenClosed()"]:::leafStyle
    DATABASETEST --> databasetest_5

    %% SchemaTest methods
    schematest_1["shouldCreateTable()"]:::leafStyle
    SCHEMATEST --> schematest_1
    schematest_2["shouldDropTable()"]:::leafStyle
    SCHEMATEST --> schematest_2
    schematest_3["shouldRenameTable()"]:::leafStyle
    SCHEMATEST --> schematest_3
    schematest_4["shouldCreateView()"]:::leafStyle
    SCHEMATEST --> schematest_4
    schematest_5["shouldDropView()"]:::leafStyle
    SCHEMATEST --> schematest_5
    schematest_6["shouldCreateStoredProcedure()"]:::leafStyle
    SCHEMATEST --> schematest_6
    schematest_7["shouldDropStoredProcedure()"]:::leafStyle
    SCHEMATEST --> schematest_7
    schematest_8["shouldCreateSequence()"]:::leafStyle
    SCHEMATEST --> schematest_8
    schematest_9["shouldDropSequence()"]:::leafStyle
    SCHEMATEST --> schematest_9
    schematest_10["shouldReturnExistingTable()"]:::leafStyle
    SCHEMATEST --> schematest_10

    %% TableTest methods
    tabletest_1["shouldInsertRow()"]:::leafStyle
    TABLETEST --> tabletest_1
    tabletest_2["shouldUpdateRow()"]:::leafStyle
    TABLETEST --> tabletest_2
    tabletest_3["shouldDeleteRow()"]:::leafStyle
    TABLETEST --> tabletest_3
    tabletest_4["shouldTruncateTable()"]:::leafStyle
    TABLETEST --> tabletest_4
    tabletest_5["shouldAnalyzeTable()"]:::leafStyle
    TABLETEST --> tabletest_5
    tabletest_6["shouldIncreaseRowCount()"]:::leafStyle
    TABLETEST --> tabletest_6
    tabletest_7["shouldDecreaseRowCount()"]:::leafStyle
    TABLETEST --> tabletest_7
    tabletest_8["shouldReturnInsertedRow()"]:::leafStyle
    TABLETEST --> tabletest_8
    tabletest_9["shouldReturnUpdatedRow()"]:::leafStyle
    TABLETEST --> tabletest_9

    %% ColumnTest methods
    columntest_1["shouldCreateColumn()"]:::leafStyle
    COLUMNTEST --> columntest_1
    columntest_2["shouldValidateColumnDefinition()"]:::leafStyle
    COLUMNTEST --> columntest_2
    columntest_3["shouldRejectInvalidDataType()"]:::leafStyle
    COLUMNTEST --> columntest_3
    columntest_4["shouldRejectInvalidLength()"]:::leafStyle
    COLUMNTEST --> columntest_4
    columntest_5["shouldAcceptNullableColumn()"]:::leafStyle
    COLUMNTEST --> columntest_5
    columntest_6["shouldRejectNullForNotNullColumn()"]:::leafStyle
    COLUMNTEST --> columntest_6
    columntest_7["shouldUpdateColumnMetadata()"]:::leafStyle
    COLUMNTEST --> columntest_7
    columntest_8["shouldChangeDefaultValue()"]:::leafStyle
    COLUMNTEST --> columntest_8

    %% RowTest methods
    rowtest_1["shouldCreateRow()"]:::leafStyle
    ROWTEST --> rowtest_1
    rowtest_2["shouldUpdateRow()"]:::leafStyle
    ROWTEST --> rowtest_2
    rowtest_3["shouldMarkRowDeleted()"]:::leafStyle
    ROWTEST --> rowtest_3
    rowtest_4["shouldReadRow()"]:::leafStyle
    ROWTEST --> rowtest_4
    rowtest_5["shouldCloneRowVersion()"]:::leafStyle
    ROWTEST --> rowtest_5
    rowtest_6["shouldUpdateRowVersion()"]:::leafStyle
    ROWTEST --> rowtest_6
    rowtest_7["shouldStoreTransactionId()"]:::leafStyle
    ROWTEST --> rowtest_7
    rowtest_8["shouldReturnColumnValue()"]:::leafStyle
    ROWTEST --> rowtest_8
    rowtest_9["shouldReplaceColumnValue()"]:::leafStyle
    ROWTEST --> rowtest_9
    rowtest_10["shouldMarkRowDeleted()"]:::leafStyle
    ROWTEST --> rowtest_10

    %% ConstraintTest methods
    constrainttest_1["shouldValidatePrimaryKey()"]:::leafStyle
    CONSTRAINTTEST --> constrainttest_1
    constrainttest_2["shouldRejectDuplicatePrimaryKey()"]:::leafStyle
    CONSTRAINTTEST --> constrainttest_2
    constrainttest_3["shouldValidateForeignKey()"]:::leafStyle
    CONSTRAINTTEST --> constrainttest_3
    constrainttest_4["shouldRejectBrokenForeignKey()"]:::leafStyle
    CONSTRAINTTEST --> constrainttest_4
    constrainttest_5["shouldValidateUniqueConstraint()"]:::leafStyle
    CONSTRAINTTEST --> constrainttest_5
    constrainttest_6["shouldRejectDuplicateUniqueValue()"]:::leafStyle
    CONSTRAINTTEST --> constrainttest_6
    constrainttest_7["shouldValidateCheckConstraint()"]:::leafStyle
    CONSTRAINTTEST --> constrainttest_7
    constrainttest_8["shouldRejectInvalidCheckConstraint()"]:::leafStyle
    CONSTRAINTTEST --> constrainttest_8

    %% BTreeIndexTest methods
    btreeindextest_1["shouldInsertKey()"]:::leafStyle
    BTREEINDEXTEST --> btreeindextest_1
    btreeindextest_2["shouldSearchKey()"]:::leafStyle
    BTREEINDEXTEST --> btreeindextest_2
    btreeindextest_3["shouldDeleteKey()"]:::leafStyle
    BTREEINDEXTEST --> btreeindextest_3
    btreeindextest_4["shouldUpdateKey()"]:::leafStyle
    BTREEINDEXTEST --> btreeindextest_4
    btreeindextest_5["shouldHandleDuplicateKey()"]:::leafStyle
    BTREEINDEXTEST --> btreeindextest_5
    btreeindextest_6["shouldRebuildIndex()"]:::leafStyle
    BTREEINDEXTEST --> btreeindextest_6

    %% HashIndexTest methods
    hashindextest_1["shouldInsertKey()"]:::leafStyle
    HASHINDEXTEST --> hashindextest_1
    hashindextest_2["shouldSearchKey()"]:::leafStyle
    HASHINDEXTEST --> hashindextest_2
    hashindextest_3["shouldDeleteKey()"]:::leafStyle
    HASHINDEXTEST --> hashindextest_3

    %% BitmapIndexTest methods
    bitmapindextest_1["shouldSearchBitmap()"]:::leafStyle
    BITMAPINDEXTEST --> bitmapindextest_1
    bitmapindextest_2["shouldUpdateBitmap()"]:::leafStyle
    BITMAPINDEXTEST --> bitmapindextest_2

    %% PartitionTest methods
    partitiontest_1["shouldPartitionTable()"]:::leafStyle
    PARTITIONTEST --> partitiontest_1
    partitiontest_2["shouldDropPartition()"]:::leafStyle
    PARTITIONTEST --> partitiontest_2
    partitiontest_3["shouldLocatePartition()"]:::leafStyle
    PARTITIONTEST --> partitiontest_3
    partitiontest_4["shouldSplitPartition()"]:::leafStyle
    PARTITIONTEST --> partitiontest_4
    partitiontest_5["shouldMergePartition()"]:::leafStyle
    PARTITIONTEST --> partitiontest_5
    partitiontest_6["shouldMoveRowBetweenPartitions()"]:::leafStyle
    PARTITIONTEST --> partitiontest_6

    %% ViewTest methods
    viewtest_1["shouldCreateView()"]:::leafStyle
    VIEWTEST --> viewtest_1
    viewtest_2["shouldValidateViewDefinition()"]:::leafStyle
    VIEWTEST --> viewtest_2
    viewtest_3["shouldExecuteViewQuery()"]:::leafStyle
    VIEWTEST --> viewtest_3
    viewtest_4["shouldRefreshViewDefinition()"]:::leafStyle
    VIEWTEST --> viewtest_4
    viewtest_5["shouldRejectInvalidViewDefinition()"]:::leafStyle
    VIEWTEST --> viewtest_5
    viewtest_6["shouldReturnViewResult()"]:::leafStyle
    VIEWTEST --> viewtest_6

    %% StoredProcedureTest methods
    storedproceduretest_1["shouldCreateProcedure()"]:::leafStyle
    STOREDPROCEDURETEST --> storedproceduretest_1
    storedproceduretest_2["shouldExecuteProcedure()"]:::leafStyle
    STOREDPROCEDURETEST --> storedproceduretest_2
    storedproceduretest_3["shouldPassProcedureParameters()"]:::leafStyle
    STOREDPROCEDURETEST --> storedproceduretest_3
    storedproceduretest_4["shouldReturnProcedureResult()"]:::leafStyle
    STOREDPROCEDURETEST --> storedproceduretest_4
    storedproceduretest_5["shouldHandleProcedureException()"]:::leafStyle
    STOREDPROCEDURETEST --> storedproceduretest_5

    %% TriggerTest methods
    triggertest_1["shouldCreateTrigger()"]:::leafStyle
    TRIGGERTEST --> triggertest_1
    triggertest_2["shouldFireTrigger()"]:::leafStyle
    TRIGGERTEST --> triggertest_2
    triggertest_3["shouldExecuteBeforeInsertTrigger()"]:::leafStyle
    TRIGGERTEST --> triggertest_3
    triggertest_4["shouldExecuteAfterInsertTrigger()"]:::leafStyle
    TRIGGERTEST --> triggertest_4
    triggertest_5["shouldExecuteBeforeUpdateTrigger()"]:::leafStyle
    TRIGGERTEST --> triggertest_5
    triggertest_6["shouldExecuteAfterUpdateTrigger()"]:::leafStyle
    TRIGGERTEST --> triggertest_6
    triggertest_7["shouldExecuteBeforeDeleteTrigger()"]:::leafStyle
    TRIGGERTEST --> triggertest_7
    triggertest_8["shouldExecuteAfterDeleteTrigger()"]:::leafStyle
    TRIGGERTEST --> triggertest_8
    triggertest_9["shouldCancelOperationWhenTriggerFails()"]:::leafStyle
    TRIGGERTEST --> triggertest_9

    %% SequenceTest methods
    sequencetest_1["shouldCreateSequence()"]:::leafStyle
    SEQUENCETEST --> sequencetest_1
    sequencetest_2["shouldGenerateNextValue()"]:::leafStyle
    SEQUENCETEST --> sequencetest_2
    sequencetest_3["shouldIncrementSequence()"]:::leafStyle
    SEQUENCETEST --> sequencetest_3
    sequencetest_4["shouldResetSequence()"]:::leafStyle
    SEQUENCETEST --> sequencetest_4
    sequencetest_5["shouldRespectStartValue()"]:::leafStyle
    SEQUENCETEST --> sequencetest_5
    sequencetest_6["shouldReturnCurrentValue()"]:::leafStyle
    SEQUENCETEST --> sequencetest_6
    sequencetest_7["shouldRespectIncrementStep()"]:::leafStyle
    SEQUENCETEST --> sequencetest_7

    %% =====================================================
    %% Styling
    %% =====================================================
    classDef rootStyle fill:#1d3557,stroke:#457b9d,stroke-width:3px,color:#fff,font-weight:bold,font-size:16px;
    classDef branchAdmin fill:#e1f5fe,stroke:#0288d1,stroke-width:2px,color:#01579b,font-weight:bold;
    classDef branchCatalog fill:#e8f5e9,stroke:#388e3c,stroke-width:2px,color:#1b5e20,font-weight:bold;
    classDef branchStorage fill:#e0f2f1,stroke:#00695c,stroke-width:2px,color:#004d40,font-weight:bold;
    classDef branchQuery fill:#fff3e0,stroke:#f57c00,stroke-width:2px,color:#e65100,font-weight:bold;
    classDef branchTx fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029,font-weight:bold;
    classDef leafStyle fill:#ffffff,stroke:#b0bec5,stroke-width:1px,color:#37474f;
```
---
# Storage Engine Module

```mermaid
classDiagram
direction TB

class StorageEngine{
    +pageSize
    +dataFiles
    +freeSpaceMap

    +readPage()
    +writePage()
    +allocatePage()
    +checkpoint()
}

class BufferPool{
    +pages
    +capacity
    +replacementPolicy

    +pinPage()
    +unpinPage()
    +flushPage()
    +evictPage()
}

class Page{
    +pageId
}

class FileManager{
    +read()
    +write()
}

StorageEngine --> BufferPool
StorageEngine --> FileManager

BufferPool --> Page

%% =====================================================
%% STYLING DEFINITIONS
%% =====================================================
style StorageEngine fill:#e0f2f1,stroke:#00695c,stroke-width:2px,color:#004d40
style BufferPool fill:#e0f2f1,stroke:#00695c,stroke-width:2px,color:#004d40
style Page fill:#e0f2f1,stroke:#00695c,stroke-width:2px,color:#004d40
style FileManager fill:#e0f2f1,stroke:#00695c,stroke-width:2px,color:#004d40
```
--- 

# Storage Engine Test
```mermaid
flowchart LR

    %% =====================================================
    %% Root
    %% =====================================================
    ROOT((Storage Engine Module Test)):::rootStyle

    %% =====================================================
    %% Integration Tests
    %% =====================================================
    INT_TEST["IntegrationTest"]:::branchTx

    it1["shouldAllocateAndWritePage()"]:::leafStyle
    it1 --> INT_TEST
    it2["shouldReadPageFromDisk()"]:::leafStyle
    it2 --> INT_TEST
    it3["shouldFlushDirtyPageToDisk()"]:::leafStyle
    it3 --> INT_TEST
    it4["shouldReloadPageIntoBufferPool()"]:::leafStyle
    it4 --> INT_TEST
    it5["shouldEvictPageUsingReplacementPolicy()"]:::leafStyle
    it5 --> INT_TEST
    it6["shouldPersistPageAcrossRestart()"]:::leafStyle
    it6 --> INT_TEST
    it7["shouldSynchronizeBufferPoolAndDisk()"]:::leafStyle
    it7 --> INT_TEST
    INT_TEST --> ROOT

    %% =====================================================
    %% Unit Tests
    %% =====================================================
    BUFFERPOOLTEST["BufferPoolTest"]:::branchStorage
    ROOT --> BUFFERPOOLTEST
    PAGEMANAGERTEST["PageManagerTest"]:::branchStorage
    ROOT --> PAGEMANAGERTEST
    FILEMANAGERTEST["FileManagerTest"]:::branchStorage
    ROOT --> FILEMANAGERTEST
    PAGETEST["PageTest"]:::branchStorage
    ROOT --> PAGETEST

    %% BufferPoolTest methods
    bufferpooltest_1["shouldPinPage()"]:::leafStyle
    BUFFERPOOLTEST --> bufferpooltest_1
    bufferpooltest_2["shouldUnpinPage()"]:::leafStyle
    BUFFERPOOLTEST --> bufferpooltest_2
    bufferpooltest_3["shouldFetchExistingPage()"]:::leafStyle
    BUFFERPOOLTEST --> bufferpooltest_3
    bufferpooltest_4["shouldFlushDirtyPage()"]:::leafStyle
    BUFFERPOOLTEST --> bufferpooltest_4
    bufferpooltest_5["shouldEvictPage()"]:::leafStyle
    BUFFERPOOLTEST --> bufferpooltest_5
    bufferpooltest_6["shouldRejectEvictPinnedPage()"]:::leafStyle
    BUFFERPOOLTEST --> bufferpooltest_6
    bufferpooltest_7["shouldReplaceVictimPage()"]:::leafStyle
    BUFFERPOOLTEST --> bufferpooltest_7
    bufferpooltest_8["shouldClearDirtyFlagAfterFlush()"]:::leafStyle
    BUFFERPOOLTEST --> bufferpooltest_8
    bufferpooltest_9["shouldTrackPinCount()"]:::leafStyle
    BUFFERPOOLTEST --> bufferpooltest_9
    bufferpooltest_10["shouldReturnCachedPage()"]:::leafStyle
    BUFFERPOOLTEST --> bufferpooltest_10

    %% PageManagerTest methods
    pagemanagertest_1["shouldReadPage()"]:::leafStyle
    PAGEMANAGERTEST --> pagemanagertest_1
    pagemanagertest_2["shouldWritePage()"]:::leafStyle
    PAGEMANAGERTEST --> pagemanagertest_2
    pagemanagertest_3["shouldAllocatePage()"]:::leafStyle
    PAGEMANAGERTEST --> pagemanagertest_3
    pagemanagertest_4["shouldDeallocatePage()"]:::leafStyle
    PAGEMANAGERTEST --> pagemanagertest_4
    pagemanagertest_5["shouldReuseFreedPage()"]:::leafStyle
    PAGEMANAGERTEST --> pagemanagertest_5
    pagemanagertest_6["shouldAssignUniquePageId()"]:::leafStyle
    PAGEMANAGERTEST --> pagemanagertest_6
    pagemanagertest_7["shouldMaintainPageMetadata()"]:::leafStyle
    PAGEMANAGERTEST --> pagemanagertest_7

    %% FileManagerTest methods
    filemanagertest_1["shouldCreateDataFile()"]:::leafStyle
    FILEMANAGERTEST --> filemanagertest_1
    filemanagertest_2["shouldOpenDataFile()"]:::leafStyle
    FILEMANAGERTEST --> filemanagertest_2
    filemanagertest_3["shouldCloseDataFile()"]:::leafStyle
    FILEMANAGERTEST --> filemanagertest_3
    filemanagertest_4["shouldReadDataFile()"]:::leafStyle
    FILEMANAGERTEST --> filemanagertest_4
    filemanagertest_5["shouldWriteDataFile()"]:::leafStyle
    FILEMANAGERTEST --> filemanagertest_5
    filemanagertest_6["shouldDeleteDataFile()"]:::leafStyle
    FILEMANAGERTEST --> filemanagertest_6
    filemanagertest_7["shouldRenameDataFile()"]:::leafStyle
    FILEMANAGERTEST --> filemanagertest_7
    filemanagertest_8["shouldExpandDataFile()"]:::leafStyle
    FILEMANAGERTEST --> filemanagertest_8
    filemanagertest_9["shouldShrinkDataFile()"]:::leafStyle
    FILEMANAGERTEST --> filemanagertest_9
    filemanagertest_10["shouldCheckFileExistence()"]:::leafStyle
    FILEMANAGERTEST --> filemanagertest_10
    filemanagertest_11["shouldSynchronizeFileToDisk()"]:::leafStyle
    FILEMANAGERTEST --> filemanagertest_11
    filemanagertest_12["shouldHandleMissingFile()"]:::leafStyle
    FILEMANAGERTEST --> filemanagertest_12

    %% PageTest methods
    pagetest_1["shouldCreatePage()"]:::leafStyle
    PAGETEST --> pagetest_1
    pagetest_2["shouldReadPageData()"]:::leafStyle
    PAGETEST --> pagetest_2
    pagetest_3["shouldWritePageData()"]:::leafStyle
    PAGETEST --> pagetest_3
    pagetest_4["shouldUpdatePageHeader()"]:::leafStyle
    PAGETEST --> pagetest_4
    pagetest_5["shouldMarkPageDirty()"]:::leafStyle
    PAGETEST --> pagetest_5
    pagetest_6["shouldClearDirtyFlag()"]:::leafStyle
    PAGETEST --> pagetest_6
    pagetest_7["shouldIncrementPageLSN()"]:::leafStyle
    PAGETEST --> pagetest_7
    pagetest_8["shouldResetPage()"]:::leafStyle
    PAGETEST --> pagetest_8

    %% =====================================================
    %% Styling
    %% =====================================================
    classDef rootStyle fill:#1d3557,stroke:#457b9d,stroke-width:3px,color:#fff,font-weight:bold,font-size:16px;
    classDef branchAdmin fill:#e1f5fe,stroke:#0288d1,stroke-width:2px,color:#01579b,font-weight:bold;
    classDef branchCatalog fill:#e8f5e9,stroke:#388e3c,stroke-width:2px,color:#1b5e20,font-weight:bold;
    classDef branchStorage fill:#e0f2f1,stroke:#00695c,stroke-width:2px,color:#004d40,font-weight:bold;
    classDef branchQuery fill:#fff3e0,stroke:#f57c00,stroke-width:2px,color:#e65100,font-weight:bold;
    classDef branchTx fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029,font-weight:bold;
    classDef leafStyle fill:#ffffff,stroke:#b0bec5,stroke-width:1px,color:#37474f;
```
---

# Query Processing Module

```mermaid
classDiagram
direction TB

class SQLParser{
    +parse()
    +tokenize()
    +validateSyntax()
}

class Lexer

class AST

class QueryOptimizer{
    +optimize()
    +estimateCost()
    +chooseJoinOrder()
}

class LogicalPlan

class PhysicalPlan

class StatisticsManager

class QueryExecutor{
    +execute()
    +fetch()
    +cancel()
}

SQLParser --> Lexer
SQLParser --> AST

AST --> LogicalPlan

QueryOptimizer --> LogicalPlan
QueryOptimizer --> PhysicalPlan
QueryOptimizer --> StatisticsManager

QueryExecutor --> PhysicalPlan

%% =====================================================
%% STYLING DEFINITIONS
%% =====================================================
style SQLParser fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03
style Lexer fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03
style AST fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03
style QueryOptimizer fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03
style LogicalPlan fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03
style PhysicalPlan fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03
style StatisticsManager fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03
style QueryExecutor fill:#fff8e1,stroke:#ff8f00,stroke-width:2px,color:#664d03
```
---

# Query Processing Test
```mermaid
flowchart LR

    %% =====================================================
    %% Root
    %% =====================================================
    ROOT((Query Processing Module Test)):::rootStyle

    %% =====================================================
    %% Integration Tests
    %% =====================================================
    INT_TEST["IntegrationTest"]:::branchTx

    it1["shouldParseOptimizeAndExecuteQuery()"]:::leafStyle
    it1 --> INT_TEST
    it2["shouldGenerateLogicalAndPhysicalPlan()"]:::leafStyle
    it2 --> INT_TEST
    it3["shouldExecuteOptimizedQueryPlan()"]:::leafStyle
    it3 --> INT_TEST
    it4["shouldCollectStatisticsDuringExecution()"]:::leafStyle
    it4 --> INT_TEST
    it5["shouldRejectInvalidSQLQuery()"]:::leafStyle
    it5 --> INT_TEST
    it6["shouldOptimizeJoinQuery()"]:::leafStyle
    it6 --> INT_TEST
    it7["shouldExecuteIndexBasedQuery()"]:::leafStyle
    it7 --> INT_TEST
    it8["shouldExecuteAggregateQuery()"]:::leafStyle
    it8 --> INT_TEST
    it9["shouldExecuteNestedQuery()"]:::leafStyle
    it9 --> INT_TEST
    it10["shouldExecuteMultiTableJoin()"]:::leafStyle
    it10 --> INT_TEST
    it11["shouldExecuteDDLStatement()"]:::leafStyle
    it11 --> INT_TEST
    it12["shouldExecuteDMLStatement()"]:::leafStyle
    it12 --> INT_TEST
    INT_TEST --> ROOT

    %% =====================================================
    %% Unit Tests
    %% =====================================================
    SQLPARSERTEST["SQLParserTest"]:::branchQuery
    ROOT --> SQLPARSERTEST
    LEXERTEST["LexerTest"]:::branchQuery
    ROOT --> LEXERTEST
    ASTTEST["ASTTest"]:::branchQuery
    ROOT --> ASTTEST
    LOGICALPLANTEST["LogicalPlanTest"]:::branchQuery
    ROOT --> LOGICALPLANTEST
    QUERYOPTIMIZERTEST["QueryOptimizerTest"]:::branchQuery
    ROOT --> QUERYOPTIMIZERTEST
    PHYSICALPLANTEST["PhysicalPlanTest"]:::branchQuery
    ROOT --> PHYSICALPLANTEST
    QUERYEXECUTORTEST["QueryExecutorTest"]:::branchQuery
    ROOT --> QUERYEXECUTORTEST
    STATISTICSMANAGERTEST["StatisticsManagerTest"]:::branchQuery
    ROOT --> STATISTICSMANAGERTEST

    %% SQLParserTest methods
    sqlparsertest_1["shouldParseValidSQL()"]:::leafStyle
    SQLPARSERTEST --> sqlparsertest_1
    sqlparsertest_2["shouldParseSelectStatement()"]:::leafStyle
    SQLPARSERTEST --> sqlparsertest_2
    sqlparsertest_3["shouldParseInsertStatement()"]:::leafStyle
    SQLPARSERTEST --> sqlparsertest_3
    sqlparsertest_4["shouldParseUpdateStatement()"]:::leafStyle
    SQLPARSERTEST --> sqlparsertest_4
    sqlparsertest_5["shouldParseDeleteStatement()"]:::leafStyle
    SQLPARSERTEST --> sqlparsertest_5
    sqlparsertest_6["shouldParseCreateTableStatement()"]:::leafStyle
    SQLPARSERTEST --> sqlparsertest_6
    sqlparsertest_7["shouldParseDropTableStatement()"]:::leafStyle
    SQLPARSERTEST --> sqlparsertest_7
    sqlparsertest_8["shouldTokenizeSQL()"]:::leafStyle
    SQLPARSERTEST --> sqlparsertest_8
    sqlparsertest_9["shouldValidateSQLSyntax()"]:::leafStyle
    SQLPARSERTEST --> sqlparsertest_9
    sqlparsertest_10["shouldRejectInvalidSQLSyntax()"]:::leafStyle
    SQLPARSERTEST --> sqlparsertest_10
    sqlparsertest_11["shouldRejectUnsupportedSQL()"]:::leafStyle
    SQLPARSERTEST --> sqlparsertest_11
    sqlparsertest_12["shouldHandleNestedQuery()"]:::leafStyle
    SQLPARSERTEST --> sqlparsertest_12
    sqlparsertest_13["shouldHandleAlias()"]:::leafStyle
    SQLPARSERTEST --> sqlparsertest_13
    sqlparsertest_14["shouldHandleJoinClause()"]:::leafStyle
    SQLPARSERTEST --> sqlparsertest_14
    sqlparsertest_15["shouldHandleGroupByClause()"]:::leafStyle
    SQLPARSERTEST --> sqlparsertest_15
    sqlparsertest_16["shouldHandleOrderByClause()"]:::leafStyle
    SQLPARSERTEST --> sqlparsertest_16
    sqlparsertest_17["shouldHandleLimitClause()"]:::leafStyle
    SQLPARSERTEST --> sqlparsertest_17

    %% LexerTest methods
    lexertest_1["shouldTokenizeSQLStatement()"]:::leafStyle
    LEXERTEST --> lexertest_1
    lexertest_2["shouldIgnoreWhitespace()"]:::leafStyle
    LEXERTEST --> lexertest_2
    lexertest_3["shouldIgnoreComments()"]:::leafStyle
    LEXERTEST --> lexertest_3
    lexertest_4["shouldRecognizeKeywords()"]:::leafStyle
    LEXERTEST --> lexertest_4
    lexertest_5["shouldRecognizeIdentifiers()"]:::leafStyle
    LEXERTEST --> lexertest_5
    lexertest_6["shouldRecognizeOperators()"]:::leafStyle
    LEXERTEST --> lexertest_6
    lexertest_7["shouldRecognizeNumbers()"]:::leafStyle
    LEXERTEST --> lexertest_7
    lexertest_8["shouldRecognizeStringLiteral()"]:::leafStyle
    LEXERTEST --> lexertest_8
    lexertest_9["shouldRecognizeBooleanLiteral()"]:::leafStyle
    LEXERTEST --> lexertest_9
    lexertest_10["shouldRecognizeDelimiter()"]:::leafStyle
    LEXERTEST --> lexertest_10

    %% ASTTest methods
    asttest_1["shouldBuildASTFromSQL()"]:::leafStyle
    ASTTEST --> asttest_1
    asttest_2["shouldStoreASTRootNode()"]:::leafStyle
    ASTTEST --> asttest_2
    asttest_3["shouldBuildSelectNode()"]:::leafStyle
    ASTTEST --> asttest_3
    asttest_4["shouldBuildInsertNode()"]:::leafStyle
    ASTTEST --> asttest_4
    asttest_5["shouldBuildUpdateNode()"]:::leafStyle
    ASTTEST --> asttest_5
    asttest_6["shouldBuildDeleteNode()"]:::leafStyle
    ASTTEST --> asttest_6
    asttest_7["shouldBuildJoinNode()"]:::leafStyle
    ASTTEST --> asttest_7
    asttest_8["shouldBuildWhereNode()"]:::leafStyle
    ASTTEST --> asttest_8
    asttest_9["shouldBuildGroupByNode()"]:::leafStyle
    ASTTEST --> asttest_9
    asttest_10["shouldBuildOrderByNode()"]:::leafStyle
    ASTTEST --> asttest_10

    %% LogicalPlanTest methods
    logicalplantest_1["shouldCreateLogicalPlan()"]:::leafStyle
    LOGICALPLANTEST --> logicalplantest_1
    logicalplantest_2["shouldAddLogicalOperators()"]:::leafStyle
    LOGICALPLANTEST --> logicalplantest_2
    logicalplantest_3["shouldCreateScanOperator()"]:::leafStyle
    LOGICALPLANTEST --> logicalplantest_3
    logicalplantest_4["shouldCreateFilterOperator()"]:::leafStyle
    LOGICALPLANTEST --> logicalplantest_4
    logicalplantest_5["shouldCreateProjectionOperator()"]:::leafStyle
    LOGICALPLANTEST --> logicalplantest_5
    logicalplantest_6["shouldCreateJoinOperator()"]:::leafStyle
    LOGICALPLANTEST --> logicalplantest_6
    logicalplantest_7["shouldCreateAggregationOperator()"]:::leafStyle
    LOGICALPLANTEST --> logicalplantest_7
    logicalplantest_8["shouldCreateSortOperator()"]:::leafStyle
    LOGICALPLANTEST --> logicalplantest_8
    logicalplantest_9["shouldCreateLimitOperator()"]:::leafStyle
    LOGICALPLANTEST --> logicalplantest_9
    logicalplantest_10["shouldLinkLogicalOperators()"]:::leafStyle
    LOGICALPLANTEST --> logicalplantest_10

    %% QueryOptimizerTest methods
    queryoptimizertest_1["shouldOptimizeLogicalPlan()"]:::leafStyle
    QUERYOPTIMIZERTEST --> queryoptimizertest_1
    queryoptimizertest_2["shouldEstimateQueryCost()"]:::leafStyle
    QUERYOPTIMIZERTEST --> queryoptimizertest_2
    queryoptimizertest_3["shouldChooseJoinOrder()"]:::leafStyle
    QUERYOPTIMIZERTEST --> queryoptimizertest_3
    queryoptimizertest_4["shouldGeneratePhysicalPlan()"]:::leafStyle
    QUERYOPTIMIZERTEST --> queryoptimizertest_4
    queryoptimizertest_5["shouldPushDownPredicate()"]:::leafStyle
    QUERYOPTIMIZERTEST --> queryoptimizertest_5
    queryoptimizertest_6["shouldEliminateUnusedProjection()"]:::leafStyle
    QUERYOPTIMIZERTEST --> queryoptimizertest_6
    queryoptimizertest_7["shouldSimplifyExpression()"]:::leafStyle
    QUERYOPTIMIZERTEST --> queryoptimizertest_7
    queryoptimizertest_8["shouldChooseIndexScan()"]:::leafStyle
    QUERYOPTIMIZERTEST --> queryoptimizertest_8
    queryoptimizertest_9["shouldChooseTableScan()"]:::leafStyle
    QUERYOPTIMIZERTEST --> queryoptimizertest_9
    queryoptimizertest_10["shouldOptimizeJoinStrategy()"]:::leafStyle
    QUERYOPTIMIZERTEST --> queryoptimizertest_10
    queryoptimizertest_11["shouldOptimizeAggregation()"]:::leafStyle
    QUERYOPTIMIZERTEST --> queryoptimizertest_11
    queryoptimizertest_12["shouldReuseStatistics()"]:::leafStyle
    QUERYOPTIMIZERTEST --> queryoptimizertest_12

    %% PhysicalPlanTest methods
    physicalplantest_1["shouldCreatePhysicalPlan()"]:::leafStyle
    PHYSICALPLANTEST --> physicalplantest_1
    physicalplantest_2["shouldStoreExecutionOperators()"]:::leafStyle
    PHYSICALPLANTEST --> physicalplantest_2
    physicalplantest_3["shouldCreateSequentialScan()"]:::leafStyle
    PHYSICALPLANTEST --> physicalplantest_3
    physicalplantest_4["shouldCreateIndexScan()"]:::leafStyle
    PHYSICALPLANTEST --> physicalplantest_4
    physicalplantest_5["shouldCreateNestedLoopJoin()"]:::leafStyle
    PHYSICALPLANTEST --> physicalplantest_5
    physicalplantest_6["shouldCreateHashJoin()"]:::leafStyle
    PHYSICALPLANTEST --> physicalplantest_6
    physicalplantest_7["shouldCreateMergeJoin()"]:::leafStyle
    PHYSICALPLANTEST --> physicalplantest_7
    physicalplantest_8["shouldCreateSortOperator()"]:::leafStyle
    PHYSICALPLANTEST --> physicalplantest_8
    physicalplantest_9["shouldCreateAggregateOperator()"]:::leafStyle
    PHYSICALPLANTEST --> physicalplantest_9
    physicalplantest_10["shouldLinkExecutionTree()"]:::leafStyle
    PHYSICALPLANTEST --> physicalplantest_10

    %% QueryExecutorTest methods
    queryexecutortest_1["shouldExecutePhysicalPlan()"]:::leafStyle
    QUERYEXECUTORTEST --> queryexecutortest_1
    queryexecutortest_2["shouldExecuteSequentialScan()"]:::leafStyle
    QUERYEXECUTORTEST --> queryexecutortest_2
    queryexecutortest_3["shouldExecuteIndexScan()"]:::leafStyle
    QUERYEXECUTORTEST --> queryexecutortest_3
    queryexecutortest_4["shouldExecuteJoin()"]:::leafStyle
    QUERYEXECUTORTEST --> queryexecutortest_4
    queryexecutortest_5["shouldExecuteAggregation()"]:::leafStyle
    QUERYEXECUTORTEST --> queryexecutortest_5
    queryexecutortest_6["shouldExecuteSort()"]:::leafStyle
    QUERYEXECUTORTEST --> queryexecutortest_6
    queryexecutortest_7["shouldExecuteProjection()"]:::leafStyle
    QUERYEXECUTORTEST --> queryexecutortest_7
    queryexecutortest_8["shouldExecuteFilter()"]:::leafStyle
    QUERYEXECUTORTEST --> queryexecutortest_8
    queryexecutortest_9["shouldFetchResultRows()"]:::leafStyle
    QUERYEXECUTORTEST --> queryexecutortest_9
    queryexecutortest_10["shouldReturnEmptyResult()"]:::leafStyle
    QUERYEXECUTORTEST --> queryexecutortest_10
    queryexecutortest_11["shouldCancelRunningQuery()"]:::leafStyle
    QUERYEXECUTORTEST --> queryexecutortest_11
    queryexecutortest_12["shouldReleaseExecutionResources()"]:::leafStyle
    QUERYEXECUTORTEST --> queryexecutortest_12

    %% StatisticsManagerTest methods
    statisticsmanagertest_1["shouldCollectTableStatistics()"]:::leafStyle
    STATISTICSMANAGERTEST --> statisticsmanagertest_1
    statisticsmanagertest_2["shouldCollectColumnStatistics()"]:::leafStyle
    STATISTICSMANAGERTEST --> statisticsmanagertest_2
    statisticsmanagertest_3["shouldUpdateStatistics()"]:::leafStyle
    STATISTICSMANAGERTEST --> statisticsmanagertest_3
    statisticsmanagertest_4["shouldDeleteStatistics()"]:::leafStyle
    STATISTICSMANAGERTEST --> statisticsmanagertest_4
    statisticsmanagertest_5["shouldEstimateRowCount()"]:::leafStyle
    STATISTICSMANAGERTEST --> statisticsmanagertest_5
    statisticsmanagertest_6["shouldEstimateSelectivity()"]:::leafStyle
    STATISTICSMANAGERTEST --> statisticsmanagertest_6
    statisticsmanagertest_7["shouldEstimateDistinctValues()"]:::leafStyle
    STATISTICSMANAGERTEST --> statisticsmanagertest_7
    statisticsmanagertest_8["shouldEstimateJoinCost()"]:::leafStyle
    STATISTICSMANAGERTEST --> statisticsmanagertest_8
    statisticsmanagertest_9["shouldProvideStatisticsForOptimizer()"]:::leafStyle
    STATISTICSMANAGERTEST --> statisticsmanagertest_9
    statisticsmanagertest_10["shouldPersistStatistics()"]:::leafStyle
    STATISTICSMANAGERTEST --> statisticsmanagertest_10

    %% =====================================================
    %% Styling
    %% =====================================================
    classDef rootStyle fill:#1d3557,stroke:#457b9d,stroke-width:3px,color:#fff,font-weight:bold,font-size:16px;
    classDef branchAdmin fill:#e1f5fe,stroke:#0288d1,stroke-width:2px,color:#01579b,font-weight:bold;
    classDef branchCatalog fill:#e8f5e9,stroke:#388e3c,stroke-width:2px,color:#1b5e20,font-weight:bold;
    classDef branchStorage fill:#e0f2f1,stroke:#00695c,stroke-width:2px,color:#004d40,font-weight:bold;
    classDef branchQuery fill:#fff3e0,stroke:#f57c00,stroke-width:2px,color:#e65100,font-weight:bold;
    classDef branchTx fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029,font-weight:bold;
    classDef leafStyle fill:#ffffff,stroke:#b0bec5,stroke-width:1px,color:#37474f;
```
---

# Transaction Management Module
```mermaid
classDiagram
direction TB

class Transaction{
    +transactionId
    +isolationLevel
    +state
    +startTime

    +begin()
    +commit()
    +rollback()
    +savepoint()
}

class TransactionManager{
    +beginTransaction()
    +commit()
    +rollback()
    +recover()
}

class LockManager{
    +acquireLock()
    +releaseLock()
    +detectDeadlock()
}

class MVCCManager{
    +createVersion()
    +garbageCollect()
}

class WALManager{
    +currentLSN
    +logFiles

    +append()
    +flush()
    +replay()
}

class RecoveryManager{
    +recover()
}

TransactionManager --> Transaction
TransactionManager --> LockManager
TransactionManager --> MVCCManager
TransactionManager --> WALManager

RecoveryManager --> WALManager

%% =====================================================
%% STYLING DEFINITIONS
%% =====================================================
style Transaction fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
style TransactionManager fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
style LockManager fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
style MVCCManager fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
style WALManager fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
style RecoveryManager fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
```
---

# Transaction Management Test
```mermaid
flowchart LR

    %% =====================================================
    %% Root
    %% =====================================================
    ROOT((Transaction & Lock Module Test)):::rootStyle

    %% =====================================================
    %% Integration Tests
    %% =====================================================
    INT_TEST["IntegrationTest"]:::branchTx

    it1["shouldCommitTransactionSuccessfully()"]:::leafStyle
    it1 --> INT_TEST
    it2["shouldRollbackTransactionSuccessfully()"]:::leafStyle
    it2 --> INT_TEST
    it3["shouldRecoverAfterCrash()"]:::leafStyle
    it3 --> INT_TEST
    it4["shouldCommitTransactionAndFlushWAL()"]:::leafStyle
    it4 --> INT_TEST
    it5["shouldRollbackTransactionAndReleaseLocks()"]:::leafStyle
    it5 --> INT_TEST
    it6["shouldRecoverDatabaseUsingWAL()"]:::leafStyle
    it6 --> INT_TEST
    it7["shouldRecoverMultipleTransactions()"]:::leafStyle
    it7 --> INT_TEST
    it8["shouldRecoverAfterPowerFailure()"]:::leafStyle
    it8 --> INT_TEST
    INT_TEST --> ROOT

    %% =====================================================
    %% Unit Tests
    %% =====================================================
    TRANSACTIONMANAGERTEST["TransactionManagerTest"]:::branchTx
    ROOT --> TRANSACTIONMANAGERTEST
    TRANSACTIONTEST["TransactionTest"]:::branchTx
    ROOT --> TRANSACTIONTEST
    LOCKMANAGERTEST["LockManagerTest"]:::branchTx
    ROOT --> LOCKMANAGERTEST
    MVCCMANAGERTEST["MVCCManagerTest"]:::branchTx
    ROOT --> MVCCMANAGERTEST

    %% TransactionManagerTest methods
    transactionmanagertest_1["shouldBeginTransaction()"]:::leafStyle
    TRANSACTIONMANAGERTEST --> transactionmanagertest_1
    transactionmanagertest_2["shouldCommitTransaction()"]:::leafStyle
    TRANSACTIONMANAGERTEST --> transactionmanagertest_2
    transactionmanagertest_3["shouldRollbackTransaction()"]:::leafStyle
    TRANSACTIONMANAGERTEST --> transactionmanagertest_3
    transactionmanagertest_4["shouldRecoverTransactions()"]:::leafStyle
    TRANSACTIONMANAGERTEST --> transactionmanagertest_4
    transactionmanagertest_5["shouldSuspendTransaction()"]:::leafStyle
    TRANSACTIONMANAGERTEST --> transactionmanagertest_5
    transactionmanagertest_6["shouldResumeTransaction()"]:::leafStyle
    TRANSACTIONMANAGERTEST --> transactionmanagertest_6
    transactionmanagertest_7["shouldRegisterActiveTransaction()"]:::leafStyle
    TRANSACTIONMANAGERTEST --> transactionmanagertest_7
    transactionmanagertest_8["shouldRemoveCommittedTransaction()"]:::leafStyle
    TRANSACTIONMANAGERTEST --> transactionmanagertest_8
    transactionmanagertest_9["shouldRemoveRolledBackTransaction()"]:::leafStyle
    TRANSACTIONMANAGERTEST --> transactionmanagertest_9
    transactionmanagertest_10["shouldAssignTransactionId()"]:::leafStyle
    TRANSACTIONMANAGERTEST --> transactionmanagertest_10
    transactionmanagertest_11["shouldTrackTransactionState()"]:::leafStyle
    TRANSACTIONMANAGERTEST --> transactionmanagertest_11
    transactionmanagertest_12["shouldRejectDuplicateTransaction()"]:::leafStyle
    TRANSACTIONMANAGERTEST --> transactionmanagertest_12

    %% TransactionTest methods
    transactiontest_1["shouldBegin()"]:::leafStyle
    TRANSACTIONTEST --> transactiontest_1
    transactiontest_2["shouldCommit()"]:::leafStyle
    TRANSACTIONTEST --> transactiontest_2
    transactiontest_3["shouldRollback()"]:::leafStyle
    TRANSACTIONTEST --> transactiontest_3
    transactiontest_4["shouldCreateSavepoint()"]:::leafStyle
    TRANSACTIONTEST --> transactiontest_4
    transactiontest_5["shouldRollbackToSavepoint()"]:::leafStyle
    TRANSACTIONTEST --> transactiontest_5
    transactiontest_6["shouldReleaseSavepoint()"]:::leafStyle
    TRANSACTIONTEST --> transactiontest_6
    transactiontest_7["shouldHandleStateTransition()"]:::leafStyle
    TRANSACTIONTEST --> transactiontest_7
    transactiontest_8["shouldEnforceIsolationLevel()"]:::leafStyle
    TRANSACTIONTEST --> transactiontest_8
    transactiontest_9["shouldRecordStartTime()"]:::leafStyle
    TRANSACTIONTEST --> transactiontest_9
    transactiontest_10["shouldRecordCommitTime()"]:::leafStyle
    TRANSACTIONTEST --> transactiontest_10
    transactiontest_11["shouldRejectCommitAfterRollback()"]:::leafStyle
    TRANSACTIONTEST --> transactiontest_11
    transactiontest_12["shouldRejectRollbackAfterCommit()"]:::leafStyle
    TRANSACTIONTEST --> transactiontest_12

    %% LockManagerTest methods
    lockmanagertest_1["shouldAcquireSharedLock()"]:::leafStyle
    LOCKMANAGERTEST --> lockmanagertest_1
    lockmanagertest_2["shouldAcquireExclusiveLock()"]:::leafStyle
    LOCKMANAGERTEST --> lockmanagertest_2
    lockmanagertest_3["shouldReleaseLock()"]:::leafStyle
    LOCKMANAGERTEST --> lockmanagertest_3
    lockmanagertest_4["shouldUpgradeLock()"]:::leafStyle
    LOCKMANAGERTEST --> lockmanagertest_4
    lockmanagertest_5["shouldDowngradeLock()"]:::leafStyle
    LOCKMANAGERTEST --> lockmanagertest_5
    lockmanagertest_6["shouldCheckCompatibility()"]:::leafStyle
    LOCKMANAGERTEST --> lockmanagertest_6
    lockmanagertest_7["shouldRejectConflictingLock()"]:::leafStyle
    LOCKMANAGERTEST --> lockmanagertest_7
    lockmanagertest_8["shouldQueueWaitingTransaction()"]:::leafStyle
    LOCKMANAGERTEST --> lockmanagertest_8
    lockmanagertest_9["shouldWakeWaitingTransaction()"]:::leafStyle
    LOCKMANAGERTEST --> lockmanagertest_9
    lockmanagertest_10["shouldDetectDeadlock()"]:::leafStyle
    LOCKMANAGERTEST --> lockmanagertest_10
    lockmanagertest_11["shouldResolveDeadlock()"]:::leafStyle
    LOCKMANAGERTEST --> lockmanagertest_11
    lockmanagertest_12["shouldReleaseAllLocksOnCommit()"]:::leafStyle
    LOCKMANAGERTEST --> lockmanagertest_12

    %% MVCCManagerTest methods
    mvccmanagertest_1["shouldCreateVersion()"]:::leafStyle
    MVCCMANAGERTEST --> mvccmanagertest_1
    mvccmanagertest_2["shouldReadVisibleVersion()"]:::leafStyle
    MVCCMANAGERTEST --> mvccmanagertest_2
    mvccmanagertest_3["shouldHideUncommittedVersion()"]:::leafStyle
    MVCCMANAGERTEST --> mvccmanagertest_3
    mvccmanagertest_4["shouldEnforceVisibilityRule()"]:::leafStyle
    MVCCMANAGERTEST --> mvccmanagertest_4
    mvccmanagertest_5["shouldCreateSnapshot()"]:::leafStyle
    MVCCMANAGERTEST --> mvccmanagertest_5
    mvccmanagertest_6["shouldReturnSnapshotVersion()"]:::leafStyle
    MVCCMANAGERTEST --> mvccmanagertest_6
    mvccmanagertest_7["shouldDeleteObsoleteVersion()"]:::leafStyle
    MVCCMANAGERTEST --> mvccmanagertest_7
    mvccmanagertest_8["shouldGarbageCollect()"]:::leafStyle
    MVCCMANAGERTEST --> mvccmanagertest_8
    mvccmanagertest_9["shouldRemoveExpiredVersion()"]:::leafStyle
    MVCCMANAGERTEST --> mvccmanagertest_9
    mvccmanagertest_10["shouldRejectInvisibleVersion()"]:::leafStyle
    MVCCMANAGERTEST --> mvccmanagertest_10

    %% =====================================================
    %% Styling
    %% =====================================================
    classDef rootStyle fill:#1d3557,stroke:#457b9d,stroke-width:3px,color:#fff,font-weight:bold,font-size:16px;
    classDef branchAdmin fill:#e1f5fe,stroke:#0288d1,stroke-width:2px,color:#01579b,font-weight:bold;
    classDef branchCatalog fill:#e8f5e9,stroke:#388e3c,stroke-width:2px,color:#1b5e20,font-weight:bold;
    classDef branchStorage fill:#e0f2f1,stroke:#00695c,stroke-width:2px,color:#004d40,font-weight:bold;
    classDef branchQuery fill:#fff3e0,stroke:#f57c00,stroke-width:2px,color:#e65100,font-weight:bold;
    classDef branchTx fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029,font-weight:bold;
    classDef leafStyle fill:#ffffff,stroke:#b0bec5,stroke-width:1px,color:#37474f;
```

# Metadata Module
```mermaid
classDiagram
direction TB

class CatalogManager{
    +metadataCache

    +registerTable()
    +getTable()
    +getSchema()
    +refreshMetadata()
}

class Database

class Schema

class Table

class Index

CatalogManager --> Database
CatalogManager --> Schema
CatalogManager --> Table
CatalogManager --> Index

%% =====================================================
%% STYLING DEFINITIONS
%% =====================================================
style CatalogManager fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Database fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Schema fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Table fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Index fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
```
---
# Metadata Test
```mermaid
flowchart LR

    %% =====================================================
    %% Root
    %% =====================================================
    ROOT((Catalog Module Test)):::rootStyle

    %% =====================================================
    %% Integration Tests
    %% =====================================================
    INT_TEST["IntegrationTest"]:::branchTx

    it1["shouldRegisterDatabaseMetadata()"]:::leafStyle
    it1 --> INT_TEST
    it2["shouldRegisterSchemaMetadata()"]:::leafStyle
    it2 --> INT_TEST
    it3["shouldRegisterTableMetadata()"]:::leafStyle
    it3 --> INT_TEST
    it4["shouldRegisterIndexMetadata()"]:::leafStyle
    it4 --> INT_TEST
    it5["shouldUpdateCatalogAfterSchemaChange()"]:::leafStyle
    it5 --> INT_TEST
    it6["shouldRefreshMetadataAfterDDL()"]:::leafStyle
    it6 --> INT_TEST
    it7["shouldSynchronizeMetadataCache()"]:::leafStyle
    it7 --> INT_TEST
    it8["shouldReloadMetadataAfterRestart()"]:::leafStyle
    it8 --> INT_TEST
    INT_TEST --> ROOT

    %% =====================================================
    %% Unit Tests
    %% =====================================================
    CATALOGMANAGERTEST["CatalogManagerTest"]:::branchCatalog
    ROOT --> CATALOGMANAGERTEST

    %% CatalogManagerTest methods
    catalogmanagertest_1["shouldRegisterDatabase()"]:::leafStyle
    CATALOGMANAGERTEST --> catalogmanagertest_1
    catalogmanagertest_2["shouldRegisterSchema()"]:::leafStyle
    CATALOGMANAGERTEST --> catalogmanagertest_2
    catalogmanagertest_3["shouldRegisterTable()"]:::leafStyle
    CATALOGMANAGERTEST --> catalogmanagertest_3
    catalogmanagertest_4["shouldRegisterIndex()"]:::leafStyle
    CATALOGMANAGERTEST --> catalogmanagertest_4
    catalogmanagertest_5["shouldFindDatabase()"]:::leafStyle
    CATALOGMANAGERTEST --> catalogmanagertest_5
    catalogmanagertest_6["shouldFindSchema()"]:::leafStyle
    CATALOGMANAGERTEST --> catalogmanagertest_6
    catalogmanagertest_7["shouldFindTable()"]:::leafStyle
    CATALOGMANAGERTEST --> catalogmanagertest_7
    catalogmanagertest_8["shouldFindIndex()"]:::leafStyle
    CATALOGMANAGERTEST --> catalogmanagertest_8
    catalogmanagertest_9["shouldRefreshMetadata()"]:::leafStyle
    CATALOGMANAGERTEST --> catalogmanagertest_9
    catalogmanagertest_10["shouldInvalidateMetadataCache()"]:::leafStyle
    CATALOGMANAGERTEST --> catalogmanagertest_10
    catalogmanagertest_11["shouldLoadMetadataFromDisk()"]:::leafStyle
    CATALOGMANAGERTEST --> catalogmanagertest_11
    catalogmanagertest_12["shouldCacheFrequentlyUsedMetadata()"]:::leafStyle
    CATALOGMANAGERTEST --> catalogmanagertest_12
    catalogmanagertest_13["shouldUpdateTableMetadata()"]:::leafStyle
    CATALOGMANAGERTEST --> catalogmanagertest_13
    catalogmanagertest_14["shouldUpdateIndexMetadata()"]:::leafStyle
    CATALOGMANAGERTEST --> catalogmanagertest_14
    catalogmanagertest_15["shouldRemoveTableMetadata()"]:::leafStyle
    CATALOGMANAGERTEST --> catalogmanagertest_15
    catalogmanagertest_16["shouldRemoveSchemaMetadata()"]:::leafStyle
    CATALOGMANAGERTEST --> catalogmanagertest_16
    catalogmanagertest_17["shouldRemoveDatabaseMetadata()"]:::leafStyle
    CATALOGMANAGERTEST --> catalogmanagertest_17
    catalogmanagertest_18["shouldDetectDuplicateTableRegistration()"]:::leafStyle
    CATALOGMANAGERTEST --> catalogmanagertest_18
    catalogmanagertest_19["shouldRejectUnknownDatabase()"]:::leafStyle
    CATALOGMANAGERTEST --> catalogmanagertest_19
    catalogmanagertest_20["shouldReturnCachedMetadata()"]:::leafStyle
    CATALOGMANAGERTEST --> catalogmanagertest_20

    %% =====================================================
    %% Styling
    %% =====================================================
    classDef rootStyle fill:#1d3557,stroke:#457b9d,stroke-width:3px,color:#fff,font-weight:bold,font-size:16px;
    classDef branchAdmin fill:#e1f5fe,stroke:#0288d1,stroke-width:2px,color:#01579b,font-weight:bold;
    classDef branchCatalog fill:#e8f5e9,stroke:#388e3c,stroke-width:2px,color:#1b5e20,font-weight:bold;
    classDef branchStorage fill:#e0f2f1,stroke:#00695c,stroke-width:2px,color:#004d40,font-weight:bold;
    classDef branchQuery fill:#fff3e0,stroke:#f57c00,stroke-width:2px,color:#e65100,font-weight:bold;
    classDef branchTx fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029,font-weight:bold;
    classDef leafStyle fill:#ffffff,stroke:#b0bec5,stroke-width:1px,color:#37474f;
```
---

# Security Module
```mermaid
classDiagram
direction TB

class SecurityManager{
    +users
    +roles
    +policies

    +authenticate()
    +authorize()
    +grantPermission()
    +revokePermission()
}

class User{
    +userId
    +username
    +passwordHash
    +status
}

class Role{
    +roleId
    +name
    +permissions
}

class Permission{
    +permissionId
    +resource
    +action
}

SecurityManager --> User
SecurityManager --> Role

Role --> Permission

%% =====================================================
%% STYLING DEFINITIONS
%% =====================================================
style SecurityManager fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style User fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style Role fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style Permission fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
```
---

# Security Test
```mermaid
flowchart LR

    %% =====================================================
    %% Root
    %% =====================================================
    ROOT((Security Module Test)):::rootStyle

    %% =====================================================
    %% Integration Tests
    %% =====================================================
    INT_TEST["IntegrationTest"]:::branchTx

    it1["shouldAuthenticateAndAuthorizeUser()"]:::leafStyle
    it1 --> INT_TEST
    it2["shouldAssignRoleAndGrantPermission()"]:::leafStyle
    it2 --> INT_TEST
    it3["shouldRevokePermissionSuccessfully()"]:::leafStyle
    it3 --> INT_TEST
    it4["shouldRejectUnauthorizedAccess()"]:::leafStyle
    it4 --> INT_TEST
    it5["shouldAuthenticateGrantAndAccess()"]:::leafStyle
    it5 --> INT_TEST
    it6["shouldGrantRoleThenAuthorize()"]:::leafStyle
    it6 --> INT_TEST
    it7["shouldRevokePermissionAndRejectAccess()"]:::leafStyle
    it7 --> INT_TEST
    INT_TEST --> ROOT

    %% =====================================================
    %% Unit Tests
    %% =====================================================
    SECURITYMANAGER["SecurityManager"]:::branchAdmin
    ROOT --> SECURITYMANAGER
    USER["User"]:::branchAdmin
    ROOT --> USER
    ROLE["Role"]:::branchAdmin
    ROOT --> ROLE
    PERMISSION["Permission"]:::branchAdmin
    ROOT --> PERMISSION

    %% SecurityManager methods
    securitymanager_1["shouldAuthenticateUser()"]:::leafStyle
    SECURITYMANAGER --> securitymanager_1
    securitymanager_2["shouldAuthorizeUser()"]:::leafStyle
    SECURITYMANAGER --> securitymanager_2
    securitymanager_3["shouldGrantPermission()"]:::leafStyle
    SECURITYMANAGER --> securitymanager_3
    securitymanager_4["shouldRevokePermission()"]:::leafStyle
    SECURITYMANAGER --> securitymanager_4
    securitymanager_5["shouldRejectInvalidPassword()"]:::leafStyle
    SECURITYMANAGER --> securitymanager_5
    securitymanager_6["shouldRejectLockedUser()"]:::leafStyle
    SECURITYMANAGER --> securitymanager_6
    securitymanager_7["shouldRejectDisabledUser()"]:::leafStyle
    SECURITYMANAGER --> securitymanager_7
    securitymanager_8["shouldCheckRolePermission()"]:::leafStyle
    SECURITYMANAGER --> securitymanager_8
    securitymanager_9["shouldGrantRolePermission()"]:::leafStyle
    SECURITYMANAGER --> securitymanager_9
    securitymanager_10["shouldVerifyPermissionInheritance()"]:::leafStyle
    SECURITYMANAGER --> securitymanager_10

    %% User methods
    user_1["shouldCreateUser()"]:::leafStyle
    USER --> user_1
    user_2["shouldUpdatePassword()"]:::leafStyle
    USER --> user_2
    user_3["shouldLockUser()"]:::leafStyle
    USER --> user_3
    user_4["shouldUnlockUser()"]:::leafStyle
    USER --> user_4
    user_5["shouldEnableUser()"]:::leafStyle
    USER --> user_5
    user_6["shouldDisableUser()"]:::leafStyle
    USER --> user_6
    user_7["shouldValidatePasswordHash()"]:::leafStyle
    USER --> user_7

    %% Role methods
    role_1["shouldCreateRole()"]:::leafStyle
    ROLE --> role_1
    role_2["shouldAssignPermission()"]:::leafStyle
    ROLE --> role_2
    role_3["shouldRemovePermission()"]:::leafStyle
    ROLE --> role_3
    role_4["shouldDeleteRole()"]:::leafStyle
    ROLE --> role_4
    role_5["shouldRenameRole()"]:::leafStyle
    ROLE --> role_5
    role_6["shouldListPermissions()"]:::leafStyle
    ROLE --> role_6

    %% Permission methods
    permission_1["shouldCreatePermission()"]:::leafStyle
    PERMISSION --> permission_1
    permission_2["shouldComparePermissions()"]:::leafStyle
    PERMISSION --> permission_2
    permission_3["shouldValidatePermission()"]:::leafStyle
    PERMISSION --> permission_3
    permission_4["shouldStoreAction()"]:::leafStyle
    PERMISSION --> permission_4
    permission_5["shouldStoreResource()"]:::leafStyle
    PERMISSION --> permission_5

    %% =====================================================
    %% Styling
    %% =====================================================
    classDef rootStyle fill:#1d3557,stroke:#457b9d,stroke-width:3px,color:#fff,font-weight:bold,font-size:16px;
    classDef branchAdmin fill:#e1f5fe,stroke:#0288d1,stroke-width:2px,color:#01579b,font-weight:bold;
    classDef branchCatalog fill:#e8f5e9,stroke:#388e3c,stroke-width:2px,color:#1b5e20,font-weight:bold;
    classDef branchStorage fill:#e0f2f1,stroke:#00695c,stroke-width:2px,color:#004d40,font-weight:bold;
    classDef branchQuery fill:#fff3e0,stroke:#f57c00,stroke-width:2px,color:#e65100,font-weight:bold;
    classDef branchTx fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029,font-weight:bold;
    classDef leafStyle fill:#ffffff,stroke:#b0bec5,stroke-width:1px,color:#37474f;
```
--- 

# Recovery Module 
```mermaid
classDiagram
direction TB

class RecoveryManager{
    +recover()
}

class WALManager{
    +currentLSN
    +logFiles

    +append()
    +flush()
    +replay()
}

class LogRecord

RecoveryManager --> WALManager
WALManager --> LogRecord

%% =====================================================
%% STYLING DEFINITIONS
%% =====================================================
style RecoveryManager fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
style WALManager fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
style LogRecord fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
```
--- 

# Recovery Test
```mermaid
flowchart LR

    %% =====================================================
    %% Root
    %% =====================================================
    ROOT((Recovery Module Test)):::rootStyle

    %% =====================================================
    %% Integration Tests
    %% =====================================================
    INT_TEST["IntegrationTest"]:::branchTx

    it1["shouldRecoverAfterCrash()"]:::leafStyle
    it1 --> INT_TEST
    it2["shouldCommitTransactionWithWAL()"]:::leafStyle
    it2 --> INT_TEST
    it3["shouldRollbackTransactionWithMVCC()"]:::leafStyle
    it3 --> INT_TEST
    it4["shouldAcquireAndReleaseLocks()"]:::leafStyle
    it4 --> INT_TEST
    it5["shouldRecoverCommittedTransactions()"]:::leafStyle
    it5 --> INT_TEST
    it6["shouldRecoverRolledBackTransactions()"]:::leafStyle
    it6 --> INT_TEST
    it7["shouldHandleConcurrentTransactions()"]:::leafStyle
    it7 --> INT_TEST
    it8["shouldResolveDeadlockAutomatically()"]:::leafStyle
    it8 --> INT_TEST
    INT_TEST --> ROOT

    %% =====================================================
    %% Unit Tests
    %% =====================================================
    WALMANAGERTEST["WALManagerTest"]:::branchTx
    ROOT --> WALMANAGERTEST
    RECOVERYMANAGERTEST["RecoveryManagerTest"]:::branchTx
    ROOT --> RECOVERYMANAGERTEST

    %% WALManagerTest methods
    walmanagertest_1["shouldAppendLog()"]:::leafStyle
    WALMANAGERTEST --> walmanagertest_1
    walmanagertest_2["shouldFlushLog()"]:::leafStyle
    WALMANAGERTEST --> walmanagertest_2
    walmanagertest_3["shouldReplayLog()"]:::leafStyle
    WALMANAGERTEST --> walmanagertest_3
    walmanagertest_4["shouldOrderLSN()"]:::leafStyle
    WALMANAGERTEST --> walmanagertest_4
    walmanagertest_5["shouldAssignIncreasingLSN()"]:::leafStyle
    WALMANAGERTEST --> walmanagertest_5
    walmanagertest_6["shouldWriteCommitRecord()"]:::leafStyle
    WALMANAGERTEST --> walmanagertest_6
    walmanagertest_7["shouldWriteAbortRecord()"]:::leafStyle
    WALMANAGERTEST --> walmanagertest_7
    walmanagertest_8["shouldRecoverLogFile()"]:::leafStyle
    WALMANAGERTEST --> walmanagertest_8
    walmanagertest_9["shouldPersistLogRecord()"]:::leafStyle
    WALMANAGERTEST --> walmanagertest_9
    walmanagertest_10["shouldVerifyWriteAheadLoggingRule()"]:::leafStyle
    WALMANAGERTEST --> walmanagertest_10

    %% RecoveryManagerTest methods
    recoverymanagertest_1["shouldRecover()"]:::leafStyle
    RECOVERYMANAGERTEST --> recoverymanagertest_1
    recoverymanagertest_2["shouldRedo()"]:::leafStyle
    RECOVERYMANAGERTEST --> recoverymanagertest_2
    recoverymanagertest_3["shouldUndo()"]:::leafStyle
    RECOVERYMANAGERTEST --> recoverymanagertest_3
    recoverymanagertest_4["shouldRecoverCheckpoint()"]:::leafStyle
    RECOVERYMANAGERTEST --> recoverymanagertest_4
    recoverymanagertest_5["shouldScanLogRecords()"]:::leafStyle
    RECOVERYMANAGERTEST --> recoverymanagertest_5
    recoverymanagertest_6["shouldRedoCommittedTransactions()"]:::leafStyle
    RECOVERYMANAGERTEST --> recoverymanagertest_6
    recoverymanagertest_7["shouldUndoIncompleteTransactions()"]:::leafStyle
    RECOVERYMANAGERTEST --> recoverymanagertest_7
    recoverymanagertest_8["shouldRestoreDatabaseState()"]:::leafStyle
    RECOVERYMANAGERTEST --> recoverymanagertest_8
    recoverymanagertest_9["shouldSkipCommittedTransaction()"]:::leafStyle
    RECOVERYMANAGERTEST --> recoverymanagertest_9
    recoverymanagertest_10["shouldFinishRecovery()"]:::leafStyle
    RECOVERYMANAGERTEST --> recoverymanagertest_10

    %% =====================================================
    %% Styling
    %% =====================================================
    classDef rootStyle fill:#1d3557,stroke:#457b9d,stroke-width:3px,color:#fff,font-weight:bold,font-size:16px;
    classDef branchAdmin fill:#e1f5fe,stroke:#0288d1,stroke-width:2px,color:#01579b,font-weight:bold;
    classDef branchCatalog fill:#e8f5e9,stroke:#388e3c,stroke-width:2px,color:#1b5e20,font-weight:bold;
    classDef branchStorage fill:#e0f2f1,stroke:#00695c,stroke-width:2px,color:#004d40,font-weight:bold;
    classDef branchQuery fill:#fff3e0,stroke:#f57c00,stroke-width:2px,color:#e65100,font-weight:bold;
    classDef branchTx fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029,font-weight:bold;
    classDef leafStyle fill:#ffffff,stroke:#b0bec5,stroke-width:1px,color:#37474f;
```
---
# Replication Module 
```mermaid
classDiagram
direction TB

class ReplicationManager{
    +replicationMode
    +replicas

    +replicate()
    +synchronize()
    +electLeader()
}

class ClusterNode

class WALManager

ReplicationManager --> ClusterNode
ReplicationManager --> WALManager

%% =====================================================
%% STYLING DEFINITIONS
%% =====================================================
style ReplicationManager fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style ClusterNode fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style WALManager fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029
```
--- 

# Replication Test
```mermaid
flowchart LR

    %% =====================================================
    %% Root
    %% =====================================================
    ROOT((Replication Module Test)):::rootStyle

    %% =====================================================
    %% Integration Tests
    %% =====================================================
    INT_TEST["IntegrationTest"]:::branchTx

    it1["shouldReplicateDataToReplica()"]:::leafStyle
    it1 --> INT_TEST
    it2["shouldSynchronizeClusterNodes()"]:::leafStyle
    it2 --> INT_TEST
    it3["shouldElectLeaderSuccessfully()"]:::leafStyle
    it3 --> INT_TEST
    it4["shouldRecoverReplicationAfterNodeFailure()"]:::leafStyle
    it4 --> INT_TEST
    it5["shouldReplicateCommittedTransactionAcrossCluster()"]:::leafStyle
    it5 --> INT_TEST
    it6["shouldSynchronizeReplicaUsingWAL()"]:::leafStyle
    it6 --> INT_TEST
    it7["shouldContinueReplicationAfterLeaderElection()"]:::leafStyle
    it7 --> INT_TEST
    it8["shouldRecoverReplicaAfterFailure()"]:::leafStyle
    it8 --> INT_TEST
    INT_TEST --> ROOT

    %% =====================================================
    %% Unit Tests
    %% =====================================================
    REPLICATIONMANAGERTEST["ReplicationManagerTest"]:::branchTx
    ROOT --> REPLICATIONMANAGERTEST
    CLUSTERNODE["ClusterNode"]:::branchTx
    ROOT --> CLUSTERNODE

    %% ReplicationManagerTest methods
    replicationmanagertest_1["shouldReplicateData()"]:::leafStyle
    REPLICATIONMANAGERTEST --> replicationmanagertest_1
    replicationmanagertest_2["shouldSynchronizeReplicas()"]:::leafStyle
    REPLICATIONMANAGERTEST --> replicationmanagertest_2
    replicationmanagertest_3["shouldElectLeader()"]:::leafStyle
    REPLICATIONMANAGERTEST --> replicationmanagertest_3
    replicationmanagertest_4["shouldHandleReplicaFailure()"]:::leafStyle
    REPLICATIONMANAGERTEST --> replicationmanagertest_4
    replicationmanagertest_5["shouldReplicateCommittedTransaction()"]:::leafStyle
    REPLICATIONMANAGERTEST --> replicationmanagertest_5
    replicationmanagertest_6["shouldRetryReplication()"]:::leafStyle
    REPLICATIONMANAGERTEST --> replicationmanagertest_6
    replicationmanagertest_7["shouldSynchronizeReplicaState()"]:::leafStyle
    REPLICATIONMANAGERTEST --> replicationmanagertest_7
    replicationmanagertest_8["shouldSynchronizeMissingLogs()"]:::leafStyle
    REPLICATIONMANAGERTEST --> replicationmanagertest_8
    replicationmanagertest_9["shouldRejectReplicationWhenLeaderUnavailable()"]:::leafStyle
    REPLICATIONMANAGERTEST --> replicationmanagertest_9
    replicationmanagertest_10["shouldUpdateClusterLeader()"]:::leafStyle
    REPLICATIONMANAGERTEST --> replicationmanagertest_10

    %% ClusterNode methods
    clusternode_1["shouldCreateClusterNode()"]:::leafStyle
    CLUSTERNODE --> clusternode_1
    clusternode_2["shouldConnectToCluster()"]:::leafStyle
    CLUSTERNODE --> clusternode_2
    clusternode_3["shouldDisconnectFromCluster()"]:::leafStyle
    CLUSTERNODE --> clusternode_3
    clusternode_4["shouldUpdateNodeStatus()"]:::leafStyle
    CLUSTERNODE --> clusternode_4
    clusternode_5["shouldHeartbeat()"]:::leafStyle
    CLUSTERNODE --> clusternode_5
    clusternode_6["shouldRecoverNode()"]:::leafStyle
    CLUSTERNODE --> clusternode_6
    clusternode_7["shouldDetectNodeFailure()"]:::leafStyle
    CLUSTERNODE --> clusternode_7
    clusternode_8["shouldSynchronizeNodeState()"]:::leafStyle
    CLUSTERNODE --> clusternode_8

    %% =====================================================
    %% Styling
    %% =====================================================
    classDef rootStyle fill:#1d3557,stroke:#457b9d,stroke-width:3px,color:#fff,font-weight:bold,font-size:16px;
    classDef branchAdmin fill:#e1f5fe,stroke:#0288d1,stroke-width:2px,color:#01579b,font-weight:bold;
    classDef branchCatalog fill:#e8f5e9,stroke:#388e3c,stroke-width:2px,color:#1b5e20,font-weight:bold;
    classDef branchStorage fill:#e0f2f1,stroke:#00695c,stroke-width:2px,color:#004d40,font-weight:bold;
    classDef branchQuery fill:#fff3e0,stroke:#f57c00,stroke-width:2px,color:#e65100,font-weight:bold;
    classDef branchTx fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029,font-weight:bold;
    classDef leafStyle fill:#ffffff,stroke:#b0bec5,stroke-width:1px,color:#37474f;
```