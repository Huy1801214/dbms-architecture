package dbms.server;

import dbms.catalog.Database;
import java.util.List;
import java.util.Map;

public class DatabaseManager {
    private Map<String, Database> databases;

    public Database createDatabase(String name, String owner) {
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