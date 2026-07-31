package api.repository;

import dbms.catalog.database.entity.Database;
import dbms.catalog.database.enums.DatabaseStatus;
import dbms.catalog.schema.entity.Schema;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class DatabaseRepository {
    private final List<Database> databases = new ArrayList<>();

    public DatabaseRepository() {
        loadMockData();
    }

    private void loadMockData() {
        InputStream is = getClass().getResourceAsStream("/mock/database.json");
        if (is == null) {
            is = getClass().getClassLoader().getResourceAsStream("mock/database.json");
        }
        if (is != null) {
            try (InputStream input = is) {
                ObjectMapper mapper = new ObjectMapper();
                List<MockDatabaseData> mockList = mapper.readValue(input, new TypeReference<List<MockDatabaseData>>() {
                });
                for (MockDatabaseData mock : mockList) {
                    DatabaseStatus status = DatabaseStatus.ONLINE;
                    if (mock.status() != null) {
                        try {
                            status = DatabaseStatus.valueOf(mock.status().toUpperCase());
                        } catch (IllegalArgumentException ignored) {
                        }
                    }
                    Database db = new Database(
                            mock.id(),
                            mock.name(),
                            mock.owner(),
                            status,
                            LocalDateTime.now());
                    databases.add(db);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Database> findAllDatabases() {
        return List.copyOf(databases);
    }

    public Database save(Database db) {
        String newId = UUID.randomUUID().toString();
        Database newDb = new Database(newId, db.getName(), db.getOwner(), db.getStatus(), db.getCreatedAt());
        databases.add(newDb);
        return newDb;
    }

    public Database findDatabaseById(String databaseId) {
        if (databaseId == null)
            return null;
        return databases.stream().filter(db -> databaseId.equals(db.getDatabaseId())).findFirst().orElse(null);
    }

    public Schema saveSchema(String databaseId, Schema schema) {
        Database database = findDatabaseById(databaseId);
        if (database == null) {
            throw new IllegalArgumentException("Database not found: " + databaseId);
        }
        String newId = UUID.randomUUID().toString();
        Schema newSchema = new Schema(newId, schema.getName(), schema.getOwner());
        database.addSchema(newSchema);
        return newSchema;
    }

    private record MockDatabaseData(String id, String name, String owner, String status) {
    }
}
