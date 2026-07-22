package dbms.server;

import dbms.catalog.database.Database;
import dbms.catalog.database.DatabaseStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public class DefaultDatabaseFactory extends DatabaseFactory {
    @Override
    public Database createDatabase(DatabaseCreateRequest request) {
        String databaseId = UUID.randomUUID().toString();
        return new Database(databaseId, request.getName(), request.getOwner(), DatabaseStatus.ONLINE, LocalDateTime.now());
    }
}
