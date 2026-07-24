package dbms.server;

import dbms.catalog.database.Database;
import dbms.catalog.database.DatabaseStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
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
        if (findDatabaseByName(request.getName()) != null) {
            throw new IllegalStateException("Duplicate database name: " + request.getName());
        }
        Database db = new Database(UUID.randomUUID().toString(), request.getName(), request.getOwner(), DatabaseStatus.ONLINE, LocalDateTime.now());
        databases.put(db.getDatabaseId(), db);
        return db;
    }

    public void dropDatabase(String databaseId) {
        Database database = findDatabaseById(databaseId);
        if (database == null) {
            throw new IllegalStateException("Database not found: " + databaseId);
        }
        database.validateDropOperation();
        unregisterDatabase(databaseId);
    }

    public Database findDatabaseById(String databaseId) {
        return databases.get(databaseId);
    }

    public Database findDatabaseByName(String databaseName) {
        if (databaseName == null) return null;
        for (Database db : databases.values()) {
            if (databaseName.equalsIgnoreCase(db.getName())) {
                return db;
            }
        }
        return null;
    }

    public List<Database> listAllDatabases() {
        return new ArrayList<>(databases.values());
    }

    public void renameDatabase(String databaseId, String name) {
        Database db = findDatabaseById(databaseId);
        if (db != null) {
            db.rename(name);
        }
    }

    private void unregisterDatabase(String databaseId) {
        databases.remove(databaseId);
    }
}