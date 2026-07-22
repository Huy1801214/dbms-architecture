package dbms.catalog.database;

import dbms.catalog.schema.Schema;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private String databaseId;
    private String name;
    private String owner;
    private DatabaseStatus status;
    private LocalDateTime createdAt;
    private Map<String, Schema> schemas = new HashMap<>();

    public Database() {
    }

    public Database(String databaseId, String name, String owner, DatabaseStatus status, LocalDateTime createdAt) {
        this.databaseId = databaseId;
        this.name = name;
        this.owner = owner;
        this.status = status;
        this.createdAt = createdAt;
    }

    public void open() {
        this.status = DatabaseStatus.ONLINE;
    }

    public void close() {
        this.status = DatabaseStatus.OFFLINE;
    }

    public void rename(String newName) {
        this.name = newName;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void validateCurrentState() {
    }

    public void validateDropOperation() {
    }

    public void executeOperation() {
    }

    public String getDatabaseId() {
        return databaseId;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public DatabaseStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Schema createSchema(String name, String owner) {
        Schema schema = new Schema(java.util.UUID.randomUUID().toString(), name, owner);
        schemas.put(name, schema);
        return schema;
    }

    public void dropSchema(String name) {
        schemas.remove(name);
    }

    public Schema getSchema(String name) {
        return schemas.get(name);
    }

    public List<Schema> listSchemas() {
        return new java.util.ArrayList<>(schemas.values());
    }
}
