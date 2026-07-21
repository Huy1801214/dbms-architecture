package dbms.server;

import dbms.catalog.Database;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class DatabaseManager {
    private Map<String, Database> databases = new HashMap<>();
    private DatabaseFactory factory;

    public DatabaseManager() {
        this.factory = new DefaultDatabaseFactory();
    }

    public DatabaseManager(DatabaseFactory factory) {
        this.factory = factory;
    }

    public Database createDatabase(String name, String owner) {
        return createDatabase(new DatabaseCreateRequest(name, owner));
    }

    public Database createDatabase(DatabaseCreateRequest request) {

        return null;
    }

    public void dropDatabase(String databaseId) {
        Database database = findDatabaseById(databaseId);
        database.validateDropOperation();
        unregisterDatabase(databaseId);
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