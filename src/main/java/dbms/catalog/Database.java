package dbms.catalog;

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
    private Map<String, Schema> schemas;

    public Database(String databaseId, String name, String owner, DatabaseStatus status, LocalDateTime createdAt) {
        this.databaseId = databaseId;
        this.name = name;
        this.owner = owner;
        this.status = status;
        this.createdAt = createdAt;
        this.schemas = new HashMap<>();
    }

    public void open() {
        validateCurrentState();
        status = DatabaseStatus.OPENING;
        initialize();
        status = DatabaseStatus.ONLINE;
    }

    private void initialize() {

    }

    private void validateCurrentState() {

    }

    public void close() {
        validateCurrentState();

        status = DatabaseStatus.CLOSING;

        flushDirtyPages();
        releaseResources();

        status = DatabaseStatus.OFFLINE;
    }

    private void flushDirtyPages() {

    }

    private void releaseResources() {

    }

    public void rename(String newName) {
        validateCurrentState();
        validateNewName(newName);
        this.name = newName;
    }

    private void validateNewName(String newName) {

    }

    public void setOwner(String owner) {
        validateCurrentState();
        validateOwner(owner);
        this.owner = owner;
    }

    public void validateDropOperation() {

    }

    private void validateOwner(String owner) {

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

    public Schema createSchema(String name, String owner) {
        return null;
    }

    public void dropSchema(String name) {
    }

    public Schema getSchema(String name) {
        return null;
    }

    public List<Schema> listSchemas() {
        return null;
    }
}