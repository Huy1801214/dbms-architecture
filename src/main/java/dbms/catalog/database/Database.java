package dbms.catalog.database;

import dbms.catalog.base.DatabaseComponent;
import dbms.catalog.base.LifecycleStatus;
import dbms.catalog.base.DropMode;
import dbms.catalog.schema.Schema;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class Database implements DatabaseComponent {
    private String databaseId;
    private String name;
    private String owner;
    private DatabaseStatus status;
    private LocalDateTime createdAt;
    private Map<String, Schema> schemas = new HashMap<>();
    private LifecycleStatus lifecycleStatus = LifecycleStatus.ACTIVE;

    public Database() {
        this.lifecycleStatus = LifecycleStatus.ACTIVE;
    }

    public Database(String databaseId, String name, String owner, DatabaseStatus status, LocalDateTime createdAt) {
        if (status == null) {
            throw new IllegalStateException("Status cannot be null");
        }
        validateName(name);
        validateOwner(owner);
        this.databaseId = databaseId;
        this.name = name;
        this.owner = owner;
        this.status = status;
        this.createdAt = createdAt;
        this.lifecycleStatus = LifecycleStatus.ACTIVE;
    }

    private void validateName(String name) {
        if (name == null) {
            throw new IllegalStateException("Database name cannot be null");
        }
        if (name.trim().isEmpty()) {
            throw new IllegalStateException("Database name cannot be empty");
        }
        if (name.length() > 64) {
            throw new IllegalStateException("Database name cannot exceed 64 characters");
        }
        if (!name.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalStateException("Database name contains invalid characters");
        }
        if ("system".equalsIgnoreCase(name)) {
            throw new IllegalStateException("Database name cannot be 'system'");
        }
    }

    private void validateOwner(String owner) {
        if (owner == null) {
            throw new IllegalStateException("Owner cannot be null");
        }
    }

    public void open() {
        if (status == DatabaseStatus.ONLINE || status == DatabaseStatus.OPENING || status == DatabaseStatus.CLOSING) {
            throw new IllegalStateException("Database is already online or in transition");
        }
        this.status = DatabaseStatus.ONLINE;
    }

    public void close() {
        if (status == DatabaseStatus.OFFLINE || status == DatabaseStatus.CLOSING) {
            throw new IllegalStateException("Database is already offline or in transition");
        }
        this.status = DatabaseStatus.OFFLINE;
    }

    @Override
    public void rename(String newName) {
        if (status == DatabaseStatus.OPENING || status == DatabaseStatus.CLOSING || status == DatabaseStatus.OFFLINE) {
            throw new IllegalStateException("Cannot rename database in current status: " + status);
        }
        validateName(newName);
        this.name = newName;
    }

    public void setOwner(String owner) {
        validateOwner(owner);
        this.owner = owner;
    }

    public void validateCurrentState() {
        if (status == DatabaseStatus.OFFLINE) {
            throw new UnsupportedOperationException("Database is offline");
        }
    }

    public void validateDropOperation() {
    }

    public void executeOperation() {
        validateCurrentState();
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

    @Override
    public UUID getId() {
        if (databaseId == null) return null;
        try {
            return UUID.fromString(databaseId);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public String getQualifiedName() {
        return "DATABASE:" + name;
    }

    @Override
    public LifecycleStatus getLifecycleStatus() {
        return lifecycleStatus;
    }

    @Override
    public void drop(DropMode mode) {
        if (this.lifecycleStatus != LifecycleStatus.ACTIVE) {
            throw new IllegalStateException("Database is not active");
        }
        if (mode == DropMode.RESTRICT && !schemas.isEmpty()) {
            throw new IllegalStateException("Cannot drop database with active schemas in RESTRICT mode");
        }
        this.lifecycleStatus = LifecycleStatus.DROPPING;
        if (mode == DropMode.CASCADE) {
            List<Schema> schemasToDrop = new ArrayList<>(schemas.values());
            for (Schema s : schemasToDrop) {
                s.drop(mode);
                schemas.remove(s.getName());
            }
        }
        this.lifecycleStatus = LifecycleStatus.DROPPED;
    }

    @Override
    public List<DatabaseComponent> getChildren() {
        return new ArrayList<>(schemas.values());
    }

    public void addSchema(Schema schema) {
        if (status == DatabaseStatus.OFFLINE) {
            throw new IllegalStateException("Database is closed");
        }
        if (schema == null || schema.getName() == null) {
            throw new IllegalArgumentException();
        }
        if (schemas.containsKey(schema.getName())) {
            throw new IllegalStateException("Schema already exists");
        }
        schemas.put(schema.getName(), schema);
    }

    public void removeSchema(UUID schemaId) {
        schemas.values().removeIf(s -> s.getId().equals(schemaId));
    }

    public Schema findSchema(String name) {
        return schemas.get(name);
    }

    public Schema createSchema(String name, String owner) {
        if (status == DatabaseStatus.OFFLINE) {
            throw new IllegalStateException("Database is closed");
        }
        if (name == null || !name.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalStateException("Invalid schema name");
        }
        if (schemas.containsKey(name)) {
            throw new IllegalStateException("Duplicate schema name");
        }
        Schema schema = new Schema(UUID.randomUUID().toString(), name, owner);
        schemas.put(name, schema);
        return schema;
    }

    public void dropSchema(String key) {
        if (schemas.containsKey(key)) {
            schemas.remove(key);
        } else {
            // Find by ID
            schemas.values().removeIf(s -> s.getSchemaId().equals(key));
        }
    }

    public Schema getSchema(String key) {
        if (schemas.containsKey(key)) {
            return schemas.get(key);
        }
        for (Schema s : schemas.values()) {
            if (s.getSchemaId().equals(key)) {
                return s;
            }
        }
        return null;
    }

    public List<Schema> listSchemas() {
        return new ArrayList<>(schemas.values());
    }
}

