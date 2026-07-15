package dbms.metadata.domain;

import dbms.metadata.request.CreateDatabaseMetadataRequest;

public interface IMetadataFactory {
    DatabaseFile createDatabaseFile(CreateDatabaseMetadataRequest request);
}
