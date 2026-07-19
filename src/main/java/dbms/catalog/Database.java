package dbms.catalog;

import java.time.LocalDateTime;

public class Database {
    private String databaseId;
    private String name;
    private String owner;
    private DatabaseStatus status;
    private LocalDateTime createdAt;

    public Database(String databaseId, String name, String owner, DatabaseStatus status, LocalDateTime createdAt) {
        this.databaseId = databaseId;
        this.name = name;
        this.owner = owner;
        this.status = status;
        this.createdAt = createdAt;
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
    }

    public void rename(String newName) {
    }

    public void setOwner(String owner) {
    }

    public void updateStatus(DatabaseStatus status) {
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
}