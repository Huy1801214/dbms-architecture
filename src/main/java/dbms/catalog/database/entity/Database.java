package dbms.catalog.database.entity;

import dbms.catalog.base.entity.DatabaseComponent;
import dbms.catalog.base.enums.DropMode;
import dbms.catalog.base.enums.LifecycleStatus;
import dbms.catalog.database.enums.DatabaseStatus;
import dbms.catalog.schema.entity.Schema;

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
    }

    private void validateOwner(String owner) {
    }

    public void open() {
    }

    public void close() {
    }

    @Override
    public void rename(String newName) {
    }

    public void setOwner(String owner) {
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

    @Override
    public UUID getId() {
        if (databaseId == null)
            return null;
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
    }

    @Override
    public List<DatabaseComponent> getChildren() {
        return new ArrayList<>(schemas.values());
    }

    public void addSchema(Schema schema) {
    }

    public void removeSchema(UUID schemaId) {
    }

    public Schema findSchema(String name) {
        return schemas.get(name);
    }

    public Schema createSchema(String name, String owner) {
        return null;
    }

    public void dropSchema(String key) {
    }

    public Schema getSchema(String key) {
        return null;
    }

    public List<Schema> listSchemas() {
        return new ArrayList<>(schemas.values());
    }
}
