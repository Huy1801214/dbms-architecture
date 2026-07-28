package dbms.server.service;

import dbms.catalog.database.entity.Database;
import dbms.catalog.database.enums.DatabaseStatus;
import dbms.server.dto.DatabaseCreateRequest;

import dbms.catalog.database.entity.Database;
import dbms.catalog.database.enums.DatabaseStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

public class DatabaseManager {
    private Map<String, Database> databases = new HashMap<>();

    private static DatabaseManager instance;

    private DatabaseManager() {
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Database createDatabase(String name, String owner) {
        return createDatabase(new DatabaseCreateRequest(name, owner));
    }

    public Database createDatabase(DatabaseCreateRequest request) {
        return null;
    }

    public void dropDatabase(String databaseId) {

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

}
