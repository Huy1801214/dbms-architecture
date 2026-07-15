package dbms.metadata.domain;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import dbms.metadata.request.CreateDatabaseMetadataRequest;

@ExtendWith(MockitoExtension.class)
public class MetadataManagerTest {
    @org.mockito.Mock
    private IMetadataRepository metadataRepository;

    @org.mockito.Mock
    private IMetadataFactory metadataFactory;

    @org.mockito.Mock
    private IMetadataValidator metadataValidator;

    @org.mockito.InjectMocks
    private MetadataManager metadataManager;

    @org.junit.jupiter.api.Test
    void should_createAndPersistDatabaseFile_when_requestIsValid() {
        CreateDatabaseMetadataRequest request = new CreateDatabaseMetadataRequest("test_db",
                "d:/My Project/DBMS/test.db", FileType.DATA, 4096, 1024L, 2048L, true, 512L);
        DatabaseFile expectedFile = new DatabaseFile(request.getFilePath(), request.getFileType(), null, null, null,
                FileState.ONLINE, null);
        ValidationResult validResult = new ValidationResult(true, new java.util.ArrayList<>());

        org.mockito.Mockito.when(metadataFactory.createDatabaseFile(request)).thenReturn(expectedFile);
        org.mockito.Mockito.when(metadataValidator.validate(expectedFile)).thenReturn(validResult);

        DatabaseFile rsFile = metadataManager.createMetadata(request);

        org.junit.jupiter.api.Assertions.assertNotNull(rsFile);
        org.junit.jupiter.api.Assertions.assertEquals(expectedFile, rsFile);
        org.mockito.Mockito.verify(metadataRepository, org.mockito.Mockito.times(1)).persist(rsFile);

    }

}
