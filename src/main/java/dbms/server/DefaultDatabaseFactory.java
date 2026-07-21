package dbms.server;

import dbms.catalog.Database;
import dbms.catalog.DatabaseStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public class DefaultDatabaseFactory extends DatabaseFactory {
    @Override
    public Database createDatabase(DatabaseCreateRequest request) {
        String databaseId = UUID.randomUUID().toString();
        return new Database(databaseId, request.getName(), request.getOwner(), DatabaseStatus.ONLINE, LocalDateTime.now());
    }
}
