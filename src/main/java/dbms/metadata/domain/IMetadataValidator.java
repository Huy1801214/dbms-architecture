package dbms.metadata.domain;

public interface IMetadataValidator {
    ValidationResult validate(DatabaseFile databaseFile);
}
