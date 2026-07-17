# Database Object Unit Test

## Database Test 

### 1. shouldOpenDatabase()
```mermaid
sequenceDiagram
    actor Test
    participant Database

    Test->>Database: open()
    Database-->>Test: Database opened successfully
```

### 2. shouldCloseDatabase()
```mermaid
sequenceDiagram
    actor Test
    participant Database

    Test->>Database: close()
    Database-->>Test: Database closed successfully
```
## Schema 

### 3. sequenceDiagram
```mermaid
sequenceDiagram
    actor Test
    participant Schema
    participant Table

    Test->>Schema: createTable(tableDefinition)
    Schema->>Table: create()
    Table-->>Schema: table created
    Schema-->>Test: success
```

### 4. shouldDropTable()
```mermaid
sequenceDiagram
    actor Test
    participant Schema
    participant Table

    Test->>Schema: dropTable(tableName)
    Schema->>Table: remove()
    Table-->>Schema: removed
    Schema-->>Test: success
```

### 5. shouldCreateView()
```mermaid
sequenceDiagram
    actor Test
    participant Schema
    participant View

    Test->>Schema: createView(viewDefinition)
    Schema->>View: create()
    View-->>Schema: created
    Schema-->>Test: success
```

### 6. shouldCreateStoredProcedure()
```mermaid
sequenceDiagram
    actor Test
    participant Schema
    participant StoredProcedure

    Test->>Schema: createProcedure(definition)
    Schema->>StoredProcedure: create()
    StoredProcedure-->>Schema: created
    Schema-->>Test: success
```
## Table

### 7. shouldInsertRow()
```mermaid
sequenceDiagram
    actor Test
    participant Table
    participant Row

    Test->>Table: insert(row)
    Table->>Row: create()
    Row-->>Table: inserted
    Table-->>Test: success
```

### 8. shouldUpdateRow()
```mermaid
sequenceDiagram
    actor Test
    participant Table
    participant Row

    Test->>Table: update(rowId,newValues)
    Table->>Row: update()
    Row-->>Table: updated
    Table-->>Test: success
```

### 9. shouldDeleteRow()
```mermaid
sequenceDiagram
    actor Test
    participant Table
    participant Row

    Test->>Table: delete(rowId)
    Table->>Row: delete()
    Row-->>Table: removed
    Table-->>Test: success 
```

### 10. shouldTruncateTable()
```mermaid
sequenceDiagram
    actor Test
    participant Table

    Test->>Table: truncate()
    Table-->>Test: all rows removed
```

### 11. shouldAnalyzeTable()
```mermaid
sequenceDiagram
    actor Test
    participant Table

    Test->>Table: analyze()
    Table-->>Test: statistics generated
```

## Column 

### 12. shouldValidateColumnDefinition()
```mermaid
sequenceDiagram
    actor Test
    participant Column

    Test->>Column: validateDefinition()
    Column-->>Test: valid
```

### 13. shouldUpdateColumnMetadata()
```mermaid
sequenceDiagram
    actor Test
    participant Column

    Test->>Column: updateMetadata()
    Column-->>Test: metadata updated
```
## Row

### 14. shouldCreateRow()
```mermaid
sequenceDiagram
    actor Test
    participant Row

    Test->>Row: create(values)
    Row-->>Test: row created
```

### 15. shouldUpdateRowVersion()
```mermaid
sequenceDiagram
    actor Test
    participant Row

    Test->>Row: updateVersion()
    Row-->>Test: version incremented
```

### 16. shouldDeleteRow()
```mermaid
sequenceDiagram
    actor Test
    participant Row

    Test->>Row: delete()
    Row-->>Test: row deleted
```

## Constraint

### 17. shouldValidatePrimaryKey()
```mermaid
sequenceDiagram
    actor Test
    participant PrimaryKey

    Test->>PrimaryKey: validate(row)
    PrimaryKey-->>Test: validation passed
```

### 18. shouldValidateForeignKey()
```mermaid
sequenceDiagram
    actor Test
    participant ForeignKey
    participant Table

    Test->>ForeignKey: validate(row)
    ForeignKey->>Table: lookupReferencedRow()
    Table-->>ForeignKey: row exists
    ForeignKey-->>Test: validation passed
```

### 19. shouldValidateUniqueConstraint()
```mermaid
sequenceDiagram
    actor Test
    participant UniqueConstraint
    participant Table

    Test->>UniqueConstraint: validate(value)
    UniqueConstraint->>Table: searchDuplicate()
    Table-->>UniqueConstraint: no duplicate
    UniqueConstraint-->>Test: validation passed
```

### 20. shouldValidateCheckConstraint()
```mermaid
sequenceDiagram
    actor Test
    participant CheckConstraint

    Test->>CheckConstraint: validate(row)
    CheckConstraint-->>Test: expression satisfied
```

## Index 

### 21. shouldInsertKey()
```mermaid
sequenceDiagram
    actor Test
    participant Index

    Test->>Index: insertKey(key,rowId)
    Index-->>Test: key inserted
```

### 22. shouldSearchKey()
```mermaid
sequenceDiagram
    actor Test
    participant Index

    Test->>Index: search(key)
    Index-->>Test: rowId
```

## 23. shouldDeleteKey()
```mermaid
sequenceDiagram
    actor Test
    participant Index

    Test->>Index: deleteKey(key)
    Index-->>Test: key removed
```

## 24. shouldRebuildIndex()
```mermaid
sequenceDiagram
    actor Test
    participant Index
    participant Table

    Test->>Index: rebuild()
    Index->>Table: scanRows()
    Table-->>Index: rows
    Index-->>Test: rebuild completed
```

## Partition 

### 25. shouldPartitionTable()
```mermaid
sequenceDiagram
    actor Test
    participant Partition
    participant Table

    Test->>Partition: createPartition()
    Partition->>Table: reorganizeRows()
    Table-->>Partition: completed
    Partition-->>Test: success
```

### 26. shouldLocatePartition()
```mermaid
sequenceDiagram
    actor Test
    participant Partition

    Test->>Partition: locatePartition(key)
    Partition-->>Test: target partition
```

## View

### 27. shouldExecuteViewQuery()
```mermaid 
sequenceDiagram
    actor Test
    participant View
    participant Table

    Test->>View: executeQuery()
    View->>Table: readRows()
    Table-->>View: result set
    View-->>Test: rows returned
```

### 28. shouldRefreshViewDefinition()
```mermaid
sequenceDiagram
    actor Test
    participant View

    Test->>View: refreshDefinition()
    View-->>Test: refreshed
```

## Store Procedure

### 29. shouldExecuteProcedure()
```mermaid
sequenceDiagram
    actor Test
    participant StoredProcedure

    Test->>StoredProcedure: execute()
    StoredProcedure-->>Test: execution completed
```

### 30. shouldPassProcedureParameters()
```mermaid
sequenceDiagram
    actor Test
    participant StoredProcedure

    Test->>StoredProcedure: execute(parameters)
    StoredProcedure-->>Test: execution completed
```

## Trigger 

### 31. shouldFireTrigger()
```mermaid
sequenceDiagram
    actor Test
    participant Trigger

    Test->>Trigger: fire()
    Trigger-->>Test: trigger executed
```

### 32. shouldExecuteBeforeInsertTrigger()
```mermaid
sequenceDiagram
    actor Test
    participant Table
    participant Trigger

    Test->>Table: insert(row)
    Table->>Trigger: BEFORE INSERT
    Trigger-->>Table: validation completed
    Table-->>Test: row inserted 
```

### 33. shouldExecuteAfterUpdateTrigger()
```mermaid
sequenceDiagram
    actor Test
    participant Table
    participant Trigger

    Test->>Table: update(row)
    Table->>Trigger: AFTER UPDATE
    Trigger-->>Table: post-processing completed
    Table-->>Test: update committed
```
## Sequence 

### 34. shouldGenerateNextValue()
```mermaid
sequenceDiagram
    actor Test
    participant Sequence

    Test->>Sequence: nextValue()
    Sequence-->>Test: next sequence value
``` 

## 35. shouldIncrementSequence()
```mermaid
sequenceDiagram
    actor Test
    participant Sequence

    Test->>Sequence: increment()
    Sequence-->>Test: current value increased
```

# Database Object Integration Test 

### 1. shouldCreateDatabaseWithSchemaAndTable()
```mermaid
sequenceDiagram
    actor Test

    participant Database
    participant Schema
    participant Table

    Test->>Database: open()

    Database->>Schema: createSchema("public")
    Schema-->>Database: schema created

    Database->>Schema: createTable(tableDefinition)
    Schema->>Table: create()
    Table-->>Schema: table created

    Schema-->>Database: success
    Database-->>Test: database initialized
```

### 2. shouldInsertRowWithConstraints()
```mermaid
sequenceDiagram
    actor Test

    participant Table
    participant PrimaryKey
    participant ForeignKey
    participant UniqueConstraint
    participant CheckConstraint
    participant Row

    Test->>Table: insert(row)

    Table->>PrimaryKey: validate(row)
    PrimaryKey-->>Table: valid

    Table->>ForeignKey: validate(row)
    ForeignKey-->>Table: valid

    Table->>UniqueConstraint: validate(row)
    UniqueConstraint-->>Table: valid

    Table->>CheckConstraint: validate(row)
    CheckConstraint-->>Table: valid

    Table->>Row: create()
    Row-->>Table: inserted

    Table-->>Test: insert success
```

### 3. shouldUseIndexForQueryExecution()
```mermaid
sequenceDiagram
    actor Test

    participant Table
    participant BTreeIndex
    participant Row

    Test->>Table: findByPrimaryKey(id)

    Table->>BTreeIndex: search(id)
    BTreeIndex-->>Table: row location

    Table->>Row: load(location)
    Row-->>Table: row data

    Table-->>Test: row returned
```
### 4. shouldExecuteTriggerDuringInsert()
```mermaid
sequenceDiagram
    actor Test

    participant Table
    participant Trigger
    participant Row

    Test->>Table: insert(row)

    Table->>Trigger: BEFORE INSERT
    Trigger-->>Table: validation completed

    Table->>Row: create()
    Row-->>Table: inserted

    Table->>Trigger: AFTER INSERT
    Trigger-->>Table: audit completed

    Table-->>Test: insert success
```
### 5. shouldExecuteStoredProcedureSuccessfully()
```mermaid
sequenceDiagram
    actor Test

    participant StoredProcedure
    participant Table
    participant Row

    Test->>StoredProcedure: execute(parameters)

    StoredProcedure->>Table: updateRows()

    Table->>Row: update()
    Row-->>Table: updated

    Table-->>StoredProcedure: success
    StoredProcedure-->>Test: execution completed
```
### 6. shouldReadDataFromView()
```mermaid
sequenceDiagram
    actor Test

    participant View
    participant Table
    participant Row

    Test->>View: executeQuery()

    View->>Table: scanRows()

    Table->>Row: read()
    Row-->>Table: data

    Table-->>View: result set
    View-->>Test: rows returned
```
### 7. shouldGenerateSequenceValueForInsert()
```mermaid
sequenceDiagram
    actor Test

    participant Sequence
    participant Table
    participant Row

    Test->>Sequence: nextValue()
    Sequence-->>Test: generated id

    Test->>Table: insert(row)

    Table->>Row: assignId(sequenceValue)
    Row-->>Table: row ready

    Table-->>Test: insert success
```




