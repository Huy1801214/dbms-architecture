# File Metadata - High Level Class Diagram

```mermaid
classDiagram

class DatabaseFile {
    <<Aggregate Root>>
}

class FileHeader {
    <<Value Object>>
}

class FileIdentifier {
    <<Value Object>>
}

class FileConfiguration {
    <<Value Object>>
}

class FileStatistics {
    <<Value Object>>
}

class FileState {
    <<Value Object>>
}

class MetadataManager {
    <<Domain Service>>
}

class MetadataFactory {
    <<Domain Factory>>
}

class MetadataValidator {
    <<Domain Service>>
}

class IMetadataFactory {
    <<interface>>
}

class IMetadataValidator {
    <<interface>>
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