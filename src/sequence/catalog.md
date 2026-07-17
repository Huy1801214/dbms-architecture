# Metadata Unit Test

## Catalog Manager

### 1. shouldRegisterTable()
```mermaid
sequenceDiagram
    autonumber
    participant Test as CatalogManagerTest
    participant Catalog as CatalogManager
    participant DB as Database
    participant Tbl as Table

    Test->>Catalog: registerTable(table)

    Catalog->>DB: findDatabase()

    DB-->>Catalog: database found

    Catalog->>Tbl: validateMetadata()

    Tbl-->>Catalog: metadata valid

    Catalog->>DB: addTable(table)

    DB-->>Catalog: registration completed

    Catalog-->>Test: RegisterSuccess=true
```

### 2. shouldFindTable()
```mermaid
sequenceDiagram
    autonumber
    participant Test as CatalogManagerTest
    participant Catalog as CatalogManager
    participant DB as Database
    participant Tbl as Table

    Test->>Catalog: getTable("Student")

    Catalog->>DB: lookupTable("Student")

    DB-->>Catalog: Table reference

    Catalog->>Tbl: loadMetadata()

    Tbl-->>Catalog: metadata

    Catalog-->>Test: Table returned
```

### 3. shouldFindSchema()
```mermaid
sequenceDiagram
    autonumber
    participant Test as CatalogManagerTest
    participant Catalog as CatalogManager
    participant DB as Database
    participant Schema as Schema

    Test->>Catalog: getSchema("public")

    Catalog->>DB: lookupSchema()

    DB-->>Catalog: Schema

    Catalog->>Schema: loadMetadata()

    Schema-->>Catalog: schema metadata

    Catalog-->>Test: Schema returned
```

### 4. shouldRefreshMetadata()
```mermaid
sequenceDiagram
    autonumber
    participant Test as CatalogManagerTest
    participant Catalog as CatalogManager
    participant DB as Database

    Test->>Catalog: refreshMetadata()

    Catalog->>DB: reloadMetadata()

    DB-->>Catalog: latest metadata

    Catalog->>Catalog: updateMetadataCache()

    Catalog-->>Test: RefreshCompleted=true
```

# Metadata Integration Test 
### 5. shouldRegisterDatabaseMetadata()
```mermaid
sequenceDiagram
    autonumber
    participant Test as MetadataIntegrationTest
    participant Catalog as CatalogManager
    participant DB as Database

    Test->>Catalog: registerDatabase(database)

    Catalog->>DB: createMetadata()

    DB-->>Catalog: metadata created

    Catalog->>Catalog: updateMetadataCache()

    Catalog-->>Test: DatabaseRegistered=true
```

### 6. shouldRegisterTableMetadata()
```mermaid
sequenceDiagram
    autonumber
    participant Test as MetadataIntegrationTest
    participant Catalog as CatalogManager
    participant Schema as Schema
    participant Tbl as Table

    Test->>Catalog: registerTable(table)

    Catalog->>Schema: findSchema()

    Schema-->>Catalog: schema found

    Catalog->>Tbl: createMetadata()

    Tbl-->>Catalog: metadata ready

    Catalog->>Schema: addTable()

    Schema-->>Catalog: table added

    Catalog-->>Test: TableRegistered=true
```

### 7. shouldUpdateCatalogAfterSchemaChange()
```mermaid
sequenceDiagram
    autonumber
    participant Test as MetadataIntegrationTest
    participant Schema as Schema
    participant Catalog as CatalogManager
    participant Tbl as Table

    Test->>Schema: alterTable()

    Schema->>Tbl: updateMetadata()

    Tbl-->>Schema: updated

    Schema->>Catalog: notifySchemaChanged()

    Catalog->>Catalog: refreshMetadata()

    Catalog-->>Test: CatalogUpdated=true
```

### 8. shouldRefreshMetadataAfterDDL()
```mermaid
sequenceDiagram
    autonumber
    participant Test as MetadataIntegrationTest
    participant Catalog as CatalogManager
    participant DB as Database
    participant Schema as Schema
    participant Tbl as Table

    Test->>Catalog: executeDDL()

    Catalog->>DB: updateDatabaseMetadata()

    DB-->>Catalog: updated

    Catalog->>Schema: updateSchema()

    Schema-->>Catalog: updated

    Catalog->>Tbl: registerTable()

    Tbl-->>Catalog: metadata created

    Catalog->>Catalog: refreshMetadata()

    Catalog-->>Test: MetadataRefreshed=true
```