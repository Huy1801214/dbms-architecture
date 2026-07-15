package dbms.metadata.domain;

import dbms.metadata.request.CreateDatabaseMetadataRequest;

public class MetadataManager {
    private final IMetadataRepository metadataRepository;
    private final IMetadataFactory metadataFactory;
    private final IMetadataValidator metadataValidator;

    public MetadataManager(IMetadataRepository metadataRepository, IMetadataFactory metadataFactory,
            IMetadataValidator metadataValidator) {
        this.metadataRepository = metadataRepository;
        this.metadataFactory = metadataFactory;
        this.metadataValidator = metadataValidator;
    }

    public DatabaseFile createMetadata(CreateDatabaseMetadataRequest request) {
        DatabaseFile rs = this.metadataFactory.createDatabaseFile(request);

        ValidationResult validationResult = this.metadataValidator.validate(rs);

        if (!validationResult.getIsValid()) {
            throw new IllegalArgumentException();
        }
        this.metadataRepository.persist(rs);

        return rs;
    }
}
