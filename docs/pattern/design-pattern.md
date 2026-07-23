## Danh sách ưu tiên tổng quát

| Priority | Core Feature | Mức độ ưu tiên | Design Pattern phù hợp |
|---:|---|---|---|
| 1 | Database Object Hierarchy and Lifecycle Management | Bắt buộc | Composite |
| 2 | Table Definition and Construction | Bắt buộc | Builder |
| 3 | Column Definition and Data Type Management | Bắt buộc | Factory Method |
| 4 | Constraint Definition and Validation | Bắt buộc | Strategy, Factory Method |
| 5 | Database Object Creation | Bắt buộc | Factory Method |
| 6 | Object Lookup, Naming and Uniqueness Validation | Bắt buộc | Specification, Repository |
| 7 | Index Definition and Index Type Selection | Quan trọng | Strategy, Factory Method |
| 8 | Table Data Operations | Quan trọng | Command, Template Method |
| 9 | View Management | Quan trọng | Composite, Specification |
| 10 | Sequence Management | Cần thiết | State, Strategy |
| 11 | Schema Object Traversal and Metadata Export | Hỗ trợ | Iterator, Visitor |
| 12 | Partition and Trigger Management | Nâng cao | Strategy, Observer, Command |

# 1. Database Object Hierarchy and Lifecycle Management
## Using Composite Pattern

## 1.1 Class Diagram
```mermaid
classDiagram
direction TB

%% =====================================================
%% Composite Pattern - Database Catalog Structure
%% =====================================================

class DatabaseComponent {
    <<interface>>

    +getId() UUID
    +getName() String
    +getOwner() String
    +getQualifiedName() String
}

%% =====================================================
%% Composite: Database
%% =====================================================

class Database {
    -databaseId : UUID
    -name : String
    -owner : String
    -status : DatabaseStatus
    -schemas : List~Schema~

    +addSchema(schema : Schema) void
    +removeSchema(schemaId : UUID) void
    +findSchema(name : String) Schema
    +listSchemas() List~Schema~

    +getId() UUID
    +getName() String
    +getOwner() String
    +getQualifiedName() String
}

class DatabaseStatus {
    <<enumeration>>

    ONLINE
    OFFLINE
    OPENING
    CLOSING
}

%% =====================================================
%% Composite: Schema
%% =====================================================

class Schema {
    -schemaId : UUID
    -name : String
    -owner : String
    -objects : List~DatabaseObject~

    +addObject(object : DatabaseObject) void
    +removeObject(objectId : UUID) void
    +findObject(name : String) DatabaseObject
    +listObjects() List~DatabaseObject~

    +getId() UUID
    +getName() String
    +getOwner() String
    +getQualifiedName() String
}

%% =====================================================
%% Abstract Leaf
%% =====================================================

class DatabaseObject {
    <<abstract>>

    #objectId : UUID
    #name : String
    #owner : String
    #schemaId : UUID

    +getId() UUID
    +getName() String
    +getOwner() String
    +getQualifiedName() String
    +rename(newName : String) void
}

%% =====================================================
%% Concrete Leaves
%% =====================================================

class Table {
    +tableId : UUID
    +engine : String
    +rowCount : Long
}

class View {
    +queryDefinition : String
}

class StoredProcedure {
    +code : String
}

class Sequence {
    +currentValue : Long
}

%% =====================================================
%% Composite Relationships
%% =====================================================

DatabaseComponent <|.. Database
DatabaseComponent <|.. Schema
DatabaseComponent <|.. DatabaseObject

DatabaseObject <|-- Table
DatabaseObject <|-- View
DatabaseObject <|-- StoredProcedure
DatabaseObject <|-- Sequence

Database *--> "0..*" Schema : contains
Schema *--> "0..*" DatabaseObject : contains

Database --> DatabaseStatus

%% =====================================================
%% Styling
%% =====================================================

style DatabaseComponent fill:#fff3e0,stroke:#ef6c00,stroke-width:2px,color:#7f2704

style Database fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style DatabaseStatus fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style Schema fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298

style DatabaseObject fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Table fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style View fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style StoredProcedure fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Sequence fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
```

## 1.2 Sequence Diagram shouldCreateDatabaseWithSchemaAndTable()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Client / Test Suite
    participant Test as DatabaseObjectsIntegrationTest
    end

    box #e3f2fd Composite Root (Database)
    participant DB as db : Database
    end

    box #fff3e0 Composite Child (Schema)
    participant S as schema : Schema
    end

    box #e8f5e9 Leaf Component (Table)
    participant T as table : Table
    end

    Note over Test,T: 1. Arrange Composite Hierarchy Components
    Test->>DB: new Database("db-001", "HuyDB", "admin", ONLINE, createdAt)
    Test->>S: new Schema("schema-001", "StudentSchema", "admin")
    Test->>T: new Table("tbl-001", "users", "InnoDB")

    Note over Test,T: 2. Act: Build Composite Tree (Database -> Schema -> Table)
    Test->>S: addObject(table)
    activate S
    S->>S: objects.add(table)
    S-->>Test: void
    deactivate S

    Test->>DB: addSchema(schema)
    activate DB
    DB->>DB: schemas.add(schema)
    DB-->>Test: void
    deactivate DB

    Note over Test,T: 3. Act: Traverse & Access Uniform DatabaseComponent Interface
    Test->>DB: findSchema("StudentSchema")
    activate DB
    DB-->>Test: schema : Schema
    deactivate DB

    Test->>S: findObject("users")
    activate S
    S-->>Test: table : DatabaseObject
    deactivate S

    Test->>T: getQualifiedName()
    activate T
    T-->>Test: "HuyDB.StudentSchema.users"
    deactivate T

    Note over Test,T: 4. Assert Composite Hierarchy Output
    Test->>Test: assertNotNull(schema)
    Test->>Test: assertNotNull(table)
    Test->>Test: assertEquals("HuyDB.StudentSchema.users", table.getQualifiedName())
```

# 2. Table Definition and Construction
## Using Builder Pattern 

## 2.1 Class Diagram
```mermaid
classDiagram
direction TB

%% =====================================================
%% Product
%% =====================================================

class Table{
    -tableId : UUID
    -name : String
    -engine : String
    -rowCount : Long

    -columns : List~Column~
    -constraints : List~Constraint~
    -indexes : List~Index~
    -partitions : List~Partition~
    -triggers : List~Trigger~

    +insert()
    +update()
    +delete()
    +truncate()
    +analyze()
}

%% =====================================================
%% Builder Pattern - Simplified Builder
%% =====================================================

class TableBuilder{
    -tableId : UUID
    -name : String
    -engine : String

    -columns : List~Column~
    -constraints : List~Constraint~
    -indexes : List~Index~
    -partitions : List~Partition~
    -triggers : List~Trigger~

    +setName(name : String) TableBuilder
    +setEngine(engine : String) TableBuilder

    +addColumn(column : Column) TableBuilder
    +addConstraint(constraint : Constraint) TableBuilder
    +addIndex(index : Index) TableBuilder
    +addPartition(partition : Partition) TableBuilder
    +addTrigger(trigger : Trigger) TableBuilder

    +build() Table
    -validate() void
}

TableBuilder ..> Table : builds

%% =====================================================
%% Components
%% =====================================================

class Column
class Constraint
class Index
class Partition
class Trigger

Table *--> "1..*" Column : contains
Table *--> "0..*" Constraint : contains
Table *--> "0..*" Index : contains
Table *--> "0..*" Partition : contains
Table *--> "0..*" Trigger : contains

%% =====================================================
%% Styling
%% =====================================================

style Table fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132

style Column fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Constraint fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Index fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Partition fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Trigger fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132

style TableBuilder fill:#fff3e0,stroke:#ef6c00,stroke-width:2px,color:#7f2704
```
## 2.2 Sequence Diagram
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite
        participant Test as SchemaTest
    end

    box #e3f2fd Schema
        participant S as Schema
    end

    box #fff3e0 Factory
        participant F as DefaultDatabaseObjectFactory
    end

    box #fff8e1 Builder
        participant B as TableBuilder
    end

    box #e8f5e9 Domain
        participant T as Table
    end

    Note over Test,S: Arrange
    Test->>Test: create CreateTableRequest

    Note over Test,S: Act
    Test->>S: createTable(request)
    activate S

    S->>S: ensureObjectNameIsUnique(request.name)

    S->>F: createTable(request)
    activate F

    F->>B: new TableBuilder()
    activate B
    B-->>F: builder
    deactivate B

    F->>B: setName(request.name)
    activate B
    B-->>F: this
    deactivate B

    F->>B: setEngine(request.engine)
    activate B
    B-->>F: this
    deactivate B

    loop For each column
        F->>B: addColumn(column)
        activate B
        B-->>F: this
        deactivate B
    end

    loop For each constraint
        F->>B: addConstraint(constraint)
        activate B
        B-->>F: this
        deactivate B
    end

    loop For each index
        F->>B: addIndex(index)
        activate B
        B-->>F: this
        deactivate B
    end

    loop For each partition
        F->>B: addPartition(partition)
        activate B
        B-->>F: this
        deactivate B
    end

    loop For each trigger
        F->>B: addTrigger(trigger)
        activate B
        B-->>F: this
        deactivate B
    end

    F->>B: build()
    activate B

    B->>B: validate()

    B->>T: new Table(builder)
    activate T
    T-->>B: table
    deactivate T

    B-->>F: table
    deactivate B

    F-->>S: table
    deactivate F

    S->>S: addObject(table)

    S-->>Test: table
    deactivate S

    Note over Test,T: Assert
    Test->>T: getName()
    T-->>Test: expected name

    Test->>T: getColumns()
    T-->>Test: expected columns
```

# 4. Constraint Definition and Validation
## Using Strategy, Factory Method

## 4.1 Class Diagram
```mermaid
classDiagram
direction TB

%% =====================================================
%% Client
%% =====================================================

class Table{
    +tableId : UUID
    +engine : String
    +rowCount : Long

    -constraints : List~Constraint~
    -factory : ConstraintFactory

    +addPrimaryKey(request)
    +addForeignKey(request)
    +addUniqueConstraint(request)
    +addCheckConstraint(request)

    +removeConstraint(name)
    +validateConstraints(row)
}

%% =====================================================
%% Strategy Pattern
%% =====================================================

class Constraint{
    <<abstract>>

    +constraintId : UUID
    +constraintName : String
    +constraintType : ConstraintType
    +tableId : UUID
    +columns : List~Column~
    +status : ConstraintStatus
    +enabled : Boolean
    +validated : Boolean
    +deferrable : Boolean
    +initiallyDeferred : Boolean
    +owner : String
    +description : String
    +createdAt : Timestamp
    +modifiedAt : Timestamp

    +validate(row, table)* void
}

class ConstraintType{
    <<enumeration>>
}

class ConstraintStatus{
    <<enumeration>>
}

class PrimaryKey{
    +validate(row, table) void
}

class ForeignKey{
    +referenceTable
    +referenceColumns

    +validate(row, table) void
}

class UniqueConstraint{
    +validate(row, table) void
}

class CheckConstraint{
    +expression

    +validate(row, table) void
}

Constraint <|-- PrimaryKey
Constraint <|-- ForeignKey
Constraint <|-- UniqueConstraint
Constraint <|-- CheckConstraint
Constraint --> ConstraintType
Constraint --> ConstraintStatus

%% =====================================================
%% Factory Method
%% =====================================================

class ConstraintFactory{
    <<interface>>

    +createPrimaryKey(request) PrimaryKey
    +createForeignKey(request) ForeignKey
    +createUnique(request) UniqueConstraint
    +createCheck(request) CheckConstraint
}

class DefaultConstraintFactory{
    +createPrimaryKey(request) PrimaryKey
    +createForeignKey(request) ForeignKey
    +createUnique(request) UniqueConstraint
    +createCheck(request) CheckConstraint
}

ConstraintFactory <|.. DefaultConstraintFactory

Table --> ConstraintFactory

DefaultConstraintFactory ..> PrimaryKey
DefaultConstraintFactory ..> ForeignKey
DefaultConstraintFactory ..> UniqueConstraint
DefaultConstraintFactory ..> CheckConstraint

%% =====================================================
%% Association
%% =====================================================

Table --> Constraint

ForeignKey --> Table

%% =====================================================
%% Styling
%% =====================================================

style Table fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298

style Constraint fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style ConstraintType fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style ConstraintStatus fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style PrimaryKey fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style ForeignKey fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style UniqueConstraint fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style CheckConstraint fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132

style ConstraintFactory fill:#fff3e0,stroke:#ef6c00,stroke-width:2px,color:#7f2704
style DefaultConstraintFactory fill:#fff3e0,stroke:#ef6c00,stroke-width:2px,color:#7f2704
```
## 4.2 Sequence Diagram
```mermaid
sequenceDiagram
    autonumber

    participant Test as PrimaryKeyValidationTest
    participant Factory as DefaultConstraintFactory
    participant Table as Table (Context)
    participant PK as PrimaryKey (Concrete Strategy)
    participant Row

    Note over Test,Row: Arrange

    Test->>Factory: createPrimaryKey(request)
    Factory->>PK: new PrimaryKey(...)
    PK-->>Factory: primaryKey
    Factory-->>Test: primaryKey

    Test->>Table: addConstraint(primaryKey)
    Test->>Row: new Row()
    Test->>Row: setValue("id", 1)

    Note over Test,Row: Act and Assert

    Test->>Table: assertDoesNotThrow(() -> validate(row))

    loop Every enabled Constraint
        Table->>PK: validate(row, table)
        PK->>Row: getValue("id")
        Row-->>PK: 1
        PK->>Table: existsPrimaryKey(1)
        Table-->>PK: false
        PK-->>Table: validation completed
    end

    Table-->>Test: completed without exception
```

--- 
```mermaid
sequenceDiagram
    autonumber

    participant Test as ForeignKeyValidationTest
    participant Factory as DefaultConstraintFactory
    participant Table as Table (Context)
    participant FK as ForeignKey (Concrete Strategy)
    participant ParentTable as ParentTable (Referenced Context)
    participant ChildRow

    Note over Test,ChildRow: Arrange

    Test->>Factory: createForeignKey(request)
    Factory->>FK: new ForeignKey(...)
    FK-->>Factory: foreignKey
    Factory-->>Test: foreignKey

    Test->>Table: addConstraint(foreignKey)
    Test->>ChildRow: new Row()
    Test->>ChildRow: setValue("user_id", "parent-001")

    Note over Test,ChildRow: Act and Assert

    Test->>Table: assertDoesNotThrow(() -> validate(row))

    loop Every enabled Constraint
        Table->>FK: validate(row, parentTable)
        FK->>ChildRow: getValue("user_id")
        ChildRow-->>FK: "parent-001"
        FK->>ParentTable: existsRow("parent-001")
        ParentTable-->>FK: true
        FK-->>Table: validation completed
    end

    Table-->>Test: completed without exception
```

# 5. Database Object Creation
## Using Abstract Factory 

## 5.1 Class Diagram
```mermaid
classDiagram
direction TB

%% =====================================================
%% Client
%% =====================================================

class Schema {
    -schemaId : UUID
    -name : String
    -owner : String
    -objects : List~DatabaseObject~
    -factory : DatabaseObjectFactory

    +createTable(request) Table
    +createView(request) View
    +createProcedure(request) StoredProcedure
    +createSequence(request) Sequence

    +removeObject(objectId : UUID) void
    +findObject(name : String) DatabaseObject
    +listObjects() List~DatabaseObject~
}

%% =====================================================
%% Abstract Factory
%% =====================================================

class DatabaseObjectFactory {
    <<interface>>

    +createTable(request) Table
    +createView(request) View
    +createProcedure(request) StoredProcedure
    +createSequence(request) Sequence
}

class DefaultDatabaseObjectFactory {
    +createTable(request) Table
    +createView(request) View
    +createProcedure(request) StoredProcedure
    +createSequence(request) Sequence
}

DatabaseObjectFactory <|.. DefaultDatabaseObjectFactory
Schema --> DatabaseObjectFactory : uses

%% =====================================================
%% Abstract Product
%% =====================================================

class DatabaseObject {
    <<abstract>>

    #objectId : UUID
    #name : String
    #owner : String
    #schemaId : UUID

    +getId() UUID
    +getName() String
    +getOwner() String
    +getQualifiedName() String
    +rename(newName : String) void
}

%% =====================================================
%% Concrete Products
%% =====================================================

class Table {
    -engine : String
    -rowCount : Long
}

class View {
    -queryDefinition : String
}

class StoredProcedure {
    -procedureDefinition : String
}

class Sequence {
    -currentValue : Long
    -incrementValue : Long

    +nextValue() Long
}

DatabaseObject <|-- Table
DatabaseObject <|-- View
DatabaseObject <|-- StoredProcedure
DatabaseObject <|-- Sequence

%% Schema owns the created products
Schema *--> "0..*" DatabaseObject : contains

%% Concrete factory creates concrete products
DefaultDatabaseObjectFactory ..> Table : creates
DefaultDatabaseObjectFactory ..> View : creates
DefaultDatabaseObjectFactory ..> StoredProcedure : creates
DefaultDatabaseObjectFactory ..> Sequence : creates

%% =====================================================
%% Styling
%% =====================================================

style Schema fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298

style DatabaseObjectFactory fill:#fff3e0,stroke:#ef6c00,stroke-width:2px,color:#7f2704
style DefaultDatabaseObjectFactory fill:#fff3e0,stroke:#ef6c00,stroke-width:2px,color:#7f2704

style DatabaseObject fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Table fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style View fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style StoredProcedure fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Sequence fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
```

## 5.2 Sequence Diagram
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Test Suite / Client
        participant Test as SchemaTest
    end

    box #e3f2fd Context
        participant S as Schema (Client)
    end

    box #fff3e0 Abstract Factory
        participant F as DefaultDatabaseObjectFactory
    end

    box #e8f5e9 Concrete Products
        participant T as Table
        participant V as View
        participant P as StoredProcedure
        participant Seq as Sequence
    end

    Note over Test,Seq: 1. Create Table via Abstract Factory
    Test->>S: createTable(tableRequest)
    activate S
    S->>F: createTable(tableRequest)
    activate F
    F->>T: new Table(request)
    activate T
    T-->>F: table
    deactivate T
    F-->>S: table
    deactivate F
    S->>S: addObject(table)
    S-->>Test: table
    deactivate S

    Note over Test,Seq: 2. Create View via Abstract Factory
    Test->>S: createView(viewRequest)
    activate S
    S->>F: createView(viewRequest)
    activate F
    F->>V: new View(request)
    activate V
    V-->>F: view
    deactivate V
    F-->>S: view
    deactivate F
    S->>S: addObject(view)
    S-->>Test: view
    deactivate S

    Note over Test,Seq: 3. Create Stored Procedure via Abstract Factory
    Test->>S: createProcedure(procedureRequest)
    activate S
    S->>F: createProcedure(procedureRequest)
    activate F
    F->>P: new StoredProcedure(request)
    activate P
    P-->>F: procedure
    deactivate P
    F-->>S: procedure
    deactivate F
    S->>S: addObject(procedure)
    S-->>Test: procedure
    deactivate S

    Note over Test,Seq: 4. Create Sequence via Abstract Factory
    Test->>S: createSequence(sequenceRequest)
    activate S
    S->>F: createSequence(sequenceRequest)
    activate F
    F->>Seq: new Sequence(request)
    activate Seq
    Seq-->>F: sequence
    deactivate Seq
    F-->>S: sequence
    deactivate F
    S->>S: addObject(sequence)
    S-->>Test: sequence
    deactivate S
```

# 11. Schema Object Traversal and Metadata Export
## Using Iterator, Visitor Pattern

# 11.1 Class Diagram
```mermaid
classDiagram
direction TB

%% =====================================================
%% Client & Elements
%% =====================================================

class Schema {
    +schemaId
    +name
    +owner
    -objects : List~DatabaseObject~
    +iterator() DatabaseObjectIterator
    +accept(visitor : DatabaseObjectVisitor) void
}

class DatabaseObject {
    <<abstract>>
    #objectId
    #name
    #owner
    +accept(visitor : DatabaseObjectVisitor)* void
}

class Table
class View
class StoredProcedure
class Sequence

DatabaseObject <|-- Table
DatabaseObject <|-- View
DatabaseObject <|-- StoredProcedure
DatabaseObject <|-- Sequence

%% =====================================================
%% Iterator Pattern
%% =====================================================

class DatabaseObjectIterator {
    <<interface>>

    +hasNext() boolean
    +next() DatabaseObject
}

class SchemaObjectIterator {
    -objects : List~DatabaseObject~
    -currentIndex : int

    +SchemaObjectIterator(objects)
    +hasNext() boolean
    +next() DatabaseObject
}

DatabaseObjectIterator <|.. SchemaObjectIterator

Schema ..> SchemaObjectIterator : creates
SchemaObjectIterator --> DatabaseObject : iterates

%% =====================================================
%% Visitor Pattern
%% =====================================================

class DatabaseObjectVisitor {
    <<interface>>

    +visit(table : Table) void
    +visit(view : View) void
    +visit(procedure : StoredProcedure) void
    +visit(sequence : Sequence) void
}

class ExportDDLVisitor {
    -result : StringBuilder

    +visit(table : Table) void
    +visit(view : View) void
    +visit(procedure : StoredProcedure) void
    +visit(sequence : Sequence) void

    +getResult() String
}

DatabaseObjectVisitor <|.. ExportDDLVisitor

Schema ..> DatabaseObjectVisitor : accepts
DatabaseObject ..> DatabaseObjectVisitor : accepts

DatabaseObjectVisitor ..> Table : visits
DatabaseObjectVisitor ..> View : visits
DatabaseObjectVisitor ..> StoredProcedure : visits
DatabaseObjectVisitor ..> Sequence : visits

%% =====================================================
%% Styling
%% =====================================================

style Schema fill:#e3f2fd,stroke:#1565c0,stroke-width:2px,color:#084298
style DatabaseObject fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Table fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style View fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style StoredProcedure fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132
style Sequence fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#0f5132

style DatabaseObjectIterator fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px,color:#4a148c
style SchemaObjectIterator fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px,color:#4a148c

style DatabaseObjectVisitor fill:#fce4ec,stroke:#c2185b,stroke-width:2px,color:#880e4f
style ExportDDLVisitor fill:#fce4ec,stroke:#c2185b,stroke-width:2px,color:#880e4f
```

# 11.2 Sequence Diagram

### 1. shouldTraverseSchemaObjectsUsingIterator() 
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Client / Test Suite
    participant Test as SchemaTest
    end

    box #e8f5e9 Schema Component
    participant S as Schema
    participant I as SchemaObjectIterator
    participant O as DatabaseObject
    end

    Note over Test,O: 1. Request Iterator from Schema
    Test->>S: iterator()
    activate S
    S->>I: new SchemaObjectIterator(objects)
    activate I
    I-->>S: iterator
    deactivate I
    S-->>Test: DatabaseObjectIterator
    deactivate S

    Note over Test,O: 2. Iterate through Database Objects
    loop While iterator.hasNext()
        Test->>I: hasNext()
        activate I
        I-->>Test: true
        deactivate I

        Test->>I: next()
        activate I
        I-->>Test: obj : DatabaseObject
        deactivate I

        Test->>O: process(obj)
    end
```

### 2. shouldExportSchemaDDLUsingVisitor()
```mermaid
sequenceDiagram
    autonumber

    box #e1f5fe Client / Test Suite
    participant Test as SchemaTest
    end

    box #fce4ec Visitor Component
    participant V as ExportDDLVisitor
    end

    box #e8f5e9 Catalog Component
    participant S as Schema
    participant T as Table
    participant W as View
    end

    Note over Test,W: 1. Create Visitor and Accept Schema
    Test->>V: new ExportDDLVisitor()
    Test->>S: accept(visitor)
    activate S

    Note over S,W: 2. Double Dispatch to Table and View
    S->>T: accept(visitor)
    activate T
    T->>V: visit(this [Table])
    activate V
    V->>V: append("CREATE TABLE ...")
    V-->>T: void
    deactivate V
    T-->>S: void
    deactivate T

    S->>W: accept(visitor)
    activate W
    W->>V: visit(this [View])
    activate V
    V->>V: append("CREATE VIEW ...")
    V-->>W: void
    deactivate V
    W-->>S: void
    deactivate W

    S-->>Test: void
    deactivate S

    Note over Test,V: 3. Retrieve DDL Result
    Test->>V: getResult()
    activate V
    V-->>Test: ddlString
    deactivate V
```