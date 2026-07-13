# File Metadata - High Level Class Diagram

```mermaid
classDiagram

class DatabaseFile {
    
}

class FileHeader {
    
}

class FileIdentifier {
   
}

class FileConfiguration {
    
}

class FileStatistics {
    
}

class FileState {
    
}

class MetadataManager {
    
}

class MetadataFactory {
    
}

class MetadataValidator {
    
}

class MetadataRepository {
    
}

class IMetadataFactory {
    
}

class IMetadataValidator {
    
}

class IMetadataRepository {
    
}

DatabaseFile *-- FileHeader
DatabaseFile *-- FileIdentifier
DatabaseFile *-- FileConfiguration
DatabaseFile *-- FileStatistics
DatabaseFile *-- FileState

MetadataFactory ..|> IMetadataFactory
MetadataValidator ..|> IMetadataValidator
MetadataRepository ..|> IMetadataRepository

MetadataFactory --> DatabaseFile : creates
MetadataValidator --> DatabaseFile : validates
MetadataRepository --> DatabaseFile : persists

MetadataManager --> IMetadataFactory : uses
MetadataManager --> IMetadataValidator : uses
MetadataManager --> IMetadataRepository : uses

MetadataRepository --> IMetadataFactory : uses
```