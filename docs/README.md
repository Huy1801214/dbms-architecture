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

# Class Diagram for Database Server Module
```mermaid
classDiagram
direction LR

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
```
--- 

# Storage Engine Module

```mermaid
classDiagram
direction LR

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
```
--- 

# Query Processing Module

```mermaid
classDiagram
direction LR

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
```
---

# Transaction Management Module
```mermaid
classDiagram
direction LR

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
```
---

# Metadata Module
```mermaid
classDiagram
direction LR

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
```
--- 
# Security Module 
```mermaid
classDiagram
direction LR

class SecurityManager{
    +users
    +roles
    +policies

    +authenticate()
    +authorize()
    +grantPermission()
    +revokePermission()
}

class User

class Role

class Permission

SecurityManager --> User
SecurityManager --> Role

Role --> Permission
```
--- 

# Recovery Module 
```mermaid
classDiagram
direction LR

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
```
--- 

# Replication Module 
```mermaid
classDiagram
direction LR

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
```
--- 