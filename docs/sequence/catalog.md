Catalog Module Unit Test

## CatalogManagerTest

### 1. shouldRegisterDatabase()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogManagerTest
    end
    box #e8f5e9 CatalogManager Component
    participant CM as CatalogManager
    end

    Test->>CM: shouldRegisterDatabase()
    CM-->>Test: success
```

### 2. shouldRegisterSchema()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogManagerTest
    end
    box #e8f5e9 CatalogManager Component
    participant CM as CatalogManager
    end

    Test->>CM: shouldRegisterSchema()
    CM-->>Test: success
```

### 3. shouldRegisterTable()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogManagerTest
    end
    box #e8f5e9 CatalogManager Component
    participant CM as CatalogManager
    end

    Test->>CM: shouldRegisterTable()
    CM-->>Test: success
```

### 4. shouldRegisterIndex()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogManagerTest
    end
    box #e8f5e9 CatalogManager Component
    participant CM as CatalogManager
    end

    Test->>CM: shouldRegisterIndex()
    CM-->>Test: success
```

### 5. shouldFindDatabase()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogManagerTest
    end
    box #e8f5e9 CatalogManager Component
    participant CM as CatalogManager
    end

    Test->>CM: shouldFindDatabase()
    CM-->>Test: success
```

### 6. shouldFindSchema()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogManagerTest
    end
    box #e8f5e9 CatalogManager Component
    participant CM as CatalogManager
    end

    Test->>CM: shouldFindSchema()
    CM-->>Test: success
```

### 7. shouldFindTable()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogManagerTest
    end
    box #e8f5e9 CatalogManager Component
    participant CM as CatalogManager
    end

    Test->>CM: shouldFindTable()
    CM-->>Test: success
```

### 8. shouldFindIndex()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogManagerTest
    end
    box #e8f5e9 CatalogManager Component
    participant CM as CatalogManager
    end

    Test->>CM: shouldFindIndex()
    CM-->>Test: success
```

### 9. shouldRefreshMetadata()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogManagerTest
    end
    box #e8f5e9 CatalogManager Component
    participant CM as CatalogManager
    end

    Test->>CM: shouldRefreshMetadata()
    CM-->>Test: success
```

### 10. shouldInvalidateMetadataCache()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogManagerTest
    end
    box #e8f5e9 CatalogManager Component
    participant CM as CatalogManager
    end

    Test->>CM: shouldInvalidateMetadataCache()
    CM-->>Test: success
```

### 11. shouldLoadMetadataFromDisk()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogManagerTest
    end
    box #e8f5e9 CatalogManager Component
    participant CM as CatalogManager
    end

    Test->>CM: shouldLoadMetadataFromDisk()
    CM-->>Test: success
```

### 12. shouldCacheFrequentlyUsedMetadata()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogManagerTest
    end
    box #e8f5e9 CatalogManager Component
    participant CM as CatalogManager
    end

    Test->>CM: shouldCacheFrequentlyUsedMetadata()
    CM-->>Test: success
```

### 13. shouldUpdateTableMetadata()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogManagerTest
    end
    box #e8f5e9 CatalogManager Component
    participant CM as CatalogManager
    end

    Test->>CM: shouldUpdateTableMetadata()
    CM-->>Test: success
```

### 14. shouldUpdateIndexMetadata()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogManagerTest
    end
    box #e8f5e9 CatalogManager Component
    participant CM as CatalogManager
    end

    Test->>CM: shouldUpdateIndexMetadata()
    CM-->>Test: success
```

### 15. shouldRemoveTableMetadata()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogManagerTest
    end
    box #e8f5e9 CatalogManager Component
    participant CM as CatalogManager
    end

    Test->>CM: shouldRemoveTableMetadata()
    CM-->>Test: success
```

### 16. shouldRemoveSchemaMetadata()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogManagerTest
    end
    box #e8f5e9 CatalogManager Component
    participant CM as CatalogManager
    end

    Test->>CM: shouldRemoveSchemaMetadata()
    CM-->>Test: success
```

### 17. shouldRemoveDatabaseMetadata()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogManagerTest
    end
    box #e8f5e9 CatalogManager Component
    participant CM as CatalogManager
    end

    Test->>CM: shouldRemoveDatabaseMetadata()
    CM-->>Test: success
```

### 18. shouldDetectDuplicateTableRegistration()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogManagerTest
    end
    box #e8f5e9 CatalogManager Component
    participant CM as CatalogManager
    end

    Test->>CM: shouldDetectDuplicateTableRegistration()
    CM-->>Test: success
```

### 19. shouldRejectUnknownDatabase()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogManagerTest
    end
    box #e8f5e9 CatalogManager Component
    participant CM as CatalogManager
    end

    Test->>CM: shouldRejectUnknownDatabase()
    CM-->>Test: success
```

### 20. shouldReturnCachedMetadata()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogManagerTest
    end
    box #e8f5e9 CatalogManager Component
    participant CM as CatalogManager
    end

    Test->>CM: shouldReturnCachedMetadata()
    CM-->>Test: success
```

# Metadata Unit Test

### 1. shouldRegisterDatabaseMetadata()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogModuleIntegrationTest
    end
    box #e8f5e9 Catalog Module Components
    participant System as System
    end

    Test->>System: shouldRegisterDatabaseMetadata()
    System-->>Test: success
```

### 2. shouldRegisterSchemaMetadata()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogModuleIntegrationTest
    end
    box #e8f5e9 Catalog Module Components
    participant System as System
    end

    Test->>System: shouldRegisterSchemaMetadata()
    System-->>Test: success
```

### 3. shouldRegisterTableMetadata()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogModuleIntegrationTest
    end
    box #e8f5e9 Catalog Module Components
    participant System as System
    end

    Test->>System: shouldRegisterTableMetadata()
    System-->>Test: success
```

### 4. shouldRegisterIndexMetadata()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogModuleIntegrationTest
    end
    box #e8f5e9 Catalog Module Components
    participant System as System
    end

    Test->>System: shouldRegisterIndexMetadata()
    System-->>Test: success
```

### 5. shouldUpdateCatalogAfterSchemaChange()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogModuleIntegrationTest
    end
    box #e8f5e9 Catalog Module Components
    participant System as System
    end

    Test->>System: shouldUpdateCatalogAfterSchemaChange()
    System-->>Test: success
```

### 6. shouldRefreshMetadataAfterDDL()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogModuleIntegrationTest
    end
    box #e8f5e9 Catalog Module Components
    participant System as System
    end

    Test->>System: shouldRefreshMetadataAfterDDL()
    System-->>Test: success
```

### 7. shouldSynchronizeMetadataCache()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogModuleIntegrationTest
    end
    box #e8f5e9 Catalog Module Components
    participant System as System
    end

    Test->>System: shouldSynchronizeMetadataCache()
    System-->>Test: success
```

### 8. shouldReloadMetadataAfterRestart()
```mermaid
sequenceDiagram
    autonumber
    box #e1f5fe Test Suite
    participant Test as CatalogModuleIntegrationTest
    end
    box #e8f5e9 Catalog Module Components
    participant System as System
    end

    Test->>System: shouldReloadMetadataAfterRestart()
    System-->>Test: success
```
