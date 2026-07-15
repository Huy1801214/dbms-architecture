package dbms.metadata.domain;

import java.util.UUID;

public class FileIdentifier {
    private final UUID value;
    private final String databaseName;

    public FileIdentifier(UUID value, String databaseName) {
        this.value = value;
        this.databaseName = databaseName;
    }

    public UUID getValue() {
        return this.value;
    }

    public String getDatabaseName() {
        return this.databaseName;
    }
}
