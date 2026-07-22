package dbms.server;

import dbms.catalog.database.Database;
import dbms.catalog.database.DatabaseStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

public class DatabaseManager {
    private Map<String, Database> databases = new HashMap<>();

    public DatabaseManager() {
    }

    public Database createDatabase(String name, String owner) {
        return createDatabase(new DatabaseCreateRequest(name, owner));
    }

    public Database createDatabase(DatabaseCreateRequest request) {
        if (request == null || request.getName() == null) return null;
        Database db = new Database(UUID.randomUUID().toString(), request.getName(), request.getOwner(), DatabaseStatus.OFFLINE, LocalDateTime.now());
        databases.put(db.getDatabaseId(), db);
        return db;
    }

    public void dropDatabase(String databaseId) {
        Database database = findDatabaseById(databaseId);
        if (database != null) {
            database.validateDropOperation();
            unregisterDatabase(databaseId);
        }
    }

    public Database findDatabaseById(String databaseId) {
        return null;
    }

    public Database findDatabaseByName(String databaseName) {
        return null;
    }

    public List<Database> listAllDatabases() {
        return null;
    }

    public void renameDatabase(String databaseId, String name) {
    }

    private void unregisterDatabase(String databaseId) {
    }
}