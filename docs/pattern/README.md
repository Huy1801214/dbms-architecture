# Database Objects Feature mindmap
```mermaid
flowchart LR
    ROOT((Database Objects Features))

    %% =====================================================
    %% PRIORITY GROUPS
    %% =====================================================
    HIGH["High Priority — Mandatory"]
    MEDIUM["Medium Priority — Important / Necessary"]
    LOW["Low Priority — Supporting / Advanced"]

    ROOT --> HIGH
    ROOT --> MEDIUM
    ROOT --> LOW

    %% =====================================================
    %% HIGH-PRIORITY FEATURES
    %% =====================================================
    H1["1. Database Operational State Management"]
    H1P["State, Singleton"]

    H2["2. Schema Management"]
    H2P["None"]

    H3["3. Database Object Hierarchy and Structural Lifecycle"]
    H3P["Composite"]

    H4["4. Table Definition and Lifecycle"]
    H4P["Builder + Proxy"]

    H5["5. Column Definition and Data Type Management"]
    H5P["None"]

    H6["6. Constraint Definition and Validation"]
    H6P["Strategy + Chain of Responsibility"]

    H7["7. Table Data and Row Operations"]
    H7P["Template Method + Command + Bridge"]

    H8["8. Object Naming, Lookup and Uniqueness"]
    H8P["None"]

    HIGH --> H1
    HIGH --> H2
    HIGH --> H3
    HIGH --> H4
    HIGH --> H5
    HIGH --> H6
    HIGH --> H7
    HIGH --> H8

    H1 --> H1P
    H2 --> H2P
    H3 --> H3P
    H4 --> H4P
    H5 --> H5P
    H6 --> H6P
    H7 --> H7P
    H8 --> H8P

    %% =====================================================
    %% MEDIUM-PRIORITY FEATURES
    %% =====================================================
    M1["9. Index Definition and Management"]
    M1P["Strategy + Factory Method (Conditional)"]

    M2["10. View Management"]
    M2P["None / Proxy (Conditional)"]

    M3["11. Stored Procedure Management"]
    M3P["Command"]

    M4["12. Sequence Management"]
    M4P["None"]

    MEDIUM --> M1
    MEDIUM --> M2
    MEDIUM --> M3
    MEDIUM --> M4

    M1 --> M1P
    M2 --> M2P
    M3 --> M3P
    M4 --> M4P

    %% =====================================================
    %% LOW-PRIORITY FEATURES
    %% =====================================================
    L1["13. Schema Object Listing and Traversal"]
    L1P["Iterator"]

    L2["14. Database Object Metadata and DDL Export"]
    L2P["Visitor"]

    L3["15. Trigger Management"]
    L3P["Observer"]

    L4["16. Table Partition Management"]
    L4P["Strategy"]

    LOW --> L1
    LOW --> L2
    LOW --> L3
    LOW --> L4

    L1 --> L1P
    L2 --> L2P
    L3 --> L3P
    L4 --> L4P

    %% =====================================================
    %% STYLE ASSIGNMENTS
    %% =====================================================
    class ROOT rootStyle

    class HIGH,H1,H1P,H2,H2P,H3,H3P,H4,H4P highStyle
    class H5,H5P,H6,H6P,H7,H7P,H8,H8P highStyle

    class MEDIUM,M1,M1P,M2,M2P,M3,M3P,M4,M4P mediumStyle

    class LOW,L1,L1P,L2,L2P,L3,L3P,L4,L4P lowStyle

    %% =====================================================
    %% STYLE DEFINITIONS
    %% =====================================================
    classDef rootStyle fill:#1d3557,stroke:#457b9d,stroke-width:3px,color:#ffffff,font-weight:bold

    classDef highStyle fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#842029,font-weight:bold

    classDef mediumStyle fill:#fff8e1,stroke:#f9a825,stroke-width:2px,color:#664d03,font-weight:bold

    classDef lowStyle fill:#ffffff,stroke:#b0bec5,stroke-width:1px,color:#37474f
```
# Database Objects Feature and Design Pattern Analysis

# Database Objects class diagram 
```mermaid
classDiagram
direction TB

%% =====================================================
%% COMPOSITE COMPONENT
%% =====================================================

class DatabaseComponent {
    <<interface>>

    +getId() UUID
    +getName() String
    +getOwner() String
    +getQualifiedName() String
    +getLifecycleStatus() LifecycleStatus

    +rename(newName : String) void
    +drop(mode : DropMode) void
    +getChildren() List~DatabaseComponent~
}

%% =====================================================
%% STRUCTURAL LIFECYCLE TYPES
%% =====================================================

class LifecycleStatus {
    <<enumeration>>

    ACTIVE
    DROPPING
    DROPPED
}

class DropMode {
    <<enumeration>>

    RESTRICT
    CASCADE
}

%% =====================================================
%% DATABASE MANAGER
%% =====================================================

class DatabaseManager {
    -instance : DatabaseManager$
    -databasesById : Map~UUID, Database~
    -databaseIdsByName : Map~String, UUID~

    +getInstance() DatabaseManager$
    +createDatabase(request) Database
    +dropDatabase(databaseId : UUID, mode : DropMode) void

    +openDatabase(databaseId : UUID) void
    +closeDatabase(databaseId : UUID) void
    +renameDatabase(databaseId : UUID, newName : String) void

    +findDatabaseById(databaseId : UUID) Database
    +findDatabaseByName(name : String) Database
    +listAllDatabases() List~Database~

    -openDatabaseResources(database : Database) void
    -closeDatabaseResources(database : Database) void
}

%% =====================================================
%% DATABASE - STATE CONTEXT
%% =====================================================

class Database {
    -databaseId : UUID
    -name : String
    -owner : String
    -state : DatabaseState
    -lifecycleStatus : LifecycleStatus
    -schemas : List~Schema~

    +open() void
    +close() void
    +rename(newName : String) void
    +drop(mode : DropMode) void
    +getStatus() DatabaseStatus

    +addSchema(schema : Schema) void
    +removeSchema(schemaId : UUID) void
    +findSchema(name : String) Schema
    +listSchemas() List~Schema~

    +getId() UUID
    +getName() String
    +getOwner() String
    +getQualifiedName() String
    +getLifecycleStatus() LifecycleStatus
    +getChildren() List~DatabaseComponent~

    ~openingSucceeded() void
    ~openingFailed() void
    ~closingSucceeded() void
    ~closingFailed() void

    ~transitionTo(state : DatabaseState) void
    ~updateName(newName : String) void
    ~attachSchema(schema : Schema) void
    ~detachSchema(schemaId : UUID) void
    ~findSchemaInternal(name : String) Schema
    ~listSchemasInternal() List~Schema~

    -validateActive() void
    -validateDrop(mode : DropMode) void
    -dropChildren() void
    -markAsDropping() void
    -markAsDropped() void
}

%% =====================================================
%% DATABASE STATE
%% =====================================================

class DatabaseState {
    <<interface>>

    +open(database : Database) void
    +close(database : Database) void
    +rename(database : Database, newName : String) void

    +addSchema(database : Database, schema : Schema) void
    +removeSchema(database : Database, schemaId : UUID) void
    +ensureSchemaReadable(database : Database) void

    +openingSucceeded(database : Database) void
    +openingFailed(database : Database) void
    +closingSucceeded(database : Database) void
    +closingFailed(database : Database) void

    +getStatus() DatabaseStatus
}

%% =====================================================
%% CONCRETE DATABASE STATES
%% =====================================================

class OfflineState {
    +open(database : Database) void
    +close(database : Database) void
    +rename(database : Database, newName : String) void

    +addSchema(database : Database, schema : Schema) void
    +removeSchema(database : Database, schemaId : UUID) void
    +ensureSchemaReadable(database : Database) void

    +openingSucceeded(database : Database) void
    +openingFailed(database : Database) void
    +closingSucceeded(database : Database) void
    +closingFailed(database : Database) void

    +getStatus() DatabaseStatus
}

class OpeningState {
    +open(database : Database) void
    +close(database : Database) void
    +rename(database : Database, newName : String) void

    +addSchema(database : Database, schema : Schema) void
    +removeSchema(database : Database, schemaId : UUID) void
    +ensureSchemaReadable(database : Database) void

    +openingSucceeded(database : Database) void
    +openingFailed(database : Database) void
    +closingSucceeded(database : Database) void
    +closingFailed(database : Database) void

    +getStatus() DatabaseStatus
}

class OnlineState {
    +open(database : Database) void
    +close(database : Database) void
    +rename(database : Database, newName : String) void

    +addSchema(database : Database, schema : Schema) void
    +removeSchema(database : Database, schemaId : UUID) void
    +ensureSchemaReadable(database : Database) void

    +openingSucceeded(database : Database) void
    +openingFailed(database : Database) void
    +closingSucceeded(database : Database) void
    +closingFailed(database : Database) void

    +getStatus() DatabaseStatus
}

class ClosingState {
    +open(database : Database) void
    +close(database : Database) void
    +rename(database : Database, newName : String) void

    +addSchema(database : Database, schema : Schema) void
    +removeSchema(database : Database, schemaId : UUID) void
    +ensureSchemaReadable(database : Database) void

    +openingSucceeded(database : Database) void
    +openingFailed(database : Database) void
    +closingSucceeded(database : Database) void
    +closingFailed(database : Database) void

    +getStatus() DatabaseStatus
}

class DatabaseStatus {
    <<enumeration>>

    OFFLINE
    OPENING
    ONLINE
    CLOSING
}

%% =====================================================
%% SCHEMA
%% =====================================================

class Schema {
    -schemaId : UUID
    -databaseId : UUID
    -name : String
    -owner : String
    -lifecycleStatus : LifecycleStatus
    -objectsById : Map~UUID, SchemaObject~
    -objectIdsByName : Map~String, UUID~

    +addObject(object : SchemaObject) void
    +dropObject(objectId : UUID, mode : DropMode) void

    +findObjectById(objectId : UUID) SchemaObject
    +findObjectByName(name : String) SchemaObject
    +listObjects() List~SchemaObject~
    +containsObjectName(name : String) boolean

    +getId() UUID
    +getDatabaseId() UUID
    +getName() String
    +getOwner() String
    +getQualifiedName() String
    +getLifecycleStatus() LifecycleStatus

    +rename(newName : String) void
    +drop(mode : DropMode) void
    +getChildren() List~DatabaseComponent~

    ~removeObjectInternal(objectId : UUID) void

    -validateActive() void
    -validateDrop(mode : DropMode) void
    -validateUniqueId(objectId : UUID) void
    -validateUniqueName(name : String) void
    -validateOwnership(object : SchemaObject) void
    -normalizeName(name : String) String
    -dropChildren() void
    -markAsDropping() void
    -markAsDropped() void
}

%% =====================================================
%% COMMON SCHEMA OBJECT
%% =====================================================

class SchemaObject {
    <<abstract>>

    #objectId : UUID
    #name : String
    #owner : String
    #schemaId : UUID
    #lifecycleStatus : LifecycleStatus

    +getId() UUID
    +getSchemaId() UUID
    +getName() String
    +getOwner() String
    +getQualifiedName() String
    +getLifecycleStatus() LifecycleStatus

    +rename(newName : String) void
    +drop(mode : DropMode) void
    +getChildren() List~DatabaseComponent~

    #validateActive() void
    #markAsDropping() void
    #markAsDropped() void
}

%% =====================================================
%% TABLE
%% =====================================================

class Table {
    -engine : String
    -storageBackend : StorageBackend
    -validationChain : RowValidationHandler

    -columns : List~Column~
    -constraints : List~Constraint~
    -indexes : List~Index~
    -partitions : List~Partition~
    -triggers : List~Trigger~

    ~Table(objectId : UUID, name : String, owner : String, schemaId : UUID, engine : String, columns, constraints, indexes, partitions, triggers)

    +getEngine() String
    +getColumns() List~Column~
    +getConstraints() List~Constraint~
    +getIndexes() List~Index~
    +getPartitions() List~Partition~
    +getTriggers() List~Trigger~

    +addConstraint(constraint : Constraint, context : ConstraintDefinitionContext) void
    +dropConstraint(constraintId : UUID) void
    +findConstraintById(constraintId : UUID) Constraint
    +findConstraintByName(name : String) Constraint
    +validateConstraints(row : Row, context : ConstraintValidationContext) void

    +addIndex(index : Index, context : IndexDefinitionContext) void
    +dropIndex(indexId : UUID) void
    +findIndexById(indexId : UUID) Index
    +findIndexByName(name : String) Index

    +insertRow(row : Row) void
    +findRow(rowId : UUID) Row
    +updateRow(rowId : UUID, changes : RowChanges) Row
    +deleteRow(rowId : UUID) Row

    +insertIntoIndexes(row : Row, context : IndexOperationContext) void
    +updateIndexes(oldRow : Row, newRow : Row, context : IndexOperationContext) void
    +deleteFromIndexes(row : Row, context : IndexOperationContext) void
}

class TableMetadataProxy {
    -isLoaded : Boolean
    -metadataLoader : MetadataLoader

    +TableMetadataProxy(objectId : UUID, name : String, owner : String, schemaId : UUID)
    +getColumns() List~Column~
    +getConstraints() List~Constraint~
    +getIndexes() List~Index~
    +getPartitions() List~Partition~
    +getTriggers() List~Trigger~
    -lazyLoad() void
}

%% =====================================================
%% TABLE BUILDER
%% =====================================================

class TableBuilder {
    -objectId : UUID
    -name : String
    -owner : String
    -schemaId : UUID
    -engine : String

    -columns : List~Column~
    -constraints : List~Constraint~
    -indexes : List~Index~
    -partitions : List~Partition~
    -triggers : List~Trigger~

    +setObjectId(objectId : UUID) TableBuilder
    +setName(name : String) TableBuilder
    +setOwner(owner : String) TableBuilder
    +setSchemaId(schemaId : UUID) TableBuilder
    +setEngine(engine : String) TableBuilder

    +addColumn(column : Column) TableBuilder
    +addConstraint(constraint : Constraint) TableBuilder
    +addIndex(index : Index) TableBuilder
    +addPartition(partition : Partition) TableBuilder
    +addTrigger(trigger : Trigger) TableBuilder

    +build() Table

    -validateRequiredFields() void
    -validateColumns() void
    -validateConstraints() void
    -validateIndexes() void
    -validatePartitions() void
    -validateTriggers() void
}

class Column {
    -columnId : UUID
    -name : String
    -dataType : DataType
    -nullable : Boolean
    -defaultValue : Object

    +getId() UUID
    +getName() String
    +getDataType() DataType
}

class Constraint {
    <<abstract>>

    #constraintId : UUID
    #constraintName : String
    #constraintType : ConstraintType
    #tableId : UUID
    #columnIds : List~UUID~
    #status : ConstraintStatus

    +getId() UUID
    +getName() String
    +getType() ConstraintType
    +getTableId() UUID
    +getColumnIds() List~UUID~
    +getStatus() ConstraintStatus

    +enable() void
    +disable() void
    +markInvalid() void

    +validateDefinition(context : ConstraintDefinitionContext)* void
    +validate(row : Row, context : ConstraintValidationContext) void
    #doValidate(row : Row, context : ConstraintValidationContext)* void
}

class PrimaryKey {
    +validateDefinition(context : ConstraintDefinitionContext) void
    #doValidate(row : Row, context : ConstraintValidationContext) void
}

class ForeignKey {
    -referencedTableId : UUID
    -referencedColumnIds : List~UUID~
    -onDeleteAction : ReferentialAction
    -onUpdateAction : ReferentialAction

    +validateDefinition(context : ConstraintDefinitionContext) void
    #doValidate(row : Row, context : ConstraintValidationContext) void
}

class UniqueConstraint {
    +validateDefinition(context : ConstraintDefinitionContext) void
    #doValidate(row : Row, context : ConstraintValidationContext) void
}

class CheckConstraint {
    -expression : CheckExpression

    +validateDefinition(context : ConstraintDefinitionContext) void
    #doValidate(row : Row, context : ConstraintValidationContext) void
}

class ConstraintDefinitionContext {
    -tableId : UUID
    -columns : List~Column~
    -existingConstraints : List~Constraint~

    +hasColumn(columnId : UUID) boolean
    +hasPrimaryKey() boolean
    +hasConstraintName(name : String) boolean
    +referencedTableExists(tableId : UUID) boolean
    +referencedColumnExists(tableId : UUID, columnId : UUID) boolean
    +areTypesCompatible(sourceColumnId : UUID, referencedColumnId : UUID) boolean
}

class ConstraintValidationContext {
    -currentTableId : UUID
    -transactionId : UUID

    +exists(tableId : UUID, columnIds : List~UUID~, values : List~Object~) boolean
    +isUnique(tableId : UUID, columnIds : List~UUID~, values : List~Object~) boolean
}

class CheckExpression {
    <<interface>>

    +evaluate(row : Row) boolean
}

class ConstraintType {
    <<enumeration>>

    PRIMARY_KEY
    FOREIGN_KEY
    UNIQUE
    CHECK
}

class ConstraintStatus {
    <<enumeration>>

    ENABLED
    DISABLED
    INVALID
}

class ReferentialAction {
    <<enumeration>>

    NO_ACTION
    RESTRICT
    CASCADE
    SET_NULL
}

class Row {
    -rowId : UUID
    -values : Map~UUID, Object~

    +getId() UUID
    +getValue(columnId : UUID) Object
}

class RowChanges {
    -values : Map~UUID, Object~

    +getValue(columnId : UUID) Object
}

class DataType {
    <<enumeration>>
}

class Index {
    -indexId : UUID
    -name : String
    -tableId : UUID
    -columnIds : List~UUID~
    -unique : Boolean
    -status : IndexStatus
    -accessMethod : IndexAccessMethod

    +getId() UUID
    +getName() String
    +getTableId() UUID
    +getColumnIds() List~UUID~
    +getType() IndexType
    +getStatus() IndexStatus
    +isUnique() boolean

    +validateDefinition(context : IndexDefinitionContext) void

    +build(context : IndexOperationContext) void
    +rebuild(context : IndexOperationContext) void
    +enable() void
    +disable() void
    +markInvalid() void
    +drop() void

    +insertEntry(row : Row, context : IndexOperationContext) void
    +updateEntry(oldRow : Row, newRow : Row, context : IndexOperationContext) void
    +deleteEntry(row : Row, context : IndexOperationContext) void

    +search(key : IndexKey) List~UUID~
    +rangeSearch(fromKey : IndexKey, toKey : IndexKey) List~UUID~

    -extractKey(row : Row) IndexKey
    -validateUniqueKey(key : IndexKey) void
    -ensureActive() void
}

%% =====================================================
%% INDEX ACCESS METHOD - STRATEGY
%% =====================================================

class IndexAccessMethod {
    <<interface>>

    +getType() IndexType
    +build(context : IndexOperationContext) void

    +insert(key : IndexKey, rowId : UUID) void
    +delete(key : IndexKey, rowId : UUID) void

    +search(key : IndexKey) List~UUID~
    +supportsRangeSearch() boolean
    +rangeSearch(fromKey : IndexKey, toKey : IndexKey) List~UUID~
}

class BTreeIndexAccessMethod {
    +getType() IndexType
    +build(context : IndexOperationContext) void

    +insert(key : IndexKey, rowId : UUID) void
    +delete(key : IndexKey, rowId : UUID) void

    +search(key : IndexKey) List~UUID~
    +supportsRangeSearch() boolean
    +rangeSearch(fromKey : IndexKey, toKey : IndexKey) List~UUID~
}

class HashIndexAccessMethod {
    +getType() IndexType
    +build(context : IndexOperationContext) void

    +insert(key : IndexKey, rowId : UUID) void
    +delete(key : IndexKey, rowId : UUID) void

    +search(key : IndexKey) List~UUID~
    +supportsRangeSearch() boolean
    +rangeSearch(fromKey : IndexKey, toKey : IndexKey) List~UUID~
}

class BitmapIndexAccessMethod {
    +getType() IndexType
    +build(context : IndexOperationContext) void

    +insert(key : IndexKey, rowId : UUID) void
    +delete(key : IndexKey, rowId : UUID) void

    +search(key : IndexKey) List~UUID~
    +supportsRangeSearch() boolean
    +rangeSearch(fromKey : IndexKey, toKey : IndexKey) List~UUID~
}

%% =====================================================
%% INDEX DEFINITION AND OPERATION CONTEXTS
%% =====================================================

class IndexDefinitionContext {
    -tableId : UUID
    -columns : List~Column~
    -existingIndexes : List~Index~

    +hasColumn(columnId : UUID) boolean
    +hasIndexName(name : String) boolean
    +hasEquivalentIndex(columnIds : List~UUID~, type : IndexType) boolean
    +supportsType(columnId : UUID, type : IndexType) boolean
}

class IndexOperationContext {
    -tableId : UUID
    -transactionId : UUID

    +getTableId() UUID
    +getTransactionId() UUID
    +scanRows() List~Row~
}

%% =====================================================
%% INDEX SUPPORTING TYPES
%% =====================================================

class IndexKey {
    -values : List~Object~

    +getValues() List~Object~
}

class IndexType {
    <<enumeration>>

    BTREE
    HASH
    BITMAP
}

class IndexStatus {
    <<enumeration>>

    BUILDING
    ACTIVE
    DISABLED
    REBUILDING
    INVALID
    DROPPED
}

class Partition {
    -partitionId : UUID
    -name : String
    -partitionColumnId : UUID

    +getId() UUID
    +getName() String
    +getPartitionColumnId() UUID
}

class Trigger {
    -triggerId : UUID
    -name : String
    -eventType : String
    -body : String

    +getId() UUID
    +getName() String
    +getEventType() String
}

%% =====================================================
%% TABLE DATA COMMAND - COMMAND AND TEMPLATE METHOD
%% =====================================================

class TableDataCommandExecutor {
    +execute(command : TableDataCommand, context : DataOperationContext) DataOperationResult
}

class TableDataCommand {
    <<interface>>

    +execute(context : DataOperationContext) DataOperationResult
}

class AbstractTableDataCommand {
    <<abstract>>

    +execute(context : DataOperationContext) DataOperationResult

    #validateRequest(context : DataOperationContext) void
    #acquireLocks(context : DataOperationContext) void
    #validateConstraints(context : DataOperationContext) void
    #writeAheadLog(context : DataOperationContext) void
    #modifyRow(context : DataOperationContext)* void
    #updateIndexes(context : DataOperationContext) void
    #afterExecution(context : DataOperationContext) DataOperationResult
    #onFailure(context : DataOperationContext, error : Exception) void
    #releaseLocks(context : DataOperationContext) void
}

class InsertRowCommand {
    -row : Row

    +InsertRowCommand(row : Row)
    #validateRequest(context : DataOperationContext) void
    #writeAheadLog(context : DataOperationContext) void
    #modifyRow(context : DataOperationContext) void
    #updateIndexes(context : DataOperationContext) void
}

class UpdateRowCommand {
    -rowId : UUID
    -changes : RowChanges
    -oldRow : Row
    -updatedRow : Row

    +UpdateRowCommand(rowId : UUID, changes : RowChanges)
    #validateRequest(context : DataOperationContext) void
    #writeAheadLog(context : DataOperationContext) void
    #modifyRow(context : DataOperationContext) void
    #updateIndexes(context : DataOperationContext) void
}

class DeleteRowCommand {
    -rowId : UUID
    -deletedRow : Row

    +DeleteRowCommand(rowId : UUID)
    #validateRequest(context : DataOperationContext) void
    #writeAheadLog(context : DataOperationContext) void
    #modifyRow(context : DataOperationContext) void
    #updateIndexes(context : DataOperationContext) void
}

class DataOperationContext {
    -table : Table
    -transactionId : UUID
    -indexOperationContext : IndexOperationContext

    +getTable() Table
    +getTransactionId() UUID
    +getIndexOperationContext() IndexOperationContext
}

class DataOperationResult {
    -affectedRowCount : Long
    -generatedRowId : UUID
    -successful : Boolean

    +getAffectedRowCount() Long
    +getGeneratedRowId() UUID
    +isSuccessful() boolean
}

%% =====================================================
%% CONSTRAINT VALIDATION CHAIN - COR
%% =====================================================

class RowValidationHandler {
    <<abstract>>
    -next : RowValidationHandler

    +setNext(next : RowValidationHandler) RowValidationHandler
    +validate(row : Row, table : Table) void
    #check(row : Row, table : Table)* void
    #checkNext(row : Row, table : Table) void
}

class NullabilityValidator {
    #check(row : Row, table : Table) void
}

class UniqueValidator {
    #check(row : Row, table : Table) void
}

class ForeignKeyValidator {
    #check(row : Row, table : Table) void
}

%% =====================================================
%% STORAGE BACKEND
%% =====================================================

class StorageBackend {
    <<interface>>
    +writeRecord(data : byte[]) void
    +readRecord(id : UUID) byte[]
}

class RowStorageBackend {
    +writeRecord(data : byte[]) void
    +readRecord(id : UUID) byte[]
}

class ColumnStorageBackend {
    +writeRecord(data : byte[]) void
    +readRecord(id : UUID) byte[]
}

%% =====================================================
%% VIEW
%% =====================================================

class View {
    -queryDefinition : String

    +getQueryDefinition() String
    +updateDefinition(query : String) void
}

%% =====================================================
%% STORED PROCEDURE
%% =====================================================

class StoredProcedure {
    -body : String
    -parameters : List~ProcedureParameter~

    +addParameter(parameter : ProcedureParameter) void
    +removeParameter(name : String) void
    +getParameters() List~ProcedureParameter~
    +updateBody(body : String) void
}

class ProcedureParameter {
    -name : String
    -dataType : String
    -mode : String
    -position : Integer
    -defaultValue : Object
}

%% =====================================================
%% SEQUENCE
%% =====================================================

class Sequence {
    -currentValue : Long
    -incrementValue : Long
    -minimumValue : Long
    -maximumValue : Long
    -cycle : Boolean

    +nextValue() Long
}

%% =====================================================
%% DATABASE MANAGEMENT RELATIONSHIPS
%% =====================================================

DatabaseManager --> Database : creates and coordinates
DatabaseManager --> DropMode : selects

%% =====================================================
%% COMPOSITE AND STRUCTURAL LIFECYCLE RELATIONSHIPS
%% =====================================================

DatabaseComponent <|.. Database
DatabaseComponent <|.. Schema
DatabaseComponent <|.. SchemaObject

DatabaseComponent --> LifecycleStatus : exposes lifecycle
DatabaseComponent --> DropMode : uses

%% =====================================================
%% DATABASE STATE RELATIONSHIPS
%% =====================================================

Database --> DatabaseState : delegates behavior
DatabaseState --> DatabaseStatus : represents

DatabaseState <|.. OfflineState
DatabaseState <|.. OpeningState
DatabaseState <|.. OnlineState
DatabaseState <|.. ClosingState

%% =====================================================
%% VALID STATE TRANSITIONS
%% =====================================================

OfflineState ..> OpeningState : open
OpeningState ..> OnlineState : openingSucceeded
OpeningState ..> OfflineState : openingFailed

OnlineState ..> ClosingState : close
ClosingState ..> OfflineState : closingSucceeded
ClosingState ..> OnlineState : closingFailed

%% =====================================================
%% DATABASE OBJECT HIERARCHY
%% =====================================================

Database *--> "0..*" Schema : contains
Schema *--> "0..*" SchemaObject : contains

SchemaObject <|-- Table
Table <|-- TableMetadataProxy
SchemaObject <|-- View
SchemaObject <|-- StoredProcedure
SchemaObject <|-- Sequence

%% =====================================================
%% TABLE COMPONENTS
%% =====================================================

TableBuilder ..> Table : builds

TableBuilder --> "1..*" Column : collects
TableBuilder --> "0..*" Constraint : collects
TableBuilder --> "0..*" Index : collects
TableBuilder --> "0..*" Partition : collects
TableBuilder --> "0..*" Trigger : collects

Table *--> "1..*" Column : defines
Table *--> "0..*" Constraint : enforces
Table *--> "0..*" Index : owns and maintains
Table *--> "0..*" Partition : partitions
Table *--> "0..*" Trigger : registers

Partition --> Column : uses partition key

%% =====================================================
%% INDEX DEFINITION AND MANAGEMENT - STRATEGY
%% =====================================================

Index *--> IndexAccessMethod : delegates operations

IndexAccessMethod <|.. BTreeIndexAccessMethod
IndexAccessMethod <|.. HashIndexAccessMethod
IndexAccessMethod <|.. BitmapIndexAccessMethod

Table ..> IndexDefinitionContext : validates index with
Table ..> IndexOperationContext : supplies operation context

Index ..> IndexDefinitionContext : validates definition with
Index ..> IndexOperationContext : builds and maintains with

Index --> IndexType : identifies
Index --> IndexStatus : has lifecycle status
Index --> "1..*" Column : indexes

Index ..> Row : extracts key from
Index ..> IndexKey : creates and searches

IndexAccessMethod ..> IndexKey : organizes
IndexAccessMethod ..> IndexOperationContext : builds from

IndexDefinitionContext --> Column : resolves
IndexDefinitionContext --> Index : checks existing

%% =====================================================
%% CONSTRAINT STRATEGY
%% =====================================================

Constraint <|-- PrimaryKey
Constraint <|-- ForeignKey
Constraint <|-- UniqueConstraint
Constraint <|-- CheckConstraint

Constraint --> ConstraintType : identifies
Constraint --> ConstraintStatus : has status
Constraint --> "1..*" Column : applies to

Constraint ..> ConstraintDefinitionContext : validates definition with
ConstraintDefinitionContext --> Column : resolves
ConstraintDefinitionContext --> Constraint : checks existing

Table ..> Row : validates and manages
Table --> ConstraintValidationContext : supplies

Constraint ..> Row : validates
Constraint ..> ConstraintValidationContext : queries data through

ForeignKey --> ReferentialAction : defines behavior
ForeignKey ..> ConstraintValidationContext : checks referenced data

CheckConstraint *--> CheckExpression : owns
CheckExpression ..> Row : evaluates

Column --> DataType : uses

TableBuilder ..> ConstraintDefinitionContext : creates validation context

Table --> RowValidationHandler : delegates row validation
RowValidationHandler --> RowValidationHandler : next link (1 to 1)

RowValidationHandler <|-- NullabilityValidator
RowValidationHandler <|-- UniqueValidator
RowValidationHandler <|-- ForeignKeyValidator

%% =====================================================
%% TABLE DATA COMMAND AND TEMPLATE METHOD
%% =====================================================

TableDataCommand <|.. AbstractTableDataCommand

AbstractTableDataCommand <|-- InsertRowCommand
AbstractTableDataCommand <|-- UpdateRowCommand
AbstractTableDataCommand <|-- DeleteRowCommand

TableDataCommandExecutor --> TableDataCommand : invokes

AbstractTableDataCommand --> DataOperationContext : uses
AbstractTableDataCommand ..> DataOperationResult : returns

DataOperationContext --> Table : provides receiver
DataOperationContext --> IndexOperationContext : provides index context

InsertRowCommand *--> Row : contains candidate row
UpdateRowCommand *--> RowChanges : contains changes
UpdateRowCommand --> Row : keeps old and updated rows
DeleteRowCommand --> Row : keeps deleted row

Table ..> RowChanges : applies

Table --> StorageBackend : delegates physical operations (Bridge)
StorageBackend <|.. RowStorageBackend
StorageBackend <|.. ColumnStorageBackend

%% =====================================================
%% STORED PROCEDURE COMPONENTS
%% =====================================================

StoredProcedure *--> "0..*" ProcedureParameter : defines

%% =====================================================
%% STYLING
%% =====================================================

style DatabaseComponent fill:#fff3e0,stroke:#ef6c00,stroke-width:2px,color:#7f2704

style DatabaseManager fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style Database fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style Schema fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298

style DatabaseState fill:#fde8e8,stroke:#e84a5f,stroke-width:2px,color:#9b1c1c
style OfflineState fill:#fde8e8,stroke:#e84a5f,stroke-width:1px,color:#9b1c1c
style OpeningState fill:#fde8e8,stroke:#e84a5f,stroke-width:1px,color:#9b1c1c
style OnlineState fill:#fde8e8,stroke:#e84a5f,stroke-width:1px,color:#9b1c1c
style ClosingState fill:#fde8e8,stroke:#e84a5f,stroke-width:1px,color:#9b1c1c
style DatabaseStatus fill:#fde8e8,stroke:#e84a5f,stroke-width:2px,color:#9b1c1c

style SchemaObject fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Table fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style TableMetadataProxy fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px,color:#4a148c
style View fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style StoredProcedure fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Sequence fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132

style Column fill:#fff8e1,stroke:#f9a825,stroke-width:1px,color:#664d03
style Index fill:#fff8e1,stroke:#f9a825,stroke-width:2px,color:#664d03
style Partition fill:#fff8e1,stroke:#f9a825,stroke-width:1px,color:#664d03
style Trigger fill:#fff8e1,stroke:#f9a825,stroke-width:1px,color:#664d03

style TableBuilder fill:#ffe0b2,stroke:#f57c00,stroke-width:2px,color:#e65100

style IndexAccessMethod fill:#fff3e0,stroke:#ef6c00,stroke-width:2px,color:#7f2704
style BTreeIndexAccessMethod fill:#fff3e0,stroke:#ef6c00,stroke-width:1px,color:#7f2704
style HashIndexAccessMethod fill:#fff3e0,stroke:#ef6c00,stroke-width:1px,color:#7f2704
style BitmapIndexAccessMethod fill:#fff3e0,stroke:#ef6c00,stroke-width:1px,color:#7f2704

style IndexDefinitionContext fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style IndexOperationContext fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298

style IndexType fill:#f3e5f5,stroke:#7b1fa2,stroke-width:1px,color:#4a148c
style IndexStatus fill:#f3e5f5,stroke:#7b1fa2,stroke-width:1px,color:#4a148c
style IndexKey fill:#f3e5f5,stroke:#7b1fa2,stroke-width:1px,color:#4a148c

style Constraint fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px,color:#4a148c
style PrimaryKey fill:#f3e5f5,stroke:#7b1fa2,stroke-width:1px,color:#4a148c
style ForeignKey fill:#f3e5f5,stroke:#7b1fa2,stroke-width:1px,color:#4a148c
style UniqueConstraint fill:#f3e5f5,stroke:#7b1fa2,stroke-width:1px,color:#4a148c
style CheckConstraint fill:#f3e5f5,stroke:#7b1fa2,stroke-width:1px,color:#4a148c

style ConstraintDefinitionContext fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style ConstraintValidationContext fill:#e0f2f1,stroke:#009688,stroke-width:1px,color:#004d40

style RowValidationHandler fill:#ffe0b2,stroke:#f57c00,stroke-width:2px,color:#e65100
style NullabilityValidator fill:#ffe0b2,stroke:#f57c00,stroke-width:1px,color:#e65100
style UniqueValidator fill:#ffe0b2,stroke:#f57c00,stroke-width:1px,color:#e65100
style ForeignKeyValidator fill:#ffe0b2,stroke:#f57c00,stroke-width:1px,color:#e65100

style CheckExpression fill:#fff3e0,stroke:#ef6c00,stroke-width:2px,color:#7f2704

style TableDataCommandExecutor fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298

style TableDataCommand fill:#fff3e0,stroke:#ef6c00,stroke-width:2px,color:#7f2704
style AbstractTableDataCommand fill:#fff3e0,stroke:#ef6c00,stroke-width:2px,color:#7f2704

style InsertRowCommand fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style UpdateRowCommand fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style DeleteRowCommand fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132

style DataOperationContext fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px,color:#4a148c
style DataOperationResult fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px,color:#4a148c

style ConstraintType fill:#fff8e1,stroke:#f9a825,stroke-width:1px,color:#664d03
style ConstraintStatus fill:#fff8e1,stroke:#f9a825,stroke-width:1px,color:#664d03
style ReferentialAction fill:#fff8e1,stroke:#f9a825,stroke-width:1px,color:#664d03

style Row fill:#e0f2f1,stroke:#009688,stroke-width:1px,color:#004d40
style RowChanges fill:#e0f2f1,stroke:#009688,stroke-width:1px,color:#004d40
style DataType fill:#e0f2f1,stroke:#009688,stroke-width:1px,color:#004d40

style ProcedureParameter fill:#f3e5f5,stroke:#7b1fa2,stroke-width:1px,color:#4a148c

style LifecycleStatus fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px,color:#4a148c
style DropMode fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px,color:#4a148c

style StorageBackend fill:#e0f2f1,stroke:#00695c,stroke-width:2px,color:#004d40
style RowStorageBackend fill:#e0f2f1,stroke:#00695c,stroke-width:1px,color:#004d40
style ColumnStorageBackend fill:#e0f2f1,stroke:#00695c,stroke-width:1px,color:#004d40
```
# 1. Database Operational State Management
## Using State, Singleton Pattern

### 1.1 Class Diagram
```mermaid
classDiagram
direction TB

%% =====================================================
%% Lifecycle Coordinator
%% =====================================================

class DatabaseManager {
    -instance : DatabaseManager$
    -databasesById : Map~UUID, Database~
    -databaseIdsByName : Map~String, UUID~

    +getInstance() DatabaseManager$
    +createDatabase(request) Database
    +dropDatabase(databaseId : UUID) void

    +openDatabase(databaseId : UUID) void
    +closeDatabase(databaseId : UUID) void
    +renameDatabase(databaseId : UUID, newName : String) void

    +findDatabaseById(databaseId : UUID) Database
    +findDatabaseByName(name : String) Database
    +listAllDatabases() List~Database~

    -openDatabaseResources(database : Database) void
    -closeDatabaseResources(database : Database) void
}

%% =====================================================
%% Database Component
%% =====================================================

class DatabaseComponent {
    <<interface>>

    +getId() UUID
    +getName() String
    +getOwner() String
    +getQualifiedName() String
}

%% =====================================================
%% Context
%% =====================================================

class Database {
    -databaseId : UUID
    -name : String
    -owner : String
    -state : DatabaseState
    -schemas : List~Schema~

    +open() void
    +close() void
    +rename(newName : String) void

    +addSchema(schema : Schema) void
    +removeSchema(schemaId : UUID) void
    +findSchema(name : String) Schema
    +listSchemas() List~Schema~

    +getStatus() DatabaseStatus

    +getId() UUID
    +getName() String
    +getOwner() String
    +getQualifiedName() String

    ~openingSucceeded() void
    ~openingFailed() void
    ~closingSucceeded() void
    ~closingFailed() void

    ~transitionTo(state : DatabaseState) void
    ~updateName(newName : String) void
    ~attachSchema(schema : Schema) void
    ~detachSchema(schemaId : UUID) void
    ~findSchemaInternal(name : String) Schema
    ~listSchemasInternal() List~Schema~
}

%% =====================================================
%% State Interface
%% =====================================================

class DatabaseState {
    <<interface>>

    +open(database : Database) void
    +close(database : Database) void
    +rename(database : Database, newName : String) void

    +addSchema(database : Database, schema : Schema) void
    +removeSchema(database : Database, schemaId : UUID) void
    +ensureSchemaReadable(database : Database) void

    +openingSucceeded(database : Database) void
    +openingFailed(database : Database) void
    +closingSucceeded(database : Database) void
    +closingFailed(database : Database) void

    +getStatus() DatabaseStatus
}

%% =====================================================
%% Concrete States
%% =====================================================

class OfflineState {
    +open(database : Database) void
    +close(database : Database) void
    +rename(database : Database, newName : String) void
    +addSchema(database : Database, schema : Schema) void
    +removeSchema(database : Database, schemaId : UUID) void
    +ensureSchemaReadable(database : Database) void
    +getStatus() DatabaseStatus
}

class OpeningState {
    +open(database : Database) void
    +close(database : Database) void
    +rename(database : Database, newName : String) void
    +addSchema(database : Database, schema : Schema) void
    +removeSchema(database : Database, schemaId : UUID) void
    +ensureSchemaReadable(database : Database) void

    +openingSucceeded(database : Database) void
    +openingFailed(database : Database) void

    +getStatus() DatabaseStatus
}

class OnlineState {
    +open(database : Database) void
    +close(database : Database) void
    +rename(database : Database, newName : String) void
    +addSchema(database : Database, schema : Schema) void
    +removeSchema(database : Database, schemaId : UUID) void
    +ensureSchemaReadable(database : Database) void
    +getStatus() DatabaseStatus
}

class ClosingState {
    +open(database : Database) void
    +close(database : Database) void
    +rename(database : Database, newName : String) void
    +addSchema(database : Database, schema : Schema) void
    +removeSchema(database : Database, schemaId : UUID) void
    +ensureSchemaReadable(database : Database) void

    +closingSucceeded(database : Database) void
    +closingFailed(database : Database) void

    +getStatus() DatabaseStatus
}

%% =====================================================
%% Status
%% =====================================================

class DatabaseStatus {
    <<enumeration>>

    OFFLINE
    OPENING
    ONLINE
    CLOSING
}

%% =====================================================
%% Schema
%% =====================================================

class Schema {
    -schemaId : UUID
    -name : String
    -owner : String
}

%% =====================================================
%% Main Relationships
%% =====================================================

DatabaseManager --> Database : coordinates lifecycle

DatabaseComponent <|.. Database
Database *--> "0..*" Schema : contains

Database --> DatabaseState : delegates behavior
DatabaseState --> DatabaseStatus : represents

DatabaseState <|.. OfflineState
DatabaseState <|.. OpeningState
DatabaseState <|.. OnlineState
DatabaseState <|.. ClosingState

%% =====================================================
%% Valid State Transitions
%% =====================================================

OfflineState ..> OpeningState : open
OpeningState ..> OnlineState : openingSucceeded
OpeningState ..> OfflineState : openingFailed

OnlineState ..> ClosingState : close
ClosingState ..> OfflineState : closingSucceeded
ClosingState ..> OnlineState : closingFailed

%% =====================================================
%% Styling
%% =====================================================

style DatabaseComponent fill:#fff3e0,stroke:#ef6c00,stroke-width:2px,color:#7f2704

style DatabaseManager fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style Database fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style Schema fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298

style DatabaseState fill:#fde8e8,stroke:#e84a5f,stroke-width:2px,color:#9b1c1c
style OfflineState fill:#fde8e8,stroke:#e84a5f,stroke-width:1px,color:#9b1c1c
style OpeningState fill:#fde8e8,stroke:#e84a5f,stroke-width:1px,color:#9b1c1c
style OnlineState fill:#fde8e8,stroke:#e84a5f,stroke-width:1px,color:#9b1c1c
style ClosingState fill:#fde8e8,stroke:#e84a5f,stroke-width:1px,color:#9b1c1c
style DatabaseStatus fill:#fde8e8,stroke:#e84a5f,stroke-width:2px,color:#9b1c1c
```

### 1.2 Sequence Diagram for shouldRejectOpenBaseOnCurrentState()
```mermaid
sequenceDiagram
    autonumber

    participant DM as DatabaseManager
    participant DB as Database 
    participant OPENING as state OpeningState
    participant ONLINE as state OnlineState
    participant CLOSING as state ClosingState

    Note over DM: openDatabase(databaseId) is requested

    DM ->> DM: findDatabaseById(databaseId)
    Note over DM,DB: Target Database found

    alt Current state is OpeningState
        Note over DB,OPENING: DB.state references OpeningState

        DM ->> DB: open()
        activate DB

        DB ->> OPENING: open(DB)
        activate OPENING

        OPENING -->> DB: throw InvalidStateException
        deactivate OPENING

        DB -->> DM: Database is already opening
        deactivate DB

        Note over DB: State remains OpeningState

    else Current state is OnlineState
        Note over DB,ONLINE: DB.state references OnlineState

        DM ->> DB: open()
        activate DB

        DB ->> ONLINE: open(DB)
        activate ONLINE

        ONLINE -->> DB: throw InvalidStateException
        deactivate ONLINE

        DB -->> DM: Database is already online
        deactivate DB

        Note over DB: State remains OnlineState

    else Current state is ClosingState
        Note over DB,CLOSING: DB.state references ClosingState

        DM ->> DB: open()
        activate DB

        DB ->> CLOSING: open(DB)
        activate CLOSING

        CLOSING -->> DB: throw InvalidStateException
        deactivate CLOSING

        DB -->> DM: Database is currently closing
        deactivate DB

        Note over DB: State remains ClosingState
    end
```

### 1.3 Sequence Diagram for shouldReturnSameInstanceOnMultipleCalls()
```mermaid
sequenceDiagram
    autonumber

    participant A as Client A
    participant B as Client B
    participant DM as "DatabaseManager (Class)"

    Note over A,B: 1. First invocation (Lazy Initialization)
    A ->> DM: getInstance()
    activate DM
    DM ->> DM: Check if instance == null (true)
    create participant INST as "instance : DatabaseManager"
    DM ->> INST: new DatabaseManager()
    Note over INST: Private Constructor executed
    DM -->> A: Return created instance
    deactivate DM

    Note over A,B: 2. Subsequent invocation (Reuse cached instance)
    B ->> DM: getInstance()
    activate DM
    DM ->> DM: Check if instance == null (false)
    DM -->> B: Return cached instance
    deactivate DM

    Note over A,B: Result: Both Client A and Client B reference the exact same instance
```

### 1.4 Code Example for State Pattern 

#### State Interface and Enum State
```java
public enum DatabaseStatus {
    OFFLINE, OPENING, ONLINE, CLOSING
}

public interface DatabaseState {

    void open(Database database);
    
    void close(Database database);
    
    void rename(Database database, String newName);
    
    DatabaseStatus getStatus();
}
```

#### Concrete State Classes
```java
public class OfflineState implements DatabaseState {

    @Override
    public void open(Database database) {
        database.validateNewName();
        database.initialize();
        database.changeState(new OpeningState());
    }

    @Override
    public void close(Database database) {
        
    }

    @Override
    public void rename(Database database, String newName) {
        
    }

    @Override
    public DatabaseStatus getStatus() {
        return DatabaseStatus.OFFLINE;
    }
}

public class OpeningState implements DatabaseState {

    @Override
    public void open(Database database) {
        
    }

    @Override
    public void close(Database database) {
        
    }

    @Override
    public void rename(Database database, String newName) {
        
    }

    @Override
    public DatabaseStatus getStatus() {
        return DatabaseStatus.OPENING;
    }
}

public class OnlineState implements DatabaseState {

    @Override
    public void open(Database database) {
        
    }

    @Override
    public void close(Database database) {
        
    }

    @Override
    public void rename(Database database, String newName) {
        
    }

    @Override
    public DatabaseStatus getStatus() {
        return DatabaseStatus.ONLINE;
    }
}

public class ClosingState implements DatabaseState {

    @Override
    public void open(Database database) {
       
    }

    @Override
    public void close(Database database) {
       
    }

    @Override
    public void rename(Database database, String newName) {
        
    }

    @Override
    public DatabaseStatus getStatus() {
        return DatabaseStatus.CLOSING;
    }
}
```

#### Context
```java
public class Database {
    private DatabaseState state;
    private String name;
    private String owner;
    private UUID databaseId;
    private List<Schema> schemas;

    public Database(String name, String owner) {
        this.name = name;
        this.owner = owner;
        this.state = new OfflineState();
        this.schemas = new ArrayList<>();
    }

    public void open() {
        state.open(this);
    }

    public void close() {
        state.close(this);
    }

    public void rename(String newName) {
        state.rename(this, newName);
    }

    public void changeState(DatabaseState state) {
        this.state = state;
    }

    public DatabaseStatus getStatus() {
        return state.getStatus();
    }
}
```

#### Client 
```java
public class DatabaseManager() {
    private Map<UUID, Database> databases = new HashMap<>();
    private StorageEngine storageEngine;
    private CatalogManager catalogManager;

    public DatabaseManager(StorageEngine storageEngine, CatalogManager catalogManager) {
        this.storageEngine = storageEngine;
        this.catalogManager = catalogManager;
    }

    public Database openDatabase(UUID databaseId) {
        return null;
    }   
}
```

---

### 1.5 Code Example for Singleton Pattern

#### Singleton Class
```java
package dbms.server;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import dbms.common.entities.Database;

public class DatabaseManager {
    private static volatile DatabaseManager instance;
    private final Map<UUID, Database> databasesById = new HashMap<>();
    private final Map<String, UUID> databaseIdsByName = new HashMap<>();

    private DatabaseManager() {
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
}
```

#### Client Code
```java
package dbms.server;

// Client A
DatabaseManager manager1 = DatabaseManager.getInstance();
Database db1 = manager1.createDatabase("StudentDB", "admin");

// Client B
DatabaseManager manager2 = DatabaseManager.getInstance();
Database db2 = manager2.createDatabase("SchoolDB", "admin");

// Verify both clients reference the same instance
System.out.println(manager1 == manager2);  // true
System.out.println(db1.getDatabaseId() != db2.getDatabaseId());  // true (different databases)
```

---
# 2. Schema Management
## Standard Domain Entity (No Pattern)

---

# 3. Database Object Hierarchy and Structural Lifecycle
## Using Composite Pattern

### 3.1 Class Diagram
```mermaid
classDiagram
direction TB

%% =====================================================
%% Root Client
%% =====================================================

class DatabaseManager {
    -databasesById : Map~UUID, Database~
    -databaseIdsByName : Map~String, UUID~

    +createDatabase(request) Database
    +dropDatabase(databaseId : UUID, mode : DropMode) void
    +renameDatabase(databaseId : UUID, newName : String) void

    +findDatabaseById(databaseId : UUID) Database
    +findDatabaseByName(name : String) Database
    +listAllDatabases() List~Database~
}

%% =====================================================
%% Composite Component
%% =====================================================

class DatabaseComponent {
    <<interface>>

    +getId() UUID
    +getName() String
    +getOwner() String
    +getQualifiedName() String
    +getLifecycleStatus() LifecycleStatus

    +rename(newName : String) void
    +drop(mode : DropMode) void
    +getChildren() List~DatabaseComponent~
}

%% =====================================================
%% Structural Lifecycle Types
%% =====================================================

class LifecycleStatus {
    <<enumeration>>

    ACTIVE
    DROPPING
    DROPPED
}

class DropMode {
    <<enumeration>>

    RESTRICT
    CASCADE
}

%% =====================================================
%% Root Composite: Database
%% =====================================================

class Database {
    -databaseId : UUID
    -name : String
    -owner : String
    -lifecycleStatus : LifecycleStatus
    -schemas : List~Schema~

    +addSchema(schema : Schema) void
    +removeSchema(schemaId : UUID) void
    +findSchema(name : String) Schema
    +listSchemas() List~Schema~

    +getId() UUID
    +getName() String
    +getOwner() String
    +getQualifiedName() String
    +getLifecycleStatus() LifecycleStatus

    +rename(newName : String) void
    +drop(mode : DropMode) void
    +getChildren() List~DatabaseComponent~

    -validateActive() void
    -validateDrop(mode : DropMode) void
    -dropChildren() void
    -markAsDropping() void
    -markAsDropped() void
}

%% =====================================================
%% Child Composite: Schema
%% =====================================================

class Schema {
    -schemaId : UUID
    -name : String
    -owner : String
    -lifecycleStatus : LifecycleStatus
    -objects : List~SchemaObject~

    +addObject(object : SchemaObject) void
    +removeObject(objectId : UUID) void
    +findObject(name : String) SchemaObject
    +listObjects() List~SchemaObject~

    +getId() UUID
    +getName() String
    +getOwner() String
    +getQualifiedName() String
    +getLifecycleStatus() LifecycleStatus

    +rename(newName : String) void
    +drop(mode : DropMode) void
    +getChildren() List~DatabaseComponent~

    -validateActive() void
    -validateDrop(mode : DropMode) void
    -dropChildren() void
    -markAsDropping() void
    -markAsDropped() void
}

%% =====================================================
%% Abstract Leaf
%% =====================================================

class SchemaObject {
    <<abstract>>

    #objectId : UUID
    #name : String
    #owner : String
    #schemaId : UUID
    #lifecycleStatus : LifecycleStatus

    +getId() UUID
    +getName() String
    +getOwner() String
    +getQualifiedName() String
    +getLifecycleStatus() LifecycleStatus

    +rename(newName : String) void
    +drop(mode : DropMode) void
    +getChildren() List~DatabaseComponent~

    #validateActive() void
    #markAsDropping() void
    #markAsDropped() void
}

%% =====================================================
%% Concrete Leaves
%% =====================================================

class Table

class View

class StoredProcedure

class Sequence

%% =====================================================
%% Composite Implementation
%% =====================================================

DatabaseComponent <|.. Database
DatabaseComponent <|.. Schema
DatabaseComponent <|.. SchemaObject

SchemaObject <|-- Table
SchemaObject <|-- View
SchemaObject <|-- StoredProcedure
SchemaObject <|-- Sequence

Database *--> "0..*" Schema : contains
Schema *--> "0..*" SchemaObject : contains

%% =====================================================
%% Root Lifecycle Management
%% =====================================================

DatabaseManager --> Database : manages root lifecycle
DatabaseManager --> DropMode : selects

%% =====================================================
%% Structural Lifecycle Relationships
%% =====================================================

DatabaseComponent --> LifecycleStatus : exposes lifecycle
DatabaseComponent --> DropMode : uses

%% =====================================================
%% Styling
%% =====================================================

style DatabaseComponent fill:#fff3e0,stroke:#ef6c00,stroke-width:2px,color:#7f2704

style DatabaseManager fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style Database fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style Schema fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298

style SchemaObject fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Table fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style View fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style StoredProcedure fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Sequence fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132

style LifecycleStatus fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px,color:#4a148c
style DropMode fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px,color:#4a148c
```

### 3.2 Sequence Diagram dropDatabaseHierarchyWithCascade()
```mermaid
sequenceDiagram
    autonumber

    participant DM as DatabaseManager
    participant DB as Database
    participant S as Schema
    participant T as Table

    Note over DB,T: Initial hierarchy: Database → Schema → Table
    Note over DB,T: All components have ACTIVE status

    DM ->> DM: findDatabaseById(databaseId)
    DM ->> DB: drop(CASCADE)
    activate DB

    DB ->> DB: lifecycleStatus = DROPPING

    Note over DB,T: Database propagates the same operation to its child

    DB ->> S: drop(CASCADE)
    activate S

    S ->> S: lifecycleStatus = DROPPING

    Note over S,T: Schema propagates the same operation to its leaf

    S ->> T: drop(CASCADE)
    activate T

    T ->> T: lifecycleStatus = DROPPING
    T ->> T: release table metadata
    T ->> T: lifecycleStatus = DROPPED

    T -->> S: drop completed
    deactivate T

    S ->> S: objects.remove(table)
    S ->> S: lifecycleStatus = DROPPED

    S -->> DB: drop completed
    deactivate S

    DB ->> DB: schemas.remove(schema)
    DB ->> DB: lifecycleStatus = DROPPED

    DB -->> DM: drop completed
    deactivate DB

    DM ->> DM: databases.remove(databaseId)

    Note over DB,T: Entire composite hierarchy is now DROPPED
```

### 3.3 Code Example
### Component
```java 
public interface DatabaseComponent {
    UUID getId();
    String getName();
    String getOwner();
    String getQualifiedName();
    LifecycleStatus getLifecycleStatus();
    void rename(String newName);
    void drop(DropMode mode);
    List<DatabaseComponent> getChildren();
}
```
### Composite
```java
public class Schema implements DatabaseComponent {
    private UUID schemaId;
    private String name;
    private String owner;
    private LifecycleStatus lifecycleStatus;
    private List<SchemaObject> objects;

    public Schema(UUID schemaId, String name, String owner) {
        this.schemaId = schemaId;
        this.name = name;
        this.owner = owner;
        this.lifecycleStatus = LifecycleStatus.ACTIVE;
        this.objects = new ArrayList<>();
    }
    @Override
    public UUID getId() {
        return schemaId;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getOwner() {
        return owner;
    }
    @Override
    public String getQualifiedName() {
        return name;
    }
    @Override
    public LifecycleStatus getLifecycleStatus() {
        return lifecycleStatus;
    }
    public void addObject(SchemaObject object) {
        
    }
    public void removeObject(UUID objectId) {
        
    }
    @Override
    public void rename(String newName) {
        
    }
    @Override
    public void drop(DropMode mode) {
        
    }
    @Override
    public List<DatabaseComponent> getChildren() {
        return null;
    }
    private void dropChildren(DropMode mode) {
        
    }
    private void markAsDropping() {
        
    }
    private void markAsDropped() {
        
    }
}

public class Database implements DatabaseComponent {
    private UUID databaseId;
    private String name;
    private String owner;
    private LifecycleStatus lifecycleStatus;
    private List<Schema> schemas;

    public Database(UUID databaseId, String name, String owner) {
        this.databaseId = databaseId;
        this.name = name;
        this.owner = owner;
        this.lifecycleStatus = LifecycleStatus.ACTIVE;
        this.schemas = new ArrayList<>();
    }
    @Override
    public UUID getId() {
        return databaseId;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getOwner() {
        return owner;
    }
    @Override
    public String getQualifiedName() {
        return name;
    }
    @Override
    public LifecycleStatus getLifecycleStatus() {
        return lifecycleStatus;
    }
    public void addSchema(Schema schema) {
        
    }
    public void removeSchema(UUID schemaId) {
        
    }
    @Override
    public void rename(String newName) {
        
    }
    @Override
    public void drop(DropMode mode) {
        
    }
    @Override
    public List<DatabaseComponent> getChildren() {
        return null;
    }
    private void dropChildren(DropMode mode) {
        
    }
    private void markAsDropping() {
        
    }
    private void markAsDropped() {
        
    }
}

```
### Leaf
```java
public abstract class SchemaObject implements DatabaseComponent {
    protected UUID objectId;
    protected String name;
    protected String owner;
    protected UUID schemaId;
    protected LifecycleStatus lifecycleStatus;

    public SchemaObject(UUID objectId, String name, String owner, UUID schemaId) {
        this.objectId = objectId;
        this.name = name;
        this.owner = owner;
        this.schemaId = schemaId;
        this.lifecycleStatus = LifecycleStatus.ACTIVE;
    }
    @Override
    public UUID getId() {
        return objectId;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getOwner() {
        return owner;
    }
    @Override
    public LifecycleStatus getLifecycleStatus() {
        return lifecycleStatus;
    }
    @Override
    public void rename(String newName) {
        
    }
    @Override
    public void drop(DropMode mode) {
        
    }
    @Override
    public List<DatabaseComponent> getChildren() {
        return null;
    }
    protected void markAsDropping() {
        
    }
    protected void markAsDropped() {
        
    }
}

public class Table extends SchemaObject {
    private String engine;
    public Table(UUID objectId, String name, String owner, UUID schemaId, String engine) {
        super(objectId, name, owner, schemaId);
        this.engine = engine;
    }
    @Override
    public String getQualifiedName() {
        return name;
    }
    @Override
    protected void releaseMetadata() {
        
    }
}
public class View extends SchemaObject {
    private String queryDefinition;
    public View(UUID objectId, String name, String owner, UUID schemaId, String queryDefinition) {
        super(objectId, name, owner, schemaId);
        this.queryDefinition = queryDefinition;
    }
    @Override
    public String getQualifiedName() {
        return name;
    }
    @Override
    protected void releaseMetadata() {
        
    }
}
public class StoredProcedure extends SchemaObject {
    public StoredProcedure(UUID objectId, String name, String owner, UUID schemaId) {
        super(objectId, name, owner, schemaId);
    }
    @Override
    public String getQualifiedName() {
        return name;
    }
    @Override
    protected void releaseMetadata() {
        
    }
}
public class Sequence extends SchemaObject {
    public Sequence(UUID objectId, String name, String owner, UUID schemaId) {
        super(objectId, name, owner, schemaId);
    }
    @Override
    public String getQualifiedName() {
        return name;
    }
    @Override
    protected void releaseMetadata() {
       
    }
}
```

--- 

# 4. Table Definition and Lifecycle Management
## Using Builder & Proxy Pattern

### 4.1 Class Diagram
```mermaid
classDiagram
direction TB

%% =====================================================
%% Schema Object Lifecycle
%% =====================================================

class SchemaObject {
    <<abstract>>

    #objectId : UUID
    #name : String
    #owner : String
    #schemaId : UUID
    #lifecycleStatus : LifecycleStatus

    +getId() UUID
    +getSchemaId() UUID
    +getName() String
    +getOwner() String
    +getQualifiedName() String
    +getLifecycleStatus() LifecycleStatus

    +rename(newName : String) void
    +drop(mode : DropMode) void

    #validateActive() void
    #markAsDropping() void
    #markAsDropped() void
}

class LifecycleStatus {
    <<enumeration>>

    ACTIVE
    DROPPING
    DROPPED
}

class DropMode {
    <<enumeration>>

    RESTRICT
    CASCADE
}

%% =====================================================
%% Product (Subject)
%% =====================================================

class Table {
    -engine : String

    -columns : List~Column~
    -constraints : List~Constraint~
    -indexes : List~Index~
    -partitions : List~Partition~
    -triggers : List~Trigger~

    ~Table(objectId : UUID, name : String, owner : String, schemaId : UUID, engine : String, columns, constraints, indexes, partitions, triggers)

    +getEngine() String
    +getColumns() List~Column~
    +getConstraints() List~Constraint~
    +getIndexes() List~Index~
    +getPartitions() List~Partition~
    +getTriggers() List~Trigger~
}

%% =====================================================
%% Proxy Product (Virtual Proxy via Inheritance)
%% =====================================================

class TableMetadataProxy {
    -isLoaded : Boolean
    -metadataLoader : MetadataLoader

    +TableMetadataProxy(objectId : UUID, name : String, owner : String, schemaId : UUID)
    +getColumns() List~Column~
    +getConstraints() List~Constraint~
    +getIndexes() List~Index~
    +getPartitions() List~Partition~
    +getTriggers() List~Trigger~
    -lazyLoad() void
}

%% =====================================================
%% Simplified Builder
%% =====================================================

class TableBuilder {
    -objectId : UUID
    -name : String
    -owner : String
    -schemaId : UUID
    -engine : String

    -columns : List~Column~
    -constraints : List~Constraint~
    -indexes : List~Index~
    -partitions : List~Partition~
    -triggers : List~Trigger~

    +setObjectId(objectId : UUID) TableBuilder
    +setName(name : String) TableBuilder
    +setOwner(owner : String) TableBuilder
    +setSchemaId(schemaId : UUID) TableBuilder
    +setEngine(engine : String) TableBuilder

    +addColumn(column : Column) TableBuilder
    +addConstraint(constraint : Constraint) TableBuilder
    +addIndex(index : Index) TableBuilder
    +addPartition(partition : Partition) TableBuilder
    +addTrigger(trigger : Trigger) TableBuilder

    +build() Table

    -validateRequiredFields() void
    -validateColumns() void
    -validateConstraints() void
    -validateIndexes() void
    -validatePartitions() void
    -validateTriggers() void
}

%% =====================================================
%% Table Components
%% =====================================================

class Column {
    -columnId : UUID
    -name : String
    -dataType : String
    -nullable : Boolean
    -defaultValue : Object

    +getId() UUID
    +getName() String
    +getDataType() String
}

class Constraint {
    -constraintId : UUID
    -name : String
    -columnIds : List~UUID~

    +getId() UUID
    +getName() String
    +getColumnIds() List~UUID~
}

class Index {
    -indexId : UUID
    -name : String
    -columnIds : List~UUID~
    -unique : Boolean

    +getId() UUID
    +getName() String
    +getColumnIds() List~UUID~
}

class Partition {
    -partitionId : UUID
    -name : String
    -partitionColumnId : UUID

    +getId() UUID
    +getName() String
    +getPartitionColumnId() UUID
}

class Trigger {
    -triggerId : UUID
    -name : String
    -eventType : String
    -body : String

    +getId() UUID
    +getName() String
    +getEventType() String
}

%% =====================================================
%% Inheritance and Lifecycle
%% =====================================================

SchemaObject <|-- Table
Table <|-- TableMetadataProxy

SchemaObject --> LifecycleStatus : has
SchemaObject --> DropMode : uses

%% =====================================================
%% Builder Relationships
%% =====================================================

TableBuilder ..> Table : builds

TableBuilder --> "1..*" Column : collects
TableBuilder --> "0..*" Constraint : collects
TableBuilder --> "0..*" Index : collects
TableBuilder --> "0..*" Partition : collects
TableBuilder --> "0..*" Trigger : collects

%% =====================================================
%% Product Composition
%% =====================================================

Table *--> "1..*" Column : defines
Table *--> "0..*" Constraint : enforces
Table *--> "0..*" Index : owns
Table *--> "0..*" Partition : partitions
Table *--> "0..*" Trigger : registers

%% =====================================================
%% Component References
%% =====================================================

Constraint --> "1..*" Column : applies to
Index --> "1..*" Column : indexes
Partition --> Column : uses partition key

%% =====================================================
%% Styling
%% =====================================================

style SchemaObject fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Table fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style TableMetadataProxy fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px,color:#4a148c

style Column fill:#e0f2f1,stroke:#009688,stroke-width:1px,color:#004d40
style Constraint fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px,color:#4a148c
style Index fill:#fff8e1,stroke:#ffb300,stroke-width:2px,color:#5d4037
style Partition fill:#eceff1,stroke:#607d8b,stroke-width:1px,color:#263238
style Trigger fill:#eceff1,stroke:#607d8b,stroke-width:1px,color:#263238

style TableBuilder fill:#ffe0b2,stroke:#f57c00,stroke-width:2px,color:#e65100

style LifecycleStatus fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px,color:#4a148c
style DropMode fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px,color:#4a148c
```

### 4.2 Sequence Diagram shouldBuildValidTableWithColumnsAndConstraints()
```mermaid
sequenceDiagram
    autonumber

    participant Test as TableBuilderTest
    participant C as idColumn : Column
    participant PK as primaryKey : Constraint
    participant B as builder : TableBuilder
    participant T as table : Table

    Note over Test,T: 1. Arrange table components

    Test ->> C: new Column("id", INTEGER, false)
    C -->> Test: idColumn

    Test ->> PK: new PrimaryKey("pk_users", idColumn)
    PK -->> Test: primaryKey

    Note over Test,T: 2. Create and configure Builder step by step

    Test ->> B: new TableBuilder()
    B -->> Test: builder

    Test ->> B: setTableId(tableId)
    B ->> B: this.tableId = tableId
    B -->> Test: builder

    Test ->> B: setName("users")
    B ->> B: this.name = "users"
    B -->> Test: builder

    Test ->> B: setOwner("admin")
    B ->> B: this.owner = "admin"
    B -->> Test: builder

    Test ->> B: setSchemaId(schemaId)
    B ->> B: this.schemaId = schemaId
    B -->> Test: builder

    Test ->> B: setEngine("InnoDB")
    B ->> B: this.engine = "InnoDB"
    B -->> Test: builder

    Test ->> B: addColumn(idColumn)
    B ->> B: columns.add(idColumn)
    B -->> Test: builder

    Test ->> B: addConstraint(primaryKey)
    B ->> B: constraints.add(primaryKey)
    B -->> Test: builder

    Note over Test,T: 3. Build final Product

    Test ->> B: build()
    activate B

    B ->> B: validate()
    B ->> B: validate required fields
    B ->> B: validate columns
    B ->> B: validate constraints

    B ->> T: new Table(tableId, name, owner, schemaId, engine, components)
    activate T
    T -->> B: table
    deactivate T

    B -->> Test: table
    deactivate B

    Note over Test,T: 4. Assert constructed Product

    Test ->> T: getName()
    T -->> Test: "users"

    Test ->> T: getColumns()
    T -->> Test: List containing idColumn

    Test ->> T: getConstraints()
    T -->> Test: List containing primaryKey

    Test ->> Test: assertNotNull(table)
    Test ->> Test: assertEquals("users", table.getName())
    Test ->> Test: assertEquals(1, table.getColumns().size())
    Test ->> Test: assertEquals(1, table.getConstraints().size())
```

### 4.2 Sequence Diagram shouldLazyLoadColumnsOnFirstAccess()
```mermaid
sequenceDiagram
    autonumber

    participant Client as TableMetadataTest
    participant P as proxy : TableMetadataProxy
    participant L as loader : MetadataLoader

    Note over Client,L: Case A: First access triggers disk read (Lazy Loading)
    Client ->> P: getColumns()
    activate P

    P ->> P: check if columns list is empty (isLoaded == false)
    P ->> P: lazyLoad()
    activate P
    
    P ->> L: loadColumns(tableId)
    activate L
    Note over L: Disk I/O: read column definitions from catalog metadata file
    L -->> P: columnsList : List~Column~
    deactivate L
    
    P ->> P: populate columns field and set isLoaded = true
    deactivate P

    P -->> Client: columnsList
    deactivate P

    Note over Client,L: Case B: Subsequent access returns cached columns instantly
    Client ->> P: getColumns()
    activate P
    P ->> P: check if columns list is empty (isLoaded == true)
    P -->> Client: columnsList
    deactivate P
```

### 4.3 Code Example

```java
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

// =====================================================
// Table Components
// =====================================================
public class Column {
    public final String name;
    public final String dataType;
    public final boolean nullable;

    public Column(String name, String dataType, boolean nullable) {
        this.name = name;
        this.dataType = dataType;
        this.nullable = nullable;
    }
}

public class Constraint {
    public final String name;
    public final Column column;

    public Constraint(String name, Column column) {
        this.name = name;
        this.column = column;
    }
}

// =====================================================
// Subject: Table Class (Concrete Class)
// =====================================================
public class Table {
    protected final UUID tableId;
    protected final String name;
    protected final String owner;
    protected final UUID schemaId;
    protected final String engine;
    protected final List<Column> columns;
    protected final List<Constraint> constraints;

    public Table(UUID tableId, String name, String owner, UUID schemaId, String engine,
                 List<Column> columns, List<Constraint> constraints) {
        this.tableId = tableId;
        this.name = name;
        this.owner = owner;
        this.schemaId = schemaId;
        this.engine = engine;
        this.columns = columns;
        this.constraints = constraints;
    }

    public UUID getTableId() { return tableId; }
    public String getName() { return name; }
    public String getOwner() { return owner; }
    public String getEngine() { return engine; }
    public List<Column> getColumns() { return columns; }
    public List<Constraint> getConstraints() { return constraints; }
}

// =====================================================
// Builder: TableBuilder Class
// =====================================================
public class TableBuilder {
    private UUID tableId;
    private String name;
    private String owner;
    private UUID schemaId;
    private String engine;
    private final List<Column> columns = new ArrayList<>();
    private final List<Constraint> constraints = new ArrayList<>();

    public TableBuilder setTableId(UUID tableId) {
        this.tableId = tableId;
        return this;
    }
    public TableBuilder setName(String name) {
        this.name = name;
        return this;
    }
    public TableBuilder setOwner(String owner) {
        this.owner = owner;
        return this;
    }
    public TableBuilder setSchemaId(UUID schemaId) {
        this.schemaId = schemaId;
        return this;
    }
    public TableBuilder setEngine(String engine) {
        this.engine = engine;
        return this;
    }
    public TableBuilder addColumn(Column column) {
        this.columns.add(column);
        return this;
    }
    public TableBuilder addConstraint(Constraint constraint) {
        this.constraints.add(constraint);
        return this;
    }

    public Table build() {
        validate();
        return new Table(tableId, name, owner, schemaId, engine, columns, constraints);
    }

    private void validate() {
        if (name == null || name.isEmpty()) {
            throw new IllegalStateException("Table name cannot be empty");
        }
        if (columns.isEmpty()) {
            throw new IllegalStateException("Table must have at least one column");
        }
    }
}   

// =====================================================
// Virtual Proxy
// =====================================================
public class MetadataLoader {
    public List<Column> loadColumns(UUID tableId) {
        System.out.println("Disk I/O: Reading column metadata for table ID: " + tableId);
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("id", "INTEGER", false));
        columns.add(new Column("name", "VARCHAR", true));
        return columns;
    }
}

public class TableMetadataProxy extends Table {
    private boolean isLoaded = false;
    private final MetadataLoader metadataLoader;

    public TableMetadataProxy(UUID tableId, String name, String owner, UUID schemaId, MetadataLoader loader) {
        super(tableId, name, owner, schemaId, "InnoDB", new ArrayList<>(), new ArrayList<>());
        this.metadataLoader = loader;
    }

    @Override
    public List<Column> getColumns() {
        lazyLoad();
        return super.getColumns();
    }

    private synchronized void lazyLoad() {
        return null;
    }
}

```

--- 

# 5. Column Definition and Data Type Management
## Standard Domain Entity (No Pattern)

---

# 6. Constraint Definition and Data Integrity Validation
## Using Strategy & Chain of Responsibility Pattern

### 6.1 Class Diagram
```mermaid
classDiagram
direction TB

%% =====================================================
%% Context
%% =====================================================

class Table {
    -constraints : List~Constraint~
    -validationChain : RowValidationHandler

    +addConstraint(constraint : Constraint) void
    +removeConstraint(constraintId : UUID) void
    +validateConstraints(row : Row, context : ConstraintValidationContext) void
}

%% =====================================================
%% Strategy
%% =====================================================

class Constraint {
    <<interface>>

    +getId() UUID
    +getName() String
    +validate(row : Row, context : ConstraintValidationContext) void
}

%% =====================================================
%% Concrete Strategies
%% =====================================================

class PrimaryKey {
    -constraintId : UUID
    -name : String
    -columnIds : List~UUID~

    +getId() UUID
    +getName() String
    +validate(row : Row, context : ConstraintValidationContext) void
}

class UniqueConstraint {
    -constraintId : UUID
    -name : String
    -columnIds : List~UUID~

    +getId() UUID
    +getName() String
    +validate(row : Row, context : ConstraintValidationContext) void
}

class ForeignKey {
    -constraintId : UUID
    -name : String
    -columnIds : List~UUID~
    -referencedTableId : UUID
    -referencedColumnIds : List~UUID~

    +getId() UUID
    +getName() String
    +validate(row : Row, context : ConstraintValidationContext) void
}

class CheckConstraint {
    -constraintId : UUID
    -name : String
    -expression : String

    +getId() UUID
    +getName() String
    +validate(row : Row, context : ConstraintValidationContext) void
}

%% =====================================================
%% Chain of Responsibility (Validation Pipeline)
%% =====================================================

class RowValidationHandler {
    <<abstract>>
    -next : RowValidationHandler

    +setNext(next : RowValidationHandler) RowValidationHandler
    +validate(row : Row, table : Table) void
    #check(row : Row, table : Table)* void
    #checkNext(row : Row, table : Table) void
}

class NullabilityValidator {
    #check(row : Row, table : Table) void
}

class UniqueValidator {
    #check(row : Row, table : Table) void
}

class ForeignKeyValidator {
    #check(row : Row, table : Table) void
}

%% =====================================================
%% Supporting Types
%% =====================================================

class Row {
    -rowId : UUID
    -values : Map~UUID, Object~

    +getValue(columnId : UUID) Object
}

class ConstraintValidationContext {
    -currentTableId : UUID
    -transactionId : UUID

    +exists(tableId : UUID, columnIds : List~UUID~, values : List~Object~) boolean
    +isUnique(tableId : UUID, columnIds : List~UUID~, values : List~Object~) boolean
}

%% =====================================================
%% Strategy Relationships
%% =====================================================

Table *--> "0..*" Constraint : contains and executes

Constraint <|.. PrimaryKey
Constraint <|.. UniqueConstraint
Constraint <|.. ForeignKey
Constraint <|.. CheckConstraint

Table ..> Row : validates
Table --> ConstraintValidationContext : supplies

Constraint ..> Row : validates
Constraint ..> ConstraintValidationContext : uses

%% =====================================================
%% Chain of Responsibility Relationships
%% =====================================================

Table --> RowValidationHandler : delegates row validation
RowValidationHandler --> RowValidationHandler : next link (1 to 1)

RowValidationHandler <|-- NullabilityValidator
RowValidationHandler <|-- UniqueValidator
RowValidationHandler <|-- ForeignKeyValidator

%% =====================================================
%% Styling
%% =====================================================

style Table fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298

style Constraint fill:#fff3e0,stroke:#ef6c00,stroke-width:2px,color:#7f2704

style PrimaryKey fill:#f3e5f5,stroke:#7b1fa2,stroke-width:1px,color:#4a148c
style UniqueConstraint fill:#f3e5f5,stroke:#7b1fa2,stroke-width:1px,color:#4a148c
style ForeignKey fill:#f3e5f5,stroke:#7b1fa2,stroke-width:1px,color:#4a148c
style CheckConstraint fill:#f3e5f5,stroke:#7b1fa2,stroke-width:1px,color:#4a148c

style RowValidationHandler fill:#ffe0b2,stroke:#f57c00,stroke-width:2px,color:#e65100
style NullabilityValidator fill:#ffe0b2,stroke:#f57c00,stroke-width:1px,color:#e65100
style UniqueValidator fill:#ffe0b2,stroke:#f57c00,stroke-width:1px,color:#e65100
style ForeignKeyValidator fill:#ffe0b2,stroke:#f57c00,stroke-width:1px,color:#e65100

style Row fill:#e0f2f1,stroke:#009688,stroke-width:1px,color:#004d40
style ConstraintValidationContext fill:#e0f2f1,stroke:#009688,stroke-width:1px,color:#004d40
```

### 6.2 Sequence Diagram shouldValidateRowUsingMultipleConstraint()
```mermaid
sequenceDiagram
    autonumber

    participant Test as TableConstraintValidationTest
    participant T as usersTable : Table
    participant PK as pk : Constraint (PrimaryKey)
    participant UQ as uniqueEmail : Constraint (UniqueConstraint)
    participant CK as checkAge : Constraint (CheckConstraint)

    Note over Test,CK: Arrange Table with interchangeable Constraint strategies

    Test ->> T: addConstraint(pk)
    T ->> T: constraints.add(pk)
    T -->> Test: void

    Test ->> T: addConstraint(uniqueEmail)
    T ->> T: constraints.add(uniqueEmail)
    T -->> Test: void

    Test ->> T: addConstraint(checkAge)
    T ->> T: constraints.add(checkAge)
    T -->> Test: void

    Note over Test,CK: Row contains id=1, email="huy@example.com", age=20

    Test ->> T: validateConstraints(row)
    activate T

    Note over T,CK: Table delegates validation through Constraint abstraction

    T ->> PK: validate(row, usersTable)
    activate PK
    PK ->> PK: check primary key is not null
    PK ->> PK: check primary key is unique
    PK -->> T: validation passed
    deactivate PK

    T ->> UQ: validate(row, usersTable)
    activate UQ
    UQ ->> UQ: check email is unique
    UQ -->> T: validation passed
    deactivate UQ

    T ->> CK: validate(row, usersTable)
    activate CK
    CK ->> CK: evaluate age >= 18
    CK -->> T: validation passed
    deactivate CK

    T -->> Test: validation completed
    deactivate T

    Test ->> Test: assertDoesNotThrow()
```

### Code Example for Chain of Responsibility Pattern
#### Base Handler 
```java 
package com.example.dbms.validation; 

public abstract class RowValidationHandler {
    private RowValidationHandler next;
    
    public RowValidationHandler setNext(RowValidationHandler next) {
        this.next = next;
        return next;
    }
    
    public void validate(Row row, Table table) {
        check(row, table);     
        checkNext(row, table);  
    }
    
    protected abstract void check(Row row, Table table);
    
    protected void checkNext(Row row, Table table) {
        if (next != null) {
            next.validate(row, table);
        }
    }
}
```

#### Concrete Handler 
```java 
public class NullabilityValidator extends RowValidationHandler {
    
    @Override
    protected void check(Row row, Table table) {
        checkNext(row, table);
    }
}

public class UniqueValidator extends RowValidationHandler {
    
    @Override
    protected void check(Row row, Table table) {
        checkNext(row, table);
    }
}

public class ForeignKeyValidator extends RowValidationHandler {
    
    @Override
    protected void check(Row row, Table table) {
        checkNext(row, table);
    }
}
```

#### Client 
```java
public class TableDataExecutor {
    
    public void insert(Table table, Row row) {
        RowValidationHandler nullabilityValidator = new NullabilityValidator();
        RowValidationHandler uniqueValidator = new UniqueValidator();
        RowValidationHandler foreignKeyValidator = new ForeignKeyValidator();
        
        nullabilityValidator.setNext(uniqueValidator).setNext(foreignKeyValidator);
        
        nullabilityValidator.validate(row, table);
    }
}
```


### 6.2 Sequence Diagram shouldPipelineConstraintValidationBeforeInsert()
```mermaid
sequenceDiagram
    autonumber

    participant Client as TableDataExecutor
    participant T as usersTable : Table
    participant N as nullability : NullabilityValidator
    participant U as unique : UniqueValidator
    participant FK as foreignKey : ForeignKeyValidator

    Note over Client,FK: Setup validation pipeline chain (Nullability -> Unique -> ForeignKey)

    Client ->> T: insert(row)
    activate T

    T ->> N: validate(row, usersTable)
    activate N

    N ->> N: check(row, usersTable)
    Note over N: Check non-nullable columns have values on RAM (Cheap check)

    N ->> N: checkNext(row, usersTable)
    activate N
    
    N ->> U: validate(row, usersTable)
    activate U
    
    U ->> U: check(row, usersTable)
    Note over U: Query index to check value uniqueness (Expensive check)
    
    U ->> U: checkNext(row, usersTable)
    activate U
    
    U ->> FK: validate(row, usersTable)
    activate FK
    
    FK ->> FK: check(row, usersTable)
    Note over FK: Query parent table to check foreign key existence (Very expensive check)
    
    FK ->> FK: checkNext(row, usersTable)
    Note over FK: No next handler in chain (reaches end)
    
    FK -->> U: validation passed
    deactivate FK
    
    deactivate U
    U -->> N: validation passed
    deactivate U
    
    deactivate N
    N -->> T: validation passed
    deactivate N

    T ->> T: saveToDisk(row)
    T -->> Client: insert completed
    deactivate T
```

### 6.3 Code Example
### Strategy
```java
public abstract class Constraint {
    protected UUID constraintId;
    protected String constraintName;
    protected ConstraintType constraintType;
    protected UUID tableId;
    protected List<Column> columns;
    protected ConstraintStatus status;
    protected boolean enabled;

    public Constraint(UUID constraintId, String constraintName, ConstraintType constraintType, 
                      UUID tableId, List<Column> columns) {
        this.constraintId = constraintId;
        this.constraintName = constraintName;
        this.constraintType = constraintType;
        this.tableId = tableId;
        this.columns = new ArrayList<>(columns);
        this.status = ConstraintStatus.ACTIVE;
        this.enabled = true;
    }

    public String getConstraintName() { return constraintName; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public abstract void validate(Row row, Table table);
}
```
### Concrete Strategy 
```java
public class PrimaryKey extends Constraint {
    public PrimaryKey(UUID constraintId, String constraintName, UUID tableId, Column column) {
        super(constraintId, constraintName, ConstraintType.PRIMARY_KEY, tableId, Collections.singletonList(column));
    }
    @Override
    public void validate(Row row, Table table) {
       
    }
}

public class UniqueConstraint extends Constraint {
    public UniqueConstraint(UUID constraintId, String constraintName, UUID tableId, Column column) {
        super(constraintId, constraintName, ConstraintType.UNIQUE, tableId, Collections.singletonList(column));
    }
    @Override
    public void validate(Row row, Table table) {
        
    }
}

public class CheckConstraint extends Constraint {
    private String expression;
    public CheckConstraint(UUID constraintId, String constraintName, UUID tableId, Column column, String expression) {
        super(constraintId, constraintName, ConstraintType.CHECK, tableId, Collections.singletonList(column));
        this.expression = expression;
    }
    @Override
    public void validate(Row row, Table table) {
        
    }
}

public class ForeignKey extends Constraint {
    private Table referencedTable;
    private Column referencedColumn;
    public ForeignKey(UUID constraintId, String constraintName, UUID tableId, Column column,
                      Table referencedTable, Column referencedColumn) {
        super(constraintId, constraintName, ConstraintType.FOREIGN_KEY, tableId, Collections.singletonList(column));
        this.referencedTable = referencedTable;
        this.referencedColumn = referencedColumn;
    }
    @Override
    public void validate(Row row, Table table) {
        
    }
}
```

### Context 
```java
public class Table {
    private UUID tableId;
    private String name;
    private List<Column> columns = new ArrayList<>();
    private List<Constraint> constraints = new ArrayList<>();
    private List<Row> rows = new ArrayList<>();

    public Table(UUID tableId, String name) {
        this.tableId = tableId;
        this.name = name;
    }

    public String getName() { return name; }
    public List<Row> getRows() { return rows; }
    public void addColumn(Column column) {
        this.columns.add(column);
    }
    public void addConstraint(Constraint constraint) {
        this.constraints.add(constraint);
    }
    public void insertRow(Row row) {
        
    }
    public void validateConstraints(Row row) {
        
    }
} 
```
---

# 7. Table Data and Row Operations
## Using Template Method, Command & Bridge Pattern

### 7.1 Class Diagram
```mermaid
classDiagram
direction TB

class TableDataCommandExecutor {
    +execute(command : TableDataCommand, context : DataOperationContext) DataOperationResult
}

class TableDataCommand {
    <<interface>>

    +execute(context : DataOperationContext) DataOperationResult
}

class AbstractTableDataCommand {
    <<abstract>>

    +execute(context : DataOperationContext) DataOperationResult

    #validateRequest(context : DataOperationContext) void
    #acquireLocks(context : DataOperationContext) void
    #validateConstraints(context : DataOperationContext) void
    #writeAheadLog(context : DataOperationContext) void
    #modifyRow(context : DataOperationContext)* void
    #updateIndexes(context : DataOperationContext) void
    #afterExecution(context : DataOperationContext) DataOperationResult
    #onFailure(context : DataOperationContext, error : Exception) void
    #releaseLocks(context : DataOperationContext) void
}

class InsertRowCommand {
    -row : Row

    +InsertRowCommand(row : Row)
    #validateRequest(context : DataOperationContext) void
    #writeAheadLog(context : DataOperationContext) void
    #modifyRow(context : DataOperationContext) void
    #updateIndexes(context : DataOperationContext) void
}

class UpdateRowCommand {
    -rowId : UUID
    -changes : RowChanges
    -oldRow : Row
    -updatedRow : Row

    +UpdateRowCommand(rowId : UUID, changes : RowChanges)
    #validateRequest(context : DataOperationContext) void
    #writeAheadLog(context : DataOperationContext) void
    #modifyRow(context : DataOperationContext) void
    #updateIndexes(context : DataOperationContext) void
}

class DeleteRowCommand {
    -rowId : UUID
    -deletedRow : Row

    +DeleteRowCommand(rowId : UUID)
    #validateRequest(context : DataOperationContext) void
    #writeAheadLog(context : DataOperationContext) void
    #modifyRow(context : DataOperationContext) void
    #updateIndexes(context : DataOperationContext) void
}

class DataOperationContext {
    -table : Table
    -transactionId : UUID

    +getTable() Table
    +getTransactionId() UUID
}

class DataOperationResult {
    -affectedRowCount : Long
    -generatedRowId : UUID
    -successful : Boolean
}

class Table {
    -tableId : UUID
    -storageBackend : StorageBackend

    +validateConstraints(row : Row, context : ConstraintValidationContext) void

    +insertRow(row : Row) void
    +findRow(rowId : UUID) Row
    +updateRow(rowId : UUID, changes : RowChanges) Row
    +deleteRow(rowId : UUID) Row

    +insertIntoIndexes(row : Row) void
    +updateIndexes(oldRow : Row, newRow : Row) void
    +deleteFromIndexes(row : Row) void
}

class StorageBackend {
    <<interface>>
    +writeRecord(data : byte[]) void
    +readRecord(id : UUID) byte[]
}

class RowStorageBackend {
    +writeRecord(data : byte[]) void
    +readRecord(id : UUID) byte[]
}

class ColumnStorageBackend {
    +writeRecord(data : byte[]) void
    +readRecord(id : UUID) byte[]
}

class Row {
    -rowId : UUID
    -values : Map~UUID, Object~
}

class RowChanges {
    -values : Map~UUID, Object~
}

class ConstraintValidationContext

TableDataCommand <|.. AbstractTableDataCommand

AbstractTableDataCommand <|-- InsertRowCommand
AbstractTableDataCommand <|-- UpdateRowCommand
AbstractTableDataCommand <|-- DeleteRowCommand

TableDataCommandExecutor --> TableDataCommand : invokes
AbstractTableDataCommand --> DataOperationContext : uses
AbstractTableDataCommand ..> DataOperationResult : returns

DataOperationContext --> Table : provides receiver

InsertRowCommand *--> Row : contains
UpdateRowCommand *--> RowChanges : contains

Table ..> Row : manages logically
Table --> StorageBackend : delegates physical operations (Bridge)
Table ..> ConstraintValidationContext : validates with

StorageBackend <|.. RowStorageBackend
StorageBackend <|.. ColumnStorageBackend

style TableDataCommandExecutor fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298

style TableDataCommand fill:#fff3e0,stroke:#ef6c00,stroke-width:2px,color:#7f2704
style AbstractTableDataCommand fill:#fff3e0,stroke:#ef6c00,stroke-width:2px,color:#7f2704

style InsertRowCommand fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style UpdateRowCommand fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style DeleteRowCommand fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132

style DataOperationContext fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px,color:#4a148c
style DataOperationResult fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px,color:#4a148c

style Table fill:#ffffff,stroke:#607d8b,stroke-width:1px,color:#37474f
style Row fill:#ffffff,stroke:#607d8b,stroke-width:1px,color:#37474f
style RowChanges fill:#ffffff,stroke:#607d8b,stroke-width:1px,color:#37474f

style StorageBackend fill:#e0f2f1,stroke:#00695c,stroke-width:2px,color:#004d40
style RowStorageBackend fill:#e0f2f1,stroke:#00695c,stroke-width:1px,color:#004d40
style ColumnStorageBackend fill:#e0f2f1,stroke:#00695c,stroke-width:1px,color:#004d40
```

### 7.2 Sequence Diagram shouldInsertRowUsingBridgedStorageBackend()
```mermaid
sequenceDiagram
    autonumber

    participant Test as TableDataIntegrationTest
    participant T as usersTable : Table
    participant SB as storageBackend : RowStorageBackend

    Note over Test,SB: The Abstraction (Table) is bridged with the Implementor (RowStorageBackend)

    Test ->> T: insertRow(row)
    activate T

    T ->> T: validateConstraints(row)
    Note over T: Logical Layer executes constraint validation

    T ->> T: serializeRowToBytes(row)
    Note over T: Serialize Row object into binary byte array

    Note over T,SB: Bridge: Abstraction delegates physical write to Implementor
    T ->> SB: writeRecord(data)
    activate SB
    Note over SB: Write binary data to disk pages (Physical row-oriented I/O)
    SB -->> T: write completed
    deactivate SB

    T -->> Test: insert completed
    deactivate T
```

### 7.2 Sequence Diagram shouldExecuteInsertRowCommand()
```mermaid
sequenceDiagram
    autonumber

    participant Test as TableDataCommandIntegrationTest
    participant QE as tableDataCommandExecutor : TableDataCommandExecutor
    participant CMD as insertCommand : InsertRowCommand
    participant T as usersTable : Table

    Note over Test,T: 1. Arrange Command, Receiver and Input

    Test ->> Test: create row
    Test ->> CMD: new InsertRowCommand(row)
    CMD -->> Test: insertCommand

    Note over Test,T: 2. Client submits Command to Invoker

    Test ->> QE: execute(insertCommand, context)
    activate QE

    Note over QE,CMD: TableDataCommandExecutor does not know Insert implementation

    QE ->> CMD: execute(context)
    activate CMD

    Note over CMD,T: Concrete Command invokes its Receiver

    CMD ->> T: insertRow(row)
    activate T

    T ->> T: store row logically
    T ->> T: rowCount++

    T -->> CMD: insertion completed
    deactivate T

    CMD -->> QE: command completed
    deactivate CMD

    QE -->> Test: execution completed
    deactivate QE

    Note over Test,T: 3. Verify Command Result

    Test ->> T: findRow(rowId)
    T -->> Test: inserted row

    Test ->> Test: assertEquals(row, insertedRow)
```

### 7.2 Sequence Diagram shouldExecuteInsertUsingTemplateWorkflow()
```mermaid
sequenceDiagram
    autonumber

    participant Test as InsertRowCommandTest
    participant CMD as insertCommand : InsertRowCommand
    participant T as usersTable : Table

    Note over Test,T: execute() is inherited from AbstractTableDataCommand

    Test ->> CMD: execute(context)
    activate CMD

    Note over CMD: Start fixed Template Method workflow

    CMD ->> CMD: validateRequest(context)
    Note over CMD: InsertRowCommand implementation

    CMD ->> CMD: acquireLocks(context)
    Note over CMD: InsertRowCommand implementation

    CMD ->> T: validateConstraints(row)
    activate T
    T -->> CMD: validation passed
    deactivate T

    CMD ->> CMD: writeAheadLog(context)
    Note over CMD: WAL is written before data modification

    CMD ->> CMD: modifyRow(context)
    Note over CMD: InsertRowCommand-specific step

    CMD ->> T: insertRow(row)
    activate T
    T ->> T: rowCount++
    T -->> CMD: insertion completed
    deactivate T

    CMD ->> CMD: updateIndexes(context)

    CMD ->> T: updateIndexes()
    activate T
    T -->> CMD: indexes updated
    deactivate T

    CMD ->> CMD: afterExecution(context)

    Note over CMD: End fixed Template Method workflow

    CMD -->> Test: execution completed
    deactivate CMD

    Test ->> Test: assertDoesNotThrow()
```

### 7.3 Code Example for Template Method
### AbstractCommand 
```java
public interface TableDataCommand {
    void execute(DataOperationContext context);
}

public abstract class AbstractTableDataCommand implements TableDataCommand {
    @Override
    public final void execute(DataOperationContext context) {

    }
    protected abstract void validateRequest(DataOperationContext context);
    protected abstract void acquireLocks(DataOperationContext context);
    protected abstract void writeAheadLog(DataOperationContext context);
    protected abstract void modifyRow(DataOperationContext context);
    protected abstract void updateIndexes(DataOperationContext context);
    protected void validateConstraints(DataOperationContext context) {
        
    }
    protected void afterExecution(DataOperationContext context) {
    }
    protected abstract Row getRow();
}

```
### Concrete class 
```java
public class InsertRowCommand extends AbstractTableDataCommand {
    private Row row;
    public InsertRowCommand(Row row) {
        this.row = row;
    }
    @Override
    protected Row getRow() {
        return row;
    }
    @Override
    protected void validateRequest(DataOperationContext context) {
        
    }
    @Override
    protected void acquireLocks(DataOperationContext context) {
    }
    @Override
    protected void writeAheadLog(DataOperationContext context) {
    }
    @Override
    protected void modifyRow(DataOperationContext context) {
        
    }
    @Override
    protected void updateIndexes(DataOperationContext context) {
        
    }
    @Override
    protected void afterExecution(DataOperationContext context) {
    }
} 
```

### 7.3 Code Example for Command Pattern 
### Receiver
```java
public class Table {
    private UUID tableId;
    private long rowCount;
    private Map<UUID, Row> storage = new HashMap<>();

    public Table(UUID tableId) {
        this.tableId = tableId;
        this.rowCount = 0L;
    }
    
    public void insertRow(Row row) {
        
    }
    public Row findRow(UUID rowId) {
        return null;
    }
    public long getRowCount() { return rowCount; }
}
```

### Command 
```java
public interface TableDataCommand {
    void execute(DataOperationContext context);
}
```

### Concrete Command
```java
public class InsertRowCommand implements TableDataCommand {
    private Row row;

    public InsertRowCommand(Row row) {
        this.row = row;
    }
    @Override
    public void execute(DataOperationContext context) {
        
    }
}
```

### Invoker
```java
public class TableDataCommandExecutor {
    public void execute(TableDataCommand command, DataOperationContext context) {
        
    }
}
```

### 7.4 Code Example for Bridge Pattern

#### Implementation 
```java
public interface StorageBackend {
    void writeRecord(byte[] data);
    byte[] readRecord(UUID id);
}
```

#### Concrete Implementation
```java
public class RowStorageBackend implements StorageBackend {
    @Override
    public void writeRecord(byte[] data) {
    }

    @Override
    public byte[] readRecord(UUID id) {
        return null;
    }
}

public class ColumnStorageBackend implements StorageBackend {
    @Override
    public void writeRecord(byte[] data) {
    }

    @Override
    public byte[] readRecord(UUID id) {
        return null;
    }
}
```

#### Abstraction 
```java
public class Table {
    protected UUID tableId;
    protected String name;
    protected StorageBackend storageBackend; 

    public Table(UUID tableId, String name, StorageBackend storageBackend) {
        this.tableId = tableId;
        this.name = name;
        this.storageBackend = storageBackend;
    }

    public void insertRow(Row row) {
    }

    private byte[] serializeRow(Row row) {
        return null;
    }
}
```

#### Client / Demo Code
```java
package dbms;

import java.util.UUID;
import dbms.catalog.table.*;
import dbms.storage.*;

public class BridgePatternDemo {
    public static void main(String[] args) {
        Row row = new Row();

        StorageBackend rowBackend = new RowStorageBackend();
        Table usersTableRowStore = new Table(UUID.randomUUID(), "users", rowBackend);
        usersTableRowStore.insertRow(row); 

        StorageBackend colBackend = new ColumnStorageBackend();
        Table usersTableColStore = new Table(UUID.randomUUID(), "users", colBackend);
        usersTableColStore.insertRow(row);
    }
}
```

---

# 8. Object Naming, Lookup, and Uniqueness Management
## Standard Domain Entity (No Pattern)

--- 

# 9. Index Definition and Management
## Using Strategy Pattern

### 9.1 Class diagram
```mermaid
classDiagram
direction TB

%% =====================================================
%% TABLE - INDEX OWNER
%% =====================================================

class Table {
    -indexes : List~Index~

    +addIndex(index : Index, context : IndexDefinitionContext) void
    +dropIndex(indexId : UUID) void

    +findIndexById(indexId : UUID) Index
    +findIndexByName(name : String) Index
    +listIndexes() List~Index~

    +insertIntoIndexes(row : Row, context : IndexOperationContext) void
    +updateIndexes(oldRow : Row, newRow : Row, context : IndexOperationContext) void
    +deleteFromIndexes(row : Row, context : IndexOperationContext) void
}

%% =====================================================
%% STRATEGY CONTEXT AND INDEX DEFINITION
%% =====================================================

class Index {
    -indexId : UUID
    -name : String
    -tableId : UUID
    -columnIds : List~UUID~
    -unique : Boolean
    -status : IndexStatus
    -accessMethod : IndexAccessMethod

    +getId() UUID
    +getName() String
    +getTableId() UUID
    +getColumnIds() List~UUID~
    +getType() IndexType
    +getStatus() IndexStatus
    +isUnique() boolean

    +validateDefinition(context : IndexDefinitionContext) void

    +build(context : IndexOperationContext) void
    +rebuild(context : IndexOperationContext) void
    +enable() void
    +disable() void
    +markInvalid() void
    +drop() void

    +insertEntry(row : Row, context : IndexOperationContext) void
    +updateEntry(oldRow : Row, newRow : Row, context : IndexOperationContext) void
    +deleteEntry(row : Row, context : IndexOperationContext) void

    +search(key : IndexKey) List~UUID~
    +rangeSearch(fromKey : IndexKey, toKey : IndexKey) List~UUID~

    -extractKey(row : Row) IndexKey
    -validateUniqueKey(key : IndexKey) void
    -ensureActive() void
}

%% =====================================================
%% STRATEGY
%% =====================================================

class IndexAccessMethod {
    <<interface>>

    +getType() IndexType
    +build(context : IndexOperationContext) void

    +insert(key : IndexKey, rowId : UUID) void
    +delete(key : IndexKey, rowId : UUID) void

    +search(key : IndexKey) List~UUID~
    +supportsRangeSearch() boolean
    +rangeSearch(fromKey : IndexKey, toKey : IndexKey) List~UUID~
}

%% =====================================================
%% CONCRETE STRATEGIES
%% =====================================================

class BTreeIndexAccessMethod {
    +getType() IndexType
    +build(context : IndexOperationContext) void

    +insert(key : IndexKey, rowId : UUID) void
    +delete(key : IndexKey, rowId : UUID) void

    +search(key : IndexKey) List~UUID~
    +supportsRangeSearch() boolean
    +rangeSearch(fromKey : IndexKey, toKey : IndexKey) List~UUID~
}

class HashIndexAccessMethod {
    +getType() IndexType
    +build(context : IndexOperationContext) void

    +insert(key : IndexKey, rowId : UUID) void
    +delete(key : IndexKey, rowId : UUID) void

    +search(key : IndexKey) List~UUID~
    +supportsRangeSearch() boolean
    +rangeSearch(fromKey : IndexKey, toKey : IndexKey) List~UUID~
}

class BitmapIndexAccessMethod {
    +getType() IndexType
    +build(context : IndexOperationContext) void

    +insert(key : IndexKey, rowId : UUID) void
    +delete(key : IndexKey, rowId : UUID) void

    +search(key : IndexKey) List~UUID~
    +supportsRangeSearch() boolean
    +rangeSearch(fromKey : IndexKey, toKey : IndexKey) List~UUID~
}

%% =====================================================
%% INDEX DEFINITION VALIDATION
%% =====================================================

class IndexDefinitionContext {
    -tableId : UUID
    -columns : List~Column~
    -existingIndexes : List~Index~

    +hasColumn(columnId : UUID) boolean
    +hasIndexName(name : String) boolean
    +hasEquivalentIndex(columnIds : List~UUID~, type : IndexType) boolean
    +supportsType(columnId : UUID, type : IndexType) boolean
}

%% =====================================================
%% INDEX OPERATION CONTEXT
%% =====================================================

class IndexOperationContext {
    -tableId : UUID
    -transactionId : UUID

    +getTableId() UUID
    +getTransactionId() UUID
    +scanRows() List~Row~
}

%% =====================================================
%% SUPPORTING DOMAIN TYPES
%% =====================================================

class IndexKey {
    -values : List~Object~

    +getValues() List~Object~
}

class IndexType {
    <<enumeration>>

    BTREE
    HASH
    BITMAP
}

class IndexStatus {
    <<enumeration>>

    BUILDING
    ACTIVE
    DISABLED
    REBUILDING
    INVALID
    DROPPED
}

class Column {
    -columnId : UUID
    -name : String
    -dataType : DataType

    +getId() UUID
    +getName() String
    +getDataType() DataType
}

class Row {
    -rowId : UUID
    -values : Map~UUID, Object~

    +getId() UUID
    +getValue(columnId : UUID) Object
}

class DataType {
    <<enumeration>>
}

%% =====================================================
%% TABLE AND INDEX RELATIONSHIPS
%% =====================================================

Table *--> "0..*" Index : owns and maintains
Table *--> "1..*" Column : defines

Table ..> Row : handles data changes
Table ..> IndexDefinitionContext : validates index with
Table ..> IndexOperationContext : supplies operation context

%% =====================================================
%% STRATEGY RELATIONSHIPS
%% =====================================================

Index *--> IndexAccessMethod : delegates operations

IndexAccessMethod <|.. BTreeIndexAccessMethod
IndexAccessMethod <|.. HashIndexAccessMethod
IndexAccessMethod <|.. BitmapIndexAccessMethod

%% =====================================================
%% DEFINITION AND OPERATION DEPENDENCIES
%% =====================================================

Index ..> IndexDefinitionContext : validates definition with
Index ..> IndexOperationContext : builds and maintains with

Index --> IndexType : identifies
Index --> IndexStatus : has lifecycle status
Index --> "1..*" Column : indexes

Index ..> Row : extracts key from
Index ..> IndexKey : creates and searches

IndexAccessMethod ..> IndexKey : organizes
IndexAccessMethod ..> IndexOperationContext : builds from

IndexDefinitionContext --> Column : resolves
IndexDefinitionContext --> Index : checks existing

Column --> DataType : uses

%% =====================================================
%% STYLING
%% =====================================================

style Table fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Index fill:#fff8e1,stroke:#f9a825,stroke-width:2px,color:#664d03

style IndexAccessMethod fill:#fff3e0,stroke:#ef6c00,stroke-width:2px,color:#7f2704
style BTreeIndexAccessMethod fill:#fff3e0,stroke:#ef6c00,stroke-width:1px,color:#7f2704
style HashIndexAccessMethod fill:#fff3e0,stroke:#ef6c00,stroke-width:1px,color:#7f2704
style BitmapIndexAccessMethod fill:#fff3e0,stroke:#ef6c00,stroke-width:1px,color:#7f2704

style IndexDefinitionContext fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style IndexOperationContext fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298

style IndexType fill:#f3e5f5,stroke:#7b1fa2,stroke-width:1px,color:#4a148c
style IndexStatus fill:#f3e5f5,stroke:#7b1fa2,stroke-width:1px,color:#4a148c
style IndexKey fill:#f3e5f5,stroke:#7b1fa2,stroke-width:1px,color:#4a148c

style Column fill:#e0f2f1,stroke:#009688,stroke-width:1px,color:#004d40
style Row fill:#e0f2f1,stroke:#009688,stroke-width:1px,color:#004d40
style DataType fill:#e0f2f1,stroke:#009688,stroke-width:1px,color:#004d40

### 9.2 Sequence Diagram Search Rows Using Selected Index Strategy
```mermaid
sequenceDiagram
    autonumber

    box #e3f2fd Client
        participant Client as executor : QueryExecutor
    end

    box #e8f5e9 Context
        participant T as usersTable : Table
        participant Index as index : Index
    end

    box #fff3e0 Strategy
        participant Strategy as accessMethod : BTreeIndexAccessMethod
    end

    Note over Client,Strategy: Search rows using B-Tree index strategy

    Client->>T: findIndexById(indexId)
    activate T
    T-->>Client: index : Index
    deactivate T

    Client->>Index: search(key)
    activate Index

    Note right of Index: Context delegates search<br/>to the concrete strategy

    Index->>Strategy: search(key)
    activate Strategy
    Strategy->>Strategy: traverseBTree(key)
    Strategy-->>Index: rowIds : List<UUID>
    deactivate Strategy

    Index-->>Client: rowIds : List<UUID>
    deactivate Index
```

### 9.3 Code Example for Search Rows Using Selected Index Strategy
### Strategy Interface
```java
public interface IndexAccessMethod {
    IndexType getType();
    void build(IndexOperationContext context);
    void insert(IndexKey key, UUID rowId);
    void delete(IndexKey key, UUID rowId);
    List<UUID> search(IndexKey key);
    boolean supportsRangeSearch();
    List<UUID> rangeSearch(IndexKey fromKey, IndexKey toKey);
}
```

### Concrete Strategy
```java
public class BTreeIndexAccessMethod implements IndexAccessMethod {
    @Override
    public IndexType getType() {
        return IndexType.BTREE;
    }

    @Override
    public void build(IndexOperationContext context) {
        System.out.println("Building BTree index structure...");
    }

    @Override
    public void insert(IndexKey key, UUID rowId) {
        System.out.println("Inserting key into BTree: " + key.getValues());
    }

    @Override
    public void delete(IndexKey key, UUID rowId) {
        System.out.println("Deleting key from BTree: " + key.getValues());
    }

    @Override
    public List<UUID> search(IndexKey key) {
        System.out.println("Searching BTree for key: " + key.getValues());
        return new ArrayList<>();
    }

    @Override
    public boolean supportsRangeSearch() {
        return true;
    }

    @Override
    public List<UUID> rangeSearch(IndexKey fromKey, IndexKey toKey) {
        System.out.println("Range searching BTree from: " + fromKey.getValues() + " to: " + toKey.getValues());
        return new ArrayList<>();
    }
}
```

### Context (Index)
```java
public class Index {
    private UUID indexId;
    private String name;
    private UUID tableId;
    private List<UUID> columnIds;
    private boolean unique;
    private IndexStatus status;
    private IndexAccessMethod accessMethod;

    public Index(UUID indexId, String name, UUID tableId, List<UUID> columnIds, boolean unique, IndexAccessMethod accessMethod) {
        this.indexId = indexId;
        this.name = name;
        this.tableId = tableId;
        this.columnIds = columnIds;
        this.unique = unique;
        this.status = IndexStatus.ACTIVE;
        this.accessMethod = accessMethod;
    }

    public UUID getId() { return indexId; }
    public String getName() { return name; }
    public UUID getTableId() { return tableId; }
    public List<UUID> getColumnIds() { return columnIds; }
    public boolean isUnique() { return unique; }
    public IndexStatus getStatus() { return status; }

    public List<UUID> search(IndexKey key) {
        return null;
    }

    public void insertEntry(Row row, IndexOperationContext context) {

    }

    public void deleteEntry(Row row, IndexOperationContext context) {

    }

    private void ensureActive() {
        
    }

    private IndexKey extractKey(Row row) {
        return null;
    }

    private void validateUniqueKey(IndexKey key) {
        
    }
}
```

### Table (Owner)
```java
public class Table {
    private List<Index> indexes = new ArrayList<>();

    public void addIndex(Index index, IndexDefinitionContext context) {
        
    }

    public void dropIndex(UUID indexId) {
       
    }

    public Index findIndexById(UUID indexId) {
        return null;
    }

    public Index findIndexByName(String name) {
        return null;
    }

    public List<Index> listIndexes() {
        return new ArrayList<>(indexes);
    }
}
```

### Client (QueryExecutor)
```java
public class QueryExecutor {
    public List<UUID> executeSearch(Table table, UUID indexId, IndexKey key) {
        return null;
    }
}
```
# Query Processing feature mindmap
```mermaid
flowchart LR
    %% =====================================================
    %% ROOT
    %% =====================================================

    ROOT((Query Processing Features))

    %% =====================================================
    %% PRIORITY GROUPS
    %% =====================================================

    HIGH["High Priority — Mandatory"]
    MEDIUM["Medium Priority — Important"]

    ROOT --> HIGH
    ROOT --> MEDIUM

    %% =====================================================
    %% HIGH PRIORITY FEATURES
    %% =====================================================

    H1["1. SQL Lexical Analysis"]
    H1P["None / State (Conditional)"]

    H2["2. SQL Syntax Parsing and AST Construction"]
    H2P["Composite"]

    H3["3. Semantic Analysis and Name Binding"]
    H3P["Visitor"]

    H4["4. Type Checking and Expression Validation"]
    H4P["Visitor"]

    H5["5. Logical Plan Generation"]
    H5P["Visitor"]

    H6["6. Logical Plan Validation and Normalization"]
    H6P["None / Chain of Responsibility (Conditional)"]

    H7["7. Physical Plan Generation"]
    H7P["None / Simple Factory / Factory Method (Conditional)"]

    H8["8. Query Execution Coordination"]
    H8P["None / Facade (Conditional)"]

    H9["9. Runtime Execution Context Management"]
    H9P["None / Builder (Conditional)"]

    H10["10. Physical Operator Execution"]
    H10P["Composite + Iterator (Conditional)"]

    H11["11. Expression Evaluation"]
    H11P["Interpreter"]

    H12["12. Query Result Production"]
    H12P["None / Builder (Conditional)"]

    HIGH --> H1
    HIGH --> H2
    HIGH --> H3
    HIGH --> H4
    HIGH --> H5
    HIGH --> H6
    HIGH --> H7
    HIGH --> H8

    MEDIUM --> H9
    MEDIUM --> H10
    MEDIUM --> H11
    MEDIUM --> H12

    H1 --> H1P
    H2 --> H2P
    H3 --> H3P
    H4 --> H4P
    H5 --> H5P
    H6 --> H6P
    H7 --> H7P
    H8 --> H8P
    H9 --> H9P
    H10 --> H10P
    H11 --> H11P
    H12 --> H12P

    %% =====================================================
    %% STYLE ASSIGNMENTS
    %% =====================================================

    class ROOT rootStyle

    class HIGH,H1,H1P,H2,H2P,H3,H3P,H4,H4P highStyle
    class H5,H5P,H6,H6P,H7,H7P,H8,H8P highStyle

    class MEDIUM,H9,H9P,H10,H10P,H11,H11P,H12,H12P mediumStyle

    %% =====================================================
    %% STYLE DEFINITIONS
    %% =====================================================

    classDef rootStyle fill:#1d3557,stroke:#457b9d,stroke-width:3px,color:#ffffff,font-weight:bold

    classDef highStyle fill:#fff3e0,stroke:#ef6c00,stroke-width:2px,color:#7f2704,font-weight:bold

    classDef mediumStyle fill:#fff8e1,stroke:#f9a825,stroke-width:2px,color:#664d03,font-weight:bold
```

---

# Query Processing Feature and Design Pattern Analysis

## 8. Query Execution Coordination (Query Compilation)
### Using Facade Pattern

#### 8.1 Class Diagram
```mermaid
classDiagram
direction TB

class QueryCompiler {
    -parser : SqlParser
    -binder : Binder
    -optimizer : QueryOptimizer
    -physicalPlanner : PhysicalPlanner

    +compile(sqlText : String) PhysicalPlan
}

class SqlParser {
    +parse(sqlText : String) AST
}

class Binder {
    -catalog : Catalog
    +bind(ast : AST) LogicalPlan
}

class QueryOptimizer {
    +optimize(logicalPlan : LogicalPlan) LogicalPlan
}

class PhysicalPlanner {
    +build(logicalPlan : LogicalPlan) PhysicalPlan
}

class AST
class LogicalPlan
class PhysicalPlan

QueryCompiler --> SqlParser : uses
QueryCompiler --> Binder : uses
QueryCompiler --> QueryOptimizer : uses
QueryCompiler --> PhysicalPlanner : uses

SqlParser ..> AST : produces
Binder ..> AST : consumes
Binder ..> LogicalPlan : produces
QueryOptimizer ..> LogicalPlan : optimizes
PhysicalPlanner ..> LogicalPlan : consumes
PhysicalPlanner ..> PhysicalPlan : produces

QueryCompiler ..> PhysicalPlan : compiles into

style QueryCompiler fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style SqlParser fill:#fff3e0,stroke:#ef6c00,stroke-width:1px,color:#7f2704
style Binder fill:#fff3e0,stroke:#ef6c00,stroke-width:1px,color:#7f2704
style QueryOptimizer fill:#fff3e0,stroke:#ef6c00,stroke-width:1px,color:#7f2704
style PhysicalPlanner fill:#fff3e0,stroke:#ef6c00,stroke-width:1px,color:#7f2704

style AST fill:#ffffff,stroke:#607d8b,stroke-width:1px,color:#37474f
style LogicalPlan fill:#ffffff,stroke:#607d8b,stroke-width:1px,color:#37474f
style PhysicalPlan fill:#ffffff,stroke:#607d8b,stroke-width:1px,color:#37474f
```

#### 8.2 Sequence Diagram `shouldCompileQuery()`
```mermaid
sequenceDiagram
    autonumber

    participant Client as QueryExecutor
    participant QC as compiler : QueryCompiler
    participant P as parser : SqlParser
    participant B as binder : Binder
    participant O as optimizer : QueryOptimizer
    participant PP as physicalPlanner : PhysicalPlanner

    Client ->> QC: compile("SELECT * FROM users")
    activate QC

    QC ->> P: parse("SELECT * FROM users")
    activate P
    P -->> QC: ast : AST
    deactivate P

    QC ->> B: bind(ast)
    activate B
    Note over B: Binds table & column names to catalog metadata
    B -->> QC: logicalPlan : LogicalPlan
    deactivate B

    QC ->> O: optimize(logicalPlan)
    activate O
    Note over O: Applies heuristic & cost-based rewrite rules
    O -->> QC: optimizedPlan : LogicalPlan
    deactivate O

    QC ->> PP: build(optimizedPlan)
    activate PP
    PP -->> QC: physicalPlan : PhysicalPlan
    deactivate PP

    QC -->> Client: physicalPlan
    deactivate QC
```

### Code Example 
#### Subsystems
```java
public class SqlParser() {
    public AST parse(String sqlText) {
        return null;   
    }
}

public class Binder() {
    private Catalog catalog;

    public LogicalPlan bind(AST ast) {
        return null;
    }
}

public class QueryOptimizer() {
    public LogicalPlan optimize(LogicalPlan logicalPlan) {
        return null;
    }
}

public class PhysicalPlanner() {
    public PhysicalPlan build(LogicalPlan logicalPlan) {
        return null;
    }
}
```
#### Facade
```java
public class QueryCompier() {
    private SqlParser parser;
    private Binder binder;
    private QueryOptimizer optimizer;
    private PhysicalPlanner physicalPlanner;

    public PhysicalPlan compile(String sqlText) {
        AST ast = parser.parse(sqlText);
        LogicalPlan logicalPlan = binder.bind(ast);
        LogicalPlan optimizedPlan = optimizer.optimize(logicalPlan);
        PhysicalPlan physicalPlan = physicalPlanner.build(optimizedPlan);
        return physicalPlan;
    }
} 
```
#### Client
```java
public class QueryExecutor {
    public PhysicalPlan execute(String sqlText) {
        QueryCompier compier = new QueryCompier();
        PhysicalPlan physicalPlan = compier.compile(sqlText);
        return physicalPlan;
    }
} 
```