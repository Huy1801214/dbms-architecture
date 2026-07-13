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

class IMetadataFactory {
    
}

class IMetadataValidator {
    
}

DatabaseFile *-- FileHeader
DatabaseFile *-- FileIdentifier
DatabaseFile *-- FileConfiguration
DatabaseFile *-- FileStatistics
DatabaseFile *-- FileState

MetadataFactory ..|> IMetadataFactory
MetadataValidator ..|> IMetadataValidator

MetadataFactory --> DatabaseFile : creates
MetadataManager --> DatabaseFile : manages
MetadataValidator --> DatabaseFile : validates
```